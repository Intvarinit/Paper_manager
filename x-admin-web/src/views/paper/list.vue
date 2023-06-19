<template>
  <div>
    <!-- 搜索框 -->
    <el-card id="search">
      <!-- 分栏 -->
      <el-row :gutter="20">
        <el-col :span="20">
          <el-input v-model="searchModel.title" style="width: 260px;margin-left: 20px;" placeholder="论文题目" clearable />
          <el-input v-model="searchModel.tag" style="width: 260px;margin-left: 20px;" placeholder="标签" clearable unused />
          <el-button style="margin-left: 20px;" type="primary" round icon="el-icon-search" @click="getPaperList">查询</el-button>
        </el-col>
        <el-col :span="3.5" align="right" style="margin-top: 6px;">
          <el-button type="primary" round icon="el-icon-plus" @click="openEditUi(null)">新增论文</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 搜索结果 -->
    <el-card>
      <el-table :data="PaperList" stripe style="width:100%;align-items: center;">
        <el-table-column type="index" label="#" width="60">
          <template slot-scope="scope">
            {{ (searchModel.pageNo-1)*searchModel.pageSize+scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="paperID" label="论文id" width="68" />
        <el-table-column prop="tagName" label="论文标签" width="112">
          <template slot-scope="scope">
            {{ tagName[scope.$index%searchModel.pageSize] }}
          </template>
        </el-table-column>
        <el-table-column prop="title" label="论文题目" width="200" />
        <el-table-column prop="summary" label="论文概要" width="260" />
        <el-table-column label="论文笔记" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="info" icon="el-icon-edit" @click="toEdit(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="论文网址" width="180">
          <template slot-scope="scope">
            <a :href="scope.row.paperUrl" target="_blank" style="">{{ scope.row.paperUrl }}</a>
          </template>
        </el-table-column>
        <el-table-column prop="paperRef" label="参考文献格式" width="240" />
        <el-table-column prop="public" label="是否公开" width="70">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.public==1">公开</el-tag>
            <el-tag v-else type="danger">私有</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="openEditUi(scope.row.paperID)" />
            <el-button type="danger" icon="el-icon-delete" circle @click="deletePaperById(scope.row)" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="searchModel.pageNo"
      :page-sizes="[3, 5, 10, 15]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <!-- 用户信息编对话框 -->
    <el-dialog :title="title" :visible.sync="dialogFormVisible" @close="clearForm">
      <el-form ref="paperFormRef" :model="paperForm">
        <!-- <el-form-item label="论文id" prop="paperID" :label-width="formLabelWidth">
          <el-input v-model="paperForm.paperID" autocomplete="off" />
        </el-form-item> -->
        <!-- <el-form-item v-if="paperForm.paperID==null||paperForm.paperID==undefined" label="" prop="password" :label-width="formLabelWidth">
          <el-input v-model="userForm.password" type="password" autocomplete="off" />
        </el-form-item> -->
        <el-form-item label="论文标签" :label-width="formLabelWidth">
          <el-input v-model="tagNameOfFrom" autocomplete="off" />
        </el-form-item>
        <el-form-item label="论文题目" prop="title" :label-width="formLabelWidth">
          <el-input v-model="paperForm.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="论文概要" prop="summary" :label-width="formLabelWidth">
          <el-input v-model="paperForm.summary" rows="3" type="textarea" autocomplete="off" />
        </el-form-item>
        <el-form-item label="论文是否公开" :label-width="formLabelWidth">
          <el-switch v-model="paperForm.public" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="论文网址" prop="paperUrl" :label-width="formLabelWidth">
          <el-input v-model="paperForm.paperUrl" autocomplete="off" />
        </el-form-item>
        <el-form-item label="论文参考格式" prop="paperRef" :label-width="formLabelWidth">
          <el-input v-model="paperForm.paperRef" autocomplete="off" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePaper">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import paperApi from '@/api/paperManage'
import tagApi from '@/api/tagManage'
import Vue from 'vue'

export default {
  data() {
    return {
      total: 0,
      PaperList: [],
      tagName: [],
      searchModel: {
        pageNo: 1,
        pageSize: 10
      },
      title: '',
      dialogFormVisible: false,
      // 表单数据
      paperForm: {},
      tagNameOfFrom: '',
      formLabelWidth: '140'
    }
  },
  created() { // 在组件创建阶段获取用户信息
    this.searchModel.id = Vue.prototype.$UserId
    this.getPaperList()
  },
  methods: {
    toEdit(paper) {
      this.$router.push({ name: 'note', params: { show: true, paperInfo: paper }})
    },
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
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getPaperList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getPaperList()
    },
    getPaperList() {
      paperApi.getPaperList(this.searchModel).then(Response => {
        this.PaperList = Response.data.rows
        // tag信息的转化处理
        this.tagName = []
        for (var i = 0; i < this.PaperList.length; i++) {
          tagApi.getTagById(this.PaperList[i]['tag']).then((response) => {
            this.tagName.push(response.data.data)
          })
        }
        this.total = Response.data.total
      })
    },
    async openEditUi(id) {
      if (id == null) {
        this.title = '新增论文信息'
        this.paperForm.publishID = Vue.prototype.$UserId
      } else {
        this.title = '修改论文信息'
        // 将要修改用户信息填入表单
        await paperApi.getPaperById(id).then(response => { // 出现跨域
          this.paperForm = response.data
          if (this.paperForm.tag != null) {
            tagApi.getTagById(this.paperForm.tag).then(res => {
              this.tagNameOfFrom = res.data.data
            })
          }
        })
      }
      this.dialogFormVisible = !this.dialogFormVisible
    },
    clearForm() {
      this.paperForm = {}
      this.tagNameOfFrom = ''
      this.$refs.paperFormRef.clearValidate() // 清除上次验证结果
    },
    savePaper() {
      // 触发表单验证
      this.$refs.paperFormRef.validate(async(valid) => {
        if (valid) { // 提交请求
          // eslint-disable-next-line eqeqeq
          if (this.paperForm.createdTime == undefined) {
            this.paperForm.createdTime = this.getDateTime()
          }
          this.paperForm.updatedTime = this.getDateTime()
          console.log(this.paperForm)
          // 对论文的标签信息进行处理
          if (this.tagNameOfFrom !== '') {
            await tagApi.getTagIdByName(this.tagNameOfFrom).then(res => {
              this.paperForm.tag = res.data.id
            })
          }
          await paperApi.savePaper(this.paperForm).then(response => {
            this.$message({
              message: response.message,
              type: 'success'
            })
            // 关闭对话框
            this.dialogFormVisible = false
            // 刷新表格
            this.getPaperList()
          })
          // this.dialogFormVisible = !this.dialogFormVisible
        } else {
          console.log('error submit!')
          return false
        }
      })
    },
    deletePaperById(paper) {
      this.$confirm('此操作将删除该论文信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        paperApi.deletePaperById(paper.paperID).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.getPaperList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style>
a {
  color: blue;
  text-decoration: underline;
}
</style>
