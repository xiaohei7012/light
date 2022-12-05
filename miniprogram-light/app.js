// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  globalData: {
    // url:'https://localhost:442/',
    url:'https://hanshou.hskj-voc.com:442/',
    fanGear:[{
      name:'关',
      value:'F0'
    },
    {
      name:'1档',
      value:'F1'
    },
    {
      name:'2档',
      value:'F2'
    },
    {
      name:'3档',
      value:'F3'
    },
    {
      name:'4档',
      value:'F4'
    },
    {
      name:'5档',
      value:'F5'
    },
    {
      name:'6档',
      value:'F6'
    },
    {
      name:'7档',
      value:'F7'
    },
    {
      name:'8档',
      value:'F8'
    },
    {
      name:'9档',
      value:'F9'
    }],
    userInfo: null
  }
})
