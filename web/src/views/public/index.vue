<template>
    <div>


        <markdown-viewer v-bind:content="cda"></markdown-viewer>

        <el-button @click="dl">下载</el-button>
        <a id="download">ssss</a>
    </div>
</template>


<script>


    import MarkdownViewer from "../../components/MarkdownViewer/index";
    // import {jsonToForm} from "../../utils";
    // import {download} from "../../api/api";
    // import {saveAs} from 'file-saver'
    import qs from 'qs';
    export default {
        name: "index",
        components: {MarkdownViewer},
        data() {
            return {
                download: {
                    bucketName: 'img',
                    objectName: '算法篇美团.pdf',
                },

                cda: '```js\n' +
                    'console.log(\'foo\')\n' +
                    '```\n' +
                    '```javascript\n' +
                    'console.log(\'bar\')\n' +
                    '```\n' +
                    '```html\n' +
                    '<div id="editor"><span>baz</span></div>\n' +
                    '```'
            }
        },
        mounted() {

        },
        methods: {
            dl() {

                let a = document.createElement('a');
                // a.href ="/api/i/upFile/getFile"+jsonToForm(this.download);
                a.href ="/api/i/upFile/getFile?"+ qs.stringify(this.download);
                a.download=this.download.objectName;
                a.target="_blank";
                a.click();
            }
        }
    }
</script>

<style scoped>

</style>
