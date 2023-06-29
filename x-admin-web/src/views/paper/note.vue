<template>
  <div v-if="this.$route.params.show">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="标题">
        <el-input :value="paperInfo.title" :disabled="true" />
      </el-form-item>
      <!-- <el-form-item label="内容">
        <el-input v-model="form.content" type="textarea" :disabled="true" />
      </el-form-item> -->
      <el-form-item label="内容">
        <editor :key="tinymceFlag" ref="editor" v-model="form.content" :init="tinymceInit" />
        <el-button type="primary" style="height: 40px;margin-top: 20px;" @click="saveNote">保存修改</el-button>
        <el-button style="height: 40px;;margin-top: 20px;" @click="back">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
  <!-- 处理非跳转页面的情况 -->
  <div v-else>
    <img border="0" src="@/../public/OIP-C.jpg" alt="picture" width="90%" height="90%">
  </div>
</template>

<script src="https://cdn.tiny.cloud/1/40qrk9tiybyargmtlkuqaux8286h5e32g969hc659iaf6siv/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
<script>
import Editor from '@tinymce/tinymce-vue'
import noteApi from '@/api/noteApi'
import paperApi from '@/api/paperManage'

export default {
  components: {
    'editor': Editor
  },
  data() {
    return {
      paperInfo: this.$route.params.paperInfo,
      form: {
        NoteID: undefined,
        author: undefined,
        content: undefined,
        tag: undefined,
        createdTime: undefined
      },
      tinymceFlag: 1,
      tinymceInit: {}
    }
  },
  created() {
    if(this.paperInfo != undefined && undefined != this.paperInfo.noteID){
      // 获取文章内容
      this.getNoteInfo()
    }
    // 加载编辑器
    this.initEditor()
  },
  mounted(){
    // 判断是否是非法跳转
    if(this.$route.params.show === undefined){
      this.skipWarn()
    }
  },
  beforeDestroy() {

  },
  activated() {
    this.tinymceFlag++
  },
  methods: {
    getDateTime() { // 获取当前格式化的时间
      var dt = new Date()
      const year = dt.getFullYear()
      const month = (dt.getMonth() + 1).toString().padStart(2, '0')
      const date = dt.getDate().toString().padStart(2, '0')
      const hour = dt.getHours().toString().padStart(2, '0')
      const minute = dt.getMinutes().toString().padStart(2, '0')
      const second = dt.getSeconds().toString().padStart(2, '0')
      return `${year}-${month}-${date} ${hour}:${minute}:${second}`
    },
    back() {
      this.$router.push({ path: '/paper/list' })
    },
    initEditor() {
      this.tinymceInit = {
        language: 'zh_CN',
        height: 650,
        theme: 'silver',
        browser_spellcheck: true,
        branding: true,
        statusbar: false, // 隐藏底部状态栏
        menubar: true,
        fontsize_formats: '12px 13px 14px 15px 16px 17px 18px 20px 22px 24px 26px 30px 32px 35px 40px 50px',
        plugins: 'print preview image searchreplace autolink directionality visualblocks visualchars fullscreen link template code codesample table charmap hr pagebreak nonbreaking anchor insertdatetime advlist lists wordcount imagetools textpattern help emoticons autosave bdmap indent2em autoresize formatpainter axupimgs',
        // 移动下面字符的位置就能影响前端的摆放
        toolbar: 'code undo redo restoredraft | cut copy paste pastetext | bullist numlist table fontsizeselect image| forecolor backcolor bold italic underline strikethrough link anchor | alignleft aligncenter alignright alignjustify outdent indent | \
        styleselect formatselect fontselect | blockquote subscript superscript removeformat | \
        charmap emoticons hr pagebreak insertdatetime print preview | fullscreen | bdmap indent2em lineheight formatpainter axupimgs',
        // paste_webkit_styles: true,
        // paste_data_images: false,
        // images_upload_url: '/note/image/upload', // 图片上传地址
        content_style: `
            *                         { padding:0; margin:0; }
            html, body                { height:100%; }
            img                       { max-width:100%; display:block;height:auto; }
            a                         { text-decoration: none; }
            iframe                    { width: 100%; }
            p                         { line-height:1.6; margin: 0px; }
            table                     { word-wrap:break-word; word-break:break-all; max-width:100%; border:none; border-color:#999; }
            .mce-object-iframe        { width:100%; box-sizing:border-box; margin:0; padding:0; }
            ul,ol                     { list-style-position:inside; }
          `,
        autosave_ask_before_unload: false,
        // content_css: 'plugins/becodesample/highlight.js-11.5.1/styles/atom-one-light.css',
        advlist_bullet_styles: 'default,circle,disc,square',
        advlist_number_styles: 'default,lower-alpha,lower-greek,lower-roman,upper-alpha,upper-roman',
        images_upload_handler: function (blobInfo, success, failure) {//关键代码
          var form = new FormData();
          console.log(blobInfo)
          form.append('editormd-image-file', blobInfo.blob(),  blobInfo.filename());
          $.ajax({
              url: "/note/image/upload" ,
              type: "post",
              data: form,
              dataType: 'json',
              processData: false,
              contentType: false,
              success: function (data) {
                // console.log(data);
                var imgUrl = data.url;//根据返回值得不同这里要自己定义
                // console.log(imgUrl);
                success(imgUrl);
              },
              // error: function (e) {
              //   alert(e);
              // }
          });
        }
      }
      this.tinymceFlag++
    },
    async getNoteInfo() {
      // 获取传输过来的noteID信息
      if (this.paperInfo.noteID != undefined || this.paperInfo.noteID != null) {
        this.form.NoteID = this.paperInfo.noteID
      }
      // 查询note信息
      await noteApi.getNoteInfo(this.form.NoteID).then(response => {
        this.form = response.data
      })
    },
    async saveNote() {
      // 未创建note而且content为空的情况下不会新建note
      if(this.paperInfo.noteID === null && this.form.content === undefined){
        this.$message.warning("您未对当前笔记信息做任何更改!")
      }else{
        if(this.paperInfo.noteID === null){
          await noteApi.createNote(this.paperInfo).then(response => {
            this.form.NoteID = response.data
            this.paperInfo.noteID = this.form.NoteID
          })
          // 把创建好的id给到paperInfo里面
          await paperApi.savePaper(this.paperInfo)
        }
        await noteApi.saveNote(this.form)
        this.$message.success("保存论文笔记信息成功!")
      }
    },
    skipWarn(){
      this.$message.warning("您当前不能直接访问当前页面，请从论文列表信息页面进入!")
    }
  }

}
</script>

<style>

</style>
