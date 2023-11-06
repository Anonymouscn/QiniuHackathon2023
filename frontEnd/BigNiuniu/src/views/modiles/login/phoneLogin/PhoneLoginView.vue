<template>
  <div id="registerView">
    <h1 style="margin-bottom: 16px; display: flex; align-items: center">
      <img src="../../../../assets/niuniu.svg" /> 用户登入
    </h1>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
    >
      <a-form-item field="手机号" label="账号">
        <a-input v-model="phoneNumber" placeholder="请输入手机号"></a-input>
        <a-button
          type="primary"
          @click="sendVerificationCode"
          :disabled="isSendingCode"
          >{{ buttonText }}</a-button
        >
      </a-form-item>
      <a-form-item style="display: flex; flex-direction: row">
        <a-button type="primary" html-type="submit" style="width: 120px">
          登录
        </a-button>
        <router-link style="margin-left: 40px" to="/login">
          <a-button type="primary" html-type="submit" style="width: 120px">
            已经账号
          </a-button>
        </router-link>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import axios from "axios";
import message from "@arco-design/web-vue/es/message";

export default defineComponent({
  data() {
    return {
      phoneNumber: "",
      isSendingCode: false,
      buttonText: "获取验证码",
    };
  },
  methods: {
    async sendVerificationCode() {
      // 校验手机号格式
      if (!/^1\d{10}\$/.test(this.phoneNumber)) {
        message.error("手机号格式不正确");
        return;
      }

      this.isSendingCode = true;
      this.buttonText = "发送中...";

      try {
        // 发送获取验证码的请求
        const response = await axios.post("/api/send-code", {
          phoneNumber: this.phoneNumber,
        });

        // 根据接口返回的结果进行处理，此处只做简单演示
        if (response.data.success) {
          message.success("验证码发送成功");
        } else {
          message.error("验证码发送失败");
        }
      } catch (error) {
        message.error("验证码发送失败");
      } finally {
        this.isSendingCode = false;
        this.buttonText = "获取验证码";
      }
    },
  },
});
</script>
