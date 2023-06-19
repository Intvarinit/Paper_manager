<template>
  <div>
    <div id="box" class="gradient-border">
      <p>当前标签为: {{ tagName }} ,目前共有 {{ tagCount }} 篇和该标签相关的论文信息。</p>
    </div>
    <div id="myWordCloud" :style="{width:'100%', height: '700px' }" :data="wordData" />
  </div>
</template>

<script>
import echarts from 'echarts'
import 'echarts-wordcloud/dist/echarts-wordcloud'
import 'echarts-wordcloud/dist/echarts-wordcloud.min'

import tagApi from '@/api/tagManage'
export default {
  name: 'TagWordCloud',
  data() {
    return {
      msg: '论文tag信息',
      // tag数据
      wordData: [],
      tagName: '',
      tagCount: 0
    }
  },
  mounted() {
    this.initChart()
  },
  methods: {
    tagHover(param) {
      this.tagName = param.name
      tagApi.countPaperByTag(param.value).then(response => {
        this.tagCount = response.data
      })
    },
    // 获取所有tag信息并渲染到词云
    async getAllTag() {
      await tagApi.getAllTag().then(response => {
        this.wordData = response.data.map(val => {
          const ret = {}
          ret.name = val.tagname
          ret.value = val.tagID
          return ret
        })
      })
    },
    // 点击事件，可将引用量为0的词云删除
    deleteTagZero(param) {
      if (this.tagCount === 0) {
        this.$confirm('该tag当前引用量为0,此操作将删除该无用tag信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          tagApi.deleteTagById(param.value).then(response => {
            this.$message({
              type: 'success',
              message: response.message
            })
            location.reload()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
    },
    async initChart() {
      await this.getAllTag()
      this.chart = echarts.init(document.getElementById('myWordCloud'))
      // 点击事件配置
      this.chart.on('mouseover', this.tagHover)
      this.chart.on('click', this.deleteTagZero)
      const option = {
        title: {
          text: '',
          x: 'center'
        },
        backgroundColor: '#fff',
        // tooltip: {
        //   pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
        // },
        series: [
          {
            type: 'wordCloud',
            // 用来调整词之间的距离
            gridSize: 20,
            // 用来调整字的大小范围
            // Text size range which the value in data will be mapped to.
            // Default to have minimum 12px and maximum 60px size.
            sizeRange: [20, 80],
            // Text rotation range and step in degree. Text will be rotated randomly in range [-90,                                                                             90] by rotationStep 45
            // 用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
            // rotationRange: [-45, 0, 45, 90],
            // rotationRange: [ 0,90],
            rotationRange: [0, 66],
            // 随机生成字体颜色
            // maskImage: maskImage,
            textStyle: {
              normal: {
                color: function() {
                  return (
                    'rgb(' +
                    Math.round(Math.random() * 255) +
                    ', ' +
                    Math.round(Math.random() * 255) +
                    ', ' +
                    Math.round(Math.random() * 255) +
                    ')'
                  )
                }
              }
            },
            // 位置相关设置
            // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
            // Default to be put in the center and has 75% x 80% size.
            left: 'center',
            top: 'center',
            right: 'center',
            bottom: 'center',
            width: '200%',
            height: '200%',
            // 数据
            data: this.wordData
          }
        ]
      }
      // 获取后端数据
      this.getAllTag()
      this.chart.setOption(option)
    }
  }
}
</script>

<style>
.gradient-border {
  --borderWidth: 3px;
  background:hsl(30, 100%, 99%);
  position: relative;
  border-radius: var(--borderWidth);
}
.gradient-border:after {
  content: '';
  position: absolute;
  top: calc(-1 * var(--borderWidth));
  left: calc(-1 * var(--borderWidth));
  height: calc(100% + var(--borderWidth) * 2);
  width: calc(100% + var(--borderWidth) * 2);
  background: linear-gradient(60deg, #f79533, #f37055, #ef4e7b, #a166ab, #5073b8, #1098ad, #07b39b, #6fba82);
  border-radius: calc(2 * var(--borderWidth));
  z-index: -1;
  animation: animatedgradient 3s ease alternate infinite;
  background-size: 300% 300%;
}
@keyframes animatedgradient {
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}

p {
      color: black;
      text-indent: 40px;
      font-weight:600;
      font-size: 24px;
      text-align: center;
    }
</style>
