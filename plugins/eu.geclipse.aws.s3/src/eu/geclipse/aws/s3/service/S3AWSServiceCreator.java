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

package eu.geclipse.aws.s3.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.aws.IAWSServiceCreator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.aws.vo.AWSVoCreator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * This element creator is able to construct {@link S3AWSService} objects.
 * 
 * @author Moritz Post
 */
public class S3AWSServiceCreator extends AbstractGridElementCreator
  implements IAWSServiceCreator
{

  /** The creators extension ID. */
  private static final String EXTENSION_ID = "eu.geclipse.aws.s3.service.s3AWSServiceCreator"; //$NON-NLS-1$

  /**
   * An {@link IFileStore} handle containing this {@link AWSVoCreator} and its
   * children.
   */
  private IFileStore s3AWSServiceCreatorFileStore;

  /** The URL to the Amazon Elastic Computing Cloud service. */
  private String s3Url;

  /** The name of the service which this {@link IGridElementCreator} can create. */
  private String serviceName;

  @Override
  public boolean internalCanCreate( final Object fromObject ) {
    if( fromObject instanceof String ) {
      try {
        new URL( ( String )fromObject );
        return true;
      } catch( MalformedURLException e ) {
        // nothing to do
      }
    }
    return false;
  }

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return elementType.isAssignableFrom( S3AWSService.class );
  }

  public IGridElement create( final IGridContainer parent )
    throws ProblemException
  {
    S3AWSService s3awsService = null;
    if( parent instanceof AWSVirtualOrganization ) {
      AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )parent;
      if( this.s3AWSServiceCreatorFileStore == null ) {
        s3awsService = new S3AWSService( this, awsVo );
      } else {
        s3awsService = new S3AWSService( awsVo );
      }
    }
    return s3awsService;
  }

  public boolean canCreate( final IFileStore fromFileStore ) {
    if( fromFileStore != null ) {
      IFileStore propertiesStore = fromFileStore.getChild( S3ServiceProperties.STORAGE_NAME );
      IFileInfo propertiesInfo = propertiesStore.fetchInfo();
      boolean result = propertiesInfo.exists();
      if( result ) {
        this.s3AWSServiceCreatorFileStore = fromFileStore;
        return true;
      }
    }
    return false;
  }

  public String getExtensionID() {
    return S3AWSServiceCreator.EXTENSION_ID;
  }

  /**
   * @return the serviceName
   */
  public String getServiceName() {
    return this.serviceName;
  }

  /**
   * @param serviceName the serviceName to set
   */
  public void setServiceName( final String serviceName ) {
    this.serviceName = serviceName;
  }

  public String getName() {
    return this.serviceName;
  }

  public String getServiceURL() {
    return this.s3Url;
  }

  public void setName( final String name ) {
    this.serviceName = name;

  }

  public void setServiceURL( final String url ) {
    this.s3Url = url;
  }
}
