import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/login'
import Layout from '../layout'
import publicLayout from '../layout/components/public'
import userRouter from "./user";
import myInfo from "./myInfo";
import worksRouter from "./works";
import applyRouter from "./apply";
import achievementRouter from "./achievement";


Vue.use(VueRouter);

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
        redirect: '/404/index',
        hidden: true,
        meta: {
            title: '404'
        },
        children: [
            {
                path: 'index',
                component: () => import('../views/error-page/404'),
                meta: {
                    title: '404'
                },
            }
        ]
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
                name: 'recover',
                meta: {
                    title: '密码重置'
                }
            }
        ]

    },
    {
        path: '/',
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
                path: 'index',
                component: () => import('../views/public/index')
            },
            {
                path: 'register',
                name: 'register',
                component: () => import('../views/register'),
                meta: {
                    title: '注册',
                }
            },
            {
                path: 'announcement',
                component: () => import('../views/public/components/announcement'),
                name: 'announcement public',
                mata: {
                    title:'公告',
                }
            },
            {
                path: 'announcement/detail/:id(\\d+)',
                component: () => import('../views/public/components/announcementDetail'),
                name: 'announcement public',
                mata: {
                    title:'详情',
                }
            },
            {
                path: 'competition',
                name: 'competition public',
                component: () => import('../views/public/components/competition'),
                mata: {
                    title:'竞赛',
                }
            },
            {
                path: 'competition/detail/:id(\\d+)',
                name: 'competition detail',
                component: () => import('../views/public/components/competitionDetail'),
                mata: {
                    title:'详情',
                }
            }

        ]
    },

];
export const asyncRoutes = [
    {
        path: '/home',
        redirect: '/home/index',
        component: Layout,
        meta: {
            icon: 'dashboard',

        },
        children: [
            {
                path: 'index',
                name: 'home',
                component: () => import('../views/dashboard/Home'),
                meta: {
                    title: '首页',
                    affix: true
                }
            },
        ]
    },
    {
        path: '/competition',
        component: Layout,
        name: 'competition',
        meta: {
            title: '竞赛',
            icon: 'competition'
        },
        children: [
            {
                path: 'user',
                name: 'studentCompetition',
                component: () => import('../views/competition/studentCompetition'),
                meta: {
                    title: '学科竞赛',
                    roles: ['ROLE_STU'],

                }
            },
            // {
            //     path: 'my/join',
            //     name: 'studentCompetition',
            //     component: () => import('../views/competition/myjoin'),
            //     meta: {
            //         title: '我参加的比赛',
            //         icon: 'record',
            //         roles: ['ROLE_STU']
            //     },
            // },
            {
                path: 'index',
                component: () => import('../views/competition/index'),
                name: 'PageCompetition',
                meta: {
                    title: '竞赛项目',
                    icon: 'project',
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
                name: 'detail ss',
                component: () => import('../views/competition/detail'),
                hidden: true,
                meta: {
                    title: '竞赛详情',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA']
                }
            },
            {
                path: 'detailStu/:id(\\d+)',
                name: 'detail ',
                component: () => import('../views/competition/detailStu'),
                hidden: true,
                meta: {
                    title: '竞赛详情',
                    roles: ['ROLE_STU']
                }
            },
            {
                path: 'publish',
                name: 'publish',
                component: () => import('../views/competition/editCompetition'),
                meta: {
                    title: '竞赛发布',
                    icon: 'publish',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA'],
                }
            },
            {
                path: 'myResponsible',
                name: 'my-responsible',
                component: () => import('../views/competition/myResponsible'),
                meta: {
                    title: '我负责的竞赛',
                    icon: 'myre',
                    roles: ['ROLE_TEA'],
                }
            }
        ]
    },
    {
        path: '/announcement',
        name: 'announcement',
        component: Layout,
        meta: {
            title: '公告',
            icon: 'announcement'
        },
        children: [
            {
                path: 'index',
                name: 'stu index',
                component: () => import('../views/Announcement/studentViwer'),
                meta: {
                    roles: ['ROLE_STU'],
                    title: '公告',
                }
            },
            {
                path: 'detailStu/:id(\\d+)',
                name: 'detailStu index',
                component: () => import('../views/Announcement/detailStu'),
                hidden: true,
                meta: {
                    roles: ['ROLE_STU'],
                    title: '公告详情',
                }
            },
            {
                path: 'publish',
                name: 'announcementPublish',
                component: () => import('../views/Announcement/publish'),
                meta: {
                    title: '发布公告',
                    icon: 'publish',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA']
                }
            },
            {
                path: 'list',
                name: 'announcementList',
                component: () => import('../views/Announcement/list'),
                meta: {
                    title: '公告信息',
                    icon: 'info',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA']
                }
            },
            {
                path: 'detail/:id(\\d+)',
                name: 'announcementDetail',
                hidden: true,
                component: () => import('../views/Announcement/detail'),
                meta: {
                    title: '公告详情',
                }
            },
            {
                path: 'edit/:id(\\d+)',
                name: 'announcementEdit',
                hidden: true,
                component: () => import('../views/Announcement/edit'),
                meta: {
                    title: '公告信息修改',
                    roles: ['ROLE_ADMIN', 'ROLE_TEA']
                }
            },

        ]
    },
    worksRouter,
    applyRouter,
    achievementRouter,
    userRouter,
    myInfo,
    {
        path: '/*',
        redirect: '/404',
        hidden: true,
        meta: {
            title: '404'
        }
    }
];

const createRouter = () => new VueRouter({
    // mode: 'history',
    base: process.env.BASE_URL,
    scrollBehavior: () => ({y: 0}),
    routes: publicRoutes
})

const router = createRouter();

export function resetRouter() {
    const newRouter = createRouter();
    router.matcher = newRouter.matcher
}

export default router

