'use strict'
const path = require('path')
function resolve(dir) {
    return path.join(__dirname, dir)
}
const defaultSettings = require('./src/settings.js')
const name = defaultSettings.title ||'学科竞赛系统'

module.exports = {

    configureWebpack:{
        name: name,
        resolve:{
            alias:{
                '@': resolve('src')
            }
        }
    },

    devServer:{
        port:8000,
        proxy:{
            '/api':{
                target:'http://localhost:8080',
                ws:true,
                changeOrigin: true,
                pathRewrite:{
                    '^/api':'/'
                }
            }
        }
    }
}
