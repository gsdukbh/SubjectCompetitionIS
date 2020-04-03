<template>
    <div>
        <!--顶部搜索框-->

        <sticky :z-index="10" class-name="sub-navbar">

            <el-select style="margin-left: 10px;" v-model="page.from" filterable placeholder="请选择信息来源">
                <el-option
                        v-for="item in college"
                        :key="item.id"
                        :label="item.collegeName"
                        :value="item.collegeName">
                </el-option>
            </el-select>

            <el-autocomplete
                    style="margin-left: 10px"
                    v-model="page.title"
                    :fetch-suggestions="querySearchAsyncTitle"
                    placeholder="请输入标题"
            ></el-autocomplete>

            <el-autocomplete
                    style="margin-left: 10px"
                    v-model="page.type"
                    :fetch-suggestions="querySearchAsync"
                    placeholder="请输入竞赛类型"
            ></el-autocomplete>

            <el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="handleFilter()">
                搜索
            </el-button>
            <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                       @click="handleRefresh">
                重置搜索
            </el-button>
        </sticky>

        <!--表格-->

        <el-table
                v-loading="loading"
                :data="tableData"
                stripe
                style="width: 100%">
            <el-table-column
                    type="index"
                    >
            </el-table-column>
            <el-table-column
                    sortable
                    prop="time"
                    label="日期"
                    width="180">
                <template slot-scope="{row}">
                    <span>{{formatTimeA(row.time) }}</span>
                </template>
            </el-table-column>

            <el-table-column
                    sortable
                    prop="title"
                    label="标题"
                    width="180">
            </el-table-column>

            <el-table-column
                    sortable
                    prop="from"
                    label="来源"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="content"
                    label="内容">
                <template slot-scope="{row}">
                    <span>{{row.content.substr(0,20)}}...</span>
                </template>
            </el-table-column>


            <el-table-column label="操作" align="center" width="350px" class-name="small-padding fixed-width">
                <template slot-scope="{row}">
                    <router-link :to="'/announcement/edit/'+row.id">

                        <el-button type="primary" size="mini" icon="el-icon-edit">
                            编辑
                        </el-button>

                    </router-link>

                    <router-link :to="'/announcement/detail/'+row.id">
                        <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading">
                            详情
                        </el-button>
                    </router-link>

                    <el-button :loading="buttonLoading" style="margin-left: 10px;" v-if="row.status ==='1'" size="mini"
                               type="success"
                               @click="handleModifyStatus(row.id,'0')">
                        发布
                    </el-button>

                    <el-button :loading="buttonLoading" style="margin-left: 10px;" v-if="row.status=== '0'" size="mini"
                               @click="handleModifyStatus(row.id,'1')">
                        草稿
                    </el-button>

                    <el-button style="margin-left: 10px;" v-if="row.status!=='deleted'" size="mini" type="danger"
                               @click="handleDelete(row.id)">
                        删除
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

        <el-tooltip placement="top" content="返回顶部">
            <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
        </el-tooltip>
    </div>
</template>

<script>
    import Sticky from "../../components/Sticky/index";
    import BackToTop from "../../components/BackTop/index";
    import {getJson, postFrom, postJson} from "../../api/api";
    import {parseTime} from '../../utils/index'

    export default {
        name: "list",
        components: {BackToTop, Sticky},
        data() {
            return {
                tableData: null,
                loading: true,
                college: null,
                temType: null,
                temTitle:null,
                buttonLoading: false,
                tempA: null,
                page: {
                    size: 20,
                    page: 0,
                    type: null,
                    title: null,
                    from: null,
                    totalElements: 100,
                },
            }
        },
        mounted() {
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题:" + error)
                });
            postFrom('public/announcement/findAll', this.page)
                .then(response => {
                    this.tableData = response.data.content;
                    this.page.totalElements = response.data.totalElements;
                    this.loading = false;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题:" + error)
                })
        },
        created() {


        },
        methods: {
            async handleModifyStatus(id, status) {
                await getJson("/public/announcement/findById/" + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.tempA = response.data.data;
                        } else if (response.data.status === 404) {
                            this.$notify.warning({
                                title: '警告',
                                message: '这个个公告找不到,可能已经被删除'
                            })
                        }
                    });
                this.tempA.status = status;
                this.loading = true;
                await postJson('/tea/announcement/save', this.tempA)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.announcement = response.data.result;
                            this.$notify.success({
                                title: '成功',
                                message: '操作成功'
                            })
                        } else {
                            this.$notify.error({
                                title: '错误',
                                message: '发生了一些错误'
                            })
                        }
                    })
                    .catch(error => {
                        this.$notify.error({
                            title: '错误',
                            message: error
                        })
                    });
                this.getDataPage();
            },
            handleDelete(id) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/announcement/deleteById/' + id)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify({
                                    title: '成功',
                                    message: '删除成功',
                                    type: 'success'
                                });
                                this.getDataPage();
                            }
                        })
                        .catch(error => {
                            this.$message.error("出现了一些问题" + error)
                        });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            querySearchAsync(queryString, cb) {
                getJson('/public/announcement/findType')
                    .then(response => {
                        this.temType = response.data.data;
                    });
                const temType = this.temType;
                const res = queryString ? temType.filter(this.createTypeFilter(queryString)) : temType;

                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(res);
                }, 3000 * Math.random());
            },
            createTypeFilter(queryString) {
                return (temType) => {
                    return (temType.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                };
            },
            querySearchAsyncTitle(queryString, cb){
                getJson('/public/announcement/findTitle')
                .then(response=>{
                    this.temTitle=response.data.data;
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

            handleSelectionChange(val) {
                this.multipleSelection = val
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

                await postFrom('public/announcement/findAll', this.page)
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
    .center {
        text-align: center;
        align-content: center;
    }
</style>
