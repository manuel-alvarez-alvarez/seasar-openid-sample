<%--
  ~ Copyright 2006-2008 Sxip Identity Corporation
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>OpenID HTML FORM Redirection</title>
</head>
<body>
<h1>OpenID HTML FORM Redirection</h1>
<form name="openid-form-redirection" action="${message.OPEndpoint}" method="post" accept-charset="utf-8">
    <table width="100%" border="0">
        <c:forEach var="parameter" items="${message.parameterMap}">
            <tr>
                <td style="width: 25%; text-align: right; padding-right: 1em"><label for="${parameter.key}">${parameter.key}</label></td>
                <td><input type="text" name="${parameter.key}" value="${parameter.value}" style="width: 100%"/><td>
            </tr>
        </c:forEach>
    </table>
    <button type="submit">Continue...</button>
</form>
</body>
</html>
