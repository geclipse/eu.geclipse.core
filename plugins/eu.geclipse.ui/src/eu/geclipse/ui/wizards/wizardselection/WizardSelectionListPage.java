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

public class WizardSelectionListPage extends WizardSelectionPage implements IPageChangedListener {
  protected static ICheatSheetManager cheatSheetManager = null;
  protected IWizardSelectionNode[] wizardSelectionNodes;
  IWizardNode preselectedNode;
  private String title;
  private String desc;
  private WizardSelectionListComposite composite;
  private Object initData;
  private boolean hidePrev;

  WizardSelectionListPage( final String pageName,
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

  public void setInitData( final Object initData ) {
    this.initData = initData;
  }
    
  public void setPreselectedNode( final IWizardNode node ) {
    this.preselectedNode = node;
    this.hidePrev = true;
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
      }
    }
    if ( this.cheatSheetManager != null && this.cheatSheetManager.getData( "var1" ) == "null" ) {
      this.cheatSheetManager.setData( "var1", wizard.getStartingPage().getName() );
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
          if ( cheatSheetManager != null )
            cheatSheetManager.setData("var2", WizardSelectionListPage.this.preselectedNode.getWizard().getWindowTitle() );
        }
      }
    } );
  }


} 
