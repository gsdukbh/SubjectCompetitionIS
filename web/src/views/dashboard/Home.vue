<template>
  <div class="home">

      <el-button
              plain
              @click="open4">
          错误
      </el-button>
      <div id="chart" class="main" style="width: 600px;height:400px;"></div>

  </div>

</template>

<script>
// @ is an alias to /src
import eCharts from 'echarts'

export default {
    name: 'home',
    data() {
        return {
            websocket: null,
            chart: null,

        }
    },
    created() {
        // this.initWebSocket();

    },
    mounted() {
        this.$nextTick(() => {
            this.initCharts()
        })
    },
    beforeDestroy() {
        if (!this.chart) {
            return
        }
        this.chart.dispose()
        this.chart = null
    },
    methods: {
        initCharts() {
            /*https://echarts.apache.org/zh/*/
            this.chart = eCharts.init(document.getElementById('chart'))
            this.chart.setOption({
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                xAxis: {
                    data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
                },
                yAxis: {},
                series: [{
                    name: '销量',
                    type: 'bar',
                    data: [5, 20, 36, 10, 10, 20]
                }]
            })
        },


        initWebSocket() {
            this.websocket = new WebSocket('ws://' + location.host + '/socket/12');

            this.websocket.onerror = this.setErrorMessage;
            // 连接成功
            this.websocket.onopen = this.setOnopenMessage;
            // 收到消息的回调
            this.websocket.onmessage = this.setOnmessageMessage;
            // 连接关闭的回调
            this.websocket.onclose = this.setOncloseMessage
        },
        setErrorMessage () {
            console.log('WebSocket连接发生错误   状态码：' + this.websocket.readyState)
        },
        setOnopenMessage () {
            console.log('WebSocket连接成功    状态码：' + this.websocket.readyState)
        },
        setOnmessageMessage (event) {
            // 根据服务器推送的消息做自己的业务处理
            console.log('服务端返回：' + event.dataAnalysis)
        },
        setOncloseMessage () {
            console.log('WebSocket连接关闭    状态码：' + this.websocket.readyState)
        },

        open4() {
            this.$notify.success({
                title: '错误',
                message: '这是一条错误的提示消息'
            });
        }
    }
}

</script>
