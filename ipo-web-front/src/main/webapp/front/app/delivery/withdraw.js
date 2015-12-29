$(function() {
	  $('.pickup').change(function() {

	    var value = $(".pickup").find("option:selected").val();

	    if (value == 'customer') {
	      $('.customer').removeClass('hide');
	      $('.dispatching').addClass('hide');
	    }
	    if (value == 'dispatching') {
	      $('.customer').addClass('hide');
	      $('.dispatching').removeClass('hide');
	    }

	  });

	  var commodities = [{
	        "commodityname": '春树秋香图',
	        "commodityid": '201510010000',
	        "position": "50",
	        "warehouse": ["1号仓库", "长三角"]
	      },{
	        "commodityname": '奔马图',
	        "commodityid": '11010000',
	        "position": "100",
	        "warehouse": ["5号仓库", "东北亚", "长三角"]
	      },{
	        "commodityname": '墨竹戒指',
	        "commodityid": '666666',
	        "position": "2333",
	        "warehouse": ["老王家", "长三角"]
	      }];
	  $.ajax({
	  	type:"GET",
	    url:'<%=request.getContextPath()%>/SettlementDeliveryController/deliveryInfo?dealerId='+'<%=dealerId %>',
	    success: function(response) {
	    	console.log(commodities);
	    	commodities = eval(response);
	    	
			console.log(commodities);
	    },
	    error: function(response) {
	    	alert("出错咯");
	    }
	  });

	  //页面初始化
	  initial ();
	  //加载藏品名称数据
	  function initial () {

	    var selname = $('#nametext');
	    var selhouse = $('#housetext');
	    for(var i = 0; i < commodities.length; i++){
	      selname.append('<option logix =' + i + '>'+commodities[i].commodityname+'</option>');
	    }
	    $("#vcode").val(commodities[0].commodityid);
	    $("#vcount").val(commodities[0].position);
	    var warehouses = commodities[0].warehouse;
	    for(var i = 0; i < warehouses.length; i++){
	      selhouse.append('<option logix =' + i + '>'+warehouses[i]+'</option>');
	    }
	  }
	  //选择其他商品名称是变化
	  $('#nametext').change(function() {
	    var logic = $("#nametext").find("option:selected").attr('logix');

	    var selname = $('#nametext');
	    var selhouse = $('#housetext');
	    $("#vcode").val(commodities[logic].commodityid);
	    $("#vcount").val(commodities[logic].position);
	    var warehouses = commodities[logic].warehouse;
	    selhouse.empty()
	    for(var i = 0; i < warehouses.length; i++){
	      selhouse.append('<option logix =' + i + '>'+warehouses[i]+'</option>');
	    }
	  });
	})