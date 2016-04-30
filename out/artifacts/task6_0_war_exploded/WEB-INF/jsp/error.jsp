<%--
  Created by IntelliJ IDEA.
  User: Настенька
  Date: 11/8/2015
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localize" var="loc"/>
    <fmt:message bundle="${loc}" key="local.title.login" var="title"/>
    <fmt:message bundle="${loc}" key="local.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="local.en" var="en"/>
    <fmt:message bundle="${loc}" key="local.button.goback" var="goback"/>
    <fmt:message bundle="${loc}" key="local.error.message" var="message"/>
    <title>${title}</title>

</head>
<body>

    <%--<c:set var="err" scope="session" value="${requestScope.error}" />--%>
    <form action="Controller" method="post">
      <input type="hidden" name="action" value="en"/>
      <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
      <input type="submit" value="${en}"/>
    </form>

    <form action="Controller" method="post">
      <input type="hidden" name="action" value="ru"/>
      <input type="hidden" name="page" value="${pageContext.request.requestURI}"/>
      <input type="submit" value="${ru}"/>
    </form>

    <h4>${message}</h4>
    ${requestScope.error}
  <a href="/index.jsp">${goback}</a>
</body>
</html>
