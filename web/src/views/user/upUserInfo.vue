<template>
    <div>

        <div class="up">
            <el-upload
                    drag
                    class="upload-demo"
                    ref="upload"
                    :data="data"
                    action="http://httpbin.org/post"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-success="upSuccess"
                    :before-upload="beforeCheck"
                    accept=".xls,.xlsx"
                    :auto-upload="false">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传excel文件</div>
            </el-upload>

            <el-switch
                    style="margin-top: 20px"
                    v-model="value1"
                    active-text="学生信息"
                    inactive-text="教师信息">
            </el-switch>
            <br>
            <el-button style="margin-top: 10px" size="small" type="primary" @click="getExcel">下载文件模板</el-button>
            <el-button style="margin-top: 10px" size="small" type="success" @click="submitUpload">上传信息</el-button>

        </div>
        <div>
            <el-divider></el-divider>
            {{info}}
        </div>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "upUserInfo",
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                upData:[],
                value1: true,
                data: {
                    id: this.userId,
                    role: 2,
                },
                info: null,
            }
        },
        mounted() {
            this.data.id = this.userId;
        },
        methods: {
            initWebSocket(){
                this.websocket=new  WebSocket('ws://'+location.host+'/socket/12');

                this.websocket.onerror = this.setErrorMessage;
                // 连接成功
                this.websocket.onopen = this.setOnopenMessage;
                // 收到消息的回调
                this.websocket.onmessage = this.setOnmessageMessage;
                // 连接关闭的回调
                this.websocket.onclose = this.setOncloseMessage

            },
            setErrorMessage () {
                console.log('WebSocket连接发生错误   状态码：' + this.websocket.readyState)
            },
            setOnopenMessage () {
                console.log('WebSocket连接成功    状态码：' + this.websocket.readyState)
            },
            setOnmessageMessage (event) {
                // 根据服务器推送的消息做自己的业务处理
                console.log('服务端返回：' + event.data)

            },
            setOncloseMessage () {
                console.log('WebSocket连接关闭    状态码：' + this.websocket.readyState)
            },
            beforeCheck(file) {
                let xlsx = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
                let xls = 'application/vnd.ms-excel';
                let type = file.type;
                if (xlsx === type || xls === type) {
                    return true;
                } else {
                    this.$notify.error({
                        title: '错误',
                        message: '不支持的文件类型'
                    });
                    return false;
                }
            },
            getExcel() {
                /*获得excel文件*/
                this.downloadLoading = true;
                import('../../utils/excel').then(excel => {
                    const tHeader = [
                        '学号',
                        '名字',
                        '性别',
                        '院系',
                        '专业',
                        '培养层次',
                        '班级',
                        '身份证号',
                        '手机号码',
                        '邮箱',
                    ];
                    const data = [];
                    excel.export_json_to_excel({
                        header: tHeader,
                        data,
                        filename: 'excel-template',
                    });
                })
            },
            upSuccess(response) {
                this.info = response;
            },
            submitUpload() {
                if (this.value1) {
                    this.data.role = 2;
                } else {
                    this.data.role = 3;
                }
                this.$refs.upload.submit();
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            }
        }
    }
</script>

<style scoped>
    .up {
        align-content: center;
        text-align: center;
        margin-top: 20px;
        margin-left: 40%;
        width: 20%;
    }

</style>
