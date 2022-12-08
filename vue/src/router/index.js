import {createRouter, createWebHashHistory} from "vue-router";
import login from "../views/login";
import register from "../views/register";
import page from "../views/page";
import user from "../views/user";
import equ from "../views/equ";
import act from "../views/act";
import sen from "../views/sen";
import index from "../views/index";

const routes = [
    //主界面
    {
        path: "/",
        name: "index",
        component: index,
        meta: {
            requireAuth: true,
        },
    },
    //登录
    {
        path: "/login",
        name: "login",
        component: login,
        meta: {
            requireAuth: true,
        },
    },
    //注册
    {
        path: "/register",
        name: "register",
        component: register,
        meta: {
            requireAuth: true,
        },
    },
    //公共
    {
        path: "/page",
        name: "page",
        component: page,
        //主页
        children: [
            //用户管理
            {
                path: "/user",
                name: "user",
                component: user,
            },
            //设备管理
            {
                path: "/equ",
                name: "equ",
                component: equ,
            },
            //传感器管理
            {
                path: "/sen",
                name: "sen",
                component: sen,
            },
            //执行器管理
            {
                path: "/act",
                name: "act",
                component: act,
            },
        ],
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

//登录拦截
router.beforeEach((to, from, next) => {
    if (!to.meta.requireAuth) {
        var token = sessionStorage.getItem("token");
        if (token != null) {
            next();
        } else {
            next({
                name: "login",
            });
        }
    } else {
        next();
    }
});
export default router;
