const HTMLWebpackPlugin = require('html-webpack-plugin');
const { join } = require('path');
const { VueLoaderPlugin } = require('vue-loader');

module.exports = {
    mode: 'development',
    entry: join(__dirname, 'src/main.js'),
    output: {
        path: join(__dirname, 'build'),
        filename: 'web-app.bundled.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                loader: 'babel-loader',
                options: {
                    presets: ['@babel/preset-env']
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader',
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: [
                    'file-loader'
                ],
            },
        ]
    },    
    plugins: [
        new VueLoaderPlugin(),
        new HTMLWebpackPlugin({
            showErrors: true,
            cache: true,
            title: 'Vue SPA Webpack',
            //favicon: join(__dirname, 'src/assets', 'logo.png'),
            template: join(__dirname, 'index.html')
        })
    ]    
};