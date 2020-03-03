'use strict'
const path = require('path')
const defaultSettings = require('./src/settings.js')
function resolve(dir) {
    return path.join(__dirname, dir)
}
const name = defaultSettings.title
module.exports = {
    publicPath:'/',
    devServer:{
        port:8002,
        proxy:{
            '/api':{
                target:'http://localhost:8080',
                changeOrigin: true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    },
    configureWebpack:{
        name:name,
        resolve: {
            alias: {
                '@': resolve('src')
            }
        }
    },
    chainWebpack(config){
        // set svg-sprite-loader
        config.module
            .rule('svg')
            .exclude.add(resolve('src/icons'))
            .end()
        config.module
            .rule('icons')
            .test(/\.svg$/)
            .include.add(resolve('src/icons'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            })
            .end()

    }
}
