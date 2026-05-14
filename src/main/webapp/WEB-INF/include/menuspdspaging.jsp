<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- c로 시작하는 태그는 uri의 기능을 사용한다는 의미 -->
<%@taglib prefix="c" uri="jakarta.tags.core" %>
	<table class="menu">
		<tr>
			<c:forEach var="menu" items="${ menuList }">
				<td>
					<!-- () ? 'A' : 'B' - 조건 연산자(if문 표현) -->
					<a href="/Pds/List?menu_id=${menu.menu_id}&nowpage=1" class="${menu.menu_id eq map.menu_id ? 'active' : ''}">
					${menu.menu_name}
					</a>
				</td>
			</c:forEach>
		</tr>
	</table>
	
	