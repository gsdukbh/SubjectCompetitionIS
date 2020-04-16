<template>
    <div>
        <!--顶部-->
        <sticky :z-index="10" class-name="sub-navbar">


            <el-button style="margin-left: 10px;" type="primary">
                导出申请信息
            </el-button>


        </sticky>

        <!--标题-->


        <!--左边信息-->

        <!--右边信息-->


        <!--返回-->
    </div>
</template>

<script>
    import Sticky from "../../components/Sticky/index";
    import {mapGetters} from "vuex";

    export default {
        name: "admin",
        components: {Sticky},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        props: {},
        data() {
            return {
                competitionId: '',
            }
        },
        created() {
        },
        methods: {}
    }
</script>

<style scoped>
    .main {
        height: auto;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);
    }

    .top {
        margin-top: 20px;
    }

    .left {
        margin-left: 20px;
    }

    .right {
        margin-right: 10px;
    }

    .title {
        margin-top: 20px;
        font-size: 50px;
        text-align: center;
        letter-spacing: 12px;
    }

    .rightColumn {
        float: left;
        width: 25%;
        padding-left: 20px;
    }

    .leftColumn {
        float: left;
        width: 75%;
    }

    #h1 {

    }

    .content {
        float: left;
        width: 100%;
    }

    .right2 {
        margin-right: 20px;
    }

    .center {
        text-align: center;
        align-content: center;
    }

    .item {
        margin-top: 10px;
        margin-right: 40px;
    }
</style>
