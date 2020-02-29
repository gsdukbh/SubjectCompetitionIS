import Vue from 'vue'
import Router from 'vue-router'
import test from "../views/test";
import HelloWorld from "../views/HelloWorld";

Vue.use(Router)
/**
 * all roles
 */

export const allRouter = [
    {
        path:'/',
        hidden:true,
        component:HelloWorld,
        name:'登录',
        meta:{
            title:"噢噢噢噢"
        }
    },
    {
        path:'/admin',
        name:'test',
        hidden:true,
        meta:{
            title:"1212"
        },
        component:test
    }
]
const createRouter = () => new Router({
    mode:'history',
    routes:allRouter
})

const router =createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}
export default router
