$(function () {
    $.ajax({
        url: 'json/baofei.json',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            drawBar('main2', 'main3', data);
            drawBar('main', 'xuxing', data)

            console.log(data);
        }
    })
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

    /*------------------------------------------------------------------------------------------ */
    //每日保单保费
    var baofei = document.getElementById('xzbf');
    var baofeiData = echarts.init(baofei);
    baofeiData.setOption({
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
            trigger: 'axis',
            formatter: function (params) {
                console.log(params);
                var res = '';
                res = params[0].name + '<br/>';
                res += params[0].seriesName + ':';
                res += params[0].value + "（万元）<br/>";

                var res1 = '';
                res1 += params[1].seriesName + ':';
                res1 += params[1].value + "（万元）<br/>";
                var he = res + res1;
                return he;
            }
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
            data: ['10分钟新增保费', '10分钟新增签单'],

        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: true,
                axisTick: {onGap: false},
                splitLine: {show: false},
                axisLabel: {
                    interval: 4
                },
                data: []

            }
        ],
        yAxis: [
            {

                type: 'value',
                name: "保费（万元）",
                scale: true,
                splitNumber: 5,
                boundaryGap: [0.01, 0.01]
            },
            {
                type: 'value',
                name: '签单（单）',
                splitNumber: 10,
                axisLabel: {
                    formatter: function (value) {

                        return (value * 1000)
                    }
                },
                splitLine: {
                    show: false
                }
            }

        ],
        series: [
            {
                name: '10分钟新增保费',
                type: 'line',
                data: [],

            },
            {
                name: '10分钟新增签单',
                type: 'line',
                data: [],
            }
        ]
    })
    $.get('json/baofei.json').done(function (data) {


        // 填入数据
        baofeiData.setOption({
            xAxis: {
                data: data.time
            },
            series: [{
                name: '10分钟新增保费',
                data: data.data5
            },
                {
                    name: '10分钟新增签单',
                    data: data.data2
                }]
        });

    });


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


