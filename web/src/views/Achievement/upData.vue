<template>
    <div>


        <div class="rightColumn">
            <el-card class="right top" shadow="hover" v-loading="loading1">
                <div slot="header" class="clearfix">
                    <span>请先选择竞赛项目</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="handleFilter1()">搜索</el-button>
                </div>

                <el-form>
                    <el-form-item style="margin-bottom: 40px;" prop="title">
                        <MdInput v-model="page1.name" name="name" required>
                            竞赛名称
                        </MdInput>
                    </el-form-item>
                </el-form>
                <div v-for="(item) in competitionDate " :key="item.id" style="text-align: center;">
                    <el-link type="primary" @click="upD(item)">
                        <span style="font-size: 15px;"> {{item.name}}</span>
                    </el-link>
                    <br>
                </div>
                <el-pagination
                        style="text-align: center;margin-top: 10px"
                        @current-change="handleCurrentChange1"
                        @next-click="handleCurrentChange1"
                        @prev-click="handleCurrentChange1"
                        small
                        layout="prev, pager, next"
                        :total="page1.totalElements">
                </el-pagination>
            </el-card>

            <el-card class="right top" shadow="hover" v-loading="loading2">
                <div slot="header" class="clearfix">
                    <span>我负责的</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="handleFilter2()">搜索</el-button>
                </div>

                <el-form>
                    <el-form-item style="margin-bottom: 40px;" prop="title">
                        <MdInput v-model="page1.name" name="name" required>
                            竞赛名称
                        </MdInput>
                    </el-form-item>
                </el-form>
                <div v-for="(item) in competitionDate1 " :key="item.id" style="text-align: center;">
                    <el-link type="primary" @click="upD(item)">
                        <span style="font-size: 15px;"> {{item.name}}</span>
                    </el-link>
                    <br>
                </div>
                <el-pagination
                        style="text-align: center;margin-top: 10px"
                        @current-change="handleCurrentChange2"
                        @next-click="handleCurrentChange2"
                        @prev-click="handleCurrentChange2"
                        small
                        layout="prev, pager, next"
                        :total="page2.totalElements">
                </el-pagination>
            </el-card>
        </div>

        <div class="title" v-if="competition!==null">
            <span> {{competition.name}}--上传成绩</span>
        </div>

        <div class="leftColumn">

            <el-alert
                    style="width: 50%;margin-left: 25%;margin-right: 25%;margin-top: 20px"
                    title="请先选择要上传成绩的竞赛，然后点击下面的按钮上传"
                    type="success"
                    center
                    show-icon>
            </el-alert>
            <el-card shadow="hover" class=" top right">

                <div class="up">
                    <el-upload
                            drag
                            class="upload-demo"
                            ref="upload"
                            :data="data"
                            action="/api/tea/score/upScoreInfo"
                            :on-success="upSuccess"
                            :on-error="upError"
                            :before-upload="beforeCheck"
                            accept=".xls,.xlsx"
                            :auto-upload="false">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        <div class="el-upload__tip" slot="tip">只能上传excel文件</div>

                    </el-upload>

                    <br>
                    <el-button style="margin-top: 10px" size="small" type="primary" @click="getExcel()">下载文件模板
                    </el-button>
                    <el-button style="margin-top: 10px" size="small" type="success" @click="submitUpload">上传信息
                    </el-button>

                </div>
            </el-card>

            <el-card class="right top " shadow="hover" v-if="show">
                <div class="info">
                    <div v-for="(item,index) in  info" :key="index">
                        <li>{{index}}</li>
                        <p> {{item.messages}}:</p>

                        <span>竞赛id:{{item.data.competitionId}} 竞赛名称：{{item.data.competitionName}} 学号：{{item.data.login}} 名字:{{item.data.name}} 分数：{{item.data.score}} 排名:{{item.data.grades}}</span>
                    </div>
                </div>
            </el-card>
        </div>


        <!--返回-->
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import BackToTop from "../../components/BackTop/index";
    import MdInput from "../../components/MDinput/index";
    import {getJson, postFrom} from "../../api/api";
    import {parseTime} from '../../utils/index'


    export default {
        name: "upData",
        components: {MdInput, BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                show: false,
                competition: null,
                competitionDate: [],
                competitionDate1: [],
                loading1: true,
                loading2: true,
                data: {
                    competitionId: null,
                    id: null
                },
                upInfo: {
                    id: '',
                    competitionId: '',
                },
                page1: {
                    size: 5,
                    page: 0,
                    name: '',
                    totalElements: 0,
                },
                page2: {
                    size: 5,
                    page: 0,
                    name: '',
                    totalElements: 0,
                },
                websocket: null,
                info: []
            }
        },
        created() {
            getJson('/public/competition/findAll')
                .then(response => {
                    this.competitionDate = response.data.content;
                    this.page1.totalElements = response.data.totalElements
                    this.loading1 = false;
                })
                .catch(() => {
                    this.loading1 = false;
                });
            this.loading1 = false;
            this.getDataPage2();
            this.initWebSocket();
        },
        methods: {
            upError() {
                this.$notify.error({
                    title: '错误',
                    message: '服务器异常'
                })
            },
            upD(value) {
                this.competition = value;
                this.data.competitionId = value.id;
                this.data.id = this.userId

            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            handleCurrentChange1(val) {
                this.page1.page = val;
                this.getDataPage1()
            },
            handleCurrentChange2(val) {
                this.page2.page = val;
                this.getDataPage2()
            },
            getDataPage2() {
                this.loading2 = true;
                postFrom('/tea/competition/findMyResponsible/' + this.userId, this.page2)
                    .then(response => {
                        this.competitionDate1 = response.data.content;
                        this.page2.totalElements = response.data.totalElements;
                        this.loading2 = false;
                    }).catch(error => {
                    this.loading2 = false;
                    this.$message.error("出现了一些问题" + error)
                })
            },
            getDataPage1() {
                this.loading1 = true;
                postFrom('/public/competition/findAll', this.page1)
                    .then(response => {
                        this.competitionDate = response.data.content;
                        this.page1.totalElements = response.data.totalElements
                        this.loading1 = false;
                    })
            },
            handleFilter2() {
                this.loading2 = true;
                this.getDataPage2();
            },
            handleFilter1() {
                /*搜索*/
                this.loading1 = true;
                this.getDataPage1();
                console.log(this.page)
            },
            upSuccess() {
                this.$notify.success({
                    title: '上传成功',
                    message: '数据提交成功'
                })
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
                if (this.competition === null) {
                    this.$message.warning("请先选择竞赛！")
                } else {
                    this.$notify.info({
                        title: '提示',
                        message: '服务器下载文件中...',
                        duration: 0
                    })
                    let a = document.createElement('a');
                    a.href = "/api/tea/score/getModel/" + this.competition.id;
                    // a.download = download.objectName;
                    a.target = "_blank";
                    a.click();
                }

            },
            submitUpload() {

                if (this.data.competitionId === '') {
                    this.$message.error("请先选择竞赛")
                } else {
                    this.data.id = this.userId
                    this.show = true;
                    this.$refs.upload.submit();
                }
            },
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
                this.info.push(JSON.parse(event.data));
            },
            setOncloseMessage() {
                console.log('WebSocket连接关闭    状态码：' + this.websocket.readyState)
            },
        }
    }
</script>

<style scoped>
    .top {
        margin-top: 20px;
    }

    .left {
        margin-left: 20px;
    }

    .right {
        margin-right: 10px;
    }

    .title {
        margin-top: 20px;
        font-size: 50px;
        text-align: center;
        letter-spacing: 12px;
    }

    .rightColumn {
        float: left;
        width: 25%;
        padding-left: 20px;
    }

    .leftColumn {
        float: left;
        width: 75%;
    }

    .info {
        height: 350px;
    }

    #h1 {

    }

    .content {
        float: left;
        width: 100%;
    }

    .right2 {
        margin-right: 20px;
    }

    .center {
        text-align: center;
        align-content: center;
        margin-top: 10px;
    }

    .item {
        margin-top: 10px;
        margin-right: 40px;
    }

    .up {
        align-content: center;
        text-align: center;
    }
</style>
