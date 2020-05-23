<template>
    <div>
        <div style="margin-top: 10px;">


            <el-input placeholder="作品名称" style="width: 200px;margin-left: 10px;" v-model="page.worksName"/>

            <el-select style="width: 200px;margin-left: 10px;" v-model="page.competitionId" filterable
                       placeholder="请选择竞赛">
                <el-option
                        v-for="item in competition"
                        :key="item.competitionId"
                        :label="item.name"
                        :value="item.competitionId">
                </el-option>
            </el-select>
            <el-button :loading="loading1" class="filter-item" type="primary" style="margin-left: 10px;"
                       icon="el-icon-search"
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


                <el-card shadow="hover" :body-style="{ padding: '0px' }" style="margin-left: 10px" @click="t()">
                    <el-tooltip class="item" effect="dark" content="点击查看作品详情" placement="top">
                        <router-link :to="'/works/detail/'+item.id">
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
                        <span v-if="works.score!=null">评分：{{works.score}}</span>
                        <span v-if="works.score==null">评分：暂无</span>
                        <br>
                        <span>所属竞赛：
                            <router-link :to="'/competition/detailStu/'+item.competitionId">
                                  <el-link :underline="false" type="primary">
                                   {{item.competitionName}}
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
                    :current-page="page.pages"
                    :page-sizes="[20,50,100]"
                    :page-size="page.size"
                    background
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.totalElements">
            </el-pagination>
        </div>
        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>
    </div>
</template>

<script>
    import BackToTop from "../../../components/BackTop/index";
    import {parseTime} from '../../../utils/index'
    import {getJson, postFrom} from "../../../api/api";
    import {mapGetters} from "vuex";

    export default {
        name: "myWorks",
        components: {BackToTop},
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                competition: [],
                loading: true,
                loading1: false,
                works: [],
                page: {
                    size: 10,
                    page: 0,
                    pages: 0,
                    competitionId: null,
                    worksName: null,
                    totalElements: 20,
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
            this.getDataPage()
        },
        methods: {
            t() {
                console.log("s")
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
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
            getDataPage() {
                this.loading = true;
                postFrom('/student/works/findMyWorks/' + this.userId, this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.works = response.data.content;
                            this.page.totalElements = response.data.totalElements.totalElements
                            this.loading = false;
                            this.loading1 = false;
                        } else {
                            this.loading = false;
                            this.loading1 = false;
                            this.$notify.warning({
                                title: "警告",
                                message: response.data.message
                            })
                        }
                    }).catch(error => {
                    this.loading1 = false;
                    this.$notify.error({
                        title: '错误',
                        message: error
                    })
                })
            },
            handleFilter() {
                this.loading = true;
                this.loading1 = true;
                this.getDataPage()
            },
            handleRefresh() {
                this.page = {
                    size: 10,
                    page: 0,
                    userName: null,
                    competitionId: null,
                    worksName: null,
                    totalElements: 20,
                }
                this.getDataPage()
            }
        }
    }
</script>

<style scoped>
    .center {
        text-align: center;
        margin-top: 10px;
    }

    .image {
        width: 100%;
        height: 250px;
        display: block;
    }
</style>
