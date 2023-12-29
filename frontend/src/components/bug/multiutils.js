export const initDimension = (myChart, exception, fatalError, syntaxError) => {
    // var myChart = echarts.init(evaluationDimension.value);
    var option;
  
    option = {
      color:['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED', '#4E8BC6', '#2A66A3',
               '#0E4180', '#7FB5D8', '#8DC3E6', '#9ACFEF', '#AACFEB', '#B9D9F5', '#C6E3FD', '#D3EDFF'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data: ['Exception', 'Syntax Error', 'Fatal Error']
        },
        // toolbox: {
        //     feature: {
        //         saveAsImage: {}
        //     }
        // },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: 'Exception',
                type: 'line',
                stack: 'Total',
                areaStyle: {},
                emphasis: {
                    focus: 'series'
                },
                data: exception
            },
            {
                name: 'Syntax Error',
                type: 'line',
                stack: 'Total',
                areaStyle: {},
                emphasis: {
                    focus: 'series'
                },
                data: syntaxError
            },
            {
                name: 'Fatal Error',
                type: 'line',
                stack: 'Total',
                areaStyle: {},
                emphasis: {
                    focus: 'series'
                },
                data: fatalError
            },
        ]
    };
    option && myChart.setOption(option);
  }