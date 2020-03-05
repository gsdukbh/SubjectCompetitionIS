import Vue from 'vue'
import Vuex from 'vuex'
import user from "./modules/user";
import app from "./modules/app";
import getters from "./getters";
import settings from "./modules/settings";
import permission from "./modules/permission";


Vue.use(Vuex)

export default new Vuex.Store({
  state: {/*保存数据，全局*/
  },
  getters,
  mutations: {/*修改state*/
  },
  /**
   * Action 提交的是 mutation，而不是直接变更状态。
   * Action 可以包含任意异步操作
   */
  actions: {
  },
  /**
   * 模块
   */
  modules: {
    user,
    app,
    settings,
    permission
  }
})
