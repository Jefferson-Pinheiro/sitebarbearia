// URL base do backend Spring Boot. Ajuste via .env (VITE_API_BASE_URL) se precisar.
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });

  if (!response.ok) {
    throw new Error(`Erro ${response.status} ao chamar ${path}`);
  }

  if (response.status === 204) return null;
  return response.json();
}

export const api = {
  getLeads: () => request('/leads'),
  createLead: (lead) => request('/leads', { method: 'POST', body: JSON.stringify(lead) }),
  updateLeadStatus: (id, status) =>
    request(`/leads/${id}/status?status=${status}`, { method: 'PATCH' }),
  getPipelineStages: () => request('/pipeline-stages'),
};
