package eu.geclipse.workflow.internal.model;

import org.eclipse.core.resources.IFile;

import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridWorkflow;
import eu.geclipse.core.model.impl.AbstractFileElementCreator;

public class GridWorkflowCreator extends AbstractFileElementCreator {
  
  private static final String FILE_EXTENSION = "workflow"; //$NON-NLS-1$

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return IGridWorkflow.class.isAssignableFrom( elementType );
  }

  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    IFile file = ( IFile ) getObject();
    GridWorkflow workflow = new GridWorkflow( file );
    return workflow;
  }
  
  @Override
  protected boolean internalCanCreate( final String fileExtension ) {
    return FILE_EXTENSION.equals( fileExtension );
  }

}
