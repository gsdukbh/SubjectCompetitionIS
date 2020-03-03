<template>
  <div class="about">
    <h1>This is an about page</h1>
    <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>

  </div>
</template>
<style  lang="scss" scoped>
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: #2c3e50;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  span {
    &:first-of-type {
      margin-right: 16px;
    }
  }
</style>
