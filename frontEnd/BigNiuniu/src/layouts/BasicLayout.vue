<template>
  <div id="basicLayout">
    <a-layout class="layout-left">
      <!-- 侧边菜单   -->
      <GlobalSider />
      <!-- 侧栏右边   -->
      <a-layout class="layout-right">
        <!--   header   -->
        <a-layout-header>
          <GlobalHeader />
        </a-layout-header>
        <!--   主要页面区   -->
        <a-layout style="padding: 0 24px">
          <!-- Header隔离 -->
          <a-breadcrumb :style="{ margin: '10px 0' }">
            <a-breadcrumb-item></a-breadcrumb-item>
          </a-breadcrumb>
          <a-layout-content class="ayout-content">
            <router-view />
          </a-layout-content>
        </a-layout>
      </a-layout>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalSider from "@/components/GlobalSider.vue";
import GlobalHeader from "@/components/GlobalHeader";
import { defineComponent, ref } from "vue";
const collapsed = ref(false);
const onCollapse = (val: any, type: any) => {
  collapsed.value = val;
};
const handleClick = (event) => {
  // 阻止默认行为
  event.preventDefault();
  // 在这里处理点击事件
};
</script>

<style scoped>
#basicLayout {
  width: 100vw;
  height: 100vh;
}
.layout-left {
  height: 100vh;
  /*background: var(--color-fill-2);*/
  overflow: hidden;
  border: 1px solid var(--color-border);
}
.layout-left :deep(.arco-layout-sider) .logo {
  height: 60px;
  margin: 12px 8px;
  display: flex;
  /*background: rgba(255, 255, 255, 0.2);*/
}
.layout-left :deep(.arco-layout-header) {
  height: 64px;
  line-height: 64px;
  overflow: hidden;
  background: var(--color-bg-3);
}
/*.layout-left :deep(.arco-layout-footer) {*/
/*  height: 48px;*/
/*  color: var(--color-text-2);*/
/*  font-weight: 400;*/
/*  font-size: 14px;*/
/*  line-height: 48px;*/
/*}*/
/*.layout-left :deep(.arco-layout-content) {*/
/*  color: var(--color-text-2);*/
/*  font-weight: 400;*/
/*  font-size: 14px;*/
/*  background: var(--color-bg-3);*/
/*  !*background: red;*!*/
/*}*/
.layout-left:deep(.arco-layout-content) {
  display: flex;
  border-radius: 8px;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
  color: var(--color-text-2);
  font-weight: 400;
  font-size: 14px;
  background: var(--color-bg-3);
}
.layout-left[data-v-d4ba89ee] .arco-layout-sider-light .logo {
  background: var(--color-fill-2);
  display: flex;
  flex-direction: row;
  padding: 5px;
  align-items: center;

  background: red;
}
.layout-right {
  display: flex;
  flex: 1;
  flex-direction: column;
  margin: 0;
  background: var(--color-border);
  border-radius: 8px;
  overflow: hidden;
  padding: 0;
}
/*.ayout-content {*/
/*  background: red;*/
/*  height: 900px;*/
/*}*/
</style>
