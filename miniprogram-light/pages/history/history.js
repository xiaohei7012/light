// pages/history/history.js
import * as echarts from '../../ec-canvas/echarts';

var service = require('../../service/device');
var utils = require('../../common/utils/utils');
var chart; //拿出来作为全局变量
var _fn = {};

Page({
    /**
     * Page initial data
     */
    data: {
        historyList: [],
        ec: {
          onInit: initChart
        }
    },
  
    /**
     * Lifecycle function--Called when page load
     */
    onLoad(options) {
        var that = this;
        var data = {
            id: options.did
        }
        service.getHistory(data, function (e) {
            for(var h in e.info){
              e.info[h] = _fn.render.call(that,e.info[h]);
            }
            that.setData({
                historyList: e.info
            })
            _fn.refreshGraph(e.info);
        })
    },

    /**
     * Lifecycle function--Called when page is initially rendered
     */
    onReady() {
      
    },

    /**
     * Lifecycle function--Called when page show
     */
    onShow() {

    },

    /**
     * Lifecycle function--Called when page hide
     */
    onHide() {

    },

    /**
     * Lifecycle function--Called when page unload
     */
    onUnload() {

    },

    /**
     * Page event handler function--Called when user drop down
     */
    onPullDownRefresh() {

    },

    /**
     * Called when page reach bottom
     */
    onReachBottom() {

    },

    /**
     * Called when user click on the top right corner to share
     */
    onShareAppMessage() {

    }
});

_fn = {
    render: function (data) {
      //时间
      data.ctime = utils.formatTime(new Date(data.ctime));
      //风扇
      data.fan = data.fan.substr(1,2) + "档";
      //灯
      var l = "l";
      var number = 1;
      var status;
      for(;number<=6;number++){
        switch(data[l+number]){
          case 'OFF': status = '关';break;
          case 'ON': status = '开';break;
          case 'BAD': status = '坏';break;
        }
        data[l+number] = status;
      }
      return data;
    },
    refreshGraph:function(data){
      var Xdata = [];
      for(var h in data){
        var c = 0;
        //灯
        var l = "l";
        var number = 1;
        var status;
        for(;number<=6;number++){
          switch(data[l+number]){
            case '开': c++;break;
          }
        }
        Xdata.push(c);
      }

      var series = [{
        data: Xdata,
        type: 'line'
      }]
      var option = {
          series: series
      }
      console.log("refresh data!")
      chart.setOption(option);
    }
}

function initChart(canvas, width, height) {
    chart = echarts.init(canvas, null, {
        width: width,
        height: height
    });
    canvas.setChart(chart);
    var option = {
      color: ["#37A2DA", "#67E0E3", "#9FE6B8"],
      grid: {
        containLabel: true
      },
      tooltip: {
        show: true,
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        boundaryGap: false
      },
      yAxis: {
        nameLocation: 'end',
        x: 'center',
        type: 'value',
        splitLine: {
          lineStyle: {
            type: 'dashed'
          }
        }
      }
    };
    chart.setOption(option); //刷新图表数据
    return chart;
  }
