from datetime import date, datetime, time, timedelta
from itertools import product
from enum import Enum
from random import Random
from typing import Generator

from .domain import *


class DemoData(Enum):
    SMALL = 'SMALL'
    LARGE = 'LARGE'


FIRST_NAMES = ("Amy", "Beth", "Chad", "Dan", "Elsa", "Flo", "Gus", "Hugo", "Ivy", "Jay")
LAST_NAMES = ("Cole", "Fox", "Green", "Jones", "King", "Li", "Poe", "Rye", "Smith", "Watt")
REQUIRED_SKILLS = ("Doctor", "Nurse")
OPTIONAL_SKILLS = ("Anaesthetics", "Cardiology")
LOCATIONS = ("Ambulatory care", "Critical care", "Pediatric care")
SHIFT_LENGTH = timedelta(hours=8)
MORNING_SHIFT_START_TIME = time(hour=6, minute=0)
DAY_SHIFT_START_TIME = time(hour=9, minute=0)
AFTERNOON_SHIFT_START_TIME = time(hour=14, minute=0)
NIGHT_SHIFT_START_TIME = time(hour=22, minute=0)

SHIFT_START_TIMES_COMBOS = (
    (MORNING_SHIFT_START_TIME, AFTERNOON_SHIFT_START_TIME),
    (MORNING_SHIFT_START_TIME, AFTERNOON_SHIFT_START_TIME, NIGHT_SHIFT_START_TIME),
    (MORNING_SHIFT_START_TIME, DAY_SHIFT_START_TIME, AFTERNOON_SHIFT_START_TIME, NIGHT_SHIFT_START_TIME),
)


location_to_shift_start_time_list_map = dict()


def earliest_monday_on_or_after(target_date: date):
    """
    Returns the date of the next given weekday after
    the given date. For example, the date of next Monday.

    NB: if it IS the day we're looking for, this returns 0.
    consider then doing onDay(foo, day + 1).
    """
    days = (7 - target_date.weekday()) % 7
    return target_date + timedelta(days=days)


def generate_demo_data() -> EmployeeSchedule:
    global location_to_shift_start_time_list_map
    initial_roster_length_in_days = 14
    start_date = earliest_monday_on_or_after(date.today())

    random = Random(37)
    shift_template_index = 0
    for location in LOCATIONS:
        location_to_shift_start_time_list_map[location] = SHIFT_START_TIMES_COMBOS[shift_template_index]
        shift_template_index = (shift_template_index + 1) % len(SHIFT_START_TIMES_COMBOS)

    name_permutations = [f'{first_name} {last_name}'
                         for first_name, last_name in product(FIRST_NAMES, LAST_NAMES)]
    random.shuffle(name_permutations)

    employees = []
    for i in range(15):
        count, = random.sample([1, 2], 1, counts=[3, 2])
        skills = random.sample(OPTIONAL_SKILLS, count)
        skills += random.sample(REQUIRED_SKILLS, 1)
        employees.append(Employee(
            name=name_permutations[i],
            skills=set(skills),
            unavailable_dates=set(),
            undesired_dates=set(),
            desired_dates=set(),
        ))

    shifts: list[Shift] = []

    def id_generator():
        current_id = 0
        while True:
            yield str(current_id)
            current_id += 1

    ids = id_generator()

    for i in range(initial_roster_length_in_days):
        count, = random.sample([1, 2, 3, 4], 1, counts=[4, 2, 3, 1])
        employees_with_availabilities_on_day = random.sample(employees, count)
        current_date = start_date + timedelta(days=i)
        for employee in employees_with_availabilities_on_day:
            rand_num = random.randint(0, 2)
            if rand_num == 0:
                employee.unavailable_dates.add(current_date)
            elif rand_num == 1:
                employee.undesired_dates.add(current_date)
            elif rand_num == 2:
                employee.desired_dates.add(current_date)
        shifts += generate_shifts_for_day(current_date, random, ids)

    shift_count = 0
    for shift in shifts:
        shift.id = str(shift_count)
        shift_count += 1

    return EmployeeSchedule(
        employees=employees,
        shifts=shifts
    )


def generate_shifts_for_day(current_date: date, random: Random, ids: Generator[str, any, any]) -> list[Shift]:
    global location_to_shift_start_time_list_map
    shifts = []
    for location in LOCATIONS:
        shift_start_times = location_to_shift_start_time_list_map[location]
        for start_time in shift_start_times:
            shift_start_date_time = datetime.combine(current_date, start_time)
            shift_end_date_time = shift_start_date_time + SHIFT_LENGTH
            shifts += generate_shifts_for_timeslot(shift_start_date_time, shift_end_date_time, location, random, ids)

    return shifts


def generate_shifts_for_timeslot(timeslot_start: datetime, timeslot_end: datetime, location: str,
                                 random: Random, ids: Generator[str, any, any]) -> list[Shift]:
    shift_count = 1

    if random.random() > 0.9:
        # generate an extra shift
        shift_count += 1

    shifts = []
    for i in range(shift_count):
        if random.random() >= 0.5:
            required_skill = random.choice(REQUIRED_SKILLS)
        else:
            required_skill = random.choice(OPTIONAL_SKILLS)
        shifts.append(Shift(
            id=next(ids),
            start=timeslot_start,
            end=timeslot_end,
            location=location,
            required_skill=required_skill))

    return shifts
