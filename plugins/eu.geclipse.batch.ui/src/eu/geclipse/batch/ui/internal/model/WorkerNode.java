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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal.model;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IWorkerNodeInfo;
import eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.BatchUpdate;
import eu.geclipse.batch.ui.internal.Messages;

/**
 * Class that is a model for a Worker Node that is part of a GEF figure
 */
public final class WorkerNode extends BatchResource  {
  /**
   * The identifier for the fqdn
   */
  public static final String PROPERTY_FQDN = "WorkerNode.fqdn"; //$NON-NLS-1$

  /**
   * The identifier for the kernelversion
   */
  public static final String PROPERTY_KERNELVERSION = "WorkerNode.kernelversion"; //$NON-NLS-1$

  /**
   * The identifier for the state
   */
  public static final String PROPERTY_STATE = "WorkerNode.state"; //$NON-NLS-1$

  /**
   * The identifier for the number of processors
   */
  public static final String PROPERTY_NUMPROCESSORS = "WorkerNode.numprocessors"; //$NON-NLS-1$

  /**
   * The identifier for the total amount of RAM
   */
  public static final String PROPERTY_TOTALMEM = "WorkerNode.totalmem"; //$NON-NLS-1$

  /**
   * The identifier for the total number of jobs
   */
  public static final String PROPERTY_TOTAL_WN_JOBS = "WorkerNode.totalWNJobs"; //$NON-NLS-1$

  private static final long serialVersionUID = 1;

  private static IPropertyDescriptor[] descriptors;

  /** A worker node shape. */
  private final Image workerNodeIcon = Activator.getDefault().getImageRegistry().get( Activator.IMG_WORKER_NODE );

  private String fqdn;
  private String kernelVersion;
  private WorkerNodeState state;
  private int numProcessors;
  private String totalMem;
  private List<String> jobIds;

