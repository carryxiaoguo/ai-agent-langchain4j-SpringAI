"""后端开发入口，便于使用 `uvicorn main:app` 启动服务。"""

from app.main import app

__all__ = ["app"]
