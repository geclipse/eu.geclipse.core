/**
 * <copyright>
 * </copyright>
 *
 * $Id: JsdlFactory.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.JsdlPackage
 * @generated
 * @deprecated This interface is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base
 */
public interface JsdlFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	JsdlFactory eINSTANCE = eu.geclipse.jsdl.model.impl.JsdlFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Application Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Application Type</em>'.
   * @generated
   */
	ApplicationType createApplicationType();

  /**
   * Returns a new object of class '<em>Boundary Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Boundary Type</em>'.
   * @generated
   */
	BoundaryType createBoundaryType();

  /**
   * Returns a new object of class '<em>Candidate Hosts Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Candidate Hosts Type</em>'.
   * @generated
   */
	CandidateHostsType createCandidateHostsType();

  /**
   * Returns a new object of class '<em>CPU Architecture Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>CPU Architecture Type</em>'.
   * @generated
   */
	CPUArchitectureType createCPUArchitectureType();

  /**
   * Returns a new object of class '<em>Data Staging Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Data Staging Type</em>'.
   * @generated
   */
	DataStagingType createDataStagingType();

  /**
   * Returns a new object of class '<em>Document Root</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Document Root</em>'.
   * @generated
   */
	DocumentRoot createDocumentRoot();

  /**
   * Returns a new object of class '<em>Exact Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Exact Type</em>'.
   * @generated
   */
	ExactType createExactType();

  /**
   * Returns a new object of class '<em>File System Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>File System Type</em>'.
   * @generated
   */
	FileSystemType createFileSystemType();

  /**
   * Returns a new object of class '<em>Job Definition Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Job Definition Type</em>'.
   * @generated
   */
	JobDefinitionType createJobDefinitionType();

  /**
   * Returns a new object of class '<em>Job Description Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Job Description Type</em>'.
   * @generated
   */
	JobDescriptionType createJobDescriptionType();

  /**
   * Returns a new object of class '<em>Job Identification Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Job Identification Type</em>'.
   * @generated
   */
	JobIdentificationType createJobIdentificationType();

  /**
   * Returns a new object of class '<em>Operating System Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Operating System Type</em>'.
   * @generated
   */
	OperatingSystemType createOperatingSystemType();

  /**
   * Returns a new object of class '<em>Operating System Type Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Operating System Type Type</em>'.
   * @generated
   */
	OperatingSystemTypeType createOperatingSystemTypeType();

  /**
   * Returns a new object of class '<em>Range Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Range Type</em>'.
   * @generated
   */
	RangeType createRangeType();

  /**
   * Returns a new object of class '<em>Range Value Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Range Value Type</em>'.
   * @generated
   */
	RangeValueType createRangeValueType();

  /**
   * Returns a new object of class '<em>Resources Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Resources Type</em>'.
   * @generated
   */
	ResourcesType createResourcesType();

  /**
   * Returns a new object of class '<em>Source Target Type</em>'.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return a new object of class '<em>Source Target Type</em>'.
   * @generated
   */
	SourceTargetType createSourceTargetType();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
	JsdlPackage getJsdlPackage();

} //JsdlFactory
