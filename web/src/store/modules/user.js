import {getInfo, logout, postFrom} from '../../api/api'
import {getToken, setToken, removeToken, getName, setName, removeName} from '../../utils/auth'
import { resetRouter } from '../../router'



const state = {
  userId:'',
  token: getToken(),
  name: getName(),
  avatar: 'https://data.werls.top/img/9544f13f3aec41ada4154e35c527485c-12.png',
  introduction: '',
  roles: []/*做权限管理*/
};

const mutations = {
  SET_ID:(state,userId)=>{
    state.userId=userId;
  },
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
            const {data} = response;
            commit('SET_TOKEN', data.token);//使用用户名
            commit('SET_NAME', data.name);
            setToken(data.token);
            setName(data.name);
            resolve(response);
            this.getInfo(commit)
          })
          .catch(errors => {
            reject(errors)
          })
    })
  },

  // 获取用户信息
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.name).then(response => {
        const { data } = response;
        if (!data) {

          reject('验证失败，请重新登录.')
        }else if(data.status === 0 ){
          reject(data.message);
          // this.resetToken(commit)
        }

      const { role, message} = data;

        // 角色必须是非空数组

        if (!role || role.length <= 0) {
          reject(message)
        }

        commit('SET_ROLES', role);
        commit('SET_ID',data.data.id);
        commit('SET_NAME', data.data.name);
        // commit('SET_AVATAR', avatar)
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
        commit('SET_TOKEN', null);
        commit('SET_ROLES', []);
        removeToken();
        resetRouter();
        removeName();
        dispatch('tagsView/delAllViews', null, { root: true });

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 删除令牌
  resetToken({ commit ,dispatch}) {
    return new Promise(resolve => {
      dispatch('tagsView/delAllViews', null, { root: true });
      commit('SET_TOKEN', null);
      commit('SET_ROLES', []);
      removeToken();
      removeName();
      resolve();
    })
  },

};

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
