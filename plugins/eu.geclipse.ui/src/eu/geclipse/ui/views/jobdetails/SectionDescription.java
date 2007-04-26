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
public class SectionDescription extends AbstractSection<IGridJob> {

  SectionDescription() {
    super( Messages.SectionDescription_name );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.AbstractSection#createSectionItems()
   */
  @Override
  protected List<ISectionItem<IGridJob>> createSectionItems()
  {
    List<ISectionItem<IGridJob>> itemsList = new ArrayList<ISectionItem<IGridJob>>();
    itemsList.add( createExe() );
    itemsList.add( createExeArgs() );
    itemsList.add( createInput() );
    itemsList.add( createOutput() );
    itemsList.add( createDescription() );
    return itemsList;
  }

  ISectionItem<IGridJob> createExe() {
    return new TextSectionItem<IGridJob>( Messages.SectionDescription_exe ) {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getJobDescription() != null ) {
          valueString = gridJob.getJobDescription().getExecutable();
        }
        return valueString;
      }
    };
  }

  ISectionItem<IGridJob> createExeArgs() {
    return new TextSectionItem<IGridJob>( Messages.SectionDescription_exeArgs )
    {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getJobDescription() != null
            && gridJob.getJobDescription().getExecutableArguments() != null )
        {
          StringBuilder stringBuilder = new StringBuilder();
          List<String> argList = gridJob.getJobDescription()
            .getExecutableArguments();
          for( String argString : argList ) {
            if( stringBuilder.length() > 0 ) {
              stringBuilder.append( ", " ); //$NON-NLS-1$
            }
            stringBuilder.append( argString );
          }
          valueString = stringBuilder.toString();
        }
        return valueString;
      }
    };
  }

  ISectionItem<IGridJob> createInput() {
    return new TextSectionItem<IGridJob>( Messages.SectionDescription_input ) {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getJobDescription() != null ) {
          valueString = gridJob.getJobDescription().getInput();
        }
        return valueString;
      }
    };
  }

  ISectionItem<IGridJob> createOutput() {
    return new TextSectionItem<IGridJob>( Messages.SectionDescription_output ) {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getJobDescription() != null ) {
          valueString = gridJob.getJobDescription().getOutput();
        }
        return valueString;
      }
    };
  }

  ISectionItem<IGridJob> createDescription() {
    return new TextSectionItem<IGridJob>( Messages.SectionDescription_description )
    {

      @Override
      protected String getValue( final IGridJob gridJob )
      {
        String valueString = null;
        if( gridJob.getJobDescription() != null ) {
          valueString = gridJob.getJobDescription().getDescription(); 
        }
        return valueString;
      }
    };
  }
}
