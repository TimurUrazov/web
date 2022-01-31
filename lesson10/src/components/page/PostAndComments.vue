<template>
  <div>
    <Post :userLogin="users[post.userId].login" :post="post" :commentsSize="getCommentsSize(post.id)" :enableLink = false />
    <Comment v-for="comment in commentsById" :userLogin = users[comment.userId].login :text = comment.text :key="comment.id"/>
  </div>
</template>

<script>
import Post from "./Post";
import Comment from "./Comment";
export default {
  name: "PostAndComments",
  components: {Comment, Post},
  props: ["post", "comments", "users"],
  computed: {
    commentsById: function () {
      return Object.values(this.comments).filter(comment => comment.postId === this.post.id);
    }
  },
  methods: {
    getCommentsSize: function(postId) {
      return Object.values(this.comments).filter(comment => comment.postId === postId).length;
    }
  }
}
</script>

<style scoped>

</style>