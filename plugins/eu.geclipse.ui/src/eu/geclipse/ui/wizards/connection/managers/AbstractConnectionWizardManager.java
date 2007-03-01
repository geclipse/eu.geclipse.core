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

package eu.geclipse.ui.wizards.connection.managers;

import java.util.ArrayList;
import eu.geclipse.core.connection.AbstractConnectionTypeManager;
import eu.geclipse.ui.wizards.connection.IConnectionWizardPage;

/**
 * Abstract class that must be implemented by a contributor providing new type
 * of connection that will be used in NewConnectionWizard. Implementation of
 * this class manages wizard pages for this connection type.
 * 
 * @author katis
 */
public abstract class AbstractConnectionWizardManager {

  ArrayList<IConnectionWizardPage> pagesList = new ArrayList<IConnectionWizardPage>();

  /**
   * Creates new instance of class
   */
  public AbstractConnectionWizardManager() {
    addPages();
  }

  /**
   * Method that returns all wizard pages that should be used in
   * NewConnectionWizard to create connection type provided by contributor
   * 
   * @return List of Wizard Pages
   */
  public ArrayList<IConnectionWizardPage> getPagesList() {
    return this.pagesList;
  }

  /**
   * @return number of wizard pages this contributor will provide for
   *         NewConnectionWizard
   */
  public int getPagesCount() {
    return this.pagesList.size();
  }

  /**
   * @param page
   */
  public void addPage( final IConnectionWizardPage page ) {
    this.pagesList.add( page );
  }

  /**
   * Method which should create and initialize all wizard pages
   */
  public abstract void addPages();

  /**
   * Method that provides access to wizard page that should be displayd as a
   * first page
   * 
   * @return Wizard page that should be displayed as a first of all pages
   */
  public abstract IConnectionWizardPage getFirstPage();

  /**
   * Returns user friendly name of connection type. This Manager manages wizard
   * pages of this connection type
   * 
   * @return name of connection type
   */
  public abstract String getConnectionName();

  /**
   * If contributor won't provide connection wizard pages he/she should use this
   * method to get implementation of AbstractConnectionTypeManager. If he/she
   * uses connection wizard pages another method to get
   * AbstractConnectionTypeManager should be created (taking into account other
   * parameters than hostName)
   * 
   * @param hostName the name of the host
   * @return instance of AbstractConnectionTypeManager dealing with transport
   *         layer, an implementation of a connection to a host using some kind
   *         of protocol
   */
  public abstract AbstractConnectionTypeManager getAbstractConnectionTypeManager( String hostName );
}
