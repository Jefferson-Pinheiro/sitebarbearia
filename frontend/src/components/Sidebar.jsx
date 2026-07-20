import { NavLink } from 'react-router-dom'

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-brand">
        CRM
        <span>WHATSAPP BUSINESS API</span>
      </div>

      <nav className="sidebar-nav">
        <NavLink to="/" end className={({ isActive }) => `sidebar-link ${isActive ? 'active' : ''}`}>
          <span className="sidebar-dot" />
          Leads
        </NavLink>
        <NavLink to="/conversas" className={({ isActive }) => `sidebar-link ${isActive ? 'active' : ''}`}>
          <span className="sidebar-dot" />
          Conversas
        </NavLink>
        <NavLink to="/funil" className={({ isActive }) => `sidebar-link ${isActive ? 'active' : ''}`}>
          <span className="sidebar-dot" />
          Funil de vendas
        </NavLink>
      </nav>
    </aside>
  )
}
