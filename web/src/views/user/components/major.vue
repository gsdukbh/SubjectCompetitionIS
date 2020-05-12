<template>
    <div class="top content">

        <el-tabs type="card" @tab-click="handleClick" value="1">
            <el-tab-pane label="创建专业" name="1">

                <el-form :model="major" :rules="rules" class="form" label-position="top" status-icon ref="major">
                    <el-form-item label="专业名称" prop="name">
                        <el-input v-model="major.name" style="width: 100%"></el-input>
                    </el-form-item>
                    <el-form-item label="培养层次" prop="level">
                        <el-select v-model="major.level" placeholder="请选择" style="width: 100%">
                            <el-option label="本科" value="本科"></el-option>
                            <el-option label="专科" value="专科"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所属学院" prop="college">
                        <el-select v-model="major.college.id" filterable placeholder="请选择" style="width: 100%"
                        >
                            <el-option
                                    v-for="item in college"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-button style="width: 30%" type="primary" :loading="buttonLoading" @click="upMajor('major')">
                        提交
                    </el-button>

                    <el-button style="width: 30%" @click="resetForm('major')">
                        重置
                    </el-button>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="专业信息管理" name="2">

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
                        :data="majors"

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
                            label="专业名称"
                    >
                    </el-table-column>


                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="{row}">

                            <el-button type="primary"
                                       size="mini"
                                       @click="edit(row)"
                                       icon="el-icon-edit">
                                修改
                            </el-button>

                            <el-button style="margin-left: 10px;"
                                       size="mini"
                                       type="danger"
                                       :loading="buttonLoading"
                                       @click="handleDelete(row)">
                                删除
                            </el-button>
                        </template>

                    </el-table-column>

                </el-table>


                <el-dialog
                        title="提示"
                        :visible.sync="dialogVisible"
                        width="30%"
                >
                    <el-form :model="majorEdit"
                             :rules="rulesB"
                             status-icon
                             label-position="top"
                             ref="majorEdit">

                        <el-form-item label="专业名称" prop="name">
                            <el-input v-model="majorEdit.name"></el-input>
                        </el-form-item>
                        <el-form-item label="培养层次" prop="level">
                            <el-select v-model="majorEdit.level" placeholder="请选择" style="width: 100%">
                                <el-option label="本科" value="本科"></el-option>
                                <el-option label="专科" value="专科"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>


                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogVisible = false">取 消</el-button>
                        <el-button type="primary" @click="updataMajor()" :loading="buttonLoading">确 定</el-button>
                    </div>
                </el-dialog>


                <div class="center">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            @next-click="handleCurrentChange"
                            @prev-click="handleCurrentChange"
                            :current-page="page.page"
                            :page-sizes="[5,10,20,50,100]"
                            :page-size="page.size"
                            background
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="page.totalElements">
                    </el-pagination>

                </div>

                <el-tooltip placement="top" content="返回顶部">
                    <back-to-top :visibility-height="300" :back-position="50" transition-name="fade"/>
                </el-tooltip>


            </el-tab-pane>

        </el-tabs>

    </div>
</template>

<script>
    import {getJson, postFrom, postJson} from "../../../api/api";
    import BackToTop from "../../../components/BackTop/index";

    export default {
        name: "major",
        components: {BackToTop},
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
            let validatePass1 = (rule, value, callback) => {
                const info = {
                    name: value
                };
                postFrom('/admin/college/repeat', info)
                    .then(response => {
                        let result = response.data.data === 'true';
                        if (this.majorEdit.id === response.data.MID && result) {
                            callback()
                        } else if (result) {
                            callback(new Error('已经存在该信息'));
                        } else {
                            callback()
                        }
                    });
            };
            return {
                dialogVisible: false,
                college: [],
                buttonLoading: false,
                loading2: false,
                loading: false,
                multipleSelection: [],
                major: {
                    name: '',
                    level: '',
                    college: {
                        id: ''
                    }
                },
                majorEdit: {
                    id: '',
                    name: '',
                    level: '',
                    college: {
                        id: ''
                    }
                },
                majors: [],
                page: {
                    size: 20,
                    page: 0,
                    name: null,
                    totalElements: 0,
                },
                rules: {
                    name: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入', trigger: 'change'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    level: [
                        {required: true, message: '请选择', trigger: 'change'}
                    ],
                    id: [
                        {required: true, message: '请选择', trigger: 'change'}
                    ]
                },
                rulesB: {
                    name: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入', trigger: 'change'},
                        {validator: validatePass1, trigger: 'blur'}
                    ],
                }
            }
        },
        created() {
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
        },
        methods: {
            handleDelete(value) {
                console.log(value)
                postJson('/admin/major/del', value)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                                message: '删除成功'
                            });
                        } else {
                            this.$notify.error({
                                title: '警告',
                                message: response.data.message
                            })
                        }
                    })
            },
            edit(value) {
                this.majorEdit = value;
                this.dialogVisible = true;
                console.log(this.collegeEdit)
            },
            updataMajor() {
                this.buttonLoading = true;
                postJson('/admin/major/save', this.majorEdit)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.$notify.success({
                                title: '成功',
                                message: '修改成功'
                            })
                            this.buttonLoading = false;
                            this.getDataPage();
                        }
                    }).catch(error => {
                    this.buttonLoading = false;
                    this.getDataPage();
                    this.$message.error('出现一些错误' + error)
                })
            },
            handleClick(tab) {
                console.log(tab.index);
                if (tab.index === '1') {
                    this.getDataPage();
                }
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
            deleteList() {
                this.loading2 = true;
                postJson('/admin/major/delAll', this.multipleSelection)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.loading2 = false;
                            this.$notify.success({
                                title: '成功',
                                message: '删除成功'
                            })
                        } else {
                            this.loading2 = false;
                            this.$notify.error({
                                title: '警告',
                                message: response.data.message
                            })
                        }
                    }).catch(error => {
                    this.loading2 = false;
                    this.$message.error('出现了一些错误' + error);
                })
            },
            getDataPage() {
                this.loading = true;
                postFrom('/admin/major/find', this.page)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.majors = response.data.content;
                            this.page.totalElements = response.data.totalElements;
                            this.loading = false;
                        }
                    }).catch(error => {
                    this.loading = false;
                    this.$message.error('出现了一些问题：' + error);
                })
            },
            upMajor(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        postJson('/admin/major/save', this.major)
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
                                this.buttonLoading = false;
                            }).catch(error => {
                            this.$message.error("出现一些错误:" + error);
                            this.buttonLoading = false;
                        });
                    }
                });
                this.buttonLoading = false;
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        }
    }
</script>

<style scoped>

    .content {
        align-content: center;
        text-align: center;
        width: 50%;
        margin-left: 25%;
        margin-right: 25%;
    }

    .form {
        width: 50%;
        margin-left: 25%;
        margin-right: 25%;
    }

    .center {
        text-align: center;
        margin-top: 20px;
    }
</style>
