/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assignment Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.AssignmentType#getParameter <em>Parameter</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.AssignmentType#getFunctionGroup <em>Function Group</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.AssignmentType#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getAssignmentType()
 * @model extendedMetaData="name='Assignment_._type' kind='elementOnly'"
 * @generated
 */
public interface AssignmentType extends EObject
{
  /**
   * Returns the value of the '<em><b>Parameter</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameter</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter</em>' attribute list.
   * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getAssignmentType_Parameter()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='element' name='Parameter' namespace='##targetNamespace'"
   * @generated
   */
  EList getParameter();

  /**
   * Returns the value of the '<em><b>Function Group</b></em>' attribute list.
   * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Function Group</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function Group</em>' attribute list.
   * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getAssignmentType_FunctionGroup()
   * @model dataType="org.eclipse.emf.ecore.EFeatureMapEntry" required="true" many="false"
   *        extendedMetaData="kind='group' name='Function:group' namespace='##targetNamespace'"
   * @generated
   */
  FeatureMap getFunctionGroup();

  /**
   * Returns the value of the '<em><b>Function</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Function</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function</em>' containment reference.
   * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getAssignmentType_Function()
   * @model containment="true" required="true" transient="true" changeable="false" volatile="true" derived="true"
   *        extendedMetaData="kind='element' name='Function' namespace='##targetNamespace' group='Function:group'"
   * @generated
   */
  EObject getFunction();

} // AssignmentType
