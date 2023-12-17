<template>
    <div class="mt-4">
        <el-input
            v-model="bugName"
            placeholder="Please input a bug name"
            class="input-with-select"
            >
            <template #append>
                <el-button 
                    :icon="Search" 
                    @click="searchTopic"
                />
            </template>
        </el-input>        
        <div v-if="searched">
            <el-descriptions title="User Info">
                <el-descriptions-item label="Question Count">
                    <el-tag size="default">{{ bugQuestionCount }}</el-tag>
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
  import { ref, getCurrentInstance } from "vue";
  import axios from "axios";
  import {useToast} from "vuestic-ui";
  
  const appConfig = ref(getCurrentInstance().appContext.config.globalProperties).value;
  axios.defaults.baseURL = appConfig.$apiBaseUrl;
  const {init} = useToast();
  const avgViewCount = ref(0);
  const avgAnswerCount = ref(0);
  const totalAnswerCount = ref(0)
  const bugQuestionCount = ref(0);
  const totalScore = ref(0);
  const avgScore = ref(0);
  const searched = ref(false);
  const bugName = ref('');


  const searchTopic = () => {
    //answer
    axios.get(`/bug/avgAnswerCount/${bugName.value}`, {}, {})
    .then(response => {
        avgAnswerCount.value = response.data
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
    axios.get(`/bug/totalAnswerCount/${bugName.value}`, {}, {})
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
    axios.get(`/bug/avgViewCount/${bugName.value}`, {}, {})
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
    axios.get(`/bug/bugQuestionCount/${bugName.value}`, {}, {})
    .then(response => {
        bugQuestionCount.value = response.data
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
    axios.get(`/bug/totalScore/${bugName.value}`, {}, {})
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
    axios.get(`/bug/avgScore/${bugName.value}`, {}, {})
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
  };
</script>
  
  