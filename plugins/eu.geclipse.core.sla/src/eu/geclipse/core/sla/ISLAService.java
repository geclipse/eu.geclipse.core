/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     Harald Kornmayer, NEC Laboratories Europe 
 *
 *****************************************************************************/
package eu.geclipse.core.sla;

import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.sla.model.SimpleTermModel;

/**
 * The middleware (or SLA schema) specific implementation are hidden behind this
 * interface. Each implemenation for SLA must implements this interface in order
 * to support the SLA process. This process looks like: 1. The provider creates
 * and publishs a Service level template (SLT) which represents an offer to
 * consumers 2. The consumer create service requirements and looks for SLTs
 * which fulfill them. He selected one of them and adds his contact details. 3.
 * He sends the final SLA document to the service provider and gets the
 * confirmation back (or failure). IMPORTANT: Make all the public methods of an
 * implementation of this interface synchronized.
 * 
 * @author korn
 */
public interface ISLAService {

  /**
   * set the URI of the registry
   * 
   * @param registryURI
   * @throws URISyntaxException
   */
  public void setRegistryURI( String registryURI ) throws URISyntaxException;

  /**
   * Publish a Service Level template in the registry.
   * 
   * @param sltFile
   */
  public void publishSLT( IFile sltFile );

  /**
   * queries the SLT registry with dedicated SLA requirements/terms
   * 
   * @param terms
   * @return the ordered list of potential SLT documents
   * @throws ProblemException 
   */
  public Object[] queryRegistry( String terms ) throws ProblemException;

  /**
   * Sends the final SLA document to the provider for confirmation.
   * 
   * @param slaDocument
   * @return the final document
   */
  public String confirmSLA( String slaDocument );

  /**
   * returns the requirements as a string (XML) from the Requirments model.
   * 
   * @param model
   * @return String
   */
  public String getRequirements( SimpleTermModel model );
}
