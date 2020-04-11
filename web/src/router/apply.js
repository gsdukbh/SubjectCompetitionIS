import Layout from "../layout"

const applyRouter={
    path:'/apply',
    name:'apply',
    component: Layout,
    meta:{
        title:'报名',
        icon:'apply'
    },
    children: [
        {
            path: 'index',
            name: 'apply index',
            component: () => import('../views/apply/index'),
            meta: {
                title: "报名详情",
                icon: 'info'
            }
        },
        {
            path: 'apply/:id(\\d+)',
            name: 'apply competition',
            component: () => import('../views/apply/apply'),
            hidden: true,
            meta: {
                title: "报名",
            }
        },
        {
            path: 'team',
            name: 'team info',
            component: () => import('../views/apply/team'),
            meta: {
                title: "团队",
                icon: 'team'
            }
        }
    ]
};
export default applyRouter;
