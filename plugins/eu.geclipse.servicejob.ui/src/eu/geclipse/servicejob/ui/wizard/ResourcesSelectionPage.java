/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.wizard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.ui.Activator;
import eu.geclipse.servicejob.ui.internal.CategoryContainer;
import eu.geclipse.servicejob.ui.internal.CategoryResourcesTreeCProvider;
import eu.geclipse.servicejob.ui.internal.CategoryResourcesTreeLProvider;
import eu.geclipse.ui.wizards.IVOSelectionProvider;

/**
 * Wizard page on which all resources (also services) taken form info system are
 * displayed.
 */
public class ResourcesSelectionPage extends WizardPage {

  CheckboxTreeViewer viewer;
  private IVOSelectionProvider voProvider;
  private IVirtualOrganization oldVO;
  private List<IGridResourceCategory> visibleCategories = new ArrayList<IGridResourceCategory>();
  private Button selectAllButton;
  private Button revertSelectionButton;
  private Button deselectAllButton;
  private List<IGridResource> selectedResources;

  /**
   * Constructor of ResourceSelectionPage objects.
   * 
   * @param selectionProvider object implementing {@link IVOSelectionProvider}
   *            interface. This object is a source of information for which VO
   *            resources should be presented.
   * @param pageName name of this wizard page
   */
  public ResourcesSelectionPage( final IVOSelectionProvider selectionProvider,
                                 final String pageName )
  {
    super( pageName );
    this.voProvider = selectionProvider;
    this.setTitle( "Resource selection" );
    this.setDescription( "Choose services to perform operator's job on." );
    this.oldVO = null;
    this.visibleCategories.add( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_COMPUTING ) );
    this.visibleCategories.add( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_STORAGE ) );
  }

  public void addVisibleCategory( final IGridResourceCategory resourceCategory )
  {
    if( !this.visibleCategories.contains( resourceCategory ) ) {
      this.visibleCategories.add( resourceCategory );
    }
  }

  public void removeVisibleCategory( final IGridResourceCategory resourceCategory )
  {
    this.visibleCategories.remove( resourceCategory );
  }

  @Override
  public void setVisible( final boolean visible ) {
    super.setVisible( visible );
    if( visible ) {
      if( this.oldVO != null
          && !this.oldVO.equals( this.voProvider.getVirtualOrganization() ) )
      {
        this.selectedResources = new ArrayList<IGridResource>();
      }
      setSelectedVO( this.voProvider.getVirtualOrganization() );
    } else {
      this.oldVO = this.voProvider.getVirtualOrganization();
    }
    this.updateButtons();
  }

  @Override
  public boolean isPageComplete() {
    return super.isPageComplete() && canFlipToNextPage();
  }

  @Override
  public boolean canFlipToNextPage() {
    boolean flag = false;
    if (this.viewer.getCheckedElements().length != 0){
      flag = true;
    }
    return flag && getNextPage() != null;
  }

  /**
   * Method to access list of selected resources.s
   * 
   * @return list of resources which were selected by user
   */
  public List<IGridResource> getSelectedResources() {
    List<IGridResource> result = new ArrayList<IGridResource>();
    for (Object checked: this.viewer.getCheckedElements()){
      if( checked instanceof IGridResource ) {
        result.add( ( IGridResource )checked );
      }
    }
    return result; 
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gd;
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 300;
    gd.heightHint = 100;
    Tree tree = new Tree( mainComp, SWT.BORDER
                                    | SWT.H_SCROLL
                                    | SWT.V_SCROLL
                                    | SWT.CHECK );
    tree.setLayoutData( gd );
    this.viewer = new CheckboxTreeViewer( tree );
    this.viewer.setContentProvider( new CategoryResourcesTreeCProvider() );
    this.viewer.setLabelProvider( new CategoryResourcesTreeLProvider() );
    this.viewer.addCheckStateListener( new ICheckStateListener() {

      public void checkStateChanged( final CheckStateChangedEvent event ) {
        updateParent( event );
        if( event.getChecked() ) {
          ResourcesSelectionPage.this.viewer.setSubtreeChecked( event.getElement(),
                                                                true );
        } else {
          ResourcesSelectionPage.this.viewer.setSubtreeChecked( event.getElement(),
                                                                false );
        }
      }
    } );
    createButtons( mainComp );
    setControl( mainComp );
  }

  private void createButtons( final Composite composite ) {
    Composite buttonsComp = new Composite( composite, SWT.NONE );
    buttonsComp.setLayout( new GridLayout( 1, false ) );
    GridData gd = new GridData();
    buttonsComp.setLayoutData( gd );
    this.selectAllButton = new Button( buttonsComp, SWT.PUSH );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    this.selectAllButton.setText( "Select all" );
    this.selectAllButton.setLayoutData( gd );
    this.deselectAllButton = new Button( buttonsComp, SWT.PUSH );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    this.deselectAllButton.setLayoutData( gd );
    this.deselectAllButton.setText( "Deselect all" );
    this.revertSelectionButton = new Button( buttonsComp, SWT.PUSH );
    gd = new GridData( GridData.FILL_HORIZONTAL );
    this.revertSelectionButton.setLayoutData( gd );
    this.revertSelectionButton.setText( "Revert selection" );
    createListenersForButtons();
  }

  private void changeStateAllTreeItems( final boolean checked ) {
    CategoryContainer[] input = ( CategoryContainer[] )this.viewer.getInput();
    for( CategoryContainer category : input ) {
      viewer.setChecked( category, checked );
      viewer.setSubtreeChecked( category, checked );
    }
  }

  private void revertSelection() {
    CategoryContainer[] input = ( CategoryContainer[] )this.viewer.getInput();
    for( CategoryContainer category : input ) {
      IGridResource childRef = null;
      boolean child = true;
      for( IGridResource resource : category.getContainedResources() ) {
        child = viewer.getChecked( resource);
        viewer.setChecked( resource, !child );
        if( child ) {
          childRef = resource;
        }
      }
      if( childRef == null ) {
        childRef = category.getContainedResources().get( 0 );
      }
      updateParent( childRef );
    }
  }

  private void createListenersForButtons() {
    this.selectAllButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        changeStateAllTreeItems( true );
        updateButtons();
      }
    } );
    this.deselectAllButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        changeStateAllTreeItems( false );
        updateButtons();
      }
    } );
    this.revertSelectionButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        revertSelection();
        updateButtons();
      }
    } );
  }

  void updateParent( final Object element ) {
    if( element instanceof IGridResource ) {
      IGridResource gridResource = ( IGridResource )element;
      CategoryContainer[] input = ( CategoryContainer[] )this.viewer.getInput();
      CategoryContainer parent = null;
      for( CategoryContainer category : input ) {
        if( category.getContainedResources().contains( gridResource ) ) {
          parent = category;
          break;
        }
      }
      if( parent != null ) {
        // we've got the parent :)
        if( viewer.getChecked( element ) ) {
          boolean childrenChecked = true;
          for( IGridResource child : parent.getContainedResources() ) {
            if( !this.viewer.getChecked( child ) ) {
              childrenChecked = false;
              break;
            }
          }
          this.viewer.setChecked( parent, childrenChecked );
        } else {
          this.viewer.setChecked( parent, false );
        }
      }
    }
  }

  protected void updateButtons() {
    
    this.getContainer().updateButtons();
  }

  /**
   * Method to set (also change) the VO from which resources are displayed by
   * this wizard page.
   * 
   * @param vo VO object from which resources will be presented on this page
   */
  public void setSelectedVO( final IVirtualOrganization vo ) {
    if( this.viewer != null ) {
      try {
        List<CategoryContainer> categories = new ArrayList<CategoryContainer>();
        for( IGridResourceCategory category : this.visibleCategories ) {
          CategoryContainer catContainer = new CategoryContainer( category );
          categories.add( catContainer );
          for( IGridResource res : vo.getAvailableResources( category,
                                                             false,
                                                             new NullProgressMonitor() ) )
          {
            catContainer.addResource( res );
          }
        }
        CategoryContainer[] input = new CategoryContainer[ categories.size() ];
        input = categories.toArray( input );
        this.viewer.setInput( input );
        this.viewer.refresh();
        this.viewer.expandAll();
      } catch( ProblemException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }


  @Override
  public IWizardPage getNextPage() {
    return super.getNextPage();
  }
  class ContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      Object[] result = null;
      if( inputElement instanceof IGridResource[] ) {
        IGridResource[] candidateHostsType = ( IGridResource[] )inputElement;
        result = candidateHostsType;
      } else if( inputElement instanceof String[] ) {
        result = ( String[] )inputElement;
      }
      return result;
    }

    public void dispose() {
      // Auto-generated method stub
    }

    public void inputChanged( final Viewer viewer1,
                              final Object oldInput,
                              final Object newInput )
    {
      // Auto-generated method stub
    }
  }
  class LabelProvider1 extends LabelProvider implements ITableLabelProvider {

    private Image argsImage;
    // private Image candHostsImage;
    private Image computingImg;
    private Image storageImg;
    private Image serviceImg;

    /**
     * Class Constructor
     */
    public LabelProvider1() {
      loadImages();
    }

    public Image getColumnImage( final Object element, final int columnIndex ) {
      Image image = null;
      switch( columnIndex ) {
        case 0:
          if( element instanceof IGridComputing ) {
            image = this.computingImg;
          } else if( element instanceof IGridStorage ) {
            image = this.storageImg;
          } else if( element instanceof IGridService ) {
            image = this.serviceImg;
          }
        break;
        default:
        break;
      } // end switch
      return image;
    }

    public String getColumnText( final Object element, final int columnIndex ) {
      String text = null;
      Object[] array = null;
      if( element instanceof Object[] ) {
        array = ( Object[] )element;
      } else if( element instanceof IGridResource ) {
        switch( columnIndex ) {
          case 0:
            text = ( ( IGridResource )element ).getName();
          break;
          default:
            // text = element.toString();
          break;
        } // end switch
      } else if( element instanceof String ) {
        text = element.toString();
      }
      if( ( array != null ) && ( columnIndex < array.length ) ) {
        text = getText( array[ columnIndex ] );
      }
      return text;
    }// End String getColumnText()

    void loadImages() {
      URL argsURL = Activator.getDefault()
        .getBundle()
        .getEntry( "icons/etool16/args.gif" ); //$NON-NLS-1$
      URL candHostsURL = Activator.getDefault()
        .getBundle()
        .getEntry( "icons/etool16/computing_obj.gif" ); //$NON-NLS-1$
      URL storageURL = Activator.getDefault()
        .getBundle()
        .getEntry( "icons/etool16/storage.gif" ); //$NON-NLS-1$
      URL serviceURL = Activator.getDefault()
        .getBundle()
        .getEntry( "icons/etool16/service.gif" ); //$NON-NLS-1$
      ImageDescriptor argsDesc = ImageDescriptor.createFromURL( argsURL );
      ImageDescriptor candHostsDesc = ImageDescriptor.createFromURL( candHostsURL );
      ImageDescriptor storageDesc = ImageDescriptor.createFromURL( storageURL );
      ImageDescriptor serviceDesc = ImageDescriptor.createFromURL( serviceURL );
      this.argsImage = argsDesc.createImage();
      this.computingImg = candHostsDesc.createImage();
      this.storageImg = storageDesc.createImage();
      this.serviceImg = serviceDesc.createImage();
    }
  }
}
