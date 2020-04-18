<template>
    <div>
        <div class="main">
            <el-tabs value="first" type="card">
                <el-tab-pane label="所有作品" name="first">
                    <all-works></all-works>
                </el-tab-pane>
                <el-tab-pane label="我的作品" name="second">


                    <my-works/>


                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import AllWorks from "./components/allWorks";
    import MyWorks from "./components/myWorks";

    export default {
        name: "index",
        components: {MyWorks, AllWorks},

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
