<template>
    <div>
        <div :id="id"/>
        <el-dialog
                title="上传添加图片"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose">

            <el-tag type="success">上传图片获取markdown格式语句</el-tag>
            <!-- action="https://httpbin.org/post"-->
            <!--/api/i/upFile/img-->
            <el-upload
                    style="padding-top: 10px"
                    action="/api/i/upFile/img"
                    list-type="picture-card"
                    :on-success="handleImageSuccess"
            >
                <i class="el-icon-plus"></i>

            </el-upload>

            <el-row>

                <el-col style="padding-top: 10px" :span="20">
                    <el-input type="text" :value="copyB" readonly="readonly"></el-input>
                </el-col>
                <el-col style="padding-top: 10px" :span="4">
                    <el-button ref="copys" @click='handleCopy(copyB,$event)'>复制连接</el-button>
                </el-col>

            </el-row>

            <span slot="footer" class="dialog-footer">

    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
        </el-dialog>
    </div>

</template>

<script>
    /*https://github.com/nhn/tui.editor*/
    // 编辑部
    import 'codemirror/lib/codemirror.css' // codemirror
    import 'tui-editor/dist/tui-editor.css' // MyEditor ui
    import 'tui-editor/dist/tui-editor-contents.css'
    import 'highlight.js/styles/github.css';
    import 'tui-chart/dist/tui-chart.css';

    // import Editor from 'tui-editor'
    import Editor from '@toast-ui/editor';
    import defaultOptions from './default-options'

    import clip from '../../utils/clipboard'

    export default {
        name: 'MarkdownEditor',

        props: {
            content:{
                type:String,
                default:''
            },
            value: {
                type: String,
                default: ''
            },
            id: {
                type: String,
                required: false,
                default() {
                    return 'markdown-MyEditor-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
                }
            },
            options: {
                type: Object,
                default() {
                    return defaultOptions
                }
            },
            mode: {
                type: String,
                default: 'markdown'
            },
            height: {
                type: String,
                required: false,
                default: '300px'
            },
            language: {
                type: String,
                required: false,
                default: 'zh_CN'
            }
        },
        data() {
            return {
                editor: null,
                dialogVisible: false,
                toolbar: null,
                copyB: null,
            }
        },
        computed: {
            editorOptions() {
                const options = Object.assign({}, defaultOptions, this.options)
                options.initialEditType = this.mode;
                options.height = this.height;
                return options
            }
        },
        watch: {
            value(newValue, preValue) {
                if (newValue !== preValue && newValue !== this.editor.getValue()) {
                    this.editor.setValue(newValue)
                }
            },
            language() {
                this.destroyEditor();
                this.initEditor();
            },
            height(newValue) {
                this.editor.height(newValue)
            },
            mode(newValue) {
                this.editor.changeMode(newValue)
            }
        },
        updated() {

        },
        mounted() {

            this.initEditor();
            this.upImg();
        },
        destroyed() {
            this.destroyEditor()
        },
        methods: {
            initEditor() {
                this.editor = new Editor({
                    el: document.getElementById(this.id),
                    ...this.editorOptions,
                });
                this.editor.setMarkdown(this.content);
                if (this.value) {
                    this.editor.setValue(this.value)
                }
                // this.editor.on('change', () => {
                //   this.$emit('input', this.editor.getValue())
                // });

            },
            handleCopy(text, event) {
                clip(text, event)
            },
            upImg() {
                this.toolbar = this.editor.getUI().getToolbar();
                const this2 = this;
                this.editor.eventManager.addEventType('clickCustomButton');
                this.editor.eventManager.listen('clickCustomButton', function () {
                    this2.dialogVisible = true;
                });
                this.toolbar.insertItem(0, {
                    type: 'button',
                    options: {
                        $el: `<div class="custom-button"><i class="el-icon-picture"></i></div>`,
                        name: 'customize',
                        className: 'custom-button',
                        tooltip: '图片',
                        event: 'clickCustomButton'
                    }
                });
            },
            handleImageSuccess(response) {
                if (response.status === 200) {
                    this.$notify.success({
                        title: '提交成功',
                        message: "文件:"+response.fileName,
                    });
                    this.copyB = response.markdown;
                }else {
                    this.$notify.error({
                        title: '错误',
                        message: response.message
                    });
                }
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(() => {
                        done();
                    })
                    .catch(() => {
                    });
            },
            destroyEditor() {
                if (!this.editor) return;
                this.editor.off('change');
                this.editor.remove();
            },
            setValue(value) {
                this.editor.setMarkdown(value)
            },
            getValue() {
                return this.editor.getValue()
            },
            setHtml(value) {
                this.editor.setHtml(value)
            },
            getHtml() {
                return this.editor.getHtml()
            },
            getMarkdown() {
                return this.editor.getMarkdown()
            }

        }
    }
</script>
<style>

    .custom-button {
        background-position: -130px -4px;
    }
</style>
