// pages/addPlan/addPlan.js
var utils = require('../../common/utils/utils');
var service = require('../../service/plan');

Page({

  /**
   * Page initial data
   */
  data: {
    startTime:"00:00",
    endTime:"00:00",
    l1:"OFF",
    l2:"OFF",
    l3:"OFF",
    l4:"OFF",
    l5:"OFF",
    l6:"OFF",
    fanArray:getApp().globalData.fanGear,
    fanIndex:1
  },
  bindSTimeChange:function(e){
    this.setData({
      startTime:e.detail.value
    })
  },
  bindETimeChange:function(e){
    this.setData({
      endTime:e.detail.value
    })
  },
  bindFanChange:function(e){
    this.setData({
      fanIndex:e.detail.value
    })
    this.data.fan = this.data.fanArray[e.detail.value].value;
  },
  switch1Change:function(e){
    if(e.detail.value){
      this.data.l1 = "ON"
    }else{
      this.data.l1 = "OFF"
    }
  },
  switch2Change:function(e){
    if(e.detail.value){
      this.data.l2 = "ON"
    }else{
      this.data.l2 = "OFF"
    }
  },
  switch3Change:function(e){
    if(e.detail.value){
      this.data.l3 = "ON"
    }else{
      this.data.l3 = "OFF"
    }
  },
  switch4Change:function(e){
    if(e.detail.value){
      this.data.l4 = "ON"
    }else{
      this.data.l4 = "OFF"
    }
  },
  switch5Change:function(e){
    if(e.detail.value){
      this.data.l5 = "ON"
    }else{
      this.data.l5 = "OFF"
    }
  },
  switch6Change:function(e){
    if(e.detail.value){
      this.data.l6 = "ON"
    }else{
      this.data.l6 = "OFF"
    }
  },
  selectFan(options){
    this.data.fan = options.detail.value;
  },
  formSubmit:function(e){
    var data = e.detail.value;
    data.l1 = this.data.l1;
    data.l2 = this.data.l2;
    data.l3 = this.data.l3;
    data.l4 = this.data.l4;
    data.l5 = this.data.l5;
    data.l6 = this.data.l6;
    data.instruction = data.l1 + " " + data.l2 + " " + data.l3 + " " + data.l4 + " " + data.l5 + " " + data.l6 + " " + this.data.fan;
    service.addPlan(data,function(data){
      wx.showModal({
        title: data.info,
        content:!data.errMsg?"创建成功":data.errMsg,
        showCancel:false,
        success (res) {
          if (res.confirm&&data.ret==true) {
            微信小程序如何返回上一个页面并刷新上一个页面

            wx.redirectTo({
              url: '../plan/plan'
            })
          }
        }
      })
    })
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