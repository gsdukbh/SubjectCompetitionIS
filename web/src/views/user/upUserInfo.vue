<template>
    <div>
        <div class="title">
            <el-alert
                    style="width: 30%;margin-left: 35%;margin-right: 35%"
                    title="因为使用用户的身份证后六位作为密码,若留空,默认密码为:123456"
                    type="warning"
                    center
                    show-icon>
            </el-alert>
        </div>
        <div class="up">
            <el-upload
                    drag
                    class="upload-demo"
                    ref="upload"
                    :data="data"
                    action="/api/admin/user/up/data"
                    :on-success="upSuccess"
                    :before-upload="beforeCheck"
                    accept=".xls,.xlsx"
                    :auto-upload="false">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传excel文件</div>
                <div class="el-upload__tip" slot="tip">上传教师信息时，不需要填写专业、班级等信息</div>

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
            <el-alert
                    v-if="show"
                    style="width: 20%;margin-left: 40%"
                    title="解析到的数据"
                    type="success"
                    show-icon>
            </el-alert>

            <div class="info">

                <el-table
                        height="350"
                        v-if="show"
                        :data="info"
                        style="width: 90%">
                    <el-table-column
                            prop="login"
                            label="学号"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="姓名"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="sex"
                            label="性别">
                    </el-table-column>
                    <el-table-column
                            prop="college"
                            label="院系">
                    </el-table-column>
                    <el-table-column
                            prop="majorName"
                            label="专业">
                    </el-table-column>

                    <el-table-column
                            prop="level"
                            label="培养层次">
                    </el-table-column>

                    <el-table-column
                            prop="level"
                            label="培养层次">
                    </el-table-column>

                    <el-table-column
                            prop="className"
                            label="班级">
                    </el-table-column>

                    <el-table-column
                            prop="phone"
                            label="手机号码">
                    </el-table-column>

                    <el-table-column
                            prop="email"
                            label="邮箱">
                    </el-table-column>
                    <el-table-column
                            prop="role"
                            label="身份">
                    </el-table-column>
                </el-table>
            </div>

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
                upData: [],
                value1: true,
                show: false,
                data: {
                    id: this.userId,
                    role: 2,
                },
                info: [],
            }
        },
        mounted() {
            this.data.id = this.userId;
            this.initWebSocket();
        },
        methods: {
            initWebSocket() {
                this.websocket = new WebSocket('ws://' + location.host + '/socket/' + this.userId);

                this.websocket.onerror = this.setErrorMessage;
                // 连接成功
                this.websocket.onopen = this.setOnopenMessage;
                // 收到消息的回调
                this.websocket.onmessage = this.setOnmessageMessage;
                // 连接关闭的回调
                this.websocket.onclose = this.setOncloseMessage

            },
            setErrorMessage() {
                console.log('WebSocket连接发生错误   状态码：' + this.websocket.readyState)
            },
            setOnopenMessage() {
                console.log('WebSocket连接成功    状态码：' + this.websocket.readyState)
            },
            setOnmessageMessage(event) {

                // console.log('服务端返回：' + event.data);
                this.info.push(JSON.parse(event.data));
            },
            setOncloseMessage() {
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
            upSuccess() {
                this.$notify.success({
                    title: '成功',
                    message: '上传了'+this.show.length()+'条信息成功'
                })
            },
            submitUpload() {
                this.show = true;
                if (this.value1) {
                    this.data.role = 2;
                } else {
                    this.data.role = 3;
                }
                this.$refs.upload.submit();
            },

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

    .title {
        margin-top: 20px;
        text-align: center;

        /*color: orange;*/
        /*font-size: 20px;*/
        /*text-shadow: 2px 2px #1482f0;*/
    }

    .info {
        text-align: center;
        margin-left: 10%;
    }
</style>
