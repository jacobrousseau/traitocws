<?xml version="1.0" encoding="UTF-8"?>
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
    xmlns:Mirth="http://www.vumc.nl/trait/odm/mirth/v0.1" xmlns:OpenClinica="http://www.openclinica.org/ns/odm_ext_v130/v3.1">

    <!-- Convert ODM Study metadata to a template for item data ODM -->

    <xsl:output omit-xml-declaration="yes" indent="yes" />
    <xsl:strip-space elements="*" />
    <xsl:template match="/cdisc:ODM">
        <ODM>
            <xsl:for-each select="cdisc:Study">
                <ClinicalData Mirth:TranslateOID="false">
                    <xsl:attribute name="StudyOID">
						<xsl:value-of select="@OID" />
					</xsl:attribute>
                    <SubjectData SubjectKey="&lt;SUBJECT ID&gt;" Mirth:TranslateOID="true" Mirth:Create="false"
                        OpenClinica:UniqueIdentifier="&lt;VALUE&gt;" OpenClinica:DateOfBirth="&lt;VALUE&gt;"
                        OpenClinica:Sex="&lt;VALUE&gt;" OpenClinica:DateOfRegistration="&lt;VALUE&gt;">
                        <xsl:for-each select="cdisc:MetaDataVersion/cdisc:Protocol/cdisc:StudyEventRef">
                            <xsl:variable name="OID" select="@StudyEventOID" />
                            <StudyEventData OpenClinica:StartDate="&lt;VALUE&gt;" StudyEventRepeatKey="&lt;VALUE&gt;">
                                <xsl:attribute name="StudyEventOID">
									<xsl:value-of select="@StudyEventOID" />
								</xsl:attribute>
                                <xsl:for-each select="//cdisc:StudyEventDef[@OID=$OID]/cdisc:FormRef">
                                    <xsl:variable name="OID" select="@FormOID" />
                                    <FormData>
                                        <xsl:attribute name="FormOID">
											<xsl:value-of select="@FormOID" />
										</xsl:attribute>
                                        <xsl:for-each select="../../cdisc:FormDef[@OID=$OID]/cdisc:ItemGroupRef">
                                            <xsl:variable name="OID" select="@ItemGroupOID" />
                                            <ItemGroupData TransactionType="Insert" ItemGroupRepeatKey="&lt;VALUE&gt;">
                                                <xsl:attribute name="ItemGroupOID">
													<xsl:value-of select="@ItemGroupOID" />
												</xsl:attribute>
                                                <xsl:for-each select="../../cdisc:ItemGroupDef[@OID=$OID]/cdisc:ItemRef">
                                                    <xsl:variable name="OID" select="@ItemOID" />
                                                    <xsl:for-each select="../../cdisc:ItemDef[@OID=$OID]">
                                                        <ItemData Value="&lt;VALUE&gt;">
                                                            <xsl:attribute name="ItemOID">
																<xsl:value-of select="@OID" />
															</xsl:attribute>
                                                            <!-- <xsl:attribute name="Mirth:Name"><xsl:value-of select="@Name"/></xsl:attribute> -->
                                                            <xsl:attribute name="Mirth:Comment">
																<xsl:value-of select="@Comment" />
															</xsl:attribute>
                                                            <xsl:attribute name="Mirth:DataType">
																<xsl:value-of select="@DataType" />
															</xsl:attribute>
                                                            <xsl:attribute name="Mirth:ResponseType">
                                                                <xsl:value-of
                                                                select="OpenClinica:ItemDetails/OpenClinica:ItemPresentInForm/OpenClinica:ItemResponse/@ResponseType" />
                                                            </xsl:attribute>
                                                        </ItemData>
                                                    </xsl:for-each>
                                                </xsl:for-each>
                                            </ItemGroupData>
                                        </xsl:for-each>
                                    </FormData>
                                </xsl:for-each>
                            </StudyEventData>
                        </xsl:for-each>
                    </SubjectData>
                </ClinicalData>
            </xsl:for-each>
        </ODM>
    </xsl:template>
</xsl:stylesheet>
