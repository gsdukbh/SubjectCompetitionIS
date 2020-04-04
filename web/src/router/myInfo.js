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
            name:'myInfo',
            component: ()=>import('../views/user/info'),
            meta:{
                title:'我的信息',
                icon: 'myInfo'
            },
        },
        {
            path:'my/join',
            name:'studentCompetition',
            component: ()=>import('../views/competition/studentCompetition'),
            meta:{
                title:'我参加的比赛',
                icon: 'record'
            },
        }

    ]
};
export default myInfo;
