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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.simpleTest.ISimpleTest;

/**
 * The abstract base class for the simple tests.
 * 
 * @author hgjermund
 */
public abstract class AbstractSimpleTestDialog extends Dialog {

  /**
   * The test for which to perform.
   */
  protected ISimpleTest test;
  
  /**
   * The list of resources which this test should be applied to.
   */
  protected List< IGridResource > resources; 

  /**
   * Construct a new dialog from the specified test.
   * 
   * @param test The <code>ISimpleTest</code> for which to create the dialog for.
   * @param resources The resources that this test should be applied to.
   * @param parentShell  The parent shell of this dialog.
   */
  public AbstractSimpleTestDialog( final ISimpleTest test, 
                                   final List< IGridResource > resources, 
                                   final Shell parentShell ) {
    super( parentShell );
    
    setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
                   | SWT.RESIZE | SWT.MIN | SWT.MAX );
    
    this.test = test;
    this.resources = resources;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
   */
  @Override
  protected IDialogSettings getDialogBoundsSettings() {
    return eu.geclipse.ui.internal.Activator.getDefault().getDialogSettings();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar()
   */
  @Override
  protected void createButtonsForButtonBar( final Composite parent) {
    createButton(parent, IDialogConstants.CANCEL_ID,  
                 Messages.getString( "PingTestDialog.closeButton" ), //$NON-NLS-1$ 
                 false);
  }
}
