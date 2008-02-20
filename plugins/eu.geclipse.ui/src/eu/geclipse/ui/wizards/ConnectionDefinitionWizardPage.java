/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

package eu.geclipse.ui.wizards;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Tree;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;
import eu.geclipse.ui.widgets.GridConnectionDefinitionComposite;
import eu.geclipse.ui.widgets.StoredCombo;

public class ConnectionDefinitionWizardPage extends WizardPage {
  
  private GridConnectionDefinitionComposite connectionDefinitionComp;
    
  public ConnectionDefinitionWizardPage() {
    super( Messages.getString("ConnectionDefinitionWizardPage.name"), //$NON-NLS-1$
           Messages.getString("ConnectionDefinitionWizardPage.title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("ConnectionDefinitionWizardPage.description") ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    this.connectionDefinitionComp = new GridConnectionDefinitionComposite( parent, SWT.NULL );
    this.connectionDefinitionComp.addModifyListener( new ModifyListener () {
      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }
    } );
    setControl( this.connectionDefinitionComp );
  }
  
  public URI getURI() {
    return this.connectionDefinitionComp.getURI();
  }
  
  protected void validatePage() {
    URI uri = getURI();
    boolean valid = this.connectionDefinitionComp.isValid();
    setPageComplete( valid && ( uri != null ) );
    String errorMessage = this.connectionDefinitionComp.getErrorMessage();
    setErrorMessage( errorMessage );
  }
   
}
