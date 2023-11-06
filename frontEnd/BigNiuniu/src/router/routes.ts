import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import HomeView from "../views/modiles/home/HomeView.vue";
import AdminView from "../views/modiles/admin/AdminView.vue";
import NoAuthView from "../views/common/NoAuthView.vue";
import UserView from "../views/modiles/user/UserView.vue";
import LoginView from "../views/modiles/login/LoginView.vue";
import RegistertView from "../views/modiles/login/register/RegisterView.vue";
import GlobalVideo from "../components/GlobalVideo.vue";
import UploadView from "../views/modiles/upload/UploadView.vue";
import ExploreView from "../views/modiles/explore/ExploreView.vue";
import FollowView from "../views/modiles/follow/FollowView.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "推荐",
    component: HomeView,
  },
  {
    path: "/following",
    name: "我的关注",
    component: FollowView,
  },
  {
    path: "/explore",
    name: "视频白洞",
    component: ExploreView,
  },
  {
    path: "/admin",
    name: "管理员",
    component: AdminView,
    meta: {
      access: "admin",
    },
  },
  {
    path: "/noAuth",
    name: "NoAuth",
    component: NoAuthView,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/register",
    name: "register",
    component: RegistertView,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      hideInMenu: true,
    },
  },
  // {
  //   path: "/user",
  //   name: "用户",
  //   component: UserView,
  //   children: [
  //     {
  //       path: "/login",
  //       name: "用户登录",
  //       component: LoginView,
  //     },
  //     {
  //       path: "/register",
  //       name: "用户注册",
  //       component: RegistertView,
  //     },
  //   ],
  //   meta: {
  //     hideInMenu: true,
  //   },
  // },
  {
    path: "/about",
    name: "about",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/common/AboutView.vue"),
  },
];
