/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import org.eclipse.ui.forms.editor.FormPage;

import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;

/**
 * @author nloulloud
 * 
 * The base class for any FormPageSection subclass.
 *
 */
public class JsdlFormPageSection extends JsdlAdaptersFactory {

  protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
  protected FormPage parentPage;
  protected boolean isNotifyAllowed = true;
  protected boolean adapterRefreshed = false;

  /**
   * Default class constructor. Calls the super constructor.
   */
  public JsdlFormPageSection() {
    super();
  }

  protected void contentChanged() {
    
    if ( this.isNotifyAllowed ){
      fireNotifyChanged( null );
    }
    
  }
}