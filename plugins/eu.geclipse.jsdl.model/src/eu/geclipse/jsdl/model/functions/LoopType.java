/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.functions;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loop Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.functions.LoopType#getException <em>Exception</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.functions.LoopType#getEnd <em>End</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.functions.LoopType#getStart <em>Start</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.functions.LoopType#getStep <em>Step</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.jsdl.model.functions.FunctionsPackage#getLoopType()
 * @model extendedMetaData="name='Loop_._type' kind='elementOnly'"
 * @generated
 */
public interface LoopType extends EObject
{
  /**
   * Returns the value of the '<em><b>Exception</b></em>' containment reference list.
   * The list contents are of type {@link eu.geclipse.jsdl.model.functions.ExceptionType}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Exception</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exception</em>' containment reference list.
   * @see eu.geclipse.jsdl.model.functions.FunctionsPackage#getLoopType_Exception()
   * @model type="eu.geclipse.jsdl.model.functions.ExceptionType" containment="true"
   *        extendedMetaData="kind='element' name='Exception' namespace='##targetNamespace'"
   * @generated
   */
  EList getException();

  /**
   * Returns the value of the '<em><b>End</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>End</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>End</em>' attribute.
   * @see #setEnd(BigInteger)
   * @see eu.geclipse.jsdl.model.functions.FunctionsPackage#getLoopType_End()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Integer" required="true"
   *        extendedMetaData="kind='attribute' name='end' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getEnd();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.functions.LoopType#getEnd <em>End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>End</em>' attribute.
   * @see #getEnd()
   * @generated
   */
  void setEnd(BigInteger value);

  /**
   * Returns the value of the '<em><b>Start</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Start</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Start</em>' attribute.
   * @see #setStart(BigInteger)
   * @see eu.geclipse.jsdl.model.functions.FunctionsPackage#getLoopType_Start()
   * @model dataType="org.eclipse.emf.ecore.xml.type.Integer" required="true"
   *        extendedMetaData="kind='attribute' name='start' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getStart();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.functions.LoopType#getStart <em>Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Start</em>' attribute.
   * @see #getStart()
   * @generated
   */
  void setStart(BigInteger value);

  /**
   * Returns the value of the '<em><b>Step</b></em>' attribute.
   * The default value is <code>"1"</code>.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Step</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Step</em>' attribute.
   * @see #isSetStep()
   * @see #unsetStep()
   * @see #setStep(BigInteger)
   * @see eu.geclipse.jsdl.model.functions.FunctionsPackage#getLoopType_Step()
   * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.PositiveInteger"
   *        extendedMetaData="kind='attribute' name='step' namespace='##targetNamespace'"
   * @generated
   */
  BigInteger getStep();

  /**
   * Sets the value of the '{@link eu.geclipse.jsdl.model.functions.LoopType#getStep <em>Step</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Step</em>' attribute.
   * @see #isSetStep()
   * @see #unsetStep()
   * @see #getStep()
   * @generated
   */
  void setStep(BigInteger value);

  /**
   * Unsets the value of the '{@link eu.geclipse.jsdl.model.functions.LoopType#getStep <em>Step</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isSetStep()
   * @see #getStep()
   * @see #setStep(BigInteger)
   * @generated
   */
  void unsetStep();

  /**
   * Returns whether the value of the '{@link eu.geclipse.jsdl.model.functions.LoopType#getStep <em>Step</em>}' attribute is set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return whether the value of the '<em>Step</em>' attribute is set.
   * @see #unsetStep()
   * @see #getStep()
   * @see #setStep(BigInteger)
   * @generated
   */
  boolean isSetStep();

} // LoopType
