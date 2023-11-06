<template>
  <div class="follow-view">
    <a-list
      style="width: 100%; height: 100vh; margin-bottom: 10px"
      :max-height="880"
      @reach-bottom="fetchData"
      :scrollbar="scrollbar"
    >
      <template #header> 关注列表 </template>
      <template #scroll-loading>
        <div v-if="bottom" style="color: #996996">没有更多的视频了!</div>
        <a-spin v-else />
      </template>
      <a-list-item class="follow-list" v-for="item of data" :key="item">
        <div style="height: 60px; font-size: 19px; color: #165dff">
          <a-avatar
            :size="48"
            trigger-type="mask"
            style="margin-left: 10px; margin-top: 5px"
          >
            <img
              alt="avatar"
              src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"
            />
            <template #trigger-icon>
              <IconEdit />
            </template> </a-avatar
          ><a href="/">{{ item }}</a>
        </div>
        <div class="follow-list-video">
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
              margin-bottom: 10px;
            "
          ></div>
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
              margin-bottom: 10px;
            "
          ></div>
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
              margin-bottom: 10px;
            "
          ></div>
          <div
            style="
              width: 315px;
              height: 565px;
              background: green;
              border-radius: 2%;
              margin-bottom: 10px;
            "
          ></div>
        </div>
      </a-list-item>
    </a-list>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
const current = ref(1);
const bottom = ref(false);
const data = reactive([]);
const scrollbar = ref(true);

const followData = reactive([
  {
    username: "我的名字叫自来水",
    video_list: [
      {
        title: "磨铁的第一天",
        video_url: "https://baidu.com",
      },
    ],
  },
]);

const fetchData = () => {
  console.log("reach bottom!");
  if (current.value <= 3) {
    window.setTimeout(() => {
      const index = data.length;
      data.push(
        `我的名字叫自来水 ${index + 1}`,
        `我的名字叫自来水 ${index + 2}`
      );
      current.value += 1;
    }, 2000);
  } else {
    bottom.value = true;
  }
};
</script>

<style lang="css" src="./styles/styles.css" scoped></style>
