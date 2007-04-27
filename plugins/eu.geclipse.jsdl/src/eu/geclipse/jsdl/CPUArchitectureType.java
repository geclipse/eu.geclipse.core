/**
 * <copyright>
 * </copyright>
 *
 * $Id: CPUArchitectureType.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CPU Architecture Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.CPUArchitectureType#getCPUArchitectureName <em>CPU Architecture Name</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.CPUArchitectureType#getAny <em>Any</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.CPUArchitectureType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.JsdlPackage#getCPUArchitectureType()
 * @model extendedMetaData="name='CPUArchitecture_Type' kind='elementOnly'"
 * @generated
 */
public interface CPUArchitectureType extends EObject
{
  /**
   * Returns the value of the '<em><b>CPU Architecture Name</b></em>' attribute.
   * The default value is <code>"sparc"</code>.
   * The literals are from the enumeration {@link eu.geclipse.jsdl.ProcessorArchitectureEnumeration}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CPU Architecture Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>CPU Architecture Name</em>' attribute.
   * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
   * @see #isSetCPUArchitectureName()
   * @see #unsetCPUArchitectureName()
   * @see #setCPUArchitectureName(ProcessorArchitectureEnumeration)
   * @see eu.geclipse.jsdl.JsdlPackage#getCPUArchitectureType_CPUArchitectureName()
   * @model default="sparc" unique="false" unsettable="true" required="true"
   *        extendedMetaData="kind='element' name='CPUArchitectureName' namespace='##targetNamespace'"
   * @generated
   */
	ProcessorArchitectureEnumeration getCPUArchitectureName();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.CPUArchitectureType#getCPUArchitectureName <em>CPU Architecture Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @param value the new value of the '<em>CPU Architecture Name</em>' attribute.
   * @see eu.geclipse.jsdl.ProcessorArchitectureEnumeration
   * @see #isSetCPUArchitectureName()
   * @see #unsetCPUArchitectureName()
   * @see #getCPUArchitectureName()
   * @generated
   */
	void setCPUArchitectureName(ProcessorArchitectureEnumeration value);

  /**
   * Unsets the value of the '{@link eu.geclipse.jsdl.CPUArchitectureType#getCPUArchitectureName <em>CPU Architecture Name</em>}' attribute.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #isSetCPUArchitectureName()
   * @see #getCPUArchitectureName()
   * @see #setCPUArchitectureName(ProcessorArchitectureEnumeration)
   * @generated
   */
	void unsetCPUArchitectureName();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.CPUArchitectureType#getCPUArchitectureName <em>CPU Architecture Name</em>}' attribute is set.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @return whether the value of the '<em>CPU Architecture Name</em>' attribute is set.
   * @see #unsetCPUArchitectureName()
   * @see #getCPUArchitectureName()
   * @see #setCPUArchitectureName(ProcessorArchitectureEnumeration)
   * @generated
   */
	boolean isSetCPUArchitectureName();

  /**
   * Returns the value of the '<em><b>Any</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Any</em>' attribute list.
   * @see eu.geclipse.jsdl.JsdlPackage#getCPUArchitectureType_Any()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':1' processing='lax'"
   * @generated
   */
	FeatureMap getAny();

  /**
   * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @return the value of the '<em>Any Attribute</em>' attribute list.
   * @see eu.geclipse.jsdl.JsdlPackage#getCPUArchitectureType_AnyAttribute()
   * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
   *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':2' processing='lax'"
   * @generated
   */
	FeatureMap getAnyAttribute();

} // CPUArchitectureType