<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>童言同语</title>
    <link rel="stylesheet" type="text/css" href="../css/media.css" />
    <script type="text/javascript" src="../js/swipe.js"></script>
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script type="text/javascript" src="../js/tx.js"></script>
    <meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, initial-scale=1, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

    <style type="text/css">

        *{margin:0;padding:0;}

        ul,li{
            list-style: none;
        }

        a,img,ul,li,div{
            border: none;
        }

		html{
		    width: 100%;
            height: 100%;
		}

        body{
            width: 100%;
            height: 100%;
            overflow:hidden
        }

		#load{
            width: 100%;
            height: 100%;
			background: rgba(255,255,255,1);
			position:relative;
			z-index:100;
        }

        #loading{
            position: absolute;
            width: 16em;
            height: 8em;
            top: 40%;
            left: 50%;
            margin-top: -8em;
            margin-left: -8em;
        }

        #load_img{
            width: 100%;
        }

        #load_rotate{
            width: 8em;
            height: 8em;
            margin-left: 4em;
            -webkit-animation-name: loadRotate;
            -webkit-animation-timing-function: linear;
            -webkit-animation-iteration-count: infinite;
            -webkit-animation-duration: 1000ms;
        }

        @-webkit-keyframes loadRotate{
            from { -webkit-transform:rotate(0deg); }
            to { -webkit-transform:rotate(360deg); }
        }

        #load_text{
            width: 100%;
            font-size: 2em;
            color: #ffba35;
            text-align: center;
            line-height: 2.5em;
        }

        #book
        {
            position:absolute;
			z-index: 10;
            //width:507px;
            //height:537px;
            top:50%;
            //margin-top:-268px;
            background:url(../images/book_bg_2.png);
            background-repeat:no-repeat;
            background-size:100% 100%;
            -webkit-background-origin:content-box; /* Safari */
            background-origin:content-box;

        }

        #logo{
            width: 87%;
            height: 10%;
            padding-top: 1.5em;
            margin-left: 3%;
        }

        #logo img{
            height: 80%;
        }

        #container{
            //width: 400px;
            position:relative;
            margin-left: 3%;
        }

        #container li{
            background-origin:content;                /*从content区域开始显示背景*/
            background-position:50% 50%;              /*图片上下左右居中*/
            background-size:contain;                  /*保持图像本身的宽高比例，将图片缩放到宽度或高度正好适应定义背景的区域*/
            background-repeat:no-repeat;              /*图像不重复显示*/
        }

        #playbtns{
            position:absolute;
            top:35%;
            left: 35%;
            width: 30%;
        }

        #playbtns input{
            width: 100%;
        }



        #bottom{
            position:absolute;
            bottom:0;
            height: 10em;
            width: 100%;
            background: #E2DED3;

        }

        #user{
            float: left;
            width: 18%;
            height: 100%;
            background-color: #222;
            background-origin:content;                /*从content区域开始显示背景*/
            background-position:50% 50%;              /*图片上下左右居中*/
            background-size:contain;                  /*保持图像本身的宽高比例，将图片缩放到宽度或高度正好适应定义背景的区域*/
            background-repeat:no-repeat;              /*图像不重复显示*/

        }

        #info{
            margin-left: .5em;
            float: left;
            font-size: 1.6em;
        }



    </style>
</head>


