<template>
  <div>
    <h2 align="center">{{ article.title }}</h2>
    <p align="center" style="margin-top: 20px;margin-bottom: 20px;">{{ formatTime }}</p>
    <div v-html="NoteInfo.content" />
  </div>
</template>

<script>
import noteApi from '@/api/noteApi'
export default {
  data() {
    return {
      noteId: this.$route.params.NoteId,
      article: this.$route.params.article,
      NoteInfo: []
    }
  },
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
    }
  },
  created() {
    this.getNoteInfo()
  },
  methods: {
    getNoteInfo() {
      noteApi.getNoteInfo(this.noteId).then(response => {
        this.NoteInfo = response.data
      })
    }
  }
}
</script>

<style>
</style>

