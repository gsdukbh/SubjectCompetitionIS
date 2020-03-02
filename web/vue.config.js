'use strict'
const path = require('path')
const defaultSettings = require('./src/settings.js')

function resolve(dir) {
    return path.join(__dirname, dir)
}

const name = defaultSettings.title || '学科竞赛系统'
module.exports = {
    publicPath: '/',
    devServer: {
        port: 8000,
        open: false,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/'
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
    }
}
