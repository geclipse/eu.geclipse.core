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
 *      - Szymon Mueller
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import eu.geclipse.core.StructuralTestUpdater;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IGridTest;
import eu.geclipse.core.model.IGridTestManager;
import eu.geclipse.core.model.IGridTestStatusListener;
import eu.geclipse.core.model.ITestable;

/**
 * Abstract class that makes some core's internal interfaces available for
 * external plug-in.
 */
public class GridTestManager
  extends
  AbstractGridElementManager implements IGridModelListener, IGridTestManager
{
  
  private static GridTestManager singleton;
  
  /**
   * The name of this manager.
   */
  private static final String NAME = ".tests"; //$NON-NLS-1$
  
  private HashMap< IGridTest, StructuralTestUpdater > structuralTests = new HashMap< IGridTest, StructuralTestUpdater >();
  
  //private List<IGridTestStatusListener> listeners = new ArrayList< IGridTestStatusListener >();
  private List< IGridTestStatusListener > globalListeners = new ArrayList< IGridTestStatusListener >();
  
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
  
  public void gridModelChanged( final IGridModelEvent event ) {
    if ( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) {
      IGridElement[] elements = event.getElements();
      for ( IGridElement element: elements) {
        if ( element instanceof IGridTest ) {
          removeTest( ( IGridTest) element );
        }
      }
    }
  }
  
  public boolean removeTest( final IGridTest test ) {
    boolean result = false;
    Iterator<IGridTest> iter = this.structuralTests.keySet().iterator();
    while ( iter.hasNext() ) {
      this.structuralTests.remove( iter.next() );
      result = true;
    }
    return result;
  }
  
  @Override
  public boolean addElement( final IGridElement element ) throws GridModelException {
    boolean flag;
    flag = super.addElement( element );
    if ( element instanceof IGridTest ) {
      IGridTest test = (IGridTest) element;
      if ( test.isStructural() ){
        StructuralTestUpdater updater = new StructuralTestUpdater( test.getName(), test );
        this.structuralTests.put( test, updater );
      } else {
        IGridTest parent = test.getParentTest();
        if ( !( addSingleTest( parent, test ) ) ) {
          //TODO throw new NoSuchStructuralTestException();
        }
      }
      informListeners();
    }
    return flag;
  }
  
  @Override
  public boolean removeElement( final IGridElement element ) {
    boolean flag;
    flag = super.removeElement( element );
    if ( element instanceof IGridTest ) {
      this.structuralTests.remove( element );
    }
    return flag;
  }

  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridTest;
  }

  public String getName() {
    return NAME;
  }
  
  public List< IGridTest > getAvaliableTests( final Object resource ) {
    List< IGridTest > tests = null;
    //TODO change to ITestable later on
    if ( resource instanceof ITestable ) {
      tests = searchTestsForResource( ( IGridResource )resource );
    }
    return tests;
  }

  /**
   * Returns structural tests for specified resource 
   * 
   * @param resource
   * @return
   */
  private List< IGridTest > searchTestsForResource( final IGridResource resource ) {
    List< IGridTest > foundTests = new ArrayList< IGridTest >();
    Iterator< IGridTest > testIterator = this.structuralTests.keySet().iterator();
    while ( testIterator.hasNext() ) {
      IGridTest test = testIterator.next();
      IGridResource tempResource = test.getTestedResource();
      if ( tempResource == resource ) {
        foundTests.add( test );
      }
    }
    return foundTests;
  }
  
  public boolean addSingleTest( final IGridTest parentTest, final IGridTest singleTest) {
    boolean result = false;
    Iterator<IGridTest> iterator = this.structuralTests.keySet().iterator();
    while ( iterator.hasNext() ) {
      IGridTest test = iterator.next();
      if ( parentTest == test ) {
        StructuralTestUpdater updater = this.structuralTests.get( test );
        updater.addSingleTest( singleTest );
        result = true;
      }
    }
    return result;
  }
  
  public List<IGridTest> getStructuralTests() {
    List<IGridTest> result = new ArrayList<IGridTest>();
    Iterator<IGridTest> iter = this.structuralTests.keySet().iterator();
    while ( iter.hasNext() ) {
      IGridTest test = iter.next();
      result.add( test );
    }
    return result;
  }

  public void addStrTest( final IGridTest test ) {
    StructuralTestUpdater up = new StructuralTestUpdater(test.getName(),test);
    this.structuralTests.put( test, up );
  }
  
  public void addTestStatusListener( final IGridTestStatusListener listener ) {
    if ( !( this.globalListeners.contains( listener ) ) ) {
      this.globalListeners.add( listener );
    }
  }
  
  public void informListeners() {
    for ( IGridTestStatusListener listener: this.globalListeners ) {
      listener.statusChanged();
    }
  }

  public IGridTest getStructuralTest( final String name ) {
    IGridTest test = null;
    for ( IGridTest strTest : this.structuralTests.keySet() ) {
      if ( strTest.getName().equals( name ) ) {
        test = strTest;
      } 
    }
    return test;
  }

  public IGridTest getSimpleTest( final String name, final String parentTestName ) {
    IGridTest test = null;
    for ( IGridTest strTest: this.structuralTests.keySet() ) {
      if ( strTest.getName().equals( parentTestName ) ) {
        for ( IGridTest childTest : strTest.getChildren() ) {
          if ( childTest.getName().equals( name ) ) 
            test = childTest;
        }
      }
    }
    return test;
  }
  
}
