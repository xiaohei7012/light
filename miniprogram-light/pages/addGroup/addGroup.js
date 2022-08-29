// pages/addGroup/addGroup.js
var service = require('../../service/device');

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
    console.log(data)
    wx.request({
      url: 'https://localhost:442/group',
      method:'POST',
      data:data,
      success:function(e){
        wx.showModal({
          title: e.data.info,
          content:!e.data.errMsg?"创建成功":e.data.errMsg,
          showCancel:false,
          success (res) {
            if (res.confirm&&e.data.ret==true) {
              wx.redirectTo({
                url: '../group/group'
              })
            }
          }
        })
      }
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
    service.getAllDeviceList(function(data){
      console.log(data)
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