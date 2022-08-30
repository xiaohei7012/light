var handle,URL,LISTTYPE,_fn;

URL = {
  planList:'plan/',
  planDetail:'plan/detail'
}

LISTTYPE = {
  0:'offline',
  1:'online'
}

handle = {
  getPlanList:function(type,callback){
    var url = getApp().globalData.url + URL.planList;
    _fn.getData({
      url:url,
      data:{
        pageNum:1,
        pageSize:10
      }
    },callback);
  },
  getPlanDetail:function(id,callback){
    var url =  getApp().globalData.url + URL.planDetail;
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