<template>
  <div ref="videoContainer" class="video-ess">
    <video
      ref="videoRef"
      class="video-js vjs-big-play-centered"
      controls
      preload="auto"
    ></video>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref, defineEmits } from "vue";
import { defineProps } from "vue";
import videojs from "video.js";
import "video.js/dist/video-js.css";

const videoContainer = ref(null);

const emit = defineEmits(["register"]);

let player: videojs.Player;

// 使用 defineProps 定义组件属性
const props = defineProps({
  id: String,
  src: String,
  poster: String,
});

const videoRef = ref(null);

//禁止键盘上下键加入播放器选中功能选型

onMounted(() => {
  player = videojs(videoRef.value, {
    controls: true,
    autoplay: false,
    preload: "auto",
  });

  player.src({ type: "application/x-mpegURL", src: props.src });
  player.poster(props.poster);

  //滚动到可视区域自动播放监听
  const observer = new IntersectionObserver(
    ([entry]) => {
      if (entry.isIntersecting) {
        player?.play();
      } else {
        player?.pause();
      }
    },
    {
      root: null,
      rootMargin: "0px",
      threshold: 1,
    }
  );

  observer.observe(videoContainer?.value);

  emit("register", {
    play: () => player?.play(),
    pause: () => player?.pause(),
  });
});

onUnmounted(() => {
  if (player) {
    player.dispose();
    player = null;
  }
});
</script>
<style>
.video-ess {
  position: relative;
  width: 100%;
  padding-top: 177.77%; /* 9:16 的纵横比 */
  /*background: red;*/
}
.vjs-poster img {
  width: 100%;
  height: 100%;
}
.video-js {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 4px;
}
.video-js .vjs-big-play-button {
  font-size: 2.5em;
  line-height: 2.3em;
  height: 2.5em;
  width: 2.5em;
  -webkit-border-radius: 2.5em;
  -moz-border-radius: 2.5em;
  border-radius: 2.5em;
  background-color: #73859f;
  background-color: rgba(115, 133, 159, 0.5);
  border-width: 0.15em;
  margin-top: -1.25em;
  margin-left: -1.3em;
}
</style>
