<?xml version="1.0" ?>


<xsl:stylesheet
   version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns:axsl="http://www.w3.org/1999/XSL/TransformAlias"
   xmlns:saxon="http://saxon.sf.net/">

<xsl:import href="iso_schematron_skeleton.xsl"/>

	<xsl:template name="process-report">
		<xsl:param name="test"/>
		<xsl:param name="diagnostics" />
		<xsl:param name="id" />
		<xsl:param name="flag" />

           	<!-- "Linkable" parameters -->
		<xsl:param name="role"/>
		<xsl:param name="subject"/>

		<!-- "Rich" parameters -->
		<xsl:param name="fpi" />
		<xsl:param name="icon" /> 
		<xsl:param name="lang" />
		<xsl:param name="see" />
		<xsl:param name="space" />

		<xsl:call-template name="process-message">
			<xsl:with-param name="pattern" select="$test"/>
			<xsl:with-param name="role" select="$role"/>
			<xsl:with-param name="diagnostics" select="$diagnostics"/>
			<xsl:with-param name="type" select="'REPORT'"/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="process-assert">

		<xsl:param name="test"/>
		<xsl:param name="diagnostics" />
		<xsl:param name="id" />
		<xsl:param name="flag" />

           	<!-- "Linkable" parameters -->
		<xsl:param name="role"/>
		<xsl:param name="subject"/>

		<!-- "Rich" parameters -->
		<xsl:param name="fpi" />
		<xsl:param name="icon" />
		<xsl:param name="lang" />
		<xsl:param name="see" />
		<xsl:param name="space" />
		
		<xsl:call-template name="process-message">
			<xsl:with-param name="pattern" select="$test"/>
			<xsl:with-param name="role" select="$role"/>
			<xsl:with-param name="diagnostics" select="$diagnostics"/>
			<xsl:with-param name="type" select="'ASSERT'"/>
		</xsl:call-template>
		
		
	</xsl:template>



	<xsl:template name="process-message">
		<xsl:param name="pattern" />
        <xsl:param name="role" />
		<xsl:param name="diagnostics" />
		<xsl:param name="type" select="'none'"/>
		<xsl:variable name="text">
			<xsl:apply-templates mode="text"/>
		</xsl:variable> 
		<axsl:value-of  select="string('&#10;@@@')"/><axsl:value-of select="saxon:line-number(current())"/>:<xsl:value-of select="$type"/>:<xsl:value-of select="saxon:line-number(.)"/>:<xsl:value-of select="normalize-space($text)"/>	
		 <xsl:if test=" $message-newline = 'true'" >
			<axsl:value-of  select="string('&#10;')"/>
		</xsl:if>
		
	</xsl:template>

</xsl:stylesheet>
