/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import eu.geclipse.core.GridException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;

/**
 * Abstract implementation of the {@link IGridJobCreator} interface
 * that delegates the {@link #canCreate(IGridJobDescription)} method to
 * an internal one and handles the storage of the argument of this
 * function.
 */
public abstract class AbstractGridJobCreator
    extends AbstractGridElementCreator
    implements IGridJobCreator {
  
  /**
   * The job description from the last successful call of the
   * {@link #canCreate(IGridJobDescription)} method.
   */
  private IGridJobDescription internalDescription;

  public boolean canCreate( final IGridJobDescription description ) {
    boolean result = internalCanCreate( description );
    if ( result ) {
      this.internalDescription = description;
    }
    return result;
  }
  
  /**
   * Get the job description from the last successful call of the
   * {@link #canCreate(IGridJobDescription)} method.
   * 
   * @return The argument of the last successful call of the
   * {@link #canCreate(IGridJobDescription)} method.
   */
  public IGridJobDescription getDescription() {
    return this.internalDescription;
  }
  
  /**
   * Internal method to determine if this creator is potentially 
   * able to create jobs from the specified description. This method
   * is called from {@link #canCreate(IGridJobDescription)} and has
   * not to care about storing the passed object since this is
   * handled by the {@link #canCreate(IGridJobDescription)} method.
   * Therefore this method may never be called directly.
   * 
   * @param description The description from which the job should be
   * created.
   * @return True if this creator is potentially able to create a
   * job from the specified description.
   */
  protected abstract boolean internalCanCreate( final IGridJobDescription description );

  
  


  
}
