/**
 * <copyright>
 * </copyright>
 *
 * $Id: JsdlResourceFactoryImpl.java,v 1.2 2007/03/01 09:15:18 emstamou Exp $
 */
package eu.geclipse.jsdl.model.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see eu.geclipse.jsdl.model.util.JsdlResourceImpl
 * @generated
 */
public class JsdlResourceFactoryImpl extends ResourceFactoryImpl 
{
  /**
   * Creates an instance of the resource factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public JsdlResourceFactoryImpl()
  {
    super();
  }

  /**
   * Creates an instance of the resource.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public Resource createResource(URI uri)
  {
    XMLResource result = new JsdlResourceImpl(uri);
    result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
    result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

    result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

    result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
    result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

    result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
    return result;
  }

} //JsdlResourceFactoryImpl
