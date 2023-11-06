import { createApp } from "vue";
import App from "./App.vue";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import router from "./router";
import store from "./store";
import "@/plugins/axios";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import VideoJs from "@videojs-player/vue";
import "video.js/dist/video-js.css";
import hls from "videojs-contrib-hls";

createApp(App)
  .use(ArcoVue)
  .use(ArcoVueIcon)
  .use(ElementPlus)
  .use(VideoJs)
  .use(hls)
  .use(store)
  .use(router)
  .mount("#app");
