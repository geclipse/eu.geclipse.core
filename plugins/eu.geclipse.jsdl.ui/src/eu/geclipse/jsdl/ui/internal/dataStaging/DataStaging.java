/******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse consortium 
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
package eu.geclipse.jsdl.ui.internal.dataStaging;

import java.net.URI;

import eu.geclipse.jsdl.ui.wizards.NewJobWizard;

/**
 * Class covering information about data staging in and out used in
 * {@link NewJobWizard}
 */
public class DataStaging {

  private String sourceLocation;
  private String name;
  private String targetLocation;

  /**
   * Creates new instance of IOFile
   * 
   * @param name name of the IOFile
   * @param targetLocation target remote location of file (data stage out)
   * @param sourceLocation source remote location of file (data stage in)
   * @throws Exception exception is thrown when target location is local or not
   *             absolute
   */
  public DataStaging( final String name,
                      final String targetLocation,
                      final String sourceLocation ) throws Exception
  {
    this.name = name;
    this.sourceLocation = sourceLocation;
    if( targetLocation == null ) {
      this.targetLocation = targetLocation;
    } else {
      setTargetLocation( targetLocation );
    }
  }

  /**
   * Getter method to access the name of the IOFile
   * 
   * @return name of the IOFile
   */
  public String getName() {
    return this.name;
  }

  /**
   * Setter method to set name of the IOFile
   * 
   * @param name name of the IOFile
   */
  public void setName( final String name ) {
    this.name = name;
  }

  /**
   * Getter method to access the source location of data staging in
   * 
   * @return source location
   */
  public String getSourceLocation() {
    return this.sourceLocation;
  }

  /**
   * Setter method to set source location of data staging in
   * 
   * @param location source location of data staging in
   */
  public void setSourceLocation( final String location ) {
    this.sourceLocation = location;
  }

  /**
   * Getter method to access the target location of data staging out
   * 
   * @return type of IOFile
   */
  public String getTargetLocation() {
    return this.targetLocation;
  }

  /**
   * Setter method to set target location of data staging out
   * 
   * @param target location
   */
  public void setTargetLocation( final String target ) throws Exception {
    //TODO kasia - change this method. Add new exception 
    URI uri = new URI( target );
    if( uri.isAbsolute() ) {
      this.targetLocation = target;
    } else {
      throw new Exception();
    }
  }

  @Override
  public boolean equals( final Object argument ) {
    boolean result = false;
    if( super.equals( argument ) ) {
      result = true;
    } else {
      if( argument instanceof DataStaging ) {
        DataStaging of = ( DataStaging )argument;
        if( of.getName().equals( this.getName() ) ) {
          result = true;
        }
      }
    }
    return result;
  }
}
