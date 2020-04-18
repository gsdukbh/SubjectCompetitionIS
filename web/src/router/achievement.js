
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
            path: "admin",
            name: 'achievement index',
            component: () => import('../views/Achievement/index'),
            meta: {
                title: '成绩排行',
                icon: 'ranks',
                roles: ['ROLE_ADMIN', 'ROLE_TEA'],
            },
        },
        {
            path: "index",
            name: 'achievement index',
            component: () => import('../views/Achievement/index'),
            meta: {
                title: '成绩排行',
                icon: 'ranks',
                roles: ['ROLE_STU'],
            },
        },
        {
            path: "upData",
            name: 'achievement upData ',
            component: () => import('../views/Achievement/upData'),
            meta: {
                title: '上传比赛成绩',
                icon: 'upInfo',
                roles: ['ROLE_ADMIN', 'ROLE_TEA'],
            }
        }

    ]
};
export default achievementRouter;
