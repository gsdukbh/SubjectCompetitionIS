<template>
    <div>
        <div class="center ">
            <el-form v-model="resetPwd" :rules="rules" ref="resetPwd" status-icon label-position="top"
                     label-width="80px">
                <el-form-item label="密码:" prop="password">
                    <el-input v-model="resetPwd.password" style="width: 50%"></el-input>
                </el-form-item>
                <el-form-item prop="checkPassword" label="确认密码">
                    <el-input type="password" v-model="resetPwd.checkPassword" autocomplete="off"
                              style="width: 50%" placeholder="确认密码"></el-input>
                </el-form-item>

            </el-form>
            <router-link to="/me/myInfo">
                <el-button style="width: 20%">
                    返回
                </el-button>
            </router-link>
            <br>

            <el-button :loading="buttonLoading" style="margin-top: 10px;width: 20%" @click="save('resetPwd')">
                保存
            </el-button>
        </div>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "reset",
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                buttonLoading: false,
                resetPwd: {
                    id: '',
                    password: '',
                    checkPassword: '',
                },
                rules: {}
            }
        },
        methods: {
            save(form) {
                this.buttonLoading = true;
                this.$refs[form].validate((valid) => {
                    if (valid) {

                        this.$notify.success({
                            title: '提交成功'
                        })
                    }
                })

            }
        }
    }
</script>

<style scoped>


    .center {
        text-align: center;
        align-content: center;
        width: 50%;
        margin-left: 25%;
        margin-right: 25%;
    }
</style>
