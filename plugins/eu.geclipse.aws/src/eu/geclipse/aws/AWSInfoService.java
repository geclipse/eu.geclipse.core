/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.aws.internal.Activator;
import eu.geclipse.aws.internal.Messages;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.AbstractGridInfoService;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.info.model.AbstractGlueStore;
import eu.geclipse.info.model.IExtendedGridInfoService;
import eu.geclipse.info.model.InfoTopTreeElement;

/**
 * The {@link IGridInfoService} for the Amazon EC2 infrastructure.
 * 
 * @author Moritz Post
 */
public class AWSInfoService extends AbstractGridInfoService
  implements IExtendedGridInfoService
{

  /** The name of the file to save this grid element in. */
  public static String STORAGE_NAME = ".awsInfoService"; //$NON-NLS-1$

  /** The virtual organization to used as underlying connection facility. */
  private AWSVirtualOrganization awsVo;

  /**
   * Create a new {@link AWSInfoService} with the provided
   * {@link AWSVirtualOrganization} acting as the connection source.
   * 
   * @param virtualOrganization the vo to hold the connection details
   */
  public AWSInfoService( final AWSVirtualOrganization virtualOrganization ) {
    this.awsVo = virtualOrganization;
  }

  public AbstractGlueStore getStore() {
    // not applicable
    return null;
  }

  public ArrayList<InfoTopTreeElement> getTopTreeElements() {
    // not applicable
    return null;
  }

  public String getVoType() {
    return this.awsVo.getTypeName();
  }

  public void scheduleFetch( final IProgressMonitor monitor ) {
    // not applicable
  }

  public void setVO( final IVirtualOrganization vo ) {
    this.awsVo = ( AWSVirtualOrganization )vo;
  }

  public IGridResource[] fetchResources( final IGridContainer parent,
                                         final IVirtualOrganization vo,
                                         final IGridResourceCategory category,
                                         final boolean exclusive,
                                         final Class<? extends IGridResource> typeFilter,
                                         final IProgressMonitor monitor )
  throws ProblemException
  {
    List<IAWSService> awsServices = null;
    try {
      awsServices = this.awsVo.getChildren( monitor, IAWSService.class );
    } catch( ProblemException pEx ) {
      Activator.log( "Could not load child services", pEx ); //$NON-NLS-1$
    }

    ArrayList<IGridResource> gridResourcesList = null;
    if( awsServices != null ) {
      gridResourcesList = new ArrayList<IGridResource>();

      for( IAWSService service : awsServices ) {
        IGridInfoService infoService = service.getInfoService();
        if( infoService != null ) {

          IGridResource[] gridResource = infoService.fetchResources( parent,
                                                                     vo,
                                                                     category,
                                                                     exclusive,
                                                                     typeFilter,
                                                                     monitor );
          if( gridResource != null ) {
            Collections.addAll( gridResourcesList, gridResource );
          }
        }
      }
    }

    if( gridResourcesList != null ) {
      return gridResourcesList.toArray( new IGridResource[ gridResourcesList.size() ] );
    }
    return new IGridResource[ 0 ];
  }

  public String getHostName() {
    return null;
  }

  public URI getURI() {
    try {
      return new URI( Messages.getString( "AWSInfoService.aws_uri" ) ); //$NON-NLS-1$
    } catch( URISyntaxException e ) {
      return null;
    }
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( AWSInfoService.STORAGE_NAME );
  }

  public String getName() {
    return "AWS @ " + getURI(); //$NON-NLS-1$
  }

  public IGridContainer getParent() {
    return this.awsVo;
  }

  public IPath getPath() {
    return getParent().getPath().append( AWSInfoService.STORAGE_NAME );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }

}
