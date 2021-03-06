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
 * A representation of the literals of the enumeration '<em><b>Queue Status Enumeration</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getQueueStatusEnumeration()
 * @model extendedMetaData="name='QueueStatusEnumeration'"
 * @generated
 */
public enum QueueStatusEnumeration implements Enumerator
{
  /**
   * The '<em><b>Enabled</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ENABLED_VALUE
   * @generated
   * @ordered
   */
  ENABLED(0, "Enabled", "Enabled"), //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * The '<em><b>Disabled</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DISABLED_VALUE
   * @generated
   * @ordered
   */
  DISABLED(1, "Disabled", "Disabled"); //$NON-NLS-1$ //$NON-NLS-2$

  /**
   * The '<em><b>Enabled</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Enabled</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ENABLED
   * @model name="Enabled"
   * @generated
   * @ordered
   */
  public static final int ENABLED_VALUE = 0;

  /**
   * The '<em><b>Disabled</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Disabled</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DISABLED
   * @model name="Disabled"
   * @generated
   * @ordered
   */
  public static final int DISABLED_VALUE = 1;

  /**
   * An array of all the '<em><b>Queue Status Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final QueueStatusEnumeration[] VALUES_ARRAY =
    new QueueStatusEnumeration[]
    {
      ENABLED,
      DISABLED,
    };

  /**
   * A public read-only list of all the '<em><b>Queue Status Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<QueueStatusEnumeration> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Queue Status Enumeration</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal 
   * @return 
   * @generated
   */
  public static QueueStatusEnumeration get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      QueueStatusEnumeration result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Queue Status Enumeration</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name 
   * @return 
   * @generated
   */
  public static QueueStatusEnumeration getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      QueueStatusEnumeration result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Queue Status Enumeration</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value 
   * @return 
   * @generated
   */
  public static QueueStatusEnumeration get(int value)
  {
    switch (value)
    {
      case ENABLED_VALUE: return ENABLED;
      case DISABLED_VALUE: return DISABLED;
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
  private QueueStatusEnumeration(int value, String name, String literal)
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
  
} //QueueStatusEnumeration
