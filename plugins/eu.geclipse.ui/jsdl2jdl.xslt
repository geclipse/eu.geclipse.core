<?xml version="1.0"?>

<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:jsdl="http://schemas.ggf.org/jsdl/2005/11/jsdl"
  xmlns:jsdl-posix="http://schemas.ggf.org/jsdl/2005/11/jsdl-posix"
  xmlns:jdl="http://www.glite.org/jdl">

<xsl:strip-space elements="*"/>
<xsl:output method="text"/>

<xsl:key name="data-requirement-name" match="//jsdl:DataStaging[@jdl:name]" use="@jdl:name"/>

<!--
   JSDL to JDL translator, version 0.4, 10/07/2006

   This stylesheet can be used to translate a JSDL document. The
   JSDL must be conformant with the specification, version 1.0
   revision 28, available at https://forge.gridforum.org/projects/jsdl-wg/document/draft-ggf-jsdl-spec/en/28

   USAGE:

     You can try the transformation by issuing the following command:

     xsltproc jsdl2jdl.xslt <jsdl file>

   ASSUMPTIONS (to be verified)

     - jsdl:DataStaging elements without a jsdl:Source or jsdl:Target
     (containing a jsdl:URI element) are skipped;

     - This stylesheet currently uses the proposed extension to the JDL
     which allows InputSandbox files to be 

     - This stylesheet inserts a "Rank =
     -other.GlueCEStateEstimatedResponseTime;" line into the resulting
     JDL (according to the JDL specification, the "Rank" attribute is
     mandatory)

     - This stylesheet inserts a "Requirements =
     other.GlueCEStateStatus == "Production";" line into the resulting
     JDL _if_ no other "Requirements" statement would be generated
     (according to the JDL specification, the "Requirements" attribute
     is mandatory).

   CURRENT LIMITATIONS:

     - "DataRequirements" JDL attribute is generated with some limitations:
       currently the stylesheet is unable to group DataRequirements entries
       by DataCatalogType and DataCatalog
     - "Filesystem" elements are not supported yet; 
     - "FileSystemName" elements inside DataStaging are not supported yet;

   AUTHORS 

     Moreno Marzolla <moreno.marzolla@pd.infn.it>

  -->


