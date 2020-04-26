import { asyncRoutes, publicRoutes } from '../../router'

/**
 * 使用meta.role确定当前用户是否具有权限
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {

  if (route.meta && route.meta.roles) {

    return roles.some(role => route.meta.roles.includes(role))
  } else {

    return true
  }
}

/**
 * 动态获取路由，根据路由元信息
 * @param routes
 * @param roles
 * @returns {[]}
 */
export function filterAsyncRoutes(routes, roles) {
  const res = [];

  routes.forEach(route => {
    const tmp = { ...route };
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  });

  return res
}

const state = {
  routes: [],
  addRoutes: []
};

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes;
    state.routes = publicRoutes.concat(routes)
  }
};

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise(resolve => {
      let accessedRoutes;
      if (roles.includes('ROLE_ADMIN') || roles.includes('ROLE_TEA')||roles.includes('ROLE_STU')) {


        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)

      } else {

        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', accessedRoutes);
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
