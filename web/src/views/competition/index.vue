<template>
    <div>
        <!--搜索-->
        <div>

            <el-input placeholder="名称" style="width: 200px;" v-model="search.name"/>

            <el-select v-model="search.organizer" filterable placeholder="请选择承办单位">
                <el-option
                        v-for="item in college"
                        :key="item.id"
                        :label="item.collegeName"
                        :value="item.collegeName">
                </el-option>
            </el-select>

            <el-button  class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
                搜索
            </el-button>
            <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit">
                添加赛事
            </el-button>
            <el-button  :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download"
                       @click="getExcel">
                导出
            </el-button>
        </div>

        <!--表格-->

        <el-table
                :data="tableData"
                border
                @select="handleSelectionChange"
                v-loading="loading"
                style="width: 100%">

            <el-table-column
                    type="selection"
                    @selection-change="handleSelectionChange"
                    width="55">
            </el-table-column>

            <el-table-column
                    prop="id"
                    label="ID"
                    sortable
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="startTime"
                    label="开始日期"
                    sortable
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="name"
                    sortable
                    label="名称"
                    width="180">
            </el-table-column>

            <el-table-column
                    sortable
                    prop="organizer"
                    label="承办单位"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="place"
                    label="举办地点">
            </el-table-column>

            <el-table-column label="操作" align="center" width="300" class-name="small-padding fixed-width">
                <template slot-scope="{row,$index}">
                    <router-link :to="''">
                        <el-button type="primary" size="mini">
                            编辑
                        </el-button>
                    </router-link>
                    <el-button v-if="row.status!=='published'" size="mini" type="success"
                               @click="handleModifyStatus(row,'published')">
                        发布
                    </el-button>
                    <el-button v-if="row.status!=='draft'" size="mini" @click="handleModifyStatus(row,'draft')">
                        草稿
                    </el-button>
                    <el-button v-if="row.status!=='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
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
    </div>
</template>

<script>
    import {getData, getJson, postFrom} from "../../api/api";

    export default {
        name: "index",
        data() {
            return {
                html: null,
                loading: true,
                tableData: [],
                page: {
                    size: 20,
                    page: 0,
                    totalElements: 100,
                },
                search:{
                    organizer:null,
                    name:null
                },
                college: {
                    id: '',
                    collegeName: '',
                },
                downloadLoading:false,
                multipleSelection: []
            }
        },
        created() {
            getData('/public/competition/findAll')
                .then(response => {
                    this.tableData = response.data.content;
                    this.page.totalElements = response.data.totalElements;
                    this.loading = false;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })


        },
        mounted() {
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
        },
        methods: {
            handleSizeChange(val) {
                this.page.size = val;
                this.loading = true;
               this.getDataPage();
            },
            handleCurrentChange(val) {
                /*页面切换*/
                this.page.size=val;
                this.getDataPage()
            },
            handleSelectionChange(val) {
                this.multipleSelection = val
            },
            handleFilter() {
                /*搜索*/

            },
            handleModifyStatus() {

            },
            handleDelete() {

            },
            getExcel(){
                /*获得excel文件*/

            },
            getDataPage(){
                postFrom('/public/competition/findAll', this.page)
                    .then(response => {
                        this.tableData = response.data.content;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;

                    }).catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
            }
        }

    }
</script>

<style scoped>
    .center {
        text-align: center;
        align-content: center;
    }
</style>
