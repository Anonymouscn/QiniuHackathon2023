<template>
  <a-layout-sider
    breakpoint="lg"
    :width="220"
    :collapsed="collapsed"
    @collapse="onCollapse"
  >
    <div class="logo">
      <img src="../assets/niuniu.svg" />
      <span class="title">BigNiuniu</span>
    </div>
    <a-menu
      class="menu"
      mode="pop"
      :selected-keys="selectedkeys"
      @menu-item-click="menuClick"
    >
      <a-menu-item
        class="emnuItem"
        v-for="item in visibleRoutes"
        :key="item.path"
      >
        <icon-home />{{ item.name }}
      </a-menu-item>
    </a-menu>
    <a-button class="retract" type="primary" @click="onCollapse">
      <icon-menu-unfold v-if="collapsed" />
      <icon-menu-fold v-else />
    </a-button>
  </a-layout-sider>
</template>
<script setup lang="ts">
import { routes } from "../router/routes";
import { useRoute, useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import ACCESS_ENUM from "@/access/accessEnum";
import checkAuth from "@/access/checkAuth";
import { Message } from "@arco-design/web-vue";

const router = useRouter();
const store = useStore();
const currentUser = store.state.user.loginUser;
/** 默认主页 */
const selectedkeys = ref(["/"]);
/** 路由跳转后，更新选中的菜单项 */
router.afterEach((to, from, failure) => {
  selectedkeys.value = [to.path];
});
/** 显示或者隐藏路由 */
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    if (!checkAuth(store.state.user.loginUser, item?.meta?.access as string)) {
      return false;
    }
    return true;
  });
});
/** 测试3秒后设置登入状态 */
setTimeout(() => {
  store.dispatch("user/getLoginUser", {
    userName: "ZT-admin",
    userRole: ACCESS_ENUM.ADMIN,
  });
}, 3000);

const menuClick = (key: string) => {
  router.push({
    path: key,
  });
};

// const collapsed = ref(false);

const collapsed = ref(false);
const onCollapse = (val: any, type: any) => {
  const content = type === "responsive" ? "触发响应式收缩" : "点击触发收缩";
  Message.info({
    content,
    duration: 2000,
  });
  collapsed.value = !collapsed.value;
};

// const toggleCollapse = () => {
//   collapsed.value = !collapsed.value;
// };
</script>
<style scoped>
.layout-demo :deep(.arco-layout-sider) .logo {
  height: 32px;
  margin: 12px 0px;
  background: rgba(255, 255, 255, 0.2);
}
.layout-demo[data-v-d4ba89ee] .arco-layout-sider-light .logo {
  /*background: var(--color-fill-2);*/
  display: flex;
  flex-direction: row;
  padding: 0px;
  align-items: center;
}
/*.menu {*/
/*  padding: 8px;*/
/*  display: flex;*/
/*  flex-direction: row-reverse;*/
/*  justify-content: center;*/
/*  text-align: center;*/
/*}*/
.menu .emnuItem {
  margin: 10% 0 0 0;
}
.title {
  width: 130px;
  color: #979797;
  font-size: 20px;
  margin: auto;
}
.retract {
  padding: 0 14px;
  float: right;
  top: 50%;
  height: 4%;
  line-height: 30px;
}
</style>
