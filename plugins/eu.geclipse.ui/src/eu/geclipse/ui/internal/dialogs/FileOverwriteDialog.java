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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.internal.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


/**
 * @author nloulloud
 *
 */
public class FileOverwriteDialog {

 protected Shell shell = null;  
 protected boolean returnValue = false; 
 protected String fileName = null;
 
 /**
 * @param shell
 * @param fileName 
 */
  public FileOverwriteDialog( final Shell shell, final String fileName ){
     this.shell = shell;
     this.fileName = fileName;
   }

  /**
   * Makes the dialog visible and brings it to the front
   * of the display.
   *
   * @return the ID of the button that was selected to dismiss the
   *         message box (e.g. SWT.OK, SWT.CANCEL, etc.)
   */
  public boolean open(){
//      
    Display.getDefault().syncExec (new Runnable () {
      public void run() {
        if ( FileOverwriteDialog.this.shell != null && !FileOverwriteDialog.this.shell.isDisposed() ){
          MessageBox mb = new MessageBox( FileOverwriteDialog.this.shell, SWT.ICON_WARNING
                                          | SWT.YES | SWT.NO);
           
           mb.setMessage( FileOverwriteDialog.this.fileName + " already exists. Do you want to replace it?"); //$NON-NLS-1$
           if ( mb.open() == SWT.YES ){
             FileOverwriteDialog.this.returnValue = true;
           }           
        }

      }
      
    });
    
    return this.returnValue;
  }

}
