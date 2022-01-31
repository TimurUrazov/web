<template>
    <footer>
        <a href="/">Codeforces</a> &copy; 2021 by Mike Mirzayanov
        <div class="statistics">
          users: {{ usersSize }}, posts: {{ postsSize }}
        </div>
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["postsSize", "usersSize"]
}
</script>

<style scoped>

</style>