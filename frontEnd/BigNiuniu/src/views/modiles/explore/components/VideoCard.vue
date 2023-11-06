<template>
  <div class="video-card">
    <a-list
      style="width: 100%; height: 100vh"
      :max-height="880"
      @reach-bottom="fetchData"
      :scrollbar="scrollbar"
    >
      <template #scroll-loading>
        <div v-if="bottom" style="color: #996996">没有更多的视频了!</div>
        <a-spin v-else />
      </template>
      <a-list-item class="explore-list" v-for="item of data" :key="item">
        <div class="explore-list-video">
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 8px;
            "
          >
            {{ item }}
          </div>
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
            "
          ></div>
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
            "
          ></div>
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
            "
          ></div>
        </div>
      </a-list-item>
    </a-list>
  </div>
</template>

<script lang="ts" setup>
import CardWrap from "./CardWrap.vue";
import { reactive, ref } from "vue";
const current = ref(1);
const bottom = ref(false);
const data = reactive([]);
const scrollbar = ref(true);

const fetchData = () => {
  if (current.value <= 4) {
    window.setTimeout(() => {
      const index = data.length;
      data.push(
        `Beijing Bytedance Technology Co., Ltd. ${index + 1}`,
        `Bytedance Technology Co., Ltd. ${index + 2}`
      );
      current.value += 1;
    }, 2000);
  } else {
    bottom.value = true;
  }
};
</script>

<style scoped>
.card-wrap {
  height: 100%;
  transition: all 0.3s;
  border: 1px solid var(--color-neutral-3);
}
.card-wrap:hover {
  transform: translateY(-4px);
}
.card-wrap .arco-card-meta-description {
  color: rgb(var(--gray-6));
}
.card-wrap .arco-card-meta-description .arco-descriptions-item-label-inline {
  font-weight: normal;
  font-size: 12px;
  color: rgb(var(--gray-6));
}
.card-wrap .arco-card-meta-description .arco-descriptions-item-value-inline {
  color: rgb(var(--gray-8));
}
.empty-wrap {
  height: 200px;
  border-radius: 4px;
}
.empty-wrap .arco-card {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
}
.empty-wrap .arco-card .arco-result-title {
  color: rgb(var(--gray-6));
}
.explore-list-video {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
