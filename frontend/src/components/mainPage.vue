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
  <topic-question-bar></topic-question-bar>
  <topic-score-bar></topic-score-bar>
  <topic-view-bar></topic-view-bar>
  
  <bug-answer-bar></bug-answer-bar>
  <bug-question-bar></bug-question-bar>
  <bug-score-bar></bug-score-bar>
  <bug-view-bar></bug-view-bar>

  <topic-view-pie></topic-view-pie>
  <topic-answer-pie></topic-answer-pie>
  <topic-question-pie></topic-question-pie>
  <topic-score-pie></topic-score-pie>

  <bug-answer-pie></bug-answer-pie>
  <bug-question-pie></bug-question-pie>
  <bug-score-pie></bug-score-pie>
  <bug-view-pie></bug-view-pie>

  <!-- <test-echart-pie></test-echart-pie> -->
</template>

<script>
import introductionPage from './introductionPage.vue'
import topicAnswerBar from "@/components/topic/topicAnswerBar.vue";
import topicQuestionBar from './topic/topicQuestionBar.vue';
import topicScoreBar from './topic/topicScoreBar.vue';
import topicViewBar from './topic/topicViewBar.vue';
import bugAnswerBar from './bug/bugAnswerBar.vue';
import bugQuestionBar from './bug/bugQuestionBar.vue';
import bugScoreBar from './bug/bugScoreBar.vue';
import bugViewBar from './bug/bugViewBar.vue';

import topicViewPie from './topic/topicViewPie.vue';
import topicAnswerPie from './topic/topicAnswerPie.vue';
import topicQuestionPie from './topic/topicQuestionPie.vue';
import topicScorePie from './topic/topicScorePie.vue';

import bugAnswerPie from './bug/bugAnswerPie.vue';
import bugQuestionPie from './bug/bugQuestionPie.vue';
import bugScorePie from './bug/bugScorePie.vue';
import bugViewPie from './bug/bugViewPie.vue';
// import testEchartPie from "@/components/testEchartPie.vue";

export default {
  name: 'mainPage',
  components: {
    introductionPage,
    topicAnswerBar,
    topicQuestionBar,
    topicScoreBar,
    topicViewBar,

    topicViewPie,
    topicAnswerPie,
    topicQuestionPie,
    topicScorePie,


    // testEchartPie,


    bugAnswerBar,
    bugQuestionBar,
    bugScoreBar,
    bugViewBar,

    bugAnswerPie,
    bugQuestionPie,
    bugScorePie,
    bugViewPie,
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