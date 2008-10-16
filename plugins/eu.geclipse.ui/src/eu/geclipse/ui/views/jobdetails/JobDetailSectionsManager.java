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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.Extensions;

/**
 * Manager of {@link IJobDetailsSection}s
 */
public class JobDetailSectionsManager {

  static private int nextId = 1;
  static private Integer generalSectionId;
  static private Integer applicationSectionId;
  static private HashSet<IGridJobID> updatedJobs = new HashSet<IGridJobID>();
  private Composite parent; // parent for sections
  private Map<Integer, IJobDetailsSection> sectionsMap = new HashMap<Integer, IJobDetailsSection>();
  private List<IJobDetail> currentDetails;
  private IViewConfiguration viewConfiguration;
  
  /**
   * Enum specifying order in which sections appears in view
   */
  public enum SectionOrder {
    /**
     * 
     */
    GENERAL,
    /**
     * 
     */
    APPLICATION;
    
    int getOrder() {
      return this.ordinal();
    }
  }

  /**
   * @param parent on which {@link IJobDetailsSection}s should be created
   * @param viewConfiguration the configuration of view, to which manager is connected
   */
  public JobDetailSectionsManager( final Composite parent, final IViewConfiguration viewConfiguration ) {
    super();
    this.parent = parent;
    this.viewConfiguration = viewConfiguration;
  }

  private static int getNextId() {
    return nextId++;
  }

  void refresh( final IGridJob inputJob )
  {
    List<IJobDetailsFactory> factories = null;
      
    if( inputJob != null ) {
      Class<?> statusClass = inputJob.getJobStatus() != null
                                                            ? inputJob.getJobStatus()
                                                              .getClass()
                                                            : null;
      factories = Extensions.getJobDetailsFactories( inputJob.getClass(),
                                                     statusClass );
    }
    List<IJobDetail> details = getDetails( inputJob, factories );
    List<IJobDetailsSection> sections = getSections( details );
    disposeNotUsedDetails( details );
    dispatchDetailsToSections( details );
    disposeNotUsedSections();
    for( IJobDetailsSection section : sections ) {
      section.refresh( inputJob, this.parent );
    }
    this.currentDetails = details;
    scheduleJobStatusUpdate( factories, inputJob );
  }

  private void scheduleJobStatusUpdate( final List<IJobDetailsFactory> factories,
                                        final IGridJob inputJob )
  {
    if( factories != null
        && inputJob != null ) {
      IGridJobID id = inputJob.getID();
      
      if( !JobDetailSectionsManager.updatedJobs.contains( id ) ) {
        for( IJobDetailsFactory jobDetailsFactory : factories ) {
          if( jobDetailsFactory.shouldUpdateJobStatus( inputJob ) ) {
            Job backgroundJob = new Job( String.format( Messages.JobDetailSectionsManager_taskNameUpdateJobStatus, inputJob.getJobName() ) ) {
    
              @Override
              protected IStatus run( final IProgressMonitor monitor ) {
                IStatus status = Status.OK_STATUS;
                IGridJobStatus oldStatus = inputJob.getJobStatus();
                inputJob.updateJobStatus( monitor, true );
                GridModel.getJobManager().jobStatusChanged( inputJob, oldStatus );
                return status;
              }
            };
            
            JobDetailSectionsManager.updatedJobs.add( id );
            backgroundJob.schedule();
            break;
          }
        }
      }
    }
  }

  private List<IJobDetail> getDetails( final IGridJob gridJob, final List<IJobDetailsFactory> factories ) {
    List<IJobDetail> details = new ArrayList<IJobDetail>( 100 );
    if( gridJob != null ) {
      for( IJobDetailsFactory jobDetailsFactory : factories ) {
        details.addAll( jobDetailsFactory.getDetails( gridJob, this ) );
      }
    }
    return details;
  }

