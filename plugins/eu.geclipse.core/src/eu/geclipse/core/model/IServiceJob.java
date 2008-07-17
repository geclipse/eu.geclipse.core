/******************************************************************************
 * Copyright (c) 2007 - 2008 g-Eclipse consortium 
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
package eu.geclipse.core.model;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;

/**
 * Interface for structural tests and simple tests.
 */
public interface IServiceJob extends IGridElement, IManageable {

  /**
   * This method is used to initialize instance just after it was created with
   * some data. This method existing separately from class' constructor, because
   * its instances are created through extension points, which require - for
   * simplicity - constructors without parameters.
   * 
   * @param initInputData object with information to initialize new (and most
   *            likely empty) instance of class
   */
  public void internalInit( final IFile initInputData );

  public void init();
  
  /**
   * Method to access all single tests that are run as a one IGridTest
   * implementation.
   * 
   * @return list of single tests names
   */
  public List<String> getSingleTestsNames();

  /**
   * Method to access names of tested resources.
   * 
   * @return List of names of tested resources
   */
  public List<String> getTestedResourcesNames();

  // /**
  // * Method to access dates of all submissions for given resource for this
  // test
  // *
  // * @param resourceName name of the tested resources
  // * @return List of dates of test submission
  // */
  // public List<Date> getTestSubmissionDates( final String resourceName );
  /**
   * Method to access text interpretation of single test for given single test's
   * name, tested resources and date of this test.
   * 
   * @param testName name of single test
   * @param resourceName name of tested resources
   * @param date date of test submission
   * @return String that is representation of single test result
   */
  public IServiceJobResult getSingleTestResult( final String testName,
                                              final String resourceName,
                                              final Date date );

  /**
   * Short user friendly description of this test (the whole structural test).
   * 
   * @return String with test's description
   */
  public String getTestDescription();

  /**
   * Method returning test name.
   * 
   * @return String
   */
  public String getName();

  /**
   * Method returns all the data which needs to be viewed in properties view.
   * 
   * @return Map with property name as a key and its value as a value
   */
  public Map<String, String> getProperties();

  /**
   * Method to access list of single tests results
   * 
   * @return List of single test results
   */
  public List<IServiceJobResult> getResults();

  /**
   * Method to run a test
   */
  public void run();

  // TODO Szymon
  public Object getStatus();

  /**
   * Method returning result of the test - as a summary of single tests.
   * 
   * @return Test's result
   */
  public Object getSummary();

  /**
   * Method to access date of last test submission
   * 
   * @return date of last test submission
   */
  public Date getLastUpdate();

  /**
   * The same as {@link IServiceJob#getLastUpdate()}, but should be used in case
   * there can be difference in number of test runs for each tested resource.
   * 
   * @param testedResourceName name of a tested resource
   * @return date of last successful test run for given resource
   */
  public Date getLastUpdate( final String testedResourceName );

  /**
   * This method gives access to ordered collection of sub tests result. All
   * test results returned by this method are results for given resource. Those
   * results are grouped by date - which means that collection itself is divided
   * into another collections, each of which keeps test result for given
   * resource and date. In other words this method returns collection of
   * collections. Those internal collections keep test result for given resource
   * and date.<br>
   * <br>
   * Method used by providers for Test Details View.
   * 
   * @param resourceName name of tested resource (e.g. host name)
   * @return List of List of
   */
  public List<List<IServiceJobResult>> getTestResultsForResourceForDate( final String resourceName );

  /**
   * This method should be called each time when test was run and new results
   * are available. Calling this method results in serialization of new results -
   * they are written to test's GTDL file - this is how exemplary implementation
   * available in test framework works.
   * 
   * @param newResults list of new results that should be added to set of
   *            results maintained by this test's class
   */
  public void addTestResult( final List<IServiceJobResult> newResults );

  /**
   * Given the instance of {@link IServiceJobResult} class should return input
   * stream of result's specific data (the type of data is determined by method
   * {@link IServiceJobResult#getResultType()}
   * 
   * @param result single test result
   * @return input stream for result's data
   */
  public InputStream getInputStreamForResult( final IServiceJobResult result );
  
  public int getColumnWidth( final String singleTestName );

}
