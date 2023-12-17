<template>
  <div ref="evaluationDimension" style="width: 100%; height: 260px"></div>
</template>
  
<script setup>
import * as echarts from "echarts";
import { ref, onMounted, getCurrentInstance } from "vue";
import axios from "axios";
import {useToast} from "vuestic-ui";

const evaluationDimension = ref()
const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
axios.defaults.baseURL = appConfig.$apiBaseUrl;
const {init} = useToast();
const items = ref([]);
const chartData = ref([]);
const getTopicsByAnswers = () => {
  axios.get(`/topic/topKByAnswerCount/${10}`, {}, {})
  .then(response => {
      items.value = response.data
      chartData.value = items.value.map(item => ({
          value: item.averageAnswerCount,
          name: item.tagName
      }));
      // init(JSON.stringify(chartData.value))
      initDimension(chartData.value)
      // init(JSON.stringify(items.value))
  })
  .catch(error => {
      if (error.response) {
      // 请求已发出，但服务器响应的状态码不在 2xx 范围内
      init({message: error.response.data.msg, color: "danger"})
      // init({message: error.message, color: "danger"})
      } else {
      // 一些错误是在设置请求的时候触发
      init({message: error.message, color: "danger"})

      }
  });
};
onMounted(() => {
  getTopicsByAnswers()
});

const initDimension = (chartData) => {
  var myChart = echarts.init(evaluationDimension.value);
  var option;

  option = {
  color:['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED', '#4E8BC6', '#2A66A3',
             '#0E4180', '#7FB5D8', '#8DC3E6', '#9ACFEF', '#AACFEB', '#B9D9F5', '#C6E3FD', '#D3EDFF'],
             
  tooltip: {
      trigger: 'item'
  },
  legend: {
      top: '5%',
      left: 'center'
  },
  series: [
      {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      label: {
          show: false,
          position: 'center'
      },
      labelLine: {
          show: false
      },
      data: chartData,
      }
  ]
  };
  option && myChart.setOption(option);
}

</script>

