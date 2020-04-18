<template>
    <div>
            <div  v-if="Page404 ===false" class="main">

                <sticky :z-index="10" class-name="sub-navbar">
                    <router-link :to="'/apply/apply/detail/'+id">
                        <el-button>
                            报名详情
                        </el-button>
                    </router-link>
                    <router-link :to="'/competition/edit/'+showData.id">
                        <el-button style="margin-left: 10px;" type="success">
                            修改
                        </el-button>
                    </router-link>

                </sticky>

                <!--标题-->
                <div class="title">
                    <span>{{showData.name}}</span>
                </div>
                <el-tabs type="card" class="" value="1" @tab-click="handleClick">
                    <el-tab-pane lazy label="竞赛详情" name="1">

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
                                        <!--                                        <el-col :span="24">-->
                                        <!--                                            <span v-if="showData.team">参赛形式：团队赛</span>-->
                                        <!--                                        </el-col>-->
                                        <!--                                        <el-col :span="24">-->
                                        <!--                                            <span v-if="!showData.team">参赛形式：个人赛</span>-->
                                        <!--                                        </el-col>-->
                                        <!--                                        <el-col :span="24">-->
                                        <!--                                            <span v-if="showData.team">人数限制：{{showData.numLimit}}</span>-->
                                        <!--                                        </el-col>-->

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
                                            <span><router-link to="/competition/index"><el-link type="primary">{{showData.organizer}} </el-link></router-link> </span>
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
                            </div>

                        </div>

                    </el-tab-pane>


                    <el-tab-pane label="问题反馈" name="2">

                        <div class="content">
                            <!--搜索框-->

                            <!--                    <div>-->
                            <!--                        <el-input placeholder="名称" style="width: 200px;" v-model="page.name"/>-->
                            <!--                        <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search" @click="handleFilter">-->
                            <!--                            搜索-->
                            <!--                        </el-button>-->
                            <!--                    </div>-->

                            <el-card shadow="hover" class="box-card  left right2">


                                <el-badge :value="noReply" :max="99" class="item">
                                    <el-button size="small" @click="HandNoReply">未回复</el-button>
                                </el-badge>
                                <el-badge :max="10" class="item">
                                    <el-button size="small" @click="HandReply">已回复</el-button>
                                </el-badge>

                                <el-divider v-if="isReply === true" content-position="left">未回复</el-divider>
                                <el-divider v-if="isReply === false " content-position="left">已回复</el-divider>

                                <el-table
                                        stripe
                                        highlight-current-row
                                        v-loading="loading1"
                                        :data="tableDataProblem "
                                        style="width: 100%">
                                    <el-table-column
                                            type="index"
                                            width="50">
                                    </el-table-column>
                                    <el-table-column
                                            sortable
                                            label="时间"
                                            prop="time"
                                    >
                                        <template slot-scope="{row}">
                                            <span>{{formatTimeA(row.time) }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            label="标题"
                                            sortable
                                            prop="title">
                                    </el-table-column>
                                    <el-table-column
                                            label="内容"
                                            prop="content">
                                    </el-table-column>

                                    <el-table-column label="操作" align="center" width="350px"
                                                     class-name="small-padding fixed-width">
                                        <template slot-scope="{row}">

                                            <el-button v-if=" row.myReply === 1" type="primary" size="mini"
                                                       icon="el-icon-edit"
                                                       @click="replyProblem(row)">
                                                回复
                                            </el-button>

                                            <el-button v-if="row.myReply === 0 " style="margin-left: 10px;" type="primary"
                                                       size="mini"
                                                       icon="el-icon-reading" @click="detailReply(row)">
                                                详情
                                            </el-button>


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
                            </el-card>

                        </div>

                    </el-tab-pane>


                </el-tabs>

                <!--对话窗-->
                <el-dialog
                        title="问题详情"
                        :visible.sync="dialogVisible"
                        width="50%"
                >
                    <div style="margin: 20px;"></div>
                    <el-form label-width="100px" :model="replyUp">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="问题id：">
                                    <span>{{replyUp.id}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="时间：">
                                    <span>{{formatTimeA(replyUp.time)}}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="类型：">
                                    <span>{{replyUp.type}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="标题：">
                                    <span>{{replyUp.title}}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>


                        <el-form-item label="问题内容：">
                            <span>{{replyUp.content}}</span>
                        </el-form-item>
                        <el-form-item label="回复：">
                            <el-input
                                    type="textarea"
                                    placeholder="请输入回复内容"
                                    v-model="reply.content"
                                    maxlength="500"
                                    show-word-limit
                            >
                            </el-input>
                        </el-form-item>

                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="replyProblemUp">回 复</el-button>
             </span>
                </el-dialog>

                <el-dialog
                        title="详情"
                        :visible.sync="dialogVisible1"
                        width="50%"
                >
                    <div style="margin: 20px;"></div>

                    <el-form label-width="100px" :model="replyUp">

                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="问题id：">
                                    <span>{{replyUp.id}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="时间：">
                                    <span>{{formatTimeA(replyUp.time)}}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="类型：">
                                    <span>{{replyUp.type}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="标题：">
                                    <span>{{replyUp.title}}</span>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-form-item label="问题内容：">
                            <span>{{replyUp.content}}</span>
                        </el-form-item>

                        <el-form-item label="回复内容：">
                            <span>{{replyDetail.content}}</span>

                        </el-form-item>
                    </el-form>


                </el-dialog>
                <el-tooltip placement="top" content="返回顶部">
                    <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
                </el-tooltip>
            </div>

           <page404  v-if="Page404 ===true"></page404>
    </div>


</template>

<script>

    import Sticky from '@/components/Sticky'
    import {getJson, postFrom, postJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    import MarkdownViewer from "../../components/MarkdownViewer/index";
    import BackToTop from "../../components/BackTop/index";
    import {mapGetters} from "vuex";
    import Page404 from '../error-page/404';
    import qs from 'qs';
    export default {
        name: "detail",
        components: {BackToTop, MarkdownViewer, Sticky, Page404},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                id: '',
                Page404: null,
                showData: {},
                loading: false,
                loading1: true,
                PageLoading:null,
                tempRoute: {},
                markdown: null,
                isReply: true,
                reply: {
                    problemId: null,
                    content: '',
                    author: ''
                },
                replyDetail: {},
                dialogVisible: false,
                dialogVisible1: false,
                multipleSelection: null,
                tableDataProblem: [{}],
                replyUp: {},
                noReply: 0,
                page: {
                    size: 20,
                    page: 0,
                    organizer: null,
                    name: null,
                    isReply: 1,
                    totalElements: 100,
                },
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
            this.PageLoading  = this.$loading({
                lock: true,
                text: '拼命加载中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        },
        mounted() {

        },
        methods: {
            dl() {
                const download = {
                    bucketName: this.showData.bucketName,
                    objectName: this.showData.objectName
                }
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(download);
                a.download = download.objectName.substr(33, download.objectName.length);
                a.target = "_blank";
                a.click();
            },
            async fetchData(id) {
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
                    .catch((error) => {
                        this.Page404 = true;
                        this.$notify.error({
                            title: '错误',
                            message: error
                        })
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
            handleClick(tab) {
                if (tab.name === "2") {
                    this.loadingProblem();
                }

            },
            loadingProblem() {
                this.getDataPage();
            },
            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val;
                this.getDataPage()
            },
            handleSelectionChange(val) {
                this.multipleSelection = val
            },
            /*打开回复对话框*/
            replyProblem(val) {
                this.dialogVisible = true;
                this.reply.problemId = val.id;
                this.reply.author = this.name;
                this.replyUp = val;
                this.reply.content = '';
            },
            /*回复问题*/
            async replyProblemUp() {
                this.dialogVisible = false;
                this.replyUp.myReply = 0;
                await postJson('/tea/competition/problem/reply/save', this.reply)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                                message: '回复成功'
                            });

                        }
                    }).catch(error => {
                        this.$notify.error({
                            title: '错误',
                            message: error
                        })
                    });
                await postJson('/public/problem/save', this.replyUp);
                this.getDataPage();
            },
            /*未回复列表*/
            HandNoReply() {
                this.page.isReply = 1;
                this.isReply = true;
                this.getDataPage();

            },
            /*已回复列表*/
            HandReply() {
                this.isReply = false;
                this.page.isReply = 0;
                this.getDataPage();
                this.loading1 = true;
            },
            /*已回复详情*/
            detailReply(val) {
                this.dialogVisible1 = true;
                this.replyUp = val;
                getJson('/tea/competition/problem/reply/find/' + val.id)
                    .then(response => {
                        this.replyDetail = response.data.data;
                    })

            },
            getDataPage() {
                this.loading1 = true;
                getJson('/tea/problem/find/competition/noReply/' + this.id)
                    .then(response => {
                        this.noReply = response.data.total;
                    });
                postFrom('/public/problem/find/competition/' + this.id, this.page)
                    .then(response => {
                        if (response.status === 200) {
                            this.tableDataProblem = [];
                            this.tableDataProblem = response.data.data;
                            this.page.totalElements = response.data.totalElements;
                            this.loading1 = false;
                        } else {
                            this.loading1 = false;
                        }
                    })
            }
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
