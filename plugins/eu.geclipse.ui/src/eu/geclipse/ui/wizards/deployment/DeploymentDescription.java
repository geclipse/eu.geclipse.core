package eu.geclipse.ui.wizards.deployment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;

/**
 * @author Yifan Zhou
 *
 */
public class DeploymentDescription extends WizardPage {

  /**
   * Create a combo for choosing the appropriate referenced project.
   */
  private Combo tagOfReferencedProject;
  /**
   * Create a text for inputing a version, the default is v1.0.
   */
  private Text textOfVersion;
  /**
   * Create a text for inputing a tag, the default was created by date.
   */
  private Text textOfTag;
  /**
   * Product a tag by using the current system's time or its date.
   */
  private Calendar calendar;
  /**
   * Store the tag for the project respectively.
   */
  private Map<String, String> mapOfTags;

  /**
   * Create a wizard page.
   * 
   * @param pageName The name of the page.
   */
  protected DeploymentDescription( final String pageName ) {
    super( pageName );
    this.setDescription( Messages.getString( "DeploymentSource.deployment_description_description" ) ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "DeploymentSource.deployment_description_title" ) ); //$NON-NLS-1$
    this.mapOfTags = new HashMap<String, String>();
  }

  /**
   * Page initialization.
   * 
   * @param parent The current page.
   */
  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new GridLayout( 4, false ) );
    GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
    gridData.horizontalSpan = 3;
    Label labelOfName = new Label( composite, SWT.NONE );
    labelOfName.setText( Messages.getString( "DeploymentSource.deployment_description_name_label" ) ); //$NON-NLS-1$
    this.tagOfReferencedProject = new Combo( composite, SWT.BORDER | SWT.READ_ONLY );
    this.tagOfReferencedProject.setLayout( new FillLayout( SWT.HORIZONTAL ) );
    this.tagOfReferencedProject.setLayoutData( gridData );
    this.tagOfReferencedProject.addModifyListener( new ComponentModifyListener() );
    Label labelOfVersion = new Label( composite, SWT.NONE );
    labelOfVersion.setText( Messages.getString( "DeploymentSource.deployment_description_version_label" ) ); //$NON-NLS-1$
    this.textOfVersion = new Text( composite, SWT.BORDER );
    this.textOfVersion.setLayoutData( gridData );
    this.textOfVersion.addModifyListener( new ComponentModifyListener() );
    Label labelOfTag = new Label( composite, SWT.NONE );
    labelOfTag.setText( Messages.getString( "DeploymentSource.deployment_description_tag_label" ) ); //$NON-NLS-1$
    this.textOfTag = new Text( composite, SWT.BORDER );
    this.textOfTag.setLayoutData( new GridData( GridData.FILL,
                                           GridData.FILL,
                                           true,
                                           false,
                                           1,
                                           1 ) );
    this.textOfTag.addModifyListener( new ComponentModifyListener() );
    Button byDateButton = new Button( composite, SWT.RADIO | SWT.FLAT );
    byDateButton.setText( Messages.getString( "DeploymentSource.deployment_description_tag_by_date" ) ); //$NON-NLS-1$
    byDateButton.setSelection( true );
    byDateButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        DeploymentDescription.this.getTextOfTag().setText( createDateTag() );
      }
    } );
    Button byTimeButton = new Button( composite, SWT.RADIO | SWT.FLAT );
    byTimeButton.setText( Messages.getString( "DeploymentSource.deployment_description_tag_by_time" ) ); //$NON-NLS-1$
    byTimeButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        DeploymentDescription.this.getTextOfTag().setText( createTimeTag() );
      }
    } );
    this.setControl( composite );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs#setVisible( boolean visible )
   */
  @Override
  public void setVisible( final boolean visible )
  {
    if( visible ) {
      this.initContent();
    }
    super.setVisible( visible );
  }

  /**
   * Content initialization.
   */
  private void initContent() {
    this.textOfVersion.setText( Messages.getString( "DeploymentSource.deployment_description_version_number" ) ); //$NON-NLS-1$
    this.textOfTag.setText( this.createDateTag() );
    this.mapOfTags.clear();
    if( this.tagOfReferencedProject != null
        && this.tagOfReferencedProject.getItemCount() > 0 )
    {
      this.tagOfReferencedProject.removeAll();
    }
    IGridElement[] referencedProjects = ( ( DeploymentWizard )this.getWizard() ).getReferencedProjects();
    for( IGridElement referencedProject : referencedProjects ) {
      if( isSelected( referencedProject ) ) {
        this.tagOfReferencedProject.add( referencedProject.getName() );
        this.mapOfTags.put( referencedProject.getName(),
                            referencedProject.getName()
                                + "_" //$NON-NLS-1$
                                + this.textOfVersion.getText()
                                + "_" //$NON-NLS-1$
                                + this.textOfTag.getText() );
      }
    }
  }

  /**
   * If the referenced project has checked elements.
   * 
   * @param inputELement The root element of referenced project.
   * @return boolean.
   */
  private boolean isSelected( final IGridElement inputELement ) {
    boolean selected = false;
    if( ( ( DeploymentSource )this.getWizard().getStartingPage() ).getSourceTree()
      .getChecked( inputELement ) )
    {
      selected = true;
    } else {
      if( inputELement instanceof IGridContainer ) {
        try {
          IGridElement[] elements = ( ( IGridContainer )inputELement ).getChildren( null );
          for( IGridElement element : elements ) {
            if( isSelected( element ) ) {
              selected = true;
            }
          }
        } catch( GridModelException e ) {
          Activator.logException( e );
        }
      }
    }
    return selected;
  }

  /**
   * Create a tag by time.
   * 
   * @return the time string.
   */
  public String createTimeTag() {
    this.calendar = new GregorianCalendar();
    return "" //$NON-NLS-1$
           + this.calendar.get( Calendar.HOUR_OF_DAY )
           + "_" //$NON-NLS-1$
           + this.calendar.get( Calendar.MINUTE )
           + "_" //$NON-NLS-1$
           + this.calendar.get( Calendar.SECOND );
  }

  /**
   * Create a tag by date.
   * 
   * @return Yifan Zhou.
   */
  public String createDateTag() {
    DateFormatSymbols symbols = new DateFormatSymbols();
    this.calendar = new GregorianCalendar();
    return "" //$NON-NLS-1$
           + this.calendar.get( Calendar.DAY_OF_MONTH )
           + "_" //$NON-NLS-1$
           + symbols.getShortMonths()[ this.calendar.get( Calendar.MONTH ) ]
           + "_" //$NON-NLS-1$
           + this.calendar.get( Calendar.YEAR );
  }

  /**
   * Store all tags.
   * 
   * @return the stored tags.
   */
  public Map<String, String> getMapOfTags() {
    return this.mapOfTags;
  }
  
  /**
   * @return the text of tag.
   */
  public Text getTextOfTag() {
    return this.textOfTag;
  }
  
  /**
   * @return the text of version.
   */
  public Text getTextOfVersion() {
    return this.textOfVersion;
  }
  
  /**
   * @return the combo box.
   */
  public Combo getTagOfReferencedProject() {
    return this.tagOfReferencedProject;
  }
  /**
   * Create a modify listener for some components.
   * 
   * @author Yifan Zhou.
   */
  class ComponentModifyListener implements ModifyListener {

    public void modifyText( final ModifyEvent arg0 ) {
      int index = DeploymentDescription.this.getTagOfReferencedProject().getSelectionIndex();
      if( index != -1 ) {
        String projectName = DeploymentDescription.this.getTagOfReferencedProject().getItem( index );
        if( DeploymentDescription.this.getMapOfTags().containsKey( projectName ) ) {
          DeploymentDescription.this.getMapOfTags().remove( projectName );
        }
        DeploymentDescription.this.getMapOfTags().put( projectName, projectName
                                    + "_" //$NON-NLS-1$
                                    + DeploymentDescription.this.getTextOfVersion().getText()
                                    + "_" //$NON-NLS-1$
                                    + DeploymentDescription.this.getTextOfTag().getText() );
      }
    }
  }
}
