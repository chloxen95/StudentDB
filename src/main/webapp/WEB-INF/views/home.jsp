<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Student Info</title>
</head>

<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
.help-block{
	color: red;
	margin-bottom: 0px;
}
</style>

<body>
<div class="container" style="position: relative">
	<h1>Student Info</h1>
	<button class="btn btn-default" id="ops-insert" style="position: absolute; right: 15px; top: 15px" 
			data-toggle="modal" data-target="#ops">增加信息</button>
	<table id="info" class="table table-hover">
		<!-- <tr>
        	<th style="width: 15%;">ID</th>
        	<th>姓名</th>
        	<th>出生日期</th>
        	<th style="width: 10%;">描述</th>
        	<th>平均分</th>
    	</tr> -->
	</table>
	<div>
		<ul class="pagination" id="page-btn-area" style="position: fixed; right: 10%; bottom: 2px">
   			<!-- <li><button class="page btn btn-default" value="previous" onclick=pagingControlForNumBtn(this)>&laquo;</button></li>
		    <li><button class="page btn btn-default" value=1 onclick=pagingControlForNumBtn(this)>1</button></li>
		    <li><button class="page btn btn-default" value="next" onclick=pagingControlForNumBtn(this)>&raquo;</button></li> -->
		</ul>
	</div>
	<!-- 操作面板 -->
	<div class="modal fade" id="ops" tabindex="-1" role="dialog" 
		aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static"  data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-info">
					<h4 class="modal-title" id="ops-title">操作面板</h4>
				</div>
				<div class="modal-body" id="ops-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label for="ops-id" class="col-sm-2 control-label">ID</label>
							<div class="col-sm-7">
								<input type="text" class="ops-text form-control" id="ops-id"
									placeholder="无需输入，自动生成" readonly/>
							</div>
							<span class="help-block" id="vali-id">请勿输入ID</span>
						</div>
						<div class="form-group">
							<label for="ops-name" class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-7">
								<input type="text" class="ops-text form-control" id="ops-name"
									placeholder="请输入姓名，长度1~40字符" />
							</div>
							<span class="help-block" id="vali-name">长度1~40字符</span>
						</div>
						<div class="form-group">
							<label for="ops-birthday" class="col-sm-2 control-label">出生日期</label>
							<div class="col-sm-7">
								<input type="text" class="ops-text form-control"
									id="ops-birthday" placeholder="请输入出生日期，格式yyyy-mm-dd" />
							</div>
							<span class="help-block" id="vali-birthday">格式yyyy-mm-dd</span>
						</div>
						<div class="form-group">
							<label for="ops-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-7">
								<input type="text" class="ops-text form-control"
									id="ops-description" placeholder="请输入描述，长度1~255字符" />
							</div>
							<span class="help-block" id="vali-description">长度1~255字符</span>
						</div>
						<div class="form-group">
							<label for="ops-avgscore" class="col-sm-2 control-label">平均分</label>
							<div class="col-sm-7">
								<input type="text" class="ops-text form-control"
									id="ops-avgscore" placeholder="请输入平均分，平均分为整数" />
							</div>
							<span class="help-block" id="vali-avgscore">平均分为整数</span>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="ops-submit">确定</button>
					<button type="button" class="btn btn-default" id="ops-abort" data-dismiss="modal">取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- /.操作面板 -->
</div>
</body>
<script>

//$(function(){

// 全局变量
	var stuInfoSet = {}; // 从后台获取的数据，存在于redis
	var sortedByAvgscore = []; // 根据平均分排序的ID列表
	var colName = [ "name", "birthday", "description", "avgscore" ]; // 列名
	var modalStatus = -1; // 目前modal的状态，-1 = nothing, 0 = insert, 1 = edit, 2 = delete, 
	var currentPage = 1; // 当前页码
	var row = 0; // 总行数
	var totalPage = 1; // 总页数

// 页面初始化
	GetAllInfo(); // 初始化表格并填充数据
	initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息

