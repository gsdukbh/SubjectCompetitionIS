import Layout from "../layout"

const userRouter={
    path:'/user',
    component:Layout,
    name:'user',
    meta:{
        title:'用户管理',
        icon: 'userAdmin',
        roles: ['ROLE_ADMIN']
    },
    children:[
        {
            path: 'admin',
            name: 'adminUser',
            component: ()=>import('../views/user/admin'),
            meta:{
                title:'学生信息',
                icon: 'list',
                roles: ['ROLE_ADMIN']
            },
        },
        {
            path:'tea',
            name:'teacher info',
            component:()=>import('../views/user/tea'),
            meta:{
                title:'教师信息',
                icon: 'list',
                roles: ['ROLE_ADMIN']
            },
        },
        {
            path:'upUserInfo',
            name:'up user info',
            component:()=>import('../views/user/upUserInfo'),
            meta:{
                title:'上传用户信息',
                icon: 'upInfo',
                roles: ['ROLE_ADMIN']
            },
        },

    ]

};
export default userRouter;
