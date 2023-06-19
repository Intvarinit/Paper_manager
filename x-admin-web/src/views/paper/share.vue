<template>
  <div id="Articles">
    <!-- 用 v-for 渲染文章列表 -->
    <Article v-for="article in articleList" :key="article.title" :article="article" />
  </div>
</template>

<script>
// 引入组件
import Article from '@/components/article/article.vue'
import paperApi from '@/api/paperManage'
export default {
  name: 'App',
  // 注册组件
  components: {
    Article
  },
  data: function() {
    return {
      articleList: [],
      searchModel: {
        pageNo: 1,
        pageSize: 10,
        total: undefined
      }
    }
  },
  mounted() {
    this.getPublicPaper()
  },
  methods: {
    async getPublicPaper() {
      await paperApi.getPaperPublicList(this.searchModel).then(response => {
        this.articleList = response.data.rows.map(val => {
          const ret = {}
          ret.title = val.title
          ret.time = val.updatedTime
          ret.noteId = val.noteID
          ret.content = val.summary
          return ret
        })
        this.total = response.data.total
      })
    }
  }
}
</script>

<style lang="scss">
#Articles {
  width: 100%;
  height: 100%;
}
</style>

