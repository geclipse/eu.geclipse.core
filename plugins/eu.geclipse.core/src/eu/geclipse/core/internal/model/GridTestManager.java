/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium 
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
 *      - Szymon Mueller
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.internal.model.AbstractGridElementManager;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridTest;
import eu.geclipse.core.model.IGridTestManager;
import eu.geclipse.core.model.IGridTestStatusListener;

/**
 * Abstract class that makes some core's internal interfaces available for
 * external plug-in.
 */
public class GridTestManager
  extends
  AbstractGridElementManager implements IGridModelListener, IGridTestManager, IGridTestStatusListener
{
  /**
   * The name of this manager.
   */
  private static final String NAME = ".tests"; //$NON-NLS-1$

  private static GridTestManager singleton;
  
  private List<IGridTest> tests = null;
  
  private List<IGridTestStatusListener> listeners;
  
  public GridTestManager() {
    super();
    this.tests = new ArrayList<IGridTest>();
    this.listeners = new ArrayList<IGridTestStatusListener>();
    
  }
  
  @Override
  public boolean addElement( final IGridElement element ) throws GridModelException {
    boolean flag;
    flag = super.addElement( element );
    if ( element instanceof IGridTest ) {
      IGridTest test = (IGridTest) element;
      this.tests.add( test );
    }
    return flag;
  }
  
  @Override
  public boolean removeElement( final IGridElement element ) {
    boolean flag;
    flag = super.removeElement( element );
    if ( element instanceof IGridTest ) {
      this.tests.remove( element );
    }
    return flag;
  }
  
  @Override
  public void addGridModelListener( final IGridModelListener listener ) {
    //TODO
  }
  
  public void addTestStatusListener( final IGridTestStatusListener listener ) {
    this.listeners.add( listener );
  }
  
  public void addTest( final IGridTest test ) {
    this.tests.add( test );
  }
  
  public void gridModelChanged( final IGridModelEvent event ) {
    if( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) {
      IGridElement[] removedElements = event.getElements();
      for( IGridElement elem : removedElements ) {
        if( elem instanceof IGridTest ) {
          IGridTest test = ( IGridTest )elem;
          this.tests.remove( test );
        }
      }
    } else if (event.getType() == IGridModelEvent.ELEMENTS_CHANGED){
      IGridElement[] changedElements = event.getElements();
      for (IGridElement element: changedElements){
        if( element instanceof IGridTest ) {
          IGridTest test = ( IGridTest )element;
//          test.update();       
        }
      }
    } 
  }

  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridTest;
  }

  public String getName() {
    // TODO Auto-generated method stub
    return NAME;
  }
  
  /**
   * Gets singleton of this manager.
   * 
   * @return Singleton of test manager.
   */
  public static GridTestManager getManager() {
    if ( singleton == null ) { 
      singleton = new GridTestManager();
    }
    return singleton;
  }
  
  public List< IGridTest > getAvaliableTests( final Object resource ) {
    //TODO implement method
//    List< IGridTest > tests = new ArrayList< IGridTest >();
    return this.tests;
  }
  
  public IGridTest getTest( final String name, final IGridProject project ) {
    IGridTest result = null;
    for ( IGridTest test: this.tests ) {
      if ( test.getName().equalsIgnoreCase( name ) && test.getProject().equals( project )) {
        result = test;
        break;
      }
    }
    return result;
  }
  
  public List< IGridTest > getTests() {
    return this.tests;
  }

  public void statusChanged( final IGridTest test ) {
    for ( IGridTestStatusListener listener: this.listeners ) {
      listener.statusChanged( test );
    }
    
  }
  
}
