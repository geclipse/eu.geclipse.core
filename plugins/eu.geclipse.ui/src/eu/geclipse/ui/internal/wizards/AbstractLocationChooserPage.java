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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.wizards;

import java.net.URI;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.auth.ICaCertificateLoader;

public abstract class AbstractLocationChooserPage extends WizardPage {
  
  private CertificateLoaderSelectionPage loaderSelectionPage;
  
  public AbstractLocationChooserPage( final String pageName,
                                      final String title,
                                      final ImageDescriptor titleImage,
                                      final CertificateLoaderSelectionPage loaderSelectionPage ) {
    super( pageName, title, titleImage );
    this.loaderSelectionPage = loaderSelectionPage;
  }
  
  public ICaCertificateLoader getLoader() {
    return this.loaderSelectionPage.getSelectedLoader();
  }
  
  public abstract URI getSelectedLocation();

}
