<template>
    <div>
        <div class="main">
            <sticky :z-index="10" class-name="sub-navbar">


                <el-button
                        @click="toApply()"
                        style="margin-left: 10px;"
                        type="success" round>
                    立刻报名
                </el-button>

            </sticky>

            <!--标题-->
            <div class="title">
                <span>{{showData.name}}</span>
            </div>

            <el-divider><i class="el-icon-trophy-1"></i></el-divider>
            <div class="el-row">

                <div class="leftColumn">

                    <el-card class="box-card top left" shadow="hover">
                        <h1>内容详情
                            <svg-icon icon-class="content"></svg-icon>
                            <el-tag style="margin-left: 10px">{{showData.level}}</el-tag>
                            <el-tag style="margin-left: 10px" v-if="showData.type === 'online'">线上比赛</el-tag>

                        </h1>

                        <el-divider content-position="right">昨夜星辰昨夜风</el-divider>
                        <!--markdown-->

                        <el-row>

                            <markdown-viewer v-bind:content="showData.content"/>

                        </el-row>

                    </el-card>

                </div>


                <div class="rightColumn">
                    <el-card class="top right" shadow="hover">
                        <h3>
                            基本信息 <i class="el-icon-info"></i>
                        </h3>
                        <el-divider content-position="right"></el-divider>
                        <el-row>
                            <el-col :span="24">
                                <span>负责人：{{showData.principal}}</span>
                            </el-col>
                            <el-col :span="24">
                                <span>承办单位：{{showData.organizer}}</span>
                            </el-col>
                            <el-col :span="24" v-if="showData.place !==''">
                                <span>举办地点：{{showData.place}}</span>
                            </el-col>
                            <el-col :span="24">
                                <span>发布者：{{showData.author}}</span>
                            </el-col>
                            <el-col :span="24">
                                <span v-if="showData.team">参赛形式：团队赛</span>
                            </el-col>
                            <el-col :span="24">
                                <span v-if="!showData.team">参赛形式：个人赛</span>
                            </el-col>
                            <el-col :span="24">
                                <span v-if="showData.team">人数限制：{{showData.numLimit}}</span>
                            </el-col>
                        </el-row>
                    </el-card>

                    <el-card class="box-card top right" shadow="hover" v-if="showData.objectName!=null">
                        <h3>相关附件
                            <svg-icon icon-class="annex"></svg-icon>
                        </h3>
                        <el-divider></el-divider>
                        <el-link type="primary" @click="dl()"><i class="el-icon-download"></i>下载</el-link>
                    </el-card>


                    <el-card class="box-card top right" shadow="hover">

                        <h3>比赛进程
                            <svg-icon icon-class="match"></svg-icon>
                        </h3>
                        <br>
                        <div class="block">

                            <el-timeline>

                                <el-timeline-item :timestamp="formatTimeA(showData.applyTime)" placement="top">
                                    <el-card>
                                        <h4>开始报名:{{formatTimeA(showData.applyTime)}}</h4>
                                        <p>参赛者开始进行报名</p>
                                    </el-card>
                                </el-timeline-item>

                                <el-timeline-item :timestamp="formatTimeA(showData.applyStop)" placement="top">
                                    <el-card>
                                        <h4>报名结束:{{formatTimeA(showData.applyStop)}}</h4>
                                        <p>报名结束，确定最终报报名人数。</p>
                                    </el-card>
                                </el-timeline-item>

                                <el-timeline-item :timestamp="formatTimeA(showData.startTime)" placement="top">
                                    <el-card>
                                        <h4>比赛开始</h4>
                                        <p>比赛开始于： {{formatTimeA(showData.startTime)}} </p>
                                    </el-card>
                                </el-timeline-item>

                                <el-timeline-item :timestamp="formatTimeA(showData.endTime)" placement="top">
                                    <el-card>
                                        <h4>比赛结束</h4>
                                        <p>比赛于：{{formatTimeA(showData.endTime)}} 结束,后续更新成绩。</p>
                                    </el-card>

                                </el-timeline-item>

                            </el-timeline>
                        </div>
                    </el-card>


                </div>
            </div>

        </div>

    </div>
</template>

<script>
    import Sticky from "../../../components/Sticky/index";
    import {mapGetters} from "vuex";
    import {getJson} from "../../../api/api";

    import qs from 'qs';
    import MarkdownViewer from "../../../components/MarkdownViewer/index";
    import {Myformat} from "../../../utils";

    export default {
        name: "competitionDetail",
        components: {MarkdownViewer, Sticky},
        computed: {
            ...mapGetters([
                'userId'
            ])
        },
        data() {
            return {
                tempRoute: {},

                showData: {name: ''},
                id: '',
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);
        },
        methods: {
            toApply() {
                if (this.userId === '') {
                    this.$message.error('请先登录！')
                    setTimeout(()=>{
                        this.$router.push({path: '/login'})
                    },500)
                } else {
                    this.$router.push({path: '/apply/apply/' + this.showData.id || '/'})
                }
            },
            dl() {

                this.download.bucketName = this.showData.bucketName;
                this.download.objectName = this.showData.objectName;
                let a = document.createElement('a');
                a.href = "/api/public/file/getFile?" + qs.stringify(this.download);
                // a.download = this.download.objectName.substr(33, this.download.objectName.length);
                a.target = "_blank";
                a.click();
            },
            async fetchData(id) {
                await getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.status === 200) {
                            this.showData = response.data.data;


                            this.setTagsViewTitle();
                            this.setPageTitle();

                        } else if (response.data.status === 404) {


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
                const title = this.showData.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title} - 详情`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.showData.name;
                document.title = `${title} - 详情`
            },
            formatTimeA(time) {
                // return parseTime(time, '{y}-{m}-{d} {h}:{i}')
                return Myformat("yyyy年MM月dd日", new Date(time))
            },
        }
    }
</script>

<style scoped>
    .main {
        padding-left: 10%;
        padding-right: 10%;
    }

    .top {
        margin-top: 20px;
    }

    .left {
        margin-left: 20px;
    }

    .right {
        margin-right: 10px;
    }

    .title {
        margin-top: 20px;
        font-size: 50px;
        text-align: center;
        text-shadow: 2px 2px #1482f0;
        letter-spacing: 12px;
        color: orange;
    }

    .rightColumn {
        float: left;
        width: 25%;
        padding-left: 20px;
    }

    .leftColumn {
        float: left;
        width: 75%;
    }

</style>
