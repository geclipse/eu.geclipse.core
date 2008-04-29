/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.impl;

import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepPackage;
import eu.geclipse.jsdl.model.sweep.SweepType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.SweepTypeImpl#getSweep <em>Sweep</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SweepTypeImpl extends EObjectImpl implements SweepType
{
  /**
   * The cached value of the '{@link #getAssignment() <em>Assignment</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAssignment()
   * @generated
   * @ordered
   */
  protected EList assignment;

  /**
   * The cached value of the '{@link #getSweep() <em>Sweep</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSweep()
   * @generated
   * @ordered
   */
  protected EList sweep;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SweepTypeImpl()
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
    return SweepPackage.Literals.SWEEP_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getAssignment()
  {
    if (assignment == null)
    {
      assignment = new EObjectContainmentEList(AssignmentType.class, this, SweepPackage.SWEEP_TYPE__ASSIGNMENT);
    }
    return assignment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getSweep()
  {
    if (sweep == null)
    {
      sweep = new EObjectContainmentEList(SweepType.class, this, SweepPackage.SWEEP_TYPE__SWEEP);
    }
    return sweep;
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
      case SweepPackage.SWEEP_TYPE__ASSIGNMENT:
        return ((InternalEList)getAssignment()).basicRemove(otherEnd, msgs);
      case SweepPackage.SWEEP_TYPE__SWEEP:
        return ((InternalEList)getSweep()).basicRemove(otherEnd, msgs);
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
      case SweepPackage.SWEEP_TYPE__ASSIGNMENT:
        return getAssignment();
      case SweepPackage.SWEEP_TYPE__SWEEP:
        return getSweep();
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
      case SweepPackage.SWEEP_TYPE__ASSIGNMENT:
        getAssignment().clear();
        getAssignment().addAll((Collection)newValue);
        return;
      case SweepPackage.SWEEP_TYPE__SWEEP:
        getSweep().clear();
        getSweep().addAll((Collection)newValue);
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
      case SweepPackage.SWEEP_TYPE__ASSIGNMENT:
        getAssignment().clear();
        return;
      case SweepPackage.SWEEP_TYPE__SWEEP:
        getSweep().clear();
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
      case SweepPackage.SWEEP_TYPE__ASSIGNMENT:
        return assignment != null && !assignment.isEmpty();
      case SweepPackage.SWEEP_TYPE__SWEEP:
        return sweep != null && !sweep.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //SweepTypeImpl
