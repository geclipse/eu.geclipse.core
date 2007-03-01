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

package eu.geclipse.gvid.internal.views;

import java.net.URL;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.gvid.Activator;
import eu.geclipse.gvid.IGVidView;
import eu.geclipse.ui.widgets.DropDownExtensionAction;
import eu.geclipse.ui.wizards.WizardSelectionWizard;

class NewGVidDropDownAction extends DropDownExtensionAction<IGVidView> {
  /**
   * Id of the extension point for the entries of the wizard.
   */
  public static final String EXT_ID_NEW_GVID_WIZARD
    = "eu.geclipse.gvid.newGVidWizards"; //$NON-NLS-1$

  /**
   * Id of the extension point for the entries of the drop down.
   */
  public static final String EXT_ID_GVID_DROP_DOWN 
    = "eu.geclipse.gvid.dropDownEntry"; //$NON-NLS-1$
  private IGVidView gvidView;
  
  /**
   * Id of the extension point for the entries of the drop down.
   */
  NewGVidDropDownAction( final IGVidView gvidView ) {
    super( gvidView, EXT_ID_GVID_DROP_DOWN );
    this.gvidView = gvidView;
    setText( Messages.getString( "NewGVidDropDownAction.openGVidPage" ) ); //$NON-NLS-1$
    setToolTipText( Messages.getString( "NewGVidDropDownAction.openNewGVidTab" ) ); //$NON-NLS-1$
  }

  @Override
  public void run() {
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/gvidwizard.png" ); //$NON-NLS-1$

    WizardDialog wizardDialog = new WizardDialog(
        Display.getCurrent().getActiveShell(),
        new WizardSelectionWizard<IGVidView>(
            this.gvidView,
            EXT_ID_NEW_GVID_WIZARD,
            Messages.getString( "NewGVidDropDownAction.selectConnectionType" ), //$NON-NLS-1$
            Messages.getString( "NewGVidDropDownAction.title" ), //$NON-NLS-1$
            Messages.getString( "NewGVidDropDownAction.description" ), //$NON-NLS-1$
            Messages.getString( "NewGVidDropDownAction.newGVidSession" ), //$NON-NLS-1$
            ImageDescriptor.createFromURL( imgUrl )
            ) );
    wizardDialog.open();
  }
}
