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

package eu.geclipse.aws.vo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.IAWSServiceCreator;
import eu.geclipse.aws.internal.Activator;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.ICreatorSourceMatcher;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * This {@link IGridElementCreator} is able to create an
 * {@link AWSVirtualOrganization} from the input provided via its setter
 * methods. The currently required element is the {@link #voName} to identify
 * the {@link AWSVirtualOrganization}. This input value is usually provided by
 * the user via some wizard page.
 * <p>
 * To make this {@link AWSVoCreator} available to the geclipse infrastructure
 * the extension point <code>eu.geclipse.core.gridElementCreator</code> is
 * extended.
 * 
 * @author Moritz Post
 * @see AWSVirtualOrganization
 * @see AWSVoProperties
 */
public class AWSVoCreator
    extends AbstractGridElementCreator
    implements ICreatorSourceMatcher {

  /**
   * The list of {@link IAWSServiceCreator}s which are able to create an
   * {@link IAWSService}.
   */
  private static List<IAWSServiceCreator> serviceCreatorList;

  /** The creators extension ID. */
  private static final String EXTENSION_ID = "eu.geclipse.aws.vo.awsVoCreator"; //$NON-NLS-1$

  /**
   * The name of the {@link IVirtualOrganization} which this
   * {@link IGridElementCreator} is able to create.
   */
  private String voName;

  /**
   * The AWS access id associated the {@link IVirtualOrganization} with an AWS
   * account.
   */
  private String awsAccessId;

  /**
   * Default constructor used for initialization.
   */
  public AWSVoCreator() {
    // nothing to do here
  }

  public boolean canCreate( final Object source ) {
      
    boolean result = false;

    if ( source instanceof IFileStore ) {
      IFileStore propertiesStore = ( ( IFileStore ) source ).getChild( AWSVoProperties.STORAGE_NAME );
      IFileInfo propertiesInfo = propertiesStore.fetchInfo();
      result = propertiesInfo.exists();
    }

    return result;

  }

  public IGridElement create( final IGridContainer parent )
    throws ProblemException
  {
    AWSVirtualOrganization vo = null;
    Object source = getSource();
    if ( source == null ) {
      vo = new AWSVirtualOrganization( this );
    } else if ( source instanceof IFileStore ){
      vo = new AWSVirtualOrganization( ( IFileStore ) source );
    }
    return vo;
  }

  /**
   * This static method retrieves a list of all installed
   * {@link IGridElementCreator}s (actually {@link IAWSServiceCreator}s),
   * which are able to produce {@link IAWSService} objects.
   * <p>
   * Additionally the {@link IAWSServiceCreator}s are initialized with the name
   * and default source pattern attribute as declared in the
   * <code>eu.geclipse.core.gridElementCreator</code> extension point.
   * 
   * @return the list of configured and instantiated {@link IAWSServiceCreator}s
   *         or an empty {@link List}
   */
  public static synchronized List<IAWSServiceCreator> getAWSServiceCreators() {
    if( AWSVoCreator.serviceCreatorList == null ) {
      AWSVoCreator.serviceCreatorList = new ArrayList<IAWSServiceCreator>();

      // add registered IAwsServices to creator
      IAWSServiceCreator serviceCreator;
      List<IConfigurationElement> configurationElements
        = GridModel.getCreatorRegistry().getConfigurations( null, IAWSService.class );
      for( IConfigurationElement configElem : configurationElements ) {
        try {
          // create element creator
          serviceCreator = ( IAWSServiceCreator )configElem.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
          IConfigurationElement[] sourceChildern = configElem.getChildren( Extensions.GRID_ELEMENT_CREATOR_SOURCE_ELEMENT );

          // fetch default source element
          for( IConfigurationElement sourceElement : sourceChildern ) {
            String defaultSource = sourceElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_DEFAULT_ATTRIBUTE );
            boolean isDefaultSource = Boolean.parseBoolean( defaultSource );
            if( isDefaultSource ) {
              // set properties on creator
              serviceCreator.setServiceURL( sourceElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_PATTERN_ATTRIBUTE ) );
              serviceCreator.setName( configElem.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE ) );
            }
          }
          AWSVoCreator.serviceCreatorList.add( serviceCreator );
        } catch( CoreException coreEx ) {
          Activator.log( "Could not create AWS service creator from extension definition: " //$NON-NLS-1$
                             + configElem.getValue(),
                         coreEx );
        } catch( ClassCastException castEx ) {
          Activator.log( "Given creator is not an instance of IAWSServiceCreator: " //$NON-NLS-1$
                             + configElem.getValue(),
                         castEx );
        }
      }
    }
    return AWSVoCreator.serviceCreatorList;
  }

  /**
   * A getter for the {@link #voName}.
   * 
   * @return the name of future {@link IVirtualOrganization}s
   */
  public String getVoName() {
    return this.voName;
  }

  /**
   * A setter for the {@link #voName}.
   * 
   * @param voName the name to be set for future {@link IVirtualOrganization}s
   */
  public void setVoName( final String voName ) {
    this.voName = voName;
  }

  /**
   * Transfers the details ({@link #voName} of this {@link AWSVoCreator} to the
   * provided {@link AWSVirtualOrganization}.
   * 
   * @param awsVo the {@link AWSVirtualOrganization} to populate
   */
  public void apply( final AWSVirtualOrganization awsVo ) {
    try {
      awsVo.apply( this );
    } catch ( ProblemException problemEx ) {
      Activator.log( "Could not populate AWSVirtualOrganization with the data from the AWSVoCreator", //$NON-NLS-1$
                     problemEx );
    }
  }

  public String getExtensionID() {
    return AWSVoCreator.EXTENSION_ID;
  }

  /**
   * A getter for the {@link #awsAccessId}.
   * 
   * @return the awsAccessId
   */
  public String getAwsAccessId() {
    return this.awsAccessId;
  }

  /**
   * A setter for the {@link #awsAccessId}.
   * 
   * @param awsAccessId the awsAccessId to set
   */
  public void setAwsAccessId( final String awsAccessId ) {
    this.awsAccessId = awsAccessId;
  }
}