<!-- The root pattern. It produces some hardcoded defaults in the resulting JDL -->
<xsl:template match="/">
  <xsl:text>[
Type = "Job"; 
JobType = "Normal"; 
RetryCount = 3;
</xsl:text>

  <!-- Now we apply other templates to produce the relevant sections
  of the JDL. NOTE: the order in which templates are applied should be
  specified explicitly here. This is necessary to group processing of
  several tags which should generate the same JDL element.  -->

  <!-- JDL Executable -->
  <xsl:apply-templates select="//jsdl-posix:Executable"/>

  <!-- JDL arguments -->
  <xsl:apply-templates select="//jsdl-posix:Argument"/>

  <!-- JDL Virtual Organization -->
  <!-- Disabled as VO is taken from VOMS certificate
  <xsl:apply-templates select="//jsdl:JobProject"/>
  -->

  <!-- JDL Data Access Protocol -->
  <xsl:apply-templates select="//jdl:DataAccessProtocol"/>

  <!-- JDL Prologue -->
  <xsl:apply-templates select="//jdl:Prologue"/>

  <!-- JDL Epilogue -->
  <xsl:apply-templates select="//jdl:Epilogue"/>

  <!-- JDL Data Requirements -->
  <!--
      <xsl:apply-templates select="//jsdl:DataStaging[@jdl:name][not(@jdl:name=preceding-sibling::jsdl:DataStaging/@jdl:name)]" mode="datarequirements">
      <xsl:sort select="@jdl:name"/>
      </xsl:apply-templates>    
  -->
  
  <xsl:apply-templates select="//jsdl:DataStaging[@jdl:name]" mode="datarequirements"/>

  <!-- JSDL StdInput & StdOutput -->
  <xsl:apply-templates select="//jsdl-posix:Input|//jsdl-posix:Output|//jsdl-posix:Error"/>

  <!-- JDL Input Sandbox -->
  <xsl:apply-templates select="//jsdl:DataStaging[jsdl:Source/jsdl:URI][not(@jdl:name)]" mode="inputsb"/>

  <!-- JDL Input Sandbox dest filename -->
  <xsl:apply-templates select="//jsdl:DataStaging[jsdl:Source/jsdl:URI][not(@jdl:name)]" mode="inputsbdestfname"/>

  <!-- JDL Output Sandbox -->
  <xsl:apply-templates select="//jsdl:DataStaging[jsdl:Target/jsdl:URI][not(@jdl:name)]" mode="outputsb"/>

  <!-- JDL Output Sandbox Dest URI -->
  <xsl:apply-templates select="//jsdl:DataStaging[jsdl:Target/jsdl:URI][not(@jdl:name)]" mode="outputsbdesturi"/>

  <!-- Delete On Terminate -->
  <xsl:apply-templates select="//jsdl:DataStaging[jsdl:FileName][jsdl:DeleteOnTermination='true']" mode="deleteontermination"/>

  <!-- JDL Environment -->
  <xsl:apply-templates select="//jsdl-posix:Environment"/>

  <!-- JDL Constraints -->
  <xsl:choose>
    <xsl:when test="//jsdl:Resources"> 
      <xsl:apply-templates select="//jsdl:Resources"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>Requirements = other.GlueCEStateStatus == "Production";
</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

  <!-- Inserts the default "Rank" expression -->
  <xsl:text>Rank = -other.GlueCEStateEstimatedResponseTime;
</xsl:text>

  <!-- Report warnings on JSDL elements which have not been considered -->
  <xsl:text>
]
</xsl:text>
</xsl:template>

<!--
============================================================================== 

The POSIX-application "Executable" tag is used to define the valua of
the "Executable" JDL Attribute

==============================================================================
-->
<xsl:template match="jsdl-posix:Executable">
  <xsl:text>Executable ="</xsl:text><xsl:value-of select="normalize-space(.)"/><xsl:text>";
</xsl:text>
</xsl:template>


<!-- The POSIX-application "Argument" tags are used to build the list
of arguments of the job. Note how the list is built: it is ESSENTIAL
that ALL "jsdl-posix:Argument" tags are processed together -->

<xsl:template match="jsdl-posix:Argument">

  <!-- When processing the first jsdl-posix:Argument tag, print the classad "Arguments" attribute name -->
  <xsl:if test="position()=1">
    <xsl:text>Arguments = "</xsl:text>
  </xsl:if>

  <xsl:value-of select="."/>

  <!-- Add trailing space only if we are not processing the last argument -->
  <xsl:if test="not(position()=last())">
    <xsl:text> </xsl:text>
  </xsl:if>

  <!-- When processing the last jsdl-posix:Argument tag, print the closing quotes -->
  <xsl:if test="position()=last()">
    <xsl:text>";
</xsl:text>
  </xsl:if>
</xsl:template>


<!--
============================================================================== 

The POSIX-application "Input" tag is used to define the valua of the
"StdInput" JDL Attribute

==============================================================================
-->
<xsl:template match="jsdl-posix:Input">
  <xsl:text>StdInput = "</xsl:text><xsl:value-of select="."/><xsl:text>";
</xsl:text>
</xsl:template>


<!--
============================================================================== 

The POSIX-application "Output" tag is used to define the valua of the
"StdOutput" JDL Attribute

==============================================================================
-->
<xsl:template match="jsdl-posix:Output">
  <xsl:text>StdOutput = "</xsl:text><xsl:value-of select="."/><xsl:text>";
</xsl:text>
</xsl:template>


<!--
============================================================================== 

The POSIX-application "Error" tag is used to define the valua of the
"StdError" JDL Attribute

