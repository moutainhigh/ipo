<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../ipoInclude.jsp"%>
<html>
<head>
<title>结算</title>
<script src="<%=request.getContextPath()%>/static/ipo/tradeSettle.js" type="text/javascript"></script>
</head>
<body onload="getStatus();">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="middle">
				<button name="手工结算" onclick="addF();" className="anniu_btn" id="add" style="width: 120px; color: #fff; background-color: #337ab7; padding: 8px 12px; margin-bottom: 0; border: 1px solid transparent;"></button>&nbsp;&nbsp;<font style="font-size: 13px;" id="balanceStatus">结算状态：未执行</font>
			</td>
		</tr>
	</table>
</body>
</html>