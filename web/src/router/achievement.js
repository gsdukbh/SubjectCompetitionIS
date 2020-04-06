
import Layout from "../layout"

const achievementRouter={
    path:'/achievement',
    name:'achievement',
    component: Layout,
    meta:{
        title:'竞赛成绩',
        icon:'achievement'
    },
    children: [
        {
            path:"index",
            name:'achievement index',
            component: ()=>import('../views/Achievement/index'),
            meta:{
                title:'成绩排行',
                icon:'ranks'
            },
        },
        {
            path: "upData",
            name:'achievement upData ',
            component:()=>import('../views/Achievement/upData'),
            meta: {
                title: '上传比赛成绩',
                icon: 'upInfo',
            }
        }

    ]
};
export default achievementRouter;
