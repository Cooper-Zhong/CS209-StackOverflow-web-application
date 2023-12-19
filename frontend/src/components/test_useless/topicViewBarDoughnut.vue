<template>
  <Doughnut 
    :data="{
    labels: items.map(item => item.tagName),
    datasets: [
      {
        data: items.map(item => item.average_view_count),
        backgroundColor: ['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED', '#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED']
      }
    ]}" 
    :options="{
      responsive: true,
      maintainAspectRatio: false
    }" />
  </template>
  
<script>
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
import { Doughnut } from 'vue-chartjs'

ChartJS.register(ArcElement, Tooltip, Legend)
import {defineComponent} from 'vue';
import axios from "axios";
import {useToast} from "vuestic-ui";
export default defineComponent({
  name: 'DoughnutChart',
  components: { Doughnut },
  created() {
    axios.defaults.baseURL = this.$apiBaseUrl;
    const { init } = useToast();
    axios.get(`/topic/topKByViewCount/${10}`)
        .then(response => {
          this.items = response.data;
          // init(JSON.stringify(this.items));
        })
        .catch(error => {
          if (error.response) {
            // 请求已发出，但服务器响应的状态码不在 2xx 范围内
            init({ message: error.response.data.msg, color: 'danger' });
            // init({message: error.message, color: "danger"})
          } else {
            // 一些错误是在设置请求的时候触发
            init({ message: error.message, color: 'danger' });
          }
        });
  },
  data() {
    return {
      items:[],
    }
  }
})
</script>