==============================================================================
-->
<xsl:template match="jsdl-posix:Error">
  <xsl:text>StdError = "</xsl:text><xsl:value-of select="."/><xsl:text>";
</xsl:text>
</xsl:template>


<!--
==============================================================================

Extension for Data Catalogs: DataAccessProtocol

==============================================================================
-->
<xsl:template match="jdl:DataAccessProtocol">

  <xsl:if test="position()=1">
    <xsl:text>DataAccessProtocol = { </xsl:text>
  </xsl:if>

  <xsl:text>"</xsl:text>
  <xsl:value-of select="."/>
  <xsl:text>"</xsl:text>

  <xsl:if test="not(position()=last())">
    <xsl:text>, </xsl:text>
  </xsl:if>

  <xsl:if test="position()=last()">
    <xsl:text> };
</xsl:text>
  </xsl:if>

</xsl:template>



<!--
==============================================================================

Extension for Data Staging (for data catalog)

==============================================================================
-->
<!--
<xsl:template match="jsdl:DataStaging[@jdl:name]" mode="datarequirements">

  <xsl:if test="position()=1">
    <xsl:text>DataRequirements = {
</xsl:text>

</xsl:if><xsl:text>[
  DataCatalogType = "</xsl:text><xsl:value-of select="@jdl:name"/><xsl:text>";
  DataCatalog = "</xsl:text><xsl:value-of select="./jdl:DataCatalogEndPoint"/><xsl:text>";
  InputData = { </xsl:text>
  <xsl:apply-templates select="key('data-requirement-name', ./@jdl:name)" mode="dataaccess"/>
  <xsl:text> };
]</xsl:text><xsl:choose>
<xsl:when  test="position()=last()">
  <xsl:text> 
};
</xsl:text>
</xsl:when>
<xsl:otherwise>
  <xsl:text>, 
</xsl:text>
</xsl:otherwise>
</xsl:choose>

</xsl:template>
-->

<xsl:template match="jsdl:DataStaging[@jdl:name]" mode="datarequirements">

  <xsl:if test="position()=1">
    <xsl:text>DataRequirements = {
</xsl:text>

</xsl:if><xsl:text>[
  DataCatalogType = "</xsl:text><xsl:value-of select="@jdl:name"/><xsl:text>";
  DataCatalog = "</xsl:text><xsl:value-of select="./jdl:DataCatalogEndPoint"/><xsl:text>";
  InputData = { "</xsl:text><xsl:value-of select="./jsdl:Source/jsdl:URI"/><xsl:text>" };
]</xsl:text><xsl:choose>
<xsl:when  test="position()=last()">
  <xsl:text> 
};
</xsl:text>
</xsl:when>
<xsl:otherwise>
  <xsl:text>, 
</xsl:text>
</xsl:otherwise>
</xsl:choose>

</xsl:template>

<!-- jdl dataaccess -->
<!--
<xsl:template match="jsdl:DataStaging" mode="dataaccess">
  <xsl:if test="position()>1">
    <xsl:text>, </xsl:text>
  </xsl:if>
  <xsl:text>"</xsl:text><xsl:value-of select="./jsdl:Source/jsdl:URI"/><xsl:text>"</xsl:text>
</xsl:template>
-->

<!--
============================================================================== 

The jsdl:DataStaging element is used to define the content of the
InputSandbox JDL attribute. Only jsdl:DataStaging elements with a
jsdl:Source child are considered. Moreover, the jsdl:URI element
_must_ be present inside the jsdl:Source (this was a decision taken at
a meeting).

