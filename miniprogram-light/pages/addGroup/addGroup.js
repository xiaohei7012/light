// pages/addGroup/addGroup.js
var deviceService = require('../../service/device');
var service = require('../../service/group');

Page({

  /**
   * Page initial data
   */
  data: {
    selectedDevices:[]
  },

  formSubmit:function(e){
    var data = e.detail.value;
    data.ids = this.data.selectedDevices;
    service.addGroup(data,function(data){
      wx.showModal({
        title: data.info,
        content:!data.errMsg?"创建成功":data.errMsg,
        showCancel:false,
        success (res) {
          if (res.confirm&&data.ret==true) {
            wx.switchTab({
              url: '../group/group'
            })
          }
        }
      })
    })
  },

  selectDevice(options){
    this.data.selectedDevices = options.detail.value;
  },
  /**
   * Lifecycle function--Called when page load
   */
  onLoad(options) {

  },

  /**
   * Lifecycle function--Called when page is initially rendered
   */
  onReady() {
    var that = this;
    deviceService.getAllDeviceList(function(data){
      that.setData({
        deviceList:data.info
      })
    })
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