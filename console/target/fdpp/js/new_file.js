function drawBar(divId, divId2, data) {
    // 路径配置
    require.config({
        paths: {
            'echarts': 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/line': 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/bar': 'http://echarts.baidu.com/build/echarts'
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
            // 基于准备好的dom，初始化echarts图表
            var myChart2 = ec.init(document.getElementById(divId));
            var myChart3 = ec.init(document.getElementById(divId2));


            option2 = {
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
                    showDelay: 0,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                },
                legend: {
                    orient: 'horizontal', //布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
                    x: 'center',
                    y: '10%', //图例定位
                    show: true,
                    data: ['实时保费（万元）', '实时签单（单）', '当日签单（单）', '当日车险保费（万元）']
                },

                toolbox: {
                    show: true
                },
                dataZoom: {
                    type: 'inside',
                    show: true,
                    realtime: true,
                    start: 50,
                    end: 100
                },
                grid: {
                    x: 70,
                    y: 70,
                    width: '87%',
                    height: '55%',
                    borderWidth: 0
                },


                xAxis: [{
                    type: 'category',
                    data: data.time
                }],
                yAxis: [
                    {
                        type: 'value',
                        scale: true,
                        name: '保费（万元）'
                    },
                    {
                        type: 'value',
                        scale: true,
                        name: '签单（单）',
                        max: 1200,
                        min: 0,
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                series: [
                    {
                        name: '实时保费（万元）',
                        type: 'line',
                        data: data.data1
                    },
                    {
                        name: '实时签单（单）',
                        type: 'line',
                        data: data.data3

                    },
                    {
                        name: '当日签单（单）',
                        type: 'line',
                        data: data.data2

                    },
                    {
                        name: '当日车险保费（万元）',
                        type: 'line',
                        data: data.data1

                    },

                ]
            };

            option3 = {
                tooltip: {
                    trigger: 'axis',
                    showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                },
                legend: {
                    y: -30,
                    data: ['上证指数', '成交金额(万)', '虚拟数据']
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
                        name: '保费（万元）',
                    },
                    {
                        type: 'value',
                        scale: true,
                        name: '签单（单）',
                        max: 1200,
                        min: 0,
                        boundaryGap: [0.2, 0.2]
                    }
                ],
                series: [
                    {
                        name: '虚拟数据',
                        type: 'bar',
                        symbol: 'none',
                        data: data.data1
                    }
                ]
            };


            myChart2.connect([myChart3]);
            /*setTimeout(function (){
             window.onresize = function () {

             myChart2.resize();
             myChart3.resize();
             }
             },200);*/
            // 为echarts对象加载数据
            //myChart.setOption(option);
            // 为echarts2对象加载数据
            myChart2.setOption(option2);
            // 为echarts3对象加载数据
            myChart3.setOption(option3);
        }
    );
};

