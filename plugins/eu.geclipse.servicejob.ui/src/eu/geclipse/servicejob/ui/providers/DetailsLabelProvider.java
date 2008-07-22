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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.providers;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.servicejob.ui.preferences.PreferencesManager;

/**
 * Label provider for Operator's Jobs details view.
 */
public class DetailsLabelProvider
  implements ITableLabelProvider, ITableColorProvider
{

  Map<Integer, String> indexVsNames = new HashMap<Integer, String>();
  IServiceJob root;
  String name;

  public Image getColumnImage( final Object element, final int columnIndex ) {
    return null;
  }

  @SuppressWarnings("unchecked")
  public String getColumnText( final Object element, final int columnIndex ) {
    String result = ""; //$NON-NLS-1$
    if( columnIndex == 0 ) {
      if( element instanceof String ) {
        result = ( String )element;
      } else if( element instanceof List ) {
        result = DateFormat.getDateTimeInstance()
          .format( ( ( IServiceJobResult )( ( List )element ).get( 0 ) ).getRunDate() );
      }
    } else if( element instanceof String ) {
      IServiceJobResult testResult = this.root.getSingleTestResult( this.indexVsNames.get( Integer.valueOf( columnIndex ) ),
                                                                  ( String )element,
                                                                  this.root.getLastUpdate( ( String )element ) );
      if( testResult == null ) {
        result = "N/A"; //$NON-NLS-1$
      } else {
        result = testResult.getResultEnum();
      }
    } else if( element instanceof List ) {
      for( IServiceJobResult testResult : ( List<IServiceJobResult> )element ) {
        if( testResult.getSubTestName()
          .equals( this.indexVsNames.get( Integer.valueOf( columnIndex ) ) ) )
        {
          result = testResult.getResultSummary();
          break;
        }
      }
    }
    return result;
  }

  public void addListener( final ILabelProviderListener listener ) {
    // empty implementation
  }

  public void dispose() {
    // empty implementation
  }

  public boolean isLabelProperty( final Object element, final String property )
  {
    return false;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    // empty implementation
  }

  /**
   * Changes Operator's Job for which labels are provided by this labels
   * provider.
   * 
   * @param newInput new Operator's Job this label provider will provide labels
   *            for
   */
  public void changeRoot( final IServiceJob newInput ) {
    this.root = newInput;
    this.indexVsNames = new HashMap<Integer, String>();
    int i = 1;
    if( newInput != null ) {
      for( String testName : this.root.getSingleTestsNames() ) {
        this.indexVsNames.put( Integer.valueOf( i ), testName );
        i++;
      }
    }
  }

  public Color getBackground( final Object element, final int columnIndex ) {
    // background is default color
    return null;
  }

  @SuppressWarnings("unchecked")
  public Color getForeground( final Object element, final int columnIndex ) {
    Color result = null;
    if( columnIndex != 0 && element instanceof List ) {
      List<IServiceJobResult> testResultList = ( List<IServiceJobResult> )element;
      if( columnIndex - 1 < testResultList.size() ) {
        IServiceJobResult testResult = testResultList.get( columnIndex - 1 );
        if( testResult != null
            && testResult.getSubTestName()
              .equals( this.indexVsNames.get( new Integer( columnIndex ) ) ) )
        {
          result = PreferencesManager.getManager()
            .getColor( testResult.getResultEnum() );
        } else if( testResult != null ) {
          for( IServiceJobResult testRes : testResultList ) {
            if( this.indexVsNames.get( new Integer( columnIndex ) )
              .equals( testRes.getSubTestName() ) )
            {
              result = PreferencesManager.getManager()
                .getColor( testRes.getResultEnum() );
              break;
            }
          }
        }
      }
    } else {
      result = PreferencesManager.getManager().getColor( "N/A" ); //$NON-NLS-1$
    }
    if( columnIndex != 0 && element instanceof String ) {
      IServiceJobResult testResult = this.root.getSingleTestResult( this.indexVsNames.get( Integer.valueOf( columnIndex ) ),
                                                                  ( String )element,
                                                                  this.root.getLastUpdate( ( String )element ) );
      if( testResult == null ) {
        result = PreferencesManager.getManager().getColor( "N/A" ); //$NON-NLS-1$
      } else {
        result = PreferencesManager.getManager()
          .getColor( testResult.getResultEnum() );
      }
    }
    return result;
  }
}
