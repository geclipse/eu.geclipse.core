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
 *    Mateusz Pabis (PSNC) - initial API and implementation
 *****************************************************************************/ 
package eu.geclipse.ui.views.gexplorer;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Label and image provider for gExplorer View 
 * TODO implement data providing for different views (i.e. table)
 * 
 * @author Mateusz Pabis
 */
public class GExplorerLabelProvider implements ILabelProvider {
  
  ISharedImages sharedImages;
  
  public Image getImage( final Object element ) {
    // TODO add more sophisticated images
    if (this.sharedImages == null) {
      this.sharedImages = PlatformUI.getWorkbench().getSharedImages();
    }
    Image fileImage = this.sharedImages.getImage( ISharedImages.IMG_OBJS_ERROR_TSK);
    
    if ( element != null ) {
      if ( element instanceof ResourceNode ) { 
        Object value =  ( ( ResourceNode ) element ).getValue();
        if ( value instanceof IFileStore) {
          IFileStore gf = (IFileStore) value;
          if ( !gf.fetchInfo().isDirectory() ) {
            fileImage = this.sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
          }  
          if ( gf.fetchInfo().isDirectory() ) {
            fileImage = this.sharedImages.getImage( ISharedImages.IMG_OBJ_FOLDER );
          }
        }
      }
    }
    
    return fileImage;
  }

  public String getText( final Object element ) {
    String result = "null"; //$NON-NLS-1$
    if ( element != null && element instanceof ResourceNode ) {
      result = ( ( ResourceNode )( element ) ).toString();
    }
    return ( result );
  }

  public void addListener( final ILabelProviderListener listener ) {
    // TODO Auto-generated method stub
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public boolean isLabelProperty( final Object element, final String property ) {
    // TODO Auto-generated method stub
    return false;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    // TODO Auto-generated method stub
  }
}
