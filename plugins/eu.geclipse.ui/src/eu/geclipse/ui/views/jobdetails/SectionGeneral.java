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

import java.util.ArrayList;
import java.util.List;
import eu.geclipse.core.model.IGridJob;

/**
 *
 */
public class SectionGeneral extends AbstractSection<IGridJob> {

  SectionGeneral( final IViewConfiguration viewConfiguration ) {
    super( Messages.SectionGeneral_general, viewConfiguration );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.AbstractSection#createSectionItems()
   */
  @Override
  protected List<ISectionItem<IGridJob>> createSectionItems()
  {
    List<ISectionItem<IGridJob>> itemsList = new ArrayList<ISectionItem<IGridJob>>();
    itemsList.add( createName() );
    itemsList.add( createIdentifier() );
    itemsList.add( createStatus() );
    return itemsList;
  }

  private ISectionItem<IGridJob> createName() {
    return new TextSectionItem<IGridJob>( Messages.SectionGeneral_name ) {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        return gridJob.getName();
      }
    };
  }

  private ISectionItem<IGridJob> createIdentifier() {
    return new TextSectionItem<IGridJob>( Messages.SectionGeneral_id ) {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getID() != null ) {
          valueString = gridJob.getID().getJobID();
        }
        return valueString;
      }
    };
  }

  private ISectionItem<IGridJob> createStatus() {
    return new TextSectionItem<IGridJob>( Messages.SectionGeneral_status ) {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getJobStatus() != null ) {
          valueString = gridJob.getJobStatus().getName();
        }
        return valueString;
      }
    };
  }

  @Override
  protected IGridJob getSourceObject( final IGridJob gridJob )
  {
    return gridJob;
  }
}
