var seckill={
		//验证手机格式
    	validataPhone:function(phone){
    		if(phone!=null&&phone.length==11&&!isNaN(phone))
    		{
    			return true;
    		}else
    		{
    			return false;
    		}
    	},
    	URL:{
    		now:function(){
    			
    			return "/seckill/time/now";
    		}
    		
    	},
		 //详情页秒杀逻辑
        detail:{
        	init:function(params){
        		  //手机验证和登录,计时交互
                //规划我们的交互流程
                //在cookie中查找手机号
        		var userPhone=$.cookie('userPhone');
        		//验证绑定的手机
        		if(!seckill.validataPhone(userPhone)){
        			  //绑定手机 控制输出
                    var killPhoneModal = $('#killPhoneModal');
                    killPhoneModal.modal({
                        show: true,//显示弹出层
                        backdrop: 'static',//禁止位置关闭
                        keyboard: false//关闭键盘事件
                    });
        		}
        			 $('#killPhoneBtn').click(function () {
                         var inputPhone = $('#killphoneKey').val();
                         console.log("inputPhone: " + inputPhone);
                         if (seckill.validataPhone(inputPhone)) {
                             //电话写入cookie(7天过期)
                             $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                             //验证通过　　刷新页面
                             window.location.reload();
                         } else {
                             //todo 错误文案信息抽取到前端字典里
                             $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                         }
                     });
        		var startTime=params.startTime;
        		var endTime=params.endTime;
        		 var seckillId = params.seckillId;
        		 $.get(seckill.URL.now(),{},function(result){
        			  if (result && result['success']) {
                          var nowTime = result['data'];
                          //时间判断 计时交互
                          seckill.countDown(seckillId, nowTime, startTime, endTime);
                      } else {
                          console.log('result: ' + result);
                          alert('result: ' + result);
                      }
        			 
        		 })
        		 
        		
        		
        	}
 	
        },
        handlerSeckill:function(seckillId, node){
        	 //获取秒杀地址,控制显示器,执行秒杀       	
            node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        	$.get('/seckill/'+seckillId+'/exposer',{},function(result){
        		if(result&&result['exposed']){
        			//开启秒杀
                    //获取秒杀地址
                    var md5 = result['md5'];
                    var killUrl='/seckill/' + seckillId + '/' + md5 + '/execution';
                    //绑定一次点击事件
                    $('#killBtn').one('click', function () {
                        //执行秒杀请求
                        //1.先禁用按钮
                    	   $(this).addClass('disabled');
                    	   $.post(killUrl, {}, function (result) {
                               if (result && result['exposed']) {
                                   var killResult = result['data'];
                                   var state = killResult['state'];
                                   var stateInfo = killResult['stateInfo'];
                                   //显示秒杀结果
                                   node.html('<span class="label label-success">' + stateInfo + '</span>');
                               }else {
                                   //未开启秒杀(浏览器计时偏差)
                                   var now = result['now'];
                                   var start = result['start'];
                                   var end = result['end'];
                                   seckill.countDown(seckillId, now, start, end);
                               }
                           });
                       });
                       node.show();
        		}
        		
        		
        	})
        	
        	
        },
    	countDown:function(seckillId, nowTime, startTime, endTime){
    		var seckillBox = $('#seckill-box');
    		if(nowTime>endTime){
                //秒杀结束
                seckillBox.html('秒杀结束!');

    		}else if(nowTime<startTime){
    			
                seckillBox.countdown(startTime, function (event) {
                    //时间格式
                	  var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
                    seckillBox.html(format);
                }).on('finish.countdown', function () {
                    //时间完成后回调事件
                    //获取秒杀地址,控制现实逻辑,执行秒杀
                  
                    seckill.handlerSeckill(seckillId,seckillBox);
                });
    		}else{
    			
    			seckill.handlerSeckill(seckillId,seckillBox);
    		}
    	}
}
