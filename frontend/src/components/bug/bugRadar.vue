<template>
    <div ref="evaluationDimension" style="width: 100%; height: 260px"></div>
</template>

<script setup>
	import * as echarts from "echarts";
	import { ref, onMounted } from "vue";
	
	const evaluationDimension = ref()
	onMounted(() => {
        initDimension()
	});
	
const initDimension = () => {
    var myChart = echarts.init(evaluationDimension.value);
    var option;

    option = {
        radar: {
            axisLine: {
                show: false
            },
            splitNumber: 5, //线的数量
            center: ['50%', '50%'],
            // 外半径占据容器大小
            radius: '75%',
            indicator: [
                { name: 'Answer Count', axisLabel: {show: true}},
                { name: 'Question Count',  axisLabel: {show: true}},
                { name: 'Score'  ,axisLabel: {show: true}},
            ],
            splitArea: {
                areaStyle: {
                    color: 'transparent',
                }
            },
            axisName: {
                color: '#333',
                formatter: (value, indicator) => {
                        // 显示维度名字
                        return indicator.name;
                    }
            },
        },
        series: [
            {
                type: 'radar',
                data: [
                    {
                        name: 'a',
                        value: [70, 60,10],
                        itemStyle: {
                            normal: {
                                color: '#47BF89',
                                lineStyle: {
                                    color: '#47BF89',
                                },
                            },
                        }
                    },
                    {
                        name: 'b',
                        value: [30, 40,50],
                        itemStyle: {
                            normal: {
                                color: '#47BF89',
                                lineStyle: {
                                    color: '#47BF89',
                                },
                            },
                        }
                    },
                    {
                        name: 'c',
                        value: [50, 60, 50],
                        itemStyle: {
                            normal: {
                                color: '#47BF89',
                                lineStyle: {
                                    color: '#47BF89',
                                },
                            },
                        }
                    }
                ]
            }
        ]
    };
    option && myChart.setOption(option);
}

</script>

