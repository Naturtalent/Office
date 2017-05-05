<?xml version="1.0" encoding="UTF-8"?>
<!-- * This file is part of the LibreOffice project. * * This Source Code 
	Form is subject to the terms of the Mozilla Public * License, v. 2.0. If 
	a copy of the MPL was not distributed with this * file, You can obtain one 
	at http://mozilla.org/MPL/2.0/. * * This file incorporates work covered by 
	the following license notice: * * Licensed to the Apache Software Foundation 
	(ASF) under one or more * contributor license agreements. See the NOTICE 
	file distributed * with this work for additional information regarding copyright 
	* ownership. The ASF licenses this file to you under the Apache * License, 
	Version 2.0 (the "License"); you may not use this file * except in compliance 
	with the License. You may obtain a copy of * the License at http://www.apache.org/licenses/LICENSE-2.0 
	. -->
<xsl:stylesheet version="1.0"

	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	
	
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- 
	xmlns:style="http://openoffice.org/2000/style" xmlns:text="http://openoffice.org/2000/text"
	xmlns:office="http://openoffice.org/2000/office" xmlns:table="http://openoffice.org/2000/table"
	xmlns:draw="http://openoffice.org/2000/drawing" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:dc="http://purl.org/dc/elements/1.1/"
	xmlns:meta="http://openoffice.org/2000/meta" xmlns:number="http://openoffice.org/2000/datastyle"
	xmlns:svg="http://www.w3.org/2000/svg" xmlns:chart="http://openoffice.org/2000/chart"
	xmlns:dr3d="http://openoffice.org/2000/dr3d" xmlns:math="http://www.w3.org/1998/Math/MathML"
	xmlns:form="http://openoffice.org/2000/form" xmlns:script="http://openoffice.org/2000/script"
	xmlns:config="http://openoffice.org/2001/config" office:class="text"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	exclude-result-prefixes="office meta table number dc fo xlink chart math script xsl draw svg dr3d form config text style">
	
	-->



<!-- 
	<xsl:template match="/">
			<xsl:apply-templates select="office:body" />
	</xsl:template>
	 -->


	<xsl:template match="table:table-cell">
	<xsl:choose>

		<xsl:when test="./@table:style-name='Adresstabelle.A1'">
			<xsl:element name="Adresse">			
				<xsl:value-of select="." />			
			</xsl:element>
		</xsl:when>

	</xsl:choose>

		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="office:text">
		<xsl:text>TEXT</xsl:text>
		<xsl:apply-templates />
	</xsl:template>	
	
	
	<xsl:template match="text:p">
		<xsl:text>TEXT P</xsl:text>
	</xsl:template>	


</xsl:stylesheet>