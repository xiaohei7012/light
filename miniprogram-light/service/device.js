var handle,URL,LISTTYPE,_fn;

URL = {
  deviceList:'device/',
  deviceDetail:'device'
}

LISTTYPE = {
  0:'offline',
  1:'online',
  2:'all'
}

handle = {
  getDeviceList:function(type,callback){
    var url = getApp().globalData.url + URL.deviceList + LISTTYPE[type];
    _fn.getData({
      url:url,
      data:{
        pageNum:1,
        pageSize:10
      }
    },callback);
  },
  getAllDeviceList:function(callback){
    var url = getApp().globalData.url + URL.deviceList + LISTTYPE[2];
    _fn.getData({
      url:url
    },callback);
  },
  getDeviceDetail:function(id,callback){
    var url = getApp().globalData.url + URL.deviceDetail;
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
      method:'get',
      data:param.data,
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