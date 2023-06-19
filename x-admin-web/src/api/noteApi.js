import request from '@/utils/request'

export default {
  getNoteInfo(noteID) {
    return request({
      url: `/note/${noteID}`,
      method: 'get'
    })
  },
  createNote(paperInfo) {
    return request({
      url: '/note/new',
      method: 'post',
      data: paperInfo
    })
  },
  saveNote(note) {
    return request({
      url: '/note/update',
      method: 'put',
      data: note
    })
  }
}
