import Layout from "../layout"

const userRouter = {
    path: '/user',
    component: Layout,
    name: 'user',
    meta: {
        title: '用户管理',
        icon: 'userAdmin',
        roles: ['ROLE_ADMIN']
    },
    children: [
        {
            path: 'add',
            name:'add user',
            component: () => import('../views/user/add'),
            meta: {
                title: '添加用户',
                icon: 'add',
            },
        },
        {
            path: 'admin',
            name: 'adminUser',
            component: () => import('../views/user/admin'),
            meta: {
                title: '学生信息',
                icon: 'list',
            },
        },
        {
            path: 'tea',
            name: 'teacher info',
            component: () => import('../views/user/tea'),
            meta: {
                title: '教师信息',
                icon: 'list',

            },
        },
        {
            path: 'upUserInfo',
            name: 'up user info',
            component: () => import('../views/user/upUserInfo'),
            meta: {
                title: '上传用户信息',
                icon: 'upInfo',

            },
        },

    ]

};
export default userRouter;
