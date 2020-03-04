import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import About from '../views/About'
import Login from '../views/login'
import Layout from '../layout'

Vue.use(VueRouter)

export const publicRoutes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta:{
      title:'home'
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
  {
    path:'/home',
    name:'ho',
    component:Layout,
    meta:{
      title:'管理员',
      roles:['ROLE_ADMIN']
    },
    children:[
      {
        path:'/admin',
        name:'s',
        component:About,
        meta:{
          title:'管理员',
          roles:['ROLE_ADMIN']
        }
      },
      {
        path:'/user',
        name:'login',
        component:Home,
        meta:{
          title:'管理员',
        }
      }
    ]
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
export const asyncRoutes =[
  {
    path:'/admin',
    name:'s',
    component:About,
    meta:{
      title:'管理员',
      roles:['ROLE_ADMIN']
    }
  },
  {
    path:'/user',
    name:'login',
    component:Home,
    meta:{
      title:'管理员',
    }
  },
  {
    path:'/home',
    name:'ho',
    component:About,
    meta:{
      title:'管理员',
      roles:['ROLE_ADMIN']
    }
  }
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

