var handle,URL,LISTTYPE,_fn;

URL = {
  groupList:'group/',
  groupDetail:'group/detail'
}

LISTTYPE = {
  0:'offline',
  1:'online'
}

handle = {
  getGroupList:function(type,callback){
    var url = getApp().globalData.url + URL.groupList;
    _fn.getData({
      url:url,
      data:{
        pageNum:1,
        pageSize:10
      }
    },callback);
  },
  getGroupDetail:function(id,callback){
    var url = getApp().globalData.url + URL.groupDetail;
    _fn.getData({
      url:url,
      data:{
        id:id
      }
    },callback);
  }
}

_fn = {
  getData:function(param,callback){
    wx.request({
      url:param.url,
      data:param.data,
      method:'get',
      header:{
        'Content-Type':'application/json'
      },
      success:function(e){
        callback(e.data);
      },
      fail:function(e){
        callback(e.data);
      }
    })
  }
}

module.exports = handle ;