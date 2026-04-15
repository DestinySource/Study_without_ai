import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'
import fs from 'node:fs'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5173, // You can change this to 5137 if you prefer
    https: {
      // These paths point to the files in your parent "study-without-ai" folder
      key: fs.readFileSync(new URL('../localhost+2-key.pem', import.meta.url)),
      cert: fs.readFileSync(new URL('../localhost+2.pem', import.meta.url)),
    },
    // If your frontend needs to talk to the backend, add this proxy:
    proxy: {
      '/api': {
        target: 'https://localhost:8443',
        secure: false, // Allows the local certificate
        changeOrigin: true
      }
    }
  }
})