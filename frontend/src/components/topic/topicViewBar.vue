<template>
    <Bar
        id="my-chart-id"
        :data="{
          labels: items.map(item=>item.tagName),
          datasets: [ { 
            label: '???????',
            data: items.map(item => item.average_view_count),
            backgroundColor: ['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED','#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED']
           } ],
        }"
        :options="{
          plugins: {
            legend: {
              display:false,
              position: 'bottom', // 设置图例位置（top, bottom, left, right）
              labels: {
                color: 'red', // 设置标签颜色
                fontSize: 16, // 设置标签字体大小
              },
            },
          },
        }"
    />
  </template>
  
  <script>
  import { Bar } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
  
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
  
  import {ref, defineComponent, onMounted, getCurrentInstance} from 'vue';
  import axios from "axios";
  import {useToast} from "vuestic-ui";
  export default defineComponent({
    name: 'BarChart',
    components: { Bar },
    setup(){
      const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
      axios.defaults.baseURL = appConfig.$apiBaseUrl;
      const {init} = useToast();
      const items = ref([]);
      const getTopicsByView = () => {
        axios.get(`/topic/topKByViewCount/${10}`, {}, {})
            .then(response => {
              items.value = response.data
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
        getTopicsByView();
      });
      return{
        items,
      };
    },
    data() {
      return {
        chartData: {
          labels: [ 'January', 'February', 'March' ],
          datasets: [ { data: [40, 20, 12] } ]
        },
      }
    }
  })
  </script>