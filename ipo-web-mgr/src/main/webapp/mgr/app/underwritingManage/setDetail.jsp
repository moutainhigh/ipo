<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/jquery-easyui/themes/default/easyui.css"> 
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/jquery-easyui/themes/icon.css"> 
        <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/ecside/css/ecside_style.css" type="text/css" />
		<style type="text/css">input {line-height: 14px;}    td {padding-bottom: 3px;}</style>
        <script src="<%=request.getContextPath()%>/static/jquery/jquery-1.8.0.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/static/jquery-easyui/jquery.easyui.min.js"  type="text/javascript"></script>
		<title>承销设置</title>
		<style type="text/css">
			legend{font-weight:bold;}
		</style>
		<script type="text/javascript">
function add(){
	if($("#underwriterid").val()==''){
		alert("承销会员编码不可为空");
		return;
	}
	if($("#commodityid").val()==''){
		alert("请选择商品");
		return;
	}
	if($("#subscribecounts").numberbox("getValue")==''){
		alert("认购数量不可为空");
		return;
	}
	if($("#subscribeprice").numberbox("getValue")==''){
		alert("认购价格不可为空");
		return;
	}
	if($("#proportion").numberbox("getValue")==''){
		alert("占承销会员手续费总和的比例不可为空");
		return;
	}
	var flag= $('#frm').form('validate');
	if(flag==true){
		var proportion=parseFloat($("#proportion").numberbox("getValue"));
		var maxproportion=parseFloat($("#maxproportion").val());
		if(proportion>maxproportion){
			alert("超出剩余可分配比例");
			return ;
		}
            	 $.ajax({  
        			 type: 'POST', 
        		      url: "<%=request.getContextPath()%>/UnderwriterSetController/findUnderwriter",  
        		     data:{"underwriterid":$("#underwriterid").val()},  
        		     success : function(data, stats) { 
        			           if(data=='0'){
        			        	   alert("承销会员代码不存在！");
        			        	   clearInfo();
        			        	   return;
        			           }
                               if(data=='1'){
                            	   $.ajax({ 
                            		   cache:false,
                                       type: "post",  
                                       url: "<%=request.getContextPath()%>/UnderwriterSetController/addSet",       
                                       data: $("#frm").serialize(),      
                                       success: function(data) { 
                                    	   if(data=='existed'){
                                    		   alert("承销会员已认购过此商品，请重新填写");
                                    		  // clearInfo();
                                    		   return;
                                    	   }
                                    	   if(data=='full'){
                                    		   alert("此商品供承销认购数量已被认购完，请重新填写");
                                    		 //  clearInfo();
                                    		   return;
                                    	   }
                                    	   if(data=='fundshort'){
                                    		   alert("资金不足，请重新填写，或增加暂扣押金");
                                    		  // clearInfo();
                                    		   return;
                                    	   }
                                    	   if(data=='true'){
                                           alert("添加成功！"); 
                                           returntoList();
                                    	   } 
                                    	   if(data=='false'){
                                    		   alert("系统异常，请联系管理员");  
                                    	   }
                                       },  
                                       error: function(data) {  
                                           alert("系统异常，请联系管理员！");  
                                       }  
                                   }) ;
        			           }
        			        }    
        				});
            }
			else{
					alert("所有参数必填！");
		}
}
	function clearInfo(){
	       $("#underwriterid").val("");
		   $("#subscribecounts").numberbox("setValue","");
		   $("#proportion").numberbox("setValue","");
		   $("#subscribeprice").numberbox("setValue","");
		   $("#commodityid").val("");
	}	

function returntoList(){
	var backUrl="<%=request.getContextPath()%>/mgr/app/underwritingManage/underwritingSet.jsp";
	document.location.href = backUrl;
}

function onlyNumberInput(){
	 if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47){
		    event.returnValue=false;
	 }
}

function setSortName(value) {
	var commList =<%=request.getAttribute("commlist") %>; 
	 for(var o in commList){  
	        if (value == commList[o].commodityid ) {
				$("#subscribeprice").numberbox("setValue",commList[o].price);
				break;
		}
	 } 
	 $.ajax({ 
         type: "post",  
         url: "<%=request.getContextPath()%>/UnderwriterSetController/checkRatioSum",       
         data: {"commodityId":value},      
         success: function(data) { 
        	 $("#proportion").numberbox("setValue",data);
        	 $("#maxproportion").val(data);
         },  
         error: function(data) {  
             alert("系统异常，请联系管理员！");  
         }  
     }) ;
}
			
</script>
</head>
<body leftmargin="14" topmargin="0">
<div class="warning">
		<div class="title font_orange_14b">温馨提示 :  承销设置</div>
		<div class="content" style="color: red"> 添加承销商认购的商品、数量及费用信息。</div>
	</div>
	<form method="POST" action="" name="frm" id="frm">
        <table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
              <fieldset class="pickList" >
	                 <legend class="common"><b>添加认购商品</b></legend>
		<table border="0" align="center" cellpadding="5" cellspacing="5" class="common" width="100%">
			<tr>
	           	<td align="center" colspan="2" style="color:red"></td>
	        </tr>  
	        <tr>
	        	<td align="right" style="font-size:15px" width="50%">承销会员编号：</td>
	            <td align="left" width="60%">
	                   <input style="width:150px;" id="underwriterid" name="underwriterid"/>
	                   <span class="required">*</span>  
	            </td>
	        </tr>   
	        <tr>
	        	<td style="font-size:15px" align="right" width="20%">商品代码：</td>
	        	<td align="left" width="60%">
	        	<select id="commodityid" name="commodityid" style="width:150px" onchange="setSortName(this.value)">
						<option value="">请选择</option>
                         <c:forEach var="commodity" items="${commList}">
                         <option value="${commodity.commodityid}">${commodity.commodityid}${commodity.commodityname}</option>
                          </c:forEach>
				</select>
				<span class="required">*</span>
	        	</td>
	        </tr>  
	        <tr>
	        	<td style="font-size:15px" align="right" width="20%">认购价格：</td>
	        	<td align="left" width="60%">
	        	 <input style="width:150px;" id="subscribeprice" name="subscribeprice"  
	        	  class="easyui-numberbox" data-options="required:true,missingMessage:'请填入正数',min:0,precision:2,disabled:true"/>
	                   <span class="required">*</span>
	        	</td>
	        </tr> 
	         <tr>
	        	<td style="font-size:15px" align="right" width="20%">认购数量(批)：</td>
	        	<td align="left" width="60%">
	        	 <input style="width:150px;" id="subscribecounts" name="subscribecounts"
	        	  class="easyui-numberbox" data-options="required:true,missingMessage:'请填入正数',min:0"/>
	        	  <span class="required">*</span>
	        	</td>
	        </tr> 
	        <tr>
	        	<td style="font-size:15px" align="right" width="20%">占承销会员手续费总和的比例(%)：</td>
	        	<td align="left" width="60%">
	        	 <input style="width:150px;" id="proportion" name="proportion" 
	        	 class="easyui-numberbox" data-options="required:true,missingMessage:'请填入正数',min:0,max:100,precision:1"/>
	                   <span class="required">*</span>
	                   <input type="hidden" id="maxproportion"/>
	        	</td>
	        </tr>  
		  	<tr>
				<td align="right">
					<input type="button" value="添加" onclick="add()"/>
		    	</td>
		    	<td align="left"><input type="button" value="返回" onclick="returntoList()"/></td>
		 	</tr>
	    </table>
	</fieldset>
	</td>
	</tr>
</table>
</form>
</body>
</html>
