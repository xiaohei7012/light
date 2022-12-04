// pages/detail2/detail.js
var utils = require('../../common/utils/utils');
var service = require('../../service/device');
var _fn;
var updateTimer;

Page({

  /**
   * Page initial data
   */
  data: {
    id : null,
    lastUpdateTime: {},
    selected: true,
    data: {
      movie: {},
      screen: {
        minHeight: 'auto'
      }
    }
  },

  /**
   * Lifecycle function--Called when page load
   */
  onLoad: function (query) {
    this.data.id = query.id;
    this.updateStatus();
    var self = this;
    utils.handle.showLoading();
    wx.getSystemInfo({
      success: function (info) {
        self.setData({
          'screen.minHeight': info.windowHeight + 'px'
        })
      }
    })
    this.getDetail(query.id);
  },

  bindStartTimeChange: function (e) {
    var that = this.data.device.id;
    this.setData({
      "device.plan.startTime": e.detail.value + ":00"
    })
    service.updateStartTime({
      deviceId: this.data.device.id,
      startTime: this.data.device.plan.startTime
    }, function () {

    })
  },

  bindEndTimeChange: function (e) {
    var that = this;
    this.setData({
      "device.plan.endTime": e.detail.value + ":00"
    })
    service.updateEndTime({
      deviceId: this.data.device.id,
      endTime: this.data.device.plan.endTime
    }, function () {

    })
  },

  //开关灯
  switchChange: function (e) {
    var that = this;
    var status = e.detail.value == true ? "ON" : "OFF";
    if (that.data.device[e.currentTarget.dataset.light] == 'ON') {
      status = 'OFF';
    } else if (that.data.device[e.currentTarget.dataset.light] == 'OFF') {
      status = 'ON';
    }
    that.data.device[e.currentTarget.dataset.light] = status;
    var data = {
      id: this.data.device.id,
      l1: this.data.device.l1,
      l2: this.data.device.l2,
      l3: this.data.device.l3,
      l4: this.data.device.l4,
      l5: this.data.device.l5,
      l6: this.data.device.l6,
      fan: this.data.device.fan
    };
    data = _fn.prepareInstruction(data);
    service.sendInstruction(data, function (r) {
      if (r.ret == true) {
        //己发送命令
        that.setData({
          device: that.data.device
        })
        wx.showToast({
          title: '发送命令成功',
          icon: 'success',
          duration: 2000
        })
      } else {
        wx.showToast({
          title: '发送命令失败',
          icon: 'error',
          duration: 3000
        })
      }

    })
  },

  //调风速
  sliderchange: function (e) {
    var that = this;
    var data = {
      id: this.data.device.id,
      l1: this.data.device.l1,
      l2: this.data.device.l2,
      l3: this.data.device.l3,
      l4: this.data.device.l4,
      l5: this.data.device.l5,
      l6: this.data.device.l6,
      fan: "F" + e.detail.value
    };
    data = _fn.prepareInstruction(data);
    service.sendInstruction(data, function (r) {
      if (r.ret == true) {
        //己发送命令
        that.setData({
          gear: e.detail.value,
          "device.fan": "F" + e.detail.value
        })
        wx.showToast({
          title: '发送命令成功',
          icon: 'success',
          duration: 2000
        })
      } else {
        that.setData({
          gear: that.data.gear,
          "device.fan": "F" + that.data.gear
        })
        wx.showToast({
          title: '发送命令失败',
          icon: 'error',
          duration: 3000
        })
      }

    })
  },

  getDetail: function(id){
    var self = this;
    service.getDeviceDetail(id, function (data) {
      var deviceDetail = {
        dname: "不存在"
      }
      data = data.info || deviceDetail;
      utils.handle.hideLoading();
      _fn.render.call(self, data);
    })
  },

  updateStatus:function(){
    // console.log("update!");
    this.getDetail(this.data.id);
    updateTimer = setTimeout(this.updateStatus,5 * 1000);
  },

  NavigatorHistory:function(){
    wx.redirectTo({
      url: '/pages/history/history?did=' + this.data.id,
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
    // console.log("关掉计时器")
    clearTimeout(updateTimer);
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
  render: function (data) {
    this.setData({
      device: data,
      "lastUpdateTime.date": utils.formatTime(new Date(Number(data.lastupdateTime))).substring(0, 10),
      "lastUpdateTime.time": utils.formatTime(new Date(Number(data.lastupdateTime))).substring(11, 20),
      "usedtime.hour": _fn.minute2hour.call(this, data.expire).hour,
      "usedtime.minute": _fn.minute2hour.call(this, data.expire).minute,
      gear: data.fan ? data.fan.substring(1) : 0
    });
  },
  minute2hour: function (minutes) {
    var hour = minutes / 60;
    var minute = minutes % 60;
    return {
      hour: parseInt(hour),
      minute: minute
    }
  },
  prepareInstruction: function (data) {
    // var ins = {};
    var l = 'l';
    var lnumber = 1;
    for(;lnumber <= 6;lnumber++){
      if(data[l + lnumber]!='ON'){
        data[l + lnumber] = 'OFF';
      }else{
        data[l + lnumber] = data[l + lnumber];
      }
    }
    return data;
  }
  
}