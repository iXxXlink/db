import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/index'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/index',
                    component: () => import("../components/page/Index"),
                    meta: { title: '系统首页' }
                },
                {
                    path:"/video",
                    component:()=>import("../components/page/Video.vue"),
                    meta:{
                        title:"视频模块"
                    }
                },

                {
                    path:"/kitchenquery",
                    component:()=>import("../components/page/Kitchenquery.vue"),
                    meta:{
                        title:"餐厅信息查询"
                    }

                },
                {
                    path:"/kitchendetails",
                    name:"kitchendetails",
                    component:()=>import("../components/page/Kitchendetails"),
                    meta:{
                        title:"详细信息"
                    }

                },
                {
                    path:"/alarm",
                    component:()=>import("../components/page/Alarm.vue"),
                    meta:{
                        title:"违规信息"
                    }
                },
                {
                    path:"/logs",
                    component:()=>import("../components/page/Logs"),
                    meta:{
                        title:"日志信息"
                    }
                },
                {
                    path:"/test",
                    component:()=>import("../components/page/Test"),
                    meta:{
                        title:"测试"
                    }
                },

            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '/register',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Register.vue'),
            meta: { title: '注册' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
