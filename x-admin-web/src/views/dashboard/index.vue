<template>
  <div class="dashboard-container">
    <div class="dashboard-text">name: {{ name }}</div>
    <div class="dashboard-text">userId: {{ userId }}</div>
    <div class="dashboard-text">完成用户评论的开发</div>
    <div class="dashboard-text"> 注册页面的样式优化 </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Vue from 'vue'

export default {
  name: 'Dashboard',
  userId: 0,
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  created: function() {
    this.userId = Vue.prototype.$UserId
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
