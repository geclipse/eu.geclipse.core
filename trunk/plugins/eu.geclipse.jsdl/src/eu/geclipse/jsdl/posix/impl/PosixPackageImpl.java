/**
 * <copyright>
 * </copyright>
 *
 * $Id: PosixPackageImpl.java,v 1.2 2007/03/01 09:15:17 emstamou Exp $
 */
package eu.geclipse.jsdl.posix.impl;

import eu.geclipse.jsdl.JsdlPackage;

import eu.geclipse.jsdl.impl.JsdlPackageImpl;

import eu.geclipse.jsdl.posix.ArgumentType;
import eu.geclipse.jsdl.posix.DirectoryNameType;
import eu.geclipse.jsdl.posix.DocumentRoot;
import eu.geclipse.jsdl.posix.EnvironmentType;
import eu.geclipse.jsdl.posix.FileNameType;
import eu.geclipse.jsdl.posix.GroupNameType;
import eu.geclipse.jsdl.posix.LimitsType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;
import eu.geclipse.jsdl.posix.PosixFactory;
import eu.geclipse.jsdl.posix.PosixPackage;
import eu.geclipse.jsdl.posix.UserNameType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PosixPackageImpl extends EPackageImpl implements PosixPackage 
{
  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass argumentTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass directoryNameTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass documentRootEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass environmentTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass fileNameTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass groupNameTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass limitsTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass posixApplicationTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private EClass userNameTypeEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see eu.geclipse.jsdl.posix.PosixPackage#eNS_URI
   * @see #init()
   * @generated
   */
	private PosixPackageImpl()
  {
    super(eNS_URI, PosixFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this
   * model, and for any others upon which it depends.  Simple
   * dependencies are satisfied by calling this method on all
   * dependent packages before doing anything else.  This method drives
   * initialization for interdependent packages directly, in parallel
   * with this package, itself.
   * <p>Of this package and its interdependencies, all packages which
   * have not yet been registered by their URI values are first created
   * and registered.  The packages are then initialized in two steps:
   * meta-model objects for all of the packages are created before any
   * are initialized, since one package's meta-model objects may refer to
   * those of another.
   * <p>Invocation of this method will not affect any packages that have
   * already been initialized.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
	public static PosixPackage init()
  {
    if (isInited) return (PosixPackage)EPackage.Registry.INSTANCE.getEPackage(PosixPackage.eNS_URI);

    // Obtain or create and register package
    PosixPackageImpl thePosixPackage = (PosixPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof PosixPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new PosixPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackage.eINSTANCE.eClass();

    // Obtain or create and register interdependencies
    JsdlPackageImpl theJsdlPackage = (JsdlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI) instanceof JsdlPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(JsdlPackage.eNS_URI) : JsdlPackage.eINSTANCE);

    // Create package meta-data objects
    thePosixPackage.createPackageContents();
    theJsdlPackage.createPackageContents();

    // Initialize created meta-data
    thePosixPackage.initializePackageContents();
    theJsdlPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    thePosixPackage.freeze();

    return thePosixPackage;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getArgumentType()
  {
    return argumentTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getArgumentType_Value()
  {
    return (EAttribute)argumentTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getArgumentType_FilesystemName()
  {
    return (EAttribute)argumentTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getArgumentType_AnyAttribute()
  {
    return (EAttribute)argumentTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getDirectoryNameType()
  {
    return directoryNameTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDirectoryNameType_Value()
  {
    return (EAttribute)directoryNameTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDirectoryNameType_FilesystemName()
  {
    return (EAttribute)directoryNameTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDirectoryNameType_AnyAttribute()
  {
    return (EAttribute)directoryNameTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getDocumentRoot()
  {
    return documentRootEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getDocumentRoot_Mixed()
  {
    return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_XMLNSPrefixMap()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_XSISchemaLocation()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Argument()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_CoreDumpLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_CPUTimeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_DataSegmentLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Environment()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Error()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Executable()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_FileSizeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_GroupName()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Input()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_LockedMemoryLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_MemoryLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_OpenDescriptorsLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_Output()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_PipeSizeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_POSIXApplication()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_ProcessCountLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_StackSizeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_ThreadCountLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_UserName()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_VirtualMemoryLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_WallTimeLimit()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getDocumentRoot_WorkingDirectory()
  {
    return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getEnvironmentType()
  {
    return environmentTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getEnvironmentType_Value()
  {
    return (EAttribute)environmentTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getEnvironmentType_FilesystemName()
  {
    return (EAttribute)environmentTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getEnvironmentType_Name()
  {
    return (EAttribute)environmentTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getEnvironmentType_AnyAttribute()
  {
    return (EAttribute)environmentTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getFileNameType()
  {
    return fileNameTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileNameType_Value()
  {
    return (EAttribute)fileNameTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileNameType_FilesystemName()
  {
    return (EAttribute)fileNameTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getFileNameType_AnyAttribute()
  {
    return (EAttribute)fileNameTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getGroupNameType()
  {
    return groupNameTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getGroupNameType_Value()
  {
    return (EAttribute)groupNameTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getGroupNameType_AnyAttribute()
  {
    return (EAttribute)groupNameTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getLimitsType()
  {
    return limitsTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getLimitsType_Value()
  {
    return (EAttribute)limitsTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getLimitsType_AnyAttribute()
  {
    return (EAttribute)limitsTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getPOSIXApplicationType()
  {
    return posixApplicationTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_Executable()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_Argument()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_Input()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_Output()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_Error()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_WorkingDirectory()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_Environment()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_WallTimeLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_FileSizeLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_CoreDumpLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_DataSegmentLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_LockedMemoryLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_MemoryLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_OpenDescriptorsLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_PipeSizeLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_StackSizeLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_CPUTimeLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_ProcessCountLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_VirtualMemoryLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_ThreadCountLimit()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_UserName()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EReference getPOSIXApplicationType_GroupName()
  {
    return (EReference)posixApplicationTypeEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getPOSIXApplicationType_Name()
  {
    return (EAttribute)posixApplicationTypeEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getPOSIXApplicationType_AnyAttribute()
  {
    return (EAttribute)posixApplicationTypeEClass.getEStructuralFeatures().get(23);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EClass getUserNameType()
  {
    return userNameTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getUserNameType_Value()
  {
    return (EAttribute)userNameTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public EAttribute getUserNameType_AnyAttribute()
  {
    return (EAttribute)userNameTypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public PosixFactory getPosixFactory()
  {
    return (PosixFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    argumentTypeEClass = createEClass(ARGUMENT_TYPE);
    createEAttribute(argumentTypeEClass, ARGUMENT_TYPE__VALUE);
    createEAttribute(argumentTypeEClass, ARGUMENT_TYPE__FILESYSTEM_NAME);
    createEAttribute(argumentTypeEClass, ARGUMENT_TYPE__ANY_ATTRIBUTE);

    directoryNameTypeEClass = createEClass(DIRECTORY_NAME_TYPE);
    createEAttribute(directoryNameTypeEClass, DIRECTORY_NAME_TYPE__VALUE);
    createEAttribute(directoryNameTypeEClass, DIRECTORY_NAME_TYPE__FILESYSTEM_NAME);
    createEAttribute(directoryNameTypeEClass, DIRECTORY_NAME_TYPE__ANY_ATTRIBUTE);

    documentRootEClass = createEClass(DOCUMENT_ROOT);
    createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
    createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ARGUMENT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CORE_DUMP_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__CPU_TIME_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__DATA_SEGMENT_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ENVIRONMENT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__ERROR);
    createEReference(documentRootEClass, DOCUMENT_ROOT__EXECUTABLE);
    createEReference(documentRootEClass, DOCUMENT_ROOT__FILE_SIZE_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__GROUP_NAME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__INPUT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__LOCKED_MEMORY_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__MEMORY_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__OPEN_DESCRIPTORS_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__OUTPUT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__PIPE_SIZE_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__POSIX_APPLICATION);
    createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESS_COUNT_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__STACK_SIZE_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__THREAD_COUNT_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__USER_NAME);
    createEReference(documentRootEClass, DOCUMENT_ROOT__VIRTUAL_MEMORY_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__WALL_TIME_LIMIT);
    createEReference(documentRootEClass, DOCUMENT_ROOT__WORKING_DIRECTORY);

    environmentTypeEClass = createEClass(ENVIRONMENT_TYPE);
    createEAttribute(environmentTypeEClass, ENVIRONMENT_TYPE__VALUE);
    createEAttribute(environmentTypeEClass, ENVIRONMENT_TYPE__FILESYSTEM_NAME);
    createEAttribute(environmentTypeEClass, ENVIRONMENT_TYPE__NAME);
    createEAttribute(environmentTypeEClass, ENVIRONMENT_TYPE__ANY_ATTRIBUTE);

    fileNameTypeEClass = createEClass(FILE_NAME_TYPE);
    createEAttribute(fileNameTypeEClass, FILE_NAME_TYPE__VALUE);
    createEAttribute(fileNameTypeEClass, FILE_NAME_TYPE__FILESYSTEM_NAME);
    createEAttribute(fileNameTypeEClass, FILE_NAME_TYPE__ANY_ATTRIBUTE);

    groupNameTypeEClass = createEClass(GROUP_NAME_TYPE);
    createEAttribute(groupNameTypeEClass, GROUP_NAME_TYPE__VALUE);
    createEAttribute(groupNameTypeEClass, GROUP_NAME_TYPE__ANY_ATTRIBUTE);

    limitsTypeEClass = createEClass(LIMITS_TYPE);
    createEAttribute(limitsTypeEClass, LIMITS_TYPE__VALUE);
    createEAttribute(limitsTypeEClass, LIMITS_TYPE__ANY_ATTRIBUTE);

    posixApplicationTypeEClass = createEClass(POSIX_APPLICATION_TYPE);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__EXECUTABLE);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__ARGUMENT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__INPUT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__OUTPUT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__ERROR);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__WORKING_DIRECTORY);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__ENVIRONMENT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__MEMORY_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__USER_NAME);
    createEReference(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__GROUP_NAME);
    createEAttribute(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__NAME);
    createEAttribute(posixApplicationTypeEClass, POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE);

    userNameTypeEClass = createEClass(USER_NAME_TYPE);
    createEAttribute(userNameTypeEClass, USER_NAME_TYPE__VALUE);
    createEAttribute(userNameTypeEClass, USER_NAME_TYPE__ANY_ATTRIBUTE);
  }

  /**
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

    // Add supertypes to classes

    // Initialize classes and features; add operations and parameters
    initEClass(argumentTypeEClass, ArgumentType.class, "ArgumentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getArgumentType_Value(), theXMLTypePackage.getNormalizedString(), "value", null, 0, 1, ArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getArgumentType_FilesystemName(), theXMLTypePackage.getNCName(), "filesystemName", null, 0, 1, ArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getArgumentType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ArgumentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(directoryNameTypeEClass, DirectoryNameType.class, "DirectoryNameType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDirectoryNameType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, DirectoryNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDirectoryNameType_FilesystemName(), theXMLTypePackage.getNCName(), "filesystemName", null, 0, 1, DirectoryNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDirectoryNameType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, DirectoryNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Argument(), this.getArgumentType(), null, "argument", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_CoreDumpLimit(), this.getLimitsType(), null, "coreDumpLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_CPUTimeLimit(), this.getLimitsType(), null, "cPUTimeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_DataSegmentLimit(), this.getLimitsType(), null, "dataSegmentLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Environment(), this.getEnvironmentType(), null, "environment", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Error(), this.getFileNameType(), null, "error", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Executable(), this.getFileNameType(), null, "executable", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_FileSizeLimit(), this.getLimitsType(), null, "fileSizeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_GroupName(), this.getGroupNameType(), null, "groupName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Input(), this.getFileNameType(), null, "input", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_LockedMemoryLimit(), this.getLimitsType(), null, "lockedMemoryLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_MemoryLimit(), this.getLimitsType(), null, "memoryLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_OpenDescriptorsLimit(), this.getLimitsType(), null, "openDescriptorsLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_Output(), this.getFileNameType(), null, "output", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_PipeSizeLimit(), this.getLimitsType(), null, "pipeSizeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_POSIXApplication(), this.getPOSIXApplicationType(), null, "pOSIXApplication", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_ProcessCountLimit(), this.getLimitsType(), null, "processCountLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_StackSizeLimit(), this.getLimitsType(), null, "stackSizeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_ThreadCountLimit(), this.getLimitsType(), null, "threadCountLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_UserName(), this.getUserNameType(), null, "userName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_VirtualMemoryLimit(), this.getLimitsType(), null, "virtualMemoryLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_WallTimeLimit(), this.getLimitsType(), null, "wallTimeLimit", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
    initEReference(getDocumentRoot_WorkingDirectory(), this.getDirectoryNameType(), null, "workingDirectory", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

    initEClass(environmentTypeEClass, EnvironmentType.class, "EnvironmentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEnvironmentType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, EnvironmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnvironmentType_FilesystemName(), theXMLTypePackage.getNCName(), "filesystemName", null, 0, 1, EnvironmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnvironmentType_Name(), theXMLTypePackage.getNCName(), "name", null, 1, 1, EnvironmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnvironmentType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, EnvironmentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fileNameTypeEClass, FileNameType.class, "FileNameType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFileNameType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, FileNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileNameType_FilesystemName(), theXMLTypePackage.getNCName(), "filesystemName", null, 0, 1, FileNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFileNameType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, FileNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(groupNameTypeEClass, GroupNameType.class, "GroupNameType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGroupNameType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, GroupNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGroupNameType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, GroupNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(limitsTypeEClass, LimitsType.class, "LimitsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLimitsType_Value(), theXMLTypePackage.getNonNegativeInteger(), "value", null, 0, 1, LimitsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLimitsType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, LimitsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(posixApplicationTypeEClass, POSIXApplicationType.class, "POSIXApplicationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getPOSIXApplicationType_Executable(), this.getFileNameType(), null, "executable", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_Argument(), this.getArgumentType(), null, "argument", null, 0, -1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_Input(), this.getFileNameType(), null, "input", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_Output(), this.getFileNameType(), null, "output", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_Error(), this.getFileNameType(), null, "error", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_WorkingDirectory(), this.getDirectoryNameType(), null, "workingDirectory", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_Environment(), this.getEnvironmentType(), null, "environment", null, 0, -1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_WallTimeLimit(), this.getLimitsType(), null, "wallTimeLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_FileSizeLimit(), this.getLimitsType(), null, "fileSizeLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_CoreDumpLimit(), this.getLimitsType(), null, "coreDumpLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_DataSegmentLimit(), this.getLimitsType(), null, "dataSegmentLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_LockedMemoryLimit(), this.getLimitsType(), null, "lockedMemoryLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_MemoryLimit(), this.getLimitsType(), null, "memoryLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_OpenDescriptorsLimit(), this.getLimitsType(), null, "openDescriptorsLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_PipeSizeLimit(), this.getLimitsType(), null, "pipeSizeLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_StackSizeLimit(), this.getLimitsType(), null, "stackSizeLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_CPUTimeLimit(), this.getLimitsType(), null, "cPUTimeLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_ProcessCountLimit(), this.getLimitsType(), null, "processCountLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_VirtualMemoryLimit(), this.getLimitsType(), null, "virtualMemoryLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_ThreadCountLimit(), this.getLimitsType(), null, "threadCountLimit", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_UserName(), this.getUserNameType(), null, "userName", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getPOSIXApplicationType_GroupName(), this.getGroupNameType(), null, "groupName", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPOSIXApplicationType_Name(), theXMLTypePackage.getNCName(), "name", null, 0, 1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPOSIXApplicationType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, POSIXApplicationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(userNameTypeEClass, UserNameType.class, "UserNameType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getUserNameType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, UserNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getUserNameType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, UserNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected void createExtendedMetaDataAnnotations()
  {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
    addAnnotation
      (argumentTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Argument_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getArgumentType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getArgumentType_FilesystemName(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "filesystemName"
       });		
    addAnnotation
      (getArgumentType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });		
    addAnnotation
      (directoryNameTypeEClass, 
       source, 
       new String[] 
       {
       "name", "DirectoryName_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getDirectoryNameType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getDirectoryNameType_FilesystemName(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "filesystemName"
       });		
    addAnnotation
      (getDirectoryNameType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });		
    addAnnotation
      (documentRootEClass, 
       source, 
       new String[] 
       {
       "name", "",
       "kind", "mixed"
       });		
    addAnnotation
      (getDocumentRoot_Mixed(), 
       source, 
       new String[] 
       {
       "kind", "elementWildcard",
       "name", ":mixed"
       });		
    addAnnotation
      (getDocumentRoot_XMLNSPrefixMap(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "xmlns:prefix"
       });		
    addAnnotation
      (getDocumentRoot_XSISchemaLocation(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "xsi:schemaLocation"
       });		
    addAnnotation
      (getDocumentRoot_Argument(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Argument",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_CoreDumpLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CoreDumpLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_CPUTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUTimeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_DataSegmentLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DataSegmentLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Environment(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Environment",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Error(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Error",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Executable(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Executable",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_FileSizeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileSizeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_GroupName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "GroupName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Input(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Input",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_LockedMemoryLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "LockedMemoryLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_MemoryLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "MemoryLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_OpenDescriptorsLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OpenDescriptorsLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_Output(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Output",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_PipeSizeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "PipeSizeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_POSIXApplication(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "POSIXApplication",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_ProcessCountLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ProcessCountLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_StackSizeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "StackSizeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_ThreadCountLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ThreadCountLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_UserName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "UserName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_VirtualMemoryLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "VirtualMemoryLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_WallTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "WallTimeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getDocumentRoot_WorkingDirectory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "WorkingDirectory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (environmentTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Environment_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getEnvironmentType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getEnvironmentType_FilesystemName(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "filesystemName"
       });		
    addAnnotation
      (getEnvironmentType_Name(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getEnvironmentType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":3",
       "processing", "lax"
       });		
    addAnnotation
      (fileNameTypeEClass, 
       source, 
       new String[] 
       {
       "name", "FileName_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getFileNameType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getFileNameType_FilesystemName(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "filesystemName"
       });		
    addAnnotation
      (getFileNameType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":2",
       "processing", "lax"
       });		
    addAnnotation
      (groupNameTypeEClass, 
       source, 
       new String[] 
       {
       "name", "GroupName_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getGroupNameType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getGroupNameType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });		
    addAnnotation
      (limitsTypeEClass, 
       source, 
       new String[] 
       {
       "name", "Limits_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getLimitsType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getLimitsType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });		
    addAnnotation
      (posixApplicationTypeEClass, 
       source, 
       new String[] 
       {
       "name", "POSIXApplication_Type",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getPOSIXApplicationType_Executable(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Executable",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_Argument(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Argument",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_Input(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Input",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_Output(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Output",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_Error(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Error",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_WorkingDirectory(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "WorkingDirectory",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_Environment(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "Environment",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_WallTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "WallTimeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_FileSizeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "FileSizeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_CoreDumpLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CoreDumpLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_DataSegmentLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "DataSegmentLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_LockedMemoryLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "LockedMemoryLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_MemoryLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "MemoryLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_OpenDescriptorsLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "OpenDescriptorsLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_PipeSizeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "PipeSizeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_StackSizeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "StackSizeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_CPUTimeLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "CPUTimeLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_ProcessCountLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ProcessCountLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_VirtualMemoryLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "VirtualMemoryLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_ThreadCountLimit(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "ThreadCountLimit",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_UserName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "UserName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_GroupName(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "GroupName",
       "namespace", "##targetNamespace"
       });		
    addAnnotation
      (getPOSIXApplicationType_Name(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "name"
       });		
    addAnnotation
      (getPOSIXApplicationType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":23",
       "processing", "lax"
       });		
    addAnnotation
      (userNameTypeEClass, 
       source, 
       new String[] 
       {
       "name", "UserName_Type",
       "kind", "simple"
       });		
    addAnnotation
      (getUserNameType_Value(), 
       source, 
       new String[] 
       {
       "name", ":0",
       "kind", "simple"
       });		
    addAnnotation
      (getUserNameType_AnyAttribute(), 
       source, 
       new String[] 
       {
       "kind", "attributeWildcard",
       "wildcards", "##other",
       "name", ":1",
       "processing", "lax"
       });
  }

} //PosixPackageImpl
