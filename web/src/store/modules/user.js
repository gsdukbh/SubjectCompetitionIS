import {getInfo, logout, postFrom} from '../../api/api'
import { getToken, setToken, removeToken } from '../../utils/auth'
import { resetRouter } from '../../router'

const state = {
  token: getToken(),
  name: '',
  avatar: 'https://avatars3.githubusercontent.com/u/43371824?s=460&v=4',
  introduction: '',
  roles: []/*做权限管理*/
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // 用户登录 异步
  login({commit}, userInfo) {


    return new Promise((resolve, reject) => {

      postFrom("/login", userInfo)
          .then(response => {
            const {data} = response
            commit('SET_TOKEN', data.token)//使用用户名
            setToken(data.token)
            resolve(response)
            // this.getInfo(commit)
          })
          .catch(errors => {
            reject(errors)
          })
    })
  },

  // 获取用户信息
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {

      getInfo(state.token).then(response => {
        const { data } = response
        if (!data) {
          reject('验证失败，请重新登录.')
        }

      const { role, message, avatar } = data

        // 角色必须是非空数组

        if (!role || role.length <= 0) {
          reject(message)
        }

        commit('SET_ROLES', role)
        commit('SET_NAME', data.data.name)
        commit('SET_AVATAR', avatar)
        // commit('SET_INTRODUCTION', introduction)

        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 用户注销
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resetRouter()

        // 重置访问的视图和缓存的视图
        // 固定 https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, { root: true })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 删除令牌
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
