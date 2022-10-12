// pages/detail2/detail.js
var utils = require('../../common/utils/utils');
var service = require('../../service/device');
var _fn;

Page({

  /**
   * Page initial data
   */
  data: {
    data:{
      movie:{},
      screen:{
        minHeight:'auto'
      }
    }
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (query) {
    var self = this;
    utils.handle.showLoading();
    wx.getSystemInfo({
      success:function(info){
        self.setData({
          'screen.minHeight':info.windowHeight + 'px'
        })
      }
    })
    console.log(query)
    service.getDeviceDetail(query.id,function(data){
      console.log(data);
      var deviceDetail = {
        dname:"不存在"
      }
      data = data.info || deviceDetail;
      console.log(data);
      utils.handle.hideLoading();
      _fn.render.call(self,data);
    })
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
});

_fn ={
  render:function(data){
    //data.genresStr = data.fenres.join('/');
    //data.staff = data.directors.concat(data.casts);
    this.setData({
      device:data
    });
  }
}