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
  <div class="task">
    <div class="username">Single Topic Popularity</div>
    <br>
    <single-topic></single-topic>
  </div>
  <!-- topic multiple -->
  <div class="task">
      <div class="username">Topic Popularity</div>
      <br>
      <br>
      <div class="mt-4">
        <div style="display: flex;  margin-bottom:20px">
          <div class="m-4" style="width: 50%;height: 100%">
            <p>Standard</p>
            <el-select v-model="selectTopic" placeholder="Select" style="width: 50%">
              <el-option label="Question Number" value="1" />
              <el-option label="Answer Number" value="2" />
              <el-option label="Average Score" value="3" />
              <el-option label="View" value="4" />
            </el-select>
          </div>
          <div style="width: 50%;height: 100%">
            <p>Number (1-15)</p>
            <el-input-number v-model="sizeTopic" :min="1" :max="15" />
          </div>
        </div>
      </div>
      <div v-if="selectTopic === '1'"> 
        Standard: Question Number
        <br>
        <br>
        <div style="width: 100%;display: flex">
          <div style="width: 50%;height: 100%">
            <topic-question-bar :kIn="sizeTopic"></topic-question-bar>
          </div>
          <div style="width: 50%;height: 100%">
            <topic-question-pie :kIn="sizeTopic"></topic-question-pie>
          </div>
        </div>
      </div>
      <div v-else-if="selectTopic === '2'">
        Standard: Answer Number
        <br>
        <br>
        <div style="width: 100%;display: flex">
          <div style="width: 50%;height: 100%">
            <topic-answer-bar :kIn="sizeTopic"></topic-answer-bar>
          </div>
          <div style="width: 50%;height: 100%">
            <topic-answer-pie :kIn="sizeTopic"></topic-answer-pie>
          </div>
        </div>
      </div>
      <div v-else-if="selectTopic === '3'">
        Standard: Average Score
        <br>
        <br>
        <div style="width: 100%;display: flex">
          <div style="width: 50%;height: 100%">
            <topic-score-bar :kIn="sizeTopic"></topic-score-bar>
          </div>
          <div style="width: 50%;height: 100%">
            <topic-score-pie :kIn="sizeTopic"></topic-score-pie>
          </div>
        </div>
      </div>
      <div v-else-if="selectTopic === '4'">
        Standard: View Count
        <br>
        <br>
        <div style="width: 100%;display: flex">
          <div style="width: 50%;height: 100%">
            <topic-view-bar :kIn="sizeTopic"></topic-view-bar>
          </div>
          <div style="width: 50%;height: 100%">
            <topic-view-pie :kIn="sizeTopic"></topic-view-pie>
          </div>
        </div>
      </div>
  </div>

  
  <div class="task">
    <div class="username">Single Bug Popularity</div>
    <br>
    <single-bug></single-bug>
    <br>
  </div>
  
  <!-- bug分类 -->
  <div class="task">
    <div class="username">Single Categorized Bug Popularity</div>
    <br>
    <div class="mt-4">
        <div style="display: flex;  margin-bottom:20px">
          <div class="m-4" style="width: 30%;height: 100%">
            <p>Bug Type</p>
            <el-select v-model="selectBugType" placeholder="Select" style="width: 50%">
              <el-option label="Exception" value="exception" />
              <el-option label="Syntax Error" value="syntaxError" />
              <el-option label="Fatal Error" value="fatalError" />
            </el-select>
          </div>
          <div class="m-4" style="width: 30%;height: 100%">
            <p>Standard</p>
            <el-select v-model="selectBugSingle" placeholder="Select" style="width: 50%">
              <el-option label="Question Number" value="1" />
              <el-option label="Answer Number" value="2" />
              <el-option label="Average Score" value="3" />
              <el-option label="View" value="4" />
            </el-select>
          </div>
          <div style="width: 40%;height: 100%">
            <p>Number (1-15)</p>
            <el-input-number v-model="sizeBugSingle" :min="1" :max="15" />
          </div>
        </div>
    </div>
    <div v-if="selectBugSingle === '1'"> 
      <div v-if="selectBugType===undefined||selectBugType==null||selectBugType===''">Please select a Bug Type.</div>
      <div v-else-if="selectBugType==='exception'">Bug Type: Exception    Standard: Question Number </div>
      <div v-else-if="selectBugType==='syntaxError'">Bug Type: Syntax Error    Standard: Question Number </div>
      <div v-else-if="selectBugType==='fatalError'">Bug Type: Fatal Error    Standard: Question Number </div>
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <single-bug-question-bar :type="selectBugType" :kIn="sizeBugSingle"></single-bug-question-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <single-bug-question-pie :type="selectBugType" :kIn="sizeBugSingle"></single-bug-question-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBugSingle === '2'">
      <div v-if="selectBugType===undefined||selectBugType==null||selectBugType===''">Please select a Bug Type.</div>
      <div v-else-if="selectBugType==='exception'">Bug Type: Exception  <br>  Standard: Answer Number </div>
      <div v-else-if="selectBugType==='syntaxError'">Bug Type: Syntax Error    Standard: Answer Number </div>
      <div v-else-if="selectBugType==='fatalError'">Bug Type: Fatal Error    Standard: Answer Number </div>
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <single-bug-answer-bar :type="selectBugType" :kIn="sizeBugSingle"></single-bug-answer-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <single-bug-answer-pie :type="selectBugType" :kIn="sizeBugSingle"></single-bug-answer-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBugSingle === '3'">
      <div v-if="selectBugType===undefined||selectBugType==null||selectBugType===''">Please select a Bug Type.</div>
      <div v-else-if="selectBugType==='exception'">Bug Type: Exception    Standard: Average Score </div>
      <div v-else-if="selectBugType==='syntaxError'">Bug Type: Syntax Error    Standard: Average Score </div>
      <div v-else-if="selectBugType==='fatalError'">Bug Type: Fatal Error    Standard: Average Score </div>
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <single-bug-score-bar :type="selectBugType" :kIn="sizeBugSingle"></single-bug-score-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <single-bug-score-pie :type="selectBugType" :kIn="sizeBugSingle"></single-bug-score-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBugSingle === '4'">
      <div v-if="selectBugType===undefined||selectBugType==null||selectBugType===''">Please select a Bug Type.</div>
      <div v-else-if="selectBugType==='exception'">Bug Type: Exception    Standard: View Count </div>
      <div v-else-if="selectBugType==='syntaxError'">Bug Type: Syntax Error    Standard: View Count</div>
      <div v-else-if="selectBugType==='fatalError'">Bug Type: Fatal Error    Standard: View Count</div>
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <single-bug-view-bar :type="selectBugType" :kIn="sizeBugSingle"></single-bug-view-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <single-bug-view-pie :type="selectBugType" :kIn="sizeBugSingle"></single-bug-view-pie>
        </div>
      </div>
    </div>
  </div>

  <div class="task">
    <div class="username">Categorized Bug Popularity</div>
    <br>
    <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <bug-compare-radar></bug-compare-radar>
        </div>
        <div style="width: 50%;height: 100%">
          <bug-compare-bar></bug-compare-bar>
        </div>
      </div>
  </div>

  <!-- bug multiple -->
  <div class="task">
    <div class="username">Bug Popularity</div>
    <br>
    <div class="mt-4">
        <div style="display: flex;  margin-bottom:20px">
          <div class="m-4" style="width: 50%;height: 100%">
            <p>Standard</p>
            <el-select v-model="selectBug" placeholder="Select" style="width: 50%">
              <el-option label="Question Number" value="1" />
              <el-option label="Answer Number" value="2" />
              <el-option label="Average Score" value="3" />
              <el-option label="View" value="4" />
              <el-option label="Appearance" value="5" />
            </el-select>
          </div>
          <div style="width: 50%;height: 100%">
            <p>Number (1-15)</p>
            <el-input-number v-model="sizeBug" :min="1" :max="15" />
          </div>
        </div>
    </div>
    <div v-if="selectBug === '1'"> 
      Standard: Question Number
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <bug-question-bar :kIn="sizeBug"></bug-question-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <bug-question-pie :kIn="sizeBug"></bug-question-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBug === '2'">
      Standard: Answer Number
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <bug-answer-bar :kIn="sizeBug"></bug-answer-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <bug-answer-pie :kIn="sizeBug"></bug-answer-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBug === '3'">
      Standard: Average Score
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <bug-score-bar :kIn="sizeBug"></bug-score-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <bug-score-pie :kIn="sizeBug"></bug-score-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBug === '4'">
      Standard: View Count
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <bug-view-bar :kIn="sizeBug"></bug-view-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <bug-view-pie :kIn="sizeBug"></bug-view-pie>
        </div>
      </div>
    </div>
    <div v-else-if="selectBug === '5'">
      Standard: Appearance Count
      <br>
      <br>
      <div style="width: 100%;display: flex">
        <div style="width: 50%;height: 100%">
          <bug-appearance-bar :kIn="sizeBug"></bug-appearance-bar>
        </div>
        <div style="width: 50%;height: 100%">
          <bug-appearance-pie :kIn="sizeBug"></bug-appearance-pie>
        </div>
      </div>
    </div>
  </div>


  <!-- relative topic -->
  <div class="task">
    <div class="username">Relative Topic</div>
    <br>
    <div class="mt-4">
      <div style="display: flex;  margin-bottom:20px">
          <div class="m-4" style="width: 50%;height: 100%">
            <p>Topic</p>
            <el-input
              v-model="topic"
              placeholder="Please input a topic"
              class="input-with-select"
            >
              <template #append>
                <el-button 
                    :icon="Search" 
                    @click="confirmSearch"
                />
              </template>
            </el-input>
          </div>
          <div style="width: 50%;height: 100%">
            <p>Number (1-15)</p>
            <el-input-number v-model="sizeRelative" :min="1" :max="15"/>
          </div>
        </div>
    </div>
    <br>
    <div class="tags">
      <div class="tag">Similar Topic</div>
    </div>
    <br>
    <div style="width: 100%;display: flex">
      <div style="width: 50%;height: 100%">
        <similar-topic :topicIn="topicIn" :searched="relativeSearched" :kIn="sizeRelative"></similar-topic>
      </div>
      <div style="width: 50%;height: 100%">
        <br>
        <br>
        <br>
        <similar-bar :topicIn="topicIn" :searched="relativeSearched" :kIn="sizeRelative"></similar-bar>
      </div>
    </div>
    <br>
    <div class="tags">
      <div class="tag">Intimacy Topic</div>
    </div>
    <br>
    <div style="width: 100%;display: flex">
      <div style="width: 50%;height: 100%">
        <intimacy-topic :topicIn="topicIn" :searched="relativeSearched" :kIn="sizeRelative"></intimacy-topic>
      </div>
      <div style="width: 50%;height: 100%">
        <br>
        <br>
        <br>
        <intimacy-bar :topicIn="topicIn" :searched="relativeSearched" :kIn="sizeRelative"></intimacy-bar>
      </div>
    </div>
    <br>
  </div>
