// pages/home2/home.js
var service = require('../../service/device');
var utils = require('../../common/utils/utils');
var _fn;

Page({

  /**
   * Page initial data
   */
  data: {
    devices:{},
    tabs:{
      currentIndex:0,
      list:[{
        text:'离线状态',
        type:'0'
      },{
        text:'联网中',
        type:'1'
      }]
    }
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
    _fn.selectTab.call(this,0);
  },

  changeTab:function(e){
    var targe = e.target;
    _fn.selectTab.call(this,targe.dataset.index);
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

_fn = {
  selectTab:function(index){
    var self = this;
    var tabs=self.data.tabs;
    self.setData({
      'tabs.currentIndex':index
    });
    utils.handle.showLoading();
    service.getDeviceList(tabs.list[index].type,function(data){
      utils.handle.hideLoading();
      console.log(data)
      _fn.renderList.call(self,data.info);
    });
  },
  renderList:function(data){
    var listData = {
      datas:[
          {
            dname:'灯设备1'
          },
          {
            dname:'灯设备2'
          }
      ]
    };
    listData.datas = data.list || listData.datas;
    console.log(data)
    this.setData({
      devices:listData
    });
  }
}