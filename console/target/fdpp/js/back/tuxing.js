$(function () {
    function flush(){
        $.ajax({
            url: 'query/json/findBaofeiInfo',
            type: 'post',
            async: false,
            data: {
                params: "{\"minute\":\"" + 60 * (new Date().getHours() * 60 + new Date().getMinutes()) + "\"}",
                dicts: "{}"
            },
            dataType: 'json',
            success: function (data) {
                var datatemo = data[0];
                drawBar('main2', 'main3', datatemo, data);
                drawBar('main', 'xuxing', datatemo);
            }
        });
    }
    flush();
    setInterval(flush,60000);
})

$(document).ready(function () {
    $.ajax({
        url: 'json/data.json',
        type: 'get',
        dataType: 'json',
        success: function (data) {

            sheng('zgs', data);
            sheng_yue('zgs_yue', data);
            sheng_nian('zgs_nian', data);
        }
    })
     
    
    function dataxzbf(){
    	 $.ajax({
    	        url: 'query/json/findXzbfInfo',
    	        type: 'post',
    	        async: false,
    	        data: {
    	            params: "{\"minute\":\"" + 60 * (new Date().getHours() * 60 + new Date().getMinutes()) + "\"}",
    	            dicts: "{}"
    	        },
    	        dataType: 'json',
    	        success: function (data) {
    	    var data0 = data[0];
    	     require.config({
    	        paths: {
    	            'echarts': 'http://echarts.baidu.com/build/echarts',
    	            'echarts/chart/line': 'http://echarts.baidu.com/build/echarts',
    	            'echarts/chart/bar': 'http://echarts.baidu.com/build/echarts',
    	            'echarts/chart/k': 'http://echarts.baidu.com/build/echarts'
    	          }
    	         });
    	    // 使用
    	    require(['echarts', 'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
    	            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
    	        ], function (ec) {
    	            var myChartxzbf = ec.init(document.getElementById("xzbf"));  
    	            optionxzbf = { 
    	                title: {
    	                    text: ''
    	                },
    	                tooltip: {
    	                    trigger: 'axis', 
    	                    formatter: function (params) {
    	                    	 var res = '';
    	                         res = params[0][1] + '<br/>';
    	                         res += params[0][0] + ":  ";
    	                         res += params[0][2] + '<br/>';
    	                         if (params.length >= 2) {
    	                             var res1 = '';
    	                             res1 += params[1][0] + ":  ";
    	                             res1 += params[1][2];
    	                             var he = '';
    	                             he = res + res1;
    	                         } else {
    	                             he = res;
    	                         }
    	                         return he;
    	                    }
    	                },
    	                legend: { 
    	                    data: ['10分钟新增保费', '10分钟新增签单'],
    	                },
    	                toolbox: {
    	                    show: true, 
    	                }, 
    	                xAxis: [
    	                        {
    	                            type: 'category',
    	                            boundaryGap: true,
    	                            axisTick: {onGap: false},
    	                            splitLine: {show: false},     	                            
    	                            axisLabel: {
    	                                interval: 5
    	                            },
    	                            data:data0.time.split(",")

    	                        }
    	                    ],
    	                    yAxis: [
    	                        {

    	                            type: 'value',
    	                            name: "保费（元）",
    	                            scale: true,
    	                            splitNumber: 5,
    	                            boundaryGap: [0.01, 0.01]
    	                        },
    	                        {
    	                            type: 'value',
    	                            name: '签单（单）',
    	                            scale: true,
    	                            splitNumber: 5,
    	                            boundaryGap: [0.01, 0.01], 
    	                            splitLine: {
    	                                show: false
    	                            }
    	                        }

    	                    ],
    	                    series: [
    	                        {
    	                            name: '10分钟新增保费',
    	                            type: 'line',
    	                            legendHoverLink: false,
    	                            itemStyle: {
    	                                normal: {
    	                                    color: '#acd1d9',
    	                                },
    	                            }, 
    	                            data:  data0.data3.split(",")

    	                        },
    	                        {
    	                            name: '10分钟新增签单',
    	                            type: 'line', 
    	                            legendHoverLink: false, 
    	                            yAxisIndex: 1,
    	                            symbol: 'none',
    	                            itemStyle: {
    	                                normal: {
    	                                    color: '#ff987f',
    	                                },
    	                            },
    	                            data: data0.data5.split(",")
    	                        }
    	                    ]
    	            };   
    	            myChartxzbf.setOption(optionxzbf); 
    	           }
    	          );
    	        }
    	    });
    }
    dataxzbf();
    setInterval(dataxzbf,60000);
    //最后一个   36个省份   日
    function sheng(divId1, data) {

        var chart = document.getElementById(divId1);
        var chartData = echarts.init(chart);

        chartData.setOption({
            title: {
                text: '',
                textStyle: {
                    color: '#000',
                    fontSize: '14px',
                    fontFamily: 'Arial,Verdana,Sans-serif',
                    fontWeight: 'normal'
                }
            },
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                x: 70,
                y: 70,
                width: '87%',
                height: '55%',
                borderWidth: 0
            },
            legend: {
                orient: 'horizontal', //布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
                x: 'center',
                y: '10%', //图例定位
                show: true,
                data: ['当日保费(万元)', '当日签单(单)'],

            },
            calculable: true,
            xAxis: {
                type: 'category',
                //	            boundaryGap:false,
                axisLabel: {
                    interval: 0,
                    rotate: 45,
                    margin: 2,
                    textStyle: {
                        color: "#222"
                    }
                },

                data: data.categories
            },
            yAxis: [
                {
                    name: '单位：（百万元）',
                    type: 'value'
                }, {

                    type: 'value',
                    name: '签单（单）',
                    splitNumber: 10,
                    axisLabel: {
                        formatter: function (value) {
                            //	console.log(value)
                            // Function formatter
                            return (value * 1000)
                        }
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [{
                name: '当日保费(万元)',
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: '#00b0f0'
                    }
                },
                data: data.data1
            },
                {
                    name: '当日签单(单)',
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            color: '#ff9900'//设置柱状颜色的
                        }
                    },
                    data: data.data2
                }]
        });

    }
});

