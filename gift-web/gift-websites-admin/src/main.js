import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
import routes from './routes'
// import Mock from './mock'
// Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
//===============引入axios============================
import axios from 'axios'
Vue.prototype.$http = axios
// 所有的aioxs请求  都自动拼上 api
// axios.defaults.baseURL='http://47.109.34.203:8090'
// 将linux服务器上的请求后台的路径 改为 http://47.109.34.203:8888
//axios.defaults.baseURL='http://159.138.148.160:8090'
axios.defaults.baseURL='http://127.0.0.1:8090'
// axios.defaults.baseURL='http://api.permission.com'
//===============引入axios============================

import BaiduMap from 'vue-baidu-map'
Vue.use(BaiduMap, {
    ak: 'DcF7MeZvNZ7q3364YLGW4d8RT9xFn9rk'
})

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

//NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes
})
// 这个是路由拦截 会在每一次 路由进行跳转的时候 被执行
/*
router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }
  let user = JSON.parse(localStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

 */

//router.afterEach(transition => {
//NProgress.done();
//});
// axios请求拦截器:添加请求拦截器，在请求头中加token
/*
axios.interceptors.request.use(
    config => {
      // 从localStorage中获取token
      let token = localStorage.getItem("token");

      // 如果token有值,我们就放到请求头里面
      if (token) {
        config.headers.token = token;
      }
      return config
    },
    error => {
      return Promise.reject(error)
    })
// 响应拦截器
axios.interceptors.response.use(function(response){
    //对返回的数据进行操作
   let data = response.data
    let result =  data // response.data 就是后端返给我们的数据
    if(!result.success && result.msg == "noLogin"){ // 说明未登录,被拦截了,那么就要跳回到登陆页面
        router.push({ path: '/login' });  // 跳转回登陆页面,让用户登陆
    }

    if(!result.success && result.msg == "noPermission"){ // 说明未登录,被拦截了,那么就要跳回到登陆页面
        router.push({ path: '/noPermission' });  // 跳转回登陆页面,让用户登陆
    }
    return response
},function(err){
    return Promise.reject(err)
})

 */
new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

