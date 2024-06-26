from timefold.solver import SolverStatus
from timefold.solver.score import HardSoftScore, ScoreDirector
from timefold.solver.domain import *

from dataclasses import dataclass
from typing import Annotated, Optional, Any
from pydantic import Field, computed_field, BeforeValidator

from .json_serialization import *


LocationValidator = BeforeValidator(lambda location: location if isinstance(location, Location)
                                    else Location(latitude=location[0], longitude=location[1]))
class Location(JsonDomainBase):
    latitude: float
    longitude: float

    def driving_time_to(self, other: 'Location') -> int:
        return round((
             (self.latitude - other.latitude) ** 2 +
             (self.longitude - other.longitude) ** 2
         ) ** 0.5 * 1000)

    def __str__(self):
        return f'[{self.latitude}, {self.longitude}]'

    def __repr__(self):
        return f'Location({self.latitude}, {self.longitude})'


@planning_entity
class Visit(JsonDomainBase):
    id: Annotated[str, PlanningId]
    name: str
    location: Annotated[Location, LocationSerializer, LocationValidator]
    demand: int
    vehicle: Annotated[Optional['Vehicle'],
                       InverseRelationShadowVariable(source_variable_name='visits'),
                       IdSerializer, VehicleValidator, Field(default=None)]
    previous_visit: Annotated[Optional['Visit'],
                              PreviousElementShadowVariable(source_variable_name='visits'),
                              IdSerializer, VisitValidator, Field(default=None)]
    next_visit: Annotated[Optional['Visit'],
                          NextElementShadowVariable(source_variable_name='visits'),
                          IdSerializer, VisitValidator, Field(default=None)]

    def driving_time_seconds_from_previous_standstill(self) -> int:
        if self.vehicle is None:
            raise ValueError("This method must not be called when the shadow variables are not initialized yet.")

        if self.previous_visit is None:
            return self.vehicle.home_location.driving_time_to(self.location)
        else:
            return self.previous_visit.location.driving_time_to(self.location)

    def driving_time_seconds_from_previous_standstill_or_none(self) -> Optional[int]:
        if self.vehicle is None:
            return None
        return self.driving_time_seconds_from_previous_standstill()

    def __str__(self):
        return self.id

    def __repr__(self):
        return f'Visit({self.id})'


@planning_entity
class Vehicle(JsonDomainBase):
    id: Annotated[str, PlanningId]
    capacity: int
    home_location: Annotated[Location, LocationSerializer, LocationValidator]
    visits: Annotated[list[Visit],
                      PlanningListVariable,
                      IdListSerializer, VisitListValidator, Field(default_factory=list)]

    @computed_field
    @property
    def total_demand(self) -> int:
        return self.calculate_total_demand()

    @computed_field
    @property
    def total_driving_time_seconds(self) -> int:
        return self.calculate_total_driving_time_seconds()

    def calculate_total_demand(self) -> int:
        total_demand = 0
        for visit in self.visits:
            total_demand += visit.demand
        return total_demand

    def calculate_total_driving_time_seconds(self) -> int:
        if len(self.visits) == 0:
            return 0
        total_driving_time_seconds = 0
        previous_location = self.home_location

        for visit in self.visits:
            total_driving_time_seconds += previous_location.driving_time_to(visit.location)
            previous_location = visit.location

        total_driving_time_seconds += previous_location.driving_time_to(self.home_location)
        return total_driving_time_seconds

    def __str__(self):
        return self.id

    def __repr__(self):
        return f'Vehicle({self.id})'


@planning_solution
class VehicleRoutePlan(JsonDomainBase):
    name: str
    south_west_corner: Annotated[Location, LocationSerializer, LocationValidator]
    north_east_corner: Annotated[Location, LocationSerializer, LocationValidator]
    vehicles: Annotated[list[Vehicle], PlanningEntityCollectionProperty]
    visits: Annotated[list[Visit], PlanningEntityCollectionProperty, ValueRangeProvider]
    score: Annotated[Optional[HardSoftScore],
                     PlanningScore,
                     ScoreSerializer, ScoreValidator, Field(default=None)]
    solver_status: Annotated[Optional[SolverStatus],
                             Field(default=None)]

    @computed_field
    @property
    def total_driving_time_seconds(self) -> int:
        out = 0
        for vehicle in self.vehicles:
            out += vehicle.total_driving_time_seconds
        return out

    def __str__(self):
        return f'VehicleRoutePlan(name={self.id}, vehicles={self.vehicles}, visits={self.visits})'
