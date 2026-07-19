export default function LeadCard({ lead }) {
  return (
    <div className="lead-card">
      <div className="lead-card-name">{lead.name || 'Sem nome'}</div>
      <div className="lead-card-phone">{lead.phoneNumber}</div>
    </div>
  )
}
