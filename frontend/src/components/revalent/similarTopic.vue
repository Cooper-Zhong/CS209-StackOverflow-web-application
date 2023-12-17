<template>
    <div style="width: 100%;height: 60vh;display: flex; justify-content: center; align-items: center;">
        <div style="width: 100%;height: 100%;">
            <div id="wordcloudSimilar" style="height: 100%;width: 100%"></div>
        </div>
    </div>
</template>

<script setup>
    // import * as echarts from "echarts";
    // import 'echarts-wordcloud';

    import { initECharts } from "./utils.js"; 
    import { ref, onMounted, getCurrentInstance } from "vue";
    import axios from "axios";
    import {useToast} from "vuestic-ui";

    const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
    axios.defaults.baseURL = appConfig.$apiBaseUrl;
    const {init} = useToast();
    const items = ref([]);
    const chartData = ref([]);
    const getSimilar = () => {
        axios.get(`/topic/similar`, {
            params: {
                k: 10,      // 可选
                topic: 'java'  // 可选
            }
        }, {})
        .then(response => {
            items.value = response.data
            // init(JSON.stringify(items.value))
            chartData.value = items.value.map(item => ({
                name: item.tagName,
                value: item.similarity,
            }));
            // init(JSON.stringify(chartData.value))
            initECharts('wordcloudSimilar',chartData.value,'Related Tag Defined By Similarity WordCloud')
            // initDimension(chartData.value)
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
        getSimilar()
    });
    
    // const initDimension = (wordcloudData) => {
    //     let wordcloudSimilar = echarts.init(document.getElementById('wordcloudSimilar'));
    //     let wordcloudOption = {
    //         title: {
    //             text: 'Related Tag Defined By Similar WordCloud',
    //             textStyle: {
    //                 fontStyle: 'oblique',
    //                 fontSize: 20,
    //                 color: '#4cc9f0'
    //             },
    //             left: 'center'
    //         },
    //         tooltip: {},
    //         series: [{
    //             type: 'wordCloud',
    //             shape: {
    //             cloudGrow: 0.2
    //             },
    //             sizeRange: [10, 60],
    //             rotationRange: [-30, 30],
    //             gridSize: 2,
    //             drawOutOfBound: false,
    //             layoutAnimation: true,
    //             keepAspect: true,
    //             textStyle: {
    //                 fontWeight: 'bold',
    //                 color: function () {
    //                     return 'rgb(' + [
    //                         Math.round(Math.random() * 160),
    //                         Math.round(Math.random() * 160),
    //                         Math.round(Math.random() * 160)
    //                     ].join(',') + ')';
    //                 }
    //             },
    //             emphasis: {
    //                 textStyle: {
    //                     shadowBlur: 15,
    //                     shadowColor: '#333'
    //                 }
    //             },
    //             data: wordcloudData.sort(function (a, b) {
    //                 return b.value - a.value;
    //             })
    //         }]
    //     };
    //     wordcloudSimilar.setOption(wordcloudOption);

    // }
  </script>
  