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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobNameEditPart;
import eu.geclipse.workflow.ui.parsers.MessageFormatParser;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowParserProvider extends AbstractProvider
  implements IParserProvider
{

  /**
   * @generated
   */
  private IParser iWorkflowJobName_4001Parser;

  /**
   * @generated
   */
  private IParser getIWorkflowJobName_4001Parser() {
    if( iWorkflowJobName_4001Parser == null ) {
      iWorkflowJobName_4001Parser = createIWorkflowJobName_4001Parser();
    }
    return iWorkflowJobName_4001Parser;
  }

  /**
   * @generated
   */
  protected IParser createIWorkflowJobName_4001Parser() {
    EAttribute[] features = new EAttribute[]{
      IWorkflowPackage.eINSTANCE.getIWorkflowElement_Name(),
    };
    MessageFormatParser parser = new MessageFormatParser( features );
    return parser;
  }

  /**
   * @generated
   */
  protected IParser getParser( int visualID ) {
    switch( visualID ) {
      case WorkflowJobNameEditPart.VISUAL_ID:
        return getIWorkflowJobName_4001Parser();
    }
    return null;
  }

  /**
   * @generated
   */
  public IParser getParser( IAdaptable hint ) {
    String vid = ( String )hint.getAdapter( String.class );
    if( vid != null ) {
      return getParser( WorkflowVisualIDRegistry.getVisualID( vid ) );
    }
    View view = ( View )hint.getAdapter( View.class );
    if( view != null ) {
      return getParser( WorkflowVisualIDRegistry.getVisualID( view ) );
    }
    return null;
  }

  /**
   * @generated
   */
  public boolean provides( IOperation operation ) {
    if( operation instanceof GetParserOperation ) {
      IAdaptable hint = ( ( GetParserOperation )operation ).getHint();
      if( WorkflowElementTypes.getElement( hint ) == null ) {
        return false;
      }
      return getParser( hint ) != null;
    }
    return false;
  }
  /**
   * @generated
   */
  public static class HintAdapter extends ParserHintAdapter {

    /**
     * @generated
     */
    private final IElementType elementType;

    /**
     * @generated
     */
    public HintAdapter( IElementType type, EObject object, String parserHint ) {
      super( object, parserHint );
      assert type != null;
      elementType = type;
    }

    /**
     * @generated
     */
    @Override
    public Object getAdapter( Class adapter ) {
      if( IElementType.class.equals( adapter ) ) {
        return elementType;
      }
      return super.getAdapter( adapter );
    }
  }
}
