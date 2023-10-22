<template>
  <div class="page">
    <div class="content">
      <h2 align="center">{{ article.title }}</h2>
      <p align="center" style="margin-top: 20px;margin-bottom: 20px;">{{ formatTime }}</p>
      <div v-html="NoteInfo.content" />
    </div>
    <div>
      <div class="top">
        <div style="margin-bottom: 1%;">当前评论总数: {{ comment_total }}</div>
        {{ receiver }}<el-input v-model="OurComment" type="textarea" style="margin-bottom: 0.5%" placeholder="在此发表评论" value="" />
        <el-button style="height: 30px;width: 60px;align-content:center; color: rgb(197, 190, 190); margin-left: 91%;" @click="clear()">清空</el-button>
        <el-button style="height: 30px;width: 60px;align-content: center; color: rgb(197, 190, 190); display: inline;" @click="publish()">发布</el-button>
        <div style="margin-bottom: 2%;" />
        <div v-for="(value,key) in CommentTop" style="margin-top: 1%;margin-bottom: 1%;">
          {{ value[1].commenterName }}: <p class="date" style="font-size: 10;">{{ getFormatTime(value[1].createdTime) }}</p>
          <el-button style="height:30px;width: 60px;align-content: center; color: rgb(197, 190, 190);" @click="reply(value[0])">回复</el-button>
          <div οnclick="reply(value[0])" style="margin-left: 3%;margin-top: 1%;">{{ value[1].content }}</div>
          <div v-for="child in getChildComment(value[0])" class="child">
            {{ child.commenterName }} to {{ Commentor.get(child.commentID) }} : <p class="date" style="font-size: 10;">{{ getFormatTime(child.createdTime) }}</p>
            <el-button style="height: 30px;width: 60px;align-content: center; color: rgb(197, 190, 190);" @click="reply(child.commentID)">回复</el-button>
            <div style="margin-left: 3%;margin-top: 1%;">{{ child.content }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import noteApi from '@/api/noteApi'
import comment from '@/api/comment'
import Vue from 'vue'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      name: '',
      noteId: this.$route.params.NoteId,
      article: this.$route.params.article,
      NoteInfo: [],
      comment_total: 0,
      comments: [],
      OurComment: '',
      Commentor: null,
      CommentRelation: null,
      CommentTop: null,
      CommentChild: null,
      receiver: '',
      receiverID: -1 // -1代表不是回复
    }
  },
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  created() {
    this.getNoteInfo()
    this.getComments()
  },
  methods: {
    async getNoteInfo() {
      noteApi.getNoteInfo(this.noteId).then(response => {
        this.NoteInfo = response.data
      })
    },
    async getComments() {
      comment.getCommetsByNoteID(this.noteId).then(response => {
        this.comment_total = response.data.total
        this.comments = response.data.data
        this.CommentTop = new Map()
        this.CommentRelation = new Map()
        this.CommentChild = new Map()
        this.Commentor = new Map()
        for (var i = 0; i < this.comments.length; i++) {
          var tmp = this.comments[i]
          if (tmp.fatherID === -1) {
            this.CommentTop.set(tmp.commentID, tmp)
          }
          this.CommentRelation.set(tmp.commentID, tmp.fatherID)
          // 进行路径压缩，给子层节点确定一个共同祖先,压缩的只有第三层及以下节点
          if (tmp.fatherID !== -1 && this.CommentRelation.has(tmp.fatherID) && this.CommentRelation.get(tmp.fatherID) !== -1) {
            this.CommentRelation.set(tmp.commentID, this.CommentRelation.get(tmp.fatherID))
          }
          // 记录每个commentID和发布者的名字
          this.Commentor.set(tmp.commentID, tmp.commenterName)
        }
        for (var j = 0; j < this.comments.length; j++) {
          var tmp_j = this.comments[j]
          if (tmp_j.fatherID !== -1) {
            var fatherID = this.CommentRelation.get(tmp_j.commentID)
            if (this.CommentChild.has(fatherID)) {
              const childs = this.CommentChild.get(fatherID)
              childs.push(tmp_j)
              this.CommentChild.set(fatherID, childs)
            } else {
              this.CommentChild.set(fatherID, [tmp_j])
            }
          }
        }
        console.log(this.CommentChild.get(1))
      })
    },
    getChildComment(id) {
      if (!this.CommentChild.has(id)) {
        return null
      }
      return this.CommentChild.get(id)
    },
    // 对时间进行格式化
    getFormatTime(format_date) {
      const dt = new Date(format_date)
      const year = dt.getFullYear()
      const month = dt.getMonth() + 1
      const date = dt.getDate()
      const hour = dt.getHours()
      const minute = dt.getMinutes()
      const second = dt.getSeconds()
      return `${year}年${month}月${date}日 ${hour}:${minute}:${second} `
    },
    reply(id) {
      this.receiverID = id
      this.receiver = 'to ' + this.Commentor.get(id) + ' :'
    },
    clear() {
      this.OurComment = ''
    },
    async publish() {
      var data = {}
      // data.commentID = null
      data.commenter = Vue.prototype.$UserId
      data.commenterName = Vue.prototype.$UserName
      // data.createdTime = null
      data.content = this.OurComment
      data.fatherID = this.receiverID
      data.likes = 0
      data.noteID = this.noteId
      await comment.publishComment(data).then(resp => {
        this.$message({
          message: '信息发布成功!',
          type: 'success'
        })
      }).catch(err => {
        console.log(err.response.data.message)
      })
      // 刷新评论区
      await this.getComments()
      // 恢复原状
      this.receiverID = -1
      this.receiver = ''
    }
  }
}
</script>

<style>
  .page{
    background-color: white;
  }
  .content{
    background-color: rgb(236, 229, 229);
    position: center;
    margin-top: 1%;
    margin-left: 1.5%;
    width: 97%;
    border-radius: 15px;
  }
  .top{
    position: center;
    margin-top: 1%;
    margin-left: 1.5%;
    width: 97%;
    border-radius: 5px;
    flex: 1;
    padding: 20px;
    background-color: #d7d7d7;
    border-radius: 8px;
  }
  .child{
    margin-top: 0.5%;
    margin-left: 3%;
    flex: 1;
    padding: 5px;
    background-color: #d7d7d7;
    border-radius: 15px;
  }
  .date{
    display: inline;
    font-size: smaller;
    opacity:0.4;
  }
</style>

