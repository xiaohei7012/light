// pages/addPlan/addPlan.js
Page({

  /**
   * Page initial data
   */
  data: {
    l1:false,
    l2:false,
    l3:false,
    l4:false,
    l5:false,
    l6:false,
    fan:"f9"
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
  switch1Change:function(e){
    this.setData({
      l1:e.detail.value
    })
  },
  switch2Change:function(e){
    this.setData({
      l2:e.detail.value
    })
  },
  switch3Change:function(e){
    this.setData({
      l3:e.detail.value
    })
  },
  switch4Change:function(e){
    this.setData({
      l4:e.detail.value
    })
  },
  switch5Change:function(e){
    this.setData({
      l5:e.detail.value
    })
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
    console.log(data)
    wx.request({
      url: 'https://localhost:442/plan',
      method:'POST',
      data:e.detail.value,
      success:function(e){
        wx.showModal({
          title: e.data.info,
          content:!e.data.errMsg?"创建成功":e.data.errMsg,
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