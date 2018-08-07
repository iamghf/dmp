<html>
<head>
<script src="/js/jquery-3.2.1.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap/bootstrap-table.css">
<script src="/bootstrap/bootstrap-table.js"></script>
<script src="/bootstrap/bootstrap-table-zh-CN.js"></script>
</head>
<body>
 <table id="test-table" class="col-xs-12" data-toolbar="#toolbar">
</body>
<script>
$(function(){
	$('#test-table').bootstrapTable({
	    url: 'data1.json',
	    columns: [{
	        field: 'id',
	        title: 'Item ID'
	    }, {
	        field: 'name',
	        title: 'Item Name'
	    }, {
	        field: 'price',
	        title: 'Item Price'
	    }, ]
	});
});
</script>
</html>