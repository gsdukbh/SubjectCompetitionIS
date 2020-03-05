import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from './utils/auth'
import getPageTitle from './utils/get-page-title'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login','/']

router.beforeEach(async(to, from, next) => {

  NProgress.start()

  document.title = getPageTitle(to.meta.title)

 //确定用户是否已登录
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
     //如果已登录，请重定向到主页

      next({ path: '/home' })

      NProgress.done()

    } else {
      const hasRoles = store.getters.roles && store.getters.roles.length > 0

      if (hasRoles) {

        next()

      } else {
        try {
       //获取用户信息

         const {role}= await store.dispatch('user/getInfo')
          /*加载权限路径*/
          console.log(role)
          const authorityRouter = await store.dispatch('permission/generateRoutes',role)
          router.addRoutes(authorityRouter)
          next()
        } catch (error) {

         //删除令牌并转到登录页面以重新登录
          await store.dispatch('user/resetToken')

          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {

    //在登录白名单中，直接进入
      next()
    } else {
     //将其他无权访问的页面重定向到登录页面.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
