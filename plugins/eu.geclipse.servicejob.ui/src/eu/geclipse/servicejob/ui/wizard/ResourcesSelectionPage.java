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
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.ui.Activator;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.wizards.IVOSelectionProvider;

/**
 * Wizard page on which all resources (also services) taken form info system are
 * displayed.
 */
public class ResourcesSelectionPage extends WizardPage {

  List<IGridResource> selectedResources;
  TableViewer viewer;
  private IVOSelectionProvider voProvider;
  private boolean showComputing;
  private boolean showStorage;
  private boolean showService;
  private IVirtualOrganization oldVO;
  private List<IGridResourceCategory> visibleCategories = new ArrayList<IGridResourceCategory>();

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
    this.showComputing = showComputing;
    this.showStorage = showStorage;
    this.showService = showService;
    this.selectedResources = new ArrayList<IGridResource>();
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
    if( this.selectedResources.size() > 0 ) {
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
    return this.selectedResources;
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gd;
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 2;
    gd.widthHint = 300;
    gd.heightHint = 100;
    Table table = new Table( mainComp, SWT.BORDER
                                       | SWT.H_SCROLL
                                       | SWT.V_SCROLL
                                       | SWT.MULTI );
    table.setLayoutData( gd );
    this.viewer = new TableViewer( table );
    this.viewer.setContentProvider( new ContentProvider() );
    this.viewer.setLabelProvider( new LabelProvider1() );
    table.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        ResourcesSelectionPage.this.selectedResources.clear();
        for( TableItem tableItem : ResourcesSelectionPage.this.viewer.getTable()
          .getSelection() )
        {
          if( tableItem.getData() instanceof IGridResource ) {
            ResourcesSelectionPage.this.selectedResources.add( ( IGridResource )tableItem.getData() );
          }
        }
        ResourcesSelectionPage.this.updateButtons();
      }
    } );
    for( TableItem item : this.viewer.getTable().getItems() ) {
      if( item instanceof IGridResource ) {
        if( item == selectedResources.get( 0 ) ) {
          this.viewer.getTable().setSelection( item );
        }
      }
    }
    setControl( mainComp );
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
    String message = "computing";
    if( this.viewer != null ) {
      try {
        List<IGridElement> inputList = new ArrayList<IGridElement>();
        for( IGridResourceCategory category : this.visibleCategories ) {
          for( IGridResource res : vo.getAvailableResources( category,
                                                             false,
                                                             new NullProgressMonitor() ) )
          {
            inputList.add( res );
          }
        }
     
//    }
    
    
//    if( this.viewer != null ) {
//      List<IGridElement> inputList = new ArrayList<IGridElement>();
//      try {
//        if( this.showComputing ) {
//          for( IGridComputing comp : vo.getComputing( null ) ) {
//            inputList.add( comp );
//          }
//        }
//        message = "storage";
//        if( this.showStorage ) {
//          for( IGridStorage storage : vo.getStorage( null ) ) {
//            inputList.add( storage );
//          }
//        }
//        message = "service";
//        if( this.showService ) {
//          for( IGridService service : vo.getServices( null ) ) {
//            inputList.add( service );
//          }
//        }
        IGridResource[] input = new IGridResource[ inputList.size() ];
        input = inputList.toArray( input );
        this.viewer.setInput( input );
        this.viewer.refresh();
        if( this.selectedResources.size() > 0 ) {
          List<TableItem> selTable = new ArrayList<TableItem>();
          for( TableItem item : this.viewer.getTable().getItems() ) {
            if( item.getData() instanceof IGridResource ) {
              for( IGridResource res : selectedResources ) {
                if( ( ( IGridResource )item.getData() ).getName()
                  .equals( res.getName() ) )
                {
                  selTable.add( item );
                  break;
                }
              }
            }
            TableItem[] tableI = new TableItem[ selTable.size() ];
            this.viewer.getTable().setSelection( selTable.toArray( tableI ) );
          }
        }
      } catch( ProblemException e ) {
        ProblemDialog.openProblem( PlatformUI.getWorkbench()
                                     .getActiveWorkbenchWindow()
                                     .getShell(),
                                   "Error occured when fetching vo contents",
                                   "Error occured when fetching "
                                       + message
                                       + " elements of the "
                                       + vo.getName()
                                       + " virtual organization",
                                   e );
      } catch( NullPointerException nullExc ) {
        String[] table = new String[ 1 ];
        table[ 0 ] = "No input";
        this.viewer.setInput( table );
        this.viewer.refresh();
      }
    }
  }

  /**
   * Method to set selection on this page.
   * 
   * @param selectedResource list of resources which should be marked as
   *            selected.
   */
  public void setSelection( final List<IGridResource> selectedResource ) {
    if( selectedResource != null ) {
      this.selectedResources = selectedResource;
      // for( TableItem item : this.viewer.getTable().getItems() ) {
      // if( item instanceof IGridResource ) {
      // if( item == selectedResource ) {
      // this.viewer.getTable().setSelection( item );
      // this.selectedResources = selectedResource;
      // }
      // }
      // }
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
