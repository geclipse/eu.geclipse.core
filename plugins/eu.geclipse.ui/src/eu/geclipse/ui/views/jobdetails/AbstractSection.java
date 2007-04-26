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

import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;


/**
 * Abstract class for jobdetails sections
 */
abstract class AbstractSection<ESourceType> implements ISection<ESourceType> {

  private String nameString;
  private List<ISectionItem<ESourceType>> sectionItemsList;

  AbstractSection( final String nameString ) {
    super();
    this.nameString = nameString;
  }

  protected abstract List<ISectionItem<ESourceType>> createSectionItems();

  protected List<ISectionItem<ESourceType>> getSectionItems() {
    if( this.sectionItemsList == null ) {
      this.sectionItemsList = createSectionItems();
    }
    return this.sectionItemsList;
  }

  public void createWidgets( final Composite parentComposite,
                             final FormToolkit formToolkit )
  {
    Section section = formToolkit.createSection( parentComposite,
                                                 ExpandableComposite.TWISTIE
                                                     | ExpandableComposite.TITLE_BAR );
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.verticalAlignment = SWT.TOP;
    section.setLayoutData( gridData );
    section.setText( this.nameString );
    section.setExpanded( true );
    Composite clientComposite = formToolkit.createComposite( section );
    clientComposite.setLayout( new GridLayout( 2, false ) );
    section.setClient( clientComposite );
    for( ISectionItem<ESourceType> sectionItem : this.getSectionItems() ) {
      sectionItem.createWidgets( clientComposite, formToolkit );
    }
  }

  /**
   * Refresh view section fields during initialization of when job was changed
   * 
   * @param source - source object containing job data
   */
  public void refresh( final ESourceType source ) {
    for( ISectionItem<ESourceType> sectionItem : this.getSectionItems() ) {
      sectionItem.refresh( source );
    }
  }
}
