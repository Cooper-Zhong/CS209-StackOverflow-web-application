<template>
    <div style="width: 100%;height: 60vh;display: flex; justify-content: center; align-items: center;">
        <div style="width: 100%;height: 100%;">
            <div id="wordcloudIntimacy" style="height: 100%;width: 100%"></div>
        </div>
    </div>
</template>

<script>
import { initECharts } from "./utils.js"; 
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
        const getIntimacy = () => {
            if(props.searched) {
                topic.value=props.topicIn;
                k.value = props.kIn;
            }
            // init('coming')
            axios.get(`/topic/intimacy`, {
                params: {
                    k: k.value,      // 可选
                    topic: topic.value  // 可选
                }
            }, {})
            .then(response => {
                items.value = response.data
                // init(JSON.stringify(items.value))
                chartData.value = items.value.map(item => ({
                    name: item.intimate_tag,
                    value: item.intimacy,
                }));
                // init(JSON.stringify(chartData.value))
                initECharts('wordcloudIntimacy',chartData.value,'Related Tag Defined By Intimacy WordCloud')
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
            getIntimacy()
        });
    }
})

</script>


  