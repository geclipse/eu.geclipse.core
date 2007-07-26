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


/**
 * Help class allow to detect which {@link AbstractPropertySource} and
 * {@link IProperty} should return property-value
 * 
 * @param <ESourceType> Type of object, for which property is displayed
 */
public class PropertyId<ESourceType> {

  private Class<? extends AbstractPropertySource<?>> sourceClass;
  private IProperty<ESourceType> property;

  /**
   * @param sourceClass Object, for which property is displayed
   * @param property {@link IProperty} object, which handles property operations
   */
  public PropertyId( final Class<? extends AbstractPropertySource<?>> sourceClass,
                     final IProperty<ESourceType> property )
  {
    super();
    this.sourceClass = sourceClass;
    this.property = property;
  }

  /**
   * @return The property
   */
  public IProperty<ESourceType> getProperty() {
    return this.property;
  }

  /**
   * @return TShe sourceClass
   */
  public Class<? extends AbstractPropertySource<?>> getSourceClass() {
    return this.sourceClass;
  }
}