// 分页控制
	// 表格中行的显示
	// row = 总行数
	// pageNum = 页码
	function pagingControlForRow(pageNum){
		if(pageNum > totalPage) // 当前页码不能大于总页码数
			return;
		var start = (pageNum - 1) * 10 + 1; // 指定页第一条数据的data-index
		var end = (start + 10 > row ? row : (start + 10 - 1)); // 指定页最后一条数据的data-index，可能为最后一页
		$("tr[data-index]").hide(); 
		for(var i = start ; i <= end ; i ++){
			$("tr[data-index="+i+"]").show();
		}
		console.log("current page: " + currentPage);
	}

	// 添加分页按钮组
	function AddPageBtn(){
		$("#page-btn-area").empty(); // 清空分页按钮
		// 添加 上一页 按钮
		$("#page-btn-area").append("<li><button class=\"page btn btn-success\" value=\"previous\" "
				+"onclick=pagingControlForNumBtn(this)>&laquo;</button></li>");
		// 添加 所有页码（1，2，……） 按钮
		for(var i = 1; i <= totalPage; i ++){
			$("#page-btn-area").append("<li><button class=\"page btn btn-default\" value=" + i
					+" onclick=pagingControlForNumBtn(this)>" + i + "</button></li>");
		}
		// 添加 下一页 按钮
		$("#page-btn-area").append("<li><button class=\"page btn btn-success\" value=\"next\" "
				+"onclick=pagingControlForNumBtn(this)>&raquo;</button></li>");
		// 高亮标出 第一页 的页码按钮
		$(".page[value=1]").removeClass("btn-default");
		$(".page[value=1]").addClass("btn-warning");
	}
	
	// 页码按钮事件
	// item: 页码按钮元素
	function pagingControlForNumBtn(item){
		var pageNum = $(item).val();
		if(pageNum == "previous" && currentPage > 1){
			currentPage = currentPage - 1;
			pagingControlForRow(currentPage);
		}
		else if(pageNum == "next" && currentPage  < totalPage){
			currentPage = currentPage + 1;
			pagingControlForRow(currentPage);
		}
		else if((pageNum == "previous" && currentPage  == 1) 
				|| (pageNum == "next" && currentPage  == totalPage)){ // 边界判定
			return;
		}
		else{
			currentPage = parseInt(pageNum);
			pagingControlForRow(currentPage);
		}
		// 高亮显示 当前页 的页码按钮
		$(".page").removeClass("btn-warning");
		$(".page").addClass("btn-default");
		$(".page[value="+currentPage+"]").removeClass("btn-default");
		$(".page[value="+currentPage+"]").addClass("btn-warning");
	}
	
	// 执行分页操作
	// row = 总行数
	function Paging(){
		// 计算总页数
		if(row % 10 == 0){ // 若数据量的各位是0（即10，20，……）
			totalPage = (row - row % 10) / 10;
		}
		else{
			totalPage = (row - row % 10) / 10 + 1;
		}
		// 分页 重置当前页码
		currentPage = 1;
		// 分页 表格分页
		pagingControlForRow(currentPage);
		// 分页 增加分页按钮
		AddPageBtn();
	}

