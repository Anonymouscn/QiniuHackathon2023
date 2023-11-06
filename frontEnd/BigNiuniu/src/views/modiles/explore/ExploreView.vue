<template>
  <div class="container">
    <a-row :gutter="20" align="stretch">
      <a-col :span="24">
        <a-card class="general-card">
          <a-row justify="space-between">
            <a-col :span="24">
              <a-tabs :default-active-tab="1" type="rounded">
                <a-tab-pane key="1" title="全部">
                  <VideoCard />
                </a-tab-pane>
                <a-tab-pane key="2" title="编程">
                  <!--                  <QualityInspection />-->
                </a-tab-pane>
                <a-tab-pane key="3" title="科技">
                  <!--                  <TheService />-->
                </a-tab-pane>
                <a-tab-pane key="4" title="美术">
                  <!--                  <RulesPreset />-->
                </a-tab-pane>
              </a-tabs>
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup>
import VideoCard from "./components/VideoCard.vue";
</script>

<style lang="css" src="./styles/styles.css" scoped></style>
