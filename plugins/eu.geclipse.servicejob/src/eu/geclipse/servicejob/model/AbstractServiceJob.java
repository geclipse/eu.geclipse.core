/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
package eu.geclipse.servicejob.model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.model.impl.ServiceJobResult;
import eu.geclipse.servicejob.parsers.GTDLParser;
import eu.geclipse.servicejob.parsers.GTDLWriter;

/**
 * An abstract, base implementation of {@link IServiceJob} interface.
 */
public abstract class AbstractServiceJob extends AbstractGridElement
  implements IServiceJob
{

  protected IGridJobService submissionService;
  protected String testInputData;
  protected String name;
  protected List<IServiceJobResult> results = new ArrayList<IServiceJobResult>();
  protected List<Date> submissionDates = new ArrayList<Date>();
  protected IServiceJobResult lastResult;
  protected List<String> testNames = new ArrayList<String>();
  protected List<String> testedResources = new ArrayList<String>();
  private IFile resource = null;

  public String getName() {
    return this.name;
  }

  public List<IServiceJobResult> getResults() {
    return this.results;
  }

  public List<String> getTestedResourcesNames() {
    return this.testedResources;
  }

  public IServiceJobResult getSingleTestResult( final String testName,
                                              final String resourceName,
                                              final Date date )
  {
    IServiceJobResult result1 = null;
    for( IServiceJobResult subTest : this.results ) {
      if( subTest.getResourceName().equals( resourceName )
          && testName.equalsIgnoreCase( subTest.getSubTestName() )
          && date.equals( subTest.getRunDate() ) )
      {
        result1 = subTest;
      }
    }
    return result1;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public IResource getResource() {
    return this.resource;
  }

  public final void internalInit( final IFile initInputData ) {
    this.resource = initInputData;
    this.name = this.resource.getName().substring( 0,
                                                   this.resource.getName()
                                                     .lastIndexOf( "." ) ); //$NON-NLS-1$
    try {
      this.testedResources = GTDLParser.getTestedResources( this.resource.getRawLocation()
        .toFile() );
      this.testInputData = GTDLParser.getInputTestData( this.resource.getRawLocation()
        .toFile() );
      this.results = new ArrayList<IServiceJobResult>();
      List<ServiceJobResult> resultsTemp = GTDLParser.getTestResults( this.resource.getRawLocation()
        .toFile() );
      this.results.addAll( resultsTemp );
      this.lastResult = computeLastResult( this.results );
      init();
    } catch( ParserConfigurationException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( DOMException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( ParseException e ) {
      // TODO katis
      Activator.logException( e );
    }
  }

  public Object getStatus() {
    // TODO katis ???
    return null;
  }

  public Date getLastUpdate() {
    return this.lastResult != null
                                  ? this.lastResult.getRunDate()
                                  : null;
  }

  public String getSummary() {
    String res = ServiceJobStates.NA.getAlias();
    if( this.lastResult != null && this.lastResult instanceof ServiceJobResult ) {
      res = ( ( ServiceJobResult )this.lastResult ).getResultEnum();
    }
    return res;
  }

  public List<List<IServiceJobResult>> getTestResultsForResourceForDate( final String resourceName )
  {
    List<List<IServiceJobResult>> resultL = new ArrayList<List<IServiceJobResult>>();
    Map<Date, List<IServiceJobResult>> tempDatesMap = new HashMap<Date, List<IServiceJobResult>>();
    for( IServiceJobResult singleResult : this.results ) {
      // 1. choose test for given resource name...
      if( singleResult.getResourceName().equalsIgnoreCase( resourceName ) ) {
        // 2. from those tests choose elements with the same date
        if( !tempDatesMap.containsKey( singleResult.getRunDate() ) ) {
          tempDatesMap.put( singleResult.getRunDate(),
                            new ArrayList<IServiceJobResult>() );
        }
        tempDatesMap.get( singleResult.getRunDate() ).add( singleResult );
      }
    }
    for( List<IServiceJobResult> resultList : tempDatesMap.values() ) {
      resultL.add( resultList );
    }
    return resultL;
  }

  public IGridContainer getParent() {
    IGridContainer parent = null;
    IPath parentPath = getPath().removeLastSegments( 1 );
    IGridElement parentElement = GridModel.getRoot().findElement( parentPath );
    if( parentElement instanceof IGridContainer ) {
      parent = ( IGridContainer )parentElement;
    }
    return parent;
  }

  public IPath getPath() {
    return getResource() != null
                                ? getResource().getFullPath()
                                : null;
  }

  public IGridElementManager getManager() {
    return GridModel.getTestManager();
  }

  public void addTestResult( final List<IServiceJobResult> newResults ) {
    try {
      GTDLWriter.addTestResults( this.resource.getRawLocation().toFile(),
                                 newResults );
      if( !this.resource.isSynchronized( IResource.DEPTH_ZERO ) ) {
        try {
          this.resource.refreshLocal( IResource.DEPTH_ZERO, null );
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          Activator.logException( e );
        }
      }
      this.results.addAll( newResults );
      this.lastResult = computeLastResult( newResults );
    } catch( ParserConfigurationException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO katis
      Activator.logException( e );
    } catch( TransformerFactoryConfigurationError e ) {
      // TODO katis
      Activator.logException( e );
    } catch( TransformerException e ) {
      // TODO katis
      Activator.logException( e );
    }
  }

  public Date getLastUpdate( final String testedResourceName ) {
    Date res = null;
    if( this.results != null && !this.results.isEmpty() ) {
      res = new Date( 0 );
      for( IServiceJobResult testResult : this.results ) {
        if( testResult.getResourceName().equalsIgnoreCase( testedResourceName )
            && testResult.getRunDate().after( res ) )
        {
          res = testResult.getRunDate();
        }
      }
    }
    return res;
  }

  private IServiceJobResult computeLastResult( final List<IServiceJobResult> resultsToComp )
  {
    IServiceJobResult res = null;
    Date tempDate = new Date( 0 );
    for( IServiceJobResult result : resultsToComp ) {
      if( tempDate == null || tempDate.before( result.getRunDate() ) ) {
        tempDate = result.getRunDate();
        res = result;
      }
    }
    return res;
  }

  public Map<String, String> getProperties() {
    Map<String, String> res = new HashMap<String, String>();
    res.put( Messages.getString( "AbstractGridTest.test_name_property" ), this.name ); //$NON-NLS-1$
    res.put( Messages.getString( "AbstractGridTest.description_property" ), this.getTestDescription() ); //$NON-NLS-1$
    return res;
  }

  // public void run() {
  // Job job = getRunnableJob();
  // job.setUser( true );
  // job.schedule();
  // }
  /**
   * Sets resource for this object.
   * 
   * @param resource
   */
  public void setResource( final IFile resource ) {
    this.resource = resource;
  }

  /**
   * This methods returns handle to folder specific to single instance of
   * service job. This folder should be used to store all files used by a
   * service job.
   * 
   * @return Folder specific for this service job.  
   */
  public IFolder getTestFolder() {
    IFolder folder = getResource().getParent()
      .getFolder( new Path( this.name + "_files" ) ); //$NON-NLS-1$
    if( !folder.exists() ) {
      try {
        folder.create( false, true, new NullProgressMonitor() );
      } catch( CoreException exc ) {
        // TODO szymon
        Activator.logException( exc );
      }
    }
    return folder;
  }

  public int getColumnWidth( final String singleTestName ) {
    return 200;
  }

  public IGridJobService getSubmissionService() {
    return this.submissionService;
  }

  public boolean needsSubmissionWizard() {
    return false;
  }

  public void setSubmissionService( final IGridJobService submissionService ) {
    this.submissionService = submissionService;
  }
  
  
}