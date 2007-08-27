package eu.geclipse.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.model.impl.GenericVirtualOrganization;
import eu.geclipse.core.model.impl.GenericVoCreator;

public class GenericVoWizardPage
    extends WizardPage {
  
  private GenericVirtualOrganization initialVo;
  
  private Text nameText;
  
  /**
   * Standard constructor.
   */
  public GenericVoWizardPage() {
    super( "genericVOPage", //$NON-NLS-1$
           "Generic VO",
           null );
    setDescription( "Specify the attributes of your VO" );
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label nameLabel = new Label( mainComp, SWT.NULL );
    nameLabel.setText( "VO Name:" );
    gData = new GridData();
    nameLabel.setLayoutData( gData );
    
    this.nameText = new Text( mainComp, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.nameText.setLayoutData( gData );
    
    if ( this.initialVo != null ) {
      initVo( this.initialVo );
    }
    
    setControl( mainComp );
    
  }
  
  protected boolean createVo() {
    
    String errorMessage = null;
    GenericVirtualOrganization vo = null;
    IVoManager manager = GridModel.getVoManager();
    
    setErrorMessage( errorMessage );
    
    GenericVoCreator creator = getVoCreator();
    
    if ( isEmpty( creator.getVoName() ) ) {
      errorMessage = "You have to specify a valid name";
    } else {
      try {
        if ( this.initialVo != null ) {
          creator.apply( this.initialVo );
        } else {
          vo = ( GenericVirtualOrganization ) manager.create( creator );
        }
      } catch( GridModelException gmExc ) {
        errorMessage = gmExc.getLocalizedMessage();
      }
    }
    
    if ( errorMessage != null ) {
      setErrorMessage( errorMessage );
      if ( vo != null ) {
        try {
          manager.delete( vo );
        } catch( GridModelException gmExc ) {
          // TODO mathias
        }
      }
    }
    
    return errorMessage == null;
    
  }
  
  /**
   * Initializes the controls of this wizard page with the attributes
   * of the specified VO.
   * 
   * @param vo The VO whose attributes should be set to the page's controls.
   * @throws GridModelException If any error occurs.
   */
  protected void initVo( final GenericVirtualOrganization vo ) {
    this.nameText.setText( vo != null ? vo.getName() : "" ); //$NON-NLS-1$
    this.nameText.setEnabled( vo == null );
  }
  
  /**
   * Set the specified VO as initial VO. This means that the controls
   * of the page will be initialized with the attributes of the specified
   * VO.
   * 
   * @param vo The initial VO.
   */
  protected void setInitialVo( final GenericVirtualOrganization vo ) {
    this.initialVo = vo;
  }
  
  private GenericVoCreator getVoCreator() {
    
    GenericVoCreator creator = new GenericVoCreator();
    
    String name = this.nameText.getText();
    creator.setVoName( name );
    
    return creator;
    
  }
  
  /**
   * Tests if the specified {@link String} is either <code>null</code>
   * or empty.
   * 
   * @param s The string to be tested.
   * @return True if the string is either <code>null</code>
   * or empty.
   */
  private boolean isEmpty( final String s ) {
    return ( s == null ) || ( s.length() == 0 ); 
  }

}
