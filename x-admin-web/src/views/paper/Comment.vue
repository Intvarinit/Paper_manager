<template>
  <div style="letter-spacing: 1px">
    <div class="head">
      <!-- 评论区域如果当前用户有头像则显示头像,没有则显示默认头像 -->
      <!-- 评论框 -->
      <input
        ref="input"
        v-model="firstComments"
        type="text"
        placeholder="请输入评论 . . ."
        @focus="obtain"
        @blur="lose"
        @keyup.enter="sumbit"
      >
      <!-- 发布按钮 -->
      <button @click="sumbit">发表评论</button>
    </div>
    <div class="content">
      <!-- 一级评论 -->
      <div v-for="(item, index) in comments" :key="index" class="first">
        <div class="first-content">
          <!-- 一级评论用户昵称 -->
          <h3 class="first-username">{{ item.username }}</h3>
          <!-- 一级评论发布时间 -->
          <span class="first-time">{{ item.date }}</span>
          <!-- 一级评论评论内容 -->
          <p class="first-comment">
            {{ item.content }}
          </p>
          <!-- 一级评论评论点赞 -->
          <div
            class="first-right"
            @mouseleave="delete_leave(item)"
            @mouseover="delete_after(item)"
          >
            <span
              v-if="item.delete"
              class="delete"
              @click="comment_delete(item, 0)"
            >删除</span>
            <span class="comments" @click="comment_input(item)">评论</span>
            <!-- 一级评论点赞数 -->
            <span
              class="praise"
              :class="item.like"
              @click="comment_like(item, 0)"
            >
              <span v-if="item.numbers">{{ item.numbers }}</span>
            </span>
          </div>
          <!-- 回复一级评论 -->
          <div v-if="item.display" class="reply-comment">
            <input
              v-model="childComments"
              type="text"
              placeholder="请输入评论 . . ."
              @keyup.enter="reply_sumbit(item, 0)"
            >
            <!-- 0为回复一级评论 -->
            <button @click="reply_sumbit(item, 0)">发表评论</button>
          </div>
          <!-- 次级评论 -->
          <div class="second">
            <ul>
              <li v-for="(sons, index) in item.sons" :key="index">
                <div class="top">
                  <!-- 次级评论头像,该用户没有头像则显示默认头像 -->
                  <div class="second-content">
                    <!-- 次级评论用户昵称 -->
                    <h3 class="second-username">{{ sons.username }}</h3>
                    <!-- 次级评论评论时间 -->
                    <span class="second-time">{{ sons.date }}</span>
                    <!-- 次级评论内容 xxx回复xxx：评论内容-->
                    <p class="second-comment">
                      <span class="second-reply">
                        <span class="to_reply">{{ sons.username }}</span>
                        回复
                        <span class="to_reply">{{ sons.to_username }}</span>：
                      </span>
                      {{ sons.content }}
                    </p>
                    <!-- 次级评论评论点赞 -->
                    <div
                      class="second-right"
                      @mouseleave="delete_leave(sons)"
                      @mouseover="delete_after(sons)"
                    >
                      <span
                        v-if="sons.delete"
                        class="delete"
                        @click="comment_delete(sons, 1)"
                      >删除</span>
                      <span
                        class="comments"
                        @click="comment_input(sons)"
                      >评论</span>
                      <!-- 次级评论点赞数 -->
                      <span
                        class="praise"
                        :class="sons.like"
                        @click="comment_like(sons, 1)"
                      >
                        <span v-if="sons.numbers">{{
                          sons.numbers
                        }}</span></span>
                    </div>
                  </div>
                </div>
                <!-- 回复次级评论 -->
                <div v-if="sons.display" class="reply-comment reply_li">
                  <input
                    v-model="childComments"
                    type="text"
                    placeholder="请输入评论 . . ."
                    @keyup.enter="reply_sumbit(sons, 1)"
                  >
                  <!-- 1为回复次级评论 -->
                  <button @click="reply_sumbit(sons, 1)">发表评论</button>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <!-- 暂无评论的空状态 -->
      <el-empty v-if="show" description="暂无评论" />
    </div>
    <!-- 页码 -->
    <div v-if="showPage" style="text-align: center; margin-top: 15px">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalNumber * 10"
        :current-page="page"
        @current-change="currentPage"
      />
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style>
  /* 评论框 */
.head {
  background-color: rgb(248, 248, 248);
  position: relative;
  height: 75px;
  border-radius: 5px;
}
.head img {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  position: absolute;
  top: 10px;
  left: 13px;
}
/* 评论框 */
.head input {
  position: absolute;
  top: 13px;
  left: 80px;
  height: 45px;
  border-radius: 5px;
  outline: none;
  width: 65%;
  font-size: 20px;
  padding: 0 20px;
  border: 2px solid #f8f8f8;
}
/* 发布评论按钮 */
.head button {
  position: absolute;
  top: 13px;
  right: 20px;
  width: 120px;
  height: 48px;
  border: 0;
  border-radius: 5px;
  font-size: 20px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
}
/* 鼠标经过字体加粗 */
.head button:hover {
  font-weight: 600;
}

/* 评论内容区域 */
.content .first {
  display: flex;
  position: relative;
  padding: 10px 10px 0px 0;
}
.first .first-img {
  flex: 1;
  text-align: center;
}
.first img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.first-username,
.second-username {
  color: #504f4f;
}
.first-content {
  flex: 9;
}
.first-time,
.second-time {
  color: #767575;
}
.first-comment,
.second-comment {
  margin-top: 5px;
}
/* 右边点赞和评论 */
.first-right,
.second-right {
  position: absolute;
  right: 1%;
  top: 10px;
}
.first-right span,
.second-right span {
  margin-right: 20px;
  cursor: pointer;
}
/* 删除评论 */
.delete:hover {
  color: red;
}
/* 评论字体图标 */
.comments::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  content: "\e8b9";
  /* 必须加 */
  font-family: "iconfont";
  margin-right: 4px;
  font-size: 16px;
}
/* 点赞字体图标 */
.praise::before {
  /* 想使用的icon的十六制编码，去掉&#x之后的 */
  content: "\ec7f";
  /* 必须加 */
  font-family: "iconfont";
  margin-right: 4px;
  font-size: 19px;
}
.second {
  background-color: #f3f3f3;
  margin-top: 10px;
}
.second li {
  padding: 10px 10px 10px 0;
  border-bottom: 1px solid rgb(237, 237, 237);
}
.second .top {
  display: flex;
  position: relative;
}
.second-img {
  flex: 1;
  text-align: center;
}
.to_reply {
  color: rgb(106, 106, 106);
}
.second-content {
  flex: 9;
}
.second .reply_li {
  margin-left: 70px;
}
/* 评论框 */
.reply-comment {
  margin: 10px 0 0 0;
}
.reply-comment input {
  height: 40px;
  border-radius: 5px;
  outline: none;
  width: 70%;
  font-size: 18px;
  padding: 0 20px;
  /* border: 2px solid #f8f8f8; */
  border: 2px solid skyblue;
}
/* 发布评论按钮 */
.reply-comment button {
  width: 15%;
  height: 43px;
  border: 0;
  border-radius: 5px;
  font-size: 18px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
  margin-left: 20px;
}
/* 鼠标经过字体加粗 */
.reply-comment button:hover {
  font-weight: 600;
}
/* 评论点赞颜色 */
.comment-like {
  color: red;
}
</style>
