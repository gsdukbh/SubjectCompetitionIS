<template>
    <div>
        <div class="title" v-if="competition!==null">
            <span> {{competition.name}}--报名数据</span>
        </div>
        <!--左边-->
        <div class="leftColumn">
            <el-card shadow="hover" class="top left">
                <div slot="header" class="clearfix">
                    <span>报名数据</span>
                    <el-tag style="margin-left: 10px">共{{page.totalElements}}人</el-tag>
                    <el-button @click="dl()" style="float: right; padding: 3px 0" type="text" icon="el-icon-download">
                        导出申请信息
                    </el-button>
                </div>
                <div style="margin-top: 10px;">

                    <el-input placeholder="名称" style="width: 200px;margin-left: 10px;" v-model="page.name"/>

                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search"
                               @click="handleFilter">
                        搜索
                    </el-button>
                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                               @click="handleRefresh">
                        重置搜索
                    </el-button>

                </div>
                <el-table
                        v-loading="loading"
                        :data="personalData"
                        style="width: 100%;margin-top: 10px"
                >
                    <el-table-column
                            sortable
                            prop="applyTime"
                            label="申请时间"
                            width="180">
                        <template slot-scope="{row}">
                            <span>{{ formatTimeA(row.applyTime) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="学号">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.login}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="名字">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.name}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="班级">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.scisClass.name}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="学院">
                        <template slot-scope="{row}">
                            <span>{{row.userInfo.scisClass.major.name}}</span>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="center">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            @next-click="handleCurrentChange"
                            @prev-click="handleCurrentChange"
                            :current-page="page.pages"
                            :page-sizes="[20,50,100]"
                            :page-size="page.size"
                            background
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="page.totalElements">
                    </el-pagination>
                </div>
            </el-card>


        </div>


        <!--        右边-->
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
                    <el-link type="primary" @click="showApply(item)">
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
                        <MdInput v-model="page2.name" name="name" required>
                            竞赛名称
                        </MdInput>
                    </el-form-item>
                </el-form>
                <div v-for="(item) in competitionDate1 " :key="item.id" style="text-align: center;">
                    <el-link type="primary" @click="showApply(item)">
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
    import MdInput from "../../components/MDinput/index";
    import {mapGetters} from "vuex";
    import {getJson, postFrom} from "../../api/api";
    import BackToTop from "../../components/BackTop/index";
    import {parseTime} from '../../utils/index'
    export default {
        name: "adminIndex",
        components: {BackToTop, MdInput},
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
                personalData: [],
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    name: '',
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
            dl() {
                let a = document.createElement('a');
                a.href = "/api/tea/apply/downloadApply/" + this.competition.id;
                // a.download = download.objectName;
                a.target = "_blank";
                a.click();
            },
            handleCurrentChange1(val) {
                this.page1.page = val - 1;
                this.getDataPage1()
            },
            handleCurrentChange2(val) {
                this.page2.page = val - 1;
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
            showApply(value) {
                console.log(value)
                this.competition = value;
                this.loading = true;
                postFrom('/tea/apply/personal/' + this.competition.id, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.personalData = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        }
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error("出现了一些问题:" + error)
                })
            },
            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
                this.getDataPage();
            },
            /*当前页数*/
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.page = val - 1;
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
            getDataPage() {
                this.loading = true;
                postFrom('/tea/apply/personal/' + this.competition.id, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.personalData = response.data.content;
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
</style>
