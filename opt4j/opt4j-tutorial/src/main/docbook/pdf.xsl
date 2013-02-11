<?xml version='1.0'?>
<xsl:stylesheet  
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:import href="util/docbook-xsl/fo/docbook.xsl"/>

	<xsl:param name="highlight.source" select="1"/>
	<xsl:param name="paper.type">A4</xsl:param>
	<xsl:param name="default.image.width">300pt</xsl:param>
	<xsl:param name="tablecolumns.extension">0</xsl:param>
	<xsl:param name="generate.index">1</xsl:param>
	<!-- <xsl:param name="admon.graphics">1</xsl:param> -->
	<xsl:param name="section.autolabel">1</xsl:param> <!-- section numbering -->
	<xsl:param name="shade.verbatim">1</xsl:param> <!-- grey box behind source code -->
	<xsl:param name="make.index.markup">1</xsl:param>
	<xsl:attribute-set name="monospace.verbatim.properties">
		<xsl:attribute name="wrap-option">wrap</xsl:attribute>
		<xsl:attribute name="font-size">6pt</xsl:attribute>
	</xsl:attribute-set>
	<xsl:param name="img.src.path">src/main/docbook/html/images/</xsl:param>
	<xsl:param name="section.label.includes.component.label">1</xsl:param>
</xsl:stylesheet>