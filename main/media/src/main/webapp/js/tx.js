/**
 * 
 */

function on_wx_jsapi_init(wx,
		is_debug,
		appid,
		timestamp,
		nonce,
		signature,
		imgUrl,
		lineLink,
		descContent,
		shareTitle,
		shareTitle2){

	wx.config({
		debug:is_debug,
		appId:appid,
		timestamp:timestamp,
		nonceStr:nonce,
		signature:signature,
		jsApiList: [
	        'checkJsApi',
	        'onMenuShareTimeline',
	        'onMenuShareAppMessage',
	        'onMenuShareQQ',
	        'onMenuShareWeibo',
	        'hideMenuItems',
	        'showMenuItems',
	        'hideAllNonBaseMenuItem',
	        'showAllNonBaseMenuItem',
	        'translateVoice',
	        'startRecord',
	        'stopRecord',
	        'onRecordEnd',
	        'playVoice',
	        'pauseVoice',
	        'stopVoice',
	        'uploadVoice',
	        'downloadVoice',
	        'chooseImage',
	        'previewImage',
	        'uploadImage',
	        'downloadImage',
	        'getNetworkType',
	        'openLocation',
	        'getLocation',
	        'hideOptionMenu',
	        'showOptionMenu',
	        'closeWindow',
	        'scanQRCode',
	        'chooseWXPay',
	        'openProductSpecificView',
	        'addCard',
	        'chooseCard',
	        'openCard'
	      ]
	});
	
	wx.ready(function(){
		var shareData = {
			title: shareTitle,
			desc: descContent,
			link: lineLink,
			imgUrl: imgUrl,
		    trigger: function (res) {
		    },
		    success: function (res) {
		      //alert('已分享:' + shareTitle);
		    },
		    cancel: function (res) {
		      //alert('已取消');
		    },
		    fail: function (res) {
		      alert(JSON.stringify(res));
		    }
		  };
  
		wx.onMenuShareAppMessage(shareData);
		wx.onMenuShareTimeline(shareData);
		wx.onMenuShareQQ(shareData);
		wx.onMenuShareWeibo(shareData);
	});
/*	wx.ready(function(){
		wx.onMenuShareAppMessage({
		      title: shareTitle,
		      desc: descContent,
		      link: lineLink,
		      imgUrl: imgUrl,
		      trigger: function (res) {
		      },
		      success: function (res) {
		        alert('已分享:' + shareTitle);
		      },
		      cancel: function (res) {
		        alert('已取消');
		      },
		      fail: function (res) {
		        alert(JSON.stringify(res));
		      }
		    });
		wx.onMenuShareTimeline({
		      title: shareTitle2,
		      link: lineLink,
		      imgUrl: imgUrl,
		      trigger: function (res) {
		        // 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
		        //alert('用户点击分享到朋友圈');
		      },
		      success: function (res) {
		        alert('已分享' + shareTitle);
		      },
		      cancel: function (res) {
		        //alert('已取消');
		      },
		      fail: function (res) {
		    	  alert(JSON.stringify(res));
		      }
		    });

		wx.onMenuShareQQ({
		    title: shareTitle, // 分享标题
		    desc: descContent, // 分享描述
		    link: lineLink, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    	alert('已分享' + shareTitle);
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    },
		    fail: function (res) {
		    	alert(JSON.stringify(res));
	        }
		});
	});
	wx.error(function(res){
		alert(res.errMsg);
	});
*/

}