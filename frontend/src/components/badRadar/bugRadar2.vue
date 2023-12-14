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
                { name: '完整性', max: 100, axisLabel: {show: true}},
                { name: '可访问性', max: 100 },
                { name: '准确性\n(未参考)', max: 100 },
                { name: '时效性\n(未参考)', max: 100 },
                { name: '规范性\n(未参考)', max: 100 },
            ],
            splitArea: {
                areaStyle: {
                    color: 'transparent',
                }
            },
            axisName: {
                color: '#333'
            },
        },
        series: [
            {
                type: 'radar',
                data: [
                    {
                        value: [70, 60],
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

