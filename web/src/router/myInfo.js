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
        },
        {
            path:'my/grades',
            name:'my grades',
            component:()=>import('../views/Achievement/my'),
            meta:{
                title:'我的成绩',
                icon:'ranks'
            },
        },
        {
            path:'my/works',
            name:'my works',
            component:()=>import('../views/works/my'),
            meta: {
                title: "我的作品",
                icon:'works'
            }
        }

    ]
};
export default myInfo;
