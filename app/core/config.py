from functools import lru_cache

from pydantic import Field, field_validator
from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    """读取后端运行配置，避免将环境值散落在业务代码中。"""

    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        env_prefix="APP_",
        extra="ignore",
    )

    name: str = Field(default="ai-agent-api", min_length=1)
    environment: str = Field(default="development", pattern="^(development|test|production)$")
    api_prefix: str = Field(default="/api/v1", pattern=r"^/[^/].*")
    cors_origins: list[str] = ["http://127.0.0.1:3000", "http://localhost:3000"]

    @field_validator("cors_origins", mode="before")
    @classmethod
    def parse_cors_origins(cls, value: object) -> object:
        """允许环境变量使用逗号分隔，避免示例配置要求 JSON 数组。"""

        if isinstance(value, str):
            return [origin.strip() for origin in value.split(",") if origin.strip()]
        return value


@lru_cache
def get_settings() -> Settings:
    """缓存配置对象，让每个请求复用同一份只读配置。"""

    return Settings()
