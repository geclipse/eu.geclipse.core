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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.ui.wizards.deployment.DeploymentWizard;

/**
 * 
 * @author Yifan Zhou
 *
 */
public class DeploymentChooserTreeContentProvider implements ITreeContentProvider {
  
  public Object[] getChildren( final Object parentElement ) {
    Object[] children = null;
    if ( hasChildren( parentElement ) ) {
      List < IConfigurationElement > elements = new ArrayList< IConfigurationElement >();
      IExtension extension = ( ( IConfigurationElement ) parentElement ).getDeclaringExtension();
      IConfigurationElement[] configElements = extension.getConfigurationElements();
      for ( IConfigurationElement configElement : configElements ) {
        String category = configElement
          .getAttribute( DeploymentWizard.EXT_CATEGORY );
        String id = ( ( IConfigurationElement ) parentElement )
          .getAttribute( DeploymentWizard.EXT_ID ); 
        if ( category != null && !category.equals( "" ) && category.equals( id ) ) { //$NON-NLS-1$
          elements.add( configElement );
        }
      }
      children = elements.toArray();
    } 
    return ( children == null ) ? new Object[0] : children;
  }

  public Object getParent( final Object element ) {
    Object parent = null;
    String category = ( ( IConfigurationElement ) element )
      .getAttribute( DeploymentWizard.EXT_CATEGORY ); 
    if ( category != null && category != "" ) { //$NON-NLS-1$
      IExtension extension = ( ( IConfigurationElement ) element ).getDeclaringExtension();
      IConfigurationElement[] configElements = extension.getConfigurationElements();
      for ( IConfigurationElement configElement : configElements ) {
        String id = configElement
          .getAttribute( DeploymentWizard.EXT_ID );
        if ( id.equals( category ) ) {
          parent = configElement;
        }
      }
    }  
    return ( parent == null ) ? new Object[0] : parent;
  }

  public boolean hasChildren( final Object element ) {
    String category = ( ( IConfigurationElement ) element )
      .getAttribute( DeploymentWizard.EXT_CATEGORY );
    return ( category == null || category.equals( "" ) ) ? true : false; //$NON-NLS-1$
  }

  public Object[] getElements( final Object inputElement ) {
    List < IConfigurationElement > elements = new ArrayList< IConfigurationElement >();
    IExtension[] extensions = ( ( IExtensionPoint ) inputElement ).getExtensions();
    for ( IExtension extension : extensions ) {
        IConfigurationElement[] configElements = extension.getConfigurationElements();
        for ( IConfigurationElement configElement : configElements ) {
          String category = configElement
            .getAttribute( DeploymentWizard.EXT_CATEGORY ); 
          if ( category == null || category == "" ) { //$NON-NLS-1$
            elements.add( configElement );
          }
        }
      }
    return elements.toArray();
  }

  public void dispose() {
    // empty implementation
  }

  public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput ) {
     // empty implementation      
  }
  
}
