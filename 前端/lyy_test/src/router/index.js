import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/login.vue'
import Register from '../views/register.vue'

import Main_teacher from '../views/teacher_views/main_teacher.vue'
import CreateCourse from '../views/teacher_views/createCourse.vue'
import MyCourse_teacher from '../views/teacher_views/myCourse_teacher.vue'
import CourseDetail_teacher from '../views/teacher_views/courseDetail_teacher.vue'
import CorrectHomework from '../views/teacher_views/correctHomework.vue'

import Main_student from '../views/student_views/main_student.vue'
import ApplyCourse from '../views/student_views/applyCourse.vue'
import MyCourse_student from '../views/student_views/myCourse_student.vue'
import CourseDetail_student from '../views/student_views/courseDetail_student.vue'


import Main from '../views/main.vue'
import CreatePaper from '../views/createPaper.vue'
import MyPaper from '../views/myPaper.vue'
import MyPaperDetail from '../views/myPaperDetail.vue'
import WritePaper from '../views/writePaper.vue'
import WritePaperDetail from '../views/writePaperDetail.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
	path: '/login',
	name: 'Login',
	component: Login  
  },
  {
	path:'/register',
	name:'Register',
	component:Register
  },
  {
	  path:'/main_teacher',
	  name:"Main_teacher",
	  component:Main_teacher,
	  children:[
		  {
			  path:"/",
			  component:CreateCourse
		  },
		  {
			  path:"createCourse",
			  component:CreateCourse
		  },
		  {
			  path:"myCourse_teacher",
			  component:MyCourse_teacher
		  },
		  {
			  path:"courseDetail_teacher",
			  component:CourseDetail_teacher
		  },
		  {
			  path:"correctHomework",
			  component:CorrectHomework
		  }
	  ]
  },
  {
	  path:'/main_student',
	  name:"Main_student",
	  component:Main_student,
	  children:[
		  {
			path:"/",
			 component:ApplyCourse 
		  },
		  {
			  path:"applyCourse",
			  component:ApplyCourse
		  },
		  {
			  path:"myCourse_student",
			  component:MyCourse_student
		  },
		  {
			  path:"courseDetail_student",
			  component:CourseDetail_student
		  }
		  
	  ]
	  
  },
  
  
  
  
  
  
  
  
  
  {
	path:'/main',
	name:'Main',
	component:Main,
	children:[
		{
			path:"/",
			component:CreatePaper
		},
		{
			path:'createPaper',
			component:CreatePaper
		},
		{
			path:'myPaper',
			component:MyPaper
		},
		{
			path:'myPaperDetail',
			component:MyPaperDetail
		},
		{
			path:'writePaper',
			component:WritePaper
		},
		{
			path:'writePaperDetail',
			component:WritePaperDetail
		}
	]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
