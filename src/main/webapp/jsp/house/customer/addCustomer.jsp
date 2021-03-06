<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.xmbl.ops.enumeration.EnumOfficeTag" %>
<%@ page import="com.xmbl.ops.enumeration.EnumHouseType" %>
<%@ page import="com.xmbl.ops.enumeration.EnumSourceType" %>
<%@ page import="com.xmbl.ops.enumeration.EnumIsKey" %>
<%@ page import="com.xmbl.ops.enumeration.EnumTradeType" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/tlds/Functions" prefix="func"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html lang="zh-CN">
	<%@ include file = "../../inc/version.jsp" %>
	<head>
		<meta charset="utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
		<title>新增客户</title>
		<meta http-equiv="keywords" content=""/>
		<meta http-equiv="description" content=""/>
		<link type="image/x-icon" rel="shortcut icon" href="<%=basePath %>image/logo/favicon.ico">
		<link rel="stylesheet" href="<%=basePath %>css/bootstrap/bootstrap.3.3.5.min.css">
		<link href="<%=basePath %>css/bootstrap/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="<%=basePath %>css/common_${ CSS_COMMON_VERSION }.css">
		<style type="text/css">
			.illustration {
				width: 200px;
				height: 100px;
				background: #999;
			}
			.illustration[src] {
				height: auto;
			}
		</style>
		<script type="text/javascript">
			var path = '<%=path %>';
			var basePath = '<%=basePath%>';
			(function(){MX=window.MX||{};var g=function(a,c){for(var b in c)a.setAttribute(b,c[b])};MX.load=function(a){var c=a.js,b=c?".js":".css",d=-1==location.search.indexOf("jsDebug"),e=a.js||a.css;-1==e.indexOf("http://")?(e=(a.path||window.basePath)+((c?"js/":"css/")+e)+(!d&&!c?".source":""),b=e+(a.version?"_"+a.version:"")+b):b=e;d||(d=b.split("#"),b=d[0],b=b+(-1!=b.indexOf("?")?"&":"?")+"r="+Math.random(),d[1]&&(b=b+"#"+d[1]));if(c){var c=b,h=a.success,f=document.createElement("script");f.onload=function(){h&&h();f=null};g(f,{type:"text/javascript",src:c,async:"true"});document.getElementsByTagName("head")[0].appendChild(f)}else{var c=b,i=a.success,a=document.createElement("link");g(a,{rel:"stylesheet"});document.getElementsByTagName("head")[0].appendChild(a);g(a,{href:c});i&&(a.onload=function(){i()});}}})();
		</script>
	</head>
	<body>
		<header class="ui-page-header">
			<div class="mini-title">当前位置：新增客户</div>
		</header>
		<article class="container-fluid" id="post-container">
		<div class="form-horizontal">
		      <div class="form-group">
				<div class="col-sm-8">
				</div>
			   </div>
			   </br>
			   <div class="form-group">
				<div class="col-sm-8">
				</div>
			   </div>
			   </br>
			<div class="form-group">
				<label class="col-sm-1 control-label">姓名:</label>
				<div class="col-sm-2">
					<input type="text" value="" class="usename form-control"
						maxLength="30" />
				</div>
				<label class="col-sm-1 control-label">电话：</label>
				<div class="col-sm-2">
					<input type="text" value="" class="mobile form-control"
						maxLength="30" />
				</div>
				</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">昵称：</label>
				<div class="col-sm-2">
					<input type="text" value="" class="nickname form-control"
						maxLength="30" />
				</div>
				<label class="col-sm-1 control-label">手机：</label>
				<div class="col-sm-2">
					<input type="text" value="" class="phone form-control"
						maxLength="30" />
				</div>
				</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">性别：</label>
				<div class="col-sm-1">
					<input type="text" value="" class="gender form-control"
						maxLength="30" />
				</div>
				<label class="col-sm-1 control-label">QQ：</label>
				<div class="col-sm-2">
					<input type="text" value="" class="qq form-control" maxLength="30" />
				</div>
				</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">微信：</label>
				<div class="col-sm-2">
					<input type="text" value="" class="wechat form-control"
						maxLength="30" />
				</div>
				<label class="col-sm-1 control-label">email：</label>
				<div class="col-sm-2">
					<input type="text" value="" class="email form-control"
						maxLength="30" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">来源：</label>
				<div class="col-sm-1">
				<select class="source form-control add-content" name="source">
			    <c:set var="enumSoureTypes" value="<%=EnumSourceType.values()%>"/>
						<c:forEach var="enumSoureType" items="${ enumSoureTypes }">
						<option value="${ enumSoureType.id }" <c:if test="${ enumSoureType.id == soure }">selected = "selected"</c:if>>${ enumSoureType.desc }</option>
						</c:forEach>
			    </select></label>
				</div>
				<label class="col-sm-1 control-label">状态：</label>
				<div class="col-sm-1">
					<select class="form-control status">
						<option value="0">有效客户</option>
						<option value="1">潜在客户</option>
						<option value="2">已签约</option>
						<option value="3">失效用户</option>
						<option value="4">锁定用户</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">地址：</label>
				<div class="col-sm-4">
					<textarea class="address form-control" rows="4"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1 control-label">备注说明</label>
				<div class="col-sm-4">
					<textarea class="remarks form-control" rows="4"></textarea>
				</div>
			</div>

			<!-- <div class="form-group">
				<label class="col-sm-1 control-label">物业公司:</label>
				<div class="col-sm-2">
					<input type="text" value="" class="propertycompany form-control"
						maxLength="30" />
				</div>
				<label class="col-sm-1 control-label">物业电话:</label>
				<div class="col-sm-2">
					<input type="text" value="" class="propertphone form-control"
						maxLength="30" />
				</div>
			</div> -->
	</article>
		<footer class="text-center ui-page-footer">
			<a class="btn btn-default min-w100" href="${referer }">返回</a>
			<button class="btn btn-primary min-w100 ml20" id="confirm-btn">提交</button>
		</footer>
		<div id="image-input" tabindex="-1" role="dialog" aria-hidden="true" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button data-dismiss="modal" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body"></div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			MX.load({
				js: 'lib/sea',
				version: '${ JS_LIB_SEA_VERSION }',
				success: function() {
				seajs.use(['lib/jquery','util/bootstrap.datetimepicker.zh-CN','module/Dialog', 'module/Validator', 'util/ajaxPromise'], function($,datetimepicker, Dialog, Validator, ajaxPromise) {
	            var allCheck = $('#all-check'),
	            dialog = new Dialog('modal-dialog'),
	            gameserverList = $('#gameserver-list'),
	            checkList =  gameserverList.find('input').filter('[name="game-server"]');
				// 绑定datetimepicker插件
				$('.form-date').datetimepicker({
					language: 'zh-CN',/*加载日历语言包，可自定义*/
					weekStart: 1,
					todayBtn:  1,
					autoclose: 1,
					todayHighlight: 1,
					minView: 0,
					forceParse: 0,
					showMeridian: 1,
					showSecond: true,  
                    timeFormat: 'hh:mm:ss',  
                    stepHour: 1,  
                    stepMinute: 1,  
                    stepSecond: 1  
				});	
				var postContainer = $('#post-container');
				// 绑定全选
				allCheck.on('click', function(e) {
				
					var el = $(this);
					if(el.prop('checked')) {
						checkList.prop('checked', true);
					} else {
						checkList.prop('checked', false);
					}
				});
				$('#confirm-btn').on('click', function(e) {
				var el = $(this), validator = new Validator(), data = {};
						data.usename = postContainer.find('.usename').val();
						data.mobile = postContainer.find('.mobile').val();
						 data.phone = postContainer.find('.phone').val();
						 data.gender = postContainer.find('.gender').val();
						 data.qq = postContainer.find('.qq').val();
						 data.wechat = postContainer.find('.wechat').val();
						 data.email = postContainer.find('.email').val();
						 data.source = postContainer.find('.source').val();
						 data.status = postContainer.find('.status').val();
						 data.address = postContainer.find('.address').val().trim();
						 data.remarks = postContainer.find('.remarks').val().trim();
						 data.nickname = postContainer.find('.nickname').val().trim();
						 validator.add(data.usename, 'isNotEmpty', function() {
								alert('姓名不能为空');
							});
						 validator.add(data.mobile, 'isNotEmpty', function() {
								alert('电话不能为空');
							});
							if(!data.Id) {
								
							}
							if(!validator.start()) {
								return;
							}
							el.prop('disabled', true);
							ajaxPromise({
								url: window.basePath + 'customer/addCustomer',
								type: 'POST',
								data: data,
								dataType: 'json'
							}).then(function(data) {
								document.location.href = window.basePath + 'customer/customerList';
							}, function() {
								el.prop('disabled', false);
							});
						});
					});
				}
			});
		</script>
	</body>
</html>
