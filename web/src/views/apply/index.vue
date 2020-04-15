<template>
    <div>
        <div class="main">

            <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="top">

                <el-tab-pane label="个人赛报名信息" lazy name="first">
                    <apply-personal/>
                </el-tab-pane>

                <el-tab-pane label="团队赛报名信息" lazy name="second">
                    <team-apply/>
                </el-tab-pane>

            </el-tabs>
        </div>
    </div>
</template>

<script>
    import ApplyPersonal from "./components/applyPersonal";
    import TeamApply from "./components/teamApply";

    export default {
        name: "index",
        components: {TeamApply, ApplyPersonal},
        data() {
            return {
                activeName: 'first'
            }
        },
        created() {

        },
        methods: {
            handleClick(tab, event) {
                console.log(tab, event);
            }
        }
    }
</script>

<style scoped>
    .main {
        width: 80%;
        margin-left: 10%;
        margin-right: 10%;
    }

    .top {
        margin-top: 20px;
    }
</style>
