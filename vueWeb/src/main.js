import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Element from 'element-ui'
import Cookies from 'js-cookie'

import VueWechatTitle from 'vue-wechat-title'

Vue.config.productionTip = false

Vue.use(Element)

Vue.use(Cookies)

Vue.use(VueWechatTitle)

new Vue({
  el:'#app',
  router,
  render: h => h(App)
})
