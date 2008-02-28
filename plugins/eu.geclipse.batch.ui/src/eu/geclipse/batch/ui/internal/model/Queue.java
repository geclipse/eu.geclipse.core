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
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.batch.IQueueInfo.QueueRunState;
import eu.geclipse.batch.IQueueInfo.QueueState;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;

/**
 * Class that is a model for a Queue that is part of a GEF figure
 */
public final class Queue extends BatchResource {
  /**
   * The identifier for the queue name
   */
  public static final String PROPERTY_NAME = "Queue.name"; //$NON-NLS-1$

  /**
   * The identifier for the state
   */
  public static final String PROPERTY_STATE = "Queue.state"; //$NON-NLS-1$

  /**
   * The identifier for the run state
   */
  public static final String PROPERTY_RUN_STATE = "Queue.runState"; //$NON-NLS-1$

  /**
   * The identifier for the memory
   */
  public static final String PROPERTY_MEM = "Queue.memory"; //$NON-NLS-1$

  /**
   * The identifier for the time CPU
   */
  public static final String PROPERTY_TIME_CPU = "Queue.timeCPU"; //$NON-NLS-1$

  /**
   * The identifier for the time wall
   */
  public static final String PROPERTY_TIME_WALL = "Queue.timeWall"; //$NON-NLS-1$

  /**
   * The identifier for the node
   */
  public static final String PROPERTY_NODE = "Queue.node"; //$NON-NLS-1$

  /**
   * The identifier for the run
   */
  public static final String PROPERTY_RUN = "Queue.run"; //$NON-NLS-1$

  /**
   * The identifier for the que
   */
  public static final String PROPERTY_QUE = "Queue.que"; //$NON-NLS-1$

  /**
   * The identifier for the lm
   */
  public static final String PROPERTY_LM = "Queue.lm"; //$NON-NLS-1$

  private static final long serialVersionUID = 1;

  private static IPropertyDescriptor[] descriptors;

  /** A queue shape. */
  private final Image queueIcon = Activator.getDefault().getImageRegistry().get( Activator.IMG_QUEUE ); 

  private String queueName;
  private QueueState state;
  private QueueRunState runState;
  private int memory;
  private String timeCPU;
  private String timeWall;
  private String node;
  private int run;
  private int que;
  private String lm;

