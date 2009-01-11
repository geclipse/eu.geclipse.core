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

package eu.geclipse.aws.ec2.service;

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
 * The {@link EC2ServiceCreator} is the factory class for creating the
 * {@link EC2Service}. Its default configuration directives are obtained from
 * the declaration in the <code>plugin.xml</code> file.
 * 
 * @author Moritz Post
 * @see EC2Service
 */
public class EC2ServiceCreator extends AbstractGridElementCreator
  implements IAWSServiceCreator
{

  /** The creators extension ID. */
  private static final String EXTENSION_ID = "eu.geclipse.aws.ec2.service.ec2ServiceCreator"; //$NON-NLS-1$

  /**
   * An {@link IFileStore} handle containing this {@link AWSVoCreator} and its
   * children.
   */
  private IFileStore ec2ServiceCreatorFileStore;

  /** The URL to the Amazon Elastic Computing Cloud service. */
  private String ec2Url;

  /** The name of the service which this {@link IGridElementCreator} can create. */
  private String serviceName;

  public IGridElement create( final IGridContainer parent )
    throws ProblemException
  {
    EC2Service ec2Service = null;
    if( parent instanceof AWSVirtualOrganization ) {
      AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )parent;
      if( this.ec2ServiceCreatorFileStore == null ) {
        ec2Service = new EC2Service( this, awsVo );
      } else {
        ec2Service = new EC2Service( awsVo );
      }
    }
    return ec2Service;
  }

  public String getExtensionID() {
    return EC2ServiceCreator.EXTENSION_ID;
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
    return this.ec2Url;
  }

  public void setName( final String name ) {
    this.serviceName = name;

  }

  public void setServiceURL( final String url ) {
    this.ec2Url = url;
  }

}
