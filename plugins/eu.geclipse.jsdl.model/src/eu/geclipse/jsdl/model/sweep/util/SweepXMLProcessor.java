/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.util;

import eu.geclipse.jsdl.model.sweep.SweepPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SweepXMLProcessor extends XMLProcessor
{

  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SweepXMLProcessor()
  {
    super((EPackage.Registry.INSTANCE));
    SweepPackage.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the SweepResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected Map getRegistrations()
  {
    if (registrations == null)
    {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new SweepResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new SweepResourceFactoryImpl());
    }
    return registrations;
  }

} //SweepXMLProcessor
