<template>
  <div class="video-box">
    <div class="video-win">
      <div class="one"></div>
      <div class="video-content">
        <a-list
          class="scrollbar-view"
          :max-height="900"
          @reach-bottom="fetchData"
          :scrollbar="true"
        >
          <template #header> 牛牛视界 </template>
          <template #scroll-loading>
            <div v-if="bottom">没有更多的视频了！！！</div>
            <a-spin v-else />
          </template>
          <a-list-item class="list-item" v-for="item of data" :key="item">
            <GlobalVideo />
          </a-list-item>
          <ZtVideo />
          <!--          <a-list-item class="list-item">-->
          <!--            <videobox />-->
          <!--          </a-list-item>-->
        </a-list>
        <!--        xxxx-->
        <div class="viedo-user"></div>
        <GlobalVideo />
        <div class="video-comment"></div>
      </div>
      <div class="there"></div>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import GlobalVideo from "../../../components/GlobalVideo";
export default {
  setup() {
    const current = ref(1);
    const bottom = ref(false);
    const data = reactive([]);

    const fetchData = () => {
      console.log("reach bottom!");
      if (current.value <= 5) {
        window.setTimeout(() => {
          const index = data.length;
          data.push(
            `Beijing Bytedance Technology Co., Ltd. ${index + 1}`,
            `Bytedance Technology Co., Ltd. ${index + 2}`,
            `Beijing Toutiao Technology Co., Ltd. ${index + 3}`,
            `Beijing Volcengine Technology Co., Ltd. ${index + 4}`,
            `China Beijing Bytedance Technology Co., Ltd. ${index + 5}`
          );
          current.value += 10;
        }, 1000);
      } else {
        bottom.value = true;
      }
    };

    return {
      current,
      bottom,
      data,
      fetchData,
    };
  },
};
</script>
<style scoped>
.video-box {
  /* width: 100%; */
  width: 80vw;
  height: 85vh;
  /* background: navy; */
  overflow: hidden;
  /* padding: 10vh 0;*/
}
.video-win {
  /*background: aqua;*/
  display: flex;
  flex-direction: row-reverse;
  height: 100%;
}
.one {
  width: 33.33%;
  height: 100%;
  /*background: gold;*/
}
.video-content {
  width: 33.33%;
  height: 1107px;
  /*background: green;*/
  display: flex;
  flex-direction: column;
  align-items: center;
}
.there {
  width: 33.33%;
  height: 100%;
  /*background: #6d10d0;*/
}
.viedo-user {
  width: 100%;
  height: 200px;
}
.scrollbar-view {
  width: 100%;
  height: 900px;
  background: blue;
}
.list-item {
  width: 100%;
  height: 100vh;
  /*background: rgba(255, 255, 0, 0.41);*/
}
</style>
