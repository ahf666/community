import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  // {
  //   path: '/',
  //   name: 'home',
  //   component: HomeView
  // },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // },

  {
    path: '/login',
    component: () => import('../views/login/login.vue')
  },
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/adminLogin',
    component: () => import('../views/admin/login/login.vue')
  },
  {
    path: '/userLogin',
    component: () => import('../views/user/login/login.vue')
  },
  {
    path: '/admin',
    redirect: '/admin/user'
  },
  {
    path: '/user',
    redirect: '/user/account'
  },
  {
    path: '/admin',
    component: () => import('../views/container/adminContainerView.vue'),
    children: [
      {
        path: '/admin/user',
        component: () => import('../views/admin/user/user.vue')
      },
      {
        path: '/admin/house',
        component: () => import('../views/admin/house/house.vue')
      },
      {
        path: '/admin/stop',
        component: () => import('../views/admin/stop/stop.vue')
      },
      {
        path: '/admin/fee',
        component: () => import('../views/admin/fee/fee.vue')
      },
      {
        path: '/admin/repair',
        component: () => import('../views/admin/repair/repair.vue')
      },
      {
        path: '/admin/complain',
        component: () => import('../views/admin/complain/complain.vue')
      },
      {
        path: '/admin/userCar',
        component: () => import('../views/admin/userCar/userCar.vue')
      },
      {
        path: '/admin/currentCarRedis',
        component: () => import('../views/admin/currentCar/currentCarRedis.vue')
      },
      {
        path: '/admin/currentCarMysql',
        component: () => import('../views/admin/currentCar/currentCarMysql.vue')
      }
    ]
  },
  {
    path: '/user',
    component: () => import('../views/container/userContainerView.vue'),
    children: [
      {
        path: '/user/account',
        component: () => import('../views/user/account/account.vue')
      },
      {
        path: '/user/stop',
        component: () => import('../views/user/property/stop.vue')
      },
      {
        path: '/user/car',
        component: () => import('../views/user/property/car.vue')
      },
      {
        path: '/user/fee',
        component: () => import('../views/user/fee/fee.vue')
      },
      {
        path: '/user/repair',
        component: () => import('../views/user/repair/repair.vue')
      },
      {
        path: '/user/complain',
        component: () => import('../views/user/complain/complain.vue')
      }
    ]
  }

]

const router = new VueRouter({
  routes
})

// 防止重复点击报错

// 获取原型对象push函数
const originalPush = VueRouter.prototype.push

// 获取原型对象replace函数
const originalReplace = VueRouter.prototype.replace

// 修改原型对象中的push函数
VueRouter.prototype.push = function push(location){
return originalPush.call(this , location).catch(err=>err)
}

// 修改原型对象中的replace函数
VueRouter.prototype.replace = function replace(location){
return originalReplace.call(this , location).catch(err=>err)
}

export default router
