import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import getPageTitle from "./utils/getPageTitle";

// import './permission'




Vue.config.productionTip = false


router.beforeEach((to,from,next)=>{
  document.title = getPageTitle(to.meta.title)
  next();
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
