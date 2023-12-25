<template>
  <div ref="singleBugAnswer" style="width: 100%; height: 270px"></div>
      <br>
  <!-- <div v-if="items!=''">
    <div ref="singleBugAnswer" style="width: 100%; height: 270px"></div>
      <br>
  </div> -->
</template>
  
<script>
import {ref, defineComponent, onMounted, getCurrentInstance, watch, defineProps} from 'vue';
import axios from "axios";
import {useToast} from "vuestic-ui";
export default defineComponent({
  props:{
    type: String,
    kIn: Number,
  },
})
</script>
  
<script setup>
import * as echarts from "echarts";
const singleBugAnswer = ref()
const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
axios.defaults.baseURL = appConfig.$apiBaseUrl;
const {init} = useToast();
const items = ref([]);
const chartData = ref([]);
const props = defineProps(['kIn','type']);
var myChart;
const getBugsByAnswers = () => {
  if(props.type!==''&& props.type!==undefined&& props.type!==null){
    axios.get(`/${props['type']}/topKByAnswerCount/${props['kIn']}`, {}, {})
    .then(response => {
        items.value = response.data
        chartData.value = items.value.map(item => ({
            value: item.averageAnswerCount,
            name: item.bugName
        }));
        // init(JSON.stringify(chartData.value))
        initDimension(myChart,chartData.value)
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
  }
  
};
onMounted(() => {
  myChart = echarts.init(singleBugAnswer.value);
  getBugsByAnswers()
});
watch(() => [props['type'], props['kIn']], () => {
  getBugsByAnswers();
});

const initDimension = (myChart,chartData) => {
  // if (myChart && myChart.dispose) {
  //   myChart.dispose();
  // }
  // var myChart = echarts.init(singleBugAnswer.value);
  var option;

  option = {
    color:['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED', '#4E8BC6', '#2A66A3',
             '#0E4180', '#7FB5D8', '#8DC3E6', '#9ACFEF', '#AACFEB', '#B9D9F5', '#C6E3FD', '#D3EDFF'],
  tooltip: {
      trigger: 'item'
  },
  legend: {
      type: 'scroll',
      orient: 'vertical',
      top: 220,
      // left: 10,
      // top: '5%',
      left: 'center'
  },
  series: [
      {
      name: 'Average Answer Count',
      type: 'pie',
      radius: ['40%', '60%'],
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
