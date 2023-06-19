<template>
  <div v-if="userStatus==1">
    <!-- 搜索框 -->
    <el-card id="search">
      <!-- 分栏 -->
      <el-row :gutter="20">
        <el-col :span="20">
          <el-input v-model="searchModel.username" placeholder="用户名" clearable />
          <el-input v-model="searchModel.phone" placeholder="电话" clearable />
          <el-button type="primary" round icon="el-icon-search" @click="getUserList">查询</el-button>
        </el-col>
        <el-col :span="3.5" align="right" style="margin-top: 6px;">
          <el-button type="primary" round icon="el-icon-plus" @click="openEditUi(null)">新增用户</el-button>
        </el-col>
      </el-row>
    </el-card>
    <!-- 搜索结果 -->
    <el-card>
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column type="index" label="#" width="180">
          <template slot-scope="scope">
            {{ (searchModel.pageNo-1)*searchModel.pageSize+scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="userID" label="用户id" width="180" />
        <el-table-column prop="username" label="用户名" width="180" />
        <el-table-column prop="phone" label="电话" width="180" />
        <el-table-column prop="status" label="用户状态" width="180">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status==1">管理员</el-tag>
            <el-tag v-else type="danger">非管理员</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="电子邮件" />
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="openEditUi(scope.row.userID)" />
            <el-button type="danger" icon="el-icon-delete" circle @click="deleteUserById(scope.row)" />
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
      <el-form ref="userFormRef" :model="userForm" :rules="rules">
        <el-form-item label="用户名" prop="username" :label-width="formLabelWidth">
          <el-input v-model="userForm.username" autocomplete="off" />
        </el-form-item>
        <el-form-item v-if="userForm.userID==null||userForm.userID==undefined" label="用户密码" prop="password" :label-width="formLabelWidth">
          <el-input v-model="userForm.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="联系电话" :label-width="formLabelWidth">
          <el-input v-model="userForm.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="用户状态" :label-width="formLabelWidth">
          <el-switch v-model="userForm.status" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="电子邮件" prop="email" :label-width="formLabelWidth">
          <el-input v-model="userForm.email" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  <div v-else>
    <img border="0" src="../../../public/OIP-C.jpg" alt="picture" width="90%" height="90%">
  </div>
</template>

<script>
import userApi from '@/api/userManage'
import Vue from 'vue'
export default {
  data() {
    // 自定义校验
    var checkEmail = (rule, value, callback) => {
      var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!reg.test(value)) {
        return callback(new Error('邮箱格式错误!'))
      }
      callback()
    }
    return {
      total: 0,
      userStatus: Vue.prototype.$UserStatus,
      userList: [],
      searchModel: {
        pageNo: 1,
        pageSize: 10
      },
      title: '',
      dialogFormVisible: false,
      // 表单数据
      userForm: {},
      formLabelWidth: '140',
      // 表单验证
      rules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在3到50个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在3到50个字符', trigger: 'blur' }
        ],
        email: [
          { required: false, message: '请输入用户邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' }
        ]
      }
    }
  },
  created() { // 在组件创建阶段获取用户信息
    this.getUserList()
  },
  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getUserList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getUserList()
    },
    getUserList() {
      userApi.getUserList(this.searchModel).then(Response => {
        this.userList = Response.data.rows
        this.total = Response.data.total
      })
    },
    openEditUi(id) {
      if (id == null) {
        this.title = '新增用户'
      } else {
        this.title = '修改用户'
        // 将要修改用户信息填入表单
        userApi.getUserById(id).then(response => { // 出现跨域
          this.userForm = response.data
        })
      }
      this.dialogFormVisible = !this.dialogFormVisible
    },
    clearForm() {
      this.userForm = {}
      this.$refs.userFormRef.clearValidate() // 清除上次验证结果
    },
    saveUser() {
      // 触发表单验证
      this.$refs.userFormRef.validate((valid) => {
        if (valid) { // 提交请求
          userApi.saveUser(this.userForm).then(response => {
            this.$message({
              message: response.message,
              type: 'sucess'
            })
            // 关闭对话框
            this.dialogFormVisible = false
            // 刷新表格
            this.getUserList()
          })
          this.dialogFormVisible = !this.dialogFormVisible
        } else {
          console.log('error submit!')
          return false
        }
      })
    },
    deleteUserById(user) {
      this.$confirm('此操作将删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userApi.deleteUserById(user.userID).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.getUserList()
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

<style lang="scss" scoped>
  #search .el-input{
    width: 200px;
    margin-right: 10px;
  }
  .el-dialog .el-input{
    width: 85%;
  }
</style>
