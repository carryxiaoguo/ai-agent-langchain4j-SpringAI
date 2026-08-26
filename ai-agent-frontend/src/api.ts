export type ConnectionState = 'checking' | 'connected' | 'disconnected'

const apiBaseUrl = import.meta.env.VITE_API_BASE_URL ?? 'http://127.0.0.1:8000/api/v1'

export async function checkBackend(signal: AbortSignal): Promise<boolean> {
  const response = await fetch(`${apiBaseUrl}/health`, {
    method: 'GET',
    headers: { Accept: 'application/json' },
    signal,
  })
  return response.ok
}
