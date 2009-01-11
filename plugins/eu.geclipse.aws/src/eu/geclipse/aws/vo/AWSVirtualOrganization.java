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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.geclipse.aws.AWSInfoService;
import eu.geclipse.aws.IAWSCategories;
import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.IAWSServiceCreator;
import eu.geclipse.aws.internal.Activator;
import eu.geclipse.aws.internal.Messages;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.model.impl.AbstractVirtualOrganization;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;


/**
 * This {@link AWSVirtualOrganization} is an {@link IVirtualOrganization}
 * implementation for the Amazon Webservices ({@link http://www.amazonaws.com/}).
 * The VO encapsulates the {@link URL} to the webservice address and provides an
 * entry point to provide services to interact with the AWS technologies.
 * <p>
 * The {@link AWSVirtualOrganization} is created by the {@link IVoManager} and a
 * corresponding {@link AWSVoCreator}.
 * <p>
 * To facilitate physically present Amazon services, additional plugins have
 * provide an {@link IGridElement} in the form of an {@link IAWSService}. The
 * {@link AWSVirtualOrganization} acts as a container for all these Services and
 * manages their displaying in the grid project.
 * 
 * @author Moritz Post
 * @see AWSVoCreator
 * @see AWSVoProperties
 */
public class AWSVirtualOrganization extends AbstractVirtualOrganization {

  /**
   * The id of the wizard to use for VO creation as specified in the
   * <code>plugin.xml</code>.
   */
  private static final String AWS_VO_WIZARD_ID = "eu.geclipse.aws.ui.wizard.awsVoWizard"; //$NON-NLS-1$

  /**
   * The type name of this VO implementation.
   */
  private static final String VO_TYPE_NAME = Messages.getString( "AWSVirtualOrganization.vo_type_name" ); //$NON-NLS-1$

  /**
   * Name of this Virtual Organization used in the {@link IFileStore}.
   */
  private String voName;

  /** The categories published by this {@link IVirtualOrganization}. */
  public static IGridResourceCategory[] STANDARD_RESOURCE_CATEGORIES = new IGridResourceCategory[]{
    GridResourceCategoryFactory.getCategory( IAWSCategories.CATEGORY_AWS_COMPUTING ),
    GridResourceCategoryFactory.getCategory( IAWSCategories.CATEGORY_AWS_SERVICE ),
    GridResourceCategoryFactory.getCategory( IAWSCategories.CATEGORY_AWS_STORAGE )
  };

  /**
   * Standard constructor used to Instantiate VO in the wizard.
   */
  public AWSVirtualOrganization() {
    // nothing to do here
  }

  /**
   * This constructor takes an {@link IFileStore} handle which contains a
   * reference to a {@link AWSVoCreator}. The content of the creator is loaded
   * and thereby populating this {@link AWSVirtualOrganization}.
   * 
   * @param fileStore the {@link AWSVoCreator} to obtain the data from
   */
  public AWSVirtualOrganization( final IFileStore fileStore ) {
    if ( fileStore != null ) {

      this.voName = fileStore.getName();
      try {
        load();
        addElement( new AWSInfoService( this ) );
      } catch ( ProblemException e ) {
        Activator.log( "Could not load the awsVo details from the filestore", e ); //$NON-NLS-1$
      }
    } else {
      Activator.log( "Could not populate AWSVirtualOrganization with data from fileStore" //$NON-NLS-1$
                     + " since given filestore was 'null'" ); //$NON-NLS-1$
    }
  }

  /**
   * This constructor takes an {@link AWSVoCreator} with the
   * {@link AWSVirtualOrganization} specific data. The details of the
   * {@linkplain AWSVoCreator creator} are
   * {@linkplain #apply(AWSVoCreator) applied} to this
   * {@link AWSVirtualOrganization}.
   * 
   * @param voCreator the {@link AWSVoCreator} to get the data from
   */
  protected AWSVirtualOrganization( final AWSVoCreator voCreator ) {
    try {
      apply( voCreator );
      addElement( new AWSInfoService( this ) );
    } catch ( ProblemException e ) {
      Activator.log( "Could not populate awsVo with data from provided AWSVo creator", //$NON-NLS-1$
                     e );
    }
  }

  /**
   * This Method transfers the name and the properties of the
   * {@link AWSVoCreator} to the {@link AWSVirtualOrganization}.
   * 
   * @param voCreator the {@link AWSVoCreator} to apply the data from
   * @throws GridModelExc8eption arises when interaction with the
   *             {@link GridModel} fails
   */
  void apply( final AWSVoCreator voCreator ) throws ProblemException {
    this.voName = voCreator.getVoName();
    AWSVoProperties voProperties = new AWSVoProperties( this, voCreator );
    // add properties to this vo, replacing the existing props
    addElement( voProperties );

    List<IAWSServiceCreator> serviceCreators = AWSVoCreator.getAWSServiceCreators();
    for ( IAWSServiceCreator serviceCreator : serviceCreators ) {
      create( serviceCreator );
    }
  }

