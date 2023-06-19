<template>
  <div class="article">
    <div class="info" @click="skipToPaper(article.noteId)">
      <div class="article-title">{{ article && article.title }}</div>
      <div class="article-time">{{ formatTime }}</div>
      <div class="article-content">{{ brief }}</div>
      <a class="target" href="" target="_blank" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'Article',
  // 接收父组件传来的 article 对象
  props: {
    article: Object
  },
  // 计算属性
  computed: {
    // 对时间进行格式化
    formatTime: function() {
      if (this.article) {
        const dt = new Date(this.article.time)
        const year = dt.getFullYear()
        const month = dt.getMonth() + 1
        const date = dt.getDate()
        return `${year} 年 ${month} 月 ${date} 日`
      }
      return ''
    },
    // 截取文章内容的前 35 个字，并加上省略号
    brief: function() {
      return this.article.content.substr(0, 35) + '...'
    }
  },
  methods: {
    skipToPaper(noteId) {
      this.$router.push({ name: 'showNote', params: { NoteId: noteId, article: this.article }})
    }
  }
}
</script>

<style lang="scss" scoped>
.article {
  width: 100%;
  display: flex;
  padding: 30px 30px;
  border-bottom: 1px solid #dbdbdb;
  &:last-child {
    border: none;
  }

  .info{
    cursor:pointer;
  }

  .article-title {
    font-size: 20px;
    font-weight: 500;
    color: #42b983;
    text-align: justify;
  }
  // .article-cover {
  //   flex: none;
  //   width: 120px;
  //   margin-right: 10px;
  //   > img {
  //     width: 100%;
  //   }
  // }
  .article-content, .article-time {
    font-size: 18px;
  }
  .article-time {
    margin: 10px 0;
    color: #c5c5c5;
  }
}
</style>

