import { asyncRoutes, publicRoutes } from '../../router'

/**
 * 使用meta.role确定当前用户是否具有权限
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {

  if (route.meta && route.meta.roles) {
    console.log("sccccccss")
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    console.log("aaaa")
    return true
  }
}


export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    console.log("今天今天i几天内")
    state.addRoutes = routes
    state.routes = publicRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      let accessedRoutes
      if (roles.includes('ROLE_ADMIN')||roles.includes('ROLE_STU')) {


        accessedRoutes = asyncRoutes || []
        console.log(accessedRoutes)
      } else {
        console.log("ssssssssssss"+roles)

        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
