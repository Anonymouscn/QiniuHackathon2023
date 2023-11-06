<template>
  <div class="video-box">
    <div class="video-win">
      <!--      <div class="one">aaa</div>-->
      <div class="video-content" @keyup="handleKeydown">
        <!--视频区-->
        <el-scrollbar class="scrollbar-view" @pulling-down="handlePullingDown">
          <div v-for="index in videoData" :key="index" class="scrollbar-item">
            <!--  头像、标题、标签、音乐   -->
            <div class="video-comment">
              <a-avatar :size="48" trigger-type="mask">
                <img
                  alt="avatar"
                  src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"
                />
                <template #trigger-icon>
                  <IconEdit />
                </template>
              </a-avatar>
              <div
                style="
                  width: 79%;
                  margin-left: 10px;
                  display: flex;
                  flex-direction: column;
                "
              >
                <span style="margin-top: 2px; color: blue"
                  >我的名字叫自来水</span
                >
                <span style="margin-top: 8px"
                  >{{ index.title }} <a href="">#{{ index.tag }}</a></span
                >
                <span style="margin-top: 8px">♬{{ index.music }}</span>
              </div>
              <a-button type="primary" shape="round">关注</a-button>
            </div>
            <div class="scrollbar-item-video">
              <GlobalVideo
                :id="index.id"
                :src="index.src"
                :poster="index.poster"
              />
            </div>

            <div class="video-interaction">
              <icon-reply :size="48" />
              <icon-at :size="48" />
              <icon-star-fill :size="48" />
              <icon-thumb-up-fill :size="48" />
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, onBeforeUnmount } from "vue";
import GlobalVideo from "../../../components/GlobalVideo";
import videojs from "video.js";

//上下键控制滚动条
const scrollbar = ref(null);
const scrollbarEl = ref(null);
// 定义对 Scrollbar 组件的引用
const scrollbarRef = ref(null);

const userInfo = reactive({
  username: "我的名字叫自来水",
  avatar: "",
});