// 输入验证
	// 初始化验证信息
	function initValidateInfo(){
		$("#vali-id").hide();
		$.each(colName, function(index, item){
			$("#vali-" + item).hide();
		})
	}

	// 检查日期格式，好厉害的说
	function checkDateFormat(date){
		var valid = /((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/;
		return valid.test(date);
	}

	// 检查字符串长度，
	// str：待查字符串  min：最小长度  max：最大长度
	function checkStringFormat(str, min, max){
		if(str.length >= min && str.length <= max){
			return true;
		}
		else{
			return false;
		}
	}

	// 检查整数格式
	function checkIntFormat(str){
		var i = parseInt(str);
		if(str == i + ""){
			return true;
		}
		else{
			return false;
		}
	}
	
	// 验证输入的正确性
	function validate(){
		// 所有格式是否全部通过？默认全部通过
		var valiFlag = true;
		
		// 检查各个文本框格式
		if(!checkStringFormat($("#ops-name").val(), 1, 40)){
			$("#vali-name").show();
			valiFlag = false;
		}
		if(!checkDateFormat($("#ops-birthday").val())){
			$("#vali-birthday").show();
			valiFlag = false;
		}
		if(!checkStringFormat($("#ops-description").val(), 1, 255)){
			$("#vali-description").show();
			valiFlag = false;
		}
		if(!checkIntFormat($("#ops-avgscore").val())){
			$("#vali-avgscore").show();
			valiFlag = false;
		}
		return valiFlag;
	}
	
// 事件触发前的准备工作
	// 准备 修改数据 事件 
	function PrepareEditInfo(item){
		$("#ops-title").text("修改数据");
		$(".ops-text[id!=ops-id]").removeAttr("readonly"); // 设置文本框可编辑
		var editInfoInHtml = getInfoFromTable(item); // 页面上存在的数据
		initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息
		modalStatus = 1;  
		console.log("edit prepared");
	}

	// 准备 删除数据 事件 
	function PrepareDeleteInfo(item){
		$("#ops-title").text("删除数据");
		$(".ops-text").attr("readonly", "true"); // 设置文本框不可编辑
		var deleteInfo = getInfoFromTable(item); // 页面上存在的数据，主要是为了将页面数据放入modal中
		initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息
		modalStatus = 2;  
		console.log("delete prepared");
	}

	// 获取 表格 整行数据 ，并将数据放入modal
	// item：整行元素，不包括<tr></tr>
	function getInfoFromTable(item) {
		var thisInfoHtml = $(item).parent().parent().children(); // 获取该行所有表格
		var id = $(thisInfoHtml[0]).html(); // 获取该行ID
		var editInfoInDB = { id : stuInfoSet[id] }; // 后台中的数据
		var editInfoInHtml = {}; // 指定行的数据
		editInfoInHtml[id] = {};
		for (i = 0; i < 4; i++) { // 获取指定行的数据
			editInfoInHtml[id][colName[i]] = $(thisInfoHtml[i+1]).html();
			$("#ops-" + colName[i]).val(editInfoInHtml[id][colName[i]]);
		}
		$("#ops-id").val(id); // 获取ID
		return editInfoInHtml;
	}		
	
// 事件绑定
	// 绑定 取消 事件
	$("#ops-abort").on("click", function(){
		$(".ops-text[id!=ops-id]").removeAttr("readonly"); // 设置文本框可编辑
		$(".ops-text").val(""); // 清空文本框内容
		initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息
		modalStatus = -1;
	});

	// 绑定 添加数据 的事件
	$("#ops-insert").on("click", function(){
		$("#ops-title").text("添加数据");
		$(".ops-text[id!=ops-id]").removeAttr("readonly"); // 设置文本框可编辑
		initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息
		modalStatus = 0;
		console.log("insert prepared");
	});

	// 提交更改
	$("#ops-submit").on("click", function(){
		initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息
		if(!validate()) // 验证输入有效性
			return;
		
		var url = "", data = {};
		if(modalStatus == 0){ // insert
			url = "insert";
			for(i = 0; i < 4; i++){
				data[colName[i]] = $("#ops-"+colName[i]).val();
			}
		}
		else if(modalStatus == 1){ // edit
			url = "edit";
			for(i = 0; i < 4; i++){
				data[colName[i]] = $("#ops-"+colName[i]).val();
			}
			data.id = $("#ops-id").val();
		}
		else if(modalStatus == 2){ // delete
			url = "del";
			data.id = $("#ops-id").val();
		}
		console.log(url);
		console.log(data);
		$.ajax({
			url: url,
			type: "post",
			data: data,
			success: function(data){
				GetAllInfo();
				$("#ops-abort").click(); // 关闭对话框，偷个懒，使用取消事件
				modalStatus = -1; // 重置modal状态
				initValidateInfo(); // 初始化验证信息，即隐藏全部格式错误信息
			}
		});
	});
	
// 初始化表格
	// 获取所有信息
	function GetAllInfo() {
		$.ajax({
			url : "getAll",
			type : "get",
			success : function(data) {
				stuInfoSet = data;
				initTableHeader();
				// 填充表格内容
				var i = 0;
				$.each(data, function(key, value) {
					i++;
					AddInfoInPage(key, value.name, value.birthday,value.description, value.avgscore, false, i);
				});
				// 设置总数据量
				row = i;
				// 分页
				Paging();
			}
		})
	}

	// 获取以平均分排序的ID
	function SortByAvgscore(){
		$.ajax({
			url: "sort",
			type: "get",
			success: function(data){
				sortedByAvgscore = data;
				console.log(sortedByAvgscore);
				initTableHeader();
				// 填充表格内容
				var i = 0;
				$.each(data, function(index, value) {
					i++;
					AddInfoInPage(value, stuInfoSet[value].name, stuInfoSet[value].birthday,
							stuInfoSet[value].description, stuInfoSet[value].avgscore, false, i);
				});
				// 设置总数据量
				row = i;
				// 分页
				Paging();
			}
		});
	}
		
	// 设置表头
	function initTableHeader(){
		$("#info").empty();
		$("#info").append("<tr><th style=\"width: 14%;\">ID&nbsp;&nbsp;<button class=\"btn btn-info btn-xs\" "
			+ "onclick=GetAllInfo() disabled>sort</button></th><th style=\"width: 12%;\">姓名</th><th style=\"width: 16%;\">出生日期</th><th>描述</th>"
			+ "<th style=\"width: 14%;\">平均分&nbsp;&nbsp;<button class=\"btn btn-info btn-xs\" onclick=SortByAvgscore()>sort</button>"
			+ "</th><th style=\"width: 14%;\">操作</th></tr>");
	}
	
	// 填充表格
	// flag=true / false : 在表格开头 / 末尾添加元素
	function AddInfoInPage(id, name, birthday, description, avgscore, flag, dataIndex) {
		var innerHTML = "<tr class=\"info-row\" data-index=" + dataIndex + "><td>" + id + "</td><td>" + name
				+ "</td><td>" + birthday + "</td><td>" + description + "</td><td>" + avgscore + "</td>"
				+ "<td><button class=\"ops-edit btn btn-success btn-sm\"  data-toggle=\"modal\" "
				+ "data-target=\"#ops\" onclick=PrepareEditInfo(this)>编辑</button>&nbsp;"
				+ "<button class=\"ops-del btn btn-danger btn-sm\"  data-toggle=\"modal\" "
				+ "data-target=\"#ops\" onclick=PrepareDeleteInfo(this)>删除</button></td></tr>";
		if (flag)
			$("#info").prepend(innerHTML);
		else
			$("#info").append(innerHTML);
	}
//})
</script>
</html>
