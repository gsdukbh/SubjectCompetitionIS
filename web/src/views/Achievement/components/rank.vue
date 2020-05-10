<template>
    <div>


        <div style="margin-top: 10px;">

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
            <el-button class="filter-item"
                       icon="el-icon-delete"
                       type="danger"
                       :loading="loadingButton1"
                       @click="deleteInfo() "
                       style="margin-left: 10px;">
                批量删除
            </el-button>

        </div>

        <el-table
                v-loading="loading"
                :data="content"
                @selection-change="handleSelectionChange"
                style="width: 100%">
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="scoreUpTime"
                    label=更新时间
                    width="180">
                <template slot-scope="{row}">
                    {{formatTimeA(row.scoreUpTime)}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="scisUser"
                    label="姓名"
                    width="100">
                <template slot-scope="{row}">
                    {{row.scisUser.name}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="scisUser"
                    label="学院">
                <template slot-scope="{row}">
                    {{row.scisUser.scisClass.major.college.name}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="scisUser"
                    label="班级">
                <template slot-scope="{row}">
                    {{row.scisUser.scisClass.name}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="works"
                    label="作品">
                <template slot-scope="{row}">
                    <router-link :to="'/works/detail/All/'+row.works.id">
                        <el-link type="primary">
                            {{row.works.name}}
                        </el-link>
                    </router-link>
                </template>
            </el-table-column>

            <el-table-column
                    sortable
                    prop="score"
                    label="成绩分数">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="gradesanking"
                    label="排名">
            </el-table-column>

            <el-table-column label="操作" align="center" width="180px" class-name="small-padding fixed-width">
                <template slot-scope="{row}">

                    <el-button type="primary" size="mini"
                               icon="el-icon-edit"
                               @click="editInfo(row)"
                    >
                        修改
                    </el-button>

                    <el-button style="margin-left: 10px;" size="mini"
                               :loading="loadingButton"
                               type="danger"
                               @click="handleDelete(row)">
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


        <el-dialog
                title="修改分数"
                :visible.sync="dialogVisible"
                width="30%"
        >
            <el-form ref="upScore" :model="score" :rules="rules">
                <!--                <el-form-item label="排名" prop="gradesanking">-->
                <!--                    <el-input v-model="score.gradesanking"></el-input>-->
                <!--                </el-form-item>-->
                <el-form-item label="分数" prop="score">
                    <el-input-number v-model="score.score"></el-input-number>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
         <el-button type="primary" @click="edit">确 定</el-button>
  </span>
        </el-dialog>
    </div>

</template>

<script>
    import {postFrom, postJson} from "../../../api/api";
    import {parseTime} from '../../../utils'

    export default {
        name: "rank",
        data() {
            return {
                type: null,
                content: [],
                delData: [],
                dialogVisible: false,
                score: {
                    score: null,
                    gradesanking: null
                },
                loadingButton: false,
                loadingButton1: false,
                id: '',
                loading: false,
                page: {
                    size: 20,
                    page: 0,
                    value: null,
                    left: null,
                    right: null,
                    totalElements: 0,
                },
                rules: {
                    score: [
                        {required: true, message: '请输入', trigger: 'blur'},
                    ],
                    gradesanking: [
                        {required: true, message: '请输入', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {

            edit() {
                this.loadingButton1 = true;
                this.$confirm('是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/score/modifyInfo/4', this.score)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify({
                                    title: '成功',
                                    message: '修改成功',
                                    type: 'success'
                                });
                                // this.tableData.splice(index, 1);
                                // this.page.totalElements -= 1;
                                this.getDataPage();
                            }
                            this.loadingButton1 = false;
                        })
                        .catch(error => {
                            this.loadingButton1 = false;
                            this.$message.error("出现了一些问题" + error)
                        });
                }).catch(() => {
                    this.loadingButton1 = false;
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },

            editInfo(value) {
                this.score = value;
                this.dialogVisible = true
            },
            handleDelete(value) {
                this.loadingButton = true;
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/score/delInfo', value)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify({
                                    title: '成功',
                                    message: '删除成功',
                                    type: 'success'
                                });
                                // this.tableData.splice(index, 1);
                                // this.page.totalElements -= 1;
                                this.getDataPage();
                            }
                            this.loadingButton = false;
                        })
                        .catch(error => {
                            this.loadingButton = false;
                            this.$message.error("出现了一些问题" + error)
                        });
                }).catch(() => {
                    this.loadingButton = false;
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            deleteInfo() {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/score/delInfoAll', this.delData)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$notify({
                                    title: '成功',
                                    message: '删除成功',
                                    type: 'success'
                                });
                                // this.tableData.splice(index, 1);
                                // this.page.totalElements -= 1;
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

            handleSelectionChange(value) {
                this.delData = value;
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
            getInfo(value) {
                this.id = value.id;
                this.loading = true;
                this.getDataPage();
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
            getDataPage() {
                postFrom('/public/score/findAll/' + this.id, this.page)
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
                console.log(this.type)
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
                console.log(this.page)
                this.getDataPage();
            },
            handleRefresh() {
                this.page.value = null;
                this.left = null;
                this.right = null;
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
