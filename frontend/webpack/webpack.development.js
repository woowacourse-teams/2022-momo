const HtmlWebpackPlugin = require('html-webpack-plugin');
const { DefinePlugin } = require('webpack');
const { merge } = require('webpack-merge');

const path = require('path');

const common = require('./webpack.common.js');

require('dotenv').config({
  path: path.join(__dirname, '../fe-security/.env/.env.development'),
});

module.exports = merge(common, {
  mode: 'development',
  devtool: 'source-map',
  devServer: {
    static: path.resolve(__dirname, '../public'),
    port: 3000,
    historyApiFallback: true,
    client: {
      logging: 'none',
    },
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: 'public/index.html',
      favicon: 'public/favicon.ico',
      KAKAO_MAP_KEY: process.env.KAKAO_MAP_KEY,
    }),
    new DefinePlugin({
      'process.env.BASE_URL': JSON.stringify(process.env.BASE_URL),
    }),
  ],
});
