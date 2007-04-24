package eu.geclipse.ui.internal.preference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import eu.geclipse.core.GridException;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.SolutionRegistry;
import eu.geclipse.core.auth.CaCertManager;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.ui.UISolution;
import eu.geclipse.ui.dialogs.NewCaCertDialog;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;

public class CaCertPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
  
  protected List caList;
  
  protected Button deleteButton;
  
  private Button addFromDirButton;
  
  private Button addFromRepoButton;
  
  public CaCertPreferencePage() {
    super();
    setDescription( Messages.getString( "CaCertPreferencePage.description" ) ); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    
    initializeDialogUnits( parent );
    noDefaultAndApplyButton();
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    
    GridLayout layout= new GridLayout( 2, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    mainComp.setLayout( layout );
    
    GridData gData;
    
    Label listLabel = new Label( mainComp, SWT.NONE );
    listLabel.setText( Messages.getString( "CaCertPreferencePage.list_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    listLabel.setLayoutData( gData );
    
    this.caList = new List( mainComp, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 100;
    gData.heightHint = 150;
    this.caList.setLayoutData( gData );
    
    Composite buttons = new Composite( mainComp, SWT.NULL );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    gData.horizontalSpan = 1;
    buttons.setLayoutData( gData );
    layout = new GridLayout( 1, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    buttons.setLayout( layout );
    
    this.addFromRepoButton = new Button( buttons, SWT.PUSH );
    this.addFromRepoButton.setText( Messages.getString( "CaCertPreferencePage.add_rep_button" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addFromRepoButton.setLayoutData( gData );
    
    this.addFromDirButton = new Button( buttons, SWT.PUSH );
    this.addFromDirButton.setText( Messages.getString( "CaCertPreferencePage.add_dir_button" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addFromDirButton.setLayoutData( gData );
    
    Label separator = new Label(buttons, SWT.NONE);
    separator.setVisible(false);
    gData = new GridData();
    gData.horizontalAlignment= GridData.FILL;
    gData.heightHint= 4;
    separator.setLayoutData(gData);

    this.deleteButton = new Button( buttons, SWT.PUSH );
    this.deleteButton.setText( Messages.getString( "CaCertPreferencePage.delete_button" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.deleteButton.setLayoutData( gData );
    this.deleteButton.setEnabled( false );
    
    this.caList.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        int sCnt = CaCertPreferencePage.this.caList.getSelectionCount();
        CaCertPreferencePage.this.deleteButton.setEnabled( sCnt > 0 );
      }
    } );
    this.addFromDirButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        addFromDirectory();
      }
    } );
    this.addFromRepoButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        addFromRepository();
      }
    } );
    this.deleteButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        deleteSelected();
      }
    } );
    
    updateCaList();
    
    return mainComp;
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
  }
  
  protected void addFromDirectory() {
    NewCaCertDialog dialog = new NewCaCertDialog( NewCaCertDialog.FROM_DIRECTORY, getShell() );
    if ( dialog.open() == Window.OK ) {
      IStatus status = null;
      String dirname = dialog.getResult();
      final File dirfile = new File( dirname );
      final CaCertManager manager = CaCertManager.getManager();
      IRunnableWithProgress runnable = new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException {
          try {
            manager.importFromDirectory( dirfile, monitor );
          } catch( IOException ioExc ) {
            throw new InvocationTargetException( ioExc );
          }
        }
      };
      try {
        new ProgressMonitorDialog( getShell() ).run( true, true, runnable );
      } catch( InvocationTargetException itExc ) {
        eu.geclipse.ui.internal.Activator.logException( itExc.getCause() );
        status = new Status( IStatus.ERROR,
                             eu.geclipse.ui.internal.Activator.PLUGIN_ID,
                             IStatus.OK,
                             Messages.getString( "CaCertPreferencePage.load_cert_error" ), //$NON-NLS-1$
                             itExc.getCause() );
      } catch( InterruptedException intExc ) {
        eu.geclipse.ui.internal.Activator.logException( intExc );
        status = new Status( IStatus.ERROR,
                             eu.geclipse.ui.internal.Activator.PLUGIN_ID,
                             IStatus.OK,
                             Messages.getString( "CaCertPreferencePage.load_cert_error" ), //$NON-NLS-1$
                             intExc );
      }
      if ( status != null ) {
        ErrorDialog.openError( getShell(),
                               Messages.getString( "CaCertPreferencePage.cert_error" ), //$NON-NLS-1$
                               Messages.getString( "CaCertPreferencePage.unable_load_cert_error" ), //$NON-NLS-1$
                               status );
      }
      updateCaList();
    }
  }
  
  protected void addFromRepository() {
    NewCaCertDialog dialog = new NewCaCertDialog( NewCaCertDialog.FROM_REPOSITORY, getShell() );
    if ( dialog.open() == Window.OK ) {
      boolean updateList = true;
      final String repoString = dialog.getResult();
      try {
        final URL repoURL = new URL( repoString );
        final CaCertManager manager = CaCertManager.getManager();
        IRunnableWithProgress runnable = new IRunnableWithProgress() {
          public void run( final IProgressMonitor monitor )
              throws InvocationTargetException, InterruptedException {
            try {
              manager.importFromRepository( repoURL, monitor );
            } catch( GridException gExc ) {
              throw new InvocationTargetException( gExc );
            }
          }
        };
        new ProgressMonitorDialog( getShell() ).run( true, true, runnable );
      } catch( MalformedURLException muExc ) {
        // Will be catched implicit by CaCertManager
      } catch( InvocationTargetException itExc ) {
        Throwable cause = itExc.getCause();
        if ( cause instanceof GridException ) {
          GridException gExc = ( GridException ) cause;
          ISolution slave = SolutionRegistry.getRegistry().getSolution( SolutionRegistry.CHECK_SERVER_URL );
          ISolution solution = new UISolution( slave, getShell() ) {
            @Override
            public void solve() {
              addFromRepository();
            }
          };
          gExc.getProblem().addSolution( solution );
          int result = 
            NewProblemDialog.openProblem( getShell(),
                                          Messages.getString( "CaCertPreferencePage.cert_error" ), //$NON-NLS-1$
                                          Messages.getString( "CaCertPreferencePage.unable_load_cert_rep_error" ), //$NON-NLS-1$
                                          gExc );
          updateList = result != NewProblemDialog.SOLVE;
        }
      } catch( InterruptedException intExc ) {
        // Nothing to do here
      }
      
      if ( updateList ) {
        updateCaList();
      }
      
/*      if ( status != null ) {
        Solution[] solutions = {
          new Solution( Messages.getString( "CaCertPreferencePage.inet_connection_solution" ) ), //$NON-NLS-1$
          new Solution( Messages.getString( "CaCertPreferencePage.check_url_solution" ) ) { //$NON-NLS-1$
            @Override
            protected void solve() {
              addFromRepository();
            }
          },
          new PreferenceSolution( Messages.getString( "CaCertPreferencePage.check_proxy_solution" ), //$NON-NLS-1$
                                  "eu.geclipse.ui.internal.preference.NetworkPreferencePage", //$NON-NLS-1$
                                  getShell() )
        };
        int result = ProblemDialog.openProblem( getShell(),
                                                Messages.getString( "CaCertPreferencePage.cert_error" ), //$NON-NLS-1$
                                                Messages.getString( "CaCertPreferencePage.unable_load_cert_rep_error" ),  //$NON-NLS-1$
                                                status,
                                                solutions );
        if ( result != ProblemDialog.SOLVE ) {
          updateCaList();
        }
      } else {
        updateCaList();
      }*/
    }
  }
  
  protected void deleteSelected() {
    final String[] selected = this.caList.getSelection();
    if ( selected.length > 0 ) {
      boolean confirm = MessageDialog.openConfirm( getShell(),
                                                   Messages.getString( "CaCertPreferencePage.confirm_delete_title" ), //$NON-NLS-1$
                                                   Messages.getString( "CaCertPreferencePage.confirm_delete_message" ) ); //$NON-NLS-1$
      if ( confirm ) {
        final CaCertManager manager = CaCertManager.getManager();
        IRunnableWithProgress runnable = new IRunnableWithProgress() {
          public void run( final IProgressMonitor monitor )
              throws InvocationTargetException, InterruptedException {
            monitor.beginTask( Messages.getString( "CaCertPreferencePage.delete_cert_prog" ), selected.length ); //$NON-NLS-1$
            for ( int i = 0 ; i < selected.length ; i++ ) {
              monitor.subTask( selected[i] );
              manager.deleteCertificate( selected[i] );
              monitor.worked( 1 );
              if ( monitor.isCanceled() ) {
                break;
              }
              monitor.done();
            }
          }
        };
        IStatus status = null;
        try {
          new ProgressMonitorDialog( getShell() ).run( true, true, runnable );
        } catch( InvocationTargetException itExc ) {
          eu.geclipse.ui.internal.Activator.logException( itExc );
          status = new Status( IStatus.ERROR,
                               eu.geclipse.ui.internal.Activator.PLUGIN_ID,
                               IStatus.OK,
                               Messages.getString( "CaCertPreferencePage.delete_cert_error" ), //$NON-NLS-1$
                               itExc.getCause() );
        } catch( InterruptedException intExc ) {
          eu.geclipse.ui.internal.Activator.logException( intExc );
          status = new Status( IStatus.ERROR,
                               eu.geclipse.ui.internal.Activator.PLUGIN_ID,
                               IStatus.OK,
                               Messages.getString( "CaCertPreferencePage.delete_cert_error" ), //$NON-NLS-1$
                               intExc );
        }
        if ( status != null ) {
          ErrorDialog.openError( getShell(),
                                 Messages.getString( "CaCertPreferencePage.cert_error" ), //$NON-NLS-1$
                                 Messages.getString( "CaCertPreferencePage.unable_delete_cert_error" ), //$NON-NLS-1$
                                 status );
        }
        updateCaList();
      }
    }
  }
  
  public void updateCaList() {
    this.caList.removeAll();
    CaCertManager manager = CaCertManager.getManager();
    ICaCertificate[] certs = manager.getCertificates();
    Arrays.sort( certs, new Comparator< ICaCertificate >() {
      public int compare( final ICaCertificate c1, final ICaCertificate c2 ) {
        return c1.getID().compareTo( c2.getID() );
      }
    } );
    for ( ICaCertificate cert : certs ) {
      this.caList.add( cert.getID() );
    }
    this.deleteButton.setEnabled( this.caList.getSelectionCount() > 0 );
  }

}
