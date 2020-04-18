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
            path: 'index',
            name: 'works index',
            component: () => import('../views/works/index'),
            meta: {
                title: '参赛作品',
                icon: 'works'
            }
        },
        {
            path: 'up',
            name: 'up works',
            component: () => import('../views/works/up'),
            meta: {
                title: '上传作品',
                icon: 'upInfo',
                roles: ['ROLE_STU']
            }
        },
        {
            path: 'detail/:id(\\d+)',
            name: 'detail works',
            hidden: true,
            component: () => import('../views/works/components/detail'),
            meta: {
                title: '作品详情',
            }
        },
        {
            path: 'edit/:id(\\d+)',
            name: 'edit works',
            hidden: true,
            component: () => import('../views/works/components/edit'),
            meta: {
                title: '作品修改',
                roles: ['ROLE_STU']
            }
        }

    ]
};
export default worksRouter;
