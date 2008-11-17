/* Copyright (c) 2008 g-Eclipse Consortium 
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
 *   Harald Kornmayer - NLE-IT
 *
 *****************************************************************************/
package eu.geclipse.core.sla;

/**
 * This interface contains the ProblemID strings for accessing the SLA problems.
 * These problem-ID's cover the issues expected to be common to different SLA
 * extensions.
 */
public class ISLAProblems {

  // Access control
  /** */
  public static final String MISSING_DOCUMENT_SERVICE = "eu.geclipse.core.sla.missingDocumentService"; //$NON-NLS-1$
  /** */
  public static final String MISSING_SLA_SERVICE = "eu.geclipse.core.sla.missingSLAService"; //$NON-NLS-1$
  /** */
  public static final String MISSING_PROVIDER_URI = "eu.geclipse.core.sla.missingProviderURI"; //$NON-NLS-1$
}
