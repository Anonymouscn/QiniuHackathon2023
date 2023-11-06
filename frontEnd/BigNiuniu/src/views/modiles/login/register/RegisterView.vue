<template>
  <div id="registerView">
    <h1 style="margin-bottom: 16px; display: flex; align-items: center">
      <img src="../../../../assets/niuniu.svg" /> 用户注册
    </h1>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item required field="账号" label="账号">
        <a-input placeholder="请输入账号" />
      </a-form-item>
      <a-form-item required field="密码" tooltip="密码不少于 8 位" label="密码">
        <a-input-password placeholder="请输入密码" />
      </a-form-item>
      <a-form-item required field="密码" tooltip="密码不少于 8 位" label="密码">
        <a-input-password placeholder="请再次输入密码" />
      </a-form-item>
      <div class="login-input">
        <a-button type="primary" html-type="submit" style="width: 120px">
          注册
        </a-button>
        <router-link to="/login">
          <a-button type="primary" html-type="submit" style="width: 120px">
            已经账号
          </a-button>
        </router-link>
      </div>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

/**
 * 表单信息
 */
const form = reactive({
  userAccount: "",
  userPassword: "",
});

const router = useRouter();
const store = useStore();

/**
 * 提交表单
 * @param data
 */
const handleSubmit = async () => {
  return null;
};
</script>
<style scoped>
#registerView {
  width: 50%;
  height: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.login-input {
  margin-top: 40px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}
</style>
