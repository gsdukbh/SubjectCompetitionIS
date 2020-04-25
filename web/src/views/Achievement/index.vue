<template>
    <div>

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
                    <el-link type="primary" @click="showInfo(item)">
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
                    <el-link type="primary" @click="showInfo(item)">
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
            <span> {{competition.name}}--成绩排行</span>
        </div>

        <div class="leftColumn">
            <el-card shadow="hover" class="top left">
                <div slot="header" class="clearfix">
                    <span>详情</span>

                    <el-button @click="dl()" style="float: right; padding: 3px 0" type="text" icon="el-icon-download">
                        下载成绩单
                    </el-button>

                </div>

                <el-tabs value="first" type="card" @tab-click="handleClick">
                    <el-tab-pane label="成绩排行" name="first">
                        <rank ref="rank"/>
                    </el-tab-pane>

                    <el-tab-pane label="数据分析" name="second">
                        <analysis ref="analysis"></analysis>
                    </el-tab-pane>

                </el-tabs>


            </el-card>
        </div>
        <!--返回-->
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>

    </div>
</template>

<script>
    import {getJson, postFrom} from "../../api/api";
    import MdInput from "../../components/MDinput/index";
    import BackToTop from "../../components/BackTop/index";

    import {parseTime} from '../../utils/index'
    import {mapGetters} from "vuex";
    import Rank from "./components/rank";
    import Analysis from "./components/analysis";


    export default {
        name: "index",
        components: {Analysis, Rank, BackToTop, MdInput},
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
            this.getDataPage2()
        },
        methods: {
            handleClick(tab) {
                if (tab.name === 'second') {
                    if (this.competition === null) {
                        this.$message.info("请先选择竞赛")
                    } else {
                        this.$refs.analysis.getInfo(this.competition);
                    }
                }
            },

            dl() {
                if (this.competition !== null) {
                    let a = document.createElement('a');
                    a.href = '/api//tea/score/getScoreInfo/' + this.competition.id
                    a.target = '_blank';
                    a.click();
                } else {
                    this.$message.info("请先选择竞赛！")
                }
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
            showInfo(value) {
                this.competition = value;
                this.$refs.rank.getInfo(value);
                this.$refs.analysis.getInfo(this.competition);
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
