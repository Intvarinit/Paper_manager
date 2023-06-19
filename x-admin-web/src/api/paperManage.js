import request from '@/utils/request'

export default {
  getPaperList(searchModel) {
    return request({
      url: '/paper/list',
      method: 'get',
      params: {
        id: searchModel.id,
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        Title: searchModel.title,
        Tag: searchModel.tag
      }
    })
  },
  getPaperById(id) {
    return request({
      url: `/paper/${id}`,
      method: 'get'
    })
  },
  addPaper(paper) {
    return request({
      url: '/paper/add',
      method: 'post',
      data: paper
    })
  },
  updatePaper(paper) {
    return request({
      url: '/paper/update',
      method: 'put',
      data: paper
    })
  },
  savePaper(paper) {
    // eslint-disable-next-line eqeqeq
    if (paper.paperID == null || paper.paperID == undefined) {
      return this.addPaper(paper)
    }
    return this.updatePaper(paper)
  },
  deletePaperById(id) {
    return request({
      url: `/paper/${id}`,
      method: 'delete'
    })
  },
  getPaperPublicList(searchModel) {
    return request({
      url: '/paper/public',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        Title: searchModel.title
      }
    })
  }
}

