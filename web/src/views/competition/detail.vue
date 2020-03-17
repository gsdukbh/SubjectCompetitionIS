<template>
    <div>
        {{showData}}
        {{this.$route.params.id}}
    </div>
</template>

<script>
    import {getJson} from "../../api/api";

    export default {
        name: "detail",
        data() {
            return {
                id: null,
                showData: null,
                loading: false,
                tempRoute: {},
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.fetchData(this.id);
            this.tempRoute = Object.assign({}, this.$route)
        },
        methods: {
            fetchData(id) {
                getJson('/public/competition/findById/' + id)
                    .then(response => {
                        if (response.data.code === 200) {
                            this.showData = response.data.data;
                            this.setTagsViewTitle();
                            this.setPageTitle()
                        } else {
                            this.$message.error("加载失败，请检查你的链接")
                        }
                    })
                    .catch(error => {
                        this.$message.error("出现了一些问题" + error)
                    });
            },
            setTagsViewTitle(){
                const title = this.showData.name;
                const route=Object.assign({}, this.tempRoute, { title: `${title}-${this.id}` })
                this.$store.dispatch('tagsView/updateVisitedView', route);
            },
            setPageTitle(){
                const title = this.showData.name;
                document.title = `${title} - ${this.id}`
            }
        }

    }
</script>

<style scoped>

</style>
