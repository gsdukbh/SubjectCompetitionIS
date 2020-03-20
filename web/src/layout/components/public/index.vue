<template>
    <div>
        <el-menu :default-active="activeIndex"
                 class="el-menu-demo top"
                 mode="horizontal"
                 text-color="#303133"
                 active-text-color="#409eff"
                 @select="handleSelect">
            <el-menu-item index="1">首页</el-menu-item>

            <el-submenu index="2">

                <template slot="title">我的工作台</template>

                <el-menu-item index="2-1">选项1</el-menu-item>

                <el-menu-item index="2-2">选项2</el-menu-item>

                <el-menu-item index="2-3">选项3</el-menu-item>

                <el-submenu index="2-4">

                    <template slot="title">选项4</template>

                    <el-menu-item index="2-4-1">选项1</el-menu-item>

                    <el-menu-item index="2-4-2">选项2</el-menu-item>

                    <el-menu-item index="2-4-3">选项3</el-menu-item>

                </el-submenu>

            </el-submenu>
            <el-menu-item index="3" disabled>消息中心</el-menu-item>

            <el-menu-item index="4">赛事进程</el-menu-item>

            <el-menu-item class="right" index="5">
                <router-link to="/login">登录</router-link>
            </el-menu-item>


            <div class="right-menu">
                <template v-if="device!=='mobile'">

                    <search id="header-search" class="right-menu-item"/>

                </template>
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

        <div class="con">
            <router-view :key="key"/>
        </div>


    </div>
</template>

<script>
    import {getToken} from '@/utils/auth'
    import Search from '@/components/HeaderSearch'
    import {mapGetters} from "vuex";

    export default {
        name: "publicLayout",
        components: {
            Search
        },
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
            const hasToken = getToken()
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
    .right {
        float: right;
    }
</style>
<style lang="scss" scoped>
    .top {
        position: fixed;
        width: 100%;
        height: auto;
        z-index: 9;
        text-align: center;
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
