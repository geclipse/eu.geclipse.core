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

package eu.geclipse.jsdl.ui.editors;

import java.util.HashMap;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Multi page editor for jsdl files
 * 
 * @author katis
 */
public class JobDescriptionEditor extends MultiPageEditorPart {

  private TextEditor editor;
  // private EnvironmentVariables variablesEditor;
//  private EnvironmentVariables tab;

  /**
   * Creates new instance of {@link JobDescriptionEditor} class
   */
  public JobDescriptionEditor() {
    //empty method
  }

  private void createFirstPage() {
    try {
      this.editor = new TextEditor();
      int index = addPage( this.editor, getEditorInput() );
      setPageText( index, this.editor.getTitle() );
    } catch( PartInitException e ) {
      ErrorDialog.openError( getSite().getShell(),
                             Messages.getString( "JobDescriptionEditor.create_test_editor_error" ), //$NON-NLS-1$
                             null,
                             e.getStatus() );
    }
  }

  @Override
  protected void createPages()
  {
    createFirstPage();
    createSecondPage();
    this.setPartName( this.editor.getTitle() );
  }

  private void setVariablesOnSecondPage() {
    // HashMap<String, String> data = new HashMap<String, String>();
//    this.tab.setVariables( getVariablesFormFile() );
  }

  private void createSecondPage() {
    Composite mainComp = new Composite( this.getContainer(), SWT.NONE );
    mainComp.setLayout( new GridLayout() );
//    this.tab = new EnvironmentVariables();
//    this.tab.createControl( mainComp );
    addPage( 1, mainComp );
    setVariablesOnSecondPage();
    setPageText( 1, Messages.getString( "JobDescriptionEditor.page_text" ) ); //$NON-NLS-1$
  }

  @Override
  public void doSave( final IProgressMonitor monitor )
  {
    // EditorInputData a = new EditorInputData()
    // getEditor( 0 ).
    setVariablesOnSecondPage();
    getEditor( 0 ).doSave( monitor );
  }

  /**
   * This method is used to parse edited file in search for definition fo
   * environment variables It depends on file structure and it should be placed
   * somewhere elese (job class? or maybe parser for jsdl file). For now the
   * structure to keep variables is xml: <variable name="name"> value
   * </variable> TODO discuss this with George
   * 
   * @return HashMap where key is variable's name and value is this variable's
   *         value
   */
  public HashMap<String, String> getVariablesFormFile() {
    HashMap<String, String> variables = new HashMap<String, String>();
    IDocument doc = ( ( ITextEditor )this.editor ).getDocumentProvider()
      .getDocument( this.editor.getEditorInput() );
    String fileText = doc.get();
    String[] working = fileText.split( "<\\s*+jsdl:EnvironmentVariable\\s*+" ); //$NON-NLS-1$
    String[] little;
    String key;
    String value;
    for( String chunk : working ) {
      if( chunk.startsWith( "name" ) ) { //$NON-NLS-1$
        little = chunk.split( "name\\s*+=\\s*+" ); //$NON-NLS-1$
        key = little[ 1 ].split( "\"" )[ 1 ].trim(); //$NON-NLS-1$
        little = chunk.split( "\">" ); //$NON-NLS-1$
        value = little[ 1 ].split( "</" )[ 0 ].trim(); //$NON-NLS-1$
        variables.put( key, value );
      }
    }
    return variables;
  }

  @Override
  public void doSaveAs()
  {
    IEditorPart ed = getEditor( 0 );
    ed.doSaveAs();
    setPageText( 0, ed.getTitle() );
    setInput( ed.getEditorInput() );
  }

  @Override
  public boolean isSaveAsAllowed()
  {
    return true;
  }
}
