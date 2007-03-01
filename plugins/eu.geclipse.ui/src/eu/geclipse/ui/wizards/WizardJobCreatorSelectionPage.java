package eu.geclipse.ui.wizards;

import java.util.List;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.core.model.IGridJobCreator;


public class WizardJobCreatorSelectionPage extends WizardPage {
  
  private JobSubmissionWizard parent;
  
  private Combo selectionCombo;
  
  protected WizardJobCreatorSelectionPage( final JobSubmissionWizard parent ) {
    super( "jobCreationPage", "Job Creation", null );
    this.parent = parent;
    setDescription( "Select the root directory where the jobs should be created" );
  }
  
  public IGridJobCreator getSelectedCreator() {
    IGridJobCreator result = null;
    String selection = this.selectionCombo.getText();
    List<IGridJobCreator> jobCreators = this.parent.getJobCreators();
    for ( IGridJobCreator creator : jobCreators ) {
      if ( creator.getJobLabel().equals( selection ) ) {
        result = creator;
        break;
      }
    }
    return result;
  }

  public void createControl( final Composite parentComp ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parentComp, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label selectionLabel = new Label( mainComp, SWT.NONE );
    selectionLabel.setText( "Choose the type of the jobs:" );
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    selectionLabel.setLayoutData( gData );
    
    this.selectionCombo = new Combo( mainComp, SWT.READ_ONLY );
    gData = new GridData();
    this.selectionCombo.setLayoutData( gData );
    
    List<IGridJobCreator> jobCreators = this.parent.getJobCreators();
    for ( IGridJobCreator creator : jobCreators ) {
      this.selectionCombo.add( creator.getJobLabel() );
    }
    this.selectionCombo.select( 0 );
    
    setControl( mainComp );
    
  }
  
}
