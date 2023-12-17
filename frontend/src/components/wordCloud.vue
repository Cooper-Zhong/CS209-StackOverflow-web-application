<template>
    <div style="width: 100%;height: 60vh;display: flex; justify-content: center; align-items: center;">
        <div style="width: 100%;height: 100%;">
            <div id="wordcloud" style="height: 100%;width: 100%"></div>
        </div>
    </div>
</template>
  
  <script setup>
  import * as echarts from "echarts";
  import { onMounted } from "vue";
  import 'echarts-wordcloud';
  
  onMounted(() => {
        initDimension()
  });
  
  const initDimension = () => {
    let wordcloud = echarts.init(document.getElementById('wordcloud'));
    let wordcloudData = [];
    let wordcloudRaw = {"rust-cargo": 1822, "borrow-checker": 1597, "lifetime": 1593, "traits": 1541, "generics": 979, "rust-tokio": 940, "iterator": 804, "serde": 775, "multithreading": 606, "string": 552, "closures": 528, "reference": 508, "macros": 506, "ffi": 500, "struct": 495, "vector": 480, "ownership": 452, "actix-web": 430, "enums": 428, "arrays": 405, "types": 381, "asynchronous": 350, "rust-diesel": 350, "webassembly": 337, "async-await": 332, "json": 303, "windows": 302, "c": 290, "rust-macros": 288}
    for (const key in wordcloudRaw) {
        wordcloudData.push({
            name: key,
            value: wordcloudRaw[key]
        });
    }
    let wordcloudOption = {
        title: {
            text: 'Related Tag WordCloud',
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

  }
  
  </script>
  
  