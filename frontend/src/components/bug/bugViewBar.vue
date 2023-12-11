<template>
    <Bar
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
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
      const getBugsByView = () => {
        axios.post('/topKByViewCount/10', {}, {})
            .then(response => {
              items.value = response.data.data
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
        getBugsByView();
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
        chartOptions: {
          responsive: true
        }
      }
    }
  })
  </script>