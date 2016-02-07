<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"
%><!doctype html>
<html>
<tiles:insertAttribute name="property" />
<tiles:insertAttribute name="config" />
<head>
	<tiles:insertAttribute name="head" />
</head>
<body>
<tiles:insertAttribute name="access" />
<div id="daumWrap" class="<tiles:getAsString name='wrapClassName' />">
	<div id="wrapMinidaum"></div>
	<!-- daumHead -->
	<div id="daumHead">
<!--tiles:insertAttribute name="header" />
	</div>
	<!-- // daumHead -->
	<hr />

	<!-- daumContent -->
	<div id="daumContent" class="<tiles:getAsString name='contentClassName' />">
		<h2 class="screen_out"><tiles:insertAttribute name="title" /></h2>
		<!-- cSub -->
		<div id="cSub"><tiles:insertAttribute name="menu" /></div>
		<!-- // cSub -->
		<!-- cMain -->
		<div id="cMain">
			<div id="mTop"><tiles:insertAttribute name="top" /></div><!--// mTop -->
			<div id="mLeft"><tiles:insertAttribute name="left" /></div><!--// mLeft -->
			<div id="mCenter"><tiles:insertAttribute name="body" /></div><!--// mCenter -->
<!--			<div id="mRight"tiles:insertAttribute name="wing" /></div><!--// mRight -->
		</div>
		<!-- // cMain -->
		<div class="cl_b"></div>
		<!-- cEtc -->
		<div id="cEtc"><tiles:insertAttribute name="etc" /></div>
		<!-- // cEtc -->
	</div>
	<!-- // daumContent -->
	<hr />

	<!-- daumFoot -->
	<div id="daumFoot">
<tiles:insertAttribute name="footer" />
	</div>
	<!-- // daumFoot -->
<tiles:insertAttribute name="minidaum" />
<!--tiles:insertAttribute name="rightNotice" /-->
</div>
<tiles:insertAttribute name="lazy" />
</body>
</html>

