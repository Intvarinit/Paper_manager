import request from '@/utils/request'

// export function login(data) {
//   return request({
//     url: '/user/login',
//     method: 'post',
//     data
//   })
// }

export default {
  getUserList(searchModel) {
    return request({
      url: '/user/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        username: searchModel.username,
        phone: searchModel.phone
      }
    })
  },
  addUser(user) {
    return request({
      url: '/user/add',
      method: 'post',
      data: user
    })
  },
  getUserById(id) {
    return request({
      url: `/user/${id}`,
      method: 'get'
    })
  },
  updateUser(user) {
    return request({
      url: '/user/update',
      method: 'put',
      data: user
    })
  },
  saveUser(user) {
    // eslint-disable-next-line eqeqeq
    if (user.userID == null || user.userID == undefined) {
      return this.addUser(user)
    }
    return this.updateUser(user)
  },
  deleteUserById(id) {
    return request({
      url: `/user/${id}`,
      method: 'delete'
    })
  }
}
