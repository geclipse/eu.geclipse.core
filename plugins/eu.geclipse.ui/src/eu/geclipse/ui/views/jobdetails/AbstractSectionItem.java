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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.jobdetails;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;


/**
 * Base implementation for section items
 */
abstract class AbstractSectionItem<ESourceType>
  implements ISectionItem<ESourceType>
{

  private String nameString;

  /**
   * @param nameString item name, which will be shown in view
   */
  public AbstractSectionItem( final String nameString ) {
    super();
    this.nameString = nameString;
  }

  public void createWidgets( final Composite parentComposite,
                             final FormToolkit formToolkit )
  {
    Label label = formToolkit.createLabel( parentComposite, this.nameString
                                                            + ":" ); //$NON-NLS-1$
    label.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    Control valueControl = createValueControl( parentComposite, formToolkit );
    valueControl.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
  }

  protected abstract String getValue( final ESourceType sourceObject );

  protected abstract Control createValueControl( final Composite parentComposite,
                                                 final FormToolkit formToolkit );

  public abstract void refresh( final ESourceType sourceObject );
}