//月
function sheng_yue(divId1, data) {

    var chart = document.getElementById(divId1);
    var chartData = echarts.init(chart);

    chartData.setOption({
        title: {
            text: '',
            textStyle: {
                color: '#000',
                fontSize: '14px',
                fontFamily: 'Arial,Verdana,Sans-serif',
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            x: 70,
            y: 70,
            width: '87%',
            height: '55%',
            borderWidth: 0
        },
        legend: {
            orient: 'horizontal', //布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
            x: 'center',
            y: '10%', // 图例定位
            show: true,
            data: ['累计保费(百万元)', '累计签单签单(单)'],

        },
        calculable: true,
        xAxis: {
            type: 'category',
            //	            boundaryGap:false,
            axisLabel: {
                interval: 0,
                rotate: 45,
                margin: 2,
                textStyle: {
                    color: "#222"
                }
            },

            data: data.categories
        },
        yAxis: [
            {
                name: '单位：（百万元）',
                type: 'value'
            }, {

                type: 'value',
                name: '签单（单）',
                splitNumber: 10,
                axisLabel: {
                    formatter: function (value) {
                        //	console.log(value)
                        // Function formatter
                        return (value * 1000)
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        series: [{
            name: '累计保费(百万元)',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#00b0f0'
                }
            },
            data: data.data1
        },
            {
                name: '累计签单签单(单)',
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: '#ff9900'//设置柱状颜色的
                    }
                },
                data: data.data2
            }]
    });

}

//年
function sheng_nian(divId1, data) {

    var chart = document.getElementById(divId1);
    var chartData = echarts.init(chart);

    chartData.setOption({
        title: {
            text: '',
            textStyle: {
                color: '#000',
                fontSize: '14px',
                fontFamily: 'Arial,Verdana,Sans-serif',
                fontWeight: 'normal'
            }
        },
        tooltip: {
            trigger: 'item'
        },
        grid: {
            x: 70,
            y: 70,
            width: '87%',
            height: '55%',
            borderWidth: 0
        },
        legend: {
            orient: 'horizontal', //布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
            x: 'center',
            y: '10%', //图例定位
            show: true,
            data: ['累计保费(百万元)', '累计签单签单(单)'],

        },
        calculable: true,
        xAxis: {
            type: 'category',
            //	            boundaryGap:false,
            axisLabel: {
                interval: 0,
                rotate: 45,
                margin: 2,
                textStyle: {
                    color: "#222"
                }
            },

            data: data.categories
        },
        yAxis: [
            {
                name: '单位：（百万元）',
                type: 'value'
            }, {

                type: 'value',
                name: '签单（单）',
                splitNumber: 10,
                axisLabel: {
                    formatter: function (value) {
                        //	console.log(value)
                        // Function formatter
                        return (value * 1000)
                    }
                },
                splitLine: {
                    show: false
                }
            }
        ],
        series: [{
            name: '累计保费(百万元)',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: '#00b0f0'
                }
            },
            data: data.data1
        },
            {
                name: '累计签单签单(单)',
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: '#ff9900'//设置柱状颜色的
                    }
                },
                data: data.data2
            }]
    });

}

