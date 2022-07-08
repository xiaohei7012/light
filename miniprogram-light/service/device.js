var handle,URL,LISTTYPE,_fn;

URL = {
  deviceList:'/',
  deviceDetail:'/'
}

LISTTYPE = {
  0:'ON',
  1:'OFF'
}

handle = {
  getDeviceList:function(type,callback){
    var url = URL.deviceList + LISTTYPE[type];
    _fn.getData({
      url:url
    },callback);
  },
  getDeviceDetail:function(id,callback){
    var url = URL.DeviceDetail +id;
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