==============================================================================
-->
<xsl:template match="jsdl:DataStaging[jsdl:Source/jsdl:URI]" mode="inputsb">
  <xsl:if test="position()=1">
    <xsl:text>InputSandbox = { "</xsl:text>
  </xsl:if>

  <xsl:value-of select="jsdl:Source/jsdl:URI"/>

  <xsl:choose>
    <xsl:when test="position()=last()">
      <xsl:text>" };
</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>", "</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!--
============================================================================== 

The jsdl:DataStaging element is used to define the content of the
InputSandboxDestFileName extension JDL attribute. 

==============================================================================
-->
<xsl:template match="jsdl:DataStaging[jsdl:Source/jsdl:URI]" mode="inputsbdestfname">
  <xsl:if test="position()=1">
    <xsl:text>InputSandboxDestFileName = { "</xsl:text>
  </xsl:if>

  <xsl:value-of select="jsdl:FileName"/>

  <xsl:choose>
    <xsl:when test="position()=last()">
      <xsl:text>" };
</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>", "</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>




<!--
============================================================================== 

The jsdl:DataStaging element is used to define the content of the
OutputSandbox JDL attribute. Only jsdl:DataStaging elements with a
jsdl:Target child are considered. Moreover, the jsdl:Target element
_must_ have a jsdl:URI child.

==============================================================================
-->
<xsl:template match="jsdl:DataStaging[jsdl:Target/jsdl:URI]" mode="outputsb">
  <xsl:if test="position()=1">
    <xsl:text>OutputSandbox = { "</xsl:text>
  </xsl:if>

  <xsl:value-of select="jsdl:FileName"/>

  <xsl:choose>
    <xsl:when test="position()=last()">
      <xsl:text>" };
</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>", "</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>


<!--
============================================================================== 

The jsdl:DataStaging element is used to define the content of the
OutputSandboxDestURI JDL attribute. 

==============================================================================
-->
<xsl:template match="jsdl:DataStaging[jsdl:Target/jsdl:URI]" mode="outputsbdesturi">
  <xsl:if test="position()=1">
    <xsl:text>OutputSandboxDestURI = { "</xsl:text>
  </xsl:if>

  <xsl:value-of select="jsdl:Target/jsdl:URI"/>

  <xsl:choose>
    <xsl:when test="position()=last()">
      <xsl:text>" };
</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>", "</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!--
==============================================================================

DeleteOnTermination

==============================================================================
-->
<xsl:template match="jsdl:DataStaging[jsdl:DeleteOnTermination='true']" mode="deleteontermination">

  <xsl:if test="position()=1">
    <xsl:text>DeleteOnTermination = { "</xsl:text>
  </xsl:if> 

  <xsl:value-of select="jsdl:FileName"/>

  <xsl:choose>
    <xsl:when test="position()=last()">
      <xsl:text>" };
</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>", "</xsl:text>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>

<!--
==============================================================================

TODO: Filesystem

==============================================================================
-->

<!--
============================================================================== 

The jsdl:JobProject attribute is used to define the
VirtualOrganization tag in the JDL.

==============================================================================
-->
<xsl:template match="jsdl:JobProject">
  <xsl:text>VirtualOrganisation = "</xsl:text>
  <xsl:value-of select="."/>
  <xsl:text>";
</xsl:text>
</xsl:template>

<!--
==============================================================================

The jsdl-posix:Environment attribute is used to define the Environment
tag in the JDL

==============================================================================
-->
<xsl:template match="jsdl-posix:Environment">
  <xsl:if test="position()=1">
    <xsl:text>Environment = { "</xsl:text>
  </xsl:if>

  <xsl:value-of select="@name"/>
  <xsl:text>=</xsl:text>
  <xsl:value-of select="."/>

  <xsl:choose>
    <xsl:when test="position()=last()">
      <xsl:text>" };
</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>", "</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>


<!--
============================================================================== 

The jsdl:Resources tag is used to define the Requirements attribute of
the JDL. This is probably the most complicated part of the
translation, as requirements have to be translated into composition of
boolean predicates.

==============================================================================
-->
<xsl:template match="jsdl:Resources">
  <xsl:text>Requirements = </xsl:text>
   <xsl:for-each select="jsdl:CandidateHosts|jsdl:CPUArchitecture|jsdl:IndividualPhysicalMemory|jsdl:IndividualVirtualMemory|jsdl:IndividualCPUSpeed|jsdl:IndividualCPUTime|jsdl:OperatingSystem|jdl:LRMSType">
     <xsl:text>( </xsl:text><xsl:apply-templates select="."/><xsl:text> )</xsl:text>
     <xsl:if test="position()&lt;last()">
        <xsl:text> &amp;&amp; </xsl:text>
     </xsl:if>
  </xsl:for-each>
  <xsl:text>;
</xsl:text>
</xsl:template>

<!-- Candidate hosts -->
<xsl:template match="jsdl:CandidateHosts">
  <xsl:if test="position()=1">
    <xsl:text>member( other.GlueCEInfoHostName, {</xsl:text>
  </xsl:if>
  <xsl:for-each select="jsdl:HostName">
    <xsl:text>"</xsl:text><xsl:value-of select="."/><xsl:text>"</xsl:text>
    <xsl:if test="position()&lt;last()">
      <xsl:text> , </xsl:text>
    </xsl:if>
  </xsl:for-each>
  <xsl:text> } )</xsl:text>
</xsl:template>

<!-- CPU Architecture -->
<!-- TODO: Check if the enumeration of jsdl:CpuArchitectureName is compatible with the values of GlueHostProcessorModel -->
<xsl:template match="jsdl:CPUArchitecture">
  <xsl:text>GlueHostProcessorModel == "</xsl:text>
  <xsl:value-of select="jsdl:CPUArchitectureName"/>
  <xsl:text>"</xsl:text>
</xsl:template>


<!-- Physical Memory -->
<xsl:template match="jsdl:IndividualPhysicalMemory">
  <xsl:apply-templates>
     <xsl:with-param name="VARNAME">GlueHostMainMemorySize</xsl:with-param>
  </xsl:apply-templates>
</xsl:template>


<!-- Virtual Memory -->
<xsl:template match="jsdl:IndividualVirtualMemory">
  <xsl:apply-templates>
     <xsl:with-param name="VARNAME">GlueHostVirtualMemorySize</xsl:with-param>
  </xsl:apply-templates>
</xsl:template>


<!-- Individual CPU Speed -->
<xsl:template match="jsdl:IndividualCPUSpeed">
  <xsl:apply-templates>
     <xsl:with-param name="VARNAME">GlueHostProcessorSpeed</xsl:with-param>
  </xsl:apply-templates>
</xsl:template>


<!-- Individual CPU Time -->
<xsl:template match="jsdl:IndividualCPUTime">
  <xsl:apply-templates>
     <xsl:with-param name="VARNAME">GlueCEMaxCpuTime</xsl:with-param>
  </xsl:apply-templates>
</xsl:template>


<!-- Operating System -->
<!-- TODO: Change mapping between jsdl:OperatingSystemName and GlueHostOperatingSystemName -->
<xsl:template match="jsdl:OperatingSystem">
  <xsl:if test="jsdl:OperatingSystemType">
    <xsl:text>GlueHostOperatingSystemName =="</xsl:text>
    <xsl:value-of select="jsdl:OperatingSystemType/jsdl:OperatingSystemName"/>
    <xsl:text>"</xsl:text>
  </xsl:if>
  <xsl:if test="jsdl:OperatingSystemVersion">
    <xsl:text>GlueHostOperatingSystemVersion =="</xsl:text>
    <xsl:value-of select="jsdl:OperatingSystemVersion"/>
    <xsl:text>"</xsl:text>
  </xsl:if>
</xsl:template>

<!-- LRMSType (glite extension) -->
<xsl:template match="jdl:LRMSType">
  <xsl:text>other.GlueCEInfoLRMSType == "</xsl:text>
  <xsl:value-of select="."/>
  <xsl:text>"</xsl:text>
</xsl:template>

<!--
==============================================================================

Range management. These patterns are used to manage rangevalue_types
as defined in the JSDL specification. CAVEAT: Currently, the
epsilon-equality test is not supported.

==============================================================================
-->
<!-- LowerBoundedRange types -->
<xsl:template match="jsdl:LowerBoundedRange">
  <xsl:param name="VARNAME"/>
  <xsl:value-of select="$VARNAME"/>
  <xsl:choose>
    <xsl:when test="@jsdl:exclusiveBound='true'">
       <xsl:text> &gt; </xsl:text>
    </xsl:when>
    <xsl:otherwise>
       <xsl:text> &gt;= </xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="."/>
</xsl:template>


<!-- UpperBoundedRange types -->
<xsl:template match="jsdl:UpperBoundedRange">
  <xsl:param name="VARNAME"/>
  <xsl:value-of select="$VARNAME"/>
  <xsl:choose>
    <xsl:when test="@jsdl:exclusiveBound='true'">
       <xsl:text> &lt; </xsl:text>
    </xsl:when>
    <xsl:otherwise>
       <xsl:text> &lt;= </xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="."/>
</xsl:template>


<!-- ExactRange types -->
<xsl:template match="jsdl:Exact">
  <xsl:param name="VARNAME"/>
  <xsl:value-of select="$VARNAME"/><xsl:text> == </xsl:text>
  <xsl:value-of select="."/>
</xsl:template>


<!-- Range types -->
<xsl:template match="jsdl:Range">
  <xsl:param name="VARNAME"/>
  <xsl:value-of select="$VARNAME"/>  
  <xsl:choose>
    <xsl:when test="jsdl:LowerBound/@jsdl:exclusiveBound='true'">
       <xsl:text> &gt; </xsl:text>
    </xsl:when>
    <xsl:otherwise>
       <xsl:text> &gt;= </xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="."/>
  <xsl:text> &amp;&amp; </xsl:text>
  <xsl:value-of select="."/>

  <xsl:choose>
    <xsl:when test="jsdl:UpperBound/@jsdl:exclusiveBound='true'">
       <xsl:text> &lt; </xsl:text>
    </xsl:when>
    <xsl:otherwise>
       <xsl:text> &lt;= </xsl:text>
    </xsl:otherwise>
  </xsl:choose>

</xsl:template>


<!--
==============================================================================

Process the jdl:Prologue attribute (JDL extension)

==============================================================================
-->
<xsl:template match="jdl:Prologue">
  <xsl:text>Prologue = "</xsl:text>

  <xsl:value-of select="."/>

  <xsl:text>" };
</xsl:text>

</xsl:template>


<!--
==============================================================================

Process the jdl:Epilogue attribute (JDL extension)

==============================================================================
-->
<xsl:template match="jdl:Epilogue">
  <xsl:text>Epilogue = "</xsl:text>

  <xsl:value-of select="."/>

  <xsl:text>" };
</xsl:text>

</xsl:template>


<!--
==============================================================================

Returns the basename of a path. Currently this is done in a very
simple way: we recursively check whether the path contains a slash
('/'); if so, the function is applied again on the substring following
the first occurrence of the '/' character.

@param thePath the string representing the path from which the
basename should be extracted

@return the basename of the path

==============================================================================
-->
<!--
<xsl:template name="path-basename">
  <xsl:param name="thePath"/>

  <xsl:choose>
    <xsl:when test="contains($thePath,'/')">
      <xsl:call-template name="path-basename">
        <xsl:with-param name="thePath" select="substring-after($thePath,'/')"/>
      </xsl:call-template>
    </xsl:when>
    <xsl:otherwise>
      <xsl:copy-of select="$thePath"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
-->

<!-- Default, fall-off rules -->

<xsl:template match="*">
  <xsl:text>/* fall-off */</xsl:text>
  <xsl:apply-templates/>
</xsl:template>

<!-- Text nodes and attributes are discarded by default -->
<!--
<xsl:template match="@*|text()">
  <xsl:text>/* unmatched element: </xsl:text>
  <xsl:value-of select="."/>
  <xsl:text> */</xsl:text>
</xsl:template>
-->
</xsl:stylesheet>
