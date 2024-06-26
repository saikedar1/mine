from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles

from .domain import EmployeeSchedule
from .demo_data import DemoData, generate_demo_data
from .solver import solver_manager, solution_manager

app = FastAPI(docs_url='/q/swagger-ui')
data_sets: dict[str, EmployeeSchedule] = {}


@app.get("/demo-data")
async def demo_data_list() -> list[DemoData]:
    return [e for e in DemoData]


@app.get("/demo-data/{dataset_id}",  response_model_exclude_none=True)
async def get_demo_data(dataset_id: str) -> EmployeeSchedule:
    return generate_demo_data()


@app.get("/schedules/{problem_id}",  response_model_exclude_none=True)
async def get_timetable(problem_id: str) -> EmployeeSchedule:
    schedule = data_sets[problem_id]
    return schedule.model_copy(update={
        'solver_status': solver_manager.get_solver_status(problem_id)
    })


def update_schedule(problem_id: str, schedule: EmployeeSchedule):
    global data_sets
    data_sets[problem_id] = schedule


@app.post("/schedules")
async def solve_timetable(schedule: EmployeeSchedule) -> str:
    data_sets['ID'] = schedule
    solver_manager.solve_and_listen('ID', schedule,
                                    lambda solution: update_schedule('ID', solution))
    return 'ID'


@app.delete("/schedules/{problem_id}")
async def stop_solving(problem_id: str) -> None:
    solver_manager.terminate_early(problem_id)


app.mount("/", StaticFiles(directory="static", html=True), name="static")
