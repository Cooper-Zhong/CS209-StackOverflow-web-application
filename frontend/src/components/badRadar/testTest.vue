<template>
  test Doughnut
  <Doughnut :data="{
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
  import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
  import { Doughnut } from 'vue-chartjs';
  ChartJS.register(ArcElement, Tooltip, Legend);
  
  export default {
  name: 'App',
  components: {
    Doughnut
  },
  data() {
    return {
      items: [
        {"tagName": "alphanumeric", "average_view_count": 1652232},
        {"tagName": "packaging", "average_view_count": 625463},
        {"tagName": "heap-dump", "average_view_count": 610778},
        {"tagName": "thread-dump", "average_view_count": 610778},
        {"tagName": "android-networking", "average_view_count": 516060},
        {"tagName": "maven-2", "average_view_count": 410870},
        {"tagName": "apk", "average_view_count": 406991},
        {"tagName": "jarsigner", "average_view_count": 406991},
        {"tagName": "uppercase", "average_view_count": 383614},
        {"tagName": "thread-local", "average_view_count": 375880},
      ],
    };
  }
  }
  </script>
  