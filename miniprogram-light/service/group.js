var utils = require('../common/utils/utils');

var handle,URL,LISTTYPE,_fn;

URL = {
  groupAdd:'group',
  groupList:'group',
  groupDetail:'group/detail'
}

LISTTYPE = {
  0:'offline',
  1:'online'
}

handle = {
  getGroupList:function(type,callback){
    var url = getApp().globalData.url + '/' + URL.groupList;
    utils.handle.getData({
      url:url,
      data:{
        pageNum:1,
        pageSize:99
      }
    },callback);
  },
  getGroupDetail:function(id,callback){
    var url = getApp().globalData.url + URL.groupDetail;
    utils.handle.getData({
      url:url,
      data:{
        id:id
      }
    },callback);
  },
  addGroup:function(data,callback){
    var url = getApp().globalData.url + URL.groupAdd;
    utils.handle.addData({
      url:url,
      data:data
    },callback);
  }
}

_fn = {
  
}

module.exports = handle ;