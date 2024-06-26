from timefold.solver.score import ConstraintJustification
from dataclasses import dataclass, field

from .json_serialization import *



@dataclass
class MatchAnalysisDTO:
    name: str
    score: Annotated[HardSoftScore, ScoreSerializer]
    justification: object


@dataclass
class ConstraintAnalysisDTO:
    name: str
    weight: Annotated[HardSoftScore, ScoreSerializer]
    matches: list[MatchAnalysisDTO]
    score: Annotated[HardSoftScore, ScoreSerializer]

@dataclass
class VehicleCapacityJustification(ConstraintJustification):
    vehicle_id: str
    capacity: int
    demand: int
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Vehicle '{self.vehicle_id}' exceeded its max capacity by "
                            f"{self.demand - self.capacity}.")


@dataclass
class MinimizeTravelTimeJustification(ConstraintJustification):
    vehicle_name: str
    total_driving_time_seconds: int
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Vehicle '{self.vehicle_name}' total travel time is "
                            f"{self.total_driving_time_seconds // (60 * 60)} hours "
                            f"{round(self.total_driving_time_seconds / 60)} minutes.")