function drawBar(divId, divId2, data, data2) {
    // 路径配置
    require.config({
        paths: {
            'echarts': 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/line': 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/bar': 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/k': 'http://echarts.baidu.com/build/echarts'
        }
    });
    // 使用
    require(['echarts', 'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ], function (ec) {
            var myChart3 = ec.init(document.getElementById(divId));
            var myChart4 = ec.init(document.getElementById(divId2));
            
            option2 = {

                title: {
                    text: ''
                },
                tooltip: {
                    trigger: 'axis',

                    formatter: function (params) {

                        var res = '';
                        res = params[0][1] + '<br/>';
                        res += params[0][0] + ":  ";
                        res += params[0][2] + '<br/>';
                        if (params.length >= 2) {
                            var res1 = '';
                            res1 += params[1][0] + ":  ";
                            res1 += params[1][2][0];
                            var he = '';
                            he = res + res1;
                        } else {
                            he = res;
                        }
                        return he;
                    }
                },
                legend: {

                    data: ['实时保费（元）', '当日签单（单）', '实时签单（单）']
                },
                toolbox: {
                    show: true,

                },
                dataZoom: {
                    show: true,
                    realtime: true,
                    start: 95,
                    end: 100
                },
                xAxis: [{
                    type: 'category',
                    boundaryGap: true,
                    axisTick: {
                        onGap: false
                    },
                    splitLine: {
                        show: false
                    },
                    data: data.time.split(",")
                }],
                yAxis: [{

                    type: 'value',
                    name: "保费（元）",
                    scale: true,
                    splitNumber: 5,
                    boundaryGap: [0.01, 0.01]
                }, {
                    type: 'value',
                    name: '签单（单）',
                    scale: true,
                    splitNumber: 5,
                    boundaryGap: [0.01, 0.01],
                    //          axisLabel: {
                    //              formatter: function (v) {
                    //              	//console.log(v);
                    ////                  return Math.round(v/10000)+'万'
                    //                  return v;
                    //              }
                    //          }
                }],
                series: [
                    {
                        name: '当日签单（单）',
                        type: 'line',
                        legendHoverLink: false,
                        itemStyle: {
                            normal: {
                                color: '#acd1d9',
                            },
                        },
                        yAxisIndex: 1,
                        symbol: 'none',
                        data: eval("[" + data.data1 + "]")
                    },
                    {
                        name: '实时保费（元）',
                        type: 'k',
                        legendHoverLink: false,
                        itemStyle: {
                            normal: {
                                color: '#ff987f',
                            },
                        },
                        data: eval("[" + data.data2 + "]")
                    }]
            };

            option3 = {
                tooltip: {
                    trigger: 'axis',
                    showDelay: 0
                    // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                },
                legend: {
                    y: -30,
                    data: ['实时保费（万元）', '当日签单（单）', '实时签单（单）']
                },
                toolbox: {
                    y: -30,
                    show: true,
                },
                grid: {
                    x: 70,
                    y: 40,
                    width: '87%',
                    height: '55%',
                    borderWidth: 0
                },
                xAxis: [{
                    type: 'category',
                    position: 'bottom',
                    boundaryGap: true,
                    axisTick: {
                        onGap: false
                    },
                    splitLine: {
                        show: false
                    },
                    data: data.time.split(",")
                }],
                yAxis: [

                    {
                        type: 'value',
                        scale: true,
                        name: '签单（单）',
                    }],
                series: [{
                    name: '实时签单',
                    legendHoverLink: false,
                    itemStyle: {
                        normal: {
                            color: '#87cefa',
                        },
                    },
                    type: 'bar',
                    symbol: 'none',
                    data: data.data5.split(","),
                }]
            };

          myChart3.connect([myChart4]);
            setTimeout(function () {
                window.onresize = function () {

                    myChart3.resize();
                    myChart4.resize();
                }
            }, 200);
            // 为echarts对象加载数据
            //myChart.setOption(option);

            // 为echarts2对象加载数据
            myChart3.setOption(option2);

            // 为echarts3对象加载数据
              myChart4.setOption(option3);

        }
    )
}



   