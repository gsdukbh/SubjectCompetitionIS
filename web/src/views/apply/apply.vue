<template>
    <div>

    </div>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "apply",
        computed: {
            ...mapGetters([
                'name',
                'userId'
            ])
        },
        data() {
            return {
                id: '',
                competition: {},
                apply: {}
            }
        }
    }
</script>

<style scoped>

</style>
