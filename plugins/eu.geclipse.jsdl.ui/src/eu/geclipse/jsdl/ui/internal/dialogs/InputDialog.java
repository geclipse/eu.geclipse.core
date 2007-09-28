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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.ui.dialogs.GridFileDialog;

/**
 * Class supports usage of {@link Dialog} with any number of text fields. This
 * class is similar to {@link MultipleInputDialog}, but it's suited to
 * g-Eclipse needs and uses widgets characteristic of g-Eclipse (e.g.
 * {@link GridFileDialog}}).
 */
public class InputDialog extends Dialog {

  /**
   * Key used when setting Widgets data. Identifies this widget name used by
   * dialog to manipulate this field.
   */
  public static final String FIELD_NAME = "FIELD_NAME"; //$NON-NLS-1$

  protected InputDialog( final Shell parentShell ) {
    super( parentShell );
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite mainComp = ( Composite )super.createDialogArea( parent );
    mainComp.setLayout( new GridLayout( 2, false ) );
    return mainComp;
  }
}
