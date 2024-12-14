from typing import Tuple, Any, List, Dict
from fastapi import FastAPI
from pydantic import BaseModel
import json
from llm import get_llm_response

app = FastAPI()

class Task(BaseModel):
    description: str
    id: int 
    name: str
    projectId: int
    state: str
    tag: str

class RequestModel(BaseModel):
    query: str
    tasks: List[Task]

def convert_to_json(request: RequestModel) -> Tuple[str, List[Dict]]:
    print(request)
    req = request.dict()
    req['tasks'] = [task for task in req['tasks'] if task['state'] == 'DOING']
    for task in req['tasks']:
        task.pop('tag')
        task.pop('projectId') 
        task.pop('id')
        task.pop('state')
    query = req['query']
    tasks = req['tasks']
    return query, tasks

@app.post("/summary")
async def summary(request: RequestModel):
    query, tasks = convert_to_json(request)
    tasks = json.dumps(tasks)
    result = get_llm_response(query, tasks, "summary")
    return result

@app.post("/question")
async def question(request: RequestModel):
    query, tasks = convert_to_json(request)
    tasks = json.dumps(tasks)
    result = get_llm_response(query, tasks)
    return result
