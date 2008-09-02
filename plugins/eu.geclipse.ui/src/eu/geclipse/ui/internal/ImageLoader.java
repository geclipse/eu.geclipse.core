/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;


/**
 * This class is used to manage extra images which we don't need/want to have
 * in the plug-in activator, just because they are not used very often.
 */
public class ImageLoader {
  
  // ACL policy icons
  /** The 'allow' icon, a green plus sign */
  public static final String IMG_ACL_ALLOW  = "obj16/acl_allow_tbl.gif"; //$NON-NLS-1$
  /** The 'deny' icon, a red cross */
  public static final String IMG_ACL_DENY   = "obj16/acl_deny_tbl.gif"; //$NON-NLS-1$
  
  // ACL Actor type icons
  /** The 'anyone' icon, a small world */
  public static final String IMG_ACL_ANYONE = "obj16/acl_anyone_tbl.gif"; //$NON-NLS-1$
  /** The 'group' icon, three blue silhouettes */
  public static final String IMG_ACL_GROUP  = "obj16/acl_group_tbl.gif"; //$NON-NLS-1$
  /** The 'ca' icon, three persons */
  public static final String IMG_ACL_CA     = "obj16/acl_ca_tbl.gif"; //$NON-NLS-1$
  /** The 'user' icon, a person */
  public static final String IMG_ACL_USER   = "obj16/acl_user_tbl.gif"; //$NON-NLS-1$
  /** The 'SMAL' icon, an envelope */
  public static final String IMG_ACL_SAML   = "obj16/acl_saml_tbl.gif"; //$NON-NLS-1$
  
  
  /** This plug-in's shared image register */
  private static ImageRegistry imageRegistry;
  
  /** The URL of the icons folder */
  private static URL iconPathURL;
  
  static {
    imageRegistry = Activator.getDefault().getImageRegistry();
    iconPathURL = Platform.getBundle( Activator.PLUGIN_ID ).getEntry( "/icons/" ); //$NON-NLS-1$
    createManaged( IMG_ACL_ALLOW );
    createManaged( IMG_ACL_DENY );
    createManaged( IMG_ACL_ANYONE );
    createManaged( IMG_ACL_GROUP);
    createManaged( IMG_ACL_CA );
    createManaged( IMG_ACL_USER );
    createManaged( IMG_ACL_SAML );
  }
  
  
  /**
   * Returns the image corresponding to the given key.
   * 
   * @param key the key used to store the image.
   * @return the managed image from the register.
   */
  public static Image get( final String key ) {
    return imageRegistry.get( key );
  }
  
  /**
   * Creates an image and adds it to this plug-in's image register.
   * 
   * @param name the relative path of the image file, also used as identifier.
   */
  private static void createManaged( final String name ) {
    
    URL url = null;
    try {
      url = new URL( iconPathURL, name );
    } catch ( MalformedURLException e ) {
      Activator.logException( e );
    }
    
    ImageDescriptor result = ImageDescriptor.createFromURL( url );
    imageRegistry.put( name, result );
  }

}
