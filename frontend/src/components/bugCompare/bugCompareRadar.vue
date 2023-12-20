<template>
  <div ref="evaluationDimension" style="width: 100%; height: 260px"></div>
  <!-- Fatal :{{ fatalError }}
  <br>
  syntaxError: {{ syntaxError }}
  <br>
  exception: {{ exception }} -->
</template>

  
<script setup>
import * as echarts from "echarts";
import {ref, onMounted, getCurrentInstance,watch} from 'vue';
import axios from "axios";
import {useToast} from "vuestic-ui";
const evaluationDimension = ref()
const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
axios.defaults.baseURL = appConfig.$apiBaseUrl;
const {init} = useToast();
const exception = ref([])
const fatalError = ref([]);
const syntaxError = ref([])

const getCompare = () => {
      // init( axios.defaults.baseURL+'/bugCompare/exception')
      axios.get(`/bugCompare/exception`, {}, {})
          .then(response => {
          exception.value = response.data
          // init(JSON.stringify(exception.value))
          })
          .catch(error => {
            if (error.response) {
              init({message: error.response.data.msg, color: "danger"})
            } else {
              // 一些错误是在设置请求的时候触发
              init({message: error.message, color: "danger"})

            }
          });
      axios.get(`/bugCompare/FatalError`, {}, {})
      .then(response => {
          fatalError.value = response.data
          // init(JSON.stringify(items.value))
      })
      .catch(error => {
          if (error.response) {
          init({message: error.response.data.msg, color: "danger"})
          } else {
          // 一些错误是在设置请求的时候触发
          init({message: error.message, color: "danger"})

          }
      });
      axios.get(`/bugCompare/SyntaxError`, {}, {})
      .then(response => {
          syntaxError.value = response.data
      })
      .catch(error => {
          if (error.response) {
          init({message: error.response.data.msg, color: "danger"})
          } else {
          // 一些错误是在设置请求的时候触发
          init({message: error.message, color: "danger"})

          }
      });
    };
onMounted(() => {
  getCompare()
});
watch(() => [exception.value,syntaxError.value,fatalError.value], () => {
  initDimension(exception.value, syntaxError.value,fatalError.value);
});
const initDimension = (exception, syntaxError, fatalError) => {
  // const initDimension = () => {
  var myChart = echarts.init(evaluationDimension.value);
  var option;

  option = {
    // title:{
    //   text: 'Classified Bug Radar'
    // },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      data: ['Exception', 'Syntax Error', 'Fatal Error'],
      bottom: 0,
    },
    radar: {
      radius: ["0%", "58%"],
      // shape: 'circle',
      indicator: [
        { name: 'Average View Count', max: 15000 },
        { name: 'Avarage Answer Count', max: 2 },
        { name: 'Total Answer Count', max: 1400 },
        { name: 'Question Count', max: 1000 },
        { name: 'Total Score', max: 4000 },
        { name: 'Avarage Score', max: 5 }
      ]
    },
    series: [
      {
        type: 'radar',
        data: [
          {
            value: exception,
            // value : [1,1,1,1,1,1],
            name: 'Exception',
            itemStyle: {
              color: '#77CEFF' // 设置颜色
            },
          },
          {
            value: fatalError,
            // value : [2,2,2,2,2,2],
            name: 'Fatal Error',
            itemStyle: {
              color: '#0E4180' // 设置颜色
            } 
          },
          {
            value: syntaxError,
            // value : [3,3,3,3,3,3],
            name: 'Syntax Error',
            itemStyle: {
              color: '#0079AF' // 设置颜色
            } 
          }
        ]
      }
    ]
  };
  option && myChart.setOption(option);
}

</script>

