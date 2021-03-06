/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.model.IWorkflowPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>IWorkflow Job</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link eu.geclipse.workflow.impl.IWorkflowJobImpl#getJobDescription <em>Job Description</em>}</li>
 * <li>{@link eu.geclipse.workflow.impl.IWorkflowJobImpl#getJobDescriptionFileName <em>Job Description File Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class WorkflowJobImpl extends WorkflowNodeImpl implements IWorkflowJob {

  /**
   * The default value of the '{@link #getJobDescription() <em>Job Description</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getJobDescription()
   * @generated
   * @ordered
   */
  protected static final String JOB_DESCRIPTION_EDEFAULT = ""; //$NON-NLS-1$
  /**
   * The cached value of the '{@link #getJobDescription() <em>Job Description</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getJobDescription()
   * @generated
   * @ordered
   */
  protected String jobDescription = JOB_DESCRIPTION_EDEFAULT;
  /**
   * The default value of the '{@link #getJobDescriptionFileName() <em>Job Description File Name</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getJobDescriptionFileName()
   * @generated
   * @ordered
   */
  protected static final String JOB_DESCRIPTION_FILE_NAME_EDEFAULT = null;
  /**
   * The cached value of the '{@link #getJobDescriptionFileName() <em>Job Description File Name</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getJobDescriptionFileName()
   * @generated
   * @ordered
   */
  protected String jobDescriptionFileName = JOB_DESCRIPTION_FILE_NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected WorkflowJobImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return IWorkflowPackage.Literals.IWORKFLOW_JOB;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getJobDescription() {
    return this.jobDescription;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setJobDescription( String newJobDescription ) {
    String oldJobDescription = this.jobDescription;
    this.jobDescription = newJobDescription;
    if( eNotificationRequired() )
      eNotify( new ENotificationImpl( this,
                                      Notification.SET,
                                      IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION,
                                      oldJobDescription,
                                      this.jobDescription ) );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getJobDescriptionFileName() {
    return this.jobDescriptionFileName;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setJobDescriptionFileName( String newJobDescriptionFileName ) {
    String oldJobDescriptionFileName = this.jobDescriptionFileName;
    this.jobDescriptionFileName = newJobDescriptionFileName;
    if( eNotificationRequired() )
      eNotify( new ENotificationImpl( this,
                                      Notification.SET,
                                      IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME,
                                      oldJobDescriptionFileName,
                                      this.jobDescriptionFileName ) );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object eGet( int featureID, boolean resolve, boolean coreType ) {
    switch( featureID ) {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        return getJobDescription();
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        return getJobDescriptionFileName();
    }
    return super.eGet( featureID, resolve, coreType );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eSet( int featureID, Object newValue ) {
    switch( featureID ) {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        setJobDescription( ( String )newValue );
        return;
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        setJobDescriptionFileName( ( String )newValue );
        return;
    }
    super.eSet( featureID, newValue );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eUnset( int featureID ) {
    switch( featureID ) {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        setJobDescription( JOB_DESCRIPTION_EDEFAULT );
        return;
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        setJobDescriptionFileName( JOB_DESCRIPTION_FILE_NAME_EDEFAULT );
        return;
    }
    super.eUnset( featureID );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public boolean eIsSet( int featureID ) {
    switch( featureID ) {
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION:
        return JOB_DESCRIPTION_EDEFAULT == null
                                               ? this.jobDescription != null
                                               : !JOB_DESCRIPTION_EDEFAULT.equals( this.jobDescription );
      case IWorkflowPackage.IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME:
        return JOB_DESCRIPTION_FILE_NAME_EDEFAULT == null
                                                         ? this.jobDescriptionFileName != null
                                                         : !JOB_DESCRIPTION_FILE_NAME_EDEFAULT.equals( this.jobDescriptionFileName );
    }
    return super.eIsSet( featureID );
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String toString() {
    if( eIsProxy() )
      return super.toString();
    StringBuffer result = new StringBuffer( super.toString() );
    result.append( " (jobDescription: " ); //$NON-NLS-1$
    result.append( this.jobDescription );
    result.append( ", jobDescriptionFileName: " ); //$NON-NLS-1$
    result.append( this.jobDescriptionFileName );
    result.append( ')' );
    return result.toString();
  }
} //IWorkflowJobImpl
