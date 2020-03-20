import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'

import Element from 'element-ui'
import Cookies from 'js-cookie'
import './icons'
import '@/styles/index.scss'

import './permission'


Vue.config.productionTip = false;



Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
