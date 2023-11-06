<template>
  <div id="userLoginView">
    <h1 style="margin-bottom: 16px; display: flex; align-items: center">
      <img src="../../../assets/niuniu.svg" /> 用户登录
    </h1>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item required field="账号" label="账号">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item required field="密码" tooltip="密码不少于 8 位" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <div class="login-input">
        <router-link to="/register">
          <a-button type="primary" style="width: 120px"> 注册 </a-button>
        </router-link>
        <router-link to="/phoneLogin">
          <a-button type="primary" style="width: 120px"> 手机号登录 </a-button>
        </router-link>
        <a-button type="primary" html-type="submit" style="width: 120px">
          登录
        </a-button>
      </div>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { UserControllerService, UserLoginRequest } from "../../../../generated";

/**
 * 表单信息
 */
const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest);

const router = useRouter();
const store = useStore();

/**
 * 提交表单
 * @param data
 */
// const handleSubmit = async () => {
//   const res = await UserControllerService.userLoginUsingPost(form);
//   //判登
//   if (res.code === 0) {
//     await store.dispatch("user/getLoginUser");
//     router.push({
//       path: "/",
//       replace: true,
//     });
//   } else {
//     message.error("登入失败" + res.message);
//   }
// };
</script>
<style scoped>
#userLoginView {
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
