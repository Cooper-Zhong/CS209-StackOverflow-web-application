<template>
    <div ref="evaluationDimension" style="width: 100%; height: 480px"></div>
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
    initDimension()
  });
  // exception, syntaxError, fatalError
  const initDimension = () => {
    var myChart = echarts.init(evaluationDimension.value);
    var option;
  
    // option = {
    //   toolbox: {
    //     show: true,
    //   },
    //   xAxis: {
    //     data: ['Avarage Answer Count', 'Total Answer Count', 'Avarage View Count',
    //      'Total View Count', 'Question Count', 'Avarage Score']
    //   },
    //   yAxis: {},
    //   series: [
    //     {
    //       type: 'bar',
    //       data: exception,
    //       itemStyle: {
    //         color: '#77CEFF' // 设置颜色
    //       } 
    //     },
    //     {
    //       type: 'bar',
    //       data: fatalError,
    //       itemStyle: {
    //         color: '#0079AF' // 设置颜色
    //       } 
    //     },
    //     {
    //       type: 'bar',
    //       data: syntaxError,
    //       itemStyle: {
    //         color: '#123E6B' // 设置颜色
    //       } 
    //     }
    //   ]
    // };
    option = {
    legend: {},
    tooltip: {},
    dataset: {
      source: [
        ['bug', '1', '2', '3', '4', '5', '6'],
        ['Milk Tea', 86.5, 92.1, 85.7, 83.1,],
        ['Matcha Latte', 41.1, 30.4, 65.1, 53.3],
        ['Cheese Cocoa', 24.1, 67.2, 79.5, 86.4, 65.2, 82.5],
        ['Walnut Brownie', 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]
      ]
    },
    series: [
      {
        type: 'pie',
        radius: '20%',
        center: ['25%', '30%'],
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
        radius: '20%',
        center: ['75%', '30%'],
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
        radius: '20%',
        center: ['25%', '75%'],
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
        radius: '20%',
        center: ['25%', '75%'],
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
        radius: '20%',
        center: ['75%', '75%'],
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
        radius: '20%',
        center: ['75%', '75%'],
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
  
  