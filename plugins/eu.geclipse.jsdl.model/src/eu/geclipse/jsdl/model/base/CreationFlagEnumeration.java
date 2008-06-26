/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Creation Flag Enumeration</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.base.JsdlPackage#getCreationFlagEnumeration()
 * @model extendedMetaData="name='CreationFlagEnumeration'"
 * @generated
 */
public final class CreationFlagEnumeration extends AbstractEnumerator
{
  /**
   * The '<em><b>Overwrite</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Overwrite</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #OVERWRITE_LITERAL
   * @model name="overwrite"
   * @generated
   * @ordered
   */
  public static final int OVERWRITE = 0;

  /**
   * The '<em><b>Append</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Append</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #APPEND_LITERAL
   * @model name="append"
   * @generated
   * @ordered
   */
  public static final int APPEND = 1;

  /**
   * The '<em><b>Dont Overwrite</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Dont Overwrite</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DONT_OVERWRITE_LITERAL
   * @model name="dontOverwrite"
   * @generated
   * @ordered
   */
  public static final int DONT_OVERWRITE = 2;

  /**
   * The '<em><b>Overwrite</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OVERWRITE
   * @generated
   * @ordered
   */
  public static final CreationFlagEnumeration OVERWRITE_LITERAL = new CreationFlagEnumeration(OVERWRITE, "overwrite", "overwrite");

  /**
   * The '<em><b>Append</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #APPEND
   * @generated
   * @ordered
   */
  public static final CreationFlagEnumeration APPEND_LITERAL = new CreationFlagEnumeration(APPEND, "append", "append");

  /**
   * The '<em><b>Dont Overwrite</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DONT_OVERWRITE
   * @generated
   * @ordered
   */
  public static final CreationFlagEnumeration DONT_OVERWRITE_LITERAL = new CreationFlagEnumeration(DONT_OVERWRITE, "dontOverwrite", "dontOverwrite");

  /**
   * An array of all the '<em><b>Creation Flag Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final CreationFlagEnumeration[] VALUES_ARRAY =
    new CreationFlagEnumeration[]
    {
      OVERWRITE_LITERAL,
      APPEND_LITERAL,
      DONT_OVERWRITE_LITERAL,
    };

  /**
   * A public read-only list of all the '<em><b>Creation Flag Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Creation Flag Enumeration</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static CreationFlagEnumeration get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      CreationFlagEnumeration result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Creation Flag Enumeration</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static CreationFlagEnumeration getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      CreationFlagEnumeration result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Creation Flag Enumeration</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static CreationFlagEnumeration get(int value)
  {
    switch (value)
    {
      case OVERWRITE: return OVERWRITE_LITERAL;
      case APPEND: return APPEND_LITERAL;
      case DONT_OVERWRITE: return DONT_OVERWRITE_LITERAL;
    }
    return null;
  }

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private CreationFlagEnumeration(int value, String name, String literal)
  {
    super(value, name, literal);
  }

} //CreationFlagEnumeration
