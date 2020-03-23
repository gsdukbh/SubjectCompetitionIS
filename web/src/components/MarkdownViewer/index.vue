<template>


    <el-row v-loading="loading">
        <div id="viewer"></div>
    </el-row>
</template>

<script>
    import '@toast-ui/editor/dist/toastui-editor-viewer.css';
    import 'highlight.js/styles/github.css';
    import 'tui-chart/dist/tui-chart.css';
    import defaultOptions from './options'
    import Viewer from 'tui-editor/dist/tui-editor-Viewer'

    export default {
        name: "MarkdownViewer",
        props: {
            content: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                viewer: null,
                contents: null,
                loading:true,
            }
        },
        computed: {
            editorOptions() {
                const options = Object.assign({}, defaultOptions, this.options)
                options.initialEditType = this.mode;
                options.height = this.height;
                options.language = this.language;
                return options
            }
        },
        created(){

        },
        mounted() {
            // this.markdownViewer()
            setTimeout(() => {
                this.markdownViewer()
            }, 2000)
        },
        methods: {

            markdownViewer() {

                this.viewer = new Viewer({
                    el: document.getElementById("viewer"),
                    ...this.editorOptions,
                    // initialValue: this.contents,
                });
                this.viewer.setValue(this.content);
                this.loading=false;
            }
        }
    }
</script>

<style scoped>

</style>
