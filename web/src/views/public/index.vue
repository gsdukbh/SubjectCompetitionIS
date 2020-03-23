<template>
    <div>


        <markdown-viewer v-bind:content="cda"></markdown-viewer>

      121
    </div>
</template>


<script>



    import MarkdownViewer from "../../components/MarkdownViewer/index";
    export default {
        name: "index",
        components: {MarkdownViewer},
        data() {
            return {
                cda:'```js\n' +
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
        methods: {}
    }
</script>

<style scoped>

</style>
