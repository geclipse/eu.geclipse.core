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
import eu.geclipse.core.model.IGridJob;


/**
 * Abstract class for jobdetails sections
 * @param <ESourceType> type of object, from which data for this section will be obtained
 */
public abstract class AbstractSection<ESourceType> implements ISection {

  private IViewConfiguration viewConfiguration;
  private String nameString;
  private List<ISectionItem<ESourceType>> sectionItemsList;
  private Section section;

  /**
   * @param nameString name for section
   * @param viewConfiguration object containing data about current configuration for job-details view
   */
  public AbstractSection( final String nameString,
                          final IViewConfiguration viewConfiguration )
  {
    super();
    this.nameString = nameString;
    this.viewConfiguration = viewConfiguration;
  }

  abstract protected List<ISectionItem<ESourceType>> createSectionItems();
  
  abstract protected ESourceType getSourceObject( final IGridJob gridJob );

  protected List<ISectionItem<ESourceType>> getSectionItems() {
    if( this.sectionItemsList == null ) {
      this.sectionItemsList = createSectionItems();
    }
    return this.sectionItemsList;
  }

  public void createWidgets( final Composite parentComposite,
                             final FormToolkit formToolkit )
  {
    this.section = formToolkit.createSection( parentComposite,
                                              ExpandableComposite.TWISTIE
                                                  | ExpandableComposite.TITLE_BAR );
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.verticalAlignment = SWT.TOP;
    this.section.setLayoutData( gridData );
    this.section.setText( this.nameString );
    this.section.setExpanded( true );
    Composite clientComposite = formToolkit.createComposite( this.section );
    clientComposite.setLayout( new GridLayout( 2, false ) );
    this.section.setClient( clientComposite );
    for( ISectionItem<ESourceType> sectionItem : this.getSectionItems() ) {
      sectionItem.createWidgets( clientComposite, formToolkit );
    }
    setVisible( false );
  }

  

  /**
   * Refresh view section fields during initialization of when job was changed
   * 
   * @param gridJob - source object containing job data
   */
  public void refresh( final IGridJob gridJob ) {
    boolean anyValueSpecified = false, valueSpecified;
    boolean showEmptyValues = this.viewConfiguration.isShowEmptyEnabled();
    ESourceType source = null;
    if( gridJob != null ) {
      source = getSourceObject( gridJob );
    }
    for( ISectionItem<ESourceType> sectionItem : this.getSectionItems() ) {
      valueSpecified = sectionItem.refresh( source );
      anyValueSpecified |= valueSpecified;
      sectionItem.setVisible( valueSpecified || showEmptyValues );
    }
    setVisible( anyValueSpecified || showEmptyValues );
  }

  void setVisible( final boolean visible ) {
    this.section.setVisible( visible );
  }
}
