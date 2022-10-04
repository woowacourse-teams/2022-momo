module.exports = {
  parser: '@typescript-eslint/parser',
  plugins: ['prettier', '@typescript-eslint'],
  extends: [
    'react-app',
    'plugin:prettier/recommended',
    'plugin:@typescript-eslint/eslint-recommended',
  ],
  rules: {
    'react/function-component-definition': [
      'error',
      {
        namedComponents: 'function-declaration',
      },
    ],
    'import/order': [
      'error',
      {
        groups: ['external', 'builtin', 'internal', ['parent', 'sibling']],
        pathGroupsExcludedImportTypes: ['react'],
        pathGroups: [
          {
            pattern: 'react',
            group: 'external',
            position: 'before',
          },
          { pattern: 'apis/**', group: 'internal' },
          { pattern: 'assets/**', group: 'internal' },
          { pattern: 'components/**', group: 'internal' },
          { pattern: 'pages/**', group: 'internal' },
          { pattern: 'layouts/**', group: 'internal' },
          { pattern: 'hooks/**', group: 'internal' },
          { pattern: 'styles/**', group: 'internal' },
          { pattern: 'types/**', group: 'internal' },
          { pattern: 'store/**', group: 'internal' },
          { pattern: 'utils/**', group: 'internal' },
          { pattern: 'constants/**', group: 'internal' },
          { pattern: 'mocks/**', group: 'internal' },
        ],
        alphabetize: {
          order: 'asc',
          caseInsensitive: true,
        },
        'newlines-between': 'always',
      },
    ],
  },
};
