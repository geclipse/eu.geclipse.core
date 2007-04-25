/******************************************************************************
  * Copyright (c) 2007 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     UCY (http://www.ucy.cs.ac.cy)
  *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
  *      - Emilia Stamou (emstamou@cs.ucy.ac.cy)
  *****************************************************************************/

package eu.geclipse.ui.jsdl.editor.pages;

import org.eclipse.osgi.util.NLS;

/**
 * Returns the localised messages for this package.
 */
public class Messages extends NLS {

  public static String DataStagingPage_1;
  public static String DataStagingPage_append;
  public static String DataStagingPage_CreationFlag;
  public static String DataStagingPage_DataStagingDescr;
  public static String DataStagingPage_DataStagingPageTitle;
  public static String DataStagingPage_DeleteOnTermination;
  public static String DataStagingPage_dontOverwrite;
  public static String DataStagingPage_false;
  public static String DataStagingPage_FileName;
  public static String DataStagingPage_FileSystemName;
  public static String DataStagingPage_overwrite;
  public static String DataStagingPage_pageId;
  public static String DataStagingPage_PageTitle;
  public static String DataStagingPage_Source;
  public static String DataStagingPage_Target;
  public static String DataStagingPage_true;
  public static String JobApplicationPage_Add;
  public static String JobApplicationPage_additionalPosixApplDescr;
  public static String JobApplicationPage_additionalPosixApplElementTitle;
  public static String JobApplicationPage_ApplicationDescr;
  public static String JobApplicationPage_ApplicationDescription;
  public static String JobApplicationPage_ApplicationName;
  public static String JobApplicationPage_Applicationtitle;
  public static String JobApplicationPage_ApplicationTitle;
  public static String JobApplicationPage_ApplicationVersion;
  public static String JobApplicationPage_Argument;
  public static String JobApplicationPage_CoreDumpLimit;
  public static String JobApplicationPage_CPUTimeLimit;
  public static String JobApplicationPage_DataSegmentLimit;
  public static String JobApplicationPage_Environment;
  public static String JobApplicationPage_Error;
  public static String JobApplicationPage_Executable;
  public static String JobApplicationPage_FileSizeLimit;
  public static String JobApplicationPage_GroupName;
  public static String JobApplicationPage_Input;
  public static String JobApplicationPage_LockedMemoryLimit;
  public static String JobApplicationPage_MemoryLimit;
  public static String JobApplicationPage_OpenDescriptorsLimit;
  public static String JobApplicationPage_Output;
  public static String JobApplicationPage_pageId;
  public static String JobApplicationPage_PageTitle;
  public static String JobApplicationPage_PipeSizeLimit;
  public static String JobApplicationPage_PosixApplicationDescription;
  public static String JobApplicationPage_PosixApplicationtitle;
  public static String JobApplicationPage_PosixName;
  public static String JobApplicationPage_ProcessCountLimit;
  public static String JobApplicationPage_StackSizeLimit;
  public static String JobApplicationPage_ThreadCountLimit;
  public static String JobApplicationPage_UserName;
  public static String JobApplicationPage_VirtualMemoryLimit;
  public static String JobApplicationPage_WallTimeLimit;
  public static String JobApplicationPage_WorkingDirectory;
  public static String JobDefinitionPage_ButtADD;
  public static String JobDefinitionPage_ButtDEL;
  public static String JobDefinitionPage_JobAnnotation;
  public static String JobDefinitionPage_JobDefinitionDescr;
  public static String JobDefinitionPage_jobDefinitionId;
  public static String JobDefinitionPage_JobDefinitionId;
  public static String JobDefinitionPage_JobDefinitionPageTitle;
  public static String JobDefinitionPage_JobDefinitionTitle;
  public static String JobDefinitionPage_JobDescr;
  public static String JobDefinitionPage_JobIdentificationDescr;
  public static String JobDefinitionPage_JobIdentificationTitle;
  public static String JobDefinitionPage_JobName;
  public static String JobDefinitionPage_JobProject;
  public static String ResourcesPage_AddElementRangeVal;
  public static String ResourcesPage_AddElementsRangeValueDescr;
  public static String ResourcesPage_CPUArch;
  public static String ResourcesPage_CPUArchDescr;
  public static String ResourcesPage_CPUArchName;
  public static String ResourcesPage_CPUTime;
  public static String ResourcesPage_DEscr;
  public static String ResourcesPage_Description;
  public static String ResourcesPage_Exact;
  public static String ResourcesPage_false;
  public static String ResourcesPage_IndCPUCount;
  public static String ResourcesPage_IndCPUSpeed;
  public static String ResourcesPage_IndCPUTime;
  public static String ResourcesPage_IndDiskSpace;
  public static String ResourcesPage_IndNetwBandwidth;
  public static String ResourcesPage_LowBoundRange;
  public static String ResourcesPage_MountPoint;
  public static String ResourcesPage_MountSource;
  public static String ResourcesPage_DiskSpace;
  public static String ResourcesPage_FileSysType;
  public static String ResourcesPage_ExclExec;
  public static String ResourcesPage_ExclExecDescr;
  public static String ResourcesPage_ButtAdd;
  public static String ResourcesPage_ButtDel;
  public static String ResourcesPage_CandHostDesc;
  public static String ResourcesPage_CanHost;
  public static String ResourcesPage_FileSystem;
  public static String ResourcesPage_FileSystemDesc;
  public static String ResourcesPage_HostName;
  public static String ResourcesPage_OperSyst;
  public static String ResourcesPage_OperSystDescr;
  public static String ResourcesPage_OperSystType;
  public static String ResourcesPage_OperSystVersion;
  public static String ResourcesPage_pageId;
  public static String ResourcesPage_PageTitle;
  public static String ResourcesPage_PhysMem;
  public static String ResourcesPage_ResourcePageDescr;
  public static String ResourcesPage_ResourcePageTitle;
  public static String ResourcesPage_TotCPUCount;
  public static String ResourcesPage_TotDiskSpace;
  public static String ResourcesPage_TotPhysMem;
  public static String ResourcesPage_TotRescCount;
  public static String ResourcesPage_TotVirtualMem;
  public static String ResourcesPage_true;
  public static String ResourcesPage_UpBoundRange;
  public static String ResourcesPage_VirtualMem;
  private static final String BUNDLE_NAME = "eu.geclipse.ui.jsdl.editor.pages.messages"; //$NON-NLS-1$
  static {
    // initialize resource bundle
    NLS.initializeMessages( BUNDLE_NAME, Messages.class );
  }

  private Messages() {
    // not instanceable
  }
}
