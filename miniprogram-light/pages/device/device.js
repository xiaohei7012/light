// pages/home2/home.js
var utils = require('../../common/utils/utils');
var service = require('../../service/device');
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
        text:'联网中',
        type:'1'
      },{
        text:'离线状态',
        type:'0'
      }]
    }
  },

  changeTab:function(e){
    var target = e.target;
    console.log(e)
    _fn.selectTab.call(this,target.dataset.index);
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
    //_fn.selectTab.call(this,0);
  },

  /**
   * Lifecycle function--Called when page show
   */
  onShow: function () {
    _fn.selectTab.call(this,0);
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
      _fn.renderList.call(self,data.info);
      console.log(data)
    });
  },
  renderList:function(data){
    var i,listData={
      datas:[]
    };
    for(i in data.list){
      var device = data.list[i];
      if(device.status=='ON'){
        device.status='开机';
      }else if(device.status='OFF'){
        device.status='关机';
      }else if(device.status='BAD'){
        device.status='坏';
      }

      if(!device.gname){
        device.gname = '';
      }

    }
    listData.datas = data.list;
    this.setData({
      devices:listData
    });
  }
}