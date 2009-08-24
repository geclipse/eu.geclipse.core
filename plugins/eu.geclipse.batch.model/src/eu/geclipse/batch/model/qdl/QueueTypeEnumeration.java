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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.batch.model.qdl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Queue Type Enumeration</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueTypeEnumeration()
 * @model extendedMetaData="name='QueueTypeEnumeration'"
 * @generated
 */
public enum QueueTypeEnumeration implements Enumerator
{
  /**
   * The '<em><b>Execution</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EXECUTION_VALUE
   * @generated
   * @ordered
   */
  EXECUTION(0, "Execution", "Execution"), //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * The '<em><b>Route</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ROUTE_VALUE
   * @generated
   * @ordered
   */
  ROUTE(1, "Route", "Route"); //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * The '<em><b>Execution</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Execution</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EXECUTION
   * @model name="Execution"
   * @generated
   * @ordered
   */
  public static final int EXECUTION_VALUE = 0;

  /**
   * The '<em><b>Route</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Route</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ROUTE
   * @model name="Route"
   * @generated
   * @ordered
   */
  public static final int ROUTE_VALUE = 1;

  /**
   * An array of all the '<em><b>Queue Type Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final QueueTypeEnumeration[] VALUES_ARRAY =
    new QueueTypeEnumeration[]
    {
      EXECUTION,
      ROUTE,
    };

  /**
   * A public read-only list of all the '<em><b>Queue Type Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<QueueTypeEnumeration> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Queue Type Enumeration</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static QueueTypeEnumeration get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      QueueTypeEnumeration result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Queue Type Enumeration</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static QueueTypeEnumeration getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      QueueTypeEnumeration result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Queue Type Enumeration</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static QueueTypeEnumeration get(int value)
  {
    switch (value)
    {
      case EXECUTION_VALUE: return EXECUTION;
      case ROUTE_VALUE: return ROUTE;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private QueueTypeEnumeration(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return this.value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral()
  {
    return this.literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return this.literal;
  }
  
} //QueueTypeEnumeration
