from timefold.solver.test import ConstraintVerifier

from vehicle_routing.domain import *
from vehicle_routing.constraints import *

# LOCATION_1 to LOCATION_2 is sqrt(3**2 + 4**2) * 1000 == 5000 seconds of driving time
# LOCATION_2 to LOCATION_3 is sqrt(3**2 + 4**2) * 1000 == 5000 seconds of driving time
# LOCATION_1 to LOCATION_3 is sqrt(1**2 + 1**2) * 1000 == 1414 seconds of driving time

LOCATION_1 = Location(latitude=0, longitude=0)
LOCATION_2 = Location(latitude=3, longitude=4)
LOCATION_3 = Location(latitude=-1, longitude=1)

constraint_verifier = ConstraintVerifier.build(define_constraints, VehicleRoutePlan, Vehicle, Visit)


def test_vehicle_capacity_unpenalized():
    vehicleA = Vehicle(id="1", capacity=100, home_location=LOCATION_1)
    visit1 = Visit(id="2", name="John", location=LOCATION_2, demand=80)
    vehicleA.visits.append(visit1)

    (constraint_verifier.verify_that(vehicle_capacity)
        .given(vehicleA, visit1)
        .penalizes_by(0))


def test_vehicle_capacity_penalized():
    vehicleA = Vehicle(id="1", capacity=100, home_location=LOCATION_1)
    visit1 = Visit(id="2", name="John", location=LOCATION_2, demand=80)
    vehicleA.visits.append(visit1)
    visit2 = Visit(id="3", name="Paul", location=LOCATION_3, demand=40)
    vehicleA.visits.append(visit2)

    (constraint_verifier.verify_that(vehicle_capacity)
        .given(vehicleA, visit1, visit2)
        .penalizes_by(20))


def test_total_driving_time():
    vehicleA = Vehicle(id="1", capacity=100, home_location=LOCATION_1)
    visit1 = Visit(id="2", name="John", location=LOCATION_2, demand=80)
    vehicleA.visits.append(visit1)
    visit2 = Visit(id="3", name="Paul", location=LOCATION_3, demand=40)
    vehicleA.visits.append(visit2)

    (constraint_verifier.verify_that(minimize_travel_time)
        .given(vehicleA, visit1, visit2)
        .penalizes_by(11414) # The sum of the approximate driving time between all three locations.
    )


def connect(vehicle: Vehicle, *visits: Visit):
    vehicle.visits = list(visits)
    for i in range(len(visits)):
        visit = visits[i]
        visit.vehicle = vehicle
        if i > 0:
            visit.setPreviousVisit(visits[i - 1])

        if i < visits.length - 1:
            visit.setNextVisit(visits[i + 1])