const videoData = reactive([
  {
    id: "11",
    title: "我十里山路，扛着300斤茅台，不换车啊！",
    tag: "生活",
    music: "老司机带带我！~",
    src: "http://s3af5jw1s.hn-bkt.clouddn.com/16360566-6d42-4a89-8b57-1ea4edb1b702/product-niutest1.m3u8",
    poster:
      "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/oM4tDepXGDRKLZPIXuIfvDIPbCoQgkeANCjUlA~tplv-photomode-zoomcover:720:720.avif?x-expires=1699405200&x-signature=jqIiucpdEmv51IBDpI7DFic%2FUo4%3D",
  },
  {
    id: "1",
    title: "我十里山路，扛着300斤茅台，不换车啊！",
    tag: "生活",
    music: "老司机带带我！~",
    src: "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
    poster:
      "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/b30151f640004399807d173899bd047e_1699025043~tplv-photomode-zoomcover:720:720.avif?x-expires=1699405200&x-signature=aPVrSmjLtC6I24C5EKG66ggbZ9s%3D",
  },
  {
    id: "2",
    title: "我十里山路，扛着300斤茅台，不换车啊！",
    tag: "生活",
    music: "老司机带带我！~",
    src: "http://s3af5jw1s.hn-bkt.clouddn.com/8a776344-dc9a-4f1c-9998-fcbf6e57503a/product-test2.m3u8",
    poster:
      "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/b30151f640004399807d173899bd047e_1699025043~tplv-photomode-zoomcover:720:720.avif?x-expires=1699405200&x-signature=aPVrSmjLtC6I24C5EKG66ggbZ9s%3D",
  },
  {
    id: "3",
    title: "我十里山路，扛着300斤茅台，不换车啊！",
    tag: "生活",
    music: "老司机带带我！~",
    src: "http://s3af5jw1s.hn-bkt.clouddn.com/7b62c51c-0de7-4d15-9cfd-6e23655ddeca/product-test.m3u8",
    poster:
      "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/b30151f640004399807d173899bd047e_1699025043~tplv-photomode-zoomcover:720:720.avif?x-expires=1699405200&x-signature=aPVrSmjLtC6I24C5EKG66ggbZ9s%3D",
  },
  {
    id: "4",
    title: "我十里山路，扛着300斤茅台，不换车啊！",
    tag: "生活",
    music: "老司机带带我！~",
    src: "http://s3af5jw1s.hn-bkt.clouddn.com/15200224-319f-41ef-a9da-b9f676c1c37b/product-test3.m3u8",
    poster:
      "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/b30151f640004399807d173899bd047e_1699025043~tplv-photomode-zoomcover:720:720.avif?x-expires=1699405200&x-signature=aPVrSmjLtC6I24C5EKG66ggbZ9s%3D",
  },
  {
    id: "5",
    title: "我十里山路，扛着300斤茅台，不换车啊！",
    tag: "生活",
    music: "老司机带带我！~",
    src: "http://s3af5jw1s.hn-bkt.clouddn.com/2a80ee33-c97c-4ac8-b626-b7d2ea4a4d41/product-isover.m3u8",
    poster:
      "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/b30151f640004399807d173899bd047e_1699025043~tplv-photomode-zoomcover:720:720.avif?x-expires=1699405200&x-signature=aPVrSmjLtC6I24C5EKG66ggbZ9s%3D",
  },
]);
//下拉更新函数
const handlePullingDown = () => {
  setTimeout(() => {
    const newData = [
      {
        id: "4",
        src: "http://s3af5jw1s.hn-bkt.clouddn.com/15200224-319f-41ef-a9da-b9f676c1c37b/product-test3.m3u8",
        poster:
          "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/18dd84d1aa614a6b95032e3835c17b02_1694943429~tplv-photomode-zoomcover:720:720.avif?x-expires=1699099200&x-signature=n2HzFhxsnj20ObA%2Fr0rY50cXR0k%3D",
      },
      {
        id: "5",
        src: "http://s3af5jw1s.hn-bkt.clouddn.com/2a80ee33-c97c-4ac8-b626-b7d2ea4a4d41/product-isover.m3u8",
        poster:
          "https://p16-sign-sg.tiktokcdn.com/tos-alisg-p-0037/18dd84d1aa614a6b95032e3835c17b02_1694943429~tplv-photomode-zoomcover:720:720.avif?x-expires=1699099200&x-signature=n2HzFhxsnj20ObA%2Fr0rY50cXR0k%3D",
      },
    ];
    videoData.value = [...newData, ...videoData?.value];
  }, 1000);
};
// const videoPlayers = ref([]);
//
// const registerPlayer = (index, player) => {
//   videoPlayers.value[index] = player;
// };
//
// const handleKeydown = (event: KeyboardEvent) => {
//   const currentIndex = videoPlayers.value.findIndex(
//     (player) => !player.paused()
//   );
//   if (event.key === "ArrowUp" && currentIndex > 0) {
//     console.log("你点了ArrowUp" + event.key);
//     videoPlayers.value[currentIndex].pause();
//     videoPlayers.value[currentIndex - 1].play();
//   } else if (
//     event.key === "ArrowDown" &&
//     currentIndex < videoPlayers.value.length - 1
//   ) {
//     console.log("你点了ArrowDown" + event.key);
//     videoPlayers.value[currentIndex].pause();
//     videoPlayers.value[currentIndex + 1].play();
//   }
// };
//
// onMounted(() => {
//   window.addEventListener("keydown", handleKeydown);
// });
</script>

<style scoped>
.video-box {
  width: 100%;
  /*width: 80vw;*/
  /*height: 85vh;*/
  height: 100%;
  /*background: navy;*/
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
  width: 66.99%;
  height: 100%;
  /*background: green;*/
}
.there {
  width: 33.33%;
  height: 100%;
  /*background: #6d10d0;*/
}
.viedo-user {
  width: 100%;
  /*background: #9e00de;*/
}
.video-view {
  width: 100%;
  height: 900px;
  /*background: #04f1eb;*/
}
.scrollbar-view {
  width: 100%;
  height: 91vh;
  /*margin-bottom: 20px;*/
  /*background: #606266;*/
}
.scrollbar-item {
  display: flex;
  flex-direction: column;
  /*align-items: center;*/
  /*justify-content: center;*/
  width: 560px;
  height: 672px;
  padding: 20px;
  margin-bottom: 20px;
  /*text-align: center;*/
  position: relative;
  border-bottom: 1px solid var(--color-neutral-3);
  /*background: red;*/
  /*color: var(--el-color-primary);*/
}
.scrollbar-item-video {
  width: 315px;
  height: 560px;
  border-radius: 4px;
  box-shadow: 0px 0px 10px #000;
  /*background: blue;*/
}
.video-comment {
  width: 100%;
  height: 100px;
  font-size: 16px;
  left: 10px;
  display: flex;
  flex-direction: row;
}
.video-interaction {
  width: 48px;
  height: 280px;
  position: absolute;
  right: 190px;
  bottom: 32px;
  flex: none;
  display: flex;
  flex-direction: column-reverse;
  justify-content: space-between;
  color: #165dff;
  /*background: green;*/
}
</style>
