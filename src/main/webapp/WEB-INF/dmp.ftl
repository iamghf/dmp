<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="${rc.contextPath}/js/echarts.min.js"></script>
    <script src="${rc.contextPath}/js/jquery-3.2.1.min.js"></script>
</head>
<body>

<#assign json="${categories}" />
<#assign json2="${reqdata}" />
<#assign json3="${bugdata}" />

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 800px; height: 600px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title : {
            text : '任务统计信息'
        },
        tooltip : {},
        legend : {
            data : [ '需求','bug' ]
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis : {
            type : 'category',
            axisLabel :{
                interval:0
            },
            data : ${json}
        },
        yAxis : {},
        series : [ {
            name : '需求',
            type : 'bar',
            data : ${json2}
        } ,{
            name : 'bug',
            type : 'bar',
            data : ${json3}
        } ]
    });
    // 使用刚指定的配置项和数据显示图表。
    //myChart.showLoading();
</script>
</body>
</html>