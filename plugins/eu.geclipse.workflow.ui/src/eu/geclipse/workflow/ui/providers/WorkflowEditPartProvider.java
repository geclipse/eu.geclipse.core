/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.providers;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.View;

import eu.geclipse.workflow.model.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPartFactory;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowEditPartProvider extends AbstractEditPartProvider {

  /**
   * @generated
   */
  private EditPartFactory factory;
  /**
   * @generated
   */
  private boolean allowCaching;
  /**
   * @generated
   */
  private WeakReference cachedPart;
  /**
   * @generated
   */
  private WeakReference cachedView;

  
  private Map shapeMap = new HashMap();
  {
    //shapeMap.put( SemanticPackage.eINSTANCE.getLED(), LEDEditPart.class );
    this.shapeMap.put( IWorkflowPackage.eINSTANCE.getIInputPort(), InputPortEditPart.class );
    this.shapeMap.put( IWorkflowPackage.eINSTANCE.getIOutputPort(), OutputPortEditPart.class );
    this.shapeMap.put( IWorkflowPackage.eINSTANCE.getIWorkflow(), WorkflowEditPart.class );
    this.shapeMap.put( IWorkflowPackage.eINSTANCE.getIWorkflowJob(), WorkflowJobEditPart.class );
  }
  
  private Map connectorMap = new HashMap();
  {
    this.connectorMap.put( IWorkflowPackage.eINSTANCE.getILink(), LinkEditPart.class );
  }
  
  /**
   * Gets a diagram's editpart class.
   * This method should be overridden by a provider if it wants to provide this service. 
   * @param view the view to be <i>controlled</code> by the created editpart
   */
  @Override
  protected Class getDiagramEditPartClass(View view ) {
      if (view.getType().equals("workflow")) { //$NON-NLS-1$
          return(DiagramEditPart.class);
      }
      return null;
  }
  
  /**
   * Set the editpart class to the editpart mapped to the supplied view's semantic hint.
   * @see org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider#setConnectorEditPartClass(org.eclipse.gmf.runtime.diagram.ui.internal.view.IConnectorView)
   */
  @Override
  protected Class getEdgeEditPartClass(View view) {
      return(Class) this.connectorMap.get(getReferencedElementEClass(view));
  }

  /**
   * Gets a Node's editpart class.
   * This method should be overridden by a provider if it wants to provide this service. 
   * @param view the view to be <i>controlled</code> by the created editpart
   */
  @Override
  protected Class getNodeEditPartClass(View view ) {
      Class clazz = null;
      String semanticHint = view.getType();
      EClass eClass = getReferencedElementEClass(view);
      clazz =  ((Class)this.shapeMap.get(eClass));
      return clazz;
  }
  
  /**
   * @generated
   */
  public WorkflowEditPartProvider() {
    setFactory( new WorkflowEditPartFactory() );
    setAllowCaching( true );
  }

  /**
   * @generated
   */
  public final EditPartFactory getFactory() {
    return this.factory;
  }

  /**
   * @generated
   */
  protected void setFactory( EditPartFactory factory ) {
    this.factory = factory;
  }

  /**
   * @generated
   */
  public final boolean isAllowCaching() {
    return this.allowCaching;
  }

  /**
   * @generated
   */
  protected synchronized void setAllowCaching( boolean allowCaching ) {
    this.allowCaching = allowCaching;
    if( !allowCaching ) {
      this.cachedPart = null;
      this.cachedView = null;
    }
  }

  /**
   * @generated
   */
  protected IGraphicalEditPart createEditPart( View view ) {
    EditPart part = this.factory.createEditPart( null, view );
    if( part instanceof IGraphicalEditPart ) {
      return ( IGraphicalEditPart )part;
    }
    return null;
  }

  /**
   * @generated
   */
  protected IGraphicalEditPart getCachedPart( View view ) {
    if( this.cachedView != null && this.cachedView.get() == view ) {
      return ( IGraphicalEditPart )this.cachedPart.get();
    }
    return null;
  }

  /**
   * @generated
   */
  @Override
  public synchronized IGraphicalEditPart createGraphicEditPart( View view ) {
    if( isAllowCaching() ) {
      IGraphicalEditPart part = getCachedPart( view );
      this.cachedPart = null;
      this.cachedView = null;
      if( part != null ) {
        return part;
      }
    }
    return createEditPart( view );
  }

  /**
   * @generated
   */
  @Override
  public synchronized boolean provides( IOperation operation ) {
    if( operation instanceof CreateGraphicEditPartOperation ) {
      View view = ( ( IEditPartOperation )operation ).getView();
      if( !WorkflowEditPart.MODEL_ID.equals( WorkflowVisualIDRegistry.getModelID( view ) ) )
      {
        return false;
      }
      if( isAllowCaching() && getCachedPart( view ) != null ) {
        return true;
      }
      IGraphicalEditPart part = createEditPart( view );
      if( part != null ) {
        if( isAllowCaching() ) {
          this.cachedPart = new WeakReference( part );
          this.cachedView = new WeakReference( view );
        }
        return true;
      }
    }
    return false;
  }

}