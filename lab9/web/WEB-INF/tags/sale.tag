<%@ tag pageEncoding="UTF-8"%>
<%-- Импортировать JSTL-библиотеку --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Кнопка нового объявления показывается только если пользователь определился с типом (authUser!=null) --%>
<html>
<body>
<ad:type type="${param.type}"/>
	Type:
	<select type ='type' size='1'>
		<option value='Продам'>Sell</option>
		<option value='Покупаю'>Buy</option>
		<option value='Меняю'>Exchange</option>
	</select>
</body>
</html>