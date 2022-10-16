const CopyWebpackPlugin = require('copy-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

const path = require('path');

module.exports = {
  entry: path.resolve(__dirname, '../src/index.tsx'),
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
      {
        test: /\.(woff(2)?|ttf|png|jpe?g|gif|webp)$/i,
        type: 'asset/resource',
      },
      {
        test: /\.svg$/i,
        issuer: /\.[jt]sx?$/,
        use: ['@svgr/webpack', 'file-loader'],
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
    alias: {
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
    },
  },
  output: {
    path: path.resolve(__dirname, '../dist'),
    publicPath: '/',
    filename: '[contenthash].bundle.js',
    clean: true,
  },
  plugins: [
    new ForkTsCheckerWebpackPlugin(),
    new CopyWebpackPlugin({
      patterns: [
        {
          from: 'src/assets/font',
          to: 'font',
        },
      ],
    }),
  ],
};
