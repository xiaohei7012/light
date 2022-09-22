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
      that.data.group.id = options.id //id
      that.data.deviceSelected.forEach(function(item){//循环
        for(var i in that.data.deviceList){
          var device = that.data.deviceList[i];
          if(device.id == item.id){
            that.data.deviceList[i].selected = true;
            that.setData({
              deviceList:that.data.deviceList
            })
          }
        }
      })
    })
  },

  formSubmit:function(e){
    var data = e.detail.value;
    data.id = this.data.group.id;
    data.ids = this.data.selectedDevices;
    service.editGroup(data,function(data){
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