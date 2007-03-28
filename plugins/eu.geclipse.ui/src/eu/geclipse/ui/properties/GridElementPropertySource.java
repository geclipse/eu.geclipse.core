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
import eu.geclipse.core.model.IGridElement;

/**
 * Simple property source for GridElement.
 * Used when specialized property source is not defined for objects inherited from {@link IGridElement} 
 */
public class GridElementPropertySource extends AbstractPropertySource {

  final IGridElement gridElement;

  /**
   * @param gridElement - object from which properties will be displayed
   */
  public GridElementPropertySource( final IGridElement gridElement ) {
    super();
    this.gridElement = gridElement;
  }

  @Override
  protected IPropertyDescriptor[] createPropertyDescriptors()
  {
    return new IPropertyDescriptor[]{
      createPropName().getDescriptor()
    };
  }

  private AbstractProperty createPropName() {
    return new AbstractProperty( Messages.getString( "GridElementPropertySource.propertyName" ), //$NON-NLS-1$
                                 Messages.getString( "GridElementPropertySource.categoryGeneral" ) ) //$NON-NLS-1$ 
    {

      public String getValue() {
        return GridElementPropertySource.this.gridElement.getName();
      }
    };
  }
}
