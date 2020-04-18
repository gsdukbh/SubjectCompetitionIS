<template>
    <div>
        <div class="main">
            <el-tabs value="first" type="card">
                <el-tab-pane label="所有作品" name="first">

                </el-tab-pane>
                <el-tab-pane label="我的作品" name="second">

                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index"
    }
</script>

<style scoped>
    .main {
        width: 98%;
        margin-left: 1%;
        margin-right: 1%;
    }

    .top {
        margin-top: 20px;
    }
</style>
