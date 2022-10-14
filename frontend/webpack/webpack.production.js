const CompressionPlugin = require('compression-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { DefinePlugin } = require('webpack');
const { merge } = require('webpack-merge');

const { join } = require('path');

const common = require('./webpack.common.js');

require('dotenv').config({
  path: join(__dirname, '../fe-security/.env/.env.production'),
});

module.exports = merge(common, {
  mode: 'production',
  plugins: [
    new HtmlWebpackPlugin({
      template: 'public/index.html',
      favicon: 'public/favicon.ico',
      KAKAO_MAP_KEY: process.env.KAKAO_MAP_KEY,
    }),
    new DefinePlugin({
      'process.env.BASE_URL': JSON.stringify(process.env.BASE_URL),
    }),
    new CompressionPlugin({
      algorithm: 'gzip',
    }),
  ],
});
