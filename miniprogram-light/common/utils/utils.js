const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('/')} ${[hour, minute, second].map(formatNumber).join(':')}`
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : `0${n}`
}
var handle;

handle = {
  showLoading : function(){
    wx.showToast({
      title: '({数据加载中})',
      icon:'loading',
      duration:10000
    })
  },
  hideLoading:function(){
    wx.hideToast();
  }
}

module.exports = {
  formatTime,
  handle
}
