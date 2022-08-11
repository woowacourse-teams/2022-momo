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
    new DefinePlugin({
      'process.env.BASE_URL': JSON.stringify(process.env.BASE_URL),
    }),
  ],
});
