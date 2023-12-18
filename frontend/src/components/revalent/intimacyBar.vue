<template>
  <!-- {{ topicIn }}
  {{ topic }}
  {{ searched }} -->
    <Bar
        id="my-chart-id"
        :options="chartOptions"
        :data="{
          labels: items.map(item=>item.intimate_tag),
          datasets: [ { 
            data: items.map(item => item.intimacy),
            backgroundColor: ['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED', '#4E8BC6', '#2A66A3',
             '#0E4180', '#7FB5D8', '#8DC3E6', '#9ACFEF', '#AACFEB', '#B9D9F5', '#C6E3FD', '#D3EDFF']
          } ]
        }"
    />
  </template>
  
  <script>
  import { Bar } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
  
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
  
  import {ref, defineComponent, onMounted, getCurrentInstance, watch} from 'vue';
  import axios from "axios";
  import {useToast} from "vuestic-ui";
  export default defineComponent({
    name: 'BarChart',
    props: {
      topicIn: String, // 声明 topicIn 为字符串类型
      searched: Boolean,
      kIn : Number,
    },
    components: { Bar },
    setup(props){
      const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
      axios.defaults.baseURL = appConfig.$apiBaseUrl;
      const {init} = useToast();
      const items = ref([]);
      const topic = ref('java')
      const k = ref(10)
      const getIntimacy = () => {
        if(props.searched) {
          topic.value=props.topicIn;
          k.value = props.kIn;
        }
        axios.get('/topic/intimacy', {
            params: {
                k: k.value,    // 可选
                topic: topic.value  // 可选
            }
            }, {})
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
      // 使用 watch 函数监测 props 的变化
      watch(() => [props.topicIn, props.searched, props.kIn], () => {
        getIntimacy();
      });
      onMounted(() => {
        getIntimacy();
      });
      return{
        items,
        topic,
      };
    },
    data() {
      return {
        chartData: {
          labels: [ 'January', 'February', 'March' ],
          datasets: [ { data: [40, 20, 12] } ]
        },
        chartOptions: {
          responsive: true,
          plugins: {
              legend: {
                display:false,
              },
            },
        }
      }
    }
  })
  </script>