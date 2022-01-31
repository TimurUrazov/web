<template>
  <div>
    <Post v-for="post in posts" :post="post" :userLogin="users[post.userId].login"
          :commentsSize="getCommentsSize(post.id)" :enableLink = true :key="post.id"/>
  </div>
</template>

<script>
import Post from "./Post";
export default {
    name: "Index",
    props: ["posts", "users", "comments"],
    components: {Post},
    methods: {
      getCommentsSize: function(postId) {
        return Object.values(this.comments).filter(comment => comment.postId === postId).length;
      }
    }
}
</script>

<style scoped>

</style>
