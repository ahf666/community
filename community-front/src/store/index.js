import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({

  state: {
    admin: {
      id : 0,
      username : '游客',
      phone : 'xxx',
      token : 'xxx'
    },
    user: {
      id : 0,
      name : '游客',
      username: '',
      sex: 0,
      phone : 'xxx',
      token : 'xxx'
    },
    house:{
      id: 0,
      building: 0,
      cell: 0,
      floor: 0,
      doorplate: ''
    }
  },
  getters: {
  },
  // 通过当前属性中定义的函数修改共享变量，必须是同步操作
  mutations: {
    setAdmin(state, adminPar){
      state.admin.id = adminPar.id;
      state.admin.username = adminPar.username;
      state.admin.phone = adminPar.phone;
      state.admin.token = adminPar.token;
    },
    setUser(state, userPar){
      state.user.id = userPar.id;
      state.user.name = userPar.name;
      state.user.username = userPar.username;
      state.user.sex = userPar.sex;
      state.user.phone = userPar.phone;
      state.user.token = userPar.token;
    },
    setHouse(state, housePar){
      state.house = housePar;
    }
  },
  // 通过actions调用mutations，在actions中可以进行异步操作
  // axios异步请求的回调函数调用mutation中的函数
  actions: {
    adminLogin(context, par){
      context.commit('setAdmin', par);
    },
    userLogin(context, par){
      context.commit('setUser', par);
    },
    asyncSetHouse(context, par){
      context.commit('setHouse', par)
    }

  },
  modules: {
  }
})
