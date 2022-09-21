// pages/groupDetail/groupDetail.js
var service = require('../../service/group');
var deviceService = require('../../service/device');
var utils = require('../../common/utils/utils');


Page({

  /**
   * Page initial data
   */
  data: {

  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad(options) {
    var that = this;
    deviceService.getAllDeviceList(function(data){
      that.setData({
        deviceList:data.info
      })
    })
    service.getGroupDetail(options.id,function(data){
      that.setData({
        group:data.info,
        deviceSelected:data.info.deviceList
      })
      that.data.deviceSelected.forEach(function(item){
        console.log(item)
      })
    })
  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady() {

  },

  /**
   * Lifecycle function--Called when page show
   */
  onShow() {

  },

  /**
   * Lifecycle function--Called when page hide
   */
  onHide() {

  },

  /**
   * Lifecycle function--Called when page unload
   */
  onUnload() {

  },

  /**
   * Page event handler function--Called when user drop down
   */
  onPullDownRefresh() {

  },

  /**
   * Called when page reach bottom
   */
  onReachBottom() {

  },

  /**
   * Called when user click on the top right corner to share
   */
  onShareAppMessage() {

  }
})