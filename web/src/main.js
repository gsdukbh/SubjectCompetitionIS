import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import getPageTitle from './utils/get-page-title'
import Element from 'element-ui'
import Cookies from 'js-cookie'

import './icons'

Vue.config.productionTip = false

router.beforeEach((to,from,next) =>{
  document.title = getPageTitle(to.meta.title)
  next()
})

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
