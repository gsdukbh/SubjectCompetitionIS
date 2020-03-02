import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About'

Vue.use(VueRouter)

export  const routes = [
  {
    path: '/',
    name: 'wode',
    component: Home,
    meta:{
      title:'sss'
    }
  },
  {
    path: '/s',
    name: 'ssss',
    component: About,
    meta:{
      title:'sss'
    }
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  base: process.env.BASE_URL,
  routes:routes
})

export function resetRouter() {
  const newRouter = router()
  router.matcher = newRouter.matcher // reset router
}
export default router

