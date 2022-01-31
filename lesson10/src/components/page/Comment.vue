<template>
  <div class="comment">
    <div class="user-pic">
      <img src="../../assets/img/user.jpg" alt="User picture">
    </div>
    <div class="body">
      <div class="information">By {{ userLogin }} </div>
      <div class="text">
        {{ text }}
      </div>
  </div>
  </div>
</template>

<script>
export default {
  name: "Comment",
  props: ["userLogin", "text"]
}
</script>

<style scoped>

</style>
