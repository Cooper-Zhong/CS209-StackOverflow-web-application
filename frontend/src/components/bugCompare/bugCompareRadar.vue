<template>
  <div ref="evaluationDimension" style="width: 100%; height: 270px"></div>
</template>

  
<script setup>
import * as echarts from "echarts";
import {ref, onMounted, getCurrentInstance} from 'vue';
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
      init( axios.defaults.baseURL+'/bugCompare/exception')
      axios.get(`/bugCompare/exception`, {}, {})
          .then(response => {
          exception.value = response.data
          init(JSON.stringify(exception.value))
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
          initDimension(exception.value, fatalError.value,syntaxError.value);
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

const initDimension = (exception, syntaxError, fatalError) => {
  var myChart = echarts.init(evaluationDimension.value);
  var option;

  option = {
    // title:{
    //   text: 'Classified Bug Radar'
    // },
    legend: {
      data: ['Exception', 'Syntax Error', 'Fatal Error']
    },
    radar: {
      // shape: 'circle',
      indicator: [
        { name: 'Avarage Answer Count', max: 6500 },
        { name: 'Total Answer Count', max: 16000 },
        { name: 'Avarage View Count', max: 30000 },
        { name: 'Total View Count', max: 38000 },
        { name: 'Question Count', max: 52000 },
        { name: 'Avarage Score', max: 200 }
      ]
    },
    series: [
      {
        type: 'radar',
        data: [
          {
            value: exception,
            name: 'Exception'
          },
          {
            value: fatalError,
            name: 'Syntax Error'
          },
          {
            value: syntaxError,
            name: 'Fatal Error'
          }
        ]
      }
    ]
  };
  option && myChart.setOption(option);
}

</script>

