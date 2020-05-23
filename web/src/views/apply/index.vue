<template>
    <div>
        <div class="main">

            <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="top">

                <el-tab-pane label="个人赛报名信息" lazy name="first">
                    <apply-personal/>
                </el-tab-pane>

                <!--                                <el-tab-pane label="团队赛报名信息" lazy name="second">-->
                <!--                                    -->
                <!--                                    <team-apply/>-->
                <!--                                    -->
                <!--                                </el-tab-pane>-->
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import ApplyPersonal from "./components/applyPersonal";


    export default {
        name: "index",
        components: {ApplyPersonal},
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
        width: 98%;
        margin-left: 1%;
        margin-right: 1%;
    }

    .top {
        margin-top: 20px;
    }
</style>