  /*
   * Initializes the property descriptors array.
   */
  static {
    descriptors = new IPropertyDescriptor[]{ // id and description pair
      new PropertyDescriptor( PROPERTY_NAME, Messages.getString( "Queue.Name" ) ), //$NON-NLS-1$
      new TextPropertyDescriptor( PROPERTY_STATE, Messages.getString( "Queue.State" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_RUN_STATE, Messages.getString( "Queue.RunState" ) ), //$NON-NLS-1$
      new TextPropertyDescriptor( PROPERTY_MEM, Messages.getString( "Queue.Memory" ) ), //$NON-NLS-1$
      new TextPropertyDescriptor( PROPERTY_TIME_CPU, Messages.getString( "Queue.TimeCPU" ) ), //$NON-NLS-1$
      new TextPropertyDescriptor( PROPERTY_TIME_WALL, Messages.getString( "Queue.TimeWall" ) ), //$NON-NLS-1$
      new TextPropertyDescriptor( PROPERTY_NODE, Messages.getString( "Queue.Node" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_RUN, Messages.getString( "Queue.Run" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_QUE, Messages.getString( "Queue.Que" ) ), //$NON-NLS-1$
      new TextPropertyDescriptor( PROPERTY_LM, Messages.getString( "Queue.Lm" ) ) //$NON-NLS-1$
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
            str = ( intValue >= 0 ) ? null : Messages.getString( "Queue.Error.LTZero" ); //$NON-NLS-1$
          } catch( NumberFormatException exc ) {
            str = Messages.getString( "Queue.Error.NotANumber" ); //$NON-NLS-1$
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
  public Queue( final BatchJobManager jobManager ) {
    super( jobManager );
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

    if ( PROPERTY_NAME.equals( propertyId ) ) {
      str = this.queueName;
    }
    else if ( PROPERTY_STATE.equals( propertyId ) ) {
      str = this.state.toString();
    }
    else if ( PROPERTY_RUN_STATE.equals( propertyId ) ) {
      str = this.runState.toString(); 
    }
    else if ( PROPERTY_TIME_CPU.equals( propertyId ) ) {
      str = this.timeCPU;
    }
    else if ( PROPERTY_TIME_WALL.equals( propertyId ) ) {
      str = this.timeWall;
    }
    else if ( PROPERTY_MEM.equals( propertyId ) ) {
      str = Integer.toString( this.memory );
    }
    else if ( PROPERTY_NODE.equals( propertyId ) ) {
      str = this.node;
    }
    else if ( PROPERTY_RUN.equals( propertyId ) ) {
      str = Integer.toString( this.run );
    }
    else if ( PROPERTY_QUE.equals( propertyId ) ) {
      str = Integer.toString( this.que );
    }
    else if ( PROPERTY_LM.equals( propertyId ) ) {
      str = this.lm;
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
   * Sets a new name for the queue.
   * @param newName The new name.
   */
  public synchronized void setQueueName( final String newName ) {
    String oldName = this.queueName;
    this.queueName = newName;
    this.pcsDelegate.firePropertyChange( PROPERTY_NAME, oldName, newName );
  }

  /**
   * @param newMemory the memory to set
   */
  public void setMemory( final int newMemory ) {
    this.memory = newMemory;
  }

  /**
   * @param newTimeCPU the timeCPU to set
   */
  public void setTimeCPU( final String newTimeCPU ) {
    this.timeCPU = newTimeCPU;
  }

  /**
   * @param newTimeWall the timeCPU to set
   */
  public void setTimeWall( final String newTimeWall ) {
    this.timeWall = newTimeWall;
  }

  /**
   * @param newNode the node to set
   */
  public void setNode( final String newNode ) {
    this.node = newNode;
  }

  /**
   * @param newRun the run to set
   */
  public void setRun( final int newRun ) {
    this.run = newRun;
  }

  /**
   * @param newLm the lm to set
   */
  public void setLm( final String newLm ) {
    this.lm = newLm;
  }

  /**
   * @param newQue the que to set
   */
  public void setQue( final int newQue ) {
    this.que = newQue;
  }

  /**
   * Sets a new state of the queue.
   * @param newState The new state.
   */
  public synchronized void setState( final QueueState newState ) {
    QueueState oldState = this.state;
    this.state = newState;
    this.pcsDelegate.firePropertyChange( PROPERTY_STATE, oldState, newState );
  }

  /**
   * @param newRunState the runState to set
   */
  public void setRunState( final QueueRunState newRunState ) {
    QueueRunState oldRunState = this.runState;
    this.runState = newRunState;
    this.pcsDelegate.firePropertyChange( PROPERTY_RUN_STATE, oldRunState, newRunState );
  }

  /**
   * Sets the current number of jobs in the batch service.
   * IMPORTANT: These are all the job in the batch service, we will do
   * lazy filtering on the jobs belonging to this Queue when needed.
   * @param jobs The current number of jobs.
   */
//  public synchronized void setJobs( final List<IBatchJobInfo> jobs ) {
//    this.jobs = jobs;
//  }

  /**
   * @return Returns the name.
   */
  public String getQueneName() {
    return this.queueName;
  }

  /**
   * @return Returns the state.
   */
  public synchronized QueueState getState() {
    return this.state;
  }

  /**
   * @return Returns the runState.
   */
  public QueueRunState getRunState() {
    return this.runState;
  }

  /**
   * @return Returns the running jobs.
   */
  public List< IBatchJobInfo > getJobs() {
    return this.jobManager.getJobs();
  }

  /**
   * @return Returns <code>true</code> if there are jobs in this queue, <code>false</code> otherwise. 
   */
  public synchronized boolean isQueueEmpty() {
    boolean ret = true;
    
    List < IBatchJobInfo > jobs = this.getJobs();
    
    if ( null != jobs ) {
      for ( IBatchJobInfo job : jobs ) {
        if ( job.getQueueName().equals( this.queueName ) ) {
          ret = false;
          break;
        }
      }
    }
    return ret;
  }
  
  /**
   * @return Returns the icon.
   */
  @Override
  public Image getIcon()
  {
    return this.queueIcon;
  }

  /**
   * Merge the state from the batch service with what we currently display.
   * @param queuei The updated info from the batch service.
   */
  public void updateState( final IQueueInfo queuei ) {
    if ( queuei.getState() != this.state )
      this.setState( queuei.getState() );
    if ( queuei.getRunState() != this.runState )
      this.setRunState( queuei.getRunState() );
    if ( queuei.getMemory() != this.memory )
      this.setMemory( queuei.getMemory() );
    if ( 0 != queuei.getTimeCPU().compareTo( this.timeCPU ) )
      this.setTimeCPU( queuei.getTimeCPU() );
    if ( 0 != queuei.getTimeWall().compareTo( this.timeWall ) )
      this.setTimeWall( queuei.getTimeWall() );
    if ( 0 != queuei.getNode().compareTo( this.node ) )
      this.setNode( queuei.getNode() );
    if ( queuei.getRun() != this.run )
      this.setRun( queuei.getRun() );
    if ( queuei.getQue() != this.que )
      this.setQue( queuei.getQue() );
    if ( 0 != queuei.getLm().compareTo( this.lm ) )
      this.setLm( queuei.getLm() );
  }

  /**
   * Returns a string describing the model element, to be used for the outline
   * view.
   * @return A string describing the model element.
   */
  @Override
  public String getOutlineString() {
    return Messages.getString( "Queue.Name" ) + this.queueName; //$NON-NLS-1$
  }
}
