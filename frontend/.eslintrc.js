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
        pathGroups: [
          {
            pattern: 'react',
            group: 'external',
            position: 'before',
          },
        ],
        pathGroupsExcludedImportTypes: ['react'],
        alphabetize: {
          order: 'asc',
          caseInsensitive: true,
        },
        'newlines-between': 'always',
      },
    ],
  },
};
