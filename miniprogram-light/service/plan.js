var handle,URL,LISTTYPE,_fn;

URL = {
  planList:'plan/',
  planDetail:'plan/detail/'
}

LISTTYPE = {
  0:'offline',
  1:'online'
}

handle = {
  getPlanList:function(type,callback){
    var url = getApp().globalData.url + URL.planList;
    _fn.getData({
      url:url
    },callback);
  },
  getPlanDetail:function(id,callback){
    var url = URL.DeviceDetail + id;
    _fn.getData({
      url:url
    },callback);
  }
}

_fn = {
  getData:function(param,callback){
    wx.request({
      url:param.url,
      method:'get',
      data:{
        pageNum:1,
        pageSize:10
      },
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