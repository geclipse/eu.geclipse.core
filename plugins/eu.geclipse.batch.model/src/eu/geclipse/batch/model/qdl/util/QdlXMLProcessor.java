/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.batch.model.qdl.util;

import eu.geclipse.batch.model.qdl.QdlPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class QdlXMLProcessor extends XMLProcessor
{

  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QdlXMLProcessor()
  {
    super((EPackage.Registry.INSTANCE));
    QdlPackage.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the QdlResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected Map<String, Resource.Factory> getRegistrations()
  {
    if (registrations == null)
    {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new QdlResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new QdlResourceFactoryImpl());
    }
    return registrations;
  }

} //QdlXMLProcessor
