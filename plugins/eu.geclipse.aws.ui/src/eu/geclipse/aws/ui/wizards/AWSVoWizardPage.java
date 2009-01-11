package eu.geclipse.aws.ui.wizards;

import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.aws.IAWSService;
import eu.geclipse.aws.ui.Messages;
import eu.geclipse.aws.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.aws.vo.AWSVoCreator;
import eu.geclipse.aws.vo.AWSVoProperties;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This {@link WizardPage} provides the form elements to input the details of a
 * new {@link AWSVirtualOrganization}. It also creates the
 * {@link AWSVirtualOrganization} in the {@link #createVo()} method.
 * Additionally a list of configured {@link IAWSService} is displayed
 * 
 * @author Moritz Post
 * @see AWSVoCreator
 */
public class AWSVoWizardPage extends WizardPage {

  /** Id of this Wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ui.awsVoWizardPage"; //$NON-NLS-1$

  /** The name of this {@link AWSVirtualOrganization}. */
  private Text voNameText;

  /** Text widget to hold the aws access id. */
  private Text awsAccessIdText;

  /** The initial {@link AWSVirtualOrganization} to take data from. */
  private AWSVirtualOrganization initialVo;

  /** The table listing the installed services. */
  private Table tableServices;

  /** A list of configured grid elements implementing the {@link IAWSService}. */
  private List<IConfigurationElement> configurationElements;

  /**
   * This default constructor creates a new instance of this
   * {@link AWSVoWizardPage} and sets up the page decoration.
   */
  protected AWSVoWizardPage() {
    super( AWSVoWizardPage.WIZARD_PAGE_ID,
           Messages.getString( "AWSVoWizardPage.page_title" ), null ); //$NON-NLS-1$ 
    setDescription( Messages.getString( "AWSVoWizardPage.page_description" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/vomsvo_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );

    setPageComplete( false );
  }

  public void createControl( final Composite parent ) {
    GridData gData;

    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );

    // general vo information
    Group settingsGroup = new Group( mainComp, SWT.NONE );
    settingsGroup.setLayout( new GridLayout( 2, false ) );
    settingsGroup.setText( Messages.getString( "AWSVoWizardPage.label_vo_settings" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    settingsGroup.setLayoutData( gData );

    Label nameLabel = new Label( settingsGroup, SWT.LEFT );
    nameLabel.setText( Messages.getString( "AWSVoWizardPage.label_vo_name" ) ); //$NON-NLS-1$
    nameLabel.setLayoutData( new GridData() );

    this.voNameText = new Text( settingsGroup, SWT.LEFT
                                               | SWT.SINGLE
                                               | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.voNameText.setLayoutData( gData );
    this.voNameText.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateInput();
      }

    } );

    Label awsAccessIdLabel = new Label( settingsGroup, SWT.LEFT );
    awsAccessIdLabel.setText( Messages.getString( "AWSVoWizardPage.label_aws_access_id" ) ); //$NON-NLS-1$
    awsAccessIdLabel.setLayoutData( new GridData() );

    this.awsAccessIdText = new Text( settingsGroup, SWT.LEFT
                                                    | SWT.SINGLE
                                                    | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.awsAccessIdText.setLayoutData( gData );
    this.awsAccessIdText.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateInput();
      }

    } );

    // general vo information
    Group servicesGroup = new Group( mainComp, SWT.NONE );
    servicesGroup.setLayout( new GridLayout( 2, false ) );
    servicesGroup.setText( Messages.getString( "AWSVoWizardPage.group_title_services" ) ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.grabExcessVerticalSpace = true;
    servicesGroup.setLayoutData( gData );

    this.tableServices = new Table( servicesGroup, SWT.BORDER
                                                   | SWT.V_SCROLL
                                                   | SWT.H_SCROLL
                                                   | SWT.SINGLE
                                                   | SWT.FULL_SELECTION );
    this.tableServices.setLinesVisible( true );
    this.tableServices.setHeaderVisible( true );

    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.tableServices.setLayoutData( gData );

    TableColumn tableColumnService = new TableColumn( this.tableServices,
                                                      SWT.NONE );
    tableColumnService.setText( Messages.getString( "AWSVoWizardPage.table_services_column_service_title" ) ); //$NON-NLS-1$
    tableColumnService.setWidth( 200 );
    TableColumn tableColumnUrl = new TableColumn( this.tableServices, SWT.NONE );
    tableColumnUrl.setText( Messages.getString( "AWSVoWizardPage.table_services_column_url_title" ) ); //$NON-NLS-1$
    tableColumnUrl.setWidth( 150 );

    setControl( mainComp );

    // set initial values
    if( this.initialVo != null ) {
      try {
        AWSVoProperties properties = this.initialVo.getProperties();
        if( properties != null ) {
          this.voNameText.setText( this.initialVo.getName() );
          this.voNameText.setEnabled( false );
          this.awsAccessIdText.setText( properties.getAwsAccessId() );
        }
      } catch( ProblemException problemEx ) {
        Activator.log( "Could not load properties from initial AWS Vo", //$NON-NLS-1$
                       problemEx );
      }
    } else {
      this.voNameText.setEnabled( true );
    }

    // populate service table
    try {
      populateServiceTable( this.initialVo );
      tableColumnUrl.pack();
      tableColumnService.pack();
    } catch( InvalidRegistryObjectException invalidRegObjEx ) {
      Activator.log( "Could not populate services table", invalidRegObjEx ); //$NON-NLS-1$
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not populate services table from stored definitons", //$NON-NLS-1$
                     problemEx );
    }
  }

  /**
   * If the {@link AWSVirtualOrganization} provided is not <code>null</code>
   * the {@link #tableServices} table is filled with the present
   * {@link IAWSService} implementations. Otherwise the {@link #tableServices}
   * is populated with all Elements registered in the plugin register, which are
   * able to provide an IAWSService.
   * 
   * @param awsVo the vo to get {@link IAWSService} implementation from
   * @throws InvalidRegistryObjectException when the registry could not be
   *             accessed
   * @throws ProblemException when accessing the grid model caused problems
   */
  private void populateServiceTable( final AWSVirtualOrganization awsVo )
    throws InvalidRegistryObjectException, ProblemException
  {
    if( awsVo == null ) {
      this.configurationElements = GridModel.getCreatorRegistry().getConfigurations( null, IAWSService.class );
      String url = null;
      for( IConfigurationElement element : this.configurationElements ) {
        String name = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE );
        IConfigurationElement[] sourceChildern = element.getChildren( Extensions.GRID_ELEMENT_CREATOR_SOURCE_ELEMENT );

        for( IConfigurationElement sourceElement : sourceChildern ) {
          String defaultSource = sourceElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_DEFAULT_ATTRIBUTE );
          boolean isDefaultSource = Boolean.parseBoolean( defaultSource );
          if( isDefaultSource ) {
            url = sourceElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_PATTERN_ATTRIBUTE );
          }
        }

        TableItem tableItem = new TableItem( this.tableServices, SWT.NONE );
        tableItem.setData( element );
        tableItem.setText( new String[]{
          name, url
        } );
      }
    } else {
      List<IAWSService> awsServices = awsVo.getChildren( new NullProgressMonitor(),
                                                         IAWSService.class );
      for( IAWSService service : awsServices ) {
        TableItem tableItem = new TableItem( this.tableServices, SWT.NONE );
        tableItem.setData( service );
        tableItem.setText( new String[]{
          service.getName(), service.getHostName()
        } );
      }
    }
  }

  /**
   * Check if the input of the form is valid.
   */
  protected void validateInput() {
    String name = this.voNameText.getText().trim();
    if( name.length() == 0 ) {
      setErrorMessage( Messages.getString( "AWSVoWizardPage.error_vo_name_required" ) ); //$NON-NLS-1$
      setPageComplete( false );
    } else if ( ( this.initialVo == null )
        && ( GridModel.getVoManager().findChild( name ) != null ) ) {
      setErrorMessage( Messages.getString( "AWSVoWizardPage.error_vo_already_exists" ) ); //$NON-NLS-1$
      setPageComplete( false );
    } else if( this.awsAccessIdText.getText().trim().length() == 0 ) {
      setErrorMessage( Messages.getString( "AWSVoWizardPage.error_acces_id_required" ) ); //$NON-NLS-1$
      setPageComplete( false );
    } else {
      setErrorMessage( null );
      setPageComplete( true );
    }
  }

  /**
   * Set the initial {@link AWSVirtualOrganization} to populate the form fields.
   * 
   * @param initialVo the vo to take the initial values from
   */
  public void setInitialVo( final AWSVirtualOrganization initialVo ) {
    this.initialVo = initialVo;
  }

  /**
   * Create either a new AWS VO from the settings of this wizard page or change
   * the settings of the initial VO. If a new VO is created this VO is directly
   * added to the {@link IVoManager}.
   * 
   * @return True if the process was successful, false otherwise.
   */
  protected boolean createVo() {

    // create new vo creator
    AWSVoCreator voCreator = new AWSVoCreator();
    voCreator.setVoName( this.voNameText.getText().trim() );
    voCreator.setAwsAccessId( this.awsAccessIdText.getText().trim() );

    // get voManager to create new vo via voCreator
    IVoManager voManager = GridModel.getVoManager();
    AWSVirtualOrganization awsVo = null;

    // create a new vo with the given manager and vo creator
    if( this.initialVo == null ) {
      try {
        // create new awsVo
        awsVo = ( AWSVirtualOrganization )voManager.create( voCreator );
      } catch( ProblemException problemEx ) {
        Activator.log( "Could not create AWSVo with provided AWSVoCreator via VOManager", //$NON-NLS-1$
                       problemEx );

        // cleanup and present error message
        try {
          voManager.delete( awsVo );
        } catch( ProblemException problemExDel ) {
          ProblemDialog.openProblem( this.getShell(),
                                     Messages.getString( "AWSVoWizardPage.error_dialog_title" ), //$NON-NLS-1$
                                     Messages.getString( "AWSVoWizardPage.error_dialog_description" ), //$NON-NLS-1$
                                     problemExDel );
          Activator.log( "Could not delete rudimentary AWSVo", problemEx ); //$NON-NLS-1$
        }
        return false;
      }
    } else {
      // work with existing vo
      voCreator.apply( this.initialVo );
    }

    return true;
  }
}
