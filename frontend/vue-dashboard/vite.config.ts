import { fileURLToPath, URL } from 'node:url'
import vite from 'vite'
import vue from '@vitejs/plugin-vue'
import vuetify from 'vite-plugin-vuetify'
import * as path from 'path'

// https://vitejs.dev/config/
export default vite.defineConfig(({ mode }: any) => {
  const env = vite.loadEnv(mode, process.cwd(), '')
  const VITE_ROOT_HOST = `${env.VITE_ROOT_HOST ?? 'http://localhost:8080'}`
  console.log(mode)
  return {
    base: '/vueapp2/',
    // base: mode === 'production' ? '/' : '/vueapp2/',
    plugins: [
      vue({
        template: {
          compilerOptions: {
            isCustomElement: tag => ['v-list-recognize-title'].includes(tag),
          },
        },
      }),
      vuetify({
        autoImport: true,
      }),
    ],
    define: {
      __APP_ENV__: JSON.stringify(env.APP_ENV),
    },
    resolve: {
      alias: {
        // @ts-ignore
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    css: {
      preprocessorOptions: {
        scss: {},
      },
    },
    build: {
      // outDir: path.join(__dirname, '../../src/main/resources/static/vueapp2'),
      outDir: mode === 'production' ? path.join(__dirname, '/dist') : path.join(__dirname, '../../src/main/resources/static/vueapp2'),
      emptyOutDir: true,
      chunkSizeWarningLimit: 1024 * 1024, // Set the limit to 1 MB
      rollupOptions: {
        output:
          mode === 'production'
            ? {}
            : {
                entryFileNames: 'assets/index.js',
                chunkFileNames: `assets/index-chunk.js`,
                assetFileNames: `assets/[name].[ext]`,
              },
      },
    },
    optimizeDeps: {
      exclude: ['vuetify'],
      entries: ['./src/**/*.vue'],
    },
    server: {
      // port: 5000,
      // host: '0.0.0.0',
      // strictPort: true,
      // hmr: {
      //   port: 3333,
      //   clientPort: 3333,
      //   host: 'localhost',
      //   path: '/hmr/',
      // },
      proxy: {
        '/api/extra': {
          target: VITE_ROOT_HOST,
          changeOrigin: true,
          // rewrite: (path: any) => path.replace(/^\/v1/, ''),
        },
      },
    },
  }
})
