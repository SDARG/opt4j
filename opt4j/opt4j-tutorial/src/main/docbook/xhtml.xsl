<?xml version='1.0'?>
<xsl:stylesheet  
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:import href="util/docbook-xsl/xhtml/docbook.xsl"/>

	<xsl:param name="html.stylesheet">style.css</xsl:param>
	<xsl:param name="html.stylesheet.type">text/css</xsl:param>
	<xsl:param name="highlight.source" select="1"/>

	<xsl:param name="tablecolumns.extension">0</xsl:param>
	<xsl:param name="generate.index">1</xsl:param>
	<xsl:param name="admon.graphics">1</xsl:param>
	<xsl:param name="section.autolabel">1</xsl:param> <!-- section numbering -->
  <!--<xsl:param name="shade.verbatim">1</xsl:param>  grey box behind source code -->
	<xsl:param name="make.index.markup">1</xsl:param>
	<xsl:param name="img.src.path">images/</xsl:param>
	<xsl:param name="section.label.includes.component.label">1</xsl:param> 
</xsl:stylesheet>