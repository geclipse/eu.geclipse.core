/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
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
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.ui.wizards.datavisualization;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;


/**
 * @author sgirtel
 *
 */
public class CreatePipelineWizPageContent extends PipelineWizPage {

  /**
   * @param parent
   * @param style
   * @param page
   */
  public CreatePipelineWizPageContent( Composite parent,
                                       int style,
                                       Listener page )
  {
    super( parent, style, page );
  }

  @Override
  public void getStatus() {
  }
}
