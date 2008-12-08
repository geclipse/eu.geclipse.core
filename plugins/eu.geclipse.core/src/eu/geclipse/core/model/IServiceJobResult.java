/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.core.model;

import java.util.Date;

/**
 * Interface for single service job result.<br>
 * <br>
 * Basic implementation of this interface (in form of SOJO bean) is in
 * {@link eu.geclipse.servicejob.model.impl.ServiceJobResult}.
 */
public interface IServiceJobResult {

  /**
   * Method to access information when corresponding service job was run.
   * 
   * @return Date when service job was run.
   */
  Date getRunDate();

  /**
   * Method to access name of the resource (e.g. host's URL) on which service 
   * job was run.
   * 
   * @return Name of resource on which service job was run in form of String.
   */
  String getResourceName();

  /**
   * In case of complex service jobs this method returns sub-job's name. If 
   * service job is simple, then returned value should be it's name.
   * 
   * @return Name of the sub-service job which was run or name of the simple
   *         service job which was run.
   */
  String getSubServiceJobName();

  /**
   * Method to access type of result's data. Preferably this should be file
   * extension that will determine Eclipse editor in which data should be
   * opened. See also {@link IServiceJob#getInputStreamForResult(IServiceJobResult)}
   * and {@link IServiceJobResult#getResultRawData()}.
   * 
   * @return Extension of file to which service job result's input stream may be
   *    saved (preferably without "." at the beginning, e.g. "TXT", not ".TXT").
   */
  String getResultType();

  /**
   * Method to access content of Output > Result > ResultData GTDL element
   * corresponding to this service job result instance. Returned String is 
   * unmodified content taken from GTDL file.
   * 
   * @return String content of Output > Result > ResultData GTDL element.
   */
  String getResultRawData();

  /**
   * Textual interpretation of service job's result.
   * 
   * @return String representing human-readable summary of service job's result.
   */
  String getResultSummary();

  /**
   * Human-readable service job's result. Represents either the state in which 
   * service job is (pending, running...) or - in case when service job was 
   * finished - service job's result in form of String (OK, ERROR, WARRNING...).
   * 
   * @return String representing human-readable summary of service job's result.
   */
  String getResultEnum();
}
