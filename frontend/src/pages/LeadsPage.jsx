import { useEffect, useState } from 'react'
import { api } from '../api'

const STATUS_LABEL = {
  NOVO: 'Novo',
  CONTATO_FEITO: 'Contato feito',
  NEGOCIANDO: 'Negociando',
  FECHADO: 'Fechado',
  PERDIDO: 'Perdido',
}

export default function LeadsPage() {
  const [leads, setLeads] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    api.getLeads()
      .then(setLeads)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false))
  }, [])

  return (
    <div className="main">
      <div className="page-header">
        <h1>Leads</h1>
        <p>Todos os contatos capturados via WhatsApp e outras origens.</p>
      </div>

      {error && (
        <div className="error-banner">
          Não foi possível carregar os leads. Confirme se o backend está rodando em localhost:8080.
        </div>
      )}

      {loading && !error && <p className="loading">Carregando leads…</p>}

      {!loading && !error && leads.length === 0 && (
        <div className="empty-state">Nenhum lead cadastrado ainda.</div>
      )}

      {!loading && !error && leads.length > 0 && (
        <table className="lead-table">
          <thead>
            <tr>
              <th>Nome</th>
              <th>Telefone</th>
              <th>Origem</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {leads.map((lead) => (
              <tr key={lead.id}>
                <td>{lead.name || '—'}</td>
                <td><span className="lead-phone">{lead.phoneNumber}</span></td>
                <td>{lead.source || '—'}</td>
                <td>
                  <span className={`status-pill ${lead.status?.toLowerCase()}`}>
                    {STATUS_LABEL[lead.status] || lead.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}