</template>

<script>
import { Search } from '@element-plus/icons-vue'
import { ElNotification } from 'element-plus'

import introductionPage from './introductionPage.vue'
import topicAnswerBar from "@/components/topic/topicAnswerBar.vue";
import topicQuestionBar from './topic/topicQuestionBar.vue';
import topicScoreBar from './topic/topicScoreBar.vue';
import topicViewBar from './topic/topicViewBar.vue';
import bugAnswerBar from './bug/bugAnswerBar.vue';
import bugQuestionBar from './bug/bugQuestionBar.vue';
import bugScoreBar from './bug/bugScoreBar.vue';
import bugViewBar from './bug/bugViewBar.vue';
import bugAppearanceBar from './bug/bugAppearanceBar.vue';

import topicViewPie from './topic/topicViewPie.vue';
import topicAnswerPie from './topic/topicAnswerPie.vue';
import topicQuestionPie from './topic/topicQuestionPie.vue';
import topicScorePie from './topic/topicScorePie.vue';

import bugAnswerPie from './bug/bugAnswerPie.vue';
import bugQuestionPie from './bug/bugQuestionPie.vue';
import bugScorePie from './bug/bugScorePie.vue';
import bugViewPie from './bug/bugViewPie.vue';
import bugAppearancePie from './bug/bugAppearancePie.vue';

