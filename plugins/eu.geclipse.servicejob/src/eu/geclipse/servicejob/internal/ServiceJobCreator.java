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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.internal;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.impl.AbstractFileElementCreator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.parsers.GTDLParser;

/**
 * Implementation of {@link AbstractFileElementCreator} for creator extension
 * point. Used to create {@link IServiceJob}s from .GTDL files.
 */
public class ServiceJobCreator extends AbstractFileElementCreator {

  @Override
  protected boolean internalCanCreate( final String fileExtension ) {
    boolean flag = false;
    if( fileExtension != null && fileExtension.equalsIgnoreCase( "gtdl" ) ) { //$NON-NLS-1$
      flag = true;
    }
    return flag;
  }

  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return IServiceJob.class.isAssignableFrom( elementType );
  }

  public IGridElement create( final IGridContainer parent )
    throws ProblemException
  {
    IServiceJob result = null;
    IFile file = ( IFile )getObject();
    try {
      String plugIn = GTDLParser.getPluginId( file.getRawLocation().toFile() );
      result = Extensions.getTestInstance( plugIn );
      result.internalInit( file );
    } catch( ParserConfigurationException parserExc ) {
      Activator.logException( parserExc );
    } catch( SAXException saxExc ) {
      Activator.logException( saxExc );
    } catch( IOException ioExc ) {
      Activator.logException( ioExc );
    }
    return result;
  }
}
