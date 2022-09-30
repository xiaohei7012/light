// pages/home2/home.js
var service = require('../../service/plan');
var utils = require('../../common/utils/utils');
var _fn;

Page({

  /**
   * Page initial data
   */
  data: {
    plans:{}
  },

  changeTab:function(e){
    var targe = e.target;
    _fn.selectTab.call(this);
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
  selectTab:function(){
    var self = this;
    // var tabs=self.data.tabs;
    // self.setData({
    //   'tabs.currentIndex':index
    // });
    utils.handle.showLoading();
    service.getPlanList(null,function(data){
      utils.handle.hideLoading();
      _fn.renderList.call(self,data.info);
    });
  },
  renderList:function(data){
    var i,listData = {
      datas:[]
    };
    listData.datas = data.list || listData.datas;
    for(i in listData.datas){
      var plan = listData.datas[i];
      if(!plan.startTime){
        plan.startTime = '';
      }
      if(!plan.endTime){
        plan.endTime = '';
      }
      var d = new Date()
      d.setTime(plan.createTime);
      plan.createTime = utils.formatTime(d);
    }
    this.setData({
      plans:listData
    });
  }
}