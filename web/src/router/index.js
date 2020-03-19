import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login'
import Layout from '../layout'
import publicLayout from '../layout/components/public'


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
    {
        path: '/404',
        name: '404',
        component: import('../views/error-page/404'),
        hidden: true,
        meta: {
            title: '404'
        }
    },

    {
        path: '/password/recover',
        component: publicLayout,
        hidden: true,
        redirect: '/password/recover/index',
        meta: {
            title: '密码重置'
        },
        children: [
            {

                path: "/password/recover/index",
                component: () => import('../views/login/pwdRe/index'),
                name: '密码重置',

            }
        ]

    },
    {
        path:'/',
        redirect: '/public',
        hidden: true,
    },

    /*公开的页面*/
    {
        path: '/public',
        component: publicLayout,
        hidden: true,
        redirect: '/public/index',
        children: [
            {
                path: '/public/index',
                component: () => import('../views/public/index')
            }
        ]
    },

]
export const asyncRoutes = [
    {
        path: '/home',
        redirect:'/home/user',
        component: Layout,
        meta: {
            title: '首页',
            icon: 'dashboard',
            // roles: ['ROLE_ADMIN'],
        },
        children: [
            {
                path: 'user',
                name: 'home',
                component: ()=>import('../views/dashboard/Home'),
                meta: {
                    title: '我的仪表盘',
                    roles: ['ROLE_STU']
                }
            },
            {
                path: 'user',
                name: 'home',
                component: ()=>import('../views/dashboard/admin'),
                meta: {
                    title: '我的仪表盘',
                    roles: ['ROLE_ADMIN']
                }
            },
            {
                path: 'user',
                name: 'home',
                component: ()=>import('../views/dashboard/teacher'),
                meta: {
                    title: '我的仪表盘',
                    roles: ['ROLE_TEA']
                }
            }
        ]
    },
    {
        path: '/competition',
        component: Layout,
        name: 'competition',
        meta: {
            title: '竞赛管理',
            roles: ['ROLE_ADMIN', 'ROLE_TEA'],
            icon: 'competition'
        },
        children: [
            {
                path: 'index',
                component: () => import('../views/competition/index'),
                name: 'PageCompetition',
                meta: {
                    title: '竞赛项目',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA'],
                }
            },
            {
                path: 'edit/:id(\\d+)',
                component: () => import('../views/competition/edit'),
                name: 'editCompetition',
                hidden: true,
                meta: {
                    title: '竞赛修改',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA']
                }
            },
            {
                path: 'detail/:id(\\d+)',
                name: 'detail',
                component: () => import('../views/competition/detail'),
                hidden: true,
                meta: {
                    title: '竞赛详情',
                }
            },
            {
                path: 'publish',
                name: 'publish',
                component: () => import('../views/competition/editCompetition'),
                meta: {
                    title: '竞赛发布',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA'],
                }
            }
        ]
    },


    {
        path: '*',
        redirect: '/404',
        hidden: true,
        meta: {
            title: '404'
        }
    }
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