import similarTopic from './revalent/similarTopic.vue';
import similarBar from './revalent/similarBar.vue';
import intimacyTopic from './revalent/intimacyTopic.vue';
import intimacyBar from './revalent/intimacyBar.vue';
import singleTopic from './single/singleTopic.vue';
import singleBug from './single/singleBug.vue';

import singleBugAnswerBar from './singleBug/singleBugAnswerBar.vue';
import singleBugAnswerPie from './singleBug/singleBugAnswerPie.vue';
import singleBugQuestionBar from './singleBug/singleBugQuestionBar.vue';
import singleBugQuestionPie from './singleBug/singleBugQuestionPie.vue';
import singleBugScoreBar from './singleBug/singleBugScoreBar.vue';
import singleBugScorePie from './singleBug/singleBugScorePie.vue';
import singleBugViewBar from './singleBug/singleBugViewBar.vue';
import singleBugViewPie from './singleBug/singleBugViewPie.vue';

import bugCompareRadar from './bugCompare/bugCompareRadar.vue';
import bugCompareBar from './bugCompare/bugCompareBar.vue';

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
    similarBar,
    intimacyBar,
    intimacyTopic,


    // testEchartPie,


    bugAnswerBar,
    bugQuestionBar,
    bugScoreBar,
    bugViewBar,
    bugAppearanceBar,

    bugAnswerPie,
    bugQuestionPie,
    bugScorePie,
    bugViewPie,
    bugAppearancePie,

    singleTopic,
    singleBug,

    singleBugAnswerBar,
    singleBugAnswerPie,
    singleBugQuestionBar,
    singleBugQuestionPie,
    singleBugScoreBar,
    singleBugScorePie,
    singleBugViewBar,
    singleBugViewPie,

    bugCompareRadarVue,
    bugCompareBar,
},
}
</script>
<script setup>
import { ref } from 'vue'
// import { Search } from '@element-plus/icons-vue'
// const inputTopic = ref('');
const selectTopic = ref('');
const selectBug = ref('');