function drawBar(divId, divId2, data) {
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
    require(
        [
            'echarts',
            'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
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

                        var res1 = '';
                        res1 += params[1][0] + ":  ";
                        res1 += params[1][2][0];
                        var he = '';
                        he = res + res1;
                        return he;
                    }
                },
                legend: {

                    data: ['实时保费（万元）', '当日签单（单）', '实时签单（单）']
                },
                toolbox: {
                    show: true,

                },
                dataZoom: {
                    show: true,
                    realtime: true,
                    start: 50,
                    end: 100
                },
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: true,
                        axisTick: {onGap: false},
                        splitLine: {show: false},
                        data: data.time,
                    }
                ],
                yAxis: [
                    {

                        type: 'value',
                        name: "保费（万元）",
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
                    }
                ],
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
                        data: data.data1,
                    },
                    {
                        name: '实时保费（万元）',
                        type: 'k',
                        legendHoverLink: false,
                        itemStyle: {
                            normal: {
                                color: '#ff987f',
                            },
                        },
                        data: [
                            [2320.26, 2302.6, 2320.26, 2302.6],
                            [9111, 9011, 9111, 9011],
                            [2295.35, 2200.5, 2295.35, 2200.5],
                            [2359.22, 2358.98, 2359.22, 2358.98],

                            [2390.75, 2382.48, 2390.75, 2382.48],

                            [2393.43, 2385.42, 2393.43, 2385.42],

                            [2877.41, 2419.02, 2877.41, 2419.02],

                            [2429.92, 2428.15, 2429.92, 2428.15],

                            [2461, 2433.13, 2461, 2433.13],

                            [2472.68, 2434.48, 2472.68, 2434.48],

                            [2430.69, 2418.53, 2430.69, 2418.53],

                            [2486.62, 2432.4, 2486.62, 2432.4],

                            [2441.91, 2421.56, 2441.91, 2421.56],

                            [2420.26, 2382.91, 2420.26, 2382.91],

                            [2483.49, 2397.18, 2483.49, 2397.18],

                            [2378.82, 2325.95, 2378.82, 2325.95],

                            [2322.94, 2314.16, 2322.94, 2314.16],

                            [2420.62, 2325.82, 2420.62, 2325.82],

                            [2313.74, 2293.34, 2313.74, 2293.34],

                            [2397.77, 2313.22, 2397.77, 2313.22],

                            [2522.32, 2365.59, 2522.32, 2365.59],

                            [2364.54, 2359.51, 2364.54, 2359.51],

                            [2332.08, 2273.4, 2332.08, 2273.4],

                            [2774.81, 2326.31, 2774.81, 2326.31],

                            [2373.61, 2347.18, 2373.61, 2347.18],

                            [2340.44, 2324.29, 2340.44, 2324.29],

                            [2326.42, 2318.61, 2326.42, 2318.61],

                            [2314.68, 2310.59, 2314.68, 2310.59],

                            [2309.16, 2286.6, 2309.16, 2286.6],

                            [2282.17, 2263.97, 2282.17, 2263.97],


                            [2285.77, 2270.28, 2285.77, 2276.22],

                            [2369.31, 2278.4, 2369.31, 2278.4],

                            [2267.29, 2240.02, 2267.29, 2240.02],

                            [2294.26, 2257.43, 2294.26, 2257.43],

                            [2457.74, 2317.37, 2457.74, 2317.37],

                            [2348.21, 2324.24, 2348.21, 2324.24],
                            [2421.4, 2328.28, 2421.4, 2328.28],

                            [2334.74, 2326.72, 2334.74, 2326.72],
                            [2318.58, 2297.67, 2318.58, 2297.67],
                            [2399.38, 2301.26, 2399.38, 2301.26],
                            [2273.55, 2236.3, 2273.55, 2236.3],
                            [2238.49, 2236.62, 2238.49, 2236.62],
                            [2289.46, 2234.4, 2289.46, 2234.4],
                            [2234.9, 2227.74, 2234.9, 2227.74],
                            [2232.69, 2225.29, 2232.69, 2225.29],
                            //[2196.24,2211.59,2180.67,2212.59],
                            [2196.24, 2096.24, 2196.24, 2096.24],


                            [2235.47, 2225.77, 2235.47, 2225.77],

                            [2324.93, 2226.13, 2324.93, 2226.13],
                            [2236.98, 2219.55, 2236.98, 2219.55],
                            [2218.09, 2206.78, 2218.09, 2206.78],
                            [2199.91, 2181.94, 2199.91, 2181.94],
                            [2269.63, 2194.85, 2269.63, 2194.85],
                            [2195.03, 2193.8, 2195.03, 2193.8],


                            [2281.82, 2197.6, 2281.82, 2197.6],
                            [2251.12, 2244.64, 2251.12, 2244.64],
                            [2276.4, 2242.17, 2276.4, 2242.17],
                            [2242.62, 2184.54, 2242.62, 2184.54],
                            [2387.35, 2218.32, 2387.35, 2218.32],
                            [2313.19, 2199.31, 2313.19, 2199.31],
                            [2203.89, 2177.91, 2203.89, 2177.91],
                            [2270.78, 2174.12, 2270.78, 2174.12],
                            [2379.05, 2205.5, 2379.05, 2205.5],
                            [2242.5, 2231.17, 2242.5, 2231.17],
                            [2257.86, 2235.57, 2257.86, 2235.57],
                            [2442.39, 2246.3, 2442.39, 2246.3],
                            [2246.96, 2232.97, 2246.96, 2232.97],
                            [2278.82, 2246.83, 2278.82, 2246.83],
                            [2247.68, 2241.92, 2247.68, 2241.92],
                            [2238.9, 2217.01, 2238.9, 2217.01],
                            [2247.09, 2224.8, 2247.09, 2224.8],
                            [2621.34, 2251.81, 2621.34, 2251.81],
                            [2349.81, 2282.87, 2349.81, 2282.87],
                            [2386.33, 2299.99, 2386.33, 2299.99],
                            [2397.11, 2305.11, 2397.11, 2305.11],
                            [2303.75, 2302.4, 2303.75, 2302.4],
                            [2293.81, 2275.67, 2293.81, 2275.67],
                            [2381.45, 2288.53, 2381.45, 2288.53],
                            [2386.66, 2293.08, 2386.66, 2293.08],
                            [2593.4, 2321.32, 2593.4, 2322.32],
                            [2329.54, 2324.02, 2329.54, 2324.02],
                            [2319.25, 2317.75, 2319.25, 2317.75],
                            [2320.74, 2300.59, 2320.74, 2300.59],
                            [2300.21, 2299.25, 2300.21, 2299.25],
                            [2297.1, 2272.42, 2297.1, 2272.42],
                            [2280.71, 2270.93, 2280.71, 2270.93],
                            [2264.43, 2242.11, 2264.43, 2242.11],
                            [2242.26, 2210.9, 2242.26, 2210.9],
                            [2190.1, 2148.35, 2190.1, 2148.35]
                        ]
                    }
                ]
            };

            option3 = {
                tooltip: {
                    trigger: 'axis',
                    showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
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
                xAxis: [
                    {
                        type: 'category',
                        position: 'bottom',
                        boundaryGap: true,
                        axisTick: {onGap: false},
                        splitLine: {show: false},
                        data: data.time
                    }
                ],
                yAxis: [

                    {
                        type: 'value',
                        scale: true,
                        name: '签单（单）',
                    }
                ],
                series: [
                    {
                        name: '实时签单',
                        legendHoverLink: false,
                        itemStyle: {
                            normal: {
                                color: '#87cefa',
                            },
                        },
                        type: 'bar',
                        symbol: 'none',
                        data: data.data5,
                    }
                ]
            };

            myChart3.connect([myChart4]);
            setTimeout(function () {
                window.onresize = function () {

                    myChart3.resize();
                    myChart4.resize();
                }
            }, 200);

            myChart3.setOption(option2);

            myChart4.setOption(option3);

        }
    )
}



   