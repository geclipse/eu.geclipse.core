/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.workflow;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see eu.geclipse.workflow.WorkflowFactory
 * @model kind="package"
 * @generated
 */
public interface IWorkflowPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "workflow"; //$NON-NLS-1$

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http:///eu/geclipse/workflow.ecore"; //$NON-NLS-1$

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "eu.geclipse.workflow.model"; //$NON-NLS-1$

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  WorkflowPackage eINSTANCE = eu.geclipse.workflow.impl.WorkflowPackageImpl.init();

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IWorkflowElementImpl <em>IWorkflow Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IWorkflowElementImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflowElement()
   * @generated
   */
  int IWORKFLOW_ELEMENT = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_ELEMENT__NAME = 0;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_ELEMENT__ID = 1;

  /**
   * The number of structural features of the '<em>IWorkflow Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_ELEMENT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IPortImpl <em>IPort</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IPortImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIPort()
   * @generated
   */
  int IPORT = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IPORT__NAME = IWORKFLOW_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IPORT__ID = IWORKFLOW_ELEMENT__ID;

  /**
   * The number of structural features of the '<em>IPort</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IPORT_FEATURE_COUNT = IWORKFLOW_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.ILinkImpl <em>ILink</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.ILinkImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getILink()
   * @generated
   */
  int ILINK = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ILINK__NAME = IWORKFLOW_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ILINK__ID = IWORKFLOW_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Workflow</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ILINK__WORKFLOW = IWORKFLOW_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ILINK__TARGET = IWORKFLOW_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Source</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ILINK__SOURCE = IWORKFLOW_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>ILink</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ILINK_FEATURE_COUNT = IWORKFLOW_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IInputPortImpl <em>IInput Port</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IInputPortImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIInputPort()
   * @generated
   */
  int IINPUT_PORT = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IINPUT_PORT__NAME = IPORT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IINPUT_PORT__ID = IPORT__ID;

  /**
   * The feature id for the '<em><b>Node</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IINPUT_PORT__NODE = IPORT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Links</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IINPUT_PORT__LINKS = IPORT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>IInput Port</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IINPUT_PORT_FEATURE_COUNT = IPORT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IOutputPortImpl <em>IOutput Port</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IOutputPortImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIOutputPort()
   * @generated
   */
  int IOUTPUT_PORT = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IOUTPUT_PORT__NAME = IPORT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IOUTPUT_PORT__ID = IPORT__ID;

  /**
   * The feature id for the '<em><b>Node</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IOUTPUT_PORT__NODE = IPORT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Links</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IOUTPUT_PORT__LINKS = IPORT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>IOutput Port</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IOUTPUT_PORT_FEATURE_COUNT = IPORT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IWorkflowImpl <em>IWorkflow</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IWorkflowImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflow()
   * @generated
   */
  int IWORKFLOW = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW__NAME = IWORKFLOW_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW__ID = IWORKFLOW_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW__NODES = IWORKFLOW_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Links</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW__LINKS = IWORKFLOW_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>IWorkflow</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_FEATURE_COUNT = IWORKFLOW_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl <em>IWorkflow Node</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IWorkflowNodeImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflowNode()
   * @generated
   */
  int IWORKFLOW_NODE = 7;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__NAME = IWORKFLOW_ELEMENT__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__ID = IWORKFLOW_ELEMENT__ID;

  /**
   * The feature id for the '<em><b>Workflow</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__WORKFLOW = IWORKFLOW_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__OUTPUTS = IWORKFLOW_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__INPUTS = IWORKFLOW_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Is Start</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__IS_START = IWORKFLOW_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Is Finish</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE__IS_FINISH = IWORKFLOW_ELEMENT_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>IWorkflow Node</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_NODE_FEATURE_COUNT = IWORKFLOW_ELEMENT_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link eu.geclipse.workflow.impl.IWorkflowJobImpl <em>IWorkflow Job</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see eu.geclipse.workflow.impl.IWorkflowJobImpl
   * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflowJob()
   * @generated
   */
  int IWORKFLOW_JOB = 5;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__NAME = IWORKFLOW_NODE__NAME;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__ID = IWORKFLOW_NODE__ID;

  /**
   * The feature id for the '<em><b>Workflow</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__WORKFLOW = IWORKFLOW_NODE__WORKFLOW;

  /**
   * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__OUTPUTS = IWORKFLOW_NODE__OUTPUTS;

  /**
   * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__INPUTS = IWORKFLOW_NODE__INPUTS;

  /**
   * The feature id for the '<em><b>Is Start</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__IS_START = IWORKFLOW_NODE__IS_START;

  /**
   * The feature id for the '<em><b>Is Finish</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__IS_FINISH = IWORKFLOW_NODE__IS_FINISH;

  /**
   * The feature id for the '<em><b>Job Description</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__JOB_DESCRIPTION = IWORKFLOW_NODE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Job Description File Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME = IWORKFLOW_NODE_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>IWorkflow Job</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int IWORKFLOW_JOB_FEATURE_COUNT = IWORKFLOW_NODE_FEATURE_COUNT + 2;


  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IPort <em>IPort</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IPort</em>'.
   * @see eu.geclipse.workflow.IPort
   * @generated
   */
  EClass getIPort();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.ILink <em>ILink</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>ILink</em>'.
   * @see eu.geclipse.workflow.ILink
   * @generated
   */
  EClass getILink();

  /**
   * Returns the meta object for the container reference '{@link eu.geclipse.workflow.ILink#getWorkflow <em>Workflow</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>Workflow</em>'.
   * @see eu.geclipse.workflow.ILink#getWorkflow()
   * @see #getILink()
   * @generated
   */
  EReference getILink_Workflow();

  /**
   * Returns the meta object for the reference '{@link eu.geclipse.workflow.ILink#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target</em>'.
   * @see eu.geclipse.workflow.ILink#getTarget()
   * @see #getILink()
   * @generated
   */
  EReference getILink_Target();

  /**
   * Returns the meta object for the reference '{@link eu.geclipse.workflow.ILink#getSource <em>Source</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Source</em>'.
   * @see eu.geclipse.workflow.ILink#getSource()
   * @see #getILink()
   * @generated
   */
  EReference getILink_Source();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IInputPort <em>IInput Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IInput Port</em>'.
   * @see eu.geclipse.workflow.IInputPort
   * @generated
   */
  EClass getIInputPort();

  /**
   * Returns the meta object for the container reference '{@link eu.geclipse.workflow.IInputPort#getNode <em>Node</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>Node</em>'.
   * @see eu.geclipse.workflow.IInputPort#getNode()
   * @see #getIInputPort()
   * @generated
   */
  EReference getIInputPort_Node();

  /**
   * Returns the meta object for the reference list '{@link eu.geclipse.workflow.IInputPort#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Links</em>'.
   * @see eu.geclipse.workflow.IInputPort#getLinks()
   * @see #getIInputPort()
   * @generated
   */
  EReference getIInputPort_Links();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IOutputPort <em>IOutput Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IOutput Port</em>'.
   * @see eu.geclipse.workflow.IOutputPort
   * @generated
   */
  EClass getIOutputPort();

  /**
   * Returns the meta object for the container reference '{@link eu.geclipse.workflow.IOutputPort#getNode <em>Node</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>Node</em>'.
   * @see eu.geclipse.workflow.IOutputPort#getNode()
   * @see #getIOutputPort()
   * @generated
   */
  EReference getIOutputPort_Node();

  /**
   * Returns the meta object for the reference list '{@link eu.geclipse.workflow.IOutputPort#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Links</em>'.
   * @see eu.geclipse.workflow.IOutputPort#getLinks()
   * @see #getIOutputPort()
   * @generated
   */
  EReference getIOutputPort_Links();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IWorkflow <em>IWorkflow</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IWorkflow</em>'.
   * @see eu.geclipse.workflow.IWorkflow
   * @generated
   */
  EClass getIWorkflow();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.workflow.IWorkflow#getNodes <em>Nodes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Nodes</em>'.
   * @see eu.geclipse.workflow.IWorkflow#getNodes()
   * @see #getIWorkflow()
   * @generated
   */
  EReference getIWorkflow_Nodes();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.workflow.IWorkflow#getLinks <em>Links</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Links</em>'.
   * @see eu.geclipse.workflow.IWorkflow#getLinks()
   * @see #getIWorkflow()
   * @generated
   */
  EReference getIWorkflow_Links();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IWorkflowJob <em>IWorkflow Job</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IWorkflow Job</em>'.
   * @see eu.geclipse.workflow.IWorkflowJob
   * @generated
   */
  EClass getIWorkflowJob();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.workflow.IWorkflowJob#getJobDescription <em>Job Description</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Job Description</em>'.
   * @see eu.geclipse.workflow.IWorkflowJob#getJobDescription()
   * @see #getIWorkflowJob()
   * @generated
   */
  EAttribute getIWorkflowJob_JobDescription();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.workflow.IWorkflowJob#getJobDescriptionFileName <em>Job Description File Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Job Description File Name</em>'.
   * @see eu.geclipse.workflow.IWorkflowJob#getJobDescriptionFileName()
   * @see #getIWorkflowJob()
   * @generated
   */
  EAttribute getIWorkflowJob_JobDescriptionFileName();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IWorkflowElement <em>IWorkflow Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IWorkflow Element</em>'.
   * @see eu.geclipse.workflow.IWorkflowElement
   * @generated
   */
  EClass getIWorkflowElement();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.workflow.IWorkflowElement#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see eu.geclipse.workflow.IWorkflowElement#getName()
   * @see #getIWorkflowElement()
   * @generated
   */
  EAttribute getIWorkflowElement_Name();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.workflow.IWorkflowElement#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see eu.geclipse.workflow.IWorkflowElement#getId()
   * @see #getIWorkflowElement()
   * @generated
   */
  EAttribute getIWorkflowElement_Id();

  /**
   * Returns the meta object for class '{@link eu.geclipse.workflow.IWorkflowNode <em>IWorkflow Node</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>IWorkflow Node</em>'.
   * @see eu.geclipse.workflow.IWorkflowNode
   * @generated
   */
  EClass getIWorkflowNode();

  /**
   * Returns the meta object for the container reference '{@link eu.geclipse.workflow.IWorkflowNode#getWorkflow <em>Workflow</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>Workflow</em>'.
   * @see eu.geclipse.workflow.IWorkflowNode#getWorkflow()
   * @see #getIWorkflowNode()
   * @generated
   */
  EReference getIWorkflowNode_Workflow();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.workflow.IWorkflowNode#getOutputs <em>Outputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Outputs</em>'.
   * @see eu.geclipse.workflow.IWorkflowNode#getOutputs()
   * @see #getIWorkflowNode()
   * @generated
   */
  EReference getIWorkflowNode_Outputs();

  /**
   * Returns the meta object for the containment reference list '{@link eu.geclipse.workflow.IWorkflowNode#getInputs <em>Inputs</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Inputs</em>'.
   * @see eu.geclipse.workflow.IWorkflowNode#getInputs()
   * @see #getIWorkflowNode()
   * @generated
   */
  EReference getIWorkflowNode_Inputs();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.workflow.IWorkflowNode#isIsStart <em>Is Start</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Start</em>'.
   * @see eu.geclipse.workflow.IWorkflowNode#isIsStart()
   * @see #getIWorkflowNode()
   * @generated
   */
  EAttribute getIWorkflowNode_IsStart();

  /**
   * Returns the meta object for the attribute '{@link eu.geclipse.workflow.IWorkflowNode#isIsFinish <em>Is Finish</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Finish</em>'.
   * @see eu.geclipse.workflow.IWorkflowNode#isIsFinish()
   * @see #getIWorkflowNode()
   * @generated
   */
  EAttribute getIWorkflowNode_IsFinish();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  WorkflowFactory getWorkflowFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IPortImpl <em>IPort</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IPortImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIPort()
     * @generated
     */
    EClass IPORT = eINSTANCE.getIPort();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.ILinkImpl <em>ILink</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.ILinkImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getILink()
     * @generated
     */
    EClass ILINK = eINSTANCE.getILink();

    /**
     * The meta object literal for the '<em><b>Workflow</b></em>' container reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ILINK__WORKFLOW = eINSTANCE.getILink_Workflow();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ILINK__TARGET = eINSTANCE.getILink_Target();

    /**
     * The meta object literal for the '<em><b>Source</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ILINK__SOURCE = eINSTANCE.getILink_Source();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IInputPortImpl <em>IInput Port</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IInputPortImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIInputPort()
     * @generated
     */
    EClass IINPUT_PORT = eINSTANCE.getIInputPort();

    /**
     * The meta object literal for the '<em><b>Node</b></em>' container reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IINPUT_PORT__NODE = eINSTANCE.getIInputPort_Node();

    /**
     * The meta object literal for the '<em><b>Links</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IINPUT_PORT__LINKS = eINSTANCE.getIInputPort_Links();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IOutputPortImpl <em>IOutput Port</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IOutputPortImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIOutputPort()
     * @generated
     */
    EClass IOUTPUT_PORT = eINSTANCE.getIOutputPort();

    /**
     * The meta object literal for the '<em><b>Node</b></em>' container reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IOUTPUT_PORT__NODE = eINSTANCE.getIOutputPort_Node();

    /**
     * The meta object literal for the '<em><b>Links</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IOUTPUT_PORT__LINKS = eINSTANCE.getIOutputPort_Links();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IWorkflowImpl <em>IWorkflow</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IWorkflowImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflow()
     * @generated
     */
    EClass IWORKFLOW = eINSTANCE.getIWorkflow();

    /**
     * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IWORKFLOW__NODES = eINSTANCE.getIWorkflow_Nodes();

    /**
     * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IWORKFLOW__LINKS = eINSTANCE.getIWorkflow_Links();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IWorkflowJobImpl <em>IWorkflow Job</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IWorkflowJobImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflowJob()
     * @generated
     */
    EClass IWORKFLOW_JOB = eINSTANCE.getIWorkflowJob();

    /**
     * The meta object literal for the '<em><b>Job Description</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IWORKFLOW_JOB__JOB_DESCRIPTION = eINSTANCE.getIWorkflowJob_JobDescription();

    /**
     * The meta object literal for the '<em><b>Job Description File Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IWORKFLOW_JOB__JOB_DESCRIPTION_FILE_NAME = eINSTANCE.getIWorkflowJob_JobDescriptionFileName();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IWorkflowElementImpl <em>IWorkflow Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IWorkflowElementImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflowElement()
     * @generated
     */
    EClass IWORKFLOW_ELEMENT = eINSTANCE.getIWorkflowElement();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IWORKFLOW_ELEMENT__NAME = eINSTANCE.getIWorkflowElement_Name();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IWORKFLOW_ELEMENT__ID = eINSTANCE.getIWorkflowElement_Id();

    /**
     * The meta object literal for the '{@link eu.geclipse.workflow.impl.IWorkflowNodeImpl <em>IWorkflow Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see eu.geclipse.workflow.impl.IWorkflowNodeImpl
     * @see eu.geclipse.workflow.impl.WorkflowPackageImpl#getIWorkflowNode()
     * @generated
     */
    EClass IWORKFLOW_NODE = eINSTANCE.getIWorkflowNode();

    /**
     * The meta object literal for the '<em><b>Workflow</b></em>' container reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IWORKFLOW_NODE__WORKFLOW = eINSTANCE.getIWorkflowNode_Workflow();

    /**
     * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IWORKFLOW_NODE__OUTPUTS = eINSTANCE.getIWorkflowNode_Outputs();

    /**
     * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference IWORKFLOW_NODE__INPUTS = eINSTANCE.getIWorkflowNode_Inputs();

    /**
     * The meta object literal for the '<em><b>Is Start</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IWORKFLOW_NODE__IS_START = eINSTANCE.getIWorkflowNode_IsStart();

    /**
     * The meta object literal for the '<em><b>Is Finish</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute IWORKFLOW_NODE__IS_FINISH = eINSTANCE.getIWorkflowNode_IsFinish();

  }

} //WorkflowPackage
