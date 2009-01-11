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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.actions;

import org.eclipse.core.expressions.PropertyTester;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IMountable.MountPointID;

/**
 * This {@link PropertyTester} tests if an {@link IGridConnectionElement} is
 * representing a file in an <code>S3</code> bucket, which has got the name
 * <code>image.manifest.xml</code>.
 * 
 * @author Moritz Post
 */
public class ConnectionElementPropertyTest extends PropertyTester {

  /** The scheme of the S3-file system implementation. */
  public static final String S3_SCHEME = "s3"; //$NON-NLS-1$

  public boolean test( final Object receiver,
                       final String property,
                       final Object[] args,
                       final Object expectedValue )
  {
    if( receiver instanceof IGridConnectionElement ) {
      IGridConnectionElement connectionElement = ( IGridConnectionElement )receiver;

      MountPointID[] mountPointIDs = connectionElement.getMountPointIDs();

      if( mountPointIDs.length > 0 ) {
        String scheme = mountPointIDs[ 0 ].getScheme();
        if( scheme.equals( ConnectionElementPropertyTest.S3_SCHEME ) ) {

          String fileName = connectionElement.getPath().lastSegment();
          if( fileName.equals( expectedValue ) ) {
            return true;
          }
        }

      }

    }
    return false;
  }
}
