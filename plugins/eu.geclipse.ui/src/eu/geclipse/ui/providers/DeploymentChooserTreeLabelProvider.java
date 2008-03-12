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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.ui.wizards.deployment.DeploymentWizard;
import eu.geclipse.ui.internal.Activator;

/**
 * @author Yifan Zhou
 */
public class DeploymentChooserTreeLabelProvider implements ILabelProvider {
  
  /**
   * Image for root configuration element.
   */
  private Image rootImage;
  
  /**
   * Image for normal configuration element
   */
  private Image typeImage;
  
  public Image getImage( final Object element ) {
    Image image = null;
    URL urlRootElementImage = Activator
      .getDefault().getBundle().getEntry( "icons/deploy16/package_green.png" ); //$NON-NLS-1$
    URL urlTypeImage = Activator
      .getDefault().getBundle().getEntry( "icons/deploy16/flag_pink.png" ); //$NON-NLS-1$
    String category = ( ( IConfigurationElement ) element )
      .getAttribute( DeploymentWizard.EXT_CATEGORY ); 
    if ( category == null || category.equals( "" ) ) { //$NON-NLS-1$
      this.rootImage = ImageDescriptor.createFromURL( urlRootElementImage ).createImage();
      image = this.rootImage; 
    } else {
      this.typeImage = ImageDescriptor.createFromURL( urlTypeImage ).createImage();
      image = this.typeImage;
    }
    return image;
  }

  public String getText( final Object element ) {
    String text = ( ( IConfigurationElement ) element )
      .getAttribute( DeploymentWizard.EXT_NAME ); 
    return text;
  }

  public void addListener( final ILabelProviderListener listener ) {
    // empty implementation
  }

  public void dispose() {
    if ( this.rootImage != null ) {
      this.rootImage.dispose();
    }
    if ( this.typeImage != null ) {
      this.typeImage.dispose();
    }
  }

  public boolean isLabelProperty( final Object element, final String property ) {
    return false;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    // empty implementation
  }
  
}
