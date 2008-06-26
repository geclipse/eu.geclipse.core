/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.batch.ui.internal.model.BatchDiagram;
import eu.geclipse.batch.ui.internal.model.BatchResource;
import eu.geclipse.batch.ui.internal.model.ModelElement;

/**
 * TreeEditPart for a BatchDiagram instance. This is used in the Outline View
 * of the BatchEditor.
 */
public class DiagramTreeEditPart extends AbstractTreeEditPart
  implements PropertyChangeListener
{
  protected Display display;

  /**
   * Create a new instance of this edit part using the given model element.
   *
   * @param model a non-null BatchDiagram instance
   */
  public DiagramTreeEditPart( final BatchDiagram model ) {
    super( model );
  }

  private BatchDiagram getCastedModel() {
    return ( BatchDiagram )getModel();
  }

  /**
   * Convenience method that returns the EditPart corresponding to a given
   * child.
   *
   * @param child a model element instance
   * @return the corresponding EditPart or <code>null</code>.
   */
  private EditPart getEditPartForChild( final Object child ) {
    return ( EditPart )getViewer().getEditPartRegistry().get( child );
  }

  /**
   * Upon activation, attach to the model element as a property change listener.
   */
  @Override
  public void activate() {
    if ( !isActive() ) {
      super.activate();
      ( ( ModelElement )getModel() ).addPropertyChangeListener( this );
      this.display = this.getRoot().getViewer().getControl().getDisplay();
    }
  }

  /**
   * Upon deactivation, detach from the model element as a property change
   * listener.
   */
  @Override
  public void deactivate() {
    if ( isActive() ) {
      super.deactivate();
      ( ( ModelElement )getModel() ).removePropertyChangeListener( this );
    }
  }

  @Override
  protected void createEditPolicies() {
    // If this editpart is the root content of the viewer, then disallow removal
    if ( getParent() instanceof RootEditPart ) {
      installEditPolicy( EditPolicy.COMPONENT_ROLE,
                         new RootComponentEditPolicy() );
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
   */
  @Override
  protected List<BatchResource> getModelChildren() {
    
    return getCastedModel().getChildren();
  
  }

  /*
   * (non-Javadoc)
   *
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  public void propertyChange( final PropertyChangeEvent evt ) {
    
    String prop = evt.getPropertyName();
    if ( BatchDiagram.CHILD_ADDED_PROP.equals( prop ) ) {
     
      // Add a child to this edit part
      this.display.syncExec( new Runnable() {  
        @SuppressWarnings("synthetic-access")
        public void run() {  
          addChild( createChild( evt.getNewValue() ), -1 );
        }  
      } 
      );       
    } else if ( BatchDiagram.CHILDREN_ADDED_PROP.equals( prop ) ) {
      
      // Add a child to this edit part
      this.display.syncExec( new Runnable() {  
        @SuppressWarnings({
          "synthetic-access", "unchecked"
        })
        public void run() { 
          List < BatchResource > childrenTmp = ( List < BatchResource > ) evt.getNewValue();
          for ( BatchResource child : childrenTmp ) {
                      
            addChild( createChild( child ), -1 );
          }
        }  
      } 
      );  
    } else if ( BatchDiagram.CHILD_REMOVED_PROP.equals( prop ) ) {
      
      // Remove a child from this edit part
      this.display.syncExec( new Runnable() {  
        @SuppressWarnings("synthetic-access")
        public void run() {  
          removeChild( getEditPartForChild( evt.getNewValue() ) );
        }  
      } 
      );       
    } else {
      this.display.syncExec( new Runnable() {  
        @SuppressWarnings("synthetic-access")
        public void run() {  
          refreshVisuals(); 
        }  
      } 
      ); 
    }
  }
}
