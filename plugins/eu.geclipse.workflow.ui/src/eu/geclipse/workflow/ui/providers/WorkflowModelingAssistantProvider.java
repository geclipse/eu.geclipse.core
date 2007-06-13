/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.part.WorkflowDiagramEditorPlugin;

/**
 * @generated
 */
public class WorkflowModelingAssistantProvider
  extends ModelingAssistantProvider
{

  /**
   * @generated
   */
  @Override
  public List getTypesForPopupBar( IAdaptable host ) {
    IGraphicalEditPart editPart = ( IGraphicalEditPart )host.getAdapter( IGraphicalEditPart.class );
    if( editPart instanceof WorkflowJobEditPart ) {
      List types = new ArrayList();
      types.add( WorkflowElementTypes.IOutputPort_2001 );
      types.add( WorkflowElementTypes.IInputPort_2002 );
      return types;
    }
    if( editPart instanceof WorkflowEditPart ) {
      List types = new ArrayList();
      types.add( WorkflowElementTypes.IWorkflowJob_1001 );
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List getRelTypesOnSource( IAdaptable source ) {
    IGraphicalEditPart sourceEditPart = ( IGraphicalEditPart )source.getAdapter( IGraphicalEditPart.class );
    if( sourceEditPart instanceof OutputPortEditPart ) {
      List types = new ArrayList();
      types.add( WorkflowElementTypes.ILink_3001 );
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List getRelTypesOnTarget( IAdaptable target ) {
    IGraphicalEditPart targetEditPart = ( IGraphicalEditPart )target.getAdapter( IGraphicalEditPart.class );
    if( targetEditPart instanceof InputPortEditPart ) {
      List types = new ArrayList();
      types.add( WorkflowElementTypes.ILink_3001 );
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List getRelTypesOnSourceAndTarget( IAdaptable source, IAdaptable target )
  {
    IGraphicalEditPart sourceEditPart = ( IGraphicalEditPart )source.getAdapter( IGraphicalEditPart.class );
    IGraphicalEditPart targetEditPart = ( IGraphicalEditPart )target.getAdapter( IGraphicalEditPart.class );
    if( sourceEditPart instanceof OutputPortEditPart ) {
      List types = new ArrayList();
      if( targetEditPart instanceof InputPortEditPart ) {
        types.add( WorkflowElementTypes.ILink_3001 );
      }
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List getTypesForSource( IAdaptable target,
                                 IElementType relationshipType )
  {
    IGraphicalEditPart targetEditPart = ( IGraphicalEditPart )target.getAdapter( IGraphicalEditPart.class );
    if( targetEditPart instanceof InputPortEditPart ) {
      List types = new ArrayList();
      if( relationshipType == WorkflowElementTypes.ILink_3001 ) {
        types.add( WorkflowElementTypes.IOutputPort_2001 );
      }
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List getTypesForTarget( IAdaptable source,
                                 IElementType relationshipType )
  {
    IGraphicalEditPart sourceEditPart = ( IGraphicalEditPart )source.getAdapter( IGraphicalEditPart.class );
    if( sourceEditPart instanceof OutputPortEditPart ) {
      List types = new ArrayList();
      if( relationshipType == WorkflowElementTypes.ILink_3001 ) {
        types.add( WorkflowElementTypes.IInputPort_2002 );
      }
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public EObject selectExistingElementForSource( IAdaptable target,
                                                 IElementType relationshipType )
  {
    return selectExistingElement( target, getTypesForSource( target,
                                                             relationshipType ) );
  }

  /**
   * @generated
   */
  @Override
  public EObject selectExistingElementForTarget( IAdaptable source,
                                                 IElementType relationshipType )
  {
    return selectExistingElement( source, getTypesForTarget( source,
                                                             relationshipType ) );
  }

  /**
   * @generated
   */
  protected EObject selectExistingElement( IAdaptable host, Collection types ) {
    if( types.isEmpty() ) {
      return null;
    }
    IGraphicalEditPart editPart = ( IGraphicalEditPart )host.getAdapter( IGraphicalEditPart.class );
    if( editPart == null ) {
      return null;
    }
    Diagram diagram = ( Diagram )editPart.getRoot().getContents().getModel();
    Collection elements = new HashSet();
    for( Iterator it = diagram.getElement().eAllContents(); it.hasNext(); ) {
      EObject element = ( EObject )it.next();
      if( isApplicableElement( element, types ) ) {
        elements.add( element );
      }
    }
    if( elements.isEmpty() ) {
      return null;
    }
    return selectElement( ( EObject[] )elements.toArray( new EObject[ elements.size() ] ) );
  }

  /**
   * @generated
   */
  protected boolean isApplicableElement( EObject element, Collection types ) {
    IElementType type = ElementTypeRegistry.getInstance()
      .getElementType( element );
    return types.contains( type );
  }

  /**
   * @generated
   */
  protected EObject selectElement( EObject[] elements ) {
    Shell shell = Display.getCurrent().getActiveShell();
    ILabelProvider labelProvider = new AdapterFactoryLabelProvider( WorkflowDiagramEditorPlugin.getInstance()
      .getItemProvidersAdapterFactory() );
    ElementListSelectionDialog dialog = new ElementListSelectionDialog( shell,
                                                                        labelProvider );
    dialog.setMessage( "Available domain model elements:" );
    dialog.setTitle( "Select domain model element" );
    dialog.setMultipleSelection( false );
    dialog.setElements( elements );
    EObject selected = null;
    if( dialog.open() == Window.OK ) {
      selected = ( EObject )dialog.getFirstResult();
    }
    return selected;
  }
}
