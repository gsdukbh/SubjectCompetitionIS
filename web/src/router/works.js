import Layout from "../layout"

const worksRouter = {
    path: '/works',
    name: 'works',
    component: Layout,
    meta:{
        title:'作品',
        icon:'works'
    },
    children: [
        {
            path:'index',
            name:'works index',
            component:()=>import('../views/works/index'),
            meta:{
                title:'参赛作品'
            }
        },

    ]
};
export default worksRouter;
