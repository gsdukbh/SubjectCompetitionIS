<template>

    <div class="center">

        <el-card v-loading="loading" v-if="Page404===false" shadow="hover" class="card">
            <!--card 头-->
            <div slot="header" class="clearfix">
                <span>填写信息</span>

            </div>

            <el-steps :active="item" align-center>
                <el-step title="基本信息" icon="el-icon-edit"></el-step>
                <el-step title="详情描述" icon="el-icon-edit-outline"></el-step>
                <el-step title="公告" icon="el-icon-finished"></el-step>
            </el-steps>
            <el-divider></el-divider>

            <div v-if="item ===1">
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="30%" class="demo-ruleForm">

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="竞赛名称" prop="name">

                                <el-tooltip class="item" content="为了避免重复，请使用第**届，作为前缀命名" placement="top-start">
                                    <el-input v-model="ruleForm.name" style="width: 80%"></el-input>
                                </el-tooltip>

                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="竞赛级别" prop="level">
                                <el-select v-model="ruleForm.level" placeholder="请选择竞赛级别">
                                    <el-option label="校级" value="校级"></el-option>
                                    <el-option label="院级" value="院级"></el-option>
                                    <el-option label="市级" value="市级"></el-option>
                                    <el-option label="区级/省级" value="区级/省级"></el-option>
                                    <el-option label="国级" value="国级"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="承办单位" prop="organizer">

                                <el-select v-model="ruleForm.organizer" placeholder="请选择举办单位">
                                    <div v-for="item in college" v-bind:key="item.id">
                                        <el-option :label=item.name :value=item.name></el-option>
                                    </div>
                                </el-select>

                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="竞赛开始时间" required>

                                <el-form-item prop="startTime">
                                    <el-date-picker type="datetime" placeholder="选择日期时间" v-model="ruleForm.startTime"

                                                    style="width: 50%;"></el-date-picker>
                                </el-form-item>

                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="竞赛结束时间" prop="endTime">
                                <el-date-picker type="datetime" placeholder="选择日期时间" v-model="ruleForm.endTime"

                                                style="width: 50%;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="竞赛报名时间" prop="applyTime">

                                <el-date-picker type="datetime" placeholder="选择日期" v-model="ruleForm.applyTime"

                                                style="width: 50%;">

                                </el-date-picker>

                            </el-form-item>
                        </el-col>
                        <el-col :span="12">

                            <el-form-item label="竞赛报名结束时间" prop="applyStop">

                                <el-date-picker type="datetime" placeholder="选择日期" v-model="ruleForm.applyStop"

                                                style="width: 50%;">

                                </el-date-picker>

                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="一等奖人数" prop="firstPrize">
                                <el-input-number v-model="ruleForm.firstPrize"
                                                 controls-position="right"
                                                 :min="1">
                                </el-input-number>
                            </el-form-item>

                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="二等奖人数" prop="secondPrize">
                                <el-input-number v-model="ruleForm.secondPrize"
                                                 controls-position="right"
                                                 :min="1">
                                </el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="三等奖人数" prop="thirdPrize">
                                <el-input-number v-model="ruleForm.thirdPrize"
                                                 controls-position="right"
                                                 :min="1">
                                </el-input-number>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="状态" prop="status">
                                <el-select v-model="ruleForm.status" placeholder="请选择">
                                    <el-option label="草稿" value="草稿"></el-option>
                                    <el-option label="公开" value="公开"></el-option>
                                    <el-option label="进行中" disabled value="进行中"></el-option>
                                    <el-option label="已结束" disabled value="已结束"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="举办形式" prop="type">
                                <el-radio-group v-model="ruleForm.type">
                                    <el-radio label="offline">线下比赛</el-radio>
                                    <el-radio label="online">线上比赛（提交作品）</el-radio>
                                </el-radio-group>

                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="举办地点" v-if="ruleForm.type === 'offline'">
                                <el-input
                                        placeholder="请输入内容"
                                        v-model="ruleForm.place"
                                        style="width: 50%">
                                </el-input>
                            </el-form-item>
                        </el-col>

                        <!--                       <el-col :span="12" >-->
                        <!--                    <el-form-item label="团队比赛">-->
                        <!--                        <el-switch-->
                        <!--                                v-model="ruleForm.team"-->
                        <!--                                active-text="团队比赛"-->
                        <!--                                inactive-text="个人比赛">-->
                        <!--                        </el-switch>-->
                        <!--                    </el-form-item>-->
                        <!--                       </el-col>-->
                        <!--                       <el-col :span="12">-->
                        <!--                    <el-form-item label="人数限制" prop="type" v-if="ruleForm.team">-->
                        <!--                        <el-input-number v-model="ruleForm.numLimit" :min="1" :max="10" label="描述文字"></el-input-number>-->
                        <!--                    </el-form-item>-->
                        <!--                       </el-col>-->

                        <el-col :span="12">

                            <el-form-item label="负责人" prop="principal">

                                <el-tooltip class="item" content="请从下面的搜索结果输入" placement="top-start">
                                    <el-autocomplete
                                            style="width: 50%"
                                            v-model="ruleForm.principal"
                                            :fetch-suggestions="querySearchAsync"
                                            placeholder="请输入内容"
                                            @select="handleSelect"
                                    ></el-autocomplete>
                                </el-tooltip>
                            </el-form-item>
                        </el-col>


                    </el-row>

                    <el-form-item>

                        <el-button type="primary" @click="next('ruleForm')">下一步</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div v-if="item === 2">
                <el-button style="float: right; padding: 3px " round icon="el-icon-check" type="primary"
                           @click="submit()">提交
                </el-button>
                <el-tag size="mini">详细内容</el-tag>
                <span style="font-size: 14px"> 使用markdown编辑  <el-link target="_blank"
                                                                      style="margin-left: 10px"
                                                                      href="https://github.com/nhn/tui.editor"
                                                                      type="info"> 详 情 </el-link> </span>
                <el-button size="mini" type="success" style="margin-left: 10px" @click="annex = true"><i
                        class="el-icon-upload2">上传附件</i></el-button>
                <el-link v-if="ruleForm.objectName!=null" style="margin-left: 10px" type="primary" @click="dl()">附件：<i
                        class="el-icon-download"></i>下载
                </el-link>
                <markdown-editor ref="markdownEditor" title="请输入详细内容 " v-bind:content="ruleForm.content"
                                 height="600px"/>

            </div>

            <div v-if="item === 3">
                <div class="info">
                    <el-alert

                            title="竞赛信息发布成功！！！赶紧去发布公告吧"
                            type="success">
                    </el-alert>
                    <router-link>
                        <el-button class="button" type="primary" round>前往发布</el-button>
                    </router-link>
                </div>

            </div>

            <el-dialog
                    center
                    title="提示"
                    :visible.sync="annex"
                    width="30%"
            >
                <el-upload
                        style="margin-left: 15%"
                        drag
                        class="upload-demo"
                        ref="upload"
                        action="/api/i/upFile/annex"
                        :on-success="upSuccess"
                        :auto-upload="false">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>

                </el-upload>

                <span slot="footer" class="dialog-footer">
                                 <el-button @click="annex = false" round>关闭</el-button>
                              <el-button round type="primary" @click="submitUpload">上 传</el-button>
                        </span>
            </el-dialog>
        </el-card>
        <page404 v-loading="loading" v-if="Page404 ===true"></page404>
    </div>
