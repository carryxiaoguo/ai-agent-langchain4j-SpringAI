from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from starlette.middleware.base import BaseHTTPMiddleware

from app.api.routes.health import router as health_router
from app.core.config import get_settings


class RequestIdMiddleware(BaseHTTPMiddleware):
    """保持请求关联标识在响应头中可见，便于本地排查接口问题。"""

    async def dispatch(self, request: Request, call_next):  # type: ignore[no-untyped-def]
        response = await call_next(request)
        request_id = request.headers.get("X-Request-ID") or response.headers.get("X-Request-ID")
        if request_id:
            response.headers["X-Request-ID"] = request_id
        return response


def create_app() -> FastAPI:
    settings = get_settings()
    application = FastAPI(title="AI Agent API", version="0.1.0")
    application.add_middleware(RequestIdMiddleware)
    application.add_middleware(
        CORSMiddleware,
        allow_origins=settings.cors_origins,
        allow_credentials=False,
        allow_methods=["GET"],
        allow_headers=["X-Request-ID"],
    )
    application.include_router(health_router, prefix=settings.api_prefix)
    return application


app = create_app()
