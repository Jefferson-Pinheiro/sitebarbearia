import { useEffect, useRef, useState } from 'react'
import { api } from '../api'

export default function InboxPage() {
  const [leads, setLeads] = useState([])
  const [selectedLead, setSelectedLead] = useState(null)
  const [messages, setMessages] = useState([])
  const [draft, setDraft] = useState('')
  const [loadingLeads, setLoadingLeads] = useState(true)
  const [loadingMessages, setLoadingMessages] = useState(false)
  const [sending, setSending] = useState(false)
  const [error, setError] = useState(null)
  const bottomRef = useRef(null)

  useEffect(() => {
    api.getLeads()
      .then((data) => {
        setLeads(data)
        if (data.length > 0) setSelectedLead(data[0])
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoadingLeads(false))
  }, [])

  useEffect(() => {
    if (!selectedLead) return
    setLoadingMessages(true)
    api.getMessages(selectedLead.id)
      .then(setMessages)
      .catch((err) => setError(err.message))
      .finally(() => setLoadingMessages(false))
  }, [selectedLead])

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])

  async function handleSend(e) {
    e.preventDefault()
    if (!draft.trim() || !selectedLead) return
    setSending(true)
    setError(null)
    try {
      const sent = await api.sendMessage(selectedLead.id, draft.trim())
      setMessages((prev) => [...prev, sent])
      setDraft('')
    } catch (err) {
      setError('Não foi possível enviar. Confirme se há uma credencial ativa do WhatsApp cadastrada.')
    } finally {
      setSending(false)
    }
  }

  return (
    <div className="main inbox-main">
      <div className="page-header">
        <h1>Conversas</h1>
        <p>Histórico de mensagens trocadas com cada lead pelo WhatsApp.</p>
      </div>

      <div className="inbox-layout">
        <aside className="inbox-lead-list">
          {loadingLeads && <p className="loading">Carregando…</p>}
          {!loadingLeads && leads.length === 0 && (
            <div className="empty-state">Nenhum lead cadastrado ainda.</div>
          )}
          {leads.map((lead) => (
            <button
              key={lead.id}
              className={`inbox-lead-item ${selectedLead?.id === lead.id ? 'active' : ''}`}
              onClick={() => setSelectedLead(lead)}
            >
              <span className="inbox-lead-name">{lead.name || lead.phoneNumber}</span>
              <span className="inbox-lead-phone">{lead.phoneNumber}</span>
            </button>
          ))}
        </aside>

        <section className="inbox-thread">
          {!selectedLead && (
            <div className="empty-state">Selecione um lead para ver a conversa.</div>
          )}

          {selectedLead && (
            <>
              <div className="inbox-thread-header">
                <strong>{selectedLead.name || 'Sem nome'}</strong>
                <span className="lead-phone">{selectedLead.phoneNumber}</span>
              </div>

              <div className="inbox-messages">
                {loadingMessages && <p className="loading">Carregando mensagens…</p>}
                {!loadingMessages && messages.length === 0 && (
                  <div className="empty-state">Nenhuma mensagem trocada ainda.</div>
                )}
                {messages.map((msg) => (
                  <div
                    key={msg.id}
                    className={`chat-bubble ${msg.direction === 'OUTBOUND' ? 'outbound' : 'inbound'}`}
                  >
                    {msg.content}
                  </div>
                ))}
                <div ref={bottomRef} />
              </div>

              {error && <div className="error-banner">{error}</div>}

              <form className="inbox-composer" onSubmit={handleSend}>
                <input
                  type="text"
                  placeholder="Escreva uma mensagem…"
                  value={draft}
                  onChange={(e) => setDraft(e.target.value)}
                  disabled={sending}
                />
                <button type="submit" disabled={sending || !draft.trim()}>
                  {sending ? 'Enviando…' : 'Enviar'}
                </button>
              </form>
            </>
          )}
        </section>
      </div>
    </div>
  )
}
