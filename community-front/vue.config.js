const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,  
  lintOnSave: false,
  devServer: {
    port: 7070,
    proxy: {
      // 配置代理解决跨域,api替换target，pathRewrite将api换成空串
      '/api' : {
        target: 'http://localhost:8080',
        pathRewrite: {
          '^/api' : ''
        }
      }
    }
  }
})
