<template>
    <div>

        竞赛管理


        <el-button style="margin-top:80px;" type="primary" icon="el-icon-document" @click="getHtml">
            Get HTML
        </el-button>
        <div v-html="html"></div>
    </div>
</template>

<script>

    export default {
        name: "index",

        data(){
            return{
                html:''
            }
        },
        methods:{
            getHtml(){
                this.html = this.$refs.markdownEditor.getValue()
                console.log(this.CodeBlockManager)

            },
            CodeBlockManager(){
              return   this.$refs.markdownEditor.CodeBlockManager()
            }
        }
    }
</script>

<style scoped>

</style>
