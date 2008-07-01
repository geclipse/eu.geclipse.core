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

import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.ILink;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowElement;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.IWorkflowNode;
import eu.geclipse.workflow.WorkflowFactory;
import eu.geclipse.workflow.WorkflowPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WorkflowPackageImpl extends EPackageImpl implements WorkflowPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iPortEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iLinkEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iInputPortEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iOutputPortEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iWorkflowEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iWorkflowJobEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iWorkflowElementEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass iWorkflowNodeEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see eu.geclipse.workflow.WorkflowPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private WorkflowPackageImpl()
  {
    super(eNS_URI, WorkflowFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this
   * model, and for any others upon which it depends.  Simple
   * dependencies are satisfied by calling this method on all
   * dependent packages before doing anything else.  This method drives
   * initialization for interdependent packages directly, in parallel
   * with this package, itself.
   * <p>Of this package and its interdependencies, all packages which
   * have not yet been registered by their URI values are first created
   * and registered.  The packages are then initialized in two steps:
   * meta-model objects for all of the packages are created before any
   * are initialized, since one package's meta-model objects may refer to
   * those of another.
   * <p>Invocation of this method will not affect any packages that have
   * already been initialized.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return WorkflowPackageImpl
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static WorkflowPackage init()
  {
    if (isInited) return (WorkflowPackage)EPackage.Registry.INSTANCE.getEPackage(WorkflowPackage.eNS_URI);

    // Obtain or create and register package
    WorkflowPackageImpl theWorkflowPackage = (WorkflowPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof WorkflowPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new WorkflowPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theWorkflowPackage.createPackageContents();

    // Initialize created meta-data
    theWorkflowPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theWorkflowPackage.freeze();

    return theWorkflowPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIPort()
  {
    return this.iPortEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getILink()
  {
    return this.iLinkEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getILink_Workflow()
  {
    return (EReference)this.iLinkEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getILink_Target()
  {
    return (EReference)this.iLinkEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getILink_Source()
  {
    return (EReference)this.iLinkEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIInputPort()
  {
    return this.iInputPortEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIInputPort_Node()
  {
    return (EReference)this.iInputPortEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIInputPort_Links()
  {
    return (EReference)this.iInputPortEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIOutputPort()
  {
    return this.iOutputPortEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIOutputPort_Node()
  {
    return (EReference)this.iOutputPortEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIOutputPort_Links()
  {
    return (EReference)this.iOutputPortEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIWorkflow()
  {
    return this.iWorkflowEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIWorkflow_Nodes()
  {
    return (EReference)this.iWorkflowEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIWorkflow_Links()
  {
    return (EReference)this.iWorkflowEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIWorkflowJob()
  {
    return this.iWorkflowJobEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIWorkflowJob_JobDescription()
  {
    return (EAttribute)this.iWorkflowJobEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIWorkflowJob_JobDescriptionFileName()
  {
    return (EAttribute)this.iWorkflowJobEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIWorkflowElement()
  {
    return this.iWorkflowElementEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIWorkflowElement_Name()
  {
    return (EAttribute)this.iWorkflowElementEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIWorkflowElement_Id()
  {
    return (EAttribute)this.iWorkflowElementEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getIWorkflowNode()
  {
    return this.iWorkflowNodeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIWorkflowNode_Workflow()
  {
    return (EReference)this.iWorkflowNodeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIWorkflowNode_Outputs()
  {
    return (EReference)this.iWorkflowNodeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getIWorkflowNode_Inputs()
  {
    return (EReference)this.iWorkflowNodeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIWorkflowNode_IsStart()
  {
    return (EAttribute)this.iWorkflowNodeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getIWorkflowNode_IsFinish()
  {
    return (EAttribute)this.iWorkflowNodeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WorkflowFactory getWorkflowFactory()
  {
    return (WorkflowFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (this.isCreated) return;
    this.isCreated = true;

    // Create classes and their features
    this.iPortEClass = createEClass(IPORT);

    this.iLinkEClass = createEClass(ILINK);
    createEReference(this.iLinkEClass, ILINK__WORKFLOW);
    createEReference(this.iLinkEClass, ILINK__TARGET);
    createEReference(this.iLinkEClass, ILINK__SOURCE);

    this.iInputPortEClass = createEClass(IINPUT_PORT);
    createEReference(this.iInputPortEClass, IINPUT_PORT__NODE);
    createEReference(this.iInputPortEClass, IINPUT_PORT__LINKS);

    this.iOutputPortEClass = createEClass(IOUTPUT_PORT);
    createEReference(this.iOutputPortEClass, IOUTPUT_PORT__NODE);
    createEReference(this.iOutputPortEClass, IOUTPUT_PORT__LINKS);

    this.iWorkflowEClass = createEClass(IWORKFLOW);
    createEReference(this.iWorkflowEClass, IWORKFLOW__NODES);
    createEReference(this.iWorkflowEClass, IWORKFLOW__LINKS);

    this.iWorkflowJobEClass = createEClass(IWORKFLOW_JOB);
    createEAttribute(this.iWorkflowJobEClass, IWORKFLOW_JOB__JOB_DESCRIPTION);
    createEAttribute(this.iWorkflowJobEClass, IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME);

    this.iWorkflowElementEClass = createEClass(IWORKFLOW_ELEMENT);
    createEAttribute(this.iWorkflowElementEClass, IWORKFLOW_ELEMENT__NAME);
    createEAttribute(this.iWorkflowElementEClass, IWORKFLOW_ELEMENT__ID);

    this.iWorkflowNodeEClass = createEClass(IWORKFLOW_NODE);
    createEReference(this.iWorkflowNodeEClass, IWORKFLOW_NODE__WORKFLOW);
    createEReference(this.iWorkflowNodeEClass, IWORKFLOW_NODE__OUTPUTS);
    createEReference(this.iWorkflowNodeEClass, IWORKFLOW_NODE__INPUTS);
    createEAttribute(this.iWorkflowNodeEClass, IWORKFLOW_NODE__IS_START);
    createEAttribute(this.iWorkflowNodeEClass, IWORKFLOW_NODE__IS_FINISH);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (this.isInitialized) return;
    this.isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    this.iPortEClass.getESuperTypes().add(this.getIWorkflowElement());
    this.iLinkEClass.getESuperTypes().add(this.getIWorkflowElement());
    this.iInputPortEClass.getESuperTypes().add(this.getIPort());
    this.iOutputPortEClass.getESuperTypes().add(this.getIPort());
    this.iWorkflowEClass.getESuperTypes().add(this.getIWorkflowElement());
    this.iWorkflowJobEClass.getESuperTypes().add(this.getIWorkflowNode());
    this.iWorkflowNodeEClass.getESuperTypes().add(this.getIWorkflowElement());

    // Initialize classes and features; add operations and parameters
    initEClass(this.iPortEClass, IPort.class, "IPort", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

    initEClass(this.iLinkEClass, ILink.class, "ILink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEReference(getILink_Workflow(), this.getIWorkflow(), this.getIWorkflow_Links(), "workflow", null, 1, 1, ILink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getILink_Target(), this.getIInputPort(), this.getIInputPort_Links(), "target", null, 1, 1, ILink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getILink_Source(), this.getIOutputPort(), this.getIOutputPort_Links(), "source", null, 1, 1, ILink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

    initEClass(this.iInputPortEClass, IInputPort.class, "IInputPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEReference(getIInputPort_Node(), this.getIWorkflowNode(), this.getIWorkflowNode_Inputs(), "node", null, 1, 1, IInputPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getIInputPort_Links(), this.getILink(), this.getILink_Target(), "links", null, 0, -1, IInputPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

    initEClass(this.iOutputPortEClass, IOutputPort.class, "IOutputPort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEReference(getIOutputPort_Node(), this.getIWorkflowNode(), this.getIWorkflowNode_Outputs(), "node", null, 1, 1, IOutputPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getIOutputPort_Links(), this.getILink(), this.getILink_Source(), "links", null, 0, -1, IOutputPort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

    initEClass(this.iWorkflowEClass, IWorkflow.class, "IWorkflow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEReference(getIWorkflow_Nodes(), this.getIWorkflowNode(), this.getIWorkflowNode_Workflow(), "nodes", null, 0, -1, IWorkflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getIWorkflow_Links(), this.getILink(), this.getILink_Workflow(), "links", null, 0, -1, IWorkflow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

    initEClass(this.iWorkflowJobEClass, IWorkflowJob.class, "IWorkflowJob", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEAttribute(getIWorkflowJob_JobDescription(), this.ecorePackage.getEString(), "jobDescription", null, 1, 1, IWorkflowJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEAttribute(getIWorkflowJob_JobDescriptionFileName(), this.ecorePackage.getEString(), "jobDescriptionFileName", null, 1, 1, IWorkflowJob.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

    initEClass(this.iWorkflowElementEClass, IWorkflowElement.class, "IWorkflowElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEAttribute(getIWorkflowElement_Name(), this.ecorePackage.getEString(), "name", null, 0, 1, IWorkflowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEAttribute(getIWorkflowElement_Id(), this.ecorePackage.getEString(), "id", null, 1, 1, IWorkflowElement.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

    initEClass(this.iWorkflowNodeEClass, IWorkflowNode.class, "IWorkflowNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
    initEReference(getIWorkflowNode_Workflow(), this.getIWorkflow(), this.getIWorkflow_Nodes(), "workflow", null, 1, 1, IWorkflowNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getIWorkflowNode_Outputs(), this.getIOutputPort(), this.getIOutputPort_Node(), "outputs", null, 1, -1, IWorkflowNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEReference(getIWorkflowNode_Inputs(), this.getIInputPort(), this.getIInputPort_Node(), "inputs", null, 1, -1, IWorkflowNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
    initEAttribute(getIWorkflowNode_IsStart(), this.ecorePackage.getEBoolean(), "isStart", "false", 1, 1, IWorkflowNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
    initEAttribute(getIWorkflowNode_IsFinish(), this.ecorePackage.getEBoolean(), "isFinish", "false", 1, 1, IWorkflowNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

    // Create resource
    createResource(eNS_URI);
  }

} //WorkflowPackageImpl
