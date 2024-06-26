from timefold.solver.score import ConstraintJustification
from dataclasses import dataclass, field

from .json_serialization import *
from .domain import *


@dataclass
class RoomConflictJustification(ConstraintJustification):
    room: str
    lesson_a: Lesson
    lesson_b: Lesson
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Room '{self.room}' is used for lesson '{self.lesson_a.subject}' "
                            f"for student group '{self.lesson_a.student_group}' and lesson "
                            f"'{self.lesson_b.subject}' for student group '{self.lesson_b.student_group}'"
                            f"at '{self.lesson_a.timeslot.day_of_week} {self.lesson_a.timeslot.start_time}'")


@dataclass
class TeacherConflictJustification(ConstraintJustification):
    teacher: str
    lesson_a: Lesson
    lesson_b: Lesson
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Teacher '{self.teacher}' needs to teach lesson '{self.lesson_a.subject}' "
                            f"for student group '{self.lesson_a.student_group}' "
                            f"and lesson '{self.lesson_b.subject}' for student group '{self.lesson_b.student_group}' "
                            f"at '{self.lesson_a.timeslot.day_of_week} {self.lesson_a.timeslot.start_time}'")


@dataclass
class StudentGroupConflictJustification(ConstraintJustification):
    student_group: str
    lesson_a: Lesson
    lesson_b: Lesson
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Student group '{self.student_group}' has lesson '{self.lesson_a.subject}' "
                            f"and lesson '{self.lesson_b.subject}' at "
                            f"'{self.lesson_a.timeslot.day_of_week} {self.lesson_a.timeslot.start_time}'")


@dataclass
class TeacherRoomStabilityJustification(ConstraintJustification):
    teacher: str
    lesson_a: Lesson
    lesson_b: Lesson
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Teacher '{self.teacher}' has two lessons in different rooms: "
                            f"room '{self.lesson_a.room}' at "
                            f"'{self.lesson_a.timeslot.day_of_week} {self.lesson_a.timeslot.start_time}' "
                            f"and room '{self.lesson_b.room}' at "
                            f"'{self.lesson_b.timeslot.day_of_week} {self.lesson_b.timeslot.start_time}'")


@dataclass
class TeacherTimeEfficiencyJustification(ConstraintJustification):
    teacher: str
    lesson_a: Lesson
    lesson_b: Lesson
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Teacher '{self.teacher}' has 2 consecutive lessons: "
                            f"lesson '{self.lesson_a.subject}' for student group '{self.lesson_a.student_group}' "
                            f"at '{self.lesson_a.timeslot.day_of_week} {self.lesson_a.timeslot.start_time}' and "
                            f"lesson '{self.lesson_b.subject}' for student group '{self.lesson_b.student_group}' "
                            f"at '{self.lesson_b.timeslot.day_of_week} {self.lesson_b.timeslot.start_time}' (gap)")


@dataclass
class StudentGroupSubjectVarietyJustification(ConstraintJustification):
    student_group: str
    lesson_a: Lesson
    lesson_b: Lesson
    description: str = field(init=False)

    def __post_init__(self):
        self.description = (f"Student Group '{self.student_group}' has two consecutive lessons on "
                            f"'{self.lesson_a.subject}' at "
                            f"'{self.lesson_a.timeslot.day_of_week} {self.lesson_a.timeslot.start_time}' "
                            f"and at '{self.lesson_b.timeslot.day_of_week} {self.lesson_b.timeslot.start_time}'")


class MatchAnalysisDTO(JsonDomainBase):
    name: str
    score: Annotated[HardSoftScore, ScoreSerializer]
    justification: object


class ConstraintAnalysisDTO(JsonDomainBase):
    name: str
    weight: Annotated[HardSoftScore, ScoreSerializer]
    matches: list[MatchAnalysisDTO]
    score: Annotated[HardSoftScore, ScoreSerializer]
