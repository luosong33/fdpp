<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">

<head>
    <title>数据实时处理平台</title>
    <link rel="stylesheet" href="css/index.css"/>
    <script src='js/jquery-1.11.3.js'></script>
    <script src='js/echarts.min.js'></script>
    <script src='js/index.js'></script>
    <script src='js/esl.js'></script>
    <script src='js/car.js'></script>
    <script src='js/gonggong.js'></script>
</head>

<body onload="showLeftTime()">
<div id="floor_Nav" style='display: none;'>
    <ul>
        <li class="last">Top</li>
    </ul>
</div>

<!--楼层-->

<div id='center'>
    <div id='header'>
        <div class='tou'>
            <center><span id='header_log'></span>
                <ul class='her_ul'>
                    <li><a href="PICC.jsp">总览</a></li>
                    <li><a href="car.jsp" style='background:url(img/nav_bg.png);color:white;'>车险</a></li>
                    <li><a href="biznocar.jsp">商业非车险</a></li>
                    <li><a href="other.jsp">其他</a></li>
                </ul>
            </center>
        </div>
    </div>
    <div class='center'>
        <div class='cbqx Floor'>
            <div class='cb_header'>
                <em class='cb_em1' style='background:url(img/nav_title2.png)'></em><em class='cb_em'></em>
                <div class='biaotou'>
                    <i>今日保费(元)</i>
                    <span class='tp'></span ><span id='numberRun' class='numberRun'></span>
                </div>
                <div class='biaotou'>
                    <i>今日签单(单)</i>
                    <span class='tp1'></span><span id='numberRun1' class='numberRun1'></span>
                </div>
                <div class='biaotou'>
                    <i>更新数据时间</i>
                    <span id='time' style='font-size:15px;'></span>
                </div>


                <div class='right'></div>
            </div>

            <div class='tubiao'>
                <span onclick="siMenu('li1')" style='position:absolute;background:#00aade;color:#fff;'><i
                        id='li1'>保单</i></span>
                <span onclick="siMenu('li2')"
                      style='position:absolute;border:1px solid #f1f3f4;left:70px;background:#fff;'><i
                        id='li2'>批单</i></span>
            </div>
            <div id='top_main'>
                <div id='main2' style="width: 900px;height:300px;float:left;margin-left:47px;"></div>

                <div id="main3" style="width: 900px;height:200px;float:left;margin-left:57px;"></div>
            </div>
            <div id='top_main1' style='display:none'>
                <div id='main' style="width: 900px;height:300px;float:left;margin-left:47px;"></div>

                <div id="xuxing" style="width: 900px;height:200px;float:left;margin-left:47px;"></div>
            </div>


        </div>

    </div>


    <div class='xzbf'>
        <div id="xzbf" style="width: 900px;height:400px;float:left;margin-left:80px;margin-top:20px;"></div>

    </div>

    <div class='zgs Floor'>
        <div class='zgs_div'>
            <span class='zgs_span_left'>总公司</span>
            <span class='zgs_span_right' id='ryn3' onclick="tab('ryn3')">年</span>
            <span class='zgs_span_right' id='ryn2' onclick="tab('ryn2')">月 </span>
            <span class='zgs_span_right' style='background:url(img/mnav_right_bg.png)' id='ryn1'
                  onclick="tab('ryn1')">日</span>
        </div>
        <div class='tab_class'>


            <div class='ryn'>
                <div class='tubiao'>
                    <span onclick="siMenu('li3')" style='position:absolute;background:#00aade;color:#fff;'><i id='li3'>图标</i></span>
                    <span onclick="siMenu('li4')"
                          style='position:absolute;border:1px solid #f1f3f4;left:70px;background:#fff;'><i
                            id='li4'>报表</i></span>
                </div>
                <div id='zgs_bottom'>
                    <div id="zgs" style="width: 900px;height:400px;float:left;margin-left:80px;"></div>

                </div>
                <div id='zgs_bottom1' style='display: none;'>
                    <div class='header_biao'>

                        <form action="#" class='biaodan' method="post">
                            <table class='tab' width="100%" border="0" cellpadding="0" cellspacing="0">
                                <th>机构名称</th>
                                <th>当日保费</th>
                                <th>当日签单</th>
                            </table>
                        </form>
                    </div>

                </div>

            </div>


            <div class='ryn_yue ' style='display: none;'>
                <div class='tubiao'>
                    <span onclick="siMenu('li5')" style='position:absolute;background:#00aade;color:#fff;'><i id='li5'>图标</i></span>
                    <span onclick="siMenu('li6')"
                          style='position:absolute;border:1px solid #f1f3f4;left:70px;background:#fff;'><i
                            id='li6'>报表</i></span>
                </div>
                <div id='zgs_bottom_yue'>

                    <div id="zgs_yue" style="width: 900px;height:400px;float:left;margin-left:80px;"></div>
                </div>
                <div id='zgs_bottom1_yue' style='display: none;'>
                    <form action="#" class='biaodan' method="post">
                        <table class='tab' width="100%" border="0" cellpadding="0" cellspacing="0">
                            <th>机构名称</th>
                            <th>当日保费</th>
                            <th>当日签单</th>
                        </table>
                    </form>
                </div>

            </div>


            <div class='ryn_nian ' style=' display: none;'>
                <div class='tubiao'>
                    <span onclick="siMenu1('li7')" style='position:absolute;background:#00aade;color:#fff;'><i id='li7'>图标</i></span>
                    <span onclick="siMenu1('li8')"
                          style='position:absolute;left:70px;border:1px solid #f1f3f4;background:#fff;'><i
                            id='li8'>报表</i></span>
                </div>
                <div id='zgs_bottom_nian'>
                    <div id="zgs_nian" style="width: 900px;height:400px;float:left;margin-left:80px;"></div>

                </div>
                <div id='zgs_bottom1_nian' style='display: none;'>
                    <form action="#" class='biaodan' method="post"
                    '>
                    <table class='tab' width="100%" border="0" cellpadding="0" cellspacing="0">
                        <th>机构名称</th>
                        <th>当日保费</th>
                        <th>当日签单</th>
                    </table>
                    </form>

                </div>

            </div>


        </div>


    </div>

</div>

<script language="javascript" type="text/javascript">
    //获得当前时间,刻度为一千分一秒
    var initializationTime = (new Date()).getTime();
    var time = document.getElementById('time');
    function showLeftTime() {
        /* var now=new Date();
         var year=now.getFullYear();
         var month=now.getMonth();
         var day=now.getDate();
         var hours=now.getHours();
         var minutes=now.getMinutes();
         var seconds=now.getSeconds();
         time.innerHTML=" "+year+"年"+(month+1)+"月"+day+"日 "+hours+":"+minutes+":"+seconds+"";
         //一秒刷新一次显示时间
         var timeID=setTimeout(showLeftTime,1000); */
    }


</script>


</body>


</html>
