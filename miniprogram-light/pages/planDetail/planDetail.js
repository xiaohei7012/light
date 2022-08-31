// pages/planDetail/planDetail.js
var service = require('../../service/plan');
var utils = require('../../common/utils/utils');


Page({

  /**
   * Page initial data
   */
  data: {
    plan:{},
    l1:"OFF",
    l2:"OFF",
    l3:"OFF",
    l4:"OFF",
    l5:"OFF",
    l6:"OFF",
    fan:"F0"
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad(options) {
    var that = this;
    this.data.id = options.id;
    service.getPlanDetail(options.id,function(data){
      var ins = data.info.instruction.split(" ");
      that.setData({
        plan:data.info,
        l1:ins[0],
        l2:ins[1],
        l3:ins[2],
        l4:ins[3],
        l5:ins[4],
        l6:ins[5],
        fan:ins[6]
      })
    })
  },

  bindSTimeChange:function(e){
    this.setData({
      "plan.startTime":e.detail.value
    })
  },
  bindETimeChange:function(e){
    this.setData({
      "plan.endTime":e.detail.value
    })
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
  switch6Change:function(e){
    this.setData({
      l6:e.detail.value
    })
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
    data.id = this.data.id;
    data.instruction = data.l1 + " " + data.l2 + " " + data.l3 + " " + data.l4 + " " + data.l5 + " " + data.l6 + " " +data.fan;
    console.log(data);
    wx.request({
      url: 'https://localhost:442/plan',
      method:'PUT',
      data:data,
      success:function(e){
        wx.showModal({
          title: e.data.info,
          content:!e.data.errMsg?"修改成功":e.data.errMsg,
          showCancel:false,
          success (res) {
            if (res.confirm&&e.data.ret==true) {
              wx.redirectTo({
                url: '../plan/plan'
              })
            }
          }
        })
      }
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