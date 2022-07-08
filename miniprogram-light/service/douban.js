var handle,URL,LISTTYPE,_fn;

URL = {
  //电影列表
  movieList: 'https://www.wxapp-gateway.com/movie/',
  //电影详情
  movieDetail:'https://www.wxapp-gateway.com/movie/subject'
}

LISTTYPE = {
  //正在热映
  1:'in_theaters',
  //即将上映
  2:'coming_soon'
}

handle = {
  //获取列表
  getMovieList:function(type,callback){
    var url = URL.movieList + LISTSTYPE[type];
    _fn.getData({
      url:url
    },callback);
  },
  //获取详情
  getMovieDetail:function(id,callback){
    var url = URL.movieDetail + '/' +id;
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
        apikey:''
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
    });
  }
}

module.exports = handle;