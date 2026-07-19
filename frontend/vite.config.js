import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  // GitHub Pages serve o site em https://usuario.github.io/NOME-DO-REPO/,
  // entao o base precisa bater com o nome do repositorio.
  // Em desenvolvimento local (npm run dev) isso e ignorado.
  base: process.env.VITE_BASE_PATH || '/',
  server: {
    port: 5173
  }
})
