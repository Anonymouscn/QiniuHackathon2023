<template>
  <a-form
    size="large"
    :model="form"
    :style="{ width: '600px', marginTop: '20px' }"
  >
    <a-form-item
      maxLength="30"
      minLength="5"
      tooltip="标题含有内容关键词，并完整概括视频内容，可以帮助系统推荐。"
      field="name"
      label="标题"
      required
      feedback
    >
      <a-input
        v-model="form.name"
        placeholder="输入5-30个字符，标题含有关键词，可以被更多人看到哦"
      />
    </a-form-item>
    <a-form-item maxLength="6" minLength="2" field="talk" label="话题" feedback>
      <a-input v-model="form.talk" placeholder="输入合适的话题" />
    </a-form-item>
    <a-form-item
      maxLength="6"
      minLength="2"
      field="image"
      label="上传封面"
      feedback
    >
      <a-upload
        action="/"
        :fileList="file ? [file] : []"
        :show-file-list="false"
        @change="onChange"
        @progress="onProgress"
      >
        <template #upload-button>
          <div
            :class="`arco-upload-list-item${
              file && file.status === 'error'
                ? ' arco-upload-list-item-error'
                : ''
            }`"
          >
            <div
              class="arco-upload-list-picture custom-upload-avatar"
              v-if="file && file.url"
            >
              <img :src="file.url" />
              <div class="arco-upload-list-picture-mask">
                <IconEdit />
              </div>
              <a-progress
                v-if="file.status === 'uploading' && file.percent < 100"
                :percent="file.percent"
                type="circle"
                size="mini"
                :style="{
                  position: 'absolute',
                  left: '50%',
                  top: '50%',
                  transform: 'translateX(-50%) translateY(-50%)',
                }"
              />
            </div>
            <div class="arco-upload-picture-card" v-else>
              <div class="arco-upload-picture-card-text">
                <IconPlus />
                <div style="margin-top: 10px; font-weight: 600">Upload</div>
              </div>
            </div>
          </div>
        </template>
      </a-upload>
    </a-form-item>
    <!--    <a-form-item-->
    <!--      field="post"-->
    <!--      label="Post"-->
    <!--      help="This is custom message"-->
    <!--      extra="This is extra text"-->
    <!--      feedback-->
    <!--    >-->
    <!--      <a-input-number v-model="form.post" />-->
    <!--    </a-form-item>-->
    <a-form-item
      field="tags"
      label="标签"
      help="正确的标签可以帮助您上热门！"
      feedback
    >
      <a-input-tag
        v-model="form.tags"
        placeholder="please enter your post..."
      />
    </a-form-item>
    <a-form-item field="tags" label="视频" feedback>
      <a-upload draggable action="/" />
    </a-form-item>
  </a-form>
</template>
<script>
import { reactive, ref } from "vue";

export default {
  setup() {
    const form = reactive({
      name: "",
      post: undefined,
      talk: undefined,
      image: undefined,
      tags: ["生活"],
      section: "",
    });

    return {
      form,
    };
  },
};
</script>
<!--<script setup lang="ts">-->
<!--import { reactive, ref } from "vue";-->
<!--const status = ref("success");-->
<!--const size = ref("medium");-->
<!--const from = reactive({-->
<!--  name: "",-->
<!--  post: undefined,-->
<!--  tags: ["tag1"],-->
<!--  section: "",-->
<!--});-->
<!--</script>-->

<style scoped></style>
