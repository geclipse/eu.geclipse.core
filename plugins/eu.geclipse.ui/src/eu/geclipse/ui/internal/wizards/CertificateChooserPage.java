package eu.geclipse.ui.internal.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateLoader;
import eu.geclipse.core.security.ICertificateLoader.CertificateID;
import eu.geclipse.ui.dialogs.ProblemDialog;


public class CertificateChooserPage extends WizardPage {

  protected CheckboxTableViewer viewer;

  private CertificateLoaderSelectionPage selectionPage; 
  
  public CertificateChooserPage( final CertificateLoaderSelectionPage selectionPage ) {
    super( "certificateChooserPage",
           "Choose Certificates",
           null );
    setDescription( "Choose the Certificates you would like to import" );
    this.selectionPage = selectionPage;
  }
  
  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    Table table = new Table( mainComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CHECK );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    table.setLayoutData( gData );
    
    this.viewer = new CheckboxTableViewer( table );
    this.viewer.setContentProvider( new ArrayContentProvider() );
    this.viewer.setLabelProvider( new LabelProvider() {
      @Override
      public String getText( final Object element ) {
        String result = super.getText( element );
        if ( element instanceof CertificateID ) {
          result = ( ( CertificateID ) element ).getName();
        }
        return result;
      }
    } );
    
    Composite buttonComp = new Composite( mainComp, SWT.NULL );
    buttonComp.setLayout( new GridLayout( 3, false ) );
    gData = new GridData();
    buttonComp.setLayoutData( gData );
    
    Button selectAllButton = new Button( buttonComp, SWT.PUSH );
    selectAllButton.setText( "&Select All" );
    gData = new GridData();
    selectAllButton.setLayoutData( gData );
    
    Button deselectAllButton = new Button( buttonComp, SWT.PUSH );
    deselectAllButton.setText( "&Deselect All" );
    gData = new GridData();
    deselectAllButton.setLayoutData( gData );
    
    Button revertButton = new Button( buttonComp, SWT.PUSH );
    revertButton.setText( "&Revert Selection" );
    gData = new GridData();
    revertButton.setLayoutData( gData );
    
    selectAllButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        selectAll();
      }
    } );
    
    deselectAllButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        deselectAll();
      }
    } );
    
    revertButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        revertSelection();
      }
    } );
    
    setControl( mainComp );
    
  }
  
  public CertificateID[] getSelectedCertificates() {
    
    CertificateID[] result = null;
    
    Object[] checkedElements = this.viewer.getCheckedElements();
    if ( checkedElements != null ) {
      result = new CertificateID[ checkedElements.length ];
      for ( int i = 0 ; i < checkedElements.length ; i++ ) {
        result[ i ] = ( CertificateID ) checkedElements[ i ];
      }
    }
    
    return result;
    
  }
  
  @Override
  public void setVisible( final boolean visible ) {
    super.setVisible( visible );
    if ( visible ) {
      loadCertificates();
    }
  }
  
  protected void selectAll() {
    this.viewer.setAllChecked( true );
  }
  
  protected void deselectAll() {
    this.viewer.setAllChecked( false );
  }
  
  protected void revertSelection() {
    CertificateID[] elements = ( CertificateID[] ) this.viewer.getInput();
    for ( CertificateID element : elements ) {
      boolean state = this.viewer.getChecked( element );
      this.viewer.setChecked( element, ! state );
    }
  }
  
  private void loadCertificates() {
    
    setErrorMessage( null );
    setMessage( null, WARNING );
    this.viewer.setInput( null );
    
    final ICertificateLoader loader = this.selectionPage.getSelectedLoader();
    final URI uri = this.selectionPage.getURI();
    
    setErrorMessage( this.selectionPage.getErrorMessage() );
    
    try {
      getContainer().run( true, true, new IRunnableWithProgress() {
        @SuppressWarnings("synthetic-access")
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {
          try {
            final CertificateID[] certificates
              = loader.listAvailableCertificates( uri, monitor );
            getContainer().getShell().getDisplay().syncExec( new Runnable() {
              public void run() {
                CertificateChooserPage.this.viewer.setInput( certificates );
                if ( certificates.length == 0 ) {
                  setMessage( "No certificates found at the specified location", WARNING );
                }
              }
            } );
          } catch ( ProblemException pExc ) {
            throw new InvocationTargetException( pExc );
          }
        }
      } );
    } catch( InvocationTargetException itExc ) {
      Throwable exc = itExc.getCause() == null ? itExc : itExc.getCause();
      setErrorMessage( "Unable to load certificates: " + exc.getLocalizedMessage() );
      ProblemDialog.openProblem( getShell(),
                                 "Certificate load failed",
                                 "Unable to load certificates",
                                 exc );
    } catch( InterruptedException intExc ) {
      setErrorMessage( "Unable to load certificates: " + intExc.getLocalizedMessage() );
    }
    
  }

}
