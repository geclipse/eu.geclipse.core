/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.terminal.internal;

import java.net.URL;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.terminal.ITerminalView;
import eu.geclipse.ui.widgets.DropDownExtensionAction;
import eu.geclipse.ui.wizards.WizardSelectionWizard;

class NewTerminalDropDownAction extends DropDownExtensionAction<ITerminalView> {
  /**
   * Id of the extension point for the entries of the wizard.
   */
  public static final String EXT_ID_NEW_TERMINAL_WIZARD
    = "eu.geclipse.terminal.newTerminalWizards"; //$NON-NLS-1$

  /**
   * Id of the extension point for the entries of the drop down.
   */
  public static final String EXT_ID_TERMINAL_DROP_DOWN 
    = "eu.geclipse.terminal.dropDownEntry"; //$NON-NLS-1$
  
  private ITerminalView terminalView;

  NewTerminalDropDownAction( final ITerminalView terminalView ) {
    super( terminalView, EXT_ID_TERMINAL_DROP_DOWN );
    this.terminalView = terminalView;
    setText( Messages.getString( "NewTerminalDropDownAction.openTerminal" ) ); //$NON-NLS-1$
    setToolTipText( Messages.getString( "NewTerminalDropDownAction.openNewTerminalTab" ) ); //$NON-NLS-1$
  }
  
  @Override
  public void run() {
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/terminalwizard.png" ); //$NON-NLS-1$

    WizardDialog wizardDialog = new WizardDialog(
        Display.getCurrent().getActiveShell(),
        new WizardSelectionWizard<ITerminalView>(
            this.terminalView,
            EXT_ID_NEW_TERMINAL_WIZARD,
            Messages.getString( "NewTerminalDropDownAction.selectConnectionType" ), //$NON-NLS-1$
            Messages.getString( "NewTerminalDropDownAction.title" ), //$NON-NLS-1$
            Messages.getString( "NewTerminalDropDownAction.description" ), //$NON-NLS-1$
            Messages.getString( "NewTerminalDropDownAction.newTerminalSession" ), //$NON-NLS-1$
            ImageDescriptor.createFromURL( imgUrl )
            ) );
    wizardDialog.open();
  }
}
