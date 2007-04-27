/**
 * <copyright>
 * </copyright>
 *
 * $Id: ProcessorArchitectureEnumeration.java,v 1.2 2007/03/01 09:15:16 emstamou Exp $
 */
package eu.geclipse.jsdl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Processor Architecture Enumeration</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.JsdlPackage#getProcessorArchitectureEnumeration()
 * @model
 * @generated
 */
public final class ProcessorArchitectureEnumeration extends AbstractEnumerator {
  /**
   * The '<em><b>Sparc</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sparc</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #SPARC_LITERAL
   * @model name="sparc"
   * @generated
   * @ordered
   */
	public static final int SPARC = 0;

  /**
   * The '<em><b>Powerpc</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Powerpc</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #POWERPC_LITERAL
   * @model name="powerpc"
   * @generated
   * @ordered
   */
	public static final int POWERPC = 1;

  /**
   * The '<em><b>X86</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>X86</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #X86_LITERAL
   * @model name="x86"
   * @generated
   * @ordered
   */
	public static final int X86 = 2;

  /**
   * The '<em><b>X8632</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>X8632</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #X8632_LITERAL
   * @model name="x8632" literal="x86_32"
   * @generated
   * @ordered
   */
	public static final int X8632 = 3;

  /**
   * The '<em><b>X8664</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>X8664</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #X8664_LITERAL
   * @model name="x8664" literal="x86_64"
   * @generated
   * @ordered
   */
	public static final int X8664 = 4;

  /**
   * The '<em><b>Parisc</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Parisc</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #PARISC_LITERAL
   * @model name="parisc"
   * @generated
   * @ordered
   */
	public static final int PARISC = 5;

  /**
   * The '<em><b>Mips</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Mips</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #MIPS_LITERAL
   * @model name="mips"
   * @generated
   * @ordered
   */
	public static final int MIPS = 6;

  /**
   * The '<em><b>Ia64</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ia64</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #IA64_LITERAL
   * @model name="ia64"
   * @generated
   * @ordered
   */
	public static final int IA64 = 7;

  /**
   * The '<em><b>Arm</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Arm</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #ARM_LITERAL
   * @model name="arm"
   * @generated
   * @ordered
   */
	public static final int ARM = 8;

  /**
   * The '<em><b>Other</b></em>' literal value.
   * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Other</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
   * @see #OTHER_LITERAL
   * @model name="other"
   * @generated
   * @ordered
   */
	public static final int OTHER = 9;

  /**
   * The '<em><b>Sparc</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #SPARC
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration SPARC_LITERAL = new ProcessorArchitectureEnumeration(SPARC, "sparc", "sparc");

  /**
   * The '<em><b>Powerpc</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #POWERPC
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration POWERPC_LITERAL = new ProcessorArchitectureEnumeration(POWERPC, "powerpc", "powerpc");

  /**
   * The '<em><b>X86</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #X86
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration X86_LITERAL = new ProcessorArchitectureEnumeration(X86, "x86", "x86");

  /**
   * The '<em><b>X8632</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #X8632
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration X8632_LITERAL = new ProcessorArchitectureEnumeration(X8632, "x8632", "x86_32");

  /**
   * The '<em><b>X8664</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #X8664
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration X8664_LITERAL = new ProcessorArchitectureEnumeration(X8664, "x8664", "x86_64");

  /**
   * The '<em><b>Parisc</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #PARISC
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration PARISC_LITERAL = new ProcessorArchitectureEnumeration(PARISC, "parisc", "parisc");

  /**
   * The '<em><b>Mips</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #MIPS
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration MIPS_LITERAL = new ProcessorArchitectureEnumeration(MIPS, "mips", "mips");

  /**
   * The '<em><b>Ia64</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #IA64
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration IA64_LITERAL = new ProcessorArchitectureEnumeration(IA64, "ia64", "ia64");

  /**
   * The '<em><b>Arm</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #ARM
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration ARM_LITERAL = new ProcessorArchitectureEnumeration(ARM, "arm", "arm");

  /**
   * The '<em><b>Other</b></em>' literal object.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #OTHER
   * @generated
   * @ordered
   */
	public static final ProcessorArchitectureEnumeration OTHER_LITERAL = new ProcessorArchitectureEnumeration(OTHER, "other", "other");

  /**
   * An array of all the '<em><b>Processor Architecture Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private static final ProcessorArchitectureEnumeration[] VALUES_ARRAY =
    new ProcessorArchitectureEnumeration[]
    {
      SPARC_LITERAL,
      POWERPC_LITERAL,
      X86_LITERAL,
      X8632_LITERAL,
      X8664_LITERAL,
      PARISC_LITERAL,
      MIPS_LITERAL,
      IA64_LITERAL,
      ARM_LITERAL,
      OTHER_LITERAL,
    };

  /**
   * A public read-only list of all the '<em><b>Processor Architecture Enumeration</b></em>' enumerators.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Processor Architecture Enumeration</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static ProcessorArchitectureEnumeration get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ProcessorArchitectureEnumeration result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Processor Architecture Enumeration</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static ProcessorArchitectureEnumeration getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      ProcessorArchitectureEnumeration result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Processor Architecture Enumeration</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public static ProcessorArchitectureEnumeration get(int value)
  {
    switch (value)
    {
      case SPARC: return SPARC_LITERAL;
      case POWERPC: return POWERPC_LITERAL;
      case X86: return X86_LITERAL;
      case X8632: return X8632_LITERAL;
      case X8664: return X8664_LITERAL;
      case PARISC: return PARISC_LITERAL;
      case MIPS: return MIPS_LITERAL;
      case IA64: return IA64_LITERAL;
      case ARM: return ARM_LITERAL;
      case OTHER: return OTHER_LITERAL;
    }
    return null;	
  }

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private ProcessorArchitectureEnumeration(int value, String name, String literal)
  {
    super(value, name, literal);
  }

} //ProcessorArchitectureEnumeration
