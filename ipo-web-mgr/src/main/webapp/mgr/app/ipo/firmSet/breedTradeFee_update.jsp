<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
    <title>�޸�����Ʒ�ֽ���������</title>
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	  <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	  <script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	  <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
	  <script type="text/javascript">
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			// �޸İ�ťע�����¼�
			$("#update").click(function(){
				// ��֤��Ϣ
				if(jQuery("#frm").validationEngine('validate')){
					
					var vaild = affirm("��ȷ��Ҫ������");
					if(vaild){
						// �޸���ϢURL
						var addUrl = $(this).attr("action");
						// ȫ URL ·��
						var url = "${basePath}"+addUrl;
						$("#frm").attr("action",url);
						$("#frm").submit();
					}
				}
			});

			
		});
	  </script>
	
    <script type="text/javascript">
      // ����
      function goback(){
    	  document.location.href = "<c:url value="${basePath}/timebargain/firmSet/commoditySpecial/listTradeFee.action"/>";
      }

      //���������ֺ�.
	  function onlyDoubleInput()
	  {
	    if (event.keyCode<46 || event.keyCode>57 || event.keyCode == 47)
	    {
	      event.returnValue=false;
	    }
	  } 

        function on_change(){
    		document.getElementById("forceCloseFeeAlgr").value = document.getElementById("feeAlgr").value;
    		
    		if ($("#feeAlgr").val() == "1") {
    		
    			$("#feeRate_BPercent").show();
    			$("#feeRate_SPercent").show();
    			$("#historyCloseFeeRate_BPercent").show();
    			$("#historyCloseFeeRate_SPercent").show();
    			$("#todayCloseFeeRate_BPercent").show();
    			$("#todayCloseFeeRate_SPercent").show();
    			$("#forceCloseFeeRate_BPercent").show();
    			$("#forceCloseFeeRate_SPercent").show();
    		}else {
    			$("#feeRate_BPercent").hide();
    			$("#feeRate_SPercent").hide();
    			$("#historyCloseFeeRate_BPercent").hide();
    			$("#historyCloseFeeRate_SPercent").hide();
    			$("#todayCloseFeeRate_BPercent").hide();
    			$("#todayCloseFeeRate_SPercent").hide();
    			$("#forceCloseFeeRate_BPercent").hide();
    			$("#forceCloseFeeRate_SPercent").hide();

    		}
    	}

        function changeb(value){
    		 var todayCloseFeeRate_B = document.getElementById("todayCloseFeeRate_B");
    		 var feeRate_B = document.getElementById("feeRate_B");
    		 var historyCloseFeeRate_B = document.getElementById("historyCloseFeeRate_B");
    	    if(todayCloseFeeRate_B.value==""){
    	        todayCloseFeeRate_B.value = feeRate_B.value;
    	        historyCloseFeeRate_B.value = feeRate_B.value;
    	    }
    	 }
    	 function changes(value){
    		 var todayCloseFeeRate_S = document.getElementById("todayCloseFeeRate_S");
    		 var feeRate_S = document.getElementById("feeRate_S");
    		 var historyCloseFeeRate_S = document.getElementById("historyCloseFeeRate_S");
    	    if(todayCloseFeeRate_S.value==""){
    	        todayCloseFeeRate_S.value = feeRate_S.value;
    	        historyCloseFeeRate_S.value = feeRate_S.value;
    	    }   
        }
    
    	 function window_load(){
    	   	   
    		 on_change();
    	 }
    </script>
  </head>
  <body onload="window_load()">
	<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden" >

	  <div class="div_cx">
		<table border="0" width="100%" align="center">
		  <tr>
		    <td>
			  <div class="warning">
			    <div class="content">
			               ��ܰ��ʾ :�޸����⽻��������
				</div>
			  </div>
			</td>
		  </tr>
		  <tr>
		    <td>
			  <table border="0" width="100%" align="center">
			   
			    <tr>
				  <td>
				    <div class="div_cxtj">
					  <div class="div_cxtjL"></div>
					  <div class="div_cxtjC">
						������Ϣ
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
					  <table border="0" cellspacing="0" cellpadding="10" align="center" class="table2_style">
					    <tr>
		          		  <td align="right">�����̴��룺</td>   
						  <td>
							 ${entity.firmID}
                		    <input type="hidden" id="firmID" name="entity.firmID" value="${entity.firmID}" />
						  </td>
						  
            			  <td align="right">Ʒ�ִ��룺</td>   		   
            			  <td>
							 ${entity.breedID}
                		    <input type="hidden" id="breedID" name="entity.breedID" value="${entity.breedID}" />
						  </td>
							           
						</tr>
					  </table>
				   	</div>
				  </td>
			    </tr>
				
				<tr>  
				  <td>
				  
				    <div class="div_cxtj">
					  <div class="div_cxtjL"></div>
					  <div class="div_cxtjC">
						������
					  </div>
					  <div class="div_cxtjR"></div>
					</div>
					<div style="clear: both;"></div>
				    <div>
			      
					  <table border="0" cellspacing="0" cellpadding="10"  class="table2_style" align="center">
					    <tr>
			              <td>
			              
                            <table cellSpacing="0" cellPadding="5"  border="0" align="center" class="common"> 
								<tr > 		            
		            			  <td colspan="2">			
		            				<span >�����������㷨��
		            				  <select id="feeAlgr" name="entity.feeAlgr" onchange="on_change()">
									    <option value="">��ѡ��</option>
					                    <c:forEach items="${algrMap}" var="map" >
									      <option value="${map.key}">${map.value}</option>
									    </c:forEach>
			   						  </select> 
			   						  <input type="hidden" id="forceCloseFeeAlgr" name="entity.forceCloseFeeAlgr" />
			   						  <span class="required">*</span>  
									</span>
									<script type="text/javascript">
											  document.getElementById("feeAlgr").value = ${entity.feeAlgr};
									</script>		
								  </td>	
								</tr>  
								<tr>          		            
		            			  <td align="right" >������
		            			    <input id="feeRate_B" name="entity.feeRate_B" value="${entity.feeRate_B }"
		            					   onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text" onchange="changeb(this.value)" />
			  						<span id="feeRate_BPercent">%</span><span class="required">*</span>
			  					  </td>
			  					  <td align="right" >��������
			  					     <input  id="feeRate_S" name="entity.feeRate_S" value="${entity.feeRate_S }"
			  							    onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text" onchange="changes(this.value)" />
			  						 <span id="feeRate_SPercent">%</span><span class="required">*</span>  
			  					  </td>
		    					</tr>	
		    					<tr>          		            
		            			  <td align="right" >��ת����ʷ������
		            			    <input id="historyCloseFeeRate_B" name="entity.historyCloseFeeRate_B"  value="${entity.historyCloseFeeRate_B }"
		            					   onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						<span id="historyCloseFeeRate_BPercent">%</span><span class="required">*</span>
			  					  </td>
			  					  <td align="right" >��ת����ʷ������
			  					     <input id="historyCloseFeeRate_S" name="entity.historyCloseFeeRate_S" value="${entity.historyCloseFeeRate_S }" 
			  							    onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						 <span id="historyCloseFeeRate_SPercent">%</span><span class="required">*</span>  
			  					  </td>
		    					</tr>	
		    					<tr>          		            
		            			  <td align="right" >��ת�ý񶩻���
		            			    <input id="todayCloseFeeRate_B" name="entity.todayCloseFeeRate_B" value="${entity.todayCloseFeeRate_B }" 
		            					   onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						<span id="todayCloseFeeRate_BPercent">%</span><span class="required">*</span>
			  					  </td>
			  					  <td align="right" >��ת�ý񶩻���
			  					     <input id="todayCloseFeeRate_S" name="entity.todayCloseFeeRate_S" value="${entity.todayCloseFeeRate_S }" 
			  							    onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						 <span id="todayCloseFeeRate_SPercent">%</span><span class="required">*</span>  
			  					  </td>
		    					</tr>	
		    					<tr>          		            
		            			  <td align="right" >��ǿ��ת�ã�
		            			    <input id="forceCloseFeeRate_B" name="entity.forceCloseFeeRate_B" value="${entity.forceCloseFeeRate_B }" 
		            					   onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						<span id="forceCloseFeeRate_BPercent">%</span><span class="required">*</span>
			  					  </td>
			  					  <td align="right" >��ǿ��ת�ã�
			  					     <input id="forceCloseFeeRate_S" name="entity.forceCloseFeeRate_S"  value="${entity.forceCloseFeeRate_S }" 
			  							    onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[15],custom[onlyDoubleSp]] input_text"  />
			  						 <span id="forceCloseFeeRate_SPercent">%</span><span class="required">*</span>  
			  					  </td>
		    					</tr>	
		    											
	                       </table>  
					
				         </td>
				       </tr>
			         </table>
			       </div> 
			       
			     </td>
		       </tr>
		</table>
	  </div>
	  <div class="tab_pad">
	    <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr>
		    <td align="center">
			  <rightButton:rightButton name="�޸�" onclick="" className="btn_sec" action="/timebargain/firmSet/breedSpecial/updateTradeFee.action" id="update"></rightButton:rightButton>
			  &nbsp;&nbsp;
			  <button class="btn_sec" onClick="window.close();">�ر�</button>
		    </td>
		  </tr>
	    </table>
	  </div>
    </form>
  </body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>