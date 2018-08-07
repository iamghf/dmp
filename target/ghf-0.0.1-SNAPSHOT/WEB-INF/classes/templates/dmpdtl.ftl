<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<table class="table table-bordered table-hover table-layout: fixed">
				<thead>
					<tr>
						<th>姓名</th>
						<th>项目</th>
						<th>任务类型</th>
						<th>任务单号</th>
						<th>任务名称</th>
					</tr>
				</thead>
				<tbody>
				<#list reqList as reqInfo>
					<tr class="info">
					<td>${reqInfo.name}</td>
					<td>${reqInfo.projectName}</td>
					<td>${reqInfo.bizType}</td>
					<td>${reqInfo.taskNo}</td>
					<td>${reqInfo.taskName}</td>
					</tr>
				</#list>
				</tbody>
			</table>
		</div>
	</div>
</div>