/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.functions.impl;

import eu.geclipse.jsdl.model.functions.ExceptionType;
import eu.geclipse.jsdl.model.functions.FunctionsPackage;
import eu.geclipse.jsdl.model.functions.LoopType;

import java.math.BigInteger;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Loop Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl#getException <em>Exception</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl#getEnd <em>End</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl#getStart <em>Start</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.functions.impl.LoopTypeImpl#getStep <em>Step</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoopTypeImpl extends EObjectImpl implements LoopType
{
  /**
   * The cached value of the '{@link #getException() <em>Exception</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getException()
   * @generated
   * @ordered
   */
  protected EList exception;

  /**
   * The default value of the '{@link #getEnd() <em>End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnd()
   * @generated
   * @ordered
   */
  protected static final BigInteger END_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEnd() <em>End</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnd()
   * @generated
   * @ordered
   */
  protected BigInteger end = END_EDEFAULT;

  /**
   * The default value of the '{@link #getStart() <em>Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStart()
   * @generated
   * @ordered
   */
  protected static final BigInteger START_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getStart() <em>Start</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStart()
   * @generated
   * @ordered
   */
  protected BigInteger start = START_EDEFAULT;

  /**
   * The default value of the '{@link #getStep() <em>Step</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStep()
   * @generated
   * @ordered
   */
  protected static final BigInteger STEP_EDEFAULT = new BigInteger("1");

  /**
   * The cached value of the '{@link #getStep() <em>Step</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStep()
   * @generated
   * @ordered
   */
  protected BigInteger step = STEP_EDEFAULT;

  /**
   * This is true if the Step attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean stepESet;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LoopTypeImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EClass eStaticClass()
  {
    return FunctionsPackage.Literals.LOOP_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getException()
  {
    if (exception == null)
    {
      exception = new EObjectContainmentEList(ExceptionType.class, this, FunctionsPackage.LOOP_TYPE__EXCEPTION);
    }
    return exception;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigInteger getEnd()
  {
    return end;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnd(BigInteger newEnd)
  {
    BigInteger oldEnd = end;
    end = newEnd;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.LOOP_TYPE__END, oldEnd, end));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigInteger getStart()
  {
    return start;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStart(BigInteger newStart)
  {
    BigInteger oldStart = start;
    start = newStart;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.LOOP_TYPE__START, oldStart, start));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BigInteger getStep()
  {
    return step;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStep(BigInteger newStep)
  {
    BigInteger oldStep = step;
    step = newStep;
    boolean oldStepESet = stepESet;
    stepESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FunctionsPackage.LOOP_TYPE__STEP, oldStep, step, !oldStepESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetStep()
  {
    BigInteger oldStep = step;
    boolean oldStepESet = stepESet;
    step = STEP_EDEFAULT;
    stepESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, FunctionsPackage.LOOP_TYPE__STEP, oldStep, STEP_EDEFAULT, oldStepESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetStep()
  {
    return stepESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case FunctionsPackage.LOOP_TYPE__EXCEPTION:
        return ((InternalEList)getException()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case FunctionsPackage.LOOP_TYPE__EXCEPTION:
        return getException();
      case FunctionsPackage.LOOP_TYPE__END:
        return getEnd();
      case FunctionsPackage.LOOP_TYPE__START:
        return getStart();
      case FunctionsPackage.LOOP_TYPE__STEP:
        return getStep();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case FunctionsPackage.LOOP_TYPE__EXCEPTION:
        getException().clear();
        getException().addAll((Collection)newValue);
        return;
      case FunctionsPackage.LOOP_TYPE__END:
        setEnd((BigInteger)newValue);
        return;
      case FunctionsPackage.LOOP_TYPE__START:
        setStart((BigInteger)newValue);
        return;
      case FunctionsPackage.LOOP_TYPE__STEP:
        setStep((BigInteger)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case FunctionsPackage.LOOP_TYPE__EXCEPTION:
        getException().clear();
        return;
      case FunctionsPackage.LOOP_TYPE__END:
        setEnd(END_EDEFAULT);
        return;
      case FunctionsPackage.LOOP_TYPE__START:
        setStart(START_EDEFAULT);
        return;
      case FunctionsPackage.LOOP_TYPE__STEP:
        unsetStep();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case FunctionsPackage.LOOP_TYPE__EXCEPTION:
        return exception != null && !exception.isEmpty();
      case FunctionsPackage.LOOP_TYPE__END:
        return END_EDEFAULT == null ? end != null : !END_EDEFAULT.equals(end);
      case FunctionsPackage.LOOP_TYPE__START:
        return START_EDEFAULT == null ? start != null : !START_EDEFAULT.equals(start);
      case FunctionsPackage.LOOP_TYPE__STEP:
        return isSetStep();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (end: ");
    result.append(end);
    result.append(", start: ");
    result.append(start);
    result.append(", step: ");
    if (stepESet) result.append(step); else result.append("<unset>");
    result.append(')');
    return result.toString();
  }

} //LoopTypeImpl
