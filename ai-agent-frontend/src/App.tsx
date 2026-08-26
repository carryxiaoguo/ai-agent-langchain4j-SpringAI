import { useCallback, useEffect, useState } from 'react'

import { checkBackend, type ConnectionState } from './api'

function App() {
  const [connection, setConnection] = useState<ConnectionState>('checking')

  const refreshConnection = useCallback(async () => {
    setConnection('checking')
    const controller = new AbortController()
    const timeout = window.setTimeout(() => controller.abort(), 3000)
    try {
      setConnection((await checkBackend(controller.signal)) ? 'connected' : 'disconnected')
    } catch {
      setConnection('disconnected')
    } finally {
      window.clearTimeout(timeout)
    }
  }, [])

  useEffect(() => {
    void refreshConnection()
  }, [refreshConnection])

  const statusCopy = {
    checking: '正在检查服务',
    connected: '服务已连接',
    disconnected: '服务未连接',
  }[connection]

  return (
    <main className="shell">
      <aside className="rail" aria-label="产品导航">
        <div className="brand-lockup">
          <span className="brand-glyph" aria-hidden="true">A</span>
          <div>
            <strong>AI Agent</strong>
            <span>开发工作台</span>
          </div>
        </div>
        <nav className="nav-list" aria-label="主导航">
          <a className="nav-item active" href="#overview">工作台</a>
        </nav>
        <div className="rail-foot">初始化阶段 · 0.1.0</div>
      </aside>

      <section className="workspace" id="overview">
        <header className="topbar">
          <div>
            <p className="eyebrow">AI AGENT</p>
            <h1>开发工作台</h1>
          </div>
          <div className={`status-chip ${connection}`} role="status" aria-live="polite">
            <span className="status-dot" aria-hidden="true" />
            {statusCopy}
          </div>
        </header>

        <section className="status-section" aria-labelledby="status-title">
          <div className="section-heading">
            <div>
              <p className="section-kicker">本地开发</p>
              <h2 id="status-title">服务状态</h2>
            </div>
            <p className="section-note">后端健康接口</p>
          </div>
          <div className={`status-panel ${connection}`}>
            <div>
              <span className="status-label">API</span>
              <strong>{statusCopy}</strong>
              <p>连接地址由环境变量提供，检查请求最多等待 3 秒。</p>
            </div>
            <button className="outline-button" type="button" onClick={() => void refreshConnection()} disabled={connection === 'checking'}>
              {connection === 'checking' ? '检查中…' : '重新检查'}
            </button>
          </div>
        </section>

        <footer className="footer-line">
          <span>Python 3.11 · React 19 · FastAPI</span>
          <span>本地初始化脚手架</span>
        </footer>
      </section>
    </main>
  )
}

export default App
