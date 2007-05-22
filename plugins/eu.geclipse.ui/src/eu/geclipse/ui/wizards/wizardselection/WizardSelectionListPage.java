package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

/**
 * Wizard page for providing a list of other wizards which can be used for the
 * next steps in the wizard.
 */
public class WizardSelectionListPage extends WizardSelectionPage implements IPageChangedListener {
  protected static ICheatSheetManager cheatSheetManager = null;
  protected IWizardSelectionNode[] wizardSelectionNodes;
  IWizardNode preselectedNode;
  private String title;
  private String desc;
  private WizardSelectionListComposite composite;
  private Object initData;
  private boolean hidePrev;

  /**
   * Creates a wizard page which allows to select a wizard for the next steps.
   * @param pageName Name of the wizard page.
   * @param wizardSelectionNodes list of the IWizardSelectionNodes to be
   *                             displayed in the WizardSelectionListPage.
   * @param title Title of the page.
   * @param desc Description text of the page.
   */
  public WizardSelectionListPage( final String pageName,
                                  final IWizardSelectionNode[] wizardSelectionNodes,
                                  final String title,
                                  final String desc ) {
    super( pageName );
    this.wizardSelectionNodes = wizardSelectionNodes;
    this.title = title;
    this.desc = desc;
    if ( wizardSelectionNodes.length == 1 ) {
      this.preselectedNode = wizardSelectionNodes[0];
    }
  }

  /**
   * Sets the data to initialize a selected wizard with in case the wizard
   * implements {@link IInitalizableWizard}.
   * @param initData the data to initialize the wizard with.
   */
  public void setInitData( final Object initData ) {
    this.initData = initData;
  }
    
  /**
   * Sets the node of the wizard which should be preselected. If set the
   * WizardSelectionListPage will be skiped and the first page of the
   * preselected wizard will be displayed.
   * @param node IWizardNode representing the wizard to be preselected.
   * @param hidePrevPage true if it should not be possible to go back to the
   *                     WizardSelectionListPage to select another wizard,
   *                     false otherwise.
   */
  public void setPreselectedNode( final IWizardNode node, final boolean hidePrevPage ) {
    this.preselectedNode = node;
    this.hidePrev = hidePrevPage;
  }

  @Override
  public void dispose() {
    for ( IWizardNode wizardNode : this.wizardSelectionNodes ) {
      wizardNode.dispose();
    }
    super.dispose();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#getTitle()
   */
  @Override
  public String getTitle() {
    return this.title; 
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#getDescription()
   */
  @Override
  public String getDescription() {
    return this.desc;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    (( WizardDialog ) getContainer()).addPageChangedListener( this );
    this.composite = new WizardSelectionListComposite( parent, SWT.NONE );
    setControl( this.composite );
    this.composite.addSelectionChangedListener( new ISelectionChangedListener() {
      @SuppressWarnings("synthetic-access")
      public void selectionChanged( final SelectionChangedEvent event ) {
        Object obj = ( ( StructuredSelection ) event.getSelection() ).getFirstElement();
        if ( obj instanceof IWizardNode ) {
          setSelectedNode( ( IWizardNode ) obj );
        }
        getWizard().getContainer().updateButtons();
      }
    } );
    this.composite.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetDefaultSelected( final SelectionEvent event ) {
        getWizard().getContainer().showPage( getNextPage() );
      }
    } );
    this.composite.fillWizardList( this.wizardSelectionNodes );
  }

  private IWizard initWizard( final IWizardNode node ) {
    IWizard wizard = null;
    if ( node != null ) {
      boolean isCreated = node.isContentCreated();
      wizard = node.getWizard();
      if (wizard != null && !isCreated) {
        if ( wizard instanceof IInitalizableWizard ) {
          ( ( IInitalizableWizard ) wizard ).init( this.initData );
        }
        wizard.addPages();
        if ( cheatSheetManager != null && cheatSheetManager.getData( "startingPageName" ) == "none" ) { //$NON-NLS-1$ //$NON-NLS-2$
          cheatSheetManager.setData( "startingPageName", wizard.getStartingPage().getName() ); //$NON-NLS-1$
        }
      }
    }

    return wizard;
  }
  
  @Override
  public IWizardPage getNextPage() {
    initWizard( getSelectedNode() );
    return super.getNextPage();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IPageChangedListener#pageChanged(org.eclipse.jface.dialogs.PageChangedEvent)
   */
  public void pageChanged( final PageChangedEvent event ) {
    ( ( WizardDialog ) getContainer() ).removePageChangedListener( this );
    Display.getCurrent().asyncExec( new Runnable() {
      @SuppressWarnings("synthetic-access")
      public void run() {
        if ( WizardSelectionListPage.this.preselectedNode != null && event.getSelectedPage() == WizardSelectionListPage.this ) {
          setSelectedNode( WizardSelectionListPage.this.preselectedNode );
          getContainer().showPage( getNextPage() );
          if ( WizardSelectionListPage.this.hidePrev ) {
            getContainer().getCurrentPage().setPreviousPage( null );
          }
          getContainer().updateButtons();
        }
      }
    } );
  }
} 