</template>

<script>
    import MarkdownEditor from '@/components/MarkdownEditor';
    import {getJson, postJson} from "../../api/api";
    import Page404 from '../error-page/404';
    import qs from 'qs';

    export default {
        name: "edit",
        components: {Page404, MarkdownEditor},
        data() {
            const validateRequire = (rule, value, callback) => {
                if (value > this.ruleForm.startTime) {
                    callback(new Error('竞赛报名结束时间不能在竞赛开始时间之后，请重新输入'))
                } else if (value < this.ruleForm.applyTime) {
                    callback(new Error('报名结束时间不能在报名开始时间之前，请重新输入'))
                } else {
                    callback()
                }
            };
            const validateRequire1 = (rule, value, callback) => {
                if (value > this.ruleForm.startTime) {
                    callback(new Error('报名时间不能在开始时间之后！请重新输入。'))
                } else {
                    callback()
                }
            }
            const validateRequire2 = (rule, value, callback) => {
                if (value < this.ruleForm.startTime) {
                    callback(new Error('竞赛结束时间不能在开始时间之前，请重新输入！'))
                } else {
                    callback()
                }
            }
            return {
                annex: false,
                id: '',
                Page404: null,
                loading: true,
                PageLoading: null,
                ruleForm: {
                    name: '',
                    level: '',
                    startTime: null,
                    endTime: null,
                    applyTime: null,
                    applyStop: null,
                    organizer: '',
                    status: '',
                    type: '',
                    author: '',
                    principal: '',
                    content: '',
                    numLimit: 1,
                    firstPrize: null,
                    secondPrize: null,
                    thirdPrize: null,
                    place: '',
                    team: false,
                    bucketName: null,
                    objectName: null,
                    user: {
                        id: null,
                    }
                },
                item: 1,
                college: {
                    id: '',
                    collegeName: '',
                },
                rules: {
                    firstPrize: [
                        {required: true, message: '该值不能为空'},
                    ],
                    secondPrize: [
                        {required: true, message: '该值不能为空'},
                    ],
                    thirdPrize: [
                        {required: true, message: '该值不能为空'},
                    ],
                    name: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入活动名称', trigger: 'blur'}
                    ],
                    level: [
                        {required: true, message: '请选择竞赛级别', trigger: 'change'}
                    ],
                    startTime: [
                        {required: true, message: '请选择日期', trigger: 'change'}
                    ],
                    endTime: [
                        {validator: validateRequire2},
                        {required: true, message: '请选择结束日期', trigger: 'change'}
                    ],
                    organizer: [
                        {required: true, message: '请选择举办单位', trigger: 'change'}
                    ],
                    status: [{required: true, message: '请选择', trigger: 'change'}],

                    principal: [
                        {max: 255, message: '长度在  255 个字符 以下', trigger: 'blur'},
                        {required: true, message: '请输入负责人', trigger: 'blur'},
                    ],
                    applyTime: [
                        {validator: validateRequire1},
                        {required: true, message: '请选择开始报名时间', trigger: 'change'}
                    ],
                    applyStop: [
                        {validator: validateRequire},
                        {required: true, message: '请选择报名结束时间', trigger: 'change'},

                    ],
                    type: [
                        {
                            required: true, message: '请选择', trigger: 'change'
                        }],

                }
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
            getJson('/public/college/findAll')
                .then(response => {
                    this.college = response.data.content;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                })
            getJson('/tea/user/findByRoleName/teacher')
                .then(response => {
                    this.userInfo = response.data.userInfo;
                })
                .catch(error => {
                    this.$message.error("出现了一些问题" + error)
                });
            this.PageLoading = this.$loading({
                lock: true,
                text: '拼命加载中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        },
        mounted() {

        },
        methods: {
            dl() {
                const download = {
                    bucketName: this.ruleForm.bucketName,
                    objectName: this.ruleForm.objectName
                }
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(download);
                a.download = download.objectName;
                a.target = "_blank";
                a.click();
            },
            submitUpload() {

                this.$refs.upload.submit();

            },
            upSuccess(response) {
                if (response.status === 200) {
                    this.$notify.success({
                        title: '成功',
                        message: '上传成功'
                    })
                    this.ruleForm.objectName = response.objectName;
                    this.ruleForm.bucketName = response.bucketName;
                } else {

                    this.$message.error("上传失败");
                }
            },
            async fetchData(id) {
                await getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.ruleForm = response.data.data;
                            this.Page404 = false;
                            this.loading = false;
                            this.setTagsViewTitle();
                            this.setPageTitle();
                            this.PageLoading.close();
                        } else if (response.data.status === 404) {
                            this.loading = false;
                            this.Page404 = true;
                            this.PageLoading.close();
                            this.$notify.warning({
                                title: '警告',
                                message: '资源不存在'
                            })
                        }
                    })
                    .catch(() => {
                    });

            },
            setTagsViewTitle() {
                const title = this.ruleForm.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title}-修改`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.ruleForm.name;
                document.title = `${title} - 修改`
            },
            querySearchAsync(queryString, cb) {
                getJson('/tea/user/findByRoleName/teacher').then(response => {
                    this.userInfo = response.data.userInfo;
                });
                const userInfo = this.userInfo;
                const res = queryString ? userInfo.filter(this.createStateFilter(queryString)) : userInfo;
                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(res);
                }, 3000 * Math.random());
            },
            createStateFilter(queryString) {
                return (userInfo) => {
                    return (userInfo.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                };
            },
            handleSelect(item) {
                this.ruleForm.user.id = item.id;
            },
            submit() {
                this.ruleForm.content = this.$refs.markdownEditor.getMarkdown();
                this.ruleForm.author = this.name;
                this.$confirm('确认提交, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    postJson('/tea/competition/save', this.ruleForm)
                        .then(response => {
                            if (response.data.status === 200) {
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.item = 3;
                            }
                        })
                        .catch(error => {
                            this.$message.error("出现了一些问题" + error)
                        });

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            next(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.ruleForm.startTime > this.ruleForm.endTime) {
                            this.$notify.error({
                                message: '竞赛开始时间不能再竞赛结束时间之后，请重新输入',
                                title: '错误',
                            });
                        } else if (this.ruleForm.startTime > this.ruleForm.endTime) {
                            this.$notify.error({
                                message: '开始时间不能再结束时间之后',
                                title: '错误',
                            });
                        } else if (this.ruleForm.applyStop > this.ruleForm.startTime) {
                            this.$notify.error({
                                message: '竞赛报名时间不能再竞赛开始时间之后，请重新输入',
                                title: '错误',
                            });
                        } else {
                            // this.$refs.markdownEditor.setValue( this.ruleForm.content);
                            this.item = 2;
                        }

                    } else {
                        this.$notify.error({
                            message: '请检查你的输入',
                            title: '错误',
                        });
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },


        }
    }
</script>

<style scoped>
    .card {
        align-items: center;
        width: 90%;
        margin: 0 auto;
        padding-top: 10px;
    }

    .info {

        align-content: center;
        text-align: center;
    }

    .button {
        margin-top: 20px;
    }

    .p {
        text-align: center;
    }
</style>
