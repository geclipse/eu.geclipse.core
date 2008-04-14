/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.SweepType#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.SweepType#getSweep <em>Sweep</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getSweepType()
 * @model extendedMetaData="name='Sweep_Type' kind='elementOnly'"
 * @generated
 */
public interface SweepType extends EObject
{
  /**
   * Returns the value of the '<em><b>Assignment</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.sweep.AssignmentType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Assignment</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assignment</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getSweepType_Assignment()
   * @model type="eu.geclipse.jsdl.model.sweep.AssignmentType" containment="true" required="true"
   *        extendedMetaData="kind='element' name='Assignment' namespace='##targetNamespace'"
   * @generated
   */
  EList getAssignment();

  /**
   * Returns the value of the '<em><b>Sweep</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.sweep.SweepType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Sweep</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Sweep</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.sweep.SweepPackage#getSweepType_Sweep()
   * @model type="eu.geclipse.jsdl.model.sweep.SweepType" containment="true"
   *        extendedMetaData="kind='element' name='Sweep' namespace='##targetNamespace'"
   * @generated
   */
  EList getSweep();

} // SweepType
