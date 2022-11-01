const path = require('path');

module.exports = {
  stories: ['../src/**/*.stories.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: [
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    '@storybook/addon-interactions',
  ],
  framework: '@storybook/react',
  core: {
    builder: 'webpack5',
  },
  staticDirs: ['../public'],
  env: config => ({
    ...config,
    BASE_URL: '',
  }),
  webpackFinal: config => {
    config.module.rules = config.module.rules.map(rule => {
      if (!rule.test.test('.svg')) {
        return rule;
      }

      const newRule = {
        ...rule,
        test: /\.(ico|jpg|jpeg|png|gif|eot|otf|webp|ttf|woff|woff2)(\?.*)?$/,
      };

      return newRule;
    });

    config.module.rules.push({
      test: /\.svg$/,
      use: ['@svgr/webpack', 'file-loader'],
    });

    config.resolve.alias = {
      ...config.resolve.alias,
      apis: path.resolve(__dirname, '../src/apis'),
      assets: path.resolve(__dirname, '../src/assets'),
      components: path.resolve(__dirname, '../src/components'),
      pages: path.resolve(__dirname, '../src/pages'),
      layouts: path.resolve(__dirname, '../src/layouts'),
      hooks: path.resolve(__dirname, '../src/hooks'),
      styles: path.resolve(__dirname, '../src/styles'),
      types: path.resolve(__dirname, '../src/types'),
      store: path.resolve(__dirname, '../src/store'),
      utils: path.resolve(__dirname, '../src/utils'),
      constants: path.resolve(__dirname, '../src/constants'),
      mocks: path.resolve(__dirname, '../src/mocks'),
    };

    return config;
  },
};
