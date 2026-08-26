from fastapi.testclient import TestClient

from app.main import app

client = TestClient(app)


def test_health_returns_expected_payload_and_request_id() -> None:
    response = client.get("/api/v1/health", headers={"X-Request-ID": "scaffold-test-001"})

    assert response.status_code == 200
    assert response.json() == {
        "status": "ok",
        "service": "ai-agent-api",
        "request_id": "scaffold-test-001",
    }
    assert response.headers["X-Request-ID"] == "scaffold-test-001"


def test_health_generates_request_id_when_header_missing() -> None:
    response = client.get("/api/v1/health")

    assert response.status_code == 200
    assert response.json()["request_id"]
    assert response.headers["X-Request-ID"] == response.json()["request_id"]
