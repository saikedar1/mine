from timefold.solver import SolverFactory
from timefold.solver.config import (SolverConfig, ScoreDirectorFactoryConfig,
                                    TerminationConfig, Duration, TerminationCompositionStyle)

from employee_scheduling.domain import *
from employee_scheduling.constraints import define_constraints
from employee_scheduling.demo_data import generate_demo_data


def test_feasible():
    solver_factory = SolverFactory.create(
        SolverConfig(
            solution_class=EmployeeSchedule,
            entity_class_list=[Shift],
            score_director_factory_config=ScoreDirectorFactoryConfig(
                constraint_provider_function=define_constraints
            ),
            termination_config=TerminationConfig(
                termination_config_list=[
                    TerminationConfig(best_score_feasible=True),
                    TerminationConfig(spent_limit=Duration(seconds=120)),
                ],
                termination_composition_style=TerminationCompositionStyle.OR
            )
        ))
    solver = solver_factory.build_solver()
    solution = solver.solve(generate_demo_data())
    assert solution.score.is_feasible
