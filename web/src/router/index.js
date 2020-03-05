import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
// import About from '../views/About'
import Login from '../views/login'
import Layout from '../layout'

Vue.use(VueRouter)

export const publicRoutes = [
    {
        path: '/login',
        name: 'login',
        component: Login,
        hidden: true,
        meta: {
            title: '登录'
        }
    },
    // {
    //     path: '/about',
    //     name: 'About',
    //     // route level code-splitting
    //     // this generates a separate chunk (about.[hash].js) for this route
    //     // which is lazy-loaded when the route is visited.
    //     component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    // },


]
export const asyncRoutes = [
    {
        path: '/home',
        name: 'sccc',
        component: Layout,
        meta: {
            title: '管理员',
            icon: 'dashboard',
            roles: ['ROLE_ADMIN'],
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
    {
        path: '/121',
        name: '121',
        component: Layout,
        // hidden:true,
        meta: {
            title: '登录ssss',
            roles: ['ROLE_ADMIN'],

        }
    },


    {path: '*', redirect: '/404', hidden: true}
]
const createRouter = () => new VueRouter({
    // mode: 'history',
    base: process.env.BASE_URL,
    scrollBehavior: () => ({y: 0}),
    routes: publicRoutes
})

const router = createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}

export default router

