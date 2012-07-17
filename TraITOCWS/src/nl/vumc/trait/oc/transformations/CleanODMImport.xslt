<?xml version="1.0" ?>
<!--

    Copyright 2012 VU Medical Center Amsterdam
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:cdisc="http://www.cdisc.org/ns/odm/v1.3"
    xmlns:Mirth="http://www.vumc.nl/trait/odm/mirth/v0.1" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
    <xsl:output omit-xml-declaration="no" indent="yes" />
    <xsl:strip-space elements="*" />
    <xsl:template match="ItemData[@Value='&lt;VALUE&gt;']" />
    <xsl:template match="ClinicalData[SubjectData/@SubjectKey='&lt;SUBJECT ID&gt;']" />
    <xsl:template match="/ | @* | node()">
        <xsl:copy>
            <xsl:apply-templates
                select="@* | node()[descendant-or-self::ItemData[@Value!='&lt;VALUE&gt;'] or ancestor-or-self::soapenv:Header]" />
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
