/*分成参数设置*/
define(function(require) {
	require.async(['jquery','icheck','ue'], function() {
			var edit=UE.getEditor('editor');
			var common=require('common');
			var $common=new common();
			var alert=require('alertUtils')
			var $alert=new alert();
			var table=require('tableUtils');
			var t=new table();
			var columns=[{
	            field: 'title', title: '标题',  align: 'center',
	        },{
	            field: 'addTime', title: '发布时间',  align: 'center',
	        },{
	            field: 'type', title: '类型',  align: 'center',
	            formatter:function(value, row, index){
                	if (!$common._noEmpty(value)) {
                		return "-";
					}else{
						if (value=="user") {
							return "<span class='badge badge-info'>客户端问题</span>";
						}else{
							return "<span class='badge badge-success'>技师端问题</span>";
						}
						
					}
                }
	        },{
	            field: 'sort', title: '排序',  align: 'center',
	        },{
	            field: 'operate',
	            title: '操作',
	            align: 'center',
	            formatter: function(value, row, index) {
	            	  return [
	  	                    '<a class="btn btn-outline btn-success js-update">修改</a>',  
	  	                    '<a class="btn btn-outline btn-warning js-info">详情</a>',
	  	                    '<a class="btn btn-outline btn-danger js-delete">删除</a>',
	  	                ].join('');
	            },
	            events: {
			        'click .js-update': function (e, value, row, index) {
			        	  $("#updateNews").removeClass('hide');
						  $("#addNews").addClass('hide');
						  $("#updateId").val(row.uuid);
						  $("#title").val(row.title);
						  $("#sort").val(row.sort);
						  $("input[name='online'][value='"+row.type+"']").iCheck('check');;
						  edit.setContent(row.content);
						  $("#myModal").modal({
							  backdrop:'static'
						  });
			        },
			        'click .js-info': function (e, value, row, index) {
			        	 $("#checkworkBox").removeClass('hide');
						 $("#mainInfo").html(row.content);
			        },
			        'click .js-delete': function (e, value, row, index) {
			        	$alert._warning("删除操作","确定删除该常见问题",function(){
			        		$.ajax({
								url:"/huatuo-other/question/deletenews",
								contentType: "application/x-www-form-urlencoded; charset=utf-8", 
								type: "post",
								dataType:"json",
								data:{
									uuid:row.uuid,
								},
								async:true,success:function(data){
									$alert._strSuc("删除成功");
	    							$t._refresh();
								},error:function(){
									$alert._alert("操作失败");
								}
							});
			        		
			        	});
			        }
			    },
	        }];
			var $t = t._init("table","/huatuo-other/question/list",columns);
			/*添加*/
			$(document).on("click",".js-add",function(e){
				  $('.form-control').val('');
				  $("#sort").val("99");
				  $("#addNews").removeClass('hide');
				  $("#updateNews").addClass('hide');
				  $("#myModal").modal({
					  backdrop:'static'
				  });
			  });
			$(document).on("click","#addNews",function(e){
				if (!$common._numBigZero($("#sort").val())) {
					$alert._alert("排序值不能小于0！");
					return;
				}
				  $.ajax({
						url:"/huatuo-other/question/addnews",
						contentType: "application/x-www-form-urlencoded; charset=utf-8", 
						type: "post",
						data:{
							title:$("#title").val(),
							sort:$("#sort").val(),
							content:UE.getEditor('editor').getContent(),
							type:$("input[name='online'][checked]").val()
						},
						dataType:"text",
						async:true,success:function(data){
							if(data!="-1"){
								$alert._alert("常见问题添加成功！");
							}else{
								$alert._alert("网络延迟，请刷新界面后稍后重试！");
							}
							$("#myModal").modal('hide');
							$t._refresh();
						}
					  }); 
			  });
			  $(document).on("click","#updateNews",function(e){
				  if (!$common._numBigZero($("#sort").val())) {
						$alert._alert("排序值不能小于0！");
						return;
					}
				  $.ajax({
						url:"/huatuo-other/question/update",
						contentType: "application/x-www-form-urlencoded; charset=utf-8", 
						type: "post",
						data:{
							uuid:$("#updateId").val(),
							title:$("#title").val(),
							sort:$("#sort").val(),
							type:$("input[name='online'][checked]").val(),
							content:UE.getEditor('editor').getContent(),
						},
						dataType:"text",
						async:true,success:function(data){
							if(data!="-1"){
								$alert._alert("常见问题修改成功！");
							}else{
								$alert._alert("网络延迟，请刷新界面后稍后重试！");
							}
							$("#myModal").modal('hide');
							$t._refresh();
						}
					  }); 
			  });
			
			 $('input.radio').iCheck({
				    checkboxClass: 'icheckbox_square-green',
				    radioClass: 'iradio_square-green',
				    increaseArea: '20%' // optional
			 });
	});
});