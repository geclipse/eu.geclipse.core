package eu.geclipse.workflow.internal.model;

import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.IGridWorkflow;
import eu.geclipse.core.model.impl.ResourceGridContainer;

public class GridWorkflow
    extends ResourceGridContainer
    implements IGridWorkflow {

  protected GridWorkflow( final IResource resource ) {
    super( resource );
  }

}
