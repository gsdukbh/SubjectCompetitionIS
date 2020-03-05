import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
// import About from '../views/About'
import Login from '../views/login'
import Layout from '../layout'

Vue.use(VueRouter)

export const publicRoutes = [
  {
    path: '/',
    name: 'Dashboard',
    component: Home,
    meta:{
      title:'首页',
      icon:'dashboard'
    }
  },
  {
    path:'/login',
    name:'login',
    component:Login,
    meta:{
      title:'登录'
    }
  },
  // {
  //   path:'/home',
  //   name:'ho',
  //   component:Layout,
  //   meta:{
  //     title:'管理员',
  //     roles:['ROLE_ADMIN']
  //   },
  //   children:[
  //     {
  //       path:'/admin',
  //       name:'s',
  //       component:About,
  //       meta:{
  //         title:'ces',
  //       }
  //     },
  //     {
  //       path:'/user',
  //       name:'ss',
  //       component:Home,
  //       meta:{
  //         title:'111',
  //       }
  //     }
  //   ]
  // },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },



]
export const asyncRoutes =[
  {
    path: '/home',
    name: 'sccc',
    component: Layout,
    meta: {
      title: '管理员',
      icon: 'dashboard',
      roles: ['ROLE_ADMIN']
    },
    children: [
      {
        path: '/user',
        name: 'scc',
        component: Home,
        meta: {
          title: 's',
        }
      }
    ]

  },
    //在最后家，否则，无限递归
  { path: '*', redirect: '/404', hidden: true }
]
const createRouter =() => new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({ y: 0 }),
  routes:publicRoutes
})

const  router =createRouter()

export function resetRouter() {
  const newRouter=createRouter()
  router.matcher = newRouter.matcher
}
export default router

