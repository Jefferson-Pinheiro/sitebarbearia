import { useEffect, useState } from 'react'
import { api } from '../api'
import LeadCard from '../components/LeadCard'

// Ordem fixa baseada no enum LeadStatus do backend.
// Observação: a entidade PipelineStage (cadastrável) ainda não está conectada
// ao status do lead — por enquanto o board usa o enum diretamente.
const STAGES = [
  { key: 'NOVO', label: 'Novo' },
  { key: 'CONTATO_FEITO', label: 'Contato feito' },
  { key: 'NEGOCIANDO', label: 'Negociando' },
  { key: 'FECHADO', label: 'Fechado' },
  { key: 'PERDIDO', label: 'Perdido' },
]

export default function PipelinePage() {
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
        <h1>Funil de vendas</h1>
        <p>Leads agrupados por etapa do funil.</p>
      </div>

      {error && (
        <div className="error-banner">
          Não foi possível carregar o funil. Confirme se o backend está rodando em localhost:8080.
        </div>
      )}

      {loading && !error && <p className="loading">Carregando funil…</p>}

      {!loading && !error && (
        <div className="board">
          {STAGES.map((stage) => {
            const stageLeads = leads.filter((lead) => lead.status === stage.key)
            return (
              <div className="board-column" key={stage.key}>
                <div className="board-column-header">
                  <h3>{stage.label}</h3>
                  <span className="board-count">{stageLeads.length}</span>
                </div>
                <div className="board-cards">
                  {stageLeads.map((lead) => (
                    <LeadCard key={lead.id} lead={lead} />
                  ))}
                  {stageLeads.length === 0 && (
                    <div className="empty-state">Vazio</div>
                  )}
                </div>
              </div>
            )
          })}
        </div>
      )}
    </div>
  )
}
