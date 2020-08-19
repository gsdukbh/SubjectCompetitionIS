<template>
    <div>
        <el-menu :default-active="activeIndex"
                 class="el-menu-demo top"
                 mode="horizontal"
                 text-color="#303133"
                 router
                 active-text-color="#409eff"
        >

            <el-menu-item index="/">首页</el-menu-item>


            <el-menu-item index="/public/competition">竞赛</el-menu-item>

            <el-menu-item index="/public/announcement">公告</el-menu-item>

            <el-menu-item class="right" :index="'/login'">
                登录
            </el-menu-item>

            <div class="right-menu">

                <el-dropdown class="avatar-container" trigger="click">

                    <el-tooltip class="item" effect="dark" content="个人中心" placement="left-end">

                        <div class="avatar-wrapper">
                            <img :src="avatar" class="user-avatar">
                            <i class="el-icon-caret-bottom"></i>
                        </div>

                    </el-tooltip>
                    <el-dropdown-menu slot="dropdown" class="user-dropdown">
                        <router-link to="/home">
                            <el-dropdown-item>
                                首页
                            </el-dropdown-item>
                        </router-link>

                        <a target="_blank" href="https://github.com/gsdukbh/SubjectCompetitionIS">
                            <el-dropdown-item>Github</el-dropdown-item>
                        </a>

                        <div v-if="islogin === false">
                            <el-dropdown-item divided @click.native="login">
                                <span style="display:block;">登录</span>
                            </el-dropdown-item>
                        </div>
                        <div v-else>
                            <el-dropdown-item divided @click.native="logout">
                                <span style="display:block;">退出</span>
                            </el-dropdown-item>
                        </div>

                    </el-dropdown-menu>

                </el-dropdown>

            </div>

        </el-menu>

        <div class="main">
            <router-view :key="key"/>
        </div>


    </div>
</template>

<script>
    import {getToken} from '@/utils/auth'

    import {mapGetters} from "vuex";

    export default {
        name: "publicLayout",
        computed: {
            key() {
                return this.$route.path
            },
            ...mapGetters([
                'avatar',
                'device'
            ])
        },
        data() {
            return {
                activeIndex: '',
                islogin: false,
                keys: null,
                keyPath: null,
            }
        },
        mounted() {
            const hasToken = getToken();
            if (hasToken) {
                this.islogin = true
            }
        },
        methods: {
            handleSelect(key, keyPath) {
                this.keys = key;
                this.keyPath = keyPath;
           
            },
            login() {
                this.$router.push({path: '/login'})
            },
            async logout() {
                await this.$store.dispatch('user/logout')
                await this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            }

        }
    }
</script>

<style scoped>
    .top {
        align-content: center;
        padding-right: 10%;
        padding-left: 10%;
    }

</style>

<style lang="scss" scoped>

    .main {

    }

    .con {
        padding-top: 60px;
    }

    .right-menu {
        float: right;
        height: 100%;
        /*line-height: 50px;*/
    }

    .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 14px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
            cursor: pointer;
            transition: background .3s;

            &:hover {
                background: rgba(0, 0, 0, .025)
            }
        }
    }

    .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
            margin-top: 5px;
            position: relative;

            .user-avatar {
                cursor: pointer;
                width: 40px;
                height: 40px;
                border-radius: 10px;
            }

            .el-icon-caret-bottom {
                cursor: pointer;
                position: absolute;
                right: -20px;
                top: 25px;
                font-size: 12px;
            }
        }
    }

</style>
