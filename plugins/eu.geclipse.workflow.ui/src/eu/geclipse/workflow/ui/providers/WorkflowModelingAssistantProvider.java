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
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;

/**
 * This class provides popup options when the mouse hovers over a Workflow or 
 * WorkflowJob. The provided popups are Input & Output ports (in the case of
 * WorkflowJobs) and WorkflowJobs (in the case of Workflows). 
 * @generated
 */
public class WorkflowModelingAssistantProvider extends ModelingAssistantProvider
{

  /**
   * @generated
   */
  @Override
  public List<IElementType> getTypesForPopupBar( IAdaptable host ) {
    IGraphicalEditPart editPart = ( IGraphicalEditPart )host.getAdapter( IGraphicalEditPart.class );
    if( editPart instanceof WorkflowJobEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
      types.add( WorkflowElementTypes.IOutputPort_2001 );
      types.add( WorkflowElementTypes.IInputPort_2002 );
      return types;
    }
    if( editPart instanceof WorkflowEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
      types.add( WorkflowElementTypes.IWorkflowJob_1001 );
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List<IElementType> getRelTypesOnSource( IAdaptable source ) {
    IGraphicalEditPart sourceEditPart = ( IGraphicalEditPart )source.getAdapter( IGraphicalEditPart.class );
    if( sourceEditPart instanceof OutputPortEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
      types.add( WorkflowElementTypes.ILink_3001 );
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List<IElementType> getRelTypesOnTarget( IAdaptable target ) {
    IGraphicalEditPart targetEditPart = ( IGraphicalEditPart )target.getAdapter( IGraphicalEditPart.class );
    if( targetEditPart instanceof InputPortEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
      types.add( WorkflowElementTypes.ILink_3001 );
      return types;
    }
    return Collections.EMPTY_LIST;
  }

  /**
   * @generated
   */
  @Override
  public List<IElementType> getRelTypesOnSourceAndTarget( IAdaptable source, IAdaptable target )
  {
    IGraphicalEditPart sourceEditPart = ( IGraphicalEditPart )source.getAdapter( IGraphicalEditPart.class );
    IGraphicalEditPart targetEditPart = ( IGraphicalEditPart )target.getAdapter( IGraphicalEditPart.class );
    if( sourceEditPart instanceof OutputPortEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
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
  public List<IElementType> getTypesForSource( IAdaptable target, IElementType relationshipType )
  {
    IGraphicalEditPart targetEditPart = ( IGraphicalEditPart )target.getAdapter( IGraphicalEditPart.class );
    if( targetEditPart instanceof InputPortEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
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
  public List<IElementType> getTypesForTarget( IAdaptable source, IElementType relationshipType )
  {
    IGraphicalEditPart sourceEditPart = ( IGraphicalEditPart )source.getAdapter( IGraphicalEditPart.class );
    if( sourceEditPart instanceof OutputPortEditPart ) {
      List<IElementType> types = new ArrayList<IElementType>();
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
  public EObject selectExistingElementForSource( IAdaptable target, IElementType relationshipType ) {
    return selectExistingElement( target, getTypesForSource( target, relationshipType ) );
  }

  /**
   * @generated
   */
  @Override
  public EObject selectExistingElementForTarget( IAdaptable source, IElementType relationshipType ) {
    return selectExistingElement( source, getTypesForTarget( source, relationshipType ) );
  }

  /**
   * @generated
   */
  protected EObject selectExistingElement( IAdaptable host, Collection<IElementType> types ) {
    if( types.isEmpty() ) {
      return null;
    }
    IGraphicalEditPart editPart = ( IGraphicalEditPart )host.getAdapter( IGraphicalEditPart.class );
    if( editPart == null ) {
      return null;
    }
    Diagram diagram = ( Diagram )editPart.getRoot().getContents().getModel();
    Collection<EObject> elements = new HashSet<EObject>();
    for( Iterator<?> it = diagram.getElement().eAllContents(); it.hasNext(); ) {
      EObject element = ( EObject )it.next();
      if( isApplicableElement( element, types ) ) {
        elements.add( element );
      }
    }
    if( elements.isEmpty() ) {
      return null;
    }
    return selectElement( elements.toArray( new EObject[ elements.size() ] ) );
  }

  /**
   * @generated
   */
  protected boolean isApplicableElement( EObject element, Collection<IElementType> types ) {
    IElementType type = ElementTypeRegistry.getInstance().getElementType( element );
    return types.contains( type );
  }

  /**
   * @generated
   */
  protected EObject selectElement( EObject[] elements ) {
    Shell shell = Display.getCurrent().getActiveShell();
    ILabelProvider labelProvider = new AdapterFactoryLabelProvider( WorkflowDiagramEditorPlugin.getInstance()
                                                                    .getItemProvidersAdapterFactory() );
    ElementListSelectionDialog dialog = new ElementListSelectionDialog( shell, labelProvider );
    dialog.setMessage( "Available domain model elements:" ); //$NON-NLS-1$
    dialog.setTitle( "Select domain model element" ); //$NON-NLS-1$
    dialog.setMultipleSelection( false );
    dialog.setElements( elements );
    EObject selected = null;
    if( dialog.open() == Window.OK ) {
      selected = ( EObject )dialog.getFirstResult();
    }
    return selected;
  }
}
