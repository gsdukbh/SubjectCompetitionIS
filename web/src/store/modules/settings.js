// import variables from '@/styles/element-variables.scss'
import defaultSettings from '../../settings'

const { showSettings, tagsView, fixedHeader, sidebarLogo } = defaultSettings

const state = {
  showSettings: showSettings,
  tagsView: tagsView,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo
}

const mutations = {

  CHANGE_SETTING: (state, { key, value }) => {
    //不安全的写法,在 "eslint": "^6.7.2",中 提示错误 by LiJiWei
    // if (state.hasOwnProperty(key)) {
    //   state[key] = value
    // }
    if(Object.prototype.hasOwnProperty.call(state,key)){
      state[key] = value
    }
  }
}

const actions = {
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

