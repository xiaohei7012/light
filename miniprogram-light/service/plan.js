var utils = require('../common/utils/utils');

var handle,URL,LISTTYPE,_fn;

URL = {
  planAdd:'plan',
  planEdit:'plan',
  planList:'plan',
  planDetail:'plan/detail'
}

LISTTYPE = {
  0:'offline',
  1:'online'
}

handle = {
  getPlanList:function(type,callback){
    var url = getApp().globalData.url + URL.planList;
    utils.handle.getData({
      url:url,
      data:{
        pageNum:1,
        pageSize:99
      }
    },callback);
  },
  getPlanDetail:function(id,callback){
    var url =  getApp().globalData.url + URL.planDetail;
    utils.handle.getData({
      url:url,
      data:{
        id:id
      }
    },callback);
  },
  addPlan:function(data,callback){
    var url = getApp().globalData.url + URL.planAdd;
    utils.handle.addData({
      url:url,
      data:data
    },callback);
  },
  editPlan:function(data,callback){
    var url =  getApp().globalData.url + URL.planEdit;
    utils.handle.editData({
      url:url,
      data:data
    },callback)
  }
}

_fn = {
  
}

module.exports = handle ;