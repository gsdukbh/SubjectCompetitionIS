import Layout from "../layout"

const myInfo = {
    path:'/me',
    name:'me',
    component:Layout,
    meta:{
        title:'我的',
        icon: 'user'
    },
    children:[
        {
            path: 'myInfo',
            name: 'myInfo',
            component: () => import('../views/user/info'),
            meta: {
                title: '我的信息',
                // icon: 'myInfo'
            },
        },

        // {
        //     path: 'my/grades',
        //     name: 'my grades',
        //     component: () => import('../views/Achievement/my'),
        //     meta: {
        //         title: '我的成绩',
        //         icon: 'ranks',
        //         roles: ['ROLE_STU']
        //     },
        // },
        // {
        //     path:'my/works',
        //     name:'my works',
        //     component:()=>import('../views/works/my'),
        //     meta: {
        //         title: "我的作品",
        //         icon: 'works',
        //         roles: ['ROLE_STU']
        //     }
        // }

    ]
};
export default myInfo;
