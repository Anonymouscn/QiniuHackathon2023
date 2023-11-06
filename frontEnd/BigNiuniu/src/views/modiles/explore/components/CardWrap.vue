<template>
  <div class="card-wrap"></div>
</template>

<script lang="ts" setup>
import { ref, defineProps } from "vue";
</script>

<style scoped lang="less">
.card-wrap {
  height: 100%;
  transition: all 0.3s;
  border: 1px solid var(--color-neutral-3);
  border-radius: 4px;
  margin-top: 20px;
  &:hover {
    transform: translateY(-4px);
    // box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1);
  }
  :deep(.arco-card) {
    height: 100%;
    border-radius: 4px;
    background: cornflowerblue;
    .arco-card-body {
      height: 100%;
      .arco-space {
        width: 100%;
        height: 100%;
        .arco-space-item {
          height: 100%;
          &:last-child {
            flex: 1;
          }
          .arco-card-meta {
            height: 100%;
            display: flex;
            flex-flow: column;
            .arco-card-meta-content {
              flex: 1;
              .arco-card-meta-description {
                margin-top: 8px;
                color: rgb(var(--gray-6));
                line-height: 20px;
                font-size: 12px;
              }
            }
            .arco-card-meta-footer {
              margin-top: 0;
            }
          }
        }
      }
    }
  }
  :deep(.arco-card-meta-title) {
    display: flex;
    align-items: center;

    // To prevent the shaking
    line-height: 28px;
  }
  :deep(.arco-skeleton-line) {
    &:last-child {
      display: flex;
      justify-content: flex-end;
      margin-top: 20px;
    }
  }
}
</style>
<style>
/*.scrollbar-box {*/
/*  width: 100%;*/
/*  height: 1000px;*/
/*  background: red;*/
/*}*/
</style>
