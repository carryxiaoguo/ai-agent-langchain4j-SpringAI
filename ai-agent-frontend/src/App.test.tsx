import { cleanup, render, screen } from '@testing-library/react'
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'

import App from './App'

describe('App', () => {
  afterEach(() => {
    cleanup()
  })

  beforeEach(() => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue({ ok: true }))
  })

  it('renders the workspace and connected state', async () => {
    render(<App />)

    expect(screen.getByRole('heading', { name: '开发工作台' })).toBeInTheDocument()
    expect(await screen.findByRole('status')).toHaveTextContent('服务已连接')
  })

  it('shows a retryable disconnected state when the backend is unavailable', async () => {
    vi.stubGlobal('fetch', vi.fn().mockRejectedValue(new Error('backend unavailable')))

    render(<App />)

    expect(await screen.findByRole('status')).toHaveTextContent('服务未连接')
    expect(screen.getByRole('button', { name: '重新检查' })).toBeEnabled()
  })
})
