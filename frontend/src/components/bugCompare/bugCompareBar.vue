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
      xAxis: {
        data: ['Avarage Answer Count', 'Total Answer Count', 'Avarage View Count',
         'Total View Count', 'Question Count', 'Avarage Score']
      },
      yAxis: {},
      series: [
        {
          type: 'bar',
          data: exception,
          itemStyle: {
            color: '#77CEFF' // 设置颜色
          } 
        },
        {
          type: 'bar',
          data: fatalError,
          itemStyle: {
            color: '#0079AF' // 设置颜色
          } 
        },
        {
          type: 'bar',
          data: syntaxError,
          itemStyle: {
            color: '#123E6B' // 设置颜色
          } 
        }
      ]
    };
    option && myChart.setOption(option);
  }
  
  </script>
  
  