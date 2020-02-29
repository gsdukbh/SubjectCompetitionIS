<template>
<div>
        {{d}}
</div>
</template>

<script>

    import axios from 'axios'
    export default {
        name: "test",
        data(){
            return {
                d:null
            }
        },
        created() {
            axios({
                method:'get',
                url:'api/admin'
            })
            .then(res => {
                this.d = res.data
                console.log(res.data)
            })
        },
        methods : {
        }

    }
</script>

<style scoped>

</style>
