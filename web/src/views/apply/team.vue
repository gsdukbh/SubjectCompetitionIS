<template>
    <div>
        <div class="main">

            <el-tabs type="card" class="top" value="first">

                <el-tab-pane label="我创建的团队" lazy name="first">
                    <my-join-team/>
                </el-tab-pane>

                <el-tab-pane label="我加入的团队" lazy name="second">

                </el-tab-pane>

            </el-tabs>
        </div>
    </div>
</template>

<script>
    import MyJoinTeam from "./components/myJoinTeam";

    export default {
        name: "team",
        components: {MyJoinTeam}
    }
</script>

<style scoped>
    .top {
        width: 80%;
        margin-right: 10%;
        margin-left: 10%;
        margin-top: 20px;
    }
</style>
