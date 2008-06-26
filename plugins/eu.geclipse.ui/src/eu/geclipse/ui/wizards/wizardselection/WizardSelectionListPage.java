/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/
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
public class WizardSelectionListPage
    extends WizardSelectionPage
    implements IPageChangedListener {

  protected ICheatSheetManager cheatSheetManager = null;
  protected IWizardSelectionNode[] wizardSelectionNodes;
  IWizardNode preselectedNode;
  private String title;
  private String desc;
  private WizardSelectionListComposite composite;
  private Object initData;
  private boolean hidePrev;

  /**
   * Creates a wizard page which allows to select a wizard for the next steps.
   * 
   * @param pageName Name of the wizard page.
   * @param wizardSelectionNodes list of the IWizardSelectionNodes to be
   *            displayed in the WizardSelectionListPage.
   * @param title Title of the page.
   * @param desc Description text of the page.
   * @param emptyListErrMsg error message that should be displayed if the list
   *            is empty
   */
  public WizardSelectionListPage( final String pageName,
                                  final IWizardSelectionNode[] wizardSelectionNodes,
                                  final String title,
                                  final String desc,
                                  final String emptyListErrMsg )
  {
    super( pageName );
    this.wizardSelectionNodes = wizardSelectionNodes;
    this.title = title;
    this.desc = desc;
    if( wizardSelectionNodes.length == 1 ) {
      this.preselectedNode = wizardSelectionNodes[ 0 ];
    } else if( wizardSelectionNodes.length == 0 ) {
      setErrorMessage( emptyListErrMsg );
    }
  }

  /**
   * Creates a wizard page which allows to select a wizard for the next steps.
   * 
   * @param pageName Name of the wizard page.
   * @param wizardSelectionNodes list of the IWizardSelectionNodes to be
   *            displayed in the WizardSelectionListPage.
   * @param title Title of the page.
   * @param desc Description text of the page.
   * @param emptyListErrMsg error message that should be displayed if the list
   *            is empty
   * @param quickSelection flag to define wizard behavior in case there is only
   *            one wizard to select. If <code>true</code> wizard should skip
   *            this (wizard selection) page. If <code>false</code> wizard
   *            should stop on this page (the normal wizard behavior).
   */
  public WizardSelectionListPage( final String pageName,
                                  final IWizardSelectionNode[] wizardSelectionNodes,
                                  final String title,
                                  final String desc,
                                  final String emptyListErrMsg,
                                  final boolean quickSelection )
  {
    super( pageName );
    this.wizardSelectionNodes = wizardSelectionNodes;
    this.title = title;
    this.desc = desc;
    if (quickSelection && wizardSelectionNodes.length == 1){
      this.preselectedNode = wizardSelectionNodes[ 0 ];
    } else if (wizardSelectionNodes.length == 0){
      setErrorMessage( emptyListErrMsg );
    }
  }

  /**
   * Sets the data to initialize a selected wizard with in case the wizard
   * implements {@link IInitalizableWizard}.
   * 
   * @param initData the data to initialize the wizard with.
   */
  public void setInitData( final Object initData ) {
    this.initData = initData;
  }

  /**
   * Sets the node of the wizard which should be preselected. If set the
   * WizardSelectionListPage will be skipped and the first page of the
   * preselected wizard will be displayed.
   * 
   * @param node IWizardNode representing the wizard to be preselected.
   * @param hidePrevPage true if it should not be possible to go back to the
   *            WizardSelectionListPage to select another wizard, false
   *            otherwise.
   */
  public void setPreselectedNode( final IWizardNode node,
                                  final boolean hidePrevPage )
  {
    this.preselectedNode = node;
    this.hidePrev = hidePrevPage;
  }

  @Override
  public void dispose() {
    for( IWizardNode wizardNode : this.wizardSelectionNodes ) {
      wizardNode.dispose();
    }
    super.dispose();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.DialogPage#getTitle()
   */
  @Override
  public String getTitle() {
    return this.title;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.DialogPage#getDescription()
   */
  @Override
  public String getDescription() {
    return this.desc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    ( ( WizardDialog )getContainer() ).addPageChangedListener( this );
    this.composite = new WizardSelectionListComposite( parent, SWT.NONE );
    setControl( this.composite );
    this.composite.addSelectionChangedListener( new ISelectionChangedListener()
    {

      @SuppressWarnings("synthetic-access")
      public void selectionChanged( final SelectionChangedEvent event ) {
        Object obj = ( ( StructuredSelection )event.getSelection() ).getFirstElement();
        if( obj instanceof IWizardNode ) {
          setSelectedNode( ( IWizardNode )obj );
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
    if( node != null ) {
      boolean isCreated = node.isContentCreated();
      wizard = node.getWizard();
      if( wizard != null && !isCreated ) {
        if( wizard instanceof IInitalizableWizard ) {
          ( ( IInitalizableWizard )wizard ).init( this.initData );
        }
        wizard.addPages();
        updateCheatSheetManager( wizard );
      }
    }
    return wizard;
  }

  @Override
  public IWizardPage getNextPage() {
    initWizard( getSelectedNode() );
    return super.getNextPage();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.IPageChangedListener#pageChanged(org.eclipse.jface.dialogs.PageChangedEvent)
   */
  public void pageChanged( final PageChangedEvent event ) {
    ( ( WizardDialog )getContainer() ).removePageChangedListener( this );
    Display.getCurrent().asyncExec( new Runnable() {

      @SuppressWarnings("synthetic-access")
      public void run() {
        if ( WizardSelectionListPage.this.preselectedNode != null
             && event.getSelectedPage() == WizardSelectionListPage.this )
        {
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

  /**
   * Sets the cheat sheet manager. The "startingPageName" cheat sheet variable
   * of the manager will be set to the name of the first page of the selected
   * wizard when the user selects a wizard.
   * 
   * @param cheatSheetManager the cheat sheet manager.
   */
  public void setCheatSheetManager( final ICheatSheetManager cheatSheetManager ) {
    this.cheatSheetManager = cheatSheetManager;
    updateCheatSheetManager( getWizard() );
  }
  
  private void updateCheatSheetManager( final IWizard wizard ) {
    if ( ( this.cheatSheetManager != null ) && ( wizard != null ) ) {
      IWizardPage startingPage = wizard.getStartingPage();
      if ( startingPage != null ) {
        this.cheatSheetManager.setData( "startingPageName", startingPage.getName() ); //$NON-NLS-1$
      }
    }
  }
  
}
