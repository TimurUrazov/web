<template>
  <div>
    <Post v-for="post in posts" :post="post" :key="post.id"/>
  </div>
</template>

<script>
import Post from "../Post";
export default {
  name: "Index",
  props: ["posts"],
  components: {Post}
}
</script>

<style scoped>

</style>
