<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PdsWrite</title>
<link href="/css/common.css" rel="stylesheet">
<link rel="shortcut icon" href="/img/favicon.png" type="image/x-icon">
<style>
	table {width:100%;}
	#table1 { width : 100%; 
		margin-bottom: 150px;
	}
	td {
		padding : 5px 10px;
		text-align : center;
		&:nth-of-type(1) {
			background : black;
			color : white;
			border : 1px solid white;
		}
	}
	tr:last-child > td {
	background : white;
	border : 1px solid black;
	}
	input[type=text], input[type=password], input[type=email] {
		width : 100%;
	}
	input[type=submit] {
	width:100px;
	}
	input[type=button] {
	width:100px;
	}
	input[name=userid] {
	width : 79%;
	}
	#btnAddFile {
		width : 150px;
	}
	textarea {
		width : 100%;
		height : 300px;		
	}
</style>
</head>
<body>
	<main>
	<%@include file="/WEB-INF/include/menuspdspaging.jsp" %>
	
		<h2 class=".h2"><b id="mname"></b>새 글 쓰기 자료실</h2>
		<form action="/Pds/Write" method="post" enctype="multipart/form-data" >
		<input type="hidden" name="menu_id" value="${map.menu_id}" />
		<input type="hidden" name="nowpage" value="${map.nowpage}" />
			<table id="table1">
				<tr>
					<td><span class='red'>*</span>제목</td>
					<td>
					<input type="text" name="title"/>
					</td>
				</tr>
				<tr>
					<td><span class='red'>*</span>작성자</td>
					<td><input type="text" name="writer" value="${sessionScope.login.userid}"/></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="content"></textarea></td>
				</tr>
				<tr>
					<td>파일</td>
					<td id="tdfile">
						<input type="button" id="btnAddFile" value="파일추가(최대100MByte)"/><br>
						<input type="file" name="upfile" class="upfile" multiple/><br>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="submit" value="추가"/>
						<input type="button" value="목록" id="goList" />
						
					</td>
				</tr>
			</table>
		</form>	
	</main>
	<script>
		// 메뉴제목 출력
 		const mnameEl		=	document.querySelector('#mname');
		let		menunameEl	=	document.querySelector('.menu .active');
		mnameEl.innerHTML = menunameEl.innerHTML; 
		
		// 목록으로 이동
		// onclick ="window.location.href='/Pds/List?menu_id=${map.menu_id}&nowpage=${map.nowpage}'"
		const goListEl = document.querySelector('#goList')
		goListEl.onclick	=	function () {
			window.location.href='/Pds/List?menu_id=${map.menu_id}&nowpage=${map.nowpage}'
		}
				
		// 파일 입력창 추가 - onclick 주는 위치에 따라 결과값이 달라짐 추후에 이야기 할거임
		const btnAddFileEl 	= document.querySelector('#btnAddFile')
		const tdfileEl 			= document.querySelector('#tdfile')
		let tag							=	'<input type="file" name="upfile" class="upfile" multiple/><br>'
		let html						= tdfileEl.innerHTML	
		// js에서 실행할때 새로 추가된 버튼은 이벤트가 한번만 작동한다
		// 해결책은 이벤트를 부모 element에 설정한다
/* 		btnAddFileEl.addEventListener('click', function () {
			html							 +=	tag
			tdfileEl.innerHTML = html 
			})
			*/ // 주석줄 사용하려면 위의 html을 아래처럼 변경해야됨
			
			/*
			<td>
				<input type="button" id="btnAddFile" value="파일추가(최대100MByte)"/>
				<div id="tdfile">
				<input type="file" name="upfile" class="upfile" multiple/><br>
				</div>
			</td>
			*/
			
			tdfileEl.addEventListener('click', function (e) {
				console.log(e.target) // btnAddFile, upfile
				if (e.target == btnAddFile) {
					html							 +=	tag
					tdfileEl.innerHTML = html
				}
			})
		
		// 입력항목 체크
		
	</script>
	<!-- javascript validation -->
	
</body>
</html>