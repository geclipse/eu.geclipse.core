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
 *    Mathias Stuempert - initial API and implementation
 *    Ariel Garcia      - added table sorting
 *****************************************************************************/

package eu.geclipse.ui.views;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.AuthenticationTokenManager;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.security.ISecurityManager;
import eu.geclipse.core.security.ISecurityManagerListener;
import eu.geclipse.ui.AbstractAuthTokenUIFactory;
import eu.geclipse.ui.IAuthTokenUIFactory;
import eu.geclipse.ui.UIAuthTokenProvider;
import eu.geclipse.ui.cheatsheets.OpenAuthTokenDialogAction;
import eu.geclipse.ui.comparators.TableColumnComparator;
import eu.geclipse.ui.dialogs.AuthTokenInfoDialog;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.listeners.TableColumnListener;


/**
 * The <code>AuthTokenView</code> is the central point in the g-Eclipse UI to
 * manage authentication tokens. It provides UI elements to create new and to destroy old
 * authentication tokens. Users can also activate or deactivate their tokens. Therefore
 * the <code>AuthTokenView</code> can be seen as graphical frontend of the
 * {@link eu.geclipse.core.auth.AuthenticationTokenManager}. 
 *
 * @author stuempert-m
 */
public class AuthTokenView
    extends ViewPart
    implements ISecurityManagerListener {
  
  /**
   * This internal class is used to present the currently available authentication
   * tokens in a structured way to the user. The standard presentation uses a table.
   * 
   * @author stuempert-m
   * @see AuthenticationTokenLabelProvider
   */
  static class AuthenticationTokenContentProvider implements IStructuredContentProvider {
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements( final Object input ) {
      Object[] resultArray = null;
      if ( input instanceof AuthenticationTokenManager ) {
        List< IAuthenticationToken > tokens = ( ( AuthenticationTokenManager ) input ).getTokens();
        IAuthenticationToken[] tokenArray = new IAuthenticationToken[ tokens.size() ];
        resultArray = tokens.toArray( tokenArray ); 
      }
      return resultArray;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput) {
      // empty implementation
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
      // empty implementation
    }
    
  }

  /**
   * This internal class is used to present the currently available authentication
   * tokens in a structured way to the user. The standard presentation uses a table.
   * 
   * @author stuempert-m
   * @see AuthenticationTokenContentProvider
   */
  static class AuthenticationTokenLabelProvider extends LabelProvider implements ITableLabelProvider, ITableFontProvider {
    
    /**
     * The font that is used to draw activated tokens.
     */
    private Font boldFont;
    
    /**
     * Construct and initialize a new label provider.
     */
    public AuthenticationTokenLabelProvider() {
      Font font = JFaceResources.getDefaultFont();
      Device device = font.getDevice();
      FontData[] fontData = font.getFontData();
      for ( int i = 0 ; i < fontData.length ; i++ ) {
        int style = fontData[i].getStyle();
        fontData[i].setStyle( style | SWT.BOLD );
      }
      this.boldFont = new Font( device, fontData );
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.LabelProvider#dispose()
     */
    @Override
    public void dispose() {
      super.dispose();
      this.boldFont.dispose();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage( final Object element,
                                 final int columnIndex ) {
      // no images supported at the moment
      return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText( final Object element,
                                 final int columnIndex ) {
      // Just for debugging
      String columnText = element.toString();
      
      if ( element instanceof IAuthenticationToken ) {
        IAuthenticationToken token = ( IAuthenticationToken ) element;
        switch ( columnIndex ) {
          case 0:
            columnText = token.getID();
            break;
          case 1:
            columnText = token.getDescription().getTokenTypeName();
            break;
          case 2:
            columnText = token.isActive()
              ? Messages.getString("AuthTokenView.token_active") //$NON-NLS-1$
              : Messages.getString("AuthTokenView.token_inactive"); //$NON-NLS-1$
            break;
          case 3:
            if ( !token.isActive() ) {
              columnText = ""; //$NON-NLS-1$
            } else {
              long lifetime = token.getTimeLeft();
              if ( lifetime < 0 ) {
                columnText = Messages.getString("AuthTokenView.lifetime_infinite"); //$NON-NLS-1$
              } else if ( lifetime == 0 ) {
                columnText = Messages.getString("AuthTokenView.lifetime_expired"); //$NON-NLS-1$
              } else {
                int days = ( int )( lifetime / 86400 );
                int hours = ( int )( ( lifetime % 86400 ) / 3600 );
                int minutes = ( int )( ( lifetime % 3600 ) / 60 );
                int seconds = ( int )( lifetime % 60 );
                columnText = String.format( "%1$3dd %2$02dh %3$02dm %4$02ds", //$NON-NLS-1$
                                            Integer.valueOf( days ),
                                            Integer.valueOf( hours ),
                                            Integer.valueOf( minutes ),
                                            Integer.valueOf( seconds ) );
              }
            }
            break;
        }
      }
      return columnText;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableFontProvider#getFont(java.lang.Object, int)
     */
    public Font getFont( final Object element, final int columnIndex ) {
      Font resultFont = null;
      if ( element instanceof IAuthenticationToken ) {
        IAuthenticationToken token = ( IAuthenticationToken ) element;
        if ( token.isActive() ) {
          resultFont = this.boldFont;
        }
      }
      return resultFont;
    }
    
  }


  /**
   * The table used to present the currently available authentication tokens.
   */
  protected Table tokenTable;
  
  /**
   * A <code>CheckboxTableViewer</code> that helps to present the authentication tokens
   * in a more elegant way.
   */
  protected CheckboxTableViewer tokenList;
  
  /**
   * The context menu that let the users directly choose the type of token they want to create.
   */
  protected IContributionItem newWizardMenu;
  
  /**
   * Action for creating new authentication tokens.
   */
  private Action newAction;
  
  /**
   * Action for deleting old authentication tokens.
   */
  private Action deleteAction;
  
  /**
   * Action for activating the selected authentication token.
   */
  private Action activateAction;
  
  /**
   * Action for deactivating the selected authentication token.
   */
  private Action deactivateAction;
  
  /**
   * Action for retrieving information about the currently selected authenticaion token.
   */
  private Action infoAction;
  
  /**
   * Action for refreshing the token list.
   */
  private Action refreshAction;
  
    
  /**
   * The menu creator for the context menu of the <code>newAction</code>. 
   */
  private IMenuCreator newActionMenuCreator = new IMenuCreator() {
  
    /**
     * The menu manager for managing the drop down menu.
     */
    private MenuManager dropDownMenuMgr;

    /* (non-Javadoc)
     * @see org.eclipse.jface.action.IMenuCreator#createDropDownMenuMgr(java.lang.Object, int)
     */
    private void createDropDownMenuMgr() {
      if ( this.dropDownMenuMgr == null ) {
        this.dropDownMenuMgr = new MenuManager();
        this.dropDownMenuMgr.add( AuthTokenView.this.newWizardMenu );
      }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.action.IMenuCreator#dispose()
     */
    public void dispose() {
      if ( this.dropDownMenuMgr != null ) {
        this.dropDownMenuMgr.dispose();
        this.dropDownMenuMgr = null;
      }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
     */
    public Menu getMenu( final Control parent ) {
      createDropDownMenuMgr();
      return this.dropDownMenuMgr.createContextMenu(parent);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
     */
    public Menu getMenu( final Menu parent ) {
      createDropDownMenuMgr();
      Menu menu = new Menu(parent);
      IContributionItem[] items = this.dropDownMenuMgr.getItems();
      for (int i = 0; i < items.length; i++) {
        IContributionItem item = items[i];
        IContributionItem newItem = item;
        if (item instanceof ActionContributionItem) {
          newItem = new ActionContributionItem(
            ( ( ActionContributionItem ) item ).getAction());
        }
        newItem.fill(menu, -1);
      }
      return menu;
    }
    
  };
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl( final Composite parent ) {
    
    this.tokenTable = new Table( parent, SWT.CHECK | SWT.MULTI | SWT.FULL_SELECTION );
    this.tokenTable.setHeaderVisible( true );
    this.tokenTable.setLinesVisible( true );
    
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    this.tokenList = new CheckboxTableViewer( this.tokenTable );
    this.tokenList.setLabelProvider( new AuthenticationTokenLabelProvider() );
    this.tokenList.setContentProvider( new AuthenticationTokenContentProvider() );
    
    TableColumnListener columnListener = new TableColumnListener( this.tokenList );
    
    TableColumn idColumn = new TableColumn( this.tokenTable, SWT.LEFT );
    idColumn.setText( Messages.getString("AuthTokenView.id_column_label") ); //$NON-NLS-1$
    idColumn.setWidth( 300 );
    idColumn.setAlignment( SWT.LEFT );
    idColumn.addSelectionListener( columnListener );
    TableColumn typeColumn = new TableColumn( this.tokenTable, SWT.CENTER );
    typeColumn.setText( Messages.getString("AuthTokenView.type_column_label") ); //$NON-NLS-1$
    typeColumn.setWidth( 150 );
    typeColumn.setAlignment( SWT.CENTER );
    typeColumn.addSelectionListener( columnListener );
    TableColumn stateColumn = new TableColumn( this.tokenTable, SWT.CENTER );
    stateColumn.setText( Messages.getString("AuthTokenView.state_column_label") ); //$NON-NLS-1$
    stateColumn.setWidth( 100 );
    stateColumn.setAlignment( SWT.CENTER );
    stateColumn.addSelectionListener( columnListener );
    TableColumn lifetimeColumn = new TableColumn( this.tokenTable, SWT.CENTER );
    lifetimeColumn.setText( Messages.getString("AuthTokenView.lifetime_column_label") ); //$NON-NLS-1$
    lifetimeColumn.setWidth( 150 );
    lifetimeColumn.setAlignment( SWT.CENTER );
    lifetimeColumn.addSelectionListener( columnListener );
    
    // Initially we sort the tokens by ID, ascending
    this.tokenTable.setSortColumn( idColumn );
    this.tokenTable.setSortDirection( SWT.UP );
    this.tokenList.setComparator( new TableColumnComparator( idColumn ) );
    
    this.tokenList.setInput( manager );
    IAuthenticationToken defaultToken = manager.getDefaultToken();
    if ( defaultToken != null ) {
      this.tokenList.setCheckedElements( new Object[] { defaultToken } );
    }
    
    this.tokenList.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        updateActions();
      }
    });
    this.tokenList.addCheckStateListener( new ICheckStateListener() {
      public void checkStateChanged( final CheckStateChangedEvent event ) {
        Object element = event.getElement();
        if ( element instanceof IAuthenticationToken ) {
          IAuthenticationToken token = ( IAuthenticationToken ) element;
          AuthenticationTokenManager innerManager = AuthenticationTokenManager.getManager();
          if ( !event.getChecked() ) {
            innerManager.setDefaultToken( null );
          }
          innerManager.setDefaultToken( token );
        }
      }
    } );
    this.tokenList.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent e ) {
        showSelectedTokenInfo();
      }
    });
    this.tokenTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed( final KeyEvent event ) {
        if (event.character == SWT.DEL && event.stateMask == 0) {
          removeSelectedTokens();
        }
      }
    });
    manager.addListener( this );
    
    createActions();
    createToolbar();
    createContextMenu();
    
    // Set as selection provider so that properties get updated
    getSite().setSelectionProvider( this.tokenList );
 
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#dispose()
   */
  @Override
  public void dispose() {
    AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
    manager.removeListener( this );
  }
  
  public void contentChanged( final ISecurityManager manager ) { 
    Runnable runnable = new Runnable() {
      public void run() {
        AuthTokenView.this.tokenList.refresh();
        AuthenticationTokenManager innerManager = AuthenticationTokenManager.getManager();
        IAuthenticationToken innerDefaultToken = innerManager.getDefaultToken();
        if ( innerDefaultToken != null ) {
          AuthTokenView.this.tokenList.setCheckedElements( new Object[] { innerDefaultToken } );
        }
        updateActions();        
      }};
      
      getViewSite().getShell().getDisplay().syncExec( runnable );
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    this.tokenTable.setFocus();
  }

  /**
   * Get the currently selected token. If there are more tokens selected get the first
   * token in the list of selected tokens.
   * 
   * @return The first token in the list of selected tokens.
   */
  public IAuthenticationToken getSelectedToken() {
    IAuthenticationToken resultToken = null;
    IStructuredSelection selection
      = ( IStructuredSelection ) this.tokenList.getSelection();
    Object o = selection.getFirstElement();
    if ( o instanceof  IAuthenticationToken ) {
      resultToken = ( IAuthenticationToken ) o;
    }
    return resultToken;
  }
  
  /**
   * Get a list of all currently selected tokens.
   * 
   * @return A list containing all currently selected tokens.
   */
  public List< IAuthenticationToken > getSelectedTokens() {
    IStructuredSelection selection 
      = ( IStructuredSelection ) this.tokenList.getSelection();
    List< ? > selectionList = selection.toList();
    List< IAuthenticationToken > result = new ArrayList< IAuthenticationToken >();
    for( Object element : selectionList ) {
      if ( element instanceof IAuthenticationToken ) {
        IAuthenticationToken token = ( IAuthenticationToken ) element;
        result.add( token );
      }
    }
    return result;
  }

  /**
   * Activates or deactivates the currently selected token.
   * 
   * @param active If true the token will be activated, otherwise it will be deactivated.
   * @see #getSelectedToken()
   */
  protected void setSelectedTokenActive( final boolean active ) {
    final IAuthenticationToken token = getSelectedToken();
    if ( active == token.isActive() ) return;
    ProgressMonitorDialog progMon = new ProgressMonitorDialog( getSite().getShell() );
    Throwable exc = null;
    try {
      progMon.run( false, false, new IRunnableWithProgress() {
        public void run( final IProgressMonitor monitor ) throws InvocationTargetException, InterruptedException {
          try {
            token.setActive( active, monitor );
          } catch( AuthenticationException authExc ) {
            throw new InvocationTargetException( authExc );
          }
        }
      } );
    } catch( InvocationTargetException itExc ) {
      exc = itExc.getCause();
    } catch( InterruptedException intExc ) {
      exc = intExc;
    }
    
    if ( exc != null ) {
      String errMsg
        = active
          ? Messages.getString("AuthTokenView.token_activation_error") //$NON-NLS-1$
          : Messages.getString("AuthTokenView.token_deactivation_error"); //$NON-NLS-1$
      ProblemDialog.openProblem( getSite().getShell(),
                                 Messages.getString("AuthTokenView.token_activation_error_title"), //$NON-NLS-1$
                                 errMsg,
                                 exc );
    }
  }

  /**
   * Removes the selected tokens from the table and destroys them using the authentication token manager.
   * 
   * @see #getSelectedTokens()
   */
  protected void removeSelectedTokens() {
    List< IAuthenticationToken > tokens = getSelectedTokens();
    if ( !tokens.isEmpty() ) {
      boolean confirm = !MessageDialog.openConfirm( getSite().getShell(),
                                                    Messages.getString("AuthTokenView.confirm_delete_title"), //$NON-NLS-1$
                                                    Messages.getString("AuthTokenView.confirm_delete_message") );  //$NON-NLS-1$
      if ( !confirm ) {
        AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
        for ( IAuthenticationToken token : tokens ) {
          try {
            manager.destroyToken( token );
          } catch( AuthenticationException authExc ) {
            eu.geclipse.ui.internal.Activator.logException( authExc );
          }
        }
        updateActions();
      }
    }
  }
  
  /**
   * Show an info dialog for the currently selected token.
   * 
   * @see #getSelectedToken()
   */
  protected void showSelectedTokenInfo() {
    IAuthenticationToken token = getSelectedToken();
    if ( token != null ) {
      IAuthTokenUIFactory factory = AbstractAuthTokenUIFactory.findFactory( token );
      if ( factory != null ) {
        AuthTokenInfoDialog infoDialog = factory.getInfoDialog( token, getSite().getShell() );
        infoDialog.open();
      }
    }
  }

  /**
   * Update the enabled state of the actions according to the current content of this view and
   * the content's state.
   */
  @SuppressWarnings("null")
  protected void updateActions() {
    IAuthenticationToken token = getSelectedToken();
    boolean selected = token != null;
    this.newAction.setEnabled( true );
    this.deleteAction.setEnabled( selected );
    this.activateAction.setEnabled( selected && !token.isActive() );
    this.deactivateAction.setEnabled( selected && token.isActive() );
    this.infoAction.setEnabled( selected );
    this.refreshAction.setEnabled( true );
  }
  
  /**
   * Fill the context menu belonging to the token table.
   * 
   * @param mgr The manager that takes responsible for the context menu.
   */
  protected void fillContextMenu( final IMenuManager mgr ) {
    if ( this.newAction.isEnabled() ) mgr.add( this.newAction );
    if ( this.deleteAction.isEnabled() || this.activateAction.isEnabled()
        || this.deactivateAction.isEnabled() || this.infoAction.isEnabled()
        || this.refreshAction.isEnabled() ) {
      mgr.add( new Separator() );
      if ( this.deleteAction.isEnabled() ) mgr.add( this.deleteAction );
      if ( this.activateAction.isEnabled() ) mgr.add( this.activateAction );
      if ( this.deactivateAction.isEnabled() ) mgr.add( this.deactivateAction );
      if ( this.infoAction.isEnabled() ) mgr.add( this.infoAction );
      if ( this.deleteAction.isEnabled() || this.activateAction.isEnabled()
          || this.deactivateAction.isEnabled() || this.infoAction.isEnabled() ) {
        mgr.add( new Separator() );
      }
      if ( this.refreshAction.isEnabled() ) mgr.add( this.refreshAction );
    }
    mgr.add(new GroupMarker( IWorkbenchActionConstants.MB_ADDITIONS ) );
  }
  
  /**
   * Create the actions of this view.
   */
  private void createActions() {
    
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
    
    ImageDescriptor newImage 
      = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD );
    ImageDescriptor deleteImage 
      = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE );
    Image image = imgReg.get( "activestate" ); //$NON-NLS-1$
    ImageDescriptor activateImage = ImageDescriptor.createFromImage( image );
    image = imgReg.get( "inactivestate" ); //$NON-NLS-1$
    ImageDescriptor deactivateImage = ImageDescriptor.createFromImage( image );
    ImageDescriptor infoImage 
      = sharedImages.getImageDescriptor( ISharedImages.IMG_OBJS_INFO_TSK );
    image = imgReg.get( "refresh" ); //$NON-NLS-1$
    ImageDescriptor refreshImage = ImageDescriptor.createFromImage( image );
    
    this.infoAction = new Action() {
      @Override
      public void run() {
        showSelectedTokenInfo();
      }
    };
    this.infoAction.setText( Messages.getString("AuthTokenView.info_text") ); //$NON-NLS-1$
    this.infoAction.setToolTipText( Messages.getString("AuthTokenView.info_tooltip") ); //$NON-NLS-1$
    this.infoAction.setImageDescriptor( infoImage );
    
    this.refreshAction = new Action() {
      @Override
      public void run() {
        AuthenticationTokenManager innerManager = AuthenticationTokenManager.getManager();
        AuthTokenView.this.tokenList.refresh();
        IAuthenticationToken innerDefaultToken = innerManager.getDefaultToken();
        if ( innerDefaultToken != null ) {
          AuthTokenView.this.tokenList.setCheckedElements( new Object[] { innerDefaultToken } );
        }
        
        ///////////// Only for testing ///////////
        
        /*UIAuthTokenProvider provider = new UIAuthTokenProvider( AuthTokenView.this.getSite().getShell() );
        IAuthenticationTokenDescription description = new GridProxyDescription();
        provider.requestToken( description );*/
        
        ///////////// Will be removed soon //////////////
        
      }
    };
    this.refreshAction.setText( Messages.getString("AuthTokenView.refresh_text") ); //$NON-NLS-1$
    this.refreshAction.setToolTipText( Messages.getString("AuthTokenView.refresh_tooltip") ); //$NON-NLS-1$
    this.refreshAction.setImageDescriptor( refreshImage );
    
    this.deleteAction = new Action() {
      @Override
      public void run() {
        removeSelectedTokens();
      }
    };
    this.deleteAction.setText( Messages.getString("AuthTokenView.delete_text") ); //$NON-NLS-1$
    this.deleteAction.setToolTipText( Messages.getString("AuthTokenView.delete_tooltip") ); //$NON-NLS-1$
    this.deleteAction.setImageDescriptor( deleteImage );
    
    this.activateAction = new Action() {
      @Override
      public void run() {
        setSelectedTokenActive( true );
      }
    };
    this.activateAction.setText( Messages.getString("AuthTokenView.activate_text") ); //$NON-NLS-1$
    this.activateAction.setToolTipText( Messages.getString("AuthTokenView.activate_tooltip") ); //$NON-NLS-1$
    this.activateAction.setImageDescriptor( activateImage );
    
    this.deactivateAction = new Action() {
      @Override
      public void run() {
        setSelectedTokenActive( false );
      }
    };
    this.deactivateAction.setText( Messages.getString("AuthTokenView.deactivate_text") ); //$NON-NLS-1$
    this.deactivateAction.setToolTipText( Messages.getString("AuthTokenView.deactivate_tooltip") ); //$NON-NLS-1$
    this.deactivateAction.setImageDescriptor( deactivateImage );

    this.newAction = new OpenAuthTokenDialogAction();
    this.newAction.setText( Messages.getString("AuthTokenView.create_text") ); //$NON-NLS-1$
    this.newAction.setToolTipText( Messages.getString("AuthTokenView.create_tooltip") ); //$NON-NLS-1$
    this.newAction.setImageDescriptor( newImage );
    this.newAction.setMenuCreator( this.newActionMenuCreator );
    
    this.newWizardMenu = new CompoundContributionItem() {
      @Override
      protected IContributionItem[] getContributionItems() {
        List<IContributionItem> itemList = new LinkedList<IContributionItem>();
        ExtensionManager manager = new ExtensionManager();
        List< IConfigurationElement > cElements
          = manager.getConfigurationElements( Extensions.AUTH_TOKEN_POINT,
                                              Extensions.AUTH_TOKEN_ELEMENT );
        for ( IConfigurationElement element : cElements ) {
          final String name = element.getAttribute( Extensions.AUTH_TOKEN_NAME_ATTRIBUTE );
          final String wizardId = element.getAttribute( Extensions.AUTH_TOKEN_WIZARD_ATTRIBUTE );
          if ( name != null && wizardId != null ) {
            Action action = new Action() {
              @Override
              public void run() {
                UIAuthTokenProvider tokenProvider
                  = new UIAuthTokenProvider( getSite().getShell() );
                tokenProvider.showNewTokenWizard( wizardId, true, null );
              }
            };
            action.setText( name );
            itemList.add( new ActionContributionItem( action ) );
          }
        }
        return itemList.toArray( new  IContributionItem[0] );
      }
    };
    
    updateActions();
        
  }
  
  /**
   * Create the toolbar of this view. 
   */
  private void createToolbar() {
    IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
    mgr.add( this.newAction );
    mgr.add( new Separator() );
    mgr.add( this.deleteAction );
    mgr.add( this.activateAction );
    mgr.add( this.deactivateAction );
    mgr.add( this.infoAction );
    mgr.add( new Separator() );
    mgr.add( this.refreshAction );
  }

  /**
   * Create the context menu for the token table.
   */
  private void createContextMenu() {
    MenuManager manager = new MenuManager();
    manager.setRemoveAllWhenShown( true );
    manager.addMenuListener( new IMenuListener() {
      public void menuAboutToShow( final IMenuManager mgr ) {
        fillContextMenu( mgr );
      }
    } );
    Menu menu = manager.createContextMenu( this.tokenTable );
    this.tokenTable.setMenu(menu);
    getSite().registerContextMenu( manager, getSite().getSelectionProvider() );
  }

}
