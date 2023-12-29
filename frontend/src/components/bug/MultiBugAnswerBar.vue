<template>
    <div ref="evaluationDimension" style="width: 100%; height: 270px"></div>
  </template>
    
  <script>
  import {ref, defineComponent, onMounted, getCurrentInstance, watch, defineProps} from 'vue';
  import axios from "axios";
  import { initDimension } from "./multiutils.js"; 
  import {useToast} from "vuestic-ui";
  export default defineComponent({
    props:{
      kIn: Number,
    },
  })
  </script>
  
  <script setup>
  import * as echarts from "echarts";
  const props = defineProps(['kIn']);
  
  const evaluationDimension = ref()
  const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
  axios.defaults.baseURL = appConfig.$apiBaseUrl;
  const {init} = useToast();
  const items = ref([]);
  const items_syntaxerror = ref([]);
  const items_fatalerror = ref([]);
  const exception = ref([]);
  const fatalError = ref([]);
  const syntaxError = ref([]);

  var myChart;
  const getBugsByView = () => {
    axios.get(`/exception/topKByAnswerCount/8`, {}, {})
    .then(response => {
        items.value = response.data
        exception.value = items.value.map(item=>(item.averageAnswerCount))
        axios.get(`/fatalError/topKByAnswerCount/8`, {}, {})
        .then(response => {
            items_fatalerror.value = response.data
            fatalError.value = items_fatalerror.value.map(item=>(item.averageAnswerCount))
            axios.get(`/syntaxError/topKByAnswerCount/8`, {}, {})
            .then(response => {
                items_syntaxerror.value = response.data
                syntaxError.value = items_syntaxerror.value.map(item=>(item.averageAnswerCount))
                initDimension(myChart, exception.value, syntaxError.value, fatalError.value)
                // alert(JSON.stringify(syntaxError.value))
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
    myChart = echarts.init(evaluationDimension.value);
    getBugsByView()
  });
  watch(() => [props['kIn']], () => {
    getBugsByView();
  });

  
  </script>
  
  