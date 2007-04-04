package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;
import eu.geclipse.ui.wizards.jobs.internal.ApplicationSpecificControlsFactory;

/**
 * @author katis
 *
 */
public class ApplicationSpecificPage extends WizardPage {

//  private Composite content;
  private Composite parent;
  private ArrayList<Text> textFieldsWithFileChooser = new ArrayList<Text>();
  // holds map with controls as a keys and parameter name as a value
  private HashMap<Control, String> controlsParametersNames = new HashMap<Control, String>();
  private String extensionPointId;
  
  private boolean rebiuld = false;
  private Composite intParentP;

  /**
   * This method must have package visibilit
   * 
   * @param pageName
   */
  public ApplicationSpecificPage( final String pageName ) {
    super( pageName );
  }

//  public void setRebuild( final boolean newRebuild ){
//    this.rebiuld = newRebuild;
//    if ( this.rebiuld ){
//      this.createControl( this.intParentP );
//    }
//  }
  
  public void createControl( final Composite parentP ) {
    this.intParentP = parentP;
    Composite mainComp = new Composite( parentP, SWT.NONE );
    GridLayout gLayout = new GridLayout( 3, false );
    mainComp.setLayout( gLayout );
    try {
      ApplicationSpecificControlsFactory factory = new ApplicationSpecificControlsFactory();
      factory.createControls( this.extensionPointId,
                              mainComp,
                              this.textFieldsWithFileChooser,
                              this.controlsParametersNames );
    } catch( SAXException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
//    this.performSpecificCreation( mainComp );
    
    this.parent = mainComp;
    setControl( mainComp );
  }

  Composite getParent() {
    return this.parent;
  }

  // extension point or - what?
  /**
   * Allows to set id of extension point that provides description of this page
   * @param id
   */
  public void setExtensionPointId( final String id ) {
    if (this.intParentP != null){
      if (this.extensionPointId != null && id != null){
        this.extensionPointId = id;
        createControl( intParentP );
      } else if ( id != null ){
        this.extensionPointId = id;
        createControl( intParentP);
      }
    }
//    if (this.extensionPointId != null && id != null){
//      if (!this.extensionPointId.equals( id ) && this.intParentP != null){
//        createControl( this.intParentP );
//      }
//    }
    this.extensionPointId = id;
    
  }

//  private void performSpecificCreation( final Composite parent ) {
//    if( this.extensionPointId != null ) {
//      Label label = new Label( parent, SWT.LEFT );
//      label.setText( this.extensionPointId );
//    }
//  }

//  public void setText( String string ) {
//    this.l.setText( string );
//  }

  /**
   * Returns map of parameter's names and their values. For one parameter there
   * may be more than one value
   * 
   * @return map of parameter's names and their values
   */
  public Map<String, ArrayList<String>> getParametersValues() {
    Map<String, ArrayList<String>> result = null;
    if( this.controlsParametersNames != null
        && !this.controlsParametersNames.isEmpty() )
    {
      result = new HashMap<String, ArrayList<String>>();
      for (Control control: this.controlsParametersNames.keySet()){
        String controlText = null;
        if( control instanceof Text ) {
          controlText = ( ( Text )control ).getText();
        } else if( control instanceof Combo ) {
          controlText = ( ( Combo )control ).getText();
        }
        if ( !result.containsKey( this.controlsParametersNames.get( control ) ) ){
          ArrayList<String> values = new ArrayList<String>();
          values.add( controlText );
          result.put( this.controlsParametersNames.get( control ), values );
        } else {
          result.get( this.controlsParametersNames.get( control ) ).add( controlText );
        }
      }
    }

    return result;
  }

}
