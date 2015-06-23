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
            //width:780px;
            //height:560px;
            top:50%;
            //margin-top:-280px;
            background:url(../images/book_bg_1.png);
            background-repeat:no-repeat;
            background-size:100% 100%;
            -webkit-background-origin:content-box; /* Safari */
            background-origin:content-box;

            display: -webkit-box;
            display:-moz-box;
            box-orient: vertical;
            box-direction: reverse;
        }

        #left
        {
           // width:150px;
            margin-top: 5%;
        }

        #center
        {
           // width:90px;
            height:100%;
        }

        #right
        {
           // width:490px;
		    margin-top: 3%;
            height:100%;
        }

        #user{
            width: 70%;
            margin:  0 auto;
            background: #fff;
            border:1px solid #AAAAAA;
            padding: 4px;
        }

        #imgPhoto{
            width:100%;
        }

        #nickName{
            width:100%;
            text-align:center;
            margin: 2px 0;
        }

        #photoFrame{
            display: block;
            width: 80%;
            margin:  5px auto;
        }

        #info{
            margin-top: 1em;
            width: 100%;
            text-align: center;
        }

        #logo{
		    margin-top: 5%;
            width: 100%;
            height: 8%;
        }

        #logo img{
            height: 100%;
        }

        #container{
            //width: 400px;
            margin-top: 2%;
			width: 100%;
            position:relative;
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
        <div id="left">
            <div id="user">
                <img id="imgPhoto" src="${photoUrl}"></img>
                <div id="nickName"><b>${username}</b></div>
            </div>
            <img id="photoFrame" src="../images/photo_frame.png">    </img>
            <div id="info">
                <span ><b>标题：</b></span>
                <br/>
                <span >${title}</span>
                <br/>
                <span><b>时间:</b></span>
                <br/>
                <span>${date}</span>
                <br/>
                <span>${time}</span>
                <br />
                <span><b>播放次数:</b><span id="txtPlayCount" >${playCount}</span></span>
            </div>

        </div>

        <div id="center"></div>

        <div id="right">

            <div id="logo">
                <span>
                    <img style="float: left;" src="../images/apple_logo.png"></img>
                </span>
                <span>
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
                    <input id="btnPlay" type="image"  src="../images/btn_play.png"  onclick="OnBtnPlay();" />
                </div>

            </div>
        </div>
    </div>
	

    <div style="display:none;">
        <audio id="audioPlayer" src="${recordUrl}" controls onpause="OnAudioPause();" onplay="OnAudioPlayer();" />
    </div>


    <script>

        var browseWidth = document.body.clientWidth;
        var browseHeight = Math.max(document.body.clientHeight,document.documentElement.clientHeight);
        var bookWidth = 777;
        var bookHeight = 596;

        var leftWidth = 150;
        var centerWidth = 90;
        var rightWidth = 490;

        var stopSlide = false;

        var ivals = new Array();
        var urls = new Array();

        var initWidth =function(){
            //785*556

            if(browseHeight/browseWidth > 1.2){
               window.location.href="phone.do" + window.location.search;
            }

            if( browseWidth/browseHeight >  bookWidth/bookHeight){
                bookHeight = browseHeight;
                bookWidth = bookHeight * 777 / 596;
            } else {
                bookWidth = browseWidth;
                bookHeight = bookWidth * 596 / 777;
            }
            book.style.width = bookWidth +"px"; //Math.min(ul.width, width);
            book.style.height = bookHeight +"px"; //Math.min(ul.height, height);
            book.style.marginTop= -(bookHeight/2) +"px";

            leftWidth = bookWidth*leftWidth/785;
            centerWidth = bookWidth*centerWidth/785;
            rightWidth = bookWidth*rightWidth/785;

            console.warn(leftWidth + "|" + centerWidth + "|" + rightWidth );

            left.style.width = leftWidth +"px";
            center.style.width = centerWidth +"px";
            right.style.width = rightWidth +"px";			

        };

		var imgLazyload = function(){
    		
			objImage = new Image();
			index =0<urls.length?1:0;
			objImage.onload = function(){
				var liDOM = slide.firstElementChild.children[index];
				liDOM.style.backgroundImage="url("+urls[index-1]+")";
				//liDOM.style.backgroundRepeat="no-repeat";
				if(index < urls.length){
					objImage.src = urls[index];
					index++;
				}	
			};
			if (index >0)
				objImage.src = urls[index-1];						
		};

        var swipe;
        var timeoutID = -1;

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
		
		console.warn("OnBtnPlay" ) ;
        	stopSlide = false;
        	var audio = document.getElementById('audioPlayer');
        	audio.play();

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
				error: function(data){
				}
            });
        }
        function OnBtnPause()
        {
		
		console.warn("OnBtnPause" ) ;
        	var audio = document.getElementById('audioPlayer');
        	audio.pause();
        }
        function OnBtnStop()
        {
		console.warn("OnBtnStop" ) ;
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

            $("#btnPause").hide();
            $("#btnStop").hide();

            window.onorientationchange  = function(){
                window.location.reload();
            }
            initWidth();

            initWX();

            <c:forEach items="${imgs}" var="img" >
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
               // auto:3000,
                callback:function(e,pos){
                }
            });

			//loading.style.visibility="hidden";
            //bottom.style.visibility="visible";
			
			load.style.display="none";

        },false);

    </script>

</body>
</html>