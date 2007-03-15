/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.connection;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.IWizardPage;

/**
 * First page of {@link ConnectionWizard} must implement this interface.
 * 
 * @author katis
 */
public interface IConnectionFirstPage extends IWizardPage {

  /**
   * Method to access connection's name
   * 
   * @return name of the connection provided by user
   */
  public abstract String getConnectionName();

  /**
   * Method to set name of the file to be created when wizard performs finish.
   * This file will hold connection's information.
   * 
   * @param name name of the file
   */
  public abstract void setFileName( String name );

  /**
   * Creates new file with connection's information
   * 
   * @return created file
   */
  public abstract IFile createNewFile();

  /**
   * Method to set initial contents of a file that will be created when wizard
   * performs finish
   * 
   * @param text initial file contents
   */
  public abstract void setInitialText( String text );
}
