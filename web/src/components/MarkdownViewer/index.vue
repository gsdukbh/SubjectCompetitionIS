
<template>
    <div id="editSection"></div>
</template>

<script>
    import 'tui-editor/dist/tui-editor-contents.css' // editor content
    import 'highlight.js/styles/github.css';
    import Viewer from 'tui-editor/dist/tui-editor-Viewer'
    export default {
        name: "MarkdownViewer",
        data(){
            return {
                html:null,
                content:'![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png) \n' +
                    '' +
                    '| @cols=2:merged |\n' +
                    '| --- | --- |\n' +
                    '| table | table |'
            }
        },
        mounted() {
         this.initEditor();
        },
        methods:{
            initEditor(){
                this.html = new Viewer({
                    el: document.querySelector('#editSection'),
                    initialEditType: 'markdown',
                    previewStyle: 'vertical',
                })},
            initialValue(){
            this.html.setValue(this.content)
            }
        }
    }
</script>

<style scoped>

</style>
