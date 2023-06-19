<template>
  <div class="register-container">
    <section>
      <el-form
        ref="userForm"
        :model="userForm"
        :rules="rules"
        label-width="150px"
        autocomplete="off"
        size="medium"
      >
        <div style="padding-top: 10px">
          <el-form-item label="邮箱" prop="email">
            <el-col :span="10">
              <el-input
                v-model="userForm.email"
                placeholder="请输入邮箱"
              />
            </el-col>
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-col :span="10">
              <el-input
                v-model="userForm.phone"
                placeholder="请输入手机号码"
              />
            </el-col>
          </el-form-item>
          <el-form-item label="账号" prop="Username">
            <el-col :span="10">
              <el-input
                v-model="userForm.Username"
                placeholder="请输入账号"
              />
            </el-col>
          </el-form-item>
          <el-form-item label="密码" prop="Password">
            <el-col :span="10">
              <el-input v-model="userForm.Password" :type="passwordType" />
            </el-col>
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
            </span>
          </el-form-item>
          <el-form-item label="确认密码" prop="cpwd">
            <el-col :span="10">
              <el-input v-model="userForm.cpwd" type="password" />
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              style="width: 18%"
              @click="register"
            >注册</el-button>
            <el-button
              type="info"
              style="width: 18%;margin-left: 27px;"
              @click="back"
            >返回登陆界面</el-button>
          </el-form-item>
        </div>
      </el-form>
    </section>
  </div>
</template>

<script>
import userApi from '@/api/user'
import { validUsername } from '@/utils/validate'
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能少于6位！'))
      } else {
        callback()
      }
    }
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入正确的用户名！'))
      } else {
        callback()
      }
    }
    return {
      userForm: {},
      passwordType: 'password',
      rules: {
        email: [{
          required: false,
          type: 'email',
          message: '邮箱格式错误，请重新输入',
          trigger: 'blur'
        }],
        Username: [{
          required: true,
          message: '请输入用户账号',
          trigger: 'blur',
          validator: validateUsername
        }],
        Password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur',
          validator: validatePassword
        }],
        cpwd: [{
          required: true,
          message: '确认密码',
          trigger: 'blur'
        }, {
          validator: (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请再次输入密码'))
            } else if (value !== this.userForm.Password) {
              callback(new Error('两次输入密码不一致'))
            } else {
              callback()
            }
          },
          trigger: 'blur'
        }]

      }
    }
  },
  methods: {
    back() {
      this.userForm = {}
      this.$router.push({ path: '/login' })
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    // 用户注册
    register: function() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          console.log(this.userForm)
          const user = {
            email: this.userForm.email,
            phone: this.userForm.phone,
            username: this.userForm.Username,
            password: this.userForm.Password
          }
          userApi.register(user).then(res => {
            this.$message({
              showClose: true,
              message: '注册成功，正在跳转到登录界面...',
              type: 'success'
            })
            setTimeout(() => {
              this.$router.push('/')
            }, 2000)
          }).catch(err => {
            console.log(err.response.data.message)
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .register-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.register-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 95%;

    input {
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      border: 1px solid rgba(255, 255, 255, 0.1);
      -webkit-appearance: none;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    label {
      font-style: normal;
      font-size: 12px;
      color: $light_gray;
    }
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.register-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .header {
    border-bottom: 2px solid rgb(235, 232, 232);
    min-width: 980px;
    color: #666;

    header {
      margin: 0 auto;
      padding: 10px 0;
      width: 980px;

      .login {
        float: right;
      }

      .bold {
        font-style: normal;
        color: $light_gray;
      }
    }
  }

  > section {
    margin: 0 auto 30px;
    padding-top: 30px;
    width: 980px;
    min-height: 300px;
    padding-right: 100px;
    box-sizing: border-box;

    .status {
      font-size: 12px;
      margin-left: 20px;
      color: #e6a23c;
    }

    .error {
      color: red;
    }
  }

  .tips {
    float: right;
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
}
</style>

<style scoped>
/* 修改验证器样式 */
.el-form-item.is-error .el-input__inner {
  border-color: #889aa4;
}
.el-form-item.is-error .el-input__validateIcon {
  color: #889aa4;
}
.el-form-item__error {
  color: #e6a23c;
}
</style>

