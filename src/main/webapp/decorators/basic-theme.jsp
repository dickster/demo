<?xml version="1.0" encoding="UTF-8" ?>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Café Mirabeau</title>
    <decorator:head />
</head>
<body>
    <h2>Sitemesh Header</h2>
    <img src="${pageContext.request.contextPath}/decorators/logo.jpg" alt="logo" width="100"/>
    <p><b>Navigation</b></p>  	
    <hr />
    <decorator:body />
    <hr />
    <h5>Sitemesh Footer</h5>
</body>
</html>