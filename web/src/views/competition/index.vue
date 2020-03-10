<template>
    <div>

        竞赛管理
        <markdown-viewer height="auto" v-bind="content"/>
        <div v-html="html"></div>
    </div>
</template>

<script>
    import MarkdownViewer from "../../components/MarkdownViewer/index";
    export default {
        name: "index",
        components: {MarkdownViewer},
        data(){
            return{
                html:null,
                content:'![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png) \n' +
                    '' +
                    '| @cols=2:merged |\n' +
                    '| --- | --- |\n' +
                    '| table | table |'
        }
        },
        mounted() {

        },
        methods:{

            }


    }
</script>

<style scoped>

</style>
