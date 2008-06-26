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
package eu.geclipse.workflow.ui.navigator;

import eu.geclipse.workflow.IWorkflowElement;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobNameEditPart;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;
import eu.geclipse.workflow.ui.providers.WorkflowParserProvider;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * @generated
 */
public class WorkflowNavigatorLabelProvider extends LabelProvider
  implements ICommonLabelProvider, ITreePathLabelProvider
{

  /**
   * @generated
   */
  static {
    WorkflowDiagramEditorPlugin.getInstance()
      .getImageRegistry()
      .put( "Navigator?InvalidElement", //$NON-NLS-1$
            ImageDescriptor.getMissingImageDescriptor() );
    WorkflowDiagramEditorPlugin.getInstance()
      .getImageRegistry()
      .put( "Navigator?UnknownElement", //$NON-NLS-1$
            ImageDescriptor.getMissingImageDescriptor() );
    WorkflowDiagramEditorPlugin.getInstance()
      .getImageRegistry()
      .put( "Navigator?ImageNotFound", //$NON-NLS-1$
            ImageDescriptor.getMissingImageDescriptor() );
  }

  /**
   * @generated
   */
  public void updateLabel( ViewerLabel label, TreePath elementPath ) {
    Object element = elementPath.getLastSegment();
    if( element instanceof WorkflowNavigatorItem
        && !isOwnView( ( ( WorkflowNavigatorItem )element ).getView() ) )
    {
      return;
    }
    label.setText( getText( element ) );
    label.setImage( getImage( element ) );
  }

  /**
   * @generated
   */
  @Override
  public Image getImage( Object element ) {
    if( element instanceof WorkflowNavigatorGroup ) {
      WorkflowNavigatorGroup group = ( WorkflowNavigatorGroup )element;
      return WorkflowDiagramEditorPlugin.getInstance()
        .getBundledImage( group.getIcon() );
    }
    if( element instanceof WorkflowNavigatorItem ) {
      WorkflowNavigatorItem navigatorItem = ( WorkflowNavigatorItem )element;
      if( !isOwnView( navigatorItem.getView() ) ) {
        return super.getImage( element );
      }
      return getImage( navigatorItem.getView() );
    }
    return super.getImage( element );
  }

  /**
   * @generated
   */
  public Image getImage( View view ) {
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return getImage( "Navigator?TopLevelNode?http:///eu/geclipse/workflow.ecore?IWorkflowJob", //$NON-NLS-1$
                         WorkflowElementTypes.IWorkflowJob_1001 );
      case OutputPortEditPart.VISUAL_ID:
        return getImage( "Navigator?Node?http:///eu/geclipse/workflow.ecore?IOutputPort", //$NON-NLS-1$
                         WorkflowElementTypes.IOutputPort_2001 );
      case InputPortEditPart.VISUAL_ID:
        return getImage( "Navigator?Node?http:///eu/geclipse/workflow.ecore?IInputPort", //$NON-NLS-1$
                         WorkflowElementTypes.IInputPort_2002 );
      case WorkflowEditPart.VISUAL_ID:
        return getImage( "Navigator?Diagram?http:///eu/geclipse/workflow.ecore?IWorkflow", //$NON-NLS-1$
                         WorkflowElementTypes.IWorkflow_79 );
      case LinkEditPart.VISUAL_ID:
        return getImage( "Navigator?Link?http:///eu/geclipse/workflow.ecore?ILink", //$NON-NLS-1$
                         WorkflowElementTypes.ILink_3001 );
      default:
        return getImage( "Navigator?UnknownElement", null ); //$NON-NLS-1$
    }
  }

  /**
   * @generated
   */
  private Image getImage( String key, IElementType elementType ) {
    ImageRegistry imageRegistry = WorkflowDiagramEditorPlugin.getInstance()
      .getImageRegistry();
    Image image = imageRegistry.get( key );
    if( image == null
        && elementType != null
        && WorkflowElementTypes.isKnownElementType( elementType ) )
    {
      image = WorkflowElementTypes.getImage( elementType );
      imageRegistry.put( key, image );
    }
    if( image == null ) {
      image = imageRegistry.get( "Navigator?ImageNotFound" ); //$NON-NLS-1$
      imageRegistry.put( key, image );
    }
    return image;
  }

  /**
   * @generated
   */
  @Override
  public String getText( Object element ) {
    if( element instanceof WorkflowNavigatorGroup ) {
      WorkflowNavigatorGroup group = ( WorkflowNavigatorGroup )element;
      return group.getGroupName();
    }
    if( element instanceof WorkflowNavigatorItem ) {
      WorkflowNavigatorItem navigatorItem = ( WorkflowNavigatorItem )element;
      if( !isOwnView( navigatorItem.getView() ) ) {
        return null;
      }
      return getText( navigatorItem.getView() );
    }
    return super.getText( element );
  }

  /**
   * @generated
   */
  public String getText( View view ) {
    if( view.getElement() != null && view.getElement().eIsProxy() ) {
      return getUnresolvedDomainElementProxyText( view );
    }
    switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return getIWorkflowJob_1001Text( view );
      case OutputPortEditPart.VISUAL_ID:
        return getIOutputPort_2001Text( view );
      case InputPortEditPart.VISUAL_ID:
        return getIInputPort_2002Text( view );
      case WorkflowEditPart.VISUAL_ID:
        return getIWorkflow_79Text( view );
      case LinkEditPart.VISUAL_ID:
        return getILink_3001Text( view );
      default:
        return getUnknownElementText( view );
    }
  }

  /**
   * @generated
   */
  private String getIWorkflowJob_1001Text( View view ) {
    IAdaptable hintAdapter = new WorkflowParserProvider.HintAdapter( WorkflowElementTypes.IWorkflowJob_1001,
                                                                     ( view.getElement() != null
                                                                                                ? view.getElement()
                                                                                                : view ),
                                                                     WorkflowVisualIDRegistry.getType( WorkflowJobNameEditPart.VISUAL_ID ) );
    IParser parser = ParserService.getInstance().getParser( hintAdapter );
    if( parser != null ) {
      return parser.getPrintString( hintAdapter, ParserOptions.NONE.intValue() );
    } else {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( "Parser was not found for label " + 4001 ); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
  }

  /**
   * @generated
   */
  private String getIOutputPort_2001Text( View view ) {
    EObject domainModelElement = view.getElement();
    if( domainModelElement != null ) {
      return ( ( IWorkflowElement )domainModelElement ).getName();
    } else {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( "No domain element for view with visualID = " + 2001 ); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
  }

  /**
   * @generated
   */
  private String getIInputPort_2002Text( View view ) {
    EObject domainModelElement = view.getElement();
    if( domainModelElement != null ) {
      return ( ( IWorkflowElement )domainModelElement ).getName();
    } else {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( "No domain element for view with visualID = " + 2002 ); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
  }

  /**
   * @generated
   */
  private String getIWorkflow_79Text( View view ) {
    EObject domainModelElement = view.getElement();
    if( domainModelElement != null ) {
      return ( ( IWorkflowElement )domainModelElement ).getName();
    } else {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( "No domain element for view with visualID = " + 79 ); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
  }

  /**
   * @generated
   */
  private String getILink_3001Text( View view ) {
    EObject domainModelElement = view.getElement();
    if( domainModelElement != null ) {
      return ( ( IWorkflowElement )domainModelElement ).getName();
    } else {
      WorkflowDiagramEditorPlugin.getInstance()
        .logError( "No domain element for view with visualID = " + 3001 ); //$NON-NLS-1$
      return ""; //$NON-NLS-1$
    }
  }

  /**
   * @generated
   */
  private String getUnknownElementText( View view ) {
    return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * @generated
   */
  private String getUnresolvedDomainElementProxyText( View view ) {
    return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * @generated
   */
  public void init( ICommonContentExtensionSite aConfig ) {
  }

  /**
   * @generated
   */
  public void restoreState( IMemento aMemento ) {
  }

  /**
   * @generated
   */
  public void saveState( IMemento aMemento ) {
  }

  /**
   * @generated
   */
  public String getDescription( Object anElement ) {
    return null;
  }

  /**
   * @generated
   */
  private boolean isOwnView( View view ) {
    return WorkflowEditPart.MODEL_ID.equals( WorkflowVisualIDRegistry.getModelID( view ) );
  }
}
