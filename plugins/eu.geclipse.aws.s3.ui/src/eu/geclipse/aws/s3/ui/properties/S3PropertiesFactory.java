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

package eu.geclipse.aws.s3.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.aws.s3.S3BucketStorage;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IPropertiesFactory;

/**
 * This properties factory provides all the {@link IPropertySource}s used
 * throughout the S3 infrastructure.
 * 
 * @author Moritz Post
 */
public class S3PropertiesFactory implements IPropertiesFactory {

  /** The list of {@link IPropertySource}s this factory supports. */
  private ArrayList<AbstractPropertySource<?>> sourcesList;

  public List<AbstractPropertySource<?>> getPropertySources( final Object sourceObject )
  {
    if( this.sourcesList == null ) {
      this.sourcesList = new ArrayList<AbstractPropertySource<?>>();

      if( sourceObject instanceof S3BucketStorage ) {
        this.sourcesList.add( new S3BucketStoragePropertySource( ( S3BucketStorage )sourceObject ) );
      }

    }
    return this.sourcesList;

  }
}
