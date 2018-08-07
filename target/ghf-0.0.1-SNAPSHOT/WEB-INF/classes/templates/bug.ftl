<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>dmp任务单统计</title>
    <script src="/js/echarts.min.js"></script>
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    
    <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>  
	<link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">  
	<script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>  
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="row">  
    <div class='col-sm-6'>  
        <div class="form-group">  
            <label>选择开始时间：</label>  
            <!--指定 date标记-->  
            <div class='input-group date' id='datetimepicker1'>  
                <input type='text' class="form-control" />  
                <span class="input-group-addon">  
                    <span class="glyphicon glyphicon-calendar"></span>  
                </span>  
            </div>  
        </div>  
    </div>  
    <div class='col-sm-6'>  
        <div class="form-group">  
            <label>选择结束时间：</label>  
            <!--指定 date标记-->  
            <div class='input-group date' id='datetimepicker2'>  
                <input type='text' class="form-control" />  
                <span class="input-group-addon">  
                    <span class="glyphicon glyphicon-calendar"></span>  
                </span>  
            </div>  
        </div>  
    </div>  
</div>  
			<button class="btn btn-success" type="button" onclick="query()">查询</button>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6">
			<div id="todo"></div>
		</div>
		<div class="span6">
			<div id="done"></div>
		</div>
	</div>
</div>
</body>

<script >

$(function () {  
    var picker1 = $('#datetimepicker1').datetimepicker({  
        format: 'YYYY-MM-DD',  
        locale: moment.locale('zh-cn'),  
        //minDate: '2016-7-1'  
    });  
    var picker2 = $('#datetimepicker2').datetimepicker({  
        format: 'YYYY-MM-DD',  
        locale: moment.locale('zh-cn')  
    });  
    //动态设置最小值  
    picker1.on('dp.change', function (e) {  
        picker2.data('DateTimePicker').minDate(e.date);  
    });  
    //动态设置最大值  
    picker2.on('dp.change', function (e) {  
        picker1.data('DateTimePicker').maxDate(e.date);  
    });  
});  

function query(){
	alert($("#datetimepicker1").data("datetimepicker").getDate() );
	return;
	$.ajax({ 
			async:false,
			type: "GET",                    
			contentType: "",
			url: "/doDmpQueryBug",                    
			data: "",                    
			dataType: 'html',
			timeout: 6000000,
			success: function(data){
				//$("#todo").html(data);
				$("#done").html(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("fuck! is error!");
			}
		});
}
</script>
</html>