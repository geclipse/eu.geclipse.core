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
 *    Martin Polak GUP, JKU - initial implementation
 *****************************************************************************/

package eu.geclipse.ui.providers;

import java.net.URL;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import eu.geclipse.core.monitoring.GridProcessMonitor;
import eu.geclipse.ui.internal.Activator;


public class ProcessViewLabelProvider extends LabelProvider
{  
  private Image computingImage;
  private Image processImage;
  
  public ProcessViewLabelProvider(){
    loadImages();
  }
  
  @Override
  public String getText( final Object o ) {
    String label = o.toString();
    
    if (o instanceof GridProcessMonitor) {
       label = ((GridProcessMonitor)o).getTarget().getHost();
    } else if (o instanceof Integer) {
      // TODO do something fancier here?
    }
    return label;
  }
  
  @Override
  public Image getImage(final Object o){
    
    if (o instanceof GridProcessMonitor){
      return this.computingImage;
    }
    return this.processImage;
  }
  
  void loadImages(){
    URL compUrl = Activator.getDefault().getBundle().getEntry( "icons/obj16/computing_obj.gif" ); //$NON-NLS-1$
    URL procUrl = Activator.getDefault().getBundle().getEntry( "icons/obj16/job_file_obj.gif" ); //$NON-NLS-1$
    ImageDescriptor compdesc = ImageDescriptor.createFromURL( compUrl ) ;
    ImageDescriptor procdesc = ImageDescriptor.createFromURL( procUrl );
    this.computingImage = compdesc.createImage();
    this.processImage = procdesc.createImage();
  }

}
