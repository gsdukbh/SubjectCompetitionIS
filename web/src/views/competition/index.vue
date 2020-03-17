<template>
    <div>
        <!--搜索-->
        <div>

            <el-input placeholder="名称" style="width: 200px;" v-model="page.name"/>

            <el-select v-model="page.organizer" filterable placeholder="请选择承办单位">
                <el-option
                        v-for="item in college"
                        :key="item.id"
                        :label="item.collegeName"
                        :value="item.collegeName">
                </el-option>
            </el-select>

            <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
                搜索
            </el-button>

            <router-link :to="'/competition/publish'">
                <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit">
                    添加赛事
                </el-button>
            </router-link>

            <el-tooltip content="请选择要导出的信息，默认当前页" placement="bottom">
                <el-button :loading="downloadLoading" style="margin-left: 10px;" class="filter-item" type="primary"
                           icon="el-icon-download"
                           @click="getExcel">
                    导出
                </el-button>


            </el-tooltip>

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

            <el-table-column label="操作" align="center" width="350" class-name="small-padding fixed-width">
                <template slot-scope="{row,$index}">
                    <router-link :to="'/competition/edit/'+row.id">

                        <el-button type="primary" size="mini" icon="el-icon-edit">
                            编辑
                        </el-button>

                    </router-link>

                    <router-link :to="'/competition/detail/'+row.id">
                        <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-reading">
                            详情
                        </el-button>
                    </router-link>

                    <el-button style="margin-left: 10px;" v-if="row.status!=='published'" size="mini" type="success"
                               @click="handleModifyStatus(row,'published')">
                        发布
                    </el-button>
                    <el-button style="margin-left: 10px;" v-if="row.status!=='draft'" size="mini"
                               @click="handleModifyStatus(row,'draft')">
                        草稿
                    </el-button>
                    <el-button v-if="row.status!=='deleted'" size="mini" type="danger"
                               @click="handleDelete(row,$index)">
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
                tableData: [
                    {
                        id: null,
                        name: null,
                        status: null,
                        startTime: null,
                        endTime: null,
                        content: null,
                        author: null,
                        level: null,
                        organizer: null,
                        place: null,
                        numLimit: null,
                        type: null,
                        team: null
                    }
                ],
                page: {
                    size: 20,
                    page: 0,
                    organizer: null,
                    name: null,
                    totalElements: 100,
                },
                college: {
                    id: '',
                    collegeName: '',
                },
                downloadLoading: false,
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
                this.page.size = val;
                this.getDataPage()
            },
            handleSelectionChange(val) {
                this.multipleSelection = val
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
            },
            handleModifyStatus() {

            },
            handleDelete() {

            },
            getExcel() {
                /*获得excel文件*/
                this.downloadLoading = true;
                import('../../utils/excel').then(excel=>{
                    const  tHeader=['id','竞赛名称','状态','开始时间','结束时间','详细内容','发布者','赛事级别','承办单位','举办地点','人数限制','比赛类型','是否团队赛'];
                    const file = [
                        "id",
                        "name",
                        "status",
                        "startTime",
                        "endTime",
                        "content",
                        "author",
                        "level",
                        "organizer",
                        "numLimit",
                        "place",
                        "type",
                        "team"
                    ]
                    const data=this.jsonToArray(file);
                    excel.export_json_to_excel({
                        header: tHeader, //表头 必填
                        data, //具体数据 必填
                        filename: 'excel-list', //非必填
                    })
                    this.downloadLoading = false;
                })
            },
            jsonToArray(data) {
                return this.tableData.map(v => data.map(j => {
                    return v[j]
                }))
            },
            getDataPage() {
                postFrom('/public/competition/findAll', this.page)
                    .then(response => {
                        this.tableData = response.data.content;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;
                    }).catch(error => {
                    this.loading = false;
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
