<template>
    <div>
        <div class="main">

            <el-alert
                    center
                    class="title"
                    title="请先选择竞赛，然后搜索成绩"
                    type="success">
            </el-alert>


            <el-tabs value="first" type="card">
                <el-tab-pane label="成绩排行" name="first">
                    <rank-all></rank-all>
                </el-tab-pane>
                <el-tab-pane label="我的成绩" name="second">

                    <my-score></my-score>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>


    import RankAll from "./components/rankAll";
    import MyScore from "./components/myScore";


    export default {
        name: "stu",
        components: {MyScore, RankAll},
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

    .title {
        margin-left: 40%;
        margin-right: 40%;
        width: 20%;
        margin-top: 20px;
    }
</style>
