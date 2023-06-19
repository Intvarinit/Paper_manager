import request from '@/utils/request'

export default {
  getTagById(id) {
    return request({
      url: `/tag/${id}`,
      method: 'get'
    })
  },
  getAllTag() {
    return request({
      url: '/tag/list',
      method: 'get'
    })
  },
  countPaperByTag(id) {
    return request({
      url: `/tag/count/${id}`,
      method: 'get'
    })
  },
  getTagIdByName(tagName) {
    return request({
      url: `/tag/${tagName}`,
      method: 'post'
    })
  },
  addTag(tag) {
    return request({
      url: '/tag/add',
      method: 'post',
      data: tag
    })
  },
  updateTag(tag) {
    return request({
      url: '/tag/update',
      method: 'put',
      data: tag
    })
  },
  saveTag(tag) {
    // eslint-disable-next-line eqeqeq
    if (tag.TagID == null || tag.TagID == undefined) {
      return this.addTag(tag)
    }
    return this.updateTag(tag)
  },
  deleteTagById(id) {
    return request({
      url: `/tag/${id}`,
      method: 'delete'
    })
  }
}
