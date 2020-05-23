<template>
    <div>
        <div style="margin-top: 10px;">

            <el-select style="width: 200px;margin-left: 10px;" v-model="page.competitionId" filterable
                       placeholder="请选择竞赛">
                <el-option
                        v-for="item in competition"
                        :key="item.competitionId"
                        :label="item.name"
                        :value="item.competitionId">
                </el-option>
            </el-select>

            <el-input placeholder="学号/姓名" style="width: 200px;margin-left: 10px;" v-model="page.value"/>

            <el-select style="margin-left: 10px;" v-model="type" filterable placeholder="成绩">
                <el-option
                        label="不合格"
                        value=0>
                </el-option>
                <el-option
                        label="合格"
                        value=1>
                </el-option>
                <el-option
                        label="中等"
                        value=2>
                </el-option>
                <el-option
                        label="良好"
                        value=3>
                </el-option>
                <el-option
                        label="优秀"
                        value=4>
                </el-option>
            </el-select>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search"
                       @click="handleFilter">
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                       @click="handleRefresh">
                重置搜索
            </el-button>

            <div style="margin-top: 20px" v-loading="loading" class="c">
                <div v-for="(item,index) in content" :key="item.id">
                    <el-card class="at" shadow="hover">
                        <el-row>
                            <el-col :span="1">
                                <span class="index">{{index+1}}</span>
                            </el-col>
                            <el-col :span="2">
                                <el-avatar :size="60" icon="el-icon-user-solid"
                                           v-if="item.scisUser.avatar===null"></el-avatar>
                                <el-avatar :size="60" :src="item.scisUser.avatar"
                                           v-if="item.scisUser.avatar!==null"></el-avatar>
                            </el-col>
                            <el-col :span="18">
                                <p style="font-size: 20px">{{item.scisUser.name}} </p>
                                <br>
                                <span style="color: #5a5e66">
                           <svg-icon icon-class="college"></svg-icon>
                            {{item.scisUser.scisClass.major.college.name}}   {{item.scisUser.scisClass.name}}
                        </span>
                                <span style="color: #5a5e66;margin-left: 20px">
                            <svg-icon icon-class="works"></svg-icon>
                            作品：
                            <router-link :to="'/works/detail/All/'+item.works.id">
                                <el-link type="primary">
                                            {{item.works.name}}
                                </el-link>
                            </router-link>
                        </span>
                            </el-col>
                            <el-col :span="2">
                                <p style="float: right; padding: 3px 0">评分 <span
                                        style="font-size: 30px">
                                      <span v-if="item.score===null"> 暂无</span>
                                    {{item.score}}
                                </span>
                                </p>
                                <br>
                                <br>
                                <p style="float: right; padding: 3px 0">排名
                                    <span v-if="item.score===null"> 暂无</span>

                                    <span
                                            style="font-size: 20px">

                                    {{item.gradesanking}}
                                </span></p>
                            </el-col>
                        </el-row>


                    </el-card>
                </div>


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
            </div>
        </div>
    </div>
</template>

<script>
    import {getJson, postFrom} from "../../../api/api";

    export default {
        name: "rankAll",
        data() {
            return {
                competition: [],
                content: [],
                loading: false,
                type: null,
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    value: null,
                    left: null,
                    right: null,
                    competitionId: null,
                    totalElements: 0,
                },
            }
        },
        created() {
            getJson('/public/competition/find')
                .then(response => {
                    if (response.data.status === 200) {
                        this.competition = response.data.competition;
                    }
                })
        },
        methods: {

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
            getDataPage() {
                postFrom('/public/score/findAll/' + this.page.competitionId, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.content = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        } else {
                            this.loading = false;
                            this.$notify.error({
                                title: '错误',
                                message: '服务器异常' + response.data.message
                            })
                        }
                    }).catch(() => {
                    this.loading = false;
                    this.$notify.error({
                        title: "错误",
                        message: '服务器异常'
                    })
                })
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                if (this.page.competitionId === null) {
                    this.$message.info('请先选择竞赛')
                    this.loading = false;
                } else {
                    if (this.type === '0') {
                        this.page.left = 0;
                        this.page.right = 59;
                    } else if (this.type === '1') {
                        this.page.left = 60;
                        this.page.right = 75;
                    } else if (this.type === '2') {
                        this.page.left = 76;
                        this.page.right = 85;
                    } else if (this.type === '3') {
                        this.page.left = 86;
                        this.page.right = 95;
                    } else if (this.type === '4') {
                        this.page.left = 96;
                        this.page.right = 100;
                    }
                    this.getDataPage();

                }
            },
            handleRefresh() {
                this.page.value = null;
                this.page.value = null;

                this.page.left = null;
                this.page.right = null;
                this.page.competitionId = null;
                this.loading = true;
                this.getDataPage();
            },
        }
    }
</script>

<style scoped>
    .center {
        text-align: center;
        margin-top: 20px;
    }
</style>
