<template>
  <a-row class="grid-demo">
    <a-col :xs="4" :sm="4" :md="6" :lg="8" :xl="10" :xxl="8"> 1 </a-col>
    <a-col :xs="12" :sm="16" :md="12" :lg="8" :xl="4" :xxl="8">
      <a-input-search size="large" placeholder="搜索热点" />
    </a-col>
    <div class="header-input">
      <a-button class="avatar" type="primary" shape="circle" size="lage">
        <router-link to="/upload">
          <img
            src="../assets/images/pushFile.svg"
            style="width: 40px; height: 40px"
          />
        </router-link>
      </a-button>
      <a-button class="avatar" shape="circle" size="lage">
        <span class="mg-num">99+</span>
        <img
          src="../assets/images/xiaoxi.png"
          style="width: 40px; height: 40px"
        />
      </a-button>
      <a-dropdown class="avatar" trigger="click">
        <a-avatar
          :size="42"
          :style="{ marginRight: '30px', cursor: 'pointer' }"
        >
          <img
            alt="avatar"
            src="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"
          />
        </a-avatar>
        <template #content>
          <a-doption>
            <a-space @click="$router.push({ name: 'Info' })">
              <icon-user />
              <span> 用户中心 </span>
            </a-space>
          </a-doption>
          <a-doption>
            <a-space @click="$router.push({ name: 'Setting' })">
              <icon-settings />
              <span> 用户设置 </span>
            </a-space>
          </a-doption>
          <a-doption>
            <a-space @click="handleLogout">
              <icon-export />
              <span> 退出登录 </span>
            </a-space>
          </a-doption>
        </template>
      </a-dropdown>
    </div>
  </a-row>
</template>

<style scoped>
.a-button {
  background-color: transparent !important;
  border: none !important;
}
.grid-demo .arco-col {
  height: 64px;
  line-height: 68px;
  color: var(--color-white);
  text-align: center;
}
/*.grid-demo .arco-col:nth-child(2n) {*/
/*  background-color: #19cda5;*/
/*}*/
.grid-demo .arco-col:nth-child(2n + 1) {
  display: flex;
  justify-content: flex-end;
  /*background-color: var(--color-primary-light-4);*/
}

.avatar {
  position: relative;
  margin-right: 40px;
  background-color: transparent !important;
  border: none !important;
}
.mg-num {
  position: absolute;
  bottom: -7px;
  right: 0px;
  font-weight: 600;
  color: #d40707;
}
.magges {
  background-color: transparent !important;
  border: none !important;
}
.header-input {
  width: 33%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: end;
  height: 5vh;
}
</style>
