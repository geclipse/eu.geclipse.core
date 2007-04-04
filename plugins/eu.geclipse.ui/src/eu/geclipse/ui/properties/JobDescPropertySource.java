/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Mariusz Wojtysiak
 *           
 *****************************************************************************/
package eu.geclipse.ui.properties;

import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.impl.JSDLJobDescription;

/**
 * Property source for {@link IGridJobDescription}
 */
public class JobDescPropertySource extends AbstractPropertySource {

  IGridJobDescription jobDescription;

  /**
   * @param jobDescription - object for which properties will be displayed
   */
  public JobDescPropertySource( final IGridJobDescription jobDescription ) {
    super();
    this.jobDescription = jobDescription;
  }

  @Override
  protected IProperty[] createProperties()
  {
    IProperty[] properties =  new IProperty[]{
      createPropertyDescription(),
      createPropertyExecutable(),
      createPropertyExecutableArg()
    };
    
    if( this.jobDescription instanceof JSDLJobDescription ) {
      JsdlPropertySource jsdlPropertySource = new JsdlPropertySource( ( JSDLJobDescription ) this.jobDescription );
      properties = joinProperties( properties, jsdlPropertySource.getProperties() );
    }

    return properties;
  }

  IProperty createPropertyDescription() {
    return new AbstractProperty( Messages.getString( "JobDescPropertySource.propertyDescription" ), //$NON-NLS-1$
                                 Messages.getString( "JobDescPropertySource.categoryDescription" ) ) { //$NON-NLS-1$

      public String getValue() {
        return JobDescPropertySource.this.jobDescription.getDescription();
      }
    };
  }

  IProperty createPropertyExecutable() {
    return new AbstractProperty( Messages.getString( "JobDescPropertySource.propertyExecutable" ), //$NON-NLS-1$
                                 Messages.getString( "JobDescPropertySource.categoryDescription" ) ) { //$NON-NLS-1$

      public String getValue() {
        return JobDescPropertySource.this.jobDescription.getExecutable();
      }
    };
  }

  IProperty createPropertyExecutableArg() {
    return new AbstractProperty( Messages.getString( "JobDescPropertySource.propertyExecArgs" ), //$NON-NLS-1$ 
                                 Messages.getString( "JobDescPropertySource.categoryDescription" ) ) { //$NON-NLS-1$

      public String getValue() {
        String argString = null;
        if( JobDescPropertySource.this.jobDescription.getExecutableArguments() != null )
        {
          argString = JobDescPropertySource.this.jobDescription.getExecutableArguments().toString();
        }
        return argString;
      }
    };
  }
}
