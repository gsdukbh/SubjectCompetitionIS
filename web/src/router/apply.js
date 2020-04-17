import Layout from "../layout"

const applyRouter={
    path:'/apply',
    name:'apply',
    component: Layout,
    meta:{
        title: '报名',
        icon: 'apply',
    },
    children: [
        {
            path: 'admin',
            name: 'admin apply',
            component: () => import('../views/apply/adminIndex'),
            meta: {
                title: '报名详情',
                roles: ['ROLE_TEA', 'ROLE_ADMIN',],
            }
        },
        {
            path: 'apply/detail/:id(\\d+)',
            name: 'admin competition',
            component: () => import('../views/apply/admin'),
            hidden: true,
            meta: {
                title: "报名",
                roles: ['ROLE_ADMIN', 'ROLE_TEA'],
            }
        },
        {
            path: 'index',
            name: 'apply index',
            component: () => import('../views/apply/index'),
            meta: {
                title: "报名详情",
                icon: 'info',
                roles: ['ROLE_STU'],
            }
        },
        {
            path: 'apply/:id(\\d+)',
            name: 'apply competition',
            component: () => import('../views/apply/apply'),
            hidden: true,
            meta: {
                title: "报名",
                roles: ['ROLE_STU']
            }
        },
        // {
        //     path: 'team',
        //     name: 'team info',
        //     component: () => import('../views/apply/team'),
        //     meta: {
        //         title: "我的团队",
        //         icon: 'team',
        //         roles: ['ROLE_STU'],
        //     }
        // }
    ]
};
export default applyRouter;
