/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *    Mathias Stümpert
 *           
 *****************************************************************************/

package eu.geclipse.jsdl.model.posix.util;

import eu.geclipse.jsdl.model.posix.PosixPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PosixXMLProcessor extends XMLProcessor 
{
  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	public PosixXMLProcessor()
  {
    super((EPackage.Registry.INSTANCE));
    PosixPackage.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the PosixResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
   * @generated
   */
	protected Map getRegistrations()
  {
    if (registrations == null)
    {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new PosixResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new PosixResourceFactoryImpl());
    }
    return registrations;
  }

} //PosixXMLProcessor
