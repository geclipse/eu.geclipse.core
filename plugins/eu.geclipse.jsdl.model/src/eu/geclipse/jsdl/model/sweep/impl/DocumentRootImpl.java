/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.impl;

import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.DocumentRoot;
import eu.geclipse.jsdl.model.sweep.SweepPackage;
import eu.geclipse.jsdl.model.sweep.SweepType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link eu.geclipse.jsdl.model.sweep.impl.DocumentRootImpl#getSweep <em>Sweep</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot
{
  /**
   * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMixed()
   * @generated
   * @ordered
   */
  protected FeatureMap mixed;

  /**
   * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXMLNSPrefixMap()
   * @generated
   * @ordered
   */
  protected EMap xMLNSPrefixMap;

  /**
   * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXSISchemaLocation()
   * @generated
   * @ordered
   */
  protected EMap xSISchemaLocation;

  /**
   * The default value of the '{@link #getParameter() <em>Parameter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameter()
   * @generated
   * @ordered
   */
  protected static final String PARAMETER_EDEFAULT = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DocumentRootImpl()
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
    return SweepPackage.Literals.DOCUMENT_ROOT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getMixed()
  {
    if (mixed == null)
    {
      mixed = new BasicFeatureMap(this, SweepPackage.DOCUMENT_ROOT__MIXED);
    }
    return mixed;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EMap getXMLNSPrefixMap()
  {
    if (xMLNSPrefixMap == null)
    {
      xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, SweepPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    }
    return xMLNSPrefixMap;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EMap getXSISchemaLocation()
  {
    if (xSISchemaLocation == null)
    {
      xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, SweepPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    }
    return xSISchemaLocation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AssignmentType getAssignment()
  {
    return (AssignmentType)getMixed().get(SweepPackage.Literals.DOCUMENT_ROOT__ASSIGNMENT, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAssignment(AssignmentType newAssignment, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(SweepPackage.Literals.DOCUMENT_ROOT__ASSIGNMENT, newAssignment, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAssignment(AssignmentType newAssignment)
  {
    ((FeatureMap.Internal)getMixed()).set(SweepPackage.Literals.DOCUMENT_ROOT__ASSIGNMENT, newAssignment);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EObject getFunction()
  {
    return (EObject)getMixed().get(SweepPackage.Literals.DOCUMENT_ROOT__FUNCTION, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFunction(EObject newFunction, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(SweepPackage.Literals.DOCUMENT_ROOT__FUNCTION, newFunction, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getParameter()
  {
    return (String)getMixed().get(SweepPackage.Literals.DOCUMENT_ROOT__PARAMETER, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setParameter(String newParameter)
  {
    ((FeatureMap.Internal)getMixed()).set(SweepPackage.Literals.DOCUMENT_ROOT__PARAMETER, newParameter);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SweepType getSweep()
  {
    return (SweepType)getMixed().get(SweepPackage.Literals.DOCUMENT_ROOT__SWEEP, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSweep(SweepType newSweep, NotificationChain msgs)
  {
    return ((FeatureMap.Internal)getMixed()).basicAdd(SweepPackage.Literals.DOCUMENT_ROOT__SWEEP, newSweep, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSweep(SweepType newSweep)
  {
    ((FeatureMap.Internal)getMixed()).set(SweepPackage.Literals.DOCUMENT_ROOT__SWEEP, newSweep);
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
      case SweepPackage.DOCUMENT_ROOT__MIXED:
        return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
      case SweepPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
      case SweepPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
      case SweepPackage.DOCUMENT_ROOT__ASSIGNMENT:
        return basicSetAssignment(null, msgs);
      case SweepPackage.DOCUMENT_ROOT__FUNCTION:
        return basicSetFunction(null, msgs);
      case SweepPackage.DOCUMENT_ROOT__SWEEP:
        return basicSetSweep(null, msgs);
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
      case SweepPackage.DOCUMENT_ROOT__MIXED:
        if (coreType) return getMixed();
        return ((FeatureMap.Internal)getMixed()).getWrapper();
      case SweepPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        if (coreType) return getXMLNSPrefixMap();
        else return getXMLNSPrefixMap().map();
      case SweepPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        if (coreType) return getXSISchemaLocation();
        else return getXSISchemaLocation().map();
      case SweepPackage.DOCUMENT_ROOT__ASSIGNMENT:
        return getAssignment();
      case SweepPackage.DOCUMENT_ROOT__FUNCTION:
        return getFunction();
      case SweepPackage.DOCUMENT_ROOT__PARAMETER:
        return getParameter();
      case SweepPackage.DOCUMENT_ROOT__SWEEP:
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
      case SweepPackage.DOCUMENT_ROOT__MIXED:
        ((FeatureMap.Internal)getMixed()).set(newValue);
        return;
      case SweepPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
        return;
      case SweepPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
        return;
      case SweepPackage.DOCUMENT_ROOT__ASSIGNMENT:
        setAssignment((AssignmentType)newValue);
        return;
      case SweepPackage.DOCUMENT_ROOT__PARAMETER:
        setParameter((String)newValue);
        return;
      case SweepPackage.DOCUMENT_ROOT__SWEEP:
        setSweep((SweepType)newValue);
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
      case SweepPackage.DOCUMENT_ROOT__MIXED:
        getMixed().clear();
        return;
      case SweepPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        getXMLNSPrefixMap().clear();
        return;
      case SweepPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        getXSISchemaLocation().clear();
        return;
      case SweepPackage.DOCUMENT_ROOT__ASSIGNMENT:
        setAssignment((AssignmentType)null);
        return;
      case SweepPackage.DOCUMENT_ROOT__PARAMETER:
        setParameter(PARAMETER_EDEFAULT);
        return;
      case SweepPackage.DOCUMENT_ROOT__SWEEP:
        setSweep((SweepType)null);
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
      case SweepPackage.DOCUMENT_ROOT__MIXED:
        return mixed != null && !mixed.isEmpty();
      case SweepPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
        return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
      case SweepPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
        return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
      case SweepPackage.DOCUMENT_ROOT__ASSIGNMENT:
        return getAssignment() != null;
      case SweepPackage.DOCUMENT_ROOT__FUNCTION:
        return getFunction() != null;
      case SweepPackage.DOCUMENT_ROOT__PARAMETER:
        return PARAMETER_EDEFAULT == null ? getParameter() != null : !PARAMETER_EDEFAULT.equals(getParameter());
      case SweepPackage.DOCUMENT_ROOT__SWEEP:
        return getSweep() != null;
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
    result.append(" (mixed: ");
    result.append(mixed);
    result.append(')');
    return result.toString();
  }

} //DocumentRootImpl
