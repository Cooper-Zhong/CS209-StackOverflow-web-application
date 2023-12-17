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

    <div>
  <div class="task">
      <div class="username">Topic Popularity</div>
      <br>
      <div class="mt-4">
    <el-input
      v-model="inputTopic"
      placeholder="Please input topic"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="Select" style="width: 115px">
          <el-option label="Question Number" value="1" />
          <el-option label="Answer Number" value="2" />
          <el-option label="Average Score" value="3" />
          <el-option label="View" value="4" />
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" />
      </template>
    </el-input>
  </div>
      <topic-answer-bar></topic-answer-bar>
      <p class="text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. In massa ipsum, laoreet quis
         mollis nec, feugiat in dui. Suspendisse et enim pretium, ullamcorper enim laoreet.</p>
  </div>

  <div class="task">
      <div class="username">Bug Popularity</div>
      <br>
      <div class="mt-4">
    <el-input
      v-model="inputBug"
      placeholder="Please input bug"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="Select" style="width: 115px">
          <el-option label="Question Number" value="1" />
          <el-option label="Answer Number" value="2" />
          <el-option label="Average Score" value="3" />
          <el-option label="View" value="4" />
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" />
      </template>
    </el-input>
  </div>
      <topic-answer-bar></topic-answer-bar>
      <p class="text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. In massa ipsum, laoreet quis
         mollis nec, feugiat in dui. Suspendisse et enim pretium, ullamcorper enim laoreet.</p>
  </div>
</div>
  <el-input
      v-model="inputBug"
      placeholder="Please input bug"
      class="input-with-select"
    >
      <template #prepend>
        <el-select v-model="select" placeholder="Select" style="width: 115px">
          <el-option label="Question Number" value="1" />
          <el-option label="Answer Number" value="2" />
          <el-option label="Average Score" value="3" />
          <el-option label="View" value="4" />
        </el-select>
      </template>
      <template #append>
        <el-button :icon="Search" />
      </template>
    </el-input>


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
  <!-- <word-cloud></word-cloud> -->
  <bug-answer-pie></bug-answer-pie>
  <bug-question-pie></bug-question-pie>
  <bug-score-pie></bug-score-pie>
  <bug-view-pie></bug-view-pie>

  <!-- <test-echart-pie></test-echart-pie> -->
  <!-- <word-cloud></word-cloud> -->
  <similar-topic></similar-topic>
  <intimacy-topic></intimacy-topic>
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
import similarTopic from './revalent/similarTopic.vue';
import intimacyTopic from './revalent/intimacyTopic.vue';
// import inputPart from './inputPart.vue';
// import wordCloud from './wordCloud.vue';

export default {
  name: 'mainPage',
  components: {
    // inputPart,

    introductionPage,
    topicAnswerBar,
    topicQuestionBar,
    topicScoreBar,
    topicViewBar,

    topicViewPie,
    topicAnswerPie,
    topicQuestionPie,
    topicScorePie,

    // wordCloud,
    similarTopic,
    intimacyTopic,


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
<script setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
const inputTopic = ref('');
const select = ref('');
const inputBug = ref('');
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


.task {
  margin: 5%;
  position: relative;
  color: #2e2e2f;
  /* cursor: move; */
  background-color: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: rgba(99, 99, 99, 0.1) 0px 2px 8px 0px;
  margin-bottom: 1rem;
  border: 3px dashed transparent;
  max-width: 350px;
}

.task:hover {
  box-shadow: rgba(99, 99, 99, 0.3) 0px 2px 8px 0px;
  /* border-color: rgba(162, 179, 207, 0.2) !important; */
}

.task p {
  font-size: 15px;
  margin: 1.2rem 0;
}

.tag {
  border-radius: 100px;
  padding: 4px 13px;
  font-size: 12px;
  color: #ffffff;
  background-color: #1389eb;
}

.tags {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.options {
  background: transparent;
  border: 0;
  color: #c4cad3;
  font-size: 17px;
}

.options svg {
  fill: #9fa4aa;
  width: 20px;
}

.stats {
  position: relative;
  width: 100%;
  color: #9fa4aa;
  font-size: 12px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.stats div {
  margin-right: 1rem;
  height: 20px;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.stats svg {
  margin-right: 5px;
  height: 100%;
  stroke: #9fa4aa;
}

.viewer span {
  height: 30px;
  width: 30px;
  background-color: rgb(28, 117, 219);
  margin-right: -10px;
  border-radius: 50%;
  border: 1px solid #fff;
  display: grid;
  align-items: center;
  text-align: center;
  font-weight: bold;
  color: #fff;
  padding: 2px;
}

.viewer span svg {
  stroke: #fff;
}

.username {
  color: #4fadd6;
  font-size: 2em;
  font-weight: 600;
}







#introduction{
  margin-top: 60px;
  margin-bottom: 100px;
}
</style>