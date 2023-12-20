<template>
    <div ref="evaluationDimension" style="width: 100%; height: 480px"></div>
  </template>
  
    
  <script setup>
  import * as echarts from "echarts";
  import {ref, onMounted, getCurrentInstance, watch} from 'vue';
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
            exception.value.unshift('Exception')
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
      axios.get(`/bugCompare/SyntaxError`, {}, {})
        .then(response => {
            syntaxError.value = response.data
            syntaxError.value.unshift('Syntax Error')
            // init(JSON.stringify(syntaxError.value))
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
            fatalError.value.unshift('Fatal Error')
            // init(JSON.stringify(fatalError.value))
            initDimension(exception.value, syntaxError.value,fatalError.value);
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
  // exception, syntaxError, fatalError
  const initDimension = (exception, syntaxError, fatalError) => {
    var myChart = echarts.init(evaluationDimension.value);
    var option;
    // exception.unshift('Exception');
    // syntaxError.unshift('Syntax Error');
    // fatalError.unshift('Fatal Error');

    // init(JSON.stringify(exception))
    // init(JSON.stringify(syntaxError))
    // init(JSON.stringify(fatalError))
    
    option = {
      color:['#77CEFF', '#0079AF', '#0E4180'],
      legend: {},
      tooltip: {},
      dataset: {
        source: [
          ['bug', '1', '2', '3', '4', '5', '6'],
          // ['Fatal Error', 14387, 1.5266666666666666, 229, 150, 684, 4.56],
          // ['Syntax Error', 1003, 0.8823529411764706, 15, 17, 2, 0.11764705882352941],
          // ['Exception', 6469, 1.4950276243093923, 1353, 905, 3918, 4.329281767955801]
          exception,
          syntaxError,
          fatalError,
        ]
      },
      series: [
        {
          type: 'pie',
          radius: '22%',
          center: ['25%', '20%'],
          encode: {
            itemName: 'bug',
            value: '1'
          },
          name: 'Average View Count',
          label: {
            show: false,
          }
          // No encode specified, by default, it is '2012'.
        },
        {
          type: 'pie',
          radius: '22%',
          center: ['75%', '20%'],
          encode: {
            itemName: 'bug',
            value: '2'
          },
          name: 'Average Answer Count',
          label: {
            show: false,
          }
        },
        {
          type: 'pie',
          radius: '22%',
          center: ['25%', '50%'],
          encode: {
            itemName: 'bug',
            value: '3'
          },
          name: 'Total Answer Count',
          label: {
            show: false,
          }
        },
        {
          type: 'pie',
          radius: '22%',
          center: ['25%', '80%'],
          encode: {
            itemName: 'bug',
            value: '4'
          },
          name: 'Total Question Count',
          label: {
            show: false,
          }
        },
        {
          type: 'pie',
          radius: '22%',
          center: ['75%', '50%'],
          encode: {
            itemName: 'bug',
            value: '5'
          },
          name: 'Total Score',
          label: {
            show: false,
          }
        },
        {
          type: 'pie',
          radius: '22%',
          center: ['75%', '80%'],
          encode: {
            itemName: 'bug',
            value: '6'
          },
          name: 'Average Score',
          label: {
            show: false,
          }
        },
      ]
    };
    option && myChart.setOption(option);
  }
  
  </script>
  
  