  /*
   * Initializes the property descriptors array.
   */
  static {
    descriptors = new IPropertyDescriptor[]{ // id and description pair
      new PropertyDescriptor( PROPERTY_FQDN, 
                              Messages.getString( "WorkerNodeFigure.fqdn" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_STATE, 
                              Messages.getString( "WorkerNodeFigure.State" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_KERNELVERSION, 
                              Messages.getString( "WorkerNodeFigure.KernelVersion" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_NUMPROCESSORS, 
                              Messages.getString( "WorkerNodeFigure.NumProcessors" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_TOTALMEM, 
                              Messages.getString( "WorkerNodeFigure.TotalMem" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_TOTAL_WN_JOBS, 
                              Messages.getString( "WorkerNodeFigure.TotalWNJobs" ) ) //$NON-NLS-1$
    };

    // use a custom cell editor validator for all four array entries
    for( int i = 0; i < descriptors.length; i++ ) {
      ( ( PropertyDescriptor )descriptors[ i ] ).setValidator( new ICellEditorValidator()
      {

        public String isValid( final Object value ) {
          int intValue = -1;
          String str = null;
          try {
            intValue = Integer.parseInt( ( String )value );
            str = ( intValue >= 0 ) ? null : Messages.getString( "WorkerNode.Error.LTZero" ); //$NON-NLS-1$
          } catch( NumberFormatException exc ) {
            str = Messages.getString( "WorkerNode.Error.NotANumber" ); //$NON-NLS-1$
          }

          return str;
        }
      } );
    }
  } // static

  /**
   * The default constructor
   * 
   * @param jobManager The manager of all the jobs residing in this batch service
   */
  public WorkerNode( final BatchJobManager jobManager ) {
    super( jobManager );
    
    this.jobIds = new ArrayList<String>();
  }
  
  /**
   * Returns an array of IPropertyDescriptors for this Worker Node.
   * <p>The returned array is used to fill the property view, when the edit-part corresponding
   * to this model element is selected.</p>
   * @return Returns the property description of this Worker Node.
   */
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return descriptors;
  }

  /**
   * Return the property value for the given propertyId, or <code>null</code>.
   * <p>The property view uses the IDs from the IPropertyDescriptors array
   * to obtain the value of the corresponding properties.</p>
   * @param propertyId The id of the property to return the value of.
   * @return The value of the indicated property.
   */
  @Override
  public Object getPropertyValue( final Object propertyId )
  {
    Object str = null;

    if ( PROPERTY_FQDN.equals( propertyId ) ) {
      str = this.fqdn;
    }
    else if ( PROPERTY_STATE.equals( propertyId ) ) {
      str = this.state.toString();
    }
    else if ( PROPERTY_KERNELVERSION.equals( propertyId ) ) {
      str = this.kernelVersion;
    }
    else if ( PROPERTY_NUMPROCESSORS.equals( propertyId ) ) {
      str = Integer.toString( this.numProcessors );
    }
    else if ( PROPERTY_TOTALMEM.equals( propertyId ) ) {
      str = this.totalMem;
    }
    else if ( PROPERTY_TOTAL_WN_JOBS.equals( propertyId ) ) {
      str = Integer.toString( this.jobIds.size() );
    }

    if ( null == str )
     str = super.getPropertyValue( propertyId );

    return str;
  }

  /**
   * Adds a property listener to this object.
   * @param listener The listener to be added.
   */
  @Override
  public void addPropertyChangeListener( final PropertyChangeListener listener ) {
    this.pcsDelegate.addPropertyChangeListener( listener );
  }

  /**
   * Removes a previously registered property listener from this object.
   * @param listener The listener to be removed.
   */
  @Override
  public void removePropertyChangeListener( final PropertyChangeListener listener ) {
    this.pcsDelegate.removePropertyChangeListener(listener);
  }

  /**
   * Sets a new name for the worker node.
   * @param newName The new name.
   */
  public void setFQDN( final String newName ) {
    String oldName = this.fqdn;
    this.fqdn = newName;
    this.pcsDelegate.firePropertyChange( PROPERTY_FQDN, oldName, newName );
  }

  /**
   * Sets the Kernel Version of the worker node.
   * @param newVersion The new version.
   */
  public void setKernelVersion( final String newVersion ) {
    this.kernelVersion = newVersion;
  }

  /**
   * Sets a new state of the worker node.
   * @param newState The new state.
   */
  public void setState( final WorkerNodeState newState ) {
    WorkerNodeState oldState = this.state;
    this.state = newState;
    this.pcsDelegate.firePropertyChange( PROPERTY_STATE, oldState, newState );
  }

  /**
   * Sets the number of processors of the worker node.
   * @param newNumProcessors The number of processors.
   */
  public void setNumProcessors( final int newNumProcessors ) {
    this.numProcessors = newNumProcessors;
  }

  /**
   * Sets the total amount of RAM of the worker node.
   * @param newTotalMem The amount of RAM.
   */
  public void setTotalMem( final String newTotalMem ) {
    this.totalMem = newTotalMem;
  }

  /**
   * Sets the ids of the running jobs.
   * @param jobs The ids of the running jobs.
   */
  public void setJobIds( final List< String > jobs ) {
    int numOld = this.jobIds.size();
    int numNew = 0;

    if ( null == jobs ) {
      this.jobIds.clear();
      numNew = 0;
    } else {
      this.jobIds = jobs;
      numNew = jobs.size();
    }

    // Fire a change that we are either getting "completely" free of getting our first job
    if ( ( numOld == 0 && numNew > 0 ) || ( numOld > 0 && numNew == 0 ) )
      this.pcsDelegate.firePropertyChange( PROPERTY_TOTAL_WN_JOBS, numOld, numNew );
  }

  /**
   * @return Returns the running jobIds.
   */
  public List< String > getJobIds() {
    return this.jobIds;
//    return new Vector< String > ( this.jobIds );
  }

  /**
   * @return Returns the fqdn.
   */
  public String getFQDN() {
    return this.fqdn;
  }

  /**
   * @return Returns the Kernel version.
   */
  public String getKernelVersion() {
    return this.kernelVersion;
  }

  /**
   * @return Returns the state.
   */
  public WorkerNodeState getState() {
    return this.state;
  }

  /**
   * @return Returns the number of processors.
   */
  public int getNumProcessors() {
    return this.numProcessors;
  }

  /**
   * @return Returns the amount of RAM.
   */
  public String getTotalMem() {
    return this.totalMem;
  }

  /**
   * @return Returns the icon.
   */
  @Override
  public Image getIcon() {
    return this.workerNodeIcon;
  }

  /**
   * Merge the state from the batch service with what we currently display.
   * @param wni The updated info from the batch service.
   */
  public void updateState( final IWorkerNodeInfo wni ) {
    if ( 0 != wni.getKernelVersion().compareTo( this.kernelVersion ) )
      this.setKernelVersion( wni.getKernelVersion() );
    if ( wni.getState() != this.state )
      this.setState( wni.getState() );
    if ( wni.getNp() != this.numProcessors )
      this.setNumProcessors( wni.getNp() );
    if ( 0 != wni.getTotalMem().compareTo( this.totalMem ) )
      this.setTotalMem( wni.getTotalMem() );
    this.setJobIds( wni.getJobs() ); // Merger the jobs
  }

  /**
   * Returns a string describing the model element, to be used for the outline
   * view.
   * @return A string describing the model element.
   */
  @Override
  public String getOutlineString() {
    return Messages.getString( "WorkerNode.WN" ) + this.fqdn; //$NON-NLS-1$
  }

  
  public int compareTo(final Object o ) {
    int value;
    String tempName ;

    if (BatchUpdate.sortedN==2 ) {
      tempName = ((WorkerNode)o).getState().toString();
      value = getState().toString().compareTo(tempName);
    }
    else {
     tempName = ((WorkerNode)o).getFQDN();
     value = getFQDN().compareTo(tempName);
    }
    return value;
  }
}