  private List<IJobDetailsSection> getSections( final List<IJobDetail> details )
  {
    List<IJobDetailsSection> sections = new ArrayList<IJobDetailsSection>();
    for( IJobDetail detail : details ) {
      if( !sections.contains( detail.getSection() ) ) {
        sections.add( detail.getSection() );
      }
    }
    Collections.sort( sections, new Comparator<IJobDetailsSection>() {

      public int compare( final IJobDetailsSection section1,
                          final IJobDetailsSection section2 )
      {
        return section1.getOrder() - section2.getOrder();
      }
    } );
    return sections;
  }

  /**
   * Return already created section. If section with given id wasn't created yet
   * (returned null), then please call
   * {@link JobDetailSectionsManager#createSection(String, int)}
   * 
   * @param sectionId identifier returned from
   *            {@link JobDetailSectionsManager#createSection(String, int)}
   * @return section, or null if section wasn't created yet.
   */
  public IJobDetailsSection getSection( final Integer sectionId ) {
    return this.sectionsMap.get( sectionId );
  }

  /**
   * @param name name for section
   * @param order
   * @param lazy <code>true</code> if section should be collapsed after creation, and refresh for details will be called after expansion by user
   * @return created section ID
   */
  public Integer createSection( final String name, final int order, final boolean lazy ) {
    Integer id = Integer.valueOf( getNextId() );
    JobDetailsSection section = new JobDetailsSection( name, order, this.viewConfiguration, lazy );
    this.sectionsMap.put( id, section );
    return id;
  }
  
  /**
   * @param name name for section
   * @param order
   * @return created section ID
   */
  public Integer createSection( final String name, final int order ) {
    return createSection( name, order, false );
  }

  /**
   * @return general section
   */
  public IJobDetailsSection getSectionGeneral() {
    IJobDetailsSection section = getSection( JobDetailSectionsManager.generalSectionId );
    if( section == null ) {
      JobDetailSectionsManager.generalSectionId = createSection( Messages.JobDetailSectionsManager_general,
                                                                 SectionOrder.GENERAL.getOrder() );
      section = getSection( JobDetailSectionsManager.generalSectionId );
    }
    return section;
  }

  /**
   * @return section
   */
  public IJobDetailsSection getSectionApplication() {
    IJobDetailsSection section = getSection( JobDetailSectionsManager.applicationSectionId );
    if( section == null ) {
      JobDetailSectionsManager.applicationSectionId = createSection( Messages.JobDetailSectionsManager_application,
                                                                     SectionOrder.APPLICATION.getOrder() );
      section = getSection( JobDetailSectionsManager.applicationSectionId );
    }
    return section;
  }

  private void dispatchDetailsToSections( final List<IJobDetail> details ) {
    if( details != null ) {
      for( IJobDetail detail : details ) {
        detail.getSection().addDetail( detail );
      }
    }
  }

  private void disposeNotUsedDetails( final List<IJobDetail> newDetails ) {
    if( this.currentDetails != null ) {
      for( IJobDetail jobDetail : this.currentDetails ) {
        if( findDetailById( newDetails,
                              jobDetail.getId(),
                              jobDetail.getSection() ) == null )
        {
          jobDetail.dispose();
          jobDetail.getSection().removeDetail( jobDetail );
        }
      }
    }
  }

  private void disposeNotUsedSections() {
    Iterator<Integer> iterator = this.sectionsMap.keySet().iterator();
    while( iterator.hasNext() ) {
      Integer sectionId = iterator.next();
      IJobDetailsSection section = getSection( sectionId );
      if( section != null
          && section.getDetails().size() == 0 ) {
        section.dispose();
      }
    }
  }

  private IJobDetail findDetailById( final List<IJobDetail> details,
                                       final String detailId,
                                       final IJobDetailsSection section )
  {
    IJobDetail foundDetail = null;
    for( Iterator<IJobDetail> iterator = details.iterator(); iterator.hasNext()
                                                             && foundDetail == null; )
    {
      IJobDetail jobDetail = iterator.next();
      if( jobDetail.getSection() == section
          && jobDetail.getId().equals( detailId ) )
      {
        foundDetail = jobDetail;
      }
    }
    return foundDetail;
  }
}
