<%--
  Created by IntelliJ IDEA.
  User: Настенька
  Date: 11/8/2015
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <fmt:setLocale value="${sessionScope.locale}"/>
  <fmt:setBundle basename="localize" var="loc"/>
  <fmt:message bundle="${loc}" key="local.title.login" var="title"/>
  <fmt:message bundle="${loc}" key="local.login" var="login"/>
  <fmt:message bundle="${loc}" key="local.password" var="password"/>
  <fmt:message bundle="${loc}" key="local.register" var="register"/>
  <fmt:message bundle="${loc}" key="local.ru" var="ru"/>
  <fmt:message bundle="${loc}" key="local.en" var="en"/>
  <fmt:message bundle="${loc}" key="local.error.message" var="message"/>
  <fmt:message bundle="${loc}" key="local.button.goback" var="goback"/>
    <title>${title}</title>
</head>
<body>

<form action="Controller" method="get">
  <input type="hidden" name="action" value="en"/>
  <input type="hidden" name="page" value="/WEB-INF/jsp/registration.jsp"/>
  <input type="submit" value="${en}"/>
</form>

<form action="Controller" method="get">
  <input type="hidden" name="action" value="ru"/>
  <input type="hidden" name="page" value="/WEB-INF/jsp/registration.jsp"/>
  <input type="submit" value="${ru}"/>
</form>

<hr/>

<p>${register}</p>
<form action="Controller" method="post">
  <input type="hidden" name="action" value="register"/>
  <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>


  <label>First name: </label><input type="text" name="first_name">
  <label>Last name: </label><input type="text" name="last_name">
  <label>Patronymic: </label><input type="text" name="patronymic">
  <label>Date of birth: </label><input type="date" name="date_of_birth">
  <label>Passport data: </label><input type="text" name="passport_data">
  <label>School score: </label><input type="text" name="school_score">
  <label>Test 1: </label><input type="text" name="test_1">
  <label>Test 2: </label><input type="text" name="test_2">
  <label>Test 3: </label><input type="text" name="test_3">

  <input type="text" name="login" placeholder="${login}"/><br/>
  <input type="password" name="password" placeholder="${password}"/>
  <br/><br/>
  <input type="submit" value="${register}"/>
</form>

<c:if test="${requestScope.error != null}">
  <div>
    <h4>${message}</h4>
    <p>${requestScope.error}</p>
  </div>
</c:if>

<a href="/index.jsp">${goback}</a>

</body>
</html>
