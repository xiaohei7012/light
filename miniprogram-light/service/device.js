var utils = require('../common/utils/utils');

var handle,URL,LISTTYPE,_fn;

URL = {
  deviceAdd:'device',
  deviceList:'device',
  deviceDetail:'device',
  sendIns:'device/send'
}

LISTTYPE = {
  0:'offline',
  1:'online',
  2:'all'
}

handle = {
  getDeviceList:function(type,callback){
    var url = getApp().globalData.url + URL.deviceList + '/' + LISTTYPE[type];
    utils.handle.getData({
      url:url,
      data:{
        pageNum:1,
        pageSize:99
      }
    },callback);
  },
  getAllDeviceList:function(callback){
    var url = getApp().globalData.url + URL.deviceList + '/' + LISTTYPE[2];
    utils.handle.getData({
      url:url
    },callback);
  },
  getDeviceDetail:function(id,callback){
    var url = getApp().globalData.url + URL.deviceDetail;
    utils.handle.getData({
      url:url,
      data:{
        id:id
      }
    },callback);
  },
  addDevice:function(data,callback){
    var url = getApp().globalData.url + URL.deviceAdd;
    utils.handle.addData({
      url:url,
      data:data
    },callback);
  },
  sendInstruction:function(data,callback){
    var url = getApp().globalData.url + URL.sendIns;
    utils.handle.getData({//getData 就是get方法
      url:url,
      data:data
    },callback);
  }

}

_fn = {

}

module.exports = handle ;