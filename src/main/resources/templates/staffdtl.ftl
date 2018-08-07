<html>
<head>
<script src="/js/echarts.min.js"></script>
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<table class="table table-bordered table-hover table-layout: fixed">
				<thead>
					<tr>
						<th>工号</th>
						<th>姓名</th>
						<th>nt</th>
						<th>邮箱</th>
					</tr>
				</thead>
				<tbody>
				<#list staffList as staffInfo>
					<tr class="info">
					<td>${staffInfo.id}</td>
					<td>${staffInfo.name}</td>
					<td>${staffInfo.code}</td>
					<td>${(staffInfo.email)?default('')}</td>
					</tr>
				</#list>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>
