<template>
    <div>
        <div class="main">
            <sticky :z-index="10" class-name="sub-navbar">

                <el-autocomplete
                        style="margin-left: 10px"
                        v-model="page.title"
                        :fetch-suggestions="querySearchAsyncTitle"
                        placeholder="请输入标题"
                ></el-autocomplete>

                <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter()">
                    搜索
                </el-button>
                <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                           @click="handleRefresh">
                    重置搜索
                </el-button>
            </sticky>

            <div style="margin-top: 20px" v-loading="loading">
                <el-card class="at" shadow="hover" v-for="(item,index) in tableData" :key="item.id">
                    <el-row>
                        <el-col :span="1">
                            <span class="index">{{index+1}}</span>
                        </el-col>
                        <el-col :span="2">
                            <el-image
                                    class="img"
                                    :src="item.img"
                                    fit="scale-down">
                                <div slot="error" class="image-slot">
                                    <i class="el-icon-picture-outline"></i>
                                </div>
                            </el-image>
                        </el-col>
                        <el-col :span="21">
                            <router-link :to="'/public/announcement/detail/'+item.id">
                                <el-link  type="primary">
                                    <p style="font-size: 20px;">{{item.title}}</p>
                                </el-link>
                            </router-link>
                            <br>
                            <br>
                            <span style="color: #5a5e66;margin-left: 10px">
                         <i class="el-icon-time"></i>   {{formatTimeA(item.time)}}
                        </span>
                            <span style="color: #5a5e66;margin-left: 10px">
                           <svg-icon icon-class="college"></svg-icon>{{item.from}}
                        </span>
                            <span style="color: #5a5e66;margin-left: 10px">
                           <i class="el-icon-s-custom"/>{{item.author}}
                        </span>

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

            <el-tooltip placement="top" content="返回顶部">
                <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
            </el-tooltip>

        </div>
    </div>
</template>

<script>
    import Sticky from "../../../components/Sticky/index";
    import {getJson, postFrom} from "../../../api/api";

    import BackToTop from "../../../components/BackTop/index";
    import {Myformat} from "../../../utils";

    export default {
        name: "announcement",
        components: {BackToTop, Sticky},
        data() {
            return {
                tableData: [],
                loading: true,
                page: {
                    size: 20,
                    page: 0,
                    pages: 0,
                    title: null,
                    totalElements: 100,
                },
            }
        },
        created() {
            this.getDataPage();
        },
        methods: {
            querySearchAsyncTitle(queryString, cb) {
                getJson('/public/announcement/findTitle')
                    .then(response => {
                        this.temTitle = response.data.data;
                    });
                const temTitle = this.temTitle;
                const res = queryString ? temTitle.filter(this.createTitleFilter(queryString)) : temTitle;

                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(res);
                }, 3000 * Math.random());
            },
            createTitleFilter(queryString) {
                return (temTitle) => {
                    return (temTitle.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                };
            },
            formatTimeA(time) {
                // return parseTime(time, '{y}-{m}-{d} {h}:{i}')
                return Myformat("yyyy年MM月dd日", new Date(time))
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
            async getDataPage() {

                await postFrom('/public/announcement/findAllA', this.page)
                    .then(response => {
                        this.tableData = response.data.content;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;
                    })
                    .catch(error => {
                        this.loading = false;
                        this.$message.error("出现了一些问题:" + error)
                    })
            },
        }
    }
</script>

<style scoped>
    .main {
        padding-left: 10%;
        padding-right: 10%;
    }

    .img {
        width: 100px;
        height: 100px;
        top: -10px;
    }

    .index {
        font-size: larger;
        width: 20px;
        top: 0;
    }

    .at {
        /*width: 80%;*/
        /*margin-left: 10%;*/
        /*margin-right: 10%;*/
        margin-top: 20px;
        height: 100px;
    }

    .center {
        text-align: center;
        margin-top: 20px;
    }
</style>
