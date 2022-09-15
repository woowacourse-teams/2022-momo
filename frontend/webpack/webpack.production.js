const { DefinePlugin } = require('webpack');
const { merge } = require('webpack-merge');

const { join } = require('path');

const common = require('./webpack.common.js');

require('dotenv').config({
  path: join(__dirname, '../fe-security/.env/.env.production'),
});

module.exports = merge(common, {
  mode: 'production',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: {
          loader: 'esbuild-loader',
          options: {
            loader: 'tsx',
            target: 'es2015',
          },
        },
      },
    ],
  },
  plugins: [
    new DefinePlugin({
      'process.env.BASE_URL': JSON.stringify(process.env.BASE_URL),
    }),
  ],
});
