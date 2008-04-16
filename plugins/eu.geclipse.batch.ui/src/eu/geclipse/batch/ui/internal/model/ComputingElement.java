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

import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;

/**
 * Class that is a model for a Computing Element that is part of a GEF figure
 */
public class ComputingElement extends BatchResource  {
  /**
   * The identifier for the fqdn
   */
  public static final String PROPERTY_FQDN = "ComputingElement.fqdn"; //$NON-NLS-1$
  /**
   * The identifier for the type
   */
  public static final String PROPERTY_TYPE = "ComputingElement.type"; //$NON-NLS-1$

  /**
   * The identifier for the number of worker nodes
   */
  public static final String PROPERTY_NUM_WN = "ComputingElement.num_wn"; //$NON-NLS-1$

  /**
   * The identifier for the number of queues
   */
  public static final String PROPERTY_NUM_QUEUE = "ComputingElement.num_queue"; //$NON-NLS-1$

  /**
   * The identifier for the current number of jobs
   */
  public static final String PROPERTY_NUM_JOBS = "ComputingElement.num_jobs"; //$NON-NLS-1$

  private static final long serialVersionUID = 1;

  private static IPropertyDescriptor[] descriptors;

  /** A computer element shape. */
  private final Image computingElementIcon = Activator.getDefault().getImageRegistry().get( Activator.IMG_COMPUTING_ELEMENT );

  private String fqdn;
  private String type;
  private int numWn;
  private int numQueue;
  private int numJobs;

  /*
   * Initializes the property descriptors array.
   */
  static {
    descriptors = new IPropertyDescriptor[]{ // id and description pair
      new PropertyDescriptor( PROPERTY_FQDN, 
                              Messages.getString( "ComputingElement.Fqdn" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_TYPE, 
                              Messages.getString( "ComputingElementFigure.ServiceType" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_NUM_WN, 
                              Messages.getString( "ComputingElementFigure.NumOfWns" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_NUM_QUEUE, 
                              Messages.getString( "ComputingElementFigure.NumOfQueues" ) ), //$NON-NLS-1$
      new PropertyDescriptor( PROPERTY_NUM_JOBS, 
                              Messages.getString( "ComputingElementFigure.NumOfJobs" ) ) //$NON-NLS-1$
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
  public ComputingElement( final BatchJobManager jobManager ) {
   
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

    if ( PROPERTY_FQDN.equals( propertyId ) ) {
      str = this.fqdn;
    }
    else if ( PROPERTY_TYPE.equals( propertyId ) ) {
      str = this.type;
    }
    else if ( PROPERTY_NUM_WN.equals( propertyId ) ) {
      str = Integer.toString( this.numWn );
    }
    else if ( PROPERTY_NUM_QUEUE.equals( propertyId ) ) {
      str = Integer.toString( this.numQueue );
    }
    else if ( PROPERTY_NUM_JOBS.equals( propertyId ) ) {
      str = Integer.toString( this.numJobs );
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
    this.pcsDelegate.removePropertyChangeListener( listener );
  }

  /**
   * Sets a new name for the computing element.
   * @param newName The new name.
   */
  public void setFQDN( final String newName ) {
    String oldName = this.fqdn;
    this.fqdn = newName;
    this.pcsDelegate.firePropertyChange( PROPERTY_FQDN, oldName, newName );
  }

  /**
   * Sets a batch system type for the computing element.
   * @param newType The new batch system type.
   */
  public void setType( final String newType ) {
    String oldType = this.type;
    this.type = newType;
    this.pcsDelegate.firePropertyChange( PROPERTY_TYPE, oldType, newType );
  }

  /**
   * Sets the number of worker nodes for the computing element.
   * @param newNumWn The number of worker nodes.
   */
  public void setNumWNs( final int newNumWn ) {
    int oldNumWn = this.numWn;
    this.numWn = newNumWn;
    this.pcsDelegate.firePropertyChange( PROPERTY_NUM_WN, oldNumWn, newNumWn );
  }

  /**
   * Sets the number of queues present in this computing element.
   * @param newNumQueue The number of queues.
   */
  public void setNumQueues( final int newNumQueue ) {
    int oldNumQueue = this.numQueue;
    this.numQueue = newNumQueue;
    this.pcsDelegate.firePropertyChange( PROPERTY_NUM_QUEUE, oldNumQueue, newNumQueue );
  }

  /**
   * Sets the number of jobs present in this computing element.
   * @param newNumJobs The number of jobs.
   */
  public void setNumJobs( final int newNumJobs ) {
    int oldNumJobs = this.numJobs;
    this.numJobs = newNumJobs;
    this.pcsDelegate.firePropertyChange( PROPERTY_NUM_JOBS, oldNumJobs, newNumJobs );
  }

  /**
   * @return Returns the fqdn.
   */
  public String getFQDN() {
    return this.fqdn;
  }

  /**
   * @return Returns the type.
   */
  public String getType() {
    return this.type;
  }

  /**
   * @return Returns the number of worker nodes.
   */
  public int getNumWNs() {
    return this.numWn;
  }

  /**
   * @return Returns the number of queues.
   */
  public int getNumQueues() {
    return this.numQueue;
  }

  /**
   * @return Returns the number of jobs.
   */
  public int getNumJobs() {
    return this.numJobs;
  }

  /**
   * @return Returns the running jobs.
   */
  public List< IBatchJobInfo > getJobs() {
   
    return this.jobManager.getJobs();
  }

  /**
   * @return Returns the icon.
   */
  @Override
  public Image getIcon()
  {
    return this.computingElementIcon;
  }

  /**
   * Returns a string describing the model element, to be used for the outline
   * view.
   * @return A string describing the model element.
   */
  @Override
  public String getOutlineString() {
    return Messages.getString( "ComputingElement.CE" ) + this.fqdn; //$NON-NLS-1$
  }

  public int compareTo( Object o ) {
    // TODO Auto-generated method stub
    return 0;
  }

 

 
}
