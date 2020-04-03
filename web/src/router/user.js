import Layout from "../layout"

const userRouter={
    path:'/user',
    component:Layout,
    name:'user',
    meta:{
        title:'用户',
        icon: 'user'
    },
    children:[
        {
            path: 'index',
            name:'myInfo',
            component: ()=>import('../views/user/info'),
            meta:{
                title:'我的信息',
                icon: 'myInfo'
            },
        },
        {
            path: 'admin',
            name: 'adminUser',
            component: ()=>import('../views/user/admin'),
            meta:{
                title:'用户管理',
                icon: 'users',
                roles: ['ROLE_ADMIN', 'ROLE_TEA']
            },
        },
        {
            path:'upUserInfo',
            name:'up user info',
            component:()=>import('../views/user/upUserInfo'),
            meta:{
                title:'上传用户信息',
                icon: 'upInfo',
                roles: ['ROLE_ADMIN', 'ROLE_TEA']
            },
        },

    ]

};
export default userRouter;
