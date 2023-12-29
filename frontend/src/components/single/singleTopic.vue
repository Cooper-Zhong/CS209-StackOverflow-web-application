<template>
    <div class="mt-4">
        <el-input
            v-model="topic"
            placeholder="Please input a topic"
            class="input-with-select"
            >
            <template #append>
                <el-button 
                    :icon="Search" 
                    @click="searchTopic"
                />
            </template>
        </el-input>
        <br>
        <br>
        <div v-if="searched">
            <el-descriptions title="User Info">
                <el-descriptions-item label="Question Count">
                    <el-tag size="default">{{ topicQuestionCount }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="Average Qustion Count">
                    <el-tag size="default">{{ avgAnswerCount }} , ({{ totalAnswerCount }} in total)</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="Average Score">
                    <el-tag size="default">{{ avgScore }} , ({{ totalScore }} in total)</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="Average View">
                    <el-tag size="default">{{ avgViewCount }}</el-tag>
                </el-descriptions-item>
            </el-descriptions>
        </div>
    </div>
</template>
    
  <script setup>
  import { Search } from '@element-plus/icons-vue'
  import { ElNotification } from 'element-plus'
  import { ref, getCurrentInstance } from "vue";
  import axios from "axios";
  import {useToast} from "vuestic-ui";
  
  const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
  axios.defaults.baseURL = appConfig.$apiBaseUrl;
  const {init} = useToast();
  const avgViewCount = ref(0);
  const avgAnswerCount = ref(0);
  const totalAnswerCount = ref(0)
  const topicQuestionCount = ref(0);
  const totalScore = ref(0);
  const avgScore = ref(0);
  const searched = ref(false);
  const topic = ref('');


  const searchTopic = () => {
    if(topic.value===''||topic.value === null || topic.value === undefined){
        ElNotification({
            title: 'Error',
            message: 'You should input at least one letter.',
            type: 'error',
        })
    }
    else{
        //answer
        axios.get(`/topic/avgAnswerCount/${topic.value}`, {}, {})
        .then(response => {
            avgAnswerCount.value = response.data
            // init(JSON.stringify(response))
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
        //total answer
        axios.get(`/topic/totalAnswerCount/${topic.value}`, {}, {})
        .then(response => {
            totalAnswerCount.value = response.data
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
        //view
        axios.get(`/topic/avgViewCount/${topic.value}`, {}, {})
        .then(response => {
            avgViewCount.value = response.data
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
        //total question
        axios.get(`/topic/topicQuestionCount/${topic.value}`, {}, {})
        .then(response => {
            topicQuestionCount.value = response.data
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
        //total score
        axios.get(`/topic/totalScore/${topic.value}`, {}, {})
        .then(response => {
            totalScore.value = response.data
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
        //avg score
        axios.get(`/topic/avgScore/${topic.value}`, {}, {})
        .then(response => {
            avgScore.value = response.data
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

        searched.value=true;
    }
  };
</script>
  
  