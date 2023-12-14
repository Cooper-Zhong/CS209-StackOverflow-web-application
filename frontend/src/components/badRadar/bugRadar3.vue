<template>
    <div>
      <div id="myChart" :style="{ width: '600px', height: '400px' }"></div>
    </div>
  </template>
   
  <script>
  export default {
    name: 'Radar',
    data() {
      return {}
    },
    mounted() {
      this.drawLine()
    },
    methods: {
      drawLine() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('myChart'))
        let dataMax = [
          { name: '易用性', max: '100' },
          { name: '功能', max: '100' },
          { name: '拍照', max: '100' },
          { name: '跑分', max: '102' },
          { name: '续航', max: '100' },
        ]
        let option = {
          //配置维度的最大值
          radar: {
            name: {
              show: true,
              color: 'red',
            },
            //   雷达图的指示器，用来指定雷达图中的多个变量（维度）
            indicator: dataMax,
            shape: 'circle', //对雷达图形设置成一个圆形,可选 circle:圆形,polygon:多角形(默认)
          },
          series: [
            {
              type: 'radar',
              label: {
                show: true, //显示数值
              },
              areaStyle: {}, //每个雷达图形成一个阴影的面积
              data: [
                {
                  name: '华为手机',
                  value: [80, 90, 80, 82, 90],
                },
                {
                  name: '苹果手机',
                  value: [70, 80, 88, 62, 98],
                },
              ],
            },
          ],
        }
        // 绘制图表
        myChart.setOption(option)
      },
    },
  }
  </script>
   
  <style>
  </style>
  
  