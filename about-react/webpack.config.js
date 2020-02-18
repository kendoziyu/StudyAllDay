const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')

const htmlPlugin = new HtmlWebpackPlugin({
    template:  path.join(__dirname, './src/index.html'),// 源文件
    filename: 'index.html'// 生成的内存中的首页的名称
})

// 向外暴露一个打包的配置对象
// Webpack 是基于 Node 构建的；所以 webpack 支持所有 Node API 和语法
module.exports = {
    mode: 'development', // development production
    // 在 webpack 4.x 中, 有一个很大的特性，就是约定大于配置
    // 约定，默认的打包入口路径是 src\index.js
    plugins: [
        htmlPlugin
    ],
    module: { // 所有第三方 模块的配置规则
        rules: [ // 第三方匹配规则
           { test: /\.js|jsx$/, use: 'babel-loader', exclude: /node_modules/ },
           { 
               test: /\.css$/,
                use: [
                    {loader: 'style-loader'}, 
                    {loader: 'css-loader',
                    options:{
                        modules: {
                            localIdentName: '[path][name]-[local]'
                        }
                    }
                }]
            },
            {test : /\.ttf|woff|woff2|eot|svg$/, use : 'url-loader'}
        ]    
    },
    resolve: {
        extensions: ['.js', '.jsx', '.json'],
        alias: {
            '@': path.join(__dirname, './src') // 这样，@ 就表示项目根目录中的 src 这一层目录
        }
    }    
}