<body>

	<div id="load">
		<div id="loading">
		    <img id="load_img"  src="../images/apple_logo_bk.png"></img>
			<img id="load_rotate"  src="../images/rotate.png"></img>
			<div id="load_text">数据加载中</div>
		</div>
    </div>

    <div id="book">

            <div id="logo">
                <span>
                    <img style="float: left;" src="../images/apple_logo.png"></img>
                </span>
                <span >
                    <a href="http://iiiview.com/kids/download">
                        <img style="float: right;" src="../images/dl_1.png"></img>
                    </a>
                </span>
            </div>

            <div id="container">
                <div id="slide" >
                    <ul>
                    	<c:choose>
                    		<c:when test="${imgs.size() == 1}">
                    			<c:forEach items="${imgs}" var="img" >
						    		<li style="background-image:url(${img.value});"></li>
					    			<li style="background-image:url(${img.value});"></li>
					    		</c:forEach>
					    	</c:when>
                    		<c:otherwise>
                    			<c:forEach items="${imgs}" var="img" begin="0" end="0">
				    				<li style="background-image:url(${img.value});"></li>
					    		</c:forEach>
                    			<c:forEach items="${imgs}" var="img" begin="1">
				    				<li style="background-image:#FFF;"></li>
					    		</c:forEach>
                    		</c:otherwise>
                    	</c:choose>
                    </ul>
                </div>

                <div id="playbtns">
                    <input id="btnPlay" type="image" value="" src="../images/btn_play.png" class="button" onclick="OnBtnPlay();" />
                </div>

            </div>
    </div>

    <div id="bottom">
        <div id="user" style="background-image:url(${photoUrl});"> </div>
        <div id="info">
            <span><b>${username}</b></span>
            <br />
            <span ><b>标题:</b>${title}</</span>
            <br />
            <span><b>时间:</b>${date} ${time}</span>
            <br />
            <span><b>播放次数:</b><span id="txtPlayCount" >${playCount}</span></span>
        </div>
    </div>

	<div style="visibility:hidden;">
		<audio id="audioPlayer" src="${recordUrl}" controls="controls" onpause="OnAudioPause();" onplay="OnAudioPlayer();" />
	</div>

    <script>

        var browseWidth = document.body.clientWidth;
        var browseHeight = Math.max(document.body.clientHeight,document.documentElement.clientHeight);
        var bookWidth = 507;
        var bookHeight = 537;
        var containerWidth = 440;
        var ivals = new Array();
        var urls = new Array();

        var initWidth =function(){

            var photoHeight = user.offsetHeight;
            var infHeight = info.offsetHeight;

            if(browseHeight/browseWidth < 1.2){
                window.location.href="pad.do" + window.location.search;
            }

            console.warn(browseWidth+"|"+browseHeight ) ;
            if( browseWidth/browseHeight >  bookWidth/bookHeight){
                bookHeight = browseHeight;
                bookWidth =  bookHeight*507/537;
            } else{
                bookWidth = browseWidth*1;
                bookHeight =  bookWidth*537/507;
            }

            console.warn(bookWidth+"|"+bookHeight ) ;
            book.style.width = bookWidth +"px"; //Math.min(ul.width, width);
            book.style.height = bookHeight +"px"; //Math.min(ul.height, height);
            book.style.marginTop= -(bookHeight+photoHeight)/2 +"px";

            containerWidth =  bookWidth*0.87;
            container.style.width = containerWidth +"px";

            console.warn(photoHeight+"|"+photoHeight ) ;
            info.style.marginTop= photoHeight-infHeight +"px";

        };

        var imgLazyload = function(){
    		
			objImage = new Image();
			index =0<urls.length?1:0;
			objImage.onload = function(){
				var liDOM = slide.firstElementChild.children[index];
				liDOM.style.backgroundImage="url("+urls[index-1]+")";
				if(index < urls.length){
					objImage.src = urls[index];
					index++;
				}	
			};
			if (index >0)
				objImage.src = urls[index-1];						
		};

        var swipe;
        var timeoutID = 0;
        var stopSlide = false;

        function OnAudioPlayer() {
            handler2();
            $("#btnStop").hide();
            $("#btnPlay").hide();
        }

        function OnAudioPause() {
        	stopSlide = true;
            if (timeoutID >= 0)
            	clearTimeout(timeoutID);

            swipe = new Swipe(slide,{
                startSlide:0,
                speed:0,
                callback:function(e,pos){
                }
            });
            $("#btnStop").hide();
            $("#btnPlay").show();
        }

        function OnBtnPlay()
        {
        	stopSlide = false;
        	var audio = document.getElementById('audioPlayer');
        	audio.play();
        	$("#btnPlay").hide();

        	jQuery.ajax({
				type: 'GET',
				contentType: 'application/json',
				url: 'hit.do' + window.location.search,
				dataType: 'json',
				success: function(data)
				{
					if (data)
						$("#txtPlayCount").html(data);
				},
				error: function(){
				}
            });
        }
        function OnBtnPause()
        {
        	var audio = document.getElementById('audioPlayer');
        	audio.pause();
        }
        function OnBtnStop()
        {
        	var audio = document.getElementById('audioPlayer');
        	audio.pause();
        	audio.currentTime = 0;

        	if (timeoutID >= 0)
            	clearTimeout(timeoutID);

        	$("#btnStop").hide();
            $("#btnPlay").show();
        }
        var handler2 = function () {
        	 if (stopSlide)
                 return;
             
  			var curIndex = swipe.getPos();
             
             timeoutID = setTimeout(function () {
                 swipe.next();
                 handler2();
             }, ivals[curIndex]);
        }

        var imgUrl="${coverUrl}";
    	var lineLink="${ticket.url}";
    	var descContent="${title}";
    	var shareTitle="${username}读英文绘本";
    	var shareTitle2="${username}读英文绘本";
    	var appid="${ticket.appId}";

        function initWX()
        {
        	on_wx_jsapi_init(wx, 
           			false, 
           			appid, 
           			"${ticket.timestamp}", 
           			"${ticket.nonceStr}", 
           			"${ticket.signature}",
           			imgUrl,
           			lineLink,
           			descContent,
           			shareTitle,
           			shareTitle2);
        }
        
        window.addEventListener("load",function(){
            
            initWX();

            $("#btnPause").hide();
            $("#btnStop").hide();
            
           initWidth();


            window.onorientationchange  = function(){
               window.location.reload();
            }

            <c:forEach items="${imgs}" var="img">
	        	ivals.push(${img.key} * 1000);
		    </c:forEach>

		    <c:forEach items="${imgs}" var="img" begin="1">
	        	urls.push("${img.value}");
		    </c:forEach>
		    imgLazyload();
	    
            var slide = document.getElementById("slide");
            swipe = new Swipe(slide,{
                startSlide:0,
                speed:0,
                //auto:3000,
                callback:function(e,pos){
                }
            });

            //loading.style.visibility="hidden";
            //bottom.style.visibility="visible";
            //book.style.visibility="visible";
			load.style.display="none";

        },false);

    </script>

</body>
</html>