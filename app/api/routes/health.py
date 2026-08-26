from typing import Annotated
from uuid import uuid4

from fastapi import APIRouter, Header, Response
from pydantic import BaseModel

router = APIRouter(tags=["health"])


class HealthResponse(BaseModel):
    status: str
    service: str
    request_id: str


@router.get("/health", response_model=HealthResponse, summary="检查后端服务")
def health_check(
    response: Response,
    x_request_id: Annotated[str | None, Header(alias="X-Request-ID")] = None,
) -> HealthResponse:
    """返回最小健康状态，供前端和本地开发者确认服务已启动。"""

    request_id = x_request_id or str(uuid4())
    response.headers["X-Request-ID"] = request_id
    return HealthResponse(status="ok", service="ai-agent-api", request_id=request_id)
