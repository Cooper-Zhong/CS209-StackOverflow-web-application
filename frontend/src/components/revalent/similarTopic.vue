<template>
    <div style="width: 100%;height: 60vh;display: flex; justify-content: center; align-items: center;">
        <div style="width: 100%;height: 100%;">
            <div id="wordcloudSimilar" style="height: 100%;width: 100%"></div>
        </div>
    </div>
</template>

<script>
import * as echarts from "echarts";
import { initECharts } from "./utils.js"; 
import { ElNotification } from 'element-plus'
import { ref, defineComponent, onMounted, getCurrentInstance, watch } from "vue";
import axios from "axios";
import {useToast} from "vuestic-ui";

export default defineComponent({
    props: {
      topicIn: String, // 声明 topicIn 为字符串类型
      searched: Boolean,
      kIn : Number,
    },
    setup(props){
        const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
        axios.defaults.baseURL = appConfig.$apiBaseUrl;
        const {init} = useToast();
        const items = ref([]);
        const chartData = ref([]);
        const topic = ref('java')
        const k = ref(10)
        let wordcloud;
        const getSimilar = () => {
            k.value = props.kIn;
            if(props.searched) {
                if(props.topicIn!==undefined && props.topicIn!==null && props.topicIn!==''){
                    topic.value=props.topicIn;
                }
                else{
                    ElNotification({
                        title: 'Error',
                        message: 'You should input at least one letter.',
                        type: 'error',
                    })
                }
            }
            // init('coming')
            axios.get(`/topic/similar`, {
                params: {
                    k: k.value,      // 可选
                    topic: topic.value  // 可选
                }
            }, {})
            .then(response => {
                items.value = response.data
                if(items.value==[]||items.value==''){
                    items.value=[{
                        "similarity":1,
                        "tagName":"Nothing"
                    }]
                }
                // init(JSON.stringify(items.value))
                chartData.value = items.value.map(item => ({
                    name: item.tagName,
                    value: item.similarity,
                }));
                // init(JSON.stringify(chartData.value))
                initECharts(wordcloud,chartData.value,`${topic.value} - Similar Topic WordCloud`)
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
            getSimilar();
        });
        onMounted(() => {
            wordcloud = echarts.init(document.getElementById('wordcloudSimilar'));
            getSimilar()
        });
    }
})

</script>


  