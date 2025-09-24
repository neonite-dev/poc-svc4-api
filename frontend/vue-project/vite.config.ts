import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vueDevTools from 'vite-plugin-vue-devtools'

import path from 'path'
// import {glob, globSync} from 'glob'

// https://vitejs.dev/config/
export default defineConfig({
  base: '/vueapp/',
  plugins: [
    vue(),
    vueJsx(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: path.join(__dirname, '../../src/main/resources/static/vueapp'),
    emptyOutDir: true,
    watch: {
      include: 'src/**',
      // exclude: 'node_modules/**, .git/**, .vscode/**'
    },
    rollupOptions: {
      output: {
        entryFileNames: 'assets/index.js',
        chunkFileNames: `assets/index-chunk.js`,
        assetFileNames: `assets/[name].[ext]`
      }
      // input: globSync(path.resolve(__dirname, '', '*.html'))//glob.sync(path.resolve(__dirname, 'src', '**/*.html'))
    },

  },
  server: {
    port: 8081,
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
})
