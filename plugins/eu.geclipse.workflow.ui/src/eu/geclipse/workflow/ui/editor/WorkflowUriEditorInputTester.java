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
package eu.geclipse.workflow.ui.editor;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.common.ui.URIEditorInput;

/**
 * @generated
 */
public class WorkflowUriEditorInputTester extends PropertyTester {

  /**
   * @generated
   */
  public boolean test( Object receiver,
                       String method,
                       Object[] args,
                       Object expectedValue )
  {
    if( false == receiver instanceof URIEditorInput ) {
      return false;
    }
    URIEditorInput editorInput = ( URIEditorInput )receiver;
    return "workflow".equals( editorInput.getURI().fileExtension() ); //$NON-NLS-1$
  }
}
