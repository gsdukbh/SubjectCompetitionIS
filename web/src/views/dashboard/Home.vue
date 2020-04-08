<template>
  <div class="home">

      <el-button
              plain
              @click="open4">
          错误
      </el-button>


  </div>

</template>

<script>
// @ is an alias to /src

export default {
  name: 'home',
    data(){
      return{
          websocket:null
      }
    },
    created() {
      this.initWebSocket();
    },
    methods:{

        initWebSocket(){
            this.websocket=new  WebSocket('ws://'+location.host+'/socket/12');

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
            console.log('服务端返回：' + event.data)
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
