<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>Top</title>
    <%@ include file="/common/miniui.jsp" %>
</head>
<body>
mapper:<input id="mapper"/><br/>
method中文:<input id="method"/><br/>
params:<textarea id="params" cols="50" rows="5"></textarea><br/>
dicts:<textarea id="dicts" cols="50" rows="5"></textarea><br/>
<button id="query">提交</button>
<br/>
result:<textarea id="result" cols="50" rows="5"></textarea>
<script>
    $(function () {
        $('#query').click(function () {
            $.post('/fdpp/query/' + $('#mapper').val() + '/' + $('#method').val(),
                    {
                        params: $('#params').val(),
                        dicts: $('#dicts').val()
                    }, function (data) {
                        $('#result').text(data)
                    })
        });
    });
</script>
</body>
</html>