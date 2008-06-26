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
 * A representation of the literals of the enumeration '<em><b>File System Type Enumeration</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.base.JsdlPackage#getFileSystemTypeEnumeration()
 * @model extendedMetaData="name='FileSystemTypeEnumeration'"
 * @generated
 */
public final class FileSystemTypeEnumeration extends AbstractEnumerator
{
  /**
   * The '<em><b>Swap</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Swap</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SWAP_LITERAL
   * @model name="swap"
   * @generated
   * @ordered
   */
  public static final int SWAP = 0;

  /**
   * The '<em><b>Temporary</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Temporary</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TEMPORARY_LITERAL
   * @model name="temporary"
   * @generated
   * @ordered
   */
  public static final int TEMPORARY = 1;

  /**
   * The '<em><b>Spool</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Spool</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SPOOL_LITERAL
   * @model name="spool"
   * @generated
   * @ordered
   */
  public static final int SPOOL = 2;

  /**
   * The '<em><b>Normal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Normal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NORMAL_LITERAL
   * @model name="normal"
   * @generated
   * @ordered
   */
  public static final int NORMAL = 3;

  /**
   * The '<em><b>Swap</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SWAP
   * @generated
   * @ordered
   */
  public static final FileSystemTypeEnumeration SWAP_LITERAL = new FileSystemTypeEnumeration(SWAP, "swap", "swap");

  /**
   * The '<em><b>Temporary</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TEMPORARY
   * @generated
   * @ordered
   */
  public static final FileSystemTypeEnumeration TEMPORARY_LITERAL = new FileSystemTypeEnumeration(TEMPORARY, "temporary", "temporary");

  /**
   * The '<em><b>Spool</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SPOOL
   * @generated
   * @ordered
   */
  public static final FileSystemTypeEnumeration SPOOL_LITERAL = new FileSystemTypeEnumeration(SPOOL, "spool", "spool");

  /**
   * The '<em><b>Normal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NORMAL
   * @generated
   * @ordered
   */
  public static final FileSystemTypeEnumeration NORMAL_LITERAL = new FileSystemTypeEnumeration(NORMAL, "normal", "normal");

  /**
   * An array of all the '<em><b>File System Type Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final FileSystemTypeEnumeration[] VALUES_ARRAY =
    new FileSystemTypeEnumeration[]
    {
      SWAP_LITERAL,
      TEMPORARY_LITERAL,
      SPOOL_LITERAL,
      NORMAL_LITERAL,
    };

  /**
   * A public read-only list of all the '<em><b>File System Type Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>File System Type Enumeration</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FileSystemTypeEnumeration get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      FileSystemTypeEnumeration result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>File System Type Enumeration</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FileSystemTypeEnumeration getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      FileSystemTypeEnumeration result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>File System Type Enumeration</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FileSystemTypeEnumeration get(int value)
  {
    switch (value)
    {
      case SWAP: return SWAP_LITERAL;
      case TEMPORARY: return TEMPORARY_LITERAL;
      case SPOOL: return SPOOL_LITERAL;
      case NORMAL: return NORMAL_LITERAL;
    }
    return null;
  }

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private FileSystemTypeEnumeration(int value, String name, String literal)
  {
    super(value, name, literal);
  }

} //FileSystemTypeEnumeration
