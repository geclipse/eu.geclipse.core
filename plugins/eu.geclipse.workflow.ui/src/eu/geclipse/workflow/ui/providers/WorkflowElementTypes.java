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

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.part.WorkflowDiagramEditorPlugin;

/**
 * @generated
 */
public class WorkflowElementTypes extends ElementInitializers {

  /**
   * @generated
   */
  private WorkflowElementTypes() {
    //
  }
  /**
   * @generated
   */
  private static Map elements;
  /**
   * @generated
   */
  private static ImageRegistry imageRegistry;
  /**
   * @generated
   */
  private static Set KNOWN_ELEMENT_TYPES;
  /**
   * @generated
   */
  public static final IElementType IWorkflow_79 = getElementType( "eu.geclipse.workflow.ui.Workflow_79" ); //$NON-NLS-1$
  /**
   * @generated
   */
  public static final IElementType IWorkflowJob_1001 = getElementType( "eu.geclipse.workflow.ui.WorkflowJob_1001" ); //$NON-NLS-1$
  /**
   * @generated
   */
  public static final IElementType IOutputPort_2001 = getElementType( "eu.geclipse.workflow.ui.OutputPort_2001" ); //$NON-NLS-1$
  /**
   * @generated
   */
  public static final IElementType IInputPort_2002 = getElementType( "eu.geclipse.workflow.ui.InputPort_2002" ); //$NON-NLS-1$
  /**
   * @generated
   */
  public static final IElementType ILink_3001 = getElementType( "eu.geclipse.workflow.ui.Link_3001" ); //$NON-NLS-1$

  /**
   * @generated
   */
  private static ImageRegistry getImageRegistry() {
    if( imageRegistry == null ) {
      imageRegistry = new ImageRegistry();
    }
    return imageRegistry;
  }

  /**
   * @generated
   */
  private static String getImageRegistryKey( ENamedElement element ) {
    return element.getName();
  }

  /**
   * @generated
   */
  private static ImageDescriptor getProvidedImageDescriptor( ENamedElement element )
  {
    if( element instanceof EStructuralFeature ) {
      EStructuralFeature feature = ( ( EStructuralFeature )element );
      EClass eContainingClass = feature.getEContainingClass();
      EClassifier eType = feature.getEType();
      if( eContainingClass != null && !eContainingClass.isAbstract() ) {
        element = eContainingClass;
      } else if( eType instanceof EClass && !( ( EClass )eType ).isAbstract() )
      {
        element = eType;
      }
    }
    if( element instanceof EClass ) {
      EClass eClass = ( EClass )element;
      if( !eClass.isAbstract() ) {
        return WorkflowDiagramEditorPlugin.getInstance()
          .getItemImageDescriptor( eClass.getEPackage()
            .getEFactoryInstance()
            .create( eClass ) );
      }
    }
    // TODO athandavan: support structural features
    return null;
  }

  /**
   * @generated
   */
  public static ImageDescriptor getImageDescriptor( ENamedElement element ) {
    String key = getImageRegistryKey( element );
    ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor( key );
    if( imageDescriptor == null ) {
      imageDescriptor = getProvidedImageDescriptor( element );
      if( imageDescriptor == null ) {
        imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
      }
      getImageRegistry().put( key, imageDescriptor );
    }
    return imageDescriptor;
  }

  /**
   * @generated
   */
  public static Image getImage( ENamedElement element ) {
    String key = getImageRegistryKey( element );
    Image image = getImageRegistry().get( key );
    if( image == null ) {
      ImageDescriptor imageDescriptor = getProvidedImageDescriptor( element );
      if( imageDescriptor == null ) {
        imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
      }
      getImageRegistry().put( key, imageDescriptor );
      image = getImageRegistry().get( key );
    }
    return image;
  }

  /**
   * @generated
   */
  public static ImageDescriptor getImageDescriptor( IAdaptable hint ) {
    ENamedElement element = getElement( hint );
    if( element == null ) {
      return null;
    }
    return getImageDescriptor( element );
  }

  /**
   * @generated
   */
  public static Image getImage( IAdaptable hint ) {
    ENamedElement element = getElement( hint );
    if( element == null ) {
      return null;
    }
    return getImage( element );
  }

  /**
   * Returns 'type' of the ecore object associated with the hint.
   * 
   * @generated
   */
  public static ENamedElement getElement( IAdaptable hint ) {
    Object type = hint.getAdapter( IElementType.class );
    if( elements == null ) {
      elements = new IdentityHashMap();
      elements.put( IWorkflow_79, IWorkflowPackage.eINSTANCE.getIWorkflow() );
      elements.put( IWorkflowJob_1001, IWorkflowPackage.eINSTANCE.getIWorkflowJob() );
      elements.put( IOutputPort_2001,
                    IWorkflowPackage.eINSTANCE.getIOutputPort() );
      elements.put( IInputPort_2002, IWorkflowPackage.eINSTANCE.getIInputPort() );
      elements.put( ILink_3001, IWorkflowPackage.eINSTANCE.getILink() );
    }
    return ( ENamedElement )elements.get( type );
  }

  /**
   * @generated
   */
  private static IElementType getElementType( String id ) {
    return ElementTypeRegistry.getInstance().getType( id );
  }

  /**
   * @generated
   */
  public static boolean isKnownElementType( IElementType elementType ) {
    if( KNOWN_ELEMENT_TYPES == null ) {
      KNOWN_ELEMENT_TYPES = new HashSet();
      KNOWN_ELEMENT_TYPES.add( IWorkflow_79 );
      KNOWN_ELEMENT_TYPES.add( IWorkflowJob_1001 );
      KNOWN_ELEMENT_TYPES.add( IOutputPort_2001 );
      KNOWN_ELEMENT_TYPES.add( IInputPort_2002 );
      KNOWN_ELEMENT_TYPES.add( ILink_3001 );
    }
    return KNOWN_ELEMENT_TYPES.contains( elementType );
  }
}
