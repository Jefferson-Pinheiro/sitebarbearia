import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Sidebar from './components/Sidebar'
import LeadsPage from './pages/LeadsPage'
import PipelinePage from './pages/PipelinePage'

export default function App() {
  return (
    <BrowserRouter basename={import.meta.env.BASE_URL}>
      <Sidebar />
      <Routes>
        <Route path="/" element={<LeadsPage />} />
        <Route path="/funil" element={<PipelinePage />} />
      </Routes>
    </BrowserRouter>
  )
}
