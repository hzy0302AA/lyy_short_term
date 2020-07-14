import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import store from './store'
import axios from 'axios'

Vue.use(ElementUI);
Vue.config.productionTip = false


Vue.prototype.$http = axios.create({
	baseURL:'http://localhost:8081'
})
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';


// router.beforeEach((to,from,next)=>{
// 	if(to.path == '/login'){
// 		sessionStorage.removeItem('user_name');
// 	}
	
// 	var user_name = sessionStorage.getItem('user_name');
// 	if(user_name==null&&to.path!='/login'){
// 		next({path:'/login'})
// 	}
// 	else{
// 		next()
// 	}
// })

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


