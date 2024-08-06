<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title id="pageTitle">
        S2Struts openid4java app
    </title>
</head>
<body>
<div id="appBody">
    <h1>OpenID HTML FORM</h1>
    <form method="POST" action="login.do" id="appForm">
        <table width="100%" border="0">
            <tr>
                <td style="width: 25%; text-align: right; padding-right: 1em"><label for="url">Url: </label></td>
                <td><input type="text" id="url" name="url" value="https://openstackid.org/" style="width: 100%"/></td>
            </tr>
        </table>
        <input type="submit" name="method" value="doLogin"/>
    </form>
</div>

</body>
</html>