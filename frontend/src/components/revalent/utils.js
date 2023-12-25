// 在一个共享的工具文件中，例如 utils.js
// import * as echarts from "echarts";
import 'echarts-wordcloud';

export const initECharts = (wordcloud, wordcloudData, title) => {
    let wordcloudOption = {
        title: {
            text: title,
            textStyle: {
                fontStyle: 'oblique',
                fontSize: 20,
                color: '#4cc9f0'
            },
            left: 'center'
        },
        tooltip: {},
        series: [{
            type: 'wordCloud',
            shape: {
                cloudGrow: 0.2
            },
            sizeRange: [10, 60],
            rotationRange: [-30, 30],
            gridSize: 2,
            drawOutOfBound: false,
            layoutAnimation: true,
            keepAspect: true,
            textStyle: {
                fontWeight: 'bold',
                color: function () {
                    return 'rgb(' + [
                        Math.round(Math.random() * 160),
                        Math.round(Math.random() * 160),
                        Math.round(Math.random() * 160)
                    ].join(',') + ')';
                }
            },
            emphasis: {
                textStyle: {
                    shadowBlur: 15,
                    shadowColor: '#333'
                }
            },
            data: wordcloudData.sort(function (a, b) {
                return b.value - a.value;
            })
        }]
    };
    wordcloud.setOption(wordcloudOption);
};