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

package eu.geclipse.aws.ec2;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.ec2.internal.Activator;
import eu.geclipse.aws.ec2.service.EC2Service;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * A Base class which can be used by Grid Resources based on the AWS system.
 * 
 * @author Moritz Post
 */
public abstract class AbstractEC2GridResource extends AbstractGridElement
  implements IGridResource
{

  private IGridContainer parent;
  
  /** The underlying service gateway. */
  private EC2Service ec2Service;

  /**
   * Creates a new {@link AbstractEC2GridResource} with the given awsVo
   * 
   * @param ec2Service the {@link IAWSService} which this grid element utilizes
   */
  public AbstractEC2GridResource( final IGridContainer parent,
                                  final EC2Service ec2Service ) {
    this.parent = parent;
    this.ec2Service = ec2Service;
  }
  
  public EC2Service getEC2Service() {
    return this.ec2Service;
  }

  public String getHostName() {
    try {
      String ec2Url = this.ec2Service.getProperties().getEc2Url();
      URL url = new URL( ec2Url );
      return url.getHost();
    } catch ( ProblemException problemEx ) {
      Activator.log( "Could not obtain properties from ec2Service", problemEx ); //$NON-NLS-1$
    } catch ( MalformedURLException malformedURLEx ) {
      Activator.log( "Could not convert URL from ec2Service", malformedURLEx ); //$NON-NLS-1$
    }
    return null;
  }

  public URI getURI() {
    String ec2Url = null;
    try {
      ec2Url = this.ec2Service.getProperties().getEc2Url();
      return new URI( ec2Url );
    } catch ( ProblemException problemEx ) {
      Activator.log( "Could not obtain properties from awsVo", problemEx ); //$NON-NLS-1$
    } catch ( URISyntaxException uriSyntaxEx ) {
      Activator.log( "Could not construct URI from awsVo: " + ec2Url, //$NON-NLS-1$
                     uriSyntaxEx );
    }
    return null;
  }

  public IFileStore getFileStore() {
    return null;
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return null;
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return false;
  }

}
