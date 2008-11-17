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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.sla.Extensions;
import eu.geclipse.core.sla.ISLAService;
import eu.geclipse.core.sla.model.SltContainerModel;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * This class offers a wizard which creates and publish a dedicated SLA
 * document. The procedure to create a SLA document is somehow different from
 * the standard Eclipse creation process. The creation of SLA is initiated by a
 * consumer of services. He defines his services requirements during the
 * creation phase and queries for "fitting" service offers in a registry with
 * service level template (SLT). If he find a list of adequate SLAs, he can
 * select one and enter his contact data. If the SLA fits his needs, he will
 * accept it (save it on the workspace or in a remote registry).
 * 
 * @author korn
 */
public class SLAQueryWizard extends Wizard implements INewWizard {

  protected IWorkbench workbench;
  protected IStructuredSelection selection;
  protected SltContainerModel sltModel = new SltContainerModel();
  protected String sltDoc = null;
  protected SLAQueryAddTermsPage termPage;
  protected SLAQuerySLTselectionPage selectPage;
  protected SLAQuerySLAPublication publishPage;

  /**
   * @param pageId
   */
  @Override
  public void addPages() {
    this.termPage = new SLAQueryAddTermsPage( "Define the SLA terms" );
    this.addPage( this.termPage );
    this.selectPage = new SLAQuerySLTselectionPage( "Select a SLT" );
    this.addPage( this.selectPage );
    this.publishPage = new SLAQuerySLAPublication( "Publish the SLA" );
    this.addPage( this.publishPage );
  }

  @Override
  public boolean performFinish() {
    boolean value = true;
    ISLAService service;
    try {
      service = Extensions.getSlaServiceImpl();
      String sla = service.confirmSLA( this.publishPage.getSLA() );
      if( sla == null )
        value = false;
    } catch( ProblemException e ) {
      ProblemDialog.openProblem( this.getShell(),
                                 "SLA service missing",
                                 "No implementation for ISLAService found",
                                 e );
      this.getContainer().getShell().close();
    }
    return value;
  }

  public void init( IWorkbench workbench, IStructuredSelection selection ) {
    this.workbench = workbench;
    this.selection = selection;
    setWindowTitle( "SLA Query and Negotiation Wizard" );
  }

  public SltContainerModel getSltModel() {
    return this.sltModel;
  }

  public void setSltDoc( String input ) {
    this.sltDoc = input;
  }

  public String getSltDoc() {
    return this.sltDoc;
  }
}