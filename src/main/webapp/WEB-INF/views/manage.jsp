<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据管理</title>
</head>
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<body>
<div class="container">
	<h2 class="col-md-12">需要身份验证</h2>
	<div class="form-group">
		<div class="form-horizontal">
    		<label for="pw" class="col-sm-1 control-label">密码</label>
    		<div class="col-sm-3">
      			<input type="password" class="form-control" id="pw" placeholder="请输入密码">
    		</div>
			<button class="col-sm-2 btn btn-success" id="generate">猛戳生成随机数据</button>
			<button class="col-sm-2 btn btn-ning" id="delete">猛戳删除最后的数据</button>
		</div>
	</div>
</div>
</body>
<script>
$(function(){
	$("#generate").on("click", function(){
		var password = $("#pw").val();
		$.ajax({
			url: "manager/generate",
			type: "post",
			data: {
				pw: password
			},
			success: function(data){
				if(data == "failed")
					alert("验证失败");
				else
					alert("成功插入" + data + "条数据！");
			}
		})
	});
	$("#delete").on("click", function(){
		var password = $("#pw").val();
		$.ajax({
			url: "manager/delete",
			type: "post",
			data: {
				pw: password
			},
			success: function(data){
				if(data == "failed")
					alert("验证失败");
				else
					alert("成功删除" + data + "条数据！");
			}
		})
	});
})
</script>
</html>