// pages/addDevice/addDevice.js
var utils = require('../../common/utils/utils');
var service = require('../../service/device');

Page({

  /**
   * Page initial data
   */
  data: {

  },

  formSubmit:function(e){
    service.addDevice(e.detail.value,function(data){
      wx.showModal({
        title: data.info,
        content:!data.errMsg?"创建成功":data.errMsg,
        showCancel:false,
        success (res) {
          if (res.confirm&&data.ret==true) {
            wx.switchTab({
              url: '../device/device'
            })
          }
        }
      })
    })
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (options) {

  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady: function () {

  },

  /**
   * Lifecycle function--Called when page show
   */
  onShow: function () {

  },

  /**
   * Lifecycle function--Called when page hide
   */
  onHide: function () {

  },

  /**
   * Lifecycle function--Called when page unload
   */
  onUnload: function () {

  },

  /**
   * Page event handler function--Called when user drop down
   */
  onPullDownRefresh: function () {

  },

  /**
   * Called when page reach bottom
   */
  onReachBottom: function () {

  },

  /**
   * Called when user click on the top right corner to share
   */
  onShareAppMessage: function () {

  }
})