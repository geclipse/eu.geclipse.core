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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages;

import java.net.URL;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * The JSDL Form Page base class.
 *
 */
public class JsdlFormPage extends FormPage implements INotifyChangedListener {

  protected boolean contentRefreshed = false;
  private ImageDescriptor helpDesc = null;  
  private boolean dirtyFlag = false;

  /**
   * Class constructor.
   * 
   * @param id The Page ID
   * @param title The Page Title
   */
  public JsdlFormPage( final String id, final String title ) {
    super( id, title );
  }

  /**
   *
   * Class Constructor.
   *
   * @param editor The FormEditor
   * @param id The Page ID
   * @param title The Page Title
   */
  public JsdlFormPage( final FormEditor editor, final String id, final String title ) {
    super( editor, id, title );
  }

  public void notifyChanged( final Notification notification ) {
    setDirty( true );
  }

  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty TRUE when the page is Dirty (content has been changed) and hence a 
   * Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirty ) {
    
    if ( this.dirtyFlag != dirty ) {
      this.dirtyFlag = dirty;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }

  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }

  protected boolean isContentRefreshed() {
    
    return this.contentRefreshed;
    
  } //End boolean isContentRefreshed()

  protected void addFormPageHelp( final ScrolledForm form ) {
    
    final String href = getHelpResource();
    if ( href != null ) {
        IToolBarManager manager = form.getToolBarManager();
        Action helpAction = new Action( "help" ) { //$NON-NLS-1$
            @Override
            public void run() {
                BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
                    public void run() {
                        PlatformUI.getWorkbench().getHelpSystem().displayHelpResource( href );
                    }
                });
            }
        };
        helpAction.setToolTipText( Messages.getString( "JsdlEditorPage_HelpToolTip" ) );  //$NON-NLS-1$
        URL stageInURL = Activator.getDefault().getBundle().getEntry( "icons/help.gif" ); //$NON-NLS-1$       
        this.helpDesc = ImageDescriptor.createFromURL( stageInURL ) ;   
        helpAction.setImageDescriptor( this.helpDesc );
        manager.add( helpAction );
        form.updateToolBar();
    }
    
  }
  
  protected String getHelpResource() {
    String result = ""; //$NON-NLS-1$
    
    return result;
  }
}