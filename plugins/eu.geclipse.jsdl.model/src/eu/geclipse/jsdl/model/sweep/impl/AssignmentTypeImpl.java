/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.impl;

import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assignment Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl#getFunctionGroup <em>Function Group</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.AssignmentTypeImpl#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssignmentTypeImpl extends EObjectImpl implements AssignmentType
{
  /**
   * The cached value of the '{@link #getParameter() <em>Parameter</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameter()
   * @generated
   * @ordered
   */
  protected EList parameter;

  /**
   * The cached value of the '{@link #getFunctionGroup() <em>Function Group</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctionGroup()
   * @generated
   * @ordered
   */
  protected FeatureMap functionGroup;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AssignmentTypeImpl()
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
    return SweepPackage.Literals.ASSIGNMENT_TYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getParameter()
  {
    if (parameter == null)
    {
      parameter = new EDataTypeEList(String.class, this, SweepPackage.ASSIGNMENT_TYPE__PARAMETER);
    }
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getFunctionGroup()
  {
    if (functionGroup == null)
    {
      functionGroup = new BasicFeatureMap(this, SweepPackage.ASSIGNMENT_TYPE__FUNCTION_GROUP);
    }
    return functionGroup;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EObject getFunction()
  {
    return (EObject)getFunctionGroup().get(SweepPackage.Literals.ASSIGNMENT_TYPE__FUNCTION, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFunction(EObject newFunction, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getFunctionGroup()).basicAdd(SweepPackage.Literals.ASSIGNMENT_TYPE__FUNCTION, newFunction, msgs);
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
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION_GROUP:
        return ((InternalEList)getFunctionGroup()).basicRemove(otherEnd, msgs);
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION:
        return basicSetFunction(null, msgs);
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
      case SweepPackage.ASSIGNMENT_TYPE__PARAMETER:
        return getParameter();
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION_GROUP:
        if (coreType) return getFunctionGroup();
        return ((FeatureMap.Internal)getFunctionGroup()).getWrapper();
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION:
        return getFunction();
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
      case SweepPackage.ASSIGNMENT_TYPE__PARAMETER:
        getParameter().clear();
        getParameter().addAll((Collection)newValue);
        return;
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION_GROUP:
        ((FeatureMap.Internal)getFunctionGroup()).set(newValue);
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
      case SweepPackage.ASSIGNMENT_TYPE__PARAMETER:
        getParameter().clear();
        return;
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION_GROUP:
        getFunctionGroup().clear();
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
      case SweepPackage.ASSIGNMENT_TYPE__PARAMETER:
        return parameter != null && !parameter.isEmpty();
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION_GROUP:
        return functionGroup != null && !functionGroup.isEmpty();
      case SweepPackage.ASSIGNMENT_TYPE__FUNCTION:
        return getFunction() != null;
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
    result.append(" (parameter: ");
    result.append(parameter);
    result.append(", functionGroup: ");
    result.append(functionGroup);
    result.append(')');
    return result.toString();
  }

} //AssignmentTypeImpl
