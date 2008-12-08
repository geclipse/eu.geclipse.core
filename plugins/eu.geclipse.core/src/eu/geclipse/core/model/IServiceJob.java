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
 * Interface for structural service job.
 */
public interface IServiceJob extends IGridElement, IManageable {

  /**
   * This method is used to initialize instance just after it was created with
   * some data. This method existing separately from class' constructor, because
   * its instances are created through extension points, which require - for
   * simplicity - constructors without parameters.
   * 
   * @param initInputData object with information to initialize new (and most
   *          likely empty) instance of class
   */
  public void internalInit( final IFile initInputData );

  /**
   * 
   */
  public void init();

  /**
   * Method to access all single service job's names that are run as a one 
   * IServiceJob implementation.
   * 
   * @return list of single service job's names.
   */
  public List<String> getSingleServiceJobNames();

  /**
   * Method to access names of the resources on which service job is operating.
   * 
   * @return List of resources' names.
   */
  public List<String> getServiceJobResourcesNames();

  /**
   * Method to access text interpretation of single service job for given 
   * single service job's name performed on target resource at given date.
   * 
   * @param singleServiceJobName name of single service job
   * @param resourceName name of resource service job is performing on
   * @param date date of the submission
   * @return String that is representation of single service job result
   */
  public IServiceJobResult getSingleServiceJobResult( final String singleServiceJobName,
                                                final String resourceName,
                                                final Date date );

  /**
   * Short, user friendly description of this service job.
   * 
   * @return String with service job's description.
   */
  public String getServiceJobDescription();

  /**
   * Method returning service job name.
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
   * Method to access list of single service job's results
   * 
   * @return List of single service job's results
   */
  public List<IServiceJobResult> getResults();

  /**
   * Method to run a service job
   */
  public void run();

  // TODO Szymon
  public Object getStatus();

  /**
   * Method returning result of the service job - as a summary of 
   * single service jobs.
   * 
   * @return Service job's result.
   */
  public Object getSummary();

  /**
   * Method to access date of last service job submission.
   * 
   * @return date of last service job submission.
   */
  public Date getLastUpdate();

  /**
   * The same as {@link IServiceJob#getLastUpdate()}, but should be used in case
   * there can be difference in number of service job's runs for each resource.
   * 
   * @param remoteResourceName Name of a resource where service job should run.
   * @return date Date of last successful service job run for given resource.
   */
  public Date getLastUpdate( final String remoteResourceName );

  /**
   * This method gives access to ordered collection of sub service jobs' results. 
   * All service jobs' results returned by this method are results for 
   * given resource. Those results are grouped by date - which means that 
   * collection itself is divided into another collections, each of which keeps
   * service job's result for given resource and date. In other words this 
   * method returns collection of collections. Those internal collections keep
   * service job's result for given resource and date.<br>
   * <br>
   * Method used by providers for Operator's Job History.
   * 
   * @param resourceName Name of resource (e.g. host name).
   * @return List of list of service job's results. 
   */
  public List<List<IServiceJobResult>> getServiceJobResultsForResourceForDate( final String resourceName );

  /**
   * This method should be called each time when service job was run and new results
   * are available. Calling this method results in serialisation of new results
   * - they are written to service job's GTDL file - this is how exemplary
   * implementation available in service job framework works.
   * 
   * @param newResults list of new results that should be added to set of
   *          results maintained by this service job's class
   */
  public void addServiceJobResult( final List<IServiceJobResult> newResults );

  /**
   * Given the instance of {@link IServiceJobResult} class should return input
   * stream of result's specific data (the type of data is determined by method
   * {@link IServiceJobResult#getResultType()}
   * 
   * @param result Single service job result.
   * @return Input stream for result's data.
   */
  public InputStream getInputStreamForResult( final IServiceJobResult result );

  /**
   * This method sets column width for  single service job with a name 
   * specified as parameter.
   * 
   * @param singleServiceJobName Name of the single service job.
   * @return Width of the column.
   */
  public int getColumnWidth( final String singleServiceJobName );

  /**
   * This method is to obtain information whether service job implementation
   * needs to display job submission wizard to the user when it is being run.
   * Job submission wizard gives access to information which job submission
   * service to use (see {@link IServiceJob#getSubmissionService()} method).
   * 
   * @return <code>true</code> if this service job needs information to which
   *         submission service submit its jobs, <code>false</code> otherwise
   */
  public boolean needsSubmissionWizard();

  /**
   * Method to set the submission service for use when job-based service jobs
   * are submitted.
   * 
   * @param submissionService
   */
  public void setSubmissionService( final IGridJobService submissionService );

  /**
   * Gives access to information to which submission service service's jobs
   * should be submitted. This information should be set by class that runs
   * service job (see also {@link IServiceJob#needsSubmissionWizard()}).
   * 
   * @return reference to submission service to which this service job should
   *         submit its jobs or <code>null</code> in case this information
   *         wasn't set yet, or when service job implementation did not declare
   *         that this information is needed (see
   *         {@link IServiceJob#needsSubmissionWizard()}).
   */
  public IGridJobService getSubmissionService();
}
