/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.util;

import eu.geclipse.jsdl.model.base.JsdlPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class JsdlXMLProcessor extends XMLProcessor
{

  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JsdlXMLProcessor()
  {
    super((EPackage.Registry.INSTANCE));
    JsdlPackage.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the JsdlResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected Map getRegistrations()
  {
    if (registrations == null)
    {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new JsdlResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new JsdlResourceFactoryImpl());
    }
    return registrations;
  }

} //JsdlXMLProcessor
