<template>
    <div class="main">

        <sticky :z-index="10" class-name="sub-navbar">
            <el-button style="margin-left: 10px;" type="success">
                publish
            </el-button>
        </sticky>
        <!--标题-->
        <div class="title">
            <span>{{showData.name}}</span>
        </div>
        <div class="el-row">

            <div class="leftColumn">

                <el-card class="box-card top left" id="viewer" shadow="hover">
                    <h1>内容详情
                        <el-tag>{{showData.level}}</el-tag>
                    </h1>

                    <el-divider content-position="right">昨夜星辰昨夜风</el-divider>
                    <!--markdown-->
                    <el-row v-loading="loading">
                        <div class="markdown" id="markdownViewer"></div>
                    </el-row>

                </el-card>

            </div>
            <div class="rightColumn">

                <el-card class="box-card top right" shadow="hover">
                    <h3>承办单位</h3>
                    <el-divider content-position="right">画楼西畔桂堂东</el-divider>

                    <router-link to="#">

                        <el-tooltip class="item" effect="dark" :content="'查看更多'+showData.organizer+'举办的竞赛'"
                                    placement="bottom">
                            <span><el-link type="primary">{{showData.organizer}}</el-link></span>
                        </el-tooltip>

                    </router-link>

                </el-card>

                <el-card class="box-card top right" shadow="hover" v-if="showData.place !==''">
                    <h3>举办地点</h3>
                    <el-divider content-position="right">身无彩凤双飞翼</el-divider>
                    <span><el-link type="primary">{{showData.place}}</el-link></span>
                </el-card>

                <el-card class="box-card top right" shadow="hover">
                    <h3>比赛进程</h3>
                    <el-divider content-position="right">隔座送钩春酒暖</el-divider>
                    <div class="block">

                        <el-timeline>

                            <el-timeline-item :timestamp="formatTimeA(showData.applyTime)" placement="top">
                                <el-card>
                                    <h4>开始报名</h4>
                                    <p>参赛者可以进行报名</p>
                                </el-card>
                            </el-timeline-item>

                            <el-timeline-item :timestamp="formatTimeA(showData.startTime)" placement="top">
                                <el-card>
                                    <h4>比赛开始</h4>
                                    <p>王小虎 提交于 2018/4/3 20:46</p>
                                </el-card>
                            </el-timeline-item>
                            <el-timeline-item timestamp="2018/4/2" placement="top">
                                <el-card>
                                    <h4>更新 Github 模板</h4>
                                    <p>王小虎 提交于 2018/4/2 20:46</p>
                                </el-card>
                            </el-timeline-item>

                        </el-timeline>
                    </div>
                </el-card>


                <el-card class="box-card top right" shadow="hover">
                    报名信息
                </el-card>

                <el-card class="box-card top right" shadow="hover">
                    问题
                </el-card>

            </div>
        </div>


    </div>
</template>

<script>

    import Sticky from '@/components/Sticky'
    import {getJson} from "../../api/api";
    import {parseTime} from '../../utils/index'
    import 'tui-editor/dist/tui-editor-contents.css' // editor content
    import 'highlight.js/styles/github.css';
    import Viewer from 'tui-editor/dist/tui-editor-Viewer'

    export default {
        name: "detail",
        components: {Sticky},
        data() {
            return {
                id: '',
                showData: {},
                loading: true,
                tempRoute: {},
                markdown: null,
                poem: null,
            }
        },
        created() {

            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route);

        },
        mounted() {
            setTimeout(() => {
                this.markdownViewer();
            }, 2000)
        },
        methods: {

            markdownViewer() {
                this.markdown = new Viewer({
                    el: document.querySelector('#markdownViewer'),
                    initialEditType: 'markdown',
                    previewStyle: 'vertical',
                    initialValue: this.showData.content,
                });
                this.loading = false;
            },

            async fetchData(id) {
                getJson('/public/competition/findById/' + id)
                    .then(response => {
                        this.showData = response.data;
                        this.setTagsViewTitle();
                        this.setPageTitle();
                    })
                    .catch(() => {
                    });
            },
            setTagsViewTitle() {
                const title = this.showData.name;
                const route = Object.assign({}, this.tempRoute, {title: `${title}-${this.id}`});
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle() {
                const title = this.showData.name;
                document.title = `${title} - ${this.id}`
            },
            formatTimeA(time) {
                return parseTime(time, '{y}-{m}-{d} {h}:{i}')
            },
        }
    }

</script>

<style scoped>

    .main {
        background-color: #76baff;
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

    #h1 {

    }
</style>
