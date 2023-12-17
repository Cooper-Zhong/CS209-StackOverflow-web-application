<template>
  <v-chart class="chart" :options="chartOptions" />
</template>

<script>
import { use } from 'echarts/core';
import { RadarChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';
import VueECharts from 'vue-echarts';
import { THEME_KEY } from 'vue-echarts';
import { onMounted,ref,provide } from 'vue';

use([TitleComponent, TooltipComponent, LegendComponent, RadarChart, CanvasRenderer]);
provide(THEME_KEY, 'dark');


export default {
  components: {
    'v-chart': VueECharts,
  },
  data() {
    return {
    };
  },
  setup(){
    const datas = [
        { Name: '学习动机', TotalScore: 10, Score: 7.6, AvgScore: 7.2 },
        { Name: '学习兴趣', TotalScore: 10, Score: 7.1, AvgScore: 6.3 },
        { Name: '情绪情感', TotalScore: 10, Score: 5.6, AvgScore: 5.8 },
        { Name: '学习毅力', TotalScore: 10, Score: 8.1, AvgScore: 6.8 },
        { Name: '自我认知', TotalScore: 10, Score: 7.7, AvgScore: 6.5 }
      ];
    const colorList= ['#36A87A', '#3f76f2'];
    const aveList=ref([]);
    const uList = ref([]);
    const nameList=ref([]);
    const chartOptions= ref({});
    const initChart =()=> {
      aveList.value = datas.map((n) => n.AvgScore);
      uList.value = datas.map((n) => n.Score);
      datas.forEach((item) => {
        nameList.value.push({
          name: item.Name,
          max: 10,
          AvgScore: item.AvgScore,
          Score: item.Score
        });
      });

      chartOptions.value = {
        title: {
          text: '综合得分：71分',
          left: 'center'
        },
        legend: {
          data: ['你的得分', '平均得分'],
          left: 'center',
          top: 'bottom',
          itemGap: 60,
          textStyle: {
            fontSize: 14
          }
        },
        radar: {
          center: ['50%', '55%'],
          radius: '65%',
          axisLine: {
            lineStyle: {
              color: '#999',
              fontSize: 30
            }
          },
          indicator: nameList.value,
          splitArea: {
            areaStyle: {
              color: '#fff'
            }
          },
          name: {
            lineHeight: 18,
            formatter: (labelName, raw) => {
              const { AvgScore, Score } = raw;
              return (
                labelName + '\n' + `{score|${Score}}` + '/' + `{avg|${AvgScore}}`
              );
            },
            rich: {
              score: {
                color: colorList[0],
                fontSize: 16
              },
              avg: {
                color: colorList[1],
                fontSize: 16
              }
            }
          }
        },
        series: [
          {
            type: 'radar',
            data: [
              {
                value: uList.value,
                name: '你的得分',
                itemStyle: {
                  color: colorList[0],
                },
                label: {
                  show: false,
                  fontSize: 16,
                  position: 'right',
                  color: colorList[1],
                  formatter: function (params) {
                    return params.value;
                  }
                },
                areaStyle: {
                  color: colorList[1],
                  opacity: 0.2
                }
              },
              {
                value: aveList.value,
                name: '平均得分',
                itemStyle: {
                  color: colorList[1]
                },
                label: {
                  show: false,
                  fontSize: 16,
                  position: 'left',
                  color: colorList[1],
                  formatter: function (params) {
                    return params.value;
                  }
                },
                areaStyle: {
                  color: colorList[1],
                  opacity: 0.2
                }
              }
            ]
          }
        ]
      };
      alert(JSON.stringify(chartOptions))
    }
    onMounted(() => {
      initChart();
    });
    return{
      datas,
      colorList,
      aveList,
      uList,
      nameList,
      chartOptions,
    }
  },
};
</script>

<style scoped>
/* Your styles can be added here */
</style>
