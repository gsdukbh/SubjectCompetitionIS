<template>
    <div id="center">


        <h2> 密码重置</h2>

        <el-divider> <i class="el-icon-lollipop"/></el-divider>
            <el-steps :active="active" finish-status="success" align-center>
                <el-step title="步骤 1"></el-step>
                <el-step title="步骤 2"></el-step>
                <el-step title="步骤 3"></el-step>
            </el-steps>


            <el-button style="margin-top: 12px;" @click="next">下一步</el-button>

    </div>

</template>

<script>


    export default {
        name: "password",
        data() {
            return {
                active: 0
            };
        },

        methods: {
            next() {
                if (this.active++ > 2)
                    this.active = 0;
            }
        }
    }
</script>

<style scoped>

    #center {
        align-content: center;
        text-align: center;
        width: auto;
        padding-top: 50px;
    }
</style>
