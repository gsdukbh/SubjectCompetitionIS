<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
      <el-button
              plain
              @click="open4">
          错误
      </el-button>
        uers
  </div>

</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
    methods:{
        open4() {
            this.$notify.success({
                title: '错误',
                message: '这是一条错误的提示消息'
            });
        }
    }
}

</script>
