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
const getBugsByScore = () => {
  axios.get(`/bug/topKByAvgScore/${10}`, {}, {})
  .then(response => {
      items.value = response.data
      chartData.value = items.value.map(item => ({
          value: item.averageScore,
          name: item.bugName
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
  getBugsByScore()
});

const initDimension = (chartData) => {
  var myChart = echarts.init(evaluationDimension.value);
  var option;

  option = {
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

