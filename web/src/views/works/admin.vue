<template>
    <div>
        <div class="title" v-if="competition!==null">
            <span> {{competition.name}}--作品</span>
        </div>

        <div class="leftColumn">
            <el-card shadow="hover" class="top left">
                <div slot="header" class="clearfix">
                    <span>所有的作品</span>
                    <el-tag style="margin-left: 10px">共{{page.totalElements}}人提交</el-tag>

                    <el-button @click="dl()" style="float: right; padding: 3px 0" type="text" icon="el-icon-download">
                        下载所有作品
                    </el-button>

                </div>
                <div style="margin-top: 10px;">

                    <el-input placeholder="作者" style="width: 200px;margin-left: 10px;" v-model="page.userName"/>
                    <el-input placeholder="作品名称" style="width: 200px;margin-left: 10px;" v-model="page.worksName"/>
                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search"
                               @click="handleFilter">
                        搜索
                    </el-button>
                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                               @click="handleRefresh">
                        重置搜索
                    </el-button>

                </div>


                <el-row v-loading="loading" style="margin-top: 10px">
                    <el-col :span="6" v-for="(item,index) in works" :key="index">


                        <el-card shadow="hover" :body-style="{ padding: '0px' }" class="card" @click="t()">
                            <el-tooltip class="item" effect="dark" content="点击查看作品详情" placement="top">
                                <router-link :to="'/works/detail/All/'+item.id">
                                    <el-image
                                            :src="item.img"
                                            fit="scale-down"
                                            class="image">
                                        <div slot="placeholder" class="image-slot">
                                            加载中<span class="dot">...</span>
                                        </div>
                                    </el-image>
                                </router-link>
                            </el-tooltip>
                            <div style="padding: 14px;">
                        <span>作品名称：
                           <router-link :to="'/works/detail/'+item.id">
                               <el-link :underline="false" type="primary">
                                   {{item.name}}
                               </el-link>
                            </router-link>
                        </span>
                                <br>
                                <span>作者: {{item.author}}</span>
                                <br>
                                <span>上传时间：{{formatTimeA(item.upTime)}}</span>
                                <br>
                                <span>作品：<el-link type="primary" icon="el-icon-download" @click="dlWorks(item)">{{item.objectName.substr(33,item.objectName.length)}}</el-link></span>
                                <br>
                                <span v-if="works.score!=null">评分：{{works.score}}</span>
                                <span v-if="works.score==null">评分：暂无</span>
                                <br>
                                <span>所属竞赛：
                            <router-link :to="'/competition/detailStu/'+item.competition.id">
                                  <el-link :underline="false" type="primary">
                                   {{item.competition.name}}
                               </el-link>
                            </router-link>
                        </span>
                            </div>

                        </el-card>


                    </el-col>

                </el-row>

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

        <div class="rightColumn">
            <el-card class="right top" shadow="hover" v-loading="loading1">
                <div slot="header" class="clearfix">
                    <span>请选择竞赛项目</span>
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
                    <el-link type="primary" @click="showWorks(item)">
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
                    <el-link type="primary" @click="showWorks(item)">
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
        <!--返回-->
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {getJson, postFrom} from "../../api/api";
    import BackToTop from "../../components/BackTop/index";
    import MdInput from "../../components/MDinput/index";
    import {parseTime} from '../../utils/index'
    import qs from 'qs';

    export default {
        name: "admin",
        components: {MdInput, BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                loading2: true,
                loading1: true,
                competition: null,
                competitionDate: [],
                competitionDate1: [],
                loading: false,
                works: [],
                page: {
                    size: 20,
                    page: 0,
                    userName: null,
                    competitionId: null,
                    worksName: null,
                    totalElements: 0,
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
            }
        },
        created() {
            getJson('/tea/competition/findAll')
                .then(response => {
                    this.competitionDate = response.data.content;
                    this.page1.totalElements = response.data.totalElements
                    this.loading1 = false;
                })
                .catch(() => {
                    this.loading1 = false;
                });
            this.loading1 = false;
            this.getDataPage2()
        },
        methods: {
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            dlWorks(value) {
                const download = {
                    bucketName: value.bucketName,
                    objectName: value.objectName,
                }
                this.$notify.info({
                    title: '提示',
                    message: '服务器下载文件中...'
                })
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(download);
                a.download = download.objectName.substr(33, download.objectName.length);
                a.target = "_blank";
                a.click();
            },
            dl() {
                this.$notify.info({
                    title: '提示',
                    message: '服务器下载文件中...',
                    duration: 0
                })
                let a = document.createElement('a');
                a.href = "/api/student/works/get/File/works/" + this.competition.id;
                // a.download = download.objectName;
                a.target = "_blank";
                a.click();
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
                postFrom('/tea/competition/findAll', this.page1)
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
            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val;
                this.loading = true;
                this.getDataPage()
            },
            handleFilter1() {
                /*搜索*/
                this.loading1 = true;
                this.getDataPage1();
                console.log(this.page)
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
                console.log(this.page)
            },
            handleRefresh() {
                this.page.title = null;
                this.page.from = null;
                this.page.type = null;
                this.loading = true;
                this.getDataPage();
            },
            showWorks(value) {
                console.log(value)
                this.competition = value;
                this.loading = true;
                this.page.competitionId = value.id;
                postFrom('/student/works/findAll', this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.works = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        }
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error("出现了一些问题:" + error)
                })
            },
            getDataPage() {
                this.loading = true;

                postFrom('/student/works/findAll', this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.works = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        }
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error("出现了一些问题:" + error)
                })
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


    .image {
        width: 100%;
        height: 250px;
        display: block;
    }
</style>
