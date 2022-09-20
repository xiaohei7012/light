// pages/planDetail/planDetail.js
var utils = require('../../common/utils/utils');
var service = require('../../service/plan');

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
    fanIndex:0,
    fanArray:[{
      name:'1档',
      value:'F1'
    },
    {
      name:'2档',
      value:'F2'
    },
    {
      name:'3档',
      value:'F3'
    },
    {
      name:'4档',
      value:'F4'
    },
    {
      name:'5档',
      value:'F5'
    },
    {
      name:'6档',
      value:'F6'
    },
    {
      name:'7档',
      value:'F7'
    },
    {
      name:'8档',
      value:'F8'
    },
    {
      name:'9档',
      value:'F9'
    }]
  },
  /**
   * Lifecycle function--Called when page load
   */
  onLoad(options) {
    var that = this;
    this.data.id = options.id;
    service.getPlanDetail(options.id,function(data){
      var ins = data.info.instruction.split(" ");
      var findex = that.data.fanArray.findIndex(function(item){
        console.log()
        return ins[6] == item['value'];
      });
      console.log(findex)
      that.setData({
        plan:data.info,
        l1:ins[0],
        l2:ins[1],
        l3:ins[2],
        l4:ins[3],
        l5:ins[4],
        l6:ins[5],
        fanIndex:findex
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
  bindFanChange:function(e){
    this.setData({
      fanIndex:e.detail.value
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
    service.editPlan(data,function(e){
      wx.showModal({
        title: e.info,
        content:!e.errMsg?"修改成功":e.errMsg,
        showCancel:false,
        success (res) {
          if (res.confirm&&e.ret==true) {
            wx.switchTab({
              url: '../plan/plan'
            })
          }
        }
      })
    });
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