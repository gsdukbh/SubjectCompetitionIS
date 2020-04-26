<template>
    <div>
        <div v-if="Page404 ===false" class="main">

            <sticky :z-index="10" class-name="sub-navbar">

                <router-link :to="'/apply/apply/'+showData.id">
                    <el-button style="margin-left: 10px;" type="success" round>
                        立刻报名
                    </el-button>
                </router-link>

            </sticky>

            <!--标题-->
            <div class="title">
                <span>{{showData.name}}</span>
            </div>

            <el-divider><i class="el-icon-trophy-1"></i></el-divider>
            <div class="el-row">

                <div class="leftColumn">

                    <el-card class="box-card top left" shadow="hover">
                        <h1>内容详情
                            <svg-icon icon-class="content"></svg-icon>
                            <el-tag style="margin-left: 10px">{{showData.level}}</el-tag>
                            <el-tag style="margin-left: 10px" v-if="showData.type === 'online'">线上比赛</el-tag>

                        </h1>

                        <el-divider content-position="right">昨夜星辰昨夜风</el-divider>
                        <!--markdown-->

                        <el-row v-loading="loading">

                            <markdown-viewer v-bind:content="showData.content"/>

                        </el-row>

                    </el-card>

                </div>


                <div class="rightColumn">
                    <el-card class="top right" shadow="hover">
                        <h3>
                            基本信息 <i class="el-icon-info"></i>
                        </h3>
                        <el-divider content-position="right"></el-divider>
                        <el-row>
                            <el-col :span="24">
                                <span>负责人：  {{showData.principal}}</span>
                            </el-col>
                            <el-col :span="24">
                                <span>发布者：{{showData.author}}</span>
                            </el-col>
                            <el-col :span="24">
                                <span v-if="showData.team">参赛形式：团队赛</span>
                            </el-col>
                            <el-col :span="24">
                                <span v-if="!showData.team">参赛形式：个人赛</span>
                            </el-col>
                            <el-col :span="24">
                                <span v-if="showData.team">人数限制：{{showData.numLimit}}</span>
                            </el-col>
                        </el-row>
                    </el-card>

                    <el-card class="box-card top right" shadow="hover">
                        <h3>承办单位
                            <svg-icon icon-class="college"></svg-icon>
                        </h3>
                        <el-divider content-position="right">画楼西畔桂堂东</el-divider>

                        <router-link to="#">

                            <el-tooltip class="item" effect="dark" :content="'查看更多'+showData.organizer+'举办的竞赛'"
                                        placement="bottom">
                                <span><router-link to="/competition/user"><el-link
                                        type="primary">{{showData.organizer}} </el-link></router-link> </span>
                            </el-tooltip>

                        </router-link>

                    </el-card>

                    <el-card class="box-card top right" shadow="hover" v-if="showData.place !==''">
                        <h3>举办地点</h3>
                        <el-divider content-position="right">身无彩凤双飞翼</el-divider>
                        <span><el-link type="primary">比赛地点 {{showData.place}}</el-link></span>
                    </el-card>

                    <el-card class="box-card top right" shadow="hover">

                        <h3>比赛进程
                            <svg-icon icon-class="match"></svg-icon>

                        </h3>

                        <el-divider content-position="right">隔座送钩春酒暖</el-divider>
                        <div class="block">

                            <el-timeline>

                                <el-timeline-item :timestamp="formatTimeA(showData.applyTime)" placement="top">
                                    <el-card>
                                        <h4>开始报名</h4>
                                        <p>参赛者可以进行报名 时间：{{formatTimeA(showData.applyTime)}}</p>
                                    </el-card>
                                </el-timeline-item>

                                <el-timeline-item :timestamp="formatTimeA(showData.startTime)" placement="top">
                                    <el-card>
                                        <h4>比赛开始</h4>
                                        <p>比赛开始于： {{formatTimeA(showData.startTime)}} </p>
                                    </el-card>
                                </el-timeline-item>
                                <el-timeline-item :timestamp="formatTimeA(showData.endTime)" placement="top">
                                    <el-card>
                                        <h4>比赛结束</h4>
                                        <p>比赛于：{{formatTimeA(showData.endTime)}} 结束</p>
                                    </el-card>

                                </el-timeline-item>

                            </el-timeline>
                        </div>
                    </el-card>


                    <el-card class="box-card top right" shadow="hover" v-if="showData.objectName!=null">
                        <h3>相关附件
                            <svg-icon icon-class="annex"></svg-icon>
                        </h3>
                        <el-divider></el-divider>
                        <el-link type="primary" @click="dl()"><i class="el-icon-download"></i>下载</el-link>
                    </el-card>
                    <el-card class="box-card top right" shadow="hover">
                        <h3>相关问题
                            <svg-icon icon-class="problem"></svg-icon>
                        </h3>
                        <el-divider/>
                        <li v-for="item in problem" :key="item.id">
                            <el-link type="primary" @click="showProblem(item)">{{item.title}}</el-link>
                        </li>

                        <el-link type="primary" @click="showMore" style="float: right; padding: 3px 0"
                                 v-if="problemTotalElements > 0"> 更多
                        </el-link>

                        <el-button round type="success" size="mini" style="margin-top: 10px"
                                   @click="dialogVisible1=true">
                            有疑问
                        </el-button>
                    </el-card>

                </div>

            </div>
            <el-dialog title="疑问" :visible.sync="problemMore">

                <el-table
                        v-loading="loading1"
                        :data="problem"
                        stripe
                        style="width: 100%">
                    <el-table-column
                            prop="time"
                            label="时间"
                            width="180">
                        <template slot-scope="{row}">
                            <span>{{formatTimeA(row.time)}}</span>
                        </template>
                    </el-table-column>

                    <el-table-column
                            prop="title"
                            label="标题"
                            width="180">
                        <template slot-scope="{row}">
                            <el-link @click="showProblem(row)"><span>{{row.title}}</span></el-link>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="内容">
                        <template slot-scope="{row}">
                            <el-link @click="showProblem(row)">
                                <span>{{row.content.substr(0,20)}}...</span>
                            </el-link>
                        </template>
                    </el-table-column>

                </el-table>
                <div class="center">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            @next-click="handleCurrentChange"
                            @prev-click="handleCurrentChange"
                            :current-page="page.page"
                            :page-sizes="[20,50,100]"
                            :page-size="page.size"
                            background
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="page.totalElements">
                    </el-pagination>
                </div>
            </el-dialog>

            <el-dialog title="疑问" :visible.sync="dialogVisible">
                <el-form>
                    <el-card shadow="hover">
                        <div slot="header" class="clearfix">
                            <span>问题描述  ---- <el-link type="info"> {{formatTimeA(problemTem.time)}} </el-link></span>
                        </div>
                        <span>标题：{{problemTem.title}}</span>
                        <br>
                        <span> 内容:</span>
                        <span>{{problemTem.content}}</span>

                    </el-card>
                    <el-card shadow="hover" style="margin-top: 10px">
                        <div slot="header" class="clearfix">
                            <span>回复  ---- <el-link type="info"> {{formatTimeA(reply.replyTime)}} </el-link></span>
                        </div>
                        <span>回复人：{{reply.author}}</span>
                        <br>
                        <span> 内容:</span>
                        <span>{{reply.content}}</span>
                    </el-card>
                </el-form>


                <span slot="footer" class="dialog-footer">
                    <el-button round @click="dialogVisible = false">取 消</el-button>
                    <el-button round type="primary" @click="dialogVisible = false">确 定</el-button>
                    </span>
            </el-dialog>

            <el-dialog title="提交疑问" :visible.sync="dialogVisible1">
                <el-form :model="problemUp" :rules="rules" ref="problemUp">
                    <el-form-item prop="title" style="margin-bottom: 40px;">
                        <MdInput v-model="problemUp.title" required>标题</MdInput>
                    </el-form-item>
                    <el-form-item label="类型:" prop="type">
                        <el-input type="text" v-model="problemUp.type" style="width: 100%"></el-input>
                    </el-form-item>
                    <el-form-item label="内容:" prop="content">
                        <el-input type="textarea" v-model="problemUp.content" maxlength="500" show-word-limit>
                        </el-input>
                    </el-form-item>
                </el-form>
                <el-button native-type="reset" round>
                    重置
                </el-button>
                <el-button @click="dialogVisible1=false" round>
                    关闭
                </el-button>
                <el-button round type="primary" style="margin-top: 10px" @click="upProblem()" :loading="buttonLoading">
                    提交
                </el-button>

            </el-dialog>
            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>
        </div>

        <page404 v-if="Page404 ===true"></page404>
    </div>
</template>

<script>
    import qs from 'qs';
    import Sticky from '@/components/Sticky';
    import MarkdownViewer from "../../components/MarkdownViewer/index";
    import BackToTop from "../../components/BackTop/index";
    import {mapGetters} from "vuex";
    import Page404 from '../error-page/404';
    import {getJson, postFrom, postJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    import {getDuration} from "../../utils";
    import MdInput from "../../components/MDinput/index";

    export default {
        name: "detailStu",
        components: {MdInput, BackToTop, MarkdownViewer, Sticky, Page404},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            const validateRequire = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('标题' + '为必传项'))
                } else {
                    callback()
                }
            };
            return {
                date: new Date(),
                dialogVisible: false,
                download: {
                    bucketName: '',
                    objectName: '',
                },
                id: '',
                Page404: null,
                showData: {},
                loading: false,
                loading1: false,

                PageLoading: null,
                tempRoute: {},
                problem: {},
                problemTem: {
                    title: '',
                    content: '',
                    time: '',
                },
                problemMore: null,
                reply: {
                    replyTime: '',
                    content: '',
                    author: ''
                },
                problemTotalElements: 0,
                problemUp: {
                    type: '',
                    title: '',
                    content: '',
                    myReply: 1,
                    competition: {
                        id: '',
                    },
                    scisUser: {
                        id: '',
                    },
                },
                page: {
                    size: 5,
                    page: 0,
                    isReply: 0,
                    totalElements: 20,
                },
                dialogVisible1: false,
                buttonLoading: false,
                rules: {
                    type: [{required: true, message: '请输入', trigger: 'blur'}],
                    title: [
                        {validator: validateRequire}
                    ],
                    content: [
                        {required: true, message: '请输入内容', trigger: 'blur'}
                    ]
                }
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.PageLoading = this.$loading({
                lock: true,
                text: '拼命加载中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);

        },
        methods: {
            upProblem() {
                this.buttonLoading = true;
                this.problemUp.scisUser.id = this.userId;
                this.problemUp.competition.id = this.id;
                postJson('/public/problem/save', this.problemUp)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                                message: response.message
                            })
                            this.buttonLoading = false;
                        } else if (response.data.status === 0) {
                            this.$notify.warning({
                                title: '警告',
                                message: response.message
                            })
                        }
                    })
                this.buttonLoading = false;
            },
            handleSizeChange(val) {
                this.page.size = val;
                this.loading1 = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val;
                this.getDataPage()
            },
            getDataPage() {
                this.loading1 = true;
                postFrom('/public/problem/find/competition/' + this.id, this.page)
                    .then(response => {
                        this.problem = response.data.data;
                        this.page.totalElements = response.data.totalElements;
                        this.loading1 = false;
                    })
                this.loading1 = false;
            },
            async showMore() {
                this.problemMore = true;
                this.getDataPage();
            },
            showProblem(problem) {
                this.problemTem = problem;
                getJson("/public/problem/competition/reply/find/" + problem.id)
                    .then(response => {
                        this.reply = response.data.data;
                    })
                this.dialogVisible = true;
            },
            toApply() {
                console.log(this.date, new Date(this.showData.applyTime))
                console.log(this.date - new Date(this.showData.applyTime))
                console.log(new Date((this.date - new Date(this.showData.applyTime))))
                console.log(getDuration(new Date(this.showData.applyTime) - new Date()))
                this.dialogVisible = true;
            },
            dl() {

                this.download.bucketName = this.showData.bucketName;
                this.download.objectName = this.showData.objectName;
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(this.download);
                // a.download = this.download.objectName.substr(33, this.download.objectName.length);
                a.target = "_blank";
                a.click();
            },
            async fetchData(id) {
                const info = {
                    isReply: 0,
                    size: 5,
                }
                postFrom('/public/problem/find/competition/' + id, info)
                    .then(response => {
                        this.problem = response.data.data;
                        this.problemTotalElements = response.data.totalElements;
                    })
                await getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.showData = response.data.data;
                            this.Page404 = false;
                            this.loading = false;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                            this.PageLoading.close();
                        } else if (response.data.status === 404) {
                            this.loading = false;
                            this.Page404 = true;
                            this.PageLoading.close();
                            this.$notify.warning({
                                title: '警告',
                                message: '资源不存在'
                            })
                        }
                    })
                    .catch(() => {
                    });
            },
            setTagsViewTitle() {
                const title = this.showData.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.showData.name;
                document.title = `${title} - 详情`
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
        }
    }
</script>

<style scoped>
    .main {
        height: auto;
        background-color: #ffffff; /* 浏览器不支持时显示 */
        background-image: linear-gradient(#ffffff, #b6fffb);
    }

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
        text-shadow: 2px 2px #1482f0;
        letter-spacing: 12px;
        color: orange;
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
    }

    .item {
        margin-top: 10px;
        margin-right: 40px;
    }
</style>
