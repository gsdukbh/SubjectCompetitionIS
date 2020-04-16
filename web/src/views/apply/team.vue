<template>
    <div>
        <div class="main">

            <el-tabs type="card" class="top" value="first">

                <el-tab-pane label="我创建的团队" lazy name="first">
                    <my-created-apply/>
                </el-tab-pane>

                <el-tab-pane label="我加入的团队" lazy name="second">
                    <my-join-team/>
                </el-tab-pane>

            </el-tabs>
        </div>
    </div>
</template>

<script>
    import MyJoinTeam from "./components/myJoinTeam";
    import MyCreatedApply from "./components/myCreatedApply";

    export default {
        name: "team",
        components: {MyCreatedApply, MyJoinTeam}
    }
</script>

<style scoped>
    .top {
        width: 95%;
        margin-right: 2.5%;
        margin-left: 2.5%;
        margin-top: 20px;
    }
</style>