  @Override
  public void load() throws ProblemException {
    deleteAll();
    IFileStore fileStore = getFileStore();
    List<IConfigurationElement> configurationElements
      = GridModel.getCreatorRegistry().getConfigurations( null, IAWSService.class );
    try {
      IFileStore[] childStores = fileStore.childStores( EFS.NONE, null );

      for ( IFileStore child : childStores ) {
        IGridElement gridElement = null;
        String childName = child.getName();

        if ( childName.equals( AWSVoProperties.STORAGE_NAME ) ) {
          AWSVoProperties properties = new AWSVoProperties( this );
          properties.load();
          gridElement = properties;
        } else {
          for ( IConfigurationElement configElement : configurationElements ) {
            String creatorId = configElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
            if ( childName.equals( creatorId ) ) {
              IGridElementCreator serviceCreator = ( IGridElementCreator )configElement.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
              gridElement = serviceCreator.create( this );
              if ( gridElement instanceof IStorableElement ) {
                ( ( IStorableElement )gridElement ).load();
              }
            }
          }
        }

        if ( gridElement != null ) {
          addElement( gridElement );
        }
      }
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    }
  }

  @Override
  public IGridResourceCategory[] getSupportedCategories() {
    ArrayList<IGridResourceCategory> categoriesList = new ArrayList<IGridResourceCategory>();
    try {
      List<IAWSService> awsServices = getChildren( new NullProgressMonitor(),
                                                   IAWSService.class );

      for ( IAWSService service : awsServices ) {
        IGridResourceCategory[] supportedResources = service.getSupportedResources();
        if ( supportedResources != null ) {
          Collections.addAll( categoriesList, supportedResources );
        }
      }
    } catch ( ProblemException problemEx ) {
      Activator.log( "Could not fetch the AWSServices from the AWS VO", //$NON-NLS-1$
                     problemEx );
    }

    Collections.addAll( categoriesList,
                        AWSVirtualOrganization.STANDARD_RESOURCE_CATEGORIES );
    return categoriesList.toArray( new IGridResourceCategory[ categoriesList.size() ] );
  }

  /**
   * This method is not used. The entire loading procedure happens in
   * {@link #load()};
   */
  @Override
  protected IGridElement loadChild( final String childName ) {
    return null;
  }

  @Override
  public boolean canContain( final IGridElement element ) {
    return super.canContain( element ) || element instanceof AWSVoProperties;
  }

  public String getTypeName() {
    return AWSVirtualOrganization.VO_TYPE_NAME;
  }

  public String getWizardId() {
    return AWSVirtualOrganization.AWS_VO_WIZARD_ID;
  }

  public boolean isLazy() {
    return false;
  }

  public String getName() {
    return this.voName;
  }

  @Override
  public boolean equals( final Object obj ) {
    boolean result = false;
    if( obj instanceof AWSVirtualOrganization ) {
      result = equals( ( AWSVirtualOrganization )obj );
    }
    return result;
  }

  /**
   * A comparison method using the {@link AWSVirtualOrganization#voName} as
   * comparison criteria.
   * 
   * @param vo the {@link AWSVirtualOrganization} to compare with
   * @return if the two VOs are equal in regards to their name
   */
  private boolean equals( final AWSVirtualOrganization vo ) {
    return getName().equals( vo.getName() );
  }

  /**
   * Find the {@link AWSVoProperties} in the list of children of this VO.
   * 
   * @return This VO's properties.
   * @throws ProblemException if an error occurs while fetching the
   *             list of children.
   */
  public AWSVoProperties getProperties() throws ProblemException {
    AWSVoProperties properties = null;
    IGridElement[] children = getChildren( null );

    for ( IGridElement child : children ) {
      if ( child instanceof AWSVoProperties ) {
        properties = ( AWSVoProperties )child;
        break;
      }
    }
    return properties;
  }

  /**
   * Gets all the children in this {@link IGridContainer}, which are an
   * instance of the passed elementType.
   * <p>
   * Method is defined not to check type conversions because it is ensured via
   * {@link Class#isInstance(Object)}.
   * 
   * @param <T> the type extending the {@link IGridElement}
   * @param monitor the monitor to track progress
   * @param elementType the element type to filter by
   * @return a list of {@link IGridElement}s which are an implementation of
   *         <code>&lt;T&gt;</code> or an empty {@link List}<code>&lt;T&gt;</code>
   * @throws ProblemException when the extraction of the children was not
   *             successful
   */
  @SuppressWarnings("unchecked")
  public <T extends IGridElement> List<T> getChildren( final IProgressMonitor monitor,
                                                       final Class<T> elementType )
    throws ProblemException
  {
    IGridElement[] children = getChildren( monitor );
    List<T> childrenList = new ArrayList<T>();
    if ( elementType != null ) {
      for ( IGridElement gridElement : children ) {
        if ( elementType.isInstance( gridElement ) ) {
          childrenList.add( ( T )gridElement );
        }
      }
    }
    return childrenList;
  }

  public String getId() {
    return this.getClass().getName();
  }
}
