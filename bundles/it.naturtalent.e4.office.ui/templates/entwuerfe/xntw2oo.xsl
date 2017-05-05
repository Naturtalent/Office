<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"

	xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
	xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
	xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
			
	<!-- Absenderdaten -->	
	<xsl:variable name="absRows" select="document('')/*/absRows"/>
	<absRows>
		<abs></abs>
  		<absName>Absendername</absName>
  		<absAdresse>Absenderadresse</absAdresse>
	</absRows>

	<!-- Adressdaten -->	
	<xsl:variable name="adrRows" select="document('')/*/adrRows"/>
	<adrRows>
		<adrLine0></adrLine0>
  		<adrLine1>Name</adrLine1>
  		<adrLine2>NameZusatz1</adrLine2>
  		<adrLine3>NameZusatz2</adrLine3>
  		<adrLine4>Ort</adrLine4>
  		<adrLine5>Strasse</adrLine5>
	</adrRows>

	<!-- Referenzdaten -->	
	<xsl:variable name="refRows" select="document('')/*/refRows"/>
	<refRows>
		<refRow></refRow>
  		<refRow></refRow>
  		<refRow></refRow>
		
  		<refReferenz>Referenz</refReferenz>
  		<refGap1></refGap1>
  		<refIz>Ihr Zeichen</refIz>
  	
  		<refAnsprechpartner>Ansprechpartner</refAnsprechpartner>  	
  		<refGap2></refGap2>
  		<refUz>Unser Zeichen</refUz>
  	
  		<refDurchwahl>Durchwahl</refDurchwahl>  	
  		<refGap3></refGap3>
  		<refTel>Telefonnummer</refTel>
 	</refRows>
 	

	<!-- Betreffdaten -->	
	<xsl:variable name="betreffRows" select="document('')/*/betreffRows"/>
	<betreffRows>	
		<refRow></refRow>
  		<refRow></refRow>
  		<refRow></refRow>
	
  		<betreffDatum>Datum</betreffDatum>
  		<betreffDmy></betreffDmy>
  		<betreffHeute>heute</betreffHeute>
  	
  		<betreffBetreff>Betreff</betreffBetreff>  	
  		<betreffGap2></betreffGap2>
  		<betreffText>Betrefftext</betreffText>
 	</betreffRows>
 
	
	<!-- Start -->
	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>
 	
<!--  Absenderabelle  -->
	<xsl:template match="table:table[@table:name='Absendertabelle']">
		<xsl:call-template name="writeTable">
			<xsl:with-param name="tableArray" select="$absRows" />  					 
		</xsl:call-template>
	</xsl:template>
 	
<!--  Addesstabelle  -->
	<xsl:template match="table:table[@table:name='Adresstabelle']">
		<xsl:call-template name="writeTable">
			<xsl:with-param name="tableArray" select="$adrRows" />  					 
		</xsl:call-template>
	</xsl:template>

<!--  Referenztabelle  -->
	<xsl:template match="table:table[@table:name='Referenztabelle']">
		<xsl:call-template name="writeTable">
			<xsl:with-param name="tableArray" select="$refRows" />  					 
		</xsl:call-template>
	</xsl:template>
	
<!--  Betrefftabelle  -->
	<xsl:template match="table:table[@table:name='Betrefftabelle']">
		<xsl:call-template name="writeTable">
			<xsl:with-param name="tableArray" select="$betreffRows" />  					 
		</xsl:call-template>
	</xsl:template>
	
<!--  Tabelle ausgeben  -->
	<xsl:template name="writeTable">	
	<xsl:param name="tableArray" /> 
	
	<!-- 'table:table' mit Attributen kopieren -->
	<xsl:copy>
    <xsl:copy-of select="@*"/>    
    
		<!-- Spalten kopieren 'table:column' kopieren (keine Kinder, deshalb 'copy-of')-->
		<xsl:copy-of select="table:table-column"/>
	
		<!-- 'table:row' die Zeilen kopieren (ohne Attribute) -->
		<xsl:for-each select="table:table-row">
		<xsl:variable name = "rowIdx"><xsl:value-of select="position()"/></xsl:variable>				
		<xsl:copy>		

			<xsl:variable name = "nCols"><xsl:value-of select="count(table:table-cell)"/></xsl:variable>
			<xsl:for-each select="table:table-cell">
			<xsl:variable name = "cellIdx"><xsl:value-of select="position()"/></xsl:variable>
							
			<xsl:copy>
			<xsl:copy-of select="@*"/>			
			
				<xsl:call-template name="writeCellText">
					<xsl:with-param name="text" select="$tableArray/*[$nCols*$rowIdx+$cellIdx]" />					  							 
				</xsl:call-template>
			 			 
			 </xsl:copy>
			 </xsl:for-each>
								
		</xsl:copy>						
		</xsl:for-each>
							
	</xsl:copy>
	</xsl:template>
		

<!-- Funktion Text in Tabellenzelle schreiben-->
	<xsl:template name="writeCellText">
  	<xsl:param name="text" /> 
  	
 	   <!-- Text kopieren und Inhalt einfuegen -->
	    <xsl:for-each select="text:p">		
    		<xsl:copy>    	
    		<xsl:copy-of select="@*"/>
    			<xsl:value-of select="$text"/>
			</xsl:copy>
		</xsl:for-each>
	</xsl:template>

	
</xsl:stylesheet>