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

package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.core.accesscontrol.ActorType;
import eu.geclipse.core.accesscontrol.IACLEntry;
import eu.geclipse.core.accesscontrol.IACLPolicy;
import eu.geclipse.ui.internal.ImageLoader;


/**
 * A table label provider for displaying IACLEntry objects.
 * 
 * @author agarcia
 */
public class ACLEntryLabelProvider extends LabelProvider
  implements ITableLabelProvider
{

  // Columns
  private static final int COL_POLICY = 0;
  private static final int COL_CAPABILITY = 1;
  private static final int COL_ACTOR_TYPE = 2;
  private static final int COL_ACTOR_ID = 3;
  private static final int COL_ACTOR_CA = 4;
  
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
   */
  public String getColumnText( final Object element, final int columnIndex ) {
    
    String result = null;
    
    if ( element instanceof IACLEntry ) {
      
      IACLEntry entry = ( IACLEntry ) element;
      switch ( columnIndex ) {
        case COL_POLICY:
          result = entry.getPolicy().getName();
          break;
        case COL_CAPABILITY:
          result = entry.getCapability().getName();
          break;
        case COL_ACTOR_TYPE:
          result = entry.getActor().getActorType().getName();
          break;
        case COL_ACTOR_ID:
          result = entry.getActor().getID();
          break;
        case COL_ACTOR_CA:
          result = entry.getActor().getCA();
          break;
        default:
          break;
      }
      
    }
    
    if ( result == null ) {
      result = ""; //$NON-NLS-1$
    }
    
    return result;
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
   */
  public Image getColumnImage( final Object element, final int columnIndex ) {
    
    Image img = null;
    
    if ( element instanceof IACLEntry ) {
      
      IACLEntry entry = ( IACLEntry ) element;
      
      switch ( columnIndex ) {
        case COL_POLICY:
          short pType = entry.getPolicy().getType();
          img = getPolicyImage( pType );
          break;
        case COL_CAPABILITY:
          break;
        case COL_ACTOR_TYPE:
          ActorType type = entry.getActor().getActorType();
          img = getActorTypeImage( type );
          break;
        case COL_ACTOR_ID:
          break;
        case COL_ACTOR_CA:
          break;
        default:
          break;
      }
    }
    
    return img;
  }
  
  /**
   * Helper method to select the image for the policy column.
   * 
   * @param pType the flag identifying the policy type.
   * @return the corresponding Image, or <code>null</code> if none was found.
   */
  private Image getPolicyImage( final short pType ) {
    Image img = null;
    
    if ( pType == IACLPolicy.TYPE_ALLOW ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_ALLOW );
    } else if ( pType == IACLPolicy.TYPE_DENY ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_DENY );
    }
    return img;
  }
  
  /**
   * Helper method to select the image for the actor type column.
   * 
   * @param type an {@link ActorType}.
   * @return an Image, or <code>null</code> if none was found.
   */
  private Image getActorTypeImage( final ActorType type ) {
    Image img = null;
    
    if ( ( type == ActorType.ANYBODY )
      || ( type == ActorType.CA_ANY_DN_ANY ) ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_ANYONE );
    } else if ( ( type == ActorType.GROUP_NAME )
             || ( type == ActorType.GROUP_PATTERN )
             || ( type == ActorType.USER_PATTERN ) ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_GROUP );
    } else if ( ( type == ActorType.CA_NAME_DN_ANY ) 
             || ( type == ActorType.CA_NAME_DN_PATTERN ) ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_CA );
    } else if ( ( type == ActorType.CA_NAME_DN_NAME )
             || ( type == ActorType.USER_NAME )
             || ( type == ActorType.USER_EMAIL ) ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_USER );
    } else if ( type == ActorType.SAML_ATTRIBUTE ) {
      img = ImageLoader.get( ImageLoader.IMG_ACL_SAML );
    }
    return img;
  }

}
