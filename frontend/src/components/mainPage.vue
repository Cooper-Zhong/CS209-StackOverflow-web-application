<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
  >
    <el-menu-item index="0">
      <img
          style="width: 200px"
          src="@/images/stackoverflow.svg"
          alt="StackOverflow logo"
      />
    </el-menu-item>
    <div class="flex-grow"/>
  </el-menu>
  <div id="introduction">
    <introduction-page></introduction-page>
  </div>
  <div class="chart_card">
    <topic-answer-bar></topic-answer-bar>
  </div>
  <div class="chart_card">
    <bug-radar></bug-radar>
  </div>
  <div class="chart_card">
    <bug-radar3></bug-radar3>
  </div>
  <div class="chart_card">
    <bug-radar-diagram></bug-radar-diagram>
  </div>
  <div class="chart_card">
    <pie-chart-diagram></pie-chart-diagram>
  </div>
</template>

<script>
import introductionPage from './introductionPage.vue'
import topicAnswerBar from "@/components/topic/topicAnswerBar.vue";
import bugRadar from './bug/bugRadar.vue';
import bugRadar3 from './bug/bugRadar3.vue';
import bugRadarDiagram from './bug/bugRadarDiagram.vue';
import BugRadar3 from './bug/bugRadar3.vue';
import pieChartDiagram from './bug/pieChartDiagram.vue'
export default {
  name: 'mainPage',
  components: {
    introductionPage,
    topicAnswerBar,
    bugRadar,
    bugRadar3,
    bugRadarDiagram,
    BugRadar3,

    pieChartDiagram,
},
}
</script>

<style scoped>
</style>

<style>
.flex-grow {
  flex-grow: 1;
}

.chart_card {
  position: relative;
  width: 40%;
  border-radius: 10px;
  padding: 2rem;
  color: #aaa;
  box-shadow: 0 .25rem .25rem rgba(0,0,0,0.2),
  0 0 1rem rgba(0,0,0,0.2);
  overflow: hidden;
}

.chart__card__title {
  text-align: center; /* 添加这一行，将文字左对齐 */
  margin-top: 35px;
  margin-bottom: 10px;
  font-weight: 800;
  letter-spacing: 0.01em;
  font-size: 24px;
}

.chart__card__content {
  text-align: left; /* 添加这一行，将文字左对齐 */
  margin-top: -1rem;
  opacity: 0;
  animation: ContentFadeIn .8s 1.6s forwards;
}

#introduction{
  margin-top: 60px;
  margin-bottom: 100px;
}
</style>