/* eslint-env node */
// eslint-disable-next-line @typescript-eslint/no-require-imports
require('@rushstack/eslint-patch/modern-module-resolution')

module.exports = {
  root: true,
  extends: ['plugin:vue/vue3-essential', 'eslint:recommended', '@vue/eslint-config-typescript/recommended', '@vue/eslint-config-prettier'],
  env: {
    browser: true,
    node: true,
    'vue/setup-compiler-macros': true,
  },
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
  },
  rules: {
    'comma-dangle': 'off',
    'prettier/prettier': ['error', { endOfLine: 'auto' }],
    'javascript.validate.enable': 0,
    // vue
    'vue/multi-word-component-names': 'off',
    'vue/valid-v-slot': 'off',
    'vue/no-side-effects-in-computed-properties': 'off',
    // typescript
    '@typescript-eslint/no-unused-vars': 'off',
    '@typescript-eslint/comma-dangle': 'off',
    '@typescript-eslint/no-explicit-any': 'off',
    '@typescript-eslint/ban-ts-comment': ['error', { 'ts-expect-error': false, 'ts-ignore': false }],
  },
  ignorePatterns: ['dist/*', 'node_modules/*'],
}