const selectBugType = ref('');
const selectBugSingle = ref('');
const sizeBugSingle = ref(10);
// const inputBug = ref('');
const sizeTopic = ref(10);
const sizeBug = ref(10);
const sizeRelative = ref(10);
const topic = ref('');
const topicIn = ref('');
const relativeSearched = ref(false);
// const options = [
//   {
//     value: 1,
//     label: '1',
//   },
//   {
//     value: 2,
//     label: '2',
//   },
//   {
//     value: 3,
//     label: '3',
//   },
//   {
//     value: 4,
//     label: '4',
//   },
//   {
//     value: 5,
//     label: '5',
//   },
//   {
//     value: 6,
//     label: '6',
//   },
//   {
//     value: 7,
//     label: '7',
//   },
//   {
//     value: 8,
//     label: '8',
//   },
//   {
//     value: 9,
//     label: '9',
//   },
//   {
//     value: 10,
//     label: '10',
//   },
// ]

const confirmSearch = () => {
  if(topic.value===''||topic.value === null || topic.value === undefined){
        ElNotification({
            title: 'Error',
            message: 'You should input at least one letter.',
            type: 'error',
        })
    }
    else{
      topicIn.value = topic.value;
      relativeSearched.value=true;
    }
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


.task {
  margin: 5%;
  /* margin-left: 10%;
  margin-right: 10%;
  margin-top: 5%;
  margin-bottom: 5%; */
  position: relative;
  color: #2e2e2f;
  /* cursor: move; */
  background-color: #fff;
  padding: 1rem;
  border-radius: 8px;
  box-shadow: rgba(115, 114, 114, 0.3) 1px 2px 8px 2px;
  margin-bottom: 1rem;
  border: 3px dashed transparent;
  max-width: 90%;
}

.task:hover {
  box-shadow: rgba(99, 99, 99, 0.1) 2px 2px 8px 8px;
  /* border-color: rgba(162, 179, 207, 0.2) !important; */
}

.task p {
  font-size: 15px;
  margin: 1.2rem 0;
}

.tag {
  border-radius: 100px;
  padding: 4px 13px;
  font-size: 16px;
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