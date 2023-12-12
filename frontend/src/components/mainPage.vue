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
    <div class="flex-grow"></div>
  </el-menu>
  <div id="introduction">
    <introduction-page></introduction-page>
  </div>
  <div class="cards-container">
    <div class="chart_card">
      <topic-answer-bar></topic-answer-bar>
      <topic-score-bar></topic-score-bar>
      <topic-question-bar-vue></topic-question-bar-vue>
    </div>
    <div class="chart_card">
      <bug-radar></bug-radar>
    </div>
  </div>
</template>

<script>
import introductionPage from './introductionPage.vue'
import topicAnswerBar from "@/components/topic/topicAnswerBar.vue";
import bugRadar from './bug/bugRadar.vue';
import topicQuestionBarVue from './topic/topicQuestionBar.vue';
import topicScoreBar from './topic/topicScoreBar.vue';
export default {
  name: 'mainPage',
  components: {
    introductionPage,
    topicAnswerBar,
    topicQuestionBarVue,
    topicScoreBar,
    bugRadar,
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
  display: flex;
  justify-content: space-between; /* 或者其他适当的对齐方式 */
  align-items: center;
  justify-content: center;
  border-radius: 60px;
  /* background: #ebebeb; */
  box-shadow:  8px 8px 9px #b3b3b3,
               -8px -8px 9px #ffffff;
  position: relative;
  width: 80%;
  margin-left: 2%;
  margin-right: 2%;
  margin-bottom: 7%;
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
.cards-container{
  display: flex;
  flex-wrap: wrap;  /* 允许换行 */
  justify-content: space-between;  /* 或者使用其他 justify-content 属性来调整子项之间的间距 */
}
</style>