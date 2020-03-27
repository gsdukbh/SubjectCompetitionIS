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
                    width="60">
            </el-table-column>

            <el-table-column
                    prop="startTime"
                    label="开始日期"
                    sortable
                    width="150">
                <template slot-scope="{row}">
                    <span>{{formatTimeA(row.startTime) }}</span>
                </template>
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

            <el-table-column

                    label="赛事级别"
                    width="100"
                    :filters="[
                    { text: '校级', value: '校级' },
                    { text: '院级', value: '院级' },
                    { text: '市级', value: '市级' },
                    { text: '区级/省级', value: '区级/省级' },
                    { text: '国级', value: '国级' }]"
                    :filter-method="filterTag"
                    filter-placement="bottom-end">
                <template slot-scope="{row}">
                    <el-tag
                            :type="row.level === '校级' ? 'primary' : 'success'"
                            disable-transitions>{{row.level}}
                    </el-tag>
                </template>

            </el-table-column>


            <el-table-column label="操作" align="center" width="350px" class-name="small-padding fixed-width">
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

                    <el-button :loading="loading1" style="margin-left: 10px;" v-if="row.status ==='草稿'" size="mini" type="success"
                               @click="handleModifyStatus(row.id,'公开')">
                        发布
                    </el-button>

                    <el-button :loading="loading1" style="margin-left: 10px;" v-if="row.status==='公开'" size="mini"
                               @click="handleModifyStatus(row.id,'草稿')">
                        草稿
                    </el-button>

                    <el-button disabled style="margin-left: 10px;" v-if="row.status==='进行中'" size="mini">
                        进行中
                    </el-button>

                    <el-button disabled style="margin-left: 10px;" v-if="row.status==='已结束'" size="mini">
                        已结束
                    </el-button>

                    <el-button style="margin-left: 10px;" v-if="row.status!=='deleted'" size="mini" type="danger"
                               @click="handleDelete(row.id,$index)">
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
    import {getData, getJson, postFrom, postJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    export default {
        name: "index",
        data() {
            return {
                html: null,
                loading: true,
                loading1:false,
                tableData: [
                    {
                        id: null,
                        name: null,
                        status: null,
                        startTime: null,
                        endTime: null,
                        applyTime:null,
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
                temSave: null,
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

        },
        mounted() {
            getData('/public/competition/findAll')
                .then(response => {
                    this.tableData = response.data.content;
                    this.page.totalElements = response.data.totalElements;
                    this.loading = false;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
        },
        methods: {
            formatTimeA(time){
                return parseTime(time,'{y}-{m}-{d} {h}:{i}')
            },
            formatTimeB(time){
                return parseTime(time)
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
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
            },
            filterTag(value, row) {
                return row.type === value;
            },
            handleModifyStatus(id, status) {
                this.loading1=true;
                getJson('/public/competition/findById/' + id)
                                .then(response => {
                                    if (response.data.status === 200) {
                                        this.temSave = response.data.data;
                                        this.temSave.status = status;
                                        postJson('/tea/competition/save', this.temSave)
                                            .then(response => {
                                                if (response.data.status === 200) {
                                                    this.$notify({
                                                        title: '成功',
                                                        message: '更改成功',
                                                        type: 'success'
                                                    });
                                                    this.getDataPage();
                                                    this.loading1=false;
                                                }
                                            })
                                            .catch(error => {
                                                this.loading1=false;
                                                this.$message.error("出现了一些问题" + error)
                                            })
                                    }
                    })
                    .catch(error => {
                        this.loading1=false;
                        this.$message.error("出现了一些问题" + error)
                    });
            },
            handleDelete(id, index) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/competition/deleteById/' + id)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify({
                                    title: '成功',
                                    message: '删除成功',
                                    type: 'success'
                                });
                                this.tableData.splice(index, 1);
                                this.page.totalElements -= 1;
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

            getExcel() {
                /*获得excel文件*/
                this.downloadLoading = true;
                import('../../utils/excel').then(excel => {
                    const tHeader = ['id', '竞赛名称', '状态', '开始时间', '结束时间', '报名时间','详细内容', '发布者', '赛事级别', '承办单位', '举办地点', '人数限制', '比赛类型', '是否团队赛'];
                    const file = [
                        "id",
                        "name",
                        "status",
                        "startTime",
                        "endTime",
                        "applyTime",
                        "content",
                        "author",
                        "level",
                        "organizer",
                        "numLimit",
                        "place",
                        "type",
                        "team"
                    ];
                    const data = this.jsonToArray(file);
                    excel.export_json_to_excel({
                        header: tHeader, //表头 必填
                        data, //具体数据 必填
                        filename: 'excel-list', //非必填
                    });
                    this.downloadLoading = false;
                })
            },
            jsonToArray(data) {
                if (this.multipleSelection !== null) {
                    return this.multipleSelection.map(v => data.map(j => {
                        return v[j]
                    }))
                } else {
                    return this.tableData.map(v => data.map(j => {
                        return v[j]
                    }))
                }
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
