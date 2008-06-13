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

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.s3.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;

/**
 * @author Moritz Post
 */
public class S3AWSService extends AbstractGridContainer
  implements IAWSService, IStorableElement
{

  /** Name to reference the service. */
  public static final String STORAGE_NAME = "eu.geclipse.aws.s3.service.s3AWSServiceCreator"; //$NON-NLS-1$

  /** The {@link AWSVirtualOrganization} this service is based on. */
  private AWSVirtualOrganization awsVo;

  /**
   * Create a new {@link S3AWSService} with the service creator acting as the
   * source of initial configuration directives.
   * 
   * @param serviceCreator the initial configuration
   * @param vo the Vo to use for authentication
   */
  public S3AWSService( final S3AWSServiceCreator serviceCreator,
                       final AWSVirtualOrganization vo )
  {
    this.awsVo = vo;
    try {
      apply( serviceCreator );
      addElement( new S3InfoService( this ) );
    } catch( GridModelException e ) {
      Activator.log( "Could not populate S3AWSAService with data from provided S3 service creator", //$NON-NLS-1$
                     e );
    }
  }

  /**
   * Creates a new {@link S3AWSService} where the properties are loaded from the
   * local storage.
   * 
   * @param vo the Vo to use for authentication
   */
  public S3AWSService( final AWSVirtualOrganization vo ) {
    this.awsVo = vo;
    try {
      S3ServiceProperties serviceProperties = new S3ServiceProperties( this );
      serviceProperties.load();
      addElement( serviceProperties );

      addElement( new S3InfoService( this ) );
    } catch( GridModelException e ) {
      Activator.log( "Could not load the s3 service details from the filestore", //$NON-NLS-1$
                     e );
    }
  }

  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof S3ServiceProperties
           || element instanceof S3InfoService;
  }

  /**
   * This Method transfers the name and the properties of the
   * {@link S3AWSServiceCreator} to the {@link S3AWSService}.
   * 
   * @param serviceCreator the {@link S3AWSServiceCreator} to apply the data
   *            from
   * @throws GridModelException
   */
  public void apply( final S3AWSServiceCreator serviceCreator )
    throws GridModelException
  {
    S3ServiceProperties properties = new S3ServiceProperties( this,
                                                              serviceCreator );
    // add properties to this service, replacing the existing properties
    addElement( properties );
  }

  public String getHostName() {
    S3ServiceProperties properties = null;
    try {
      properties = getProperties();
    } catch( GridModelException e ) {
      Activator.log( "Could not load the properties of the s3 service", e ); //$NON-NLS-1$
    }
    if( properties != null ) {
      return properties.getS3Url();
    }
    return null;
  }

  public URI getURI() {
    String hostName = getHostName();
    try {
      if( hostName != null ) {
        return new URI( hostName );
      }
    } catch( URISyntaxException e ) {
      Activator.log( "Could not create S3 service URI from " + hostName, e ); //$NON-NLS-1$
    }
    return null;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( S3AWSService.STORAGE_NAME );
  }

  public String getName() {
    S3ServiceProperties properties = null;
    try {
      properties = getProperties();
    } catch( GridModelException gridModelEx ) {
      Activator.log( "Could not load the properties of the s3 service", gridModelEx ); //$NON-NLS-1$
    }
    if( properties != null ) {
      return properties.getServiceName();
    }
    return null;
  }

  public IGridContainer getParent() {
    return this.awsVo;
  }

  public IPath getPath() {
    return getParent().getPath().append( S3AWSService.STORAGE_NAME );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }

  public boolean isLazy() {
    return false;
  }

  @Override
  public boolean equals( final Object obj ) {
    boolean result = false;
    if( obj instanceof S3AWSService ) {
      result = equals( ( S3AWSService )obj );
    }
    return result;
  }

  /**
   * A comparison method using the {@link S3AWSService#awsVo} as comparison
   * criteria.
   * 
   * @param service the {@link S3AWSService} to compare with
   * @return if the two services are equal in regards to their name
   */
  private boolean equals( final S3AWSService service ) {
    return getName().equals( service.getName() );
  }

  /**
   * Find the {@link S3ServiceProperties} in the list of children of this
   * service.
   * 
   * @return this services properties or <code>null</code> if no properties
   *         could be retrieved
   * @throws GridModelException Thrown if an error occurs while fetching the
   *             properties.
   */
  public S3ServiceProperties getProperties() throws GridModelException {
    S3ServiceProperties properties = null;
    IGridElement[] children = getChildren( null );

    for( IGridElement child : children ) {
      if( child instanceof S3ServiceProperties ) {
        properties = ( S3ServiceProperties )child;
        break;
      }
    }
    return properties;
  }

  public IGridInfoService getInfoService() {
    IGridInfoService infoService = null;
    try {
      IGridElement[] children = getChildren( null );
      for( IGridElement child : children ) {
        if( child instanceof IGridInfoService ) {
          infoService = ( S3InfoService )child;
          break;
        }
      }
    } catch( GridModelException gridModelEx ) {
      Activator.log( "Could not get info service from EC2Service", gridModelEx ); //$NON-NLS-1$
    }
    return infoService;
  }

  public void load() throws GridModelException {
    deleteAll();

    addElement( new S3InfoService( this ) );
    IFileStore fileStore = getFileStore();
    try {
      IFileStore[] childStores = fileStore.childStores( EFS.NONE, null );
      for( IFileStore child : childStores ) {

        if( child.getName().equals( S3ServiceProperties.STORAGE_NAME ) ) {
          S3ServiceProperties serviceProperties = new S3ServiceProperties( this );
          serviceProperties.load();
          addElement( serviceProperties );
        }
      }
    } catch( CoreException cExc ) {
      throw new GridModelException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                    cExc,
                                    Activator.PLUGIN_ID );
    }
  }

  public void save() throws GridModelException {
    // create own storage directory
    IFileStore fileStore = getFileStore();
    if( !fileStore.fetchInfo().exists() ) {
      try {
        fileStore.mkdir( EFS.NONE, new NullProgressMonitor() );
      } catch( CoreException e ) {
        Activator.log( "Could not create storage dir for s3Service", e ); //$NON-NLS-1$
        return;
      }
    }

    // save children
    IGridElement[] children = getChildren( null );
    for( IGridElement child : children ) {
      if( child instanceof IStorableElement ) {
        ( ( IStorableElement )child ).save();
      }
    }
  }

}
