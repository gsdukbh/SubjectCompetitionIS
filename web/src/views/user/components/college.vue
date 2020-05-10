<template>
    <div class="top main">

        <el-tabs type="card" @tab-click="handleClick" value="first">
            <el-tab-pane label="创建学院信息" name="first">
                <el-form style="width: 50%;margin-left: 25%;margin-right: 25%" label-position="top" :model="college"
                         :rules="rulesA" status-icon ref="college">
                    <el-form-item label="学院名称 " prop="name">
                        <el-input v-model="college.name"></el-input>
                    </el-form-item>

                    <el-button style="width: 20%" type="primary" :loading="buttonLoading"
                               @click="upCollegeInfo('college')">
                        提交
                    </el-button>

                    <el-button style="width: 20%" @click="resetForm('college')">
                        重置
                    </el-button>
                </el-form>
            </el-tab-pane>


            <el-tab-pane label="学院信息" name="second">
                <div style="margin-top: 10px;">

                    <el-input placeholder="名称" style="width: 200px;" v-model="page.name"/>

                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-search"
                               @click="handleFilter">
                        搜索
                    </el-button>

                    <el-button class="filter-item" type="primary" style="margin-left: 10px;" icon="el-icon-refresh-left"
                               @click="handleRefresh">
                        重置搜索
                    </el-button>

                    <el-button :loading="loading2" class="filter-item" type="danger" style="margin-left: 10px;"
                               icon="el-icon-delete"
                               @click="deleteList()">
                        批量删除
                    </el-button>
                </div>


                <el-table
                        :data="colleges"
                        border
                        @selection-change="handleSelectionChange"
                        v-loading="loading"
                        style="width: 100%;margin-top: 10px;">

                    <el-table-column
                            type="selection"
                            width="55">
                    </el-table-column>

                    <el-table-column
                            prop="id"
                            label="ID"
                            width="100"
                            sortable
                    >
                    </el-table-column>

                    <el-table-column
                            prop="name"
                            sortable
                            label="名称"
                    >
                    </el-table-column>


                    <el-table-column label="操作" align="center" width="450px" class-name="small-padding fixed-width">
                        <template slot-scope="{row}">

                            <el-button type="primary" size="mini" icon="el-icon-edit">
                                编辑
                            </el-button>

                            <el-button style="margin-left: 10px;" v-if="row.status!=='deleted'" size="mini"
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

            </el-tab-pane>

        </el-tabs>


        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%"
        >
            <el-form :model="collegeEdit" :rules="rulesB">

            </el-form>


            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {postFrom, postJson} from "../../../api/api";

    export default {
        name: "college",
        data() {

            let validatePass = (rule, value, callback) => {
                const info = {
                    name: value
                };
                postFrom('/admin/college/repeat', info)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (result) {
                            callback(new Error('已经存在该信息'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                buttonLoading: false,
                dialogVisible: false,
                loading2: false,
                loading: false,
                multipleSelection: null,
                college: {
                    name: ''
                },
                page: {
                    size: 20,
                    page: 0,
                    name: null,
                    totalElements: 0,
                },
                colleges: [],
                collegeEdit: {
                    id: '',
                    name: ''
                },
                rulesB: {
                    name: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入名称', trigger: 'blur'},
                        {validator: validatePass, trigger: 'blur'}
                    ]
                },
                rulesA: {
                    name: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {validator: validatePass, trigger: 'blur'},
                        {required: true, message: '请输入名称', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {
            handleClick(tab) {
                console.log(tab.index)
            },
            handleDelete(value) {
                console.log(value)
                postJson('')
            },
            deleteList() {

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
                this.multipleSelection = val;
            },
            handleFilter() {
                /*搜索*/
                this.loading = true;
                this.getDataPage();
            },
            handleRefresh() {
                this.page.name = null;
                this.getDataPage();
            },

            getDataPage() {
                this.loading = true;
                postFrom('/tea/college/find', this.page)
                    .then(response => {
                        this.colleges = response.data.content;
                        this.page.totalElements = response.data.totalElements;
                        this.loading = false;
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error("出现了一些问题" + error)
                })
            },

            upCollegeInfo(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/admin/college/save', this.college)
                            .then(response => {
                                if (response.data.status === 200) {
                                    this.$notify.success({
                                        title: '提交成功'
                                    })
                                } else if (response.data.status === 0) {
                                    this.$notify.warning({
                                        title: '警告',
                                        message: response.data.message,
                                    })
                                }
                            })
                            .catch(error => {
                                this.$message.error("出现一些错误:" + error)
                            });
                        this.buttonLoading = false;
                    }
                })
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>
    .main {
        text-align: center;
        width: 80%;
        margin-left: 10%;
    }

    .center {
        text-align: center;
        margin-top: 20px;
    }
</style>
