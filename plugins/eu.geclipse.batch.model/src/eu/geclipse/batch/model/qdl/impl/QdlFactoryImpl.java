/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.batch.model.qdl.impl;

import eu.geclipse.batch.model.qdl.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class QdlFactoryImpl extends EFactoryImpl implements QdlFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static QdlFactory init()
  {
    try
    {
      QdlFactory theQdlFactory = (QdlFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/geclipse/qdl"); 
      if (theQdlFactory != null)
      {
        return theQdlFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new QdlFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QdlFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case QdlPackage.ALLOWED_VIRTUAL_ORGANIZATIONS_TYPE: return createAllowedVirtualOrganizationsType();
      case QdlPackage.BOUNDARY_TYPE: return createBoundaryType();
      case QdlPackage.DOCUMENT_ROOT: return createDocumentRoot();
      case QdlPackage.EXACT_TYPE: return createExactType();
      case QdlPackage.QUEUE_TYPE: return createQueueType();
      case QdlPackage.RANGE_TYPE: return createRangeType();
      case QdlPackage.RANGE_VALUE_TYPE: return createRangeValueType();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case QdlPackage.QUEUE_STATUS_ENUMERATION:
        return createQueueStatusEnumerationFromString(eDataType, initialValue);
      case QdlPackage.QUEUE_TYPE_ENUMERATION:
        return createQueueTypeEnumerationFromString(eDataType, initialValue);
      case QdlPackage.DESCRIPTION_TYPE:
        return createDescriptionTypeFromString(eDataType, initialValue);
      case QdlPackage.QUEUE_STATUS_ENUMERATION_OBJECT:
        return createQueueStatusEnumerationObjectFromString(eDataType, initialValue);
      case QdlPackage.QUEUE_TYPE_ENUMERATION_OBJECT:
        return createQueueTypeEnumerationObjectFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case QdlPackage.QUEUE_STATUS_ENUMERATION:
        return convertQueueStatusEnumerationToString(eDataType, instanceValue);
      case QdlPackage.QUEUE_TYPE_ENUMERATION:
        return convertQueueTypeEnumerationToString(eDataType, instanceValue);
      case QdlPackage.DESCRIPTION_TYPE:
        return convertDescriptionTypeToString(eDataType, instanceValue);
      case QdlPackage.QUEUE_STATUS_ENUMERATION_OBJECT:
        return convertQueueStatusEnumerationObjectToString(eDataType, instanceValue);
      case QdlPackage.QUEUE_TYPE_ENUMERATION_OBJECT:
        return convertQueueTypeEnumerationObjectToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AllowedVirtualOrganizationsType createAllowedVirtualOrganizationsType()
  {
    AllowedVirtualOrganizationsTypeImpl allowedVirtualOrganizationsType = new AllowedVirtualOrganizationsTypeImpl();
    return allowedVirtualOrganizationsType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BoundaryType createBoundaryType()
  {
    BoundaryTypeImpl boundaryType = new BoundaryTypeImpl();
    return boundaryType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DocumentRoot createDocumentRoot()
  {
    DocumentRootImpl documentRoot = new DocumentRootImpl();
    return documentRoot;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExactType createExactType()
  {
    ExactTypeImpl exactType = new ExactTypeImpl();
    return exactType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueType createQueueType()
  {
    QueueTypeImpl queueType = new QueueTypeImpl();
    return queueType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeType createRangeType()
  {
    RangeTypeImpl rangeType = new RangeTypeImpl();
    return rangeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RangeValueType createRangeValueType()
  {
    RangeValueTypeImpl rangeValueType = new RangeValueTypeImpl();
    return rangeValueType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueStatusEnumeration createQueueStatusEnumerationFromString(EDataType eDataType, String initialValue)
  {
    QueueStatusEnumeration result = QueueStatusEnumeration.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertQueueStatusEnumerationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueTypeEnumeration createQueueTypeEnumerationFromString(EDataType eDataType, String initialValue)
  {
    QueueTypeEnumeration result = QueueTypeEnumeration.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertQueueTypeEnumerationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String createDescriptionTypeFromString(EDataType eDataType, String initialValue)
  {
    return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDescriptionTypeToString(EDataType eDataType, Object instanceValue)
  {
    return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueStatusEnumeration createQueueStatusEnumerationObjectFromString(EDataType eDataType, String initialValue)
  {
    return createQueueStatusEnumerationFromString(QdlPackage.Literals.QUEUE_STATUS_ENUMERATION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertQueueStatusEnumerationObjectToString(EDataType eDataType, Object instanceValue)
  {
    return convertQueueStatusEnumerationToString(QdlPackage.Literals.QUEUE_STATUS_ENUMERATION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QueueTypeEnumeration createQueueTypeEnumerationObjectFromString(EDataType eDataType, String initialValue)
  {
    return createQueueTypeEnumerationFromString(QdlPackage.Literals.QUEUE_TYPE_ENUMERATION, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertQueueTypeEnumerationObjectToString(EDataType eDataType, Object instanceValue)
  {
    return convertQueueTypeEnumerationToString(QdlPackage.Literals.QUEUE_TYPE_ENUMERATION, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QdlPackage getQdlPackage()
  {
    return (QdlPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static QdlPackage getPackage()
  {
    return QdlPackage.eINSTANCE;
  }

} //QdlFactoryImpl
