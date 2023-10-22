import request from '@/utils/request'

export default {
  getCommetsByNoteID(noteID) {
    return request({
      url: `/comment/${noteID}`,
      method: 'get'
    })
  },
  publishComment(comment) {
    return request({
      url: '/comment/publish',
      method: 'post',
      data: comment
    })
  }
  // saveNote(note) {
  //   return request({
  //     url: '/note/update',
  //     method: 'put',
  //     data: note
  //   })
  // }
}
