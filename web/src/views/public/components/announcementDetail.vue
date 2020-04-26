<template>
    <div>
        <sticky :z-index="10" class-name="sub-navbar">



            <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" >
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                      >
                重置搜索
            </el-button>
        </sticky>

    </div>
</template>

<script>
    import Sticky from "../../../components/Sticky/index";
    export default {
        name: "announcementDetail",
        components: {Sticky},
        data() {
            return{

            }
        },
        created(){

        },
        methods:{

        }
    }
</script>

<style scoped>

</style>
