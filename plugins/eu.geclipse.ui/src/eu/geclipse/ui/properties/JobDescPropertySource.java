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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import eu.geclipse.core.model.IGridJobDescription;

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
  protected IPropertyDescriptor[] createPropertyDescriptors()
  {
    return new IPropertyDescriptor[]{
      createPropertyDescription().getDescriptor(),
      createPropertyExecutable().getDescriptor(),
      createPropertyExecutableArg().getDescriptor()
    };
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
        if( JobDescPropertySource.this.jobDescription.getExecutableArguments() != null )
        {
          JobDescPropertySource.this.jobDescription.getExecutableArguments()
            .toString();
        }
        return null;
      }
    };
  }
}
