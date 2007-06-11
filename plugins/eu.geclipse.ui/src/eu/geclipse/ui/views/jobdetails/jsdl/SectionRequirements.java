/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.jobdetails.jsdl;

import java.util.ArrayList;
import java.util.List;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.ui.views.jobdetails.AbstractSection;
import eu.geclipse.ui.views.jobdetails.ISectionItem;
import eu.geclipse.ui.views.jobdetails.IViewConfiguration;
import eu.geclipse.ui.views.jobdetails.TextSectionItem;

/**
 *
 */
public class SectionRequirements extends AbstractSection<JSDLJobDescription> {

  /**
   * @param viewConfiguration object containing data about current configuration for job-details view
   */
  public SectionRequirements( final IViewConfiguration viewConfiguration ) {
    super( Messages.SectionRequirements_sectionRequirements, viewConfiguration );
  }

  @Override
  protected JSDLJobDescription getSourceObject( final IGridJob gridJob )
  {
    JSDLJobDescription jsdlDescription = null;
    IGridJobDescription gridJobDesc = gridJob.getJobDescription();
    if( gridJobDesc != null && gridJobDesc instanceof JSDLJobDescription ) {
      jsdlDescription = ( JSDLJobDescription )gridJobDesc;
    }
    return jsdlDescription;
  }

  @Override
  protected List<ISectionItem<JSDLJobDescription>> createSectionItems()
  {
    List<ISectionItem<JSDLJobDescription>> sections = new ArrayList<ISectionItem<JSDLJobDescription>>();
    sections.add( createCpuArchitecture() );
    sections.add( createOS() );
    sections.add( createOSVersion() );
    sections.add( createFS() );
    sections.add( createFSDiskSpace() );
    sections.add( createFSMountPoint() );
    return sections;
  }

  ISectionItem<JSDLJobDescription> createCpuArchitecture() {
    return new TextSectionItem<JSDLJobDescription>( Messages.SectionRequirements_itemCpuArch )
    {

      @Override
      protected String getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getCpuArchitectureName();
      }
    };
  }

  ISectionItem<JSDLJobDescription> createOS() {
    return new TextSectionItem<JSDLJobDescription>( Messages.SectionRequirements_itemOS )
    {

      @Override
      protected String getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getOSTypeName();
      }
    };
  }

  ISectionItem<JSDLJobDescription> createOSVersion() {
    return new TextSectionItem<JSDLJobDescription>( Messages.SectionRequirements_itemOSVersion )
    {

      @Override
      protected String getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getOSVersion();
      }
    };
  }

  ISectionItem<JSDLJobDescription> createFS() {
    return new TextSectionItem<JSDLJobDescription>( Messages.SectionRequirements_itemFSName )
    {

      @Override
      protected String getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getFilesystemType();
      }
    };
  }

  ISectionItem<JSDLJobDescription> createFSDiskSpace() {
    return new TextSectionItem<JSDLJobDescription>( Messages.SectionRequirements_itemFSDiskSpace )
    {

      @Override
      protected String getValue( final JSDLJobDescription sourceObject )
      {
        return null;//sourceObject.getFilesystemDiskSpace();
      }
    };
  }

  ISectionItem<JSDLJobDescription> createFSMountPoint() {
    return new TextSectionItem<JSDLJobDescription>( Messages.SectionRequirements_itemFSMountPoint )
    {

      @Override
      protected String getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getFilesystemMountPoint();
      }
    };
  }
}
