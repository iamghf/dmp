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
    
    <!-- 百度统计 -->
    <script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?c1b480119ac4758bb84d4885b5046e83";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>

    
</head>
<body>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="row">
    				<button class="btn btn-success" type="button" onclick="query(0)">报表</button>
    				<button class="btn btn-success" type="button" onclick="queryDtl(0)">明细</button>
    				<button class="btn btn-success" type="button" onclick="queryBug(0)">bug修复情况</button>
    				<button class="btn btn-success" type="button" onclick="refresh()">刷新</button>
			</div>  
		</div>
	<div class="row-fluid">
		<div class="span6">
			<div id="baobiao"></div>
		</div>
		<div class="span6">
			<div id="mingxi">
			</div>
		</div>
	</div>
</div>
</body>

<script >
var qdata,flag;
function queryDtl(flag){
	$("#mingxi").show();
	$("#baobiao").hide();
	$.ajax({ 
			async:false,
			type: "GET",                    
			contentType: "",
			url: "/doDmpQueryDetail?flag="+flag,                    
			data: "",                    
			dataType: 'html',
			timeout: 6000000,
			success: function(data){
				$("#mingxi").html(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("fuck! is error!");
			}
		});
}

function refresh(){
	query(1);
}

function queryBug(flag){
	window.open("/menuFixBug")
}

function query(flag){
	$("#mingxi").hide();
	$.ajax({ 
			async:false,
			type: "GET",                    
			contentType: "",
			url: "/doDmpQuery?flag="+flag,                    
			data: "",                    
			dataType: 'html',
			timeout: 6000000,
			success: function(data){
				$("#baobiao").show();
				$("#baobiao").html(data);
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) { 
				alert("fuck! is error!");
			}
		});
}
</script>
</html>