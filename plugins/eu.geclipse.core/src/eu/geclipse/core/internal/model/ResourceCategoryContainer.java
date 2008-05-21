package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.core.reporting.ProblemException;

public class ResourceCategoryContainer extends VirtualGridContainer {
  
  private IGridResourceCategory category;
  
  private List< IGridElement > permanentChildren
    = new ArrayList< IGridElement >();
  
  protected ResourceCategoryContainer( final IGridContainer parent,
                                       final IGridResourceCategory category ) {
    super( parent, category.getName() );
    this.category = category;
    if ( parent instanceof ResourceCategoryContainer ) {
      ( ( ResourceCategoryContainer ) parent ).permanentChildren.add( this );
    }
  }
  
  public void addChild( final ResourceCategoryContainer child )
      throws GridModelException {
    addElement( child );
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return ( element instanceof IGridResource )
      || ( element instanceof ResourceCategoryContainer )
      || ( element instanceof ContainerMarker );
  }

  public boolean isLocal() {
    return true;
  }
  
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor )
      throws GridModelException {
  
    for ( IGridElement permantenChild : this.permanentChildren ) {
      addElement( permantenChild );
    }
    
    try {
      
      IGridResource[] resources
        = getProject().getVO().getAvailableResources( this.category, monitor);
      
      if ( ( resources != null ) && ( resources.length > 0 ) ) {
        lock();
        try {
          for ( IGridElement resource : resources ) {
            addElement( resource );
          }
        } finally {
          unlock();
        }
      } else {
        addElement( new ContainerMarker( this,
                                         ContainerMarker.MarkerType.INFO,
                                         "No matching elements found" ) );
      }
      
    } catch ( ProblemException pExc ) {
      throw new GridModelException( pExc.getProblem() );
    }
    
    return Status.OK_STATUS;
    
  }

}
