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
 *    Harald Kornmayer - initial API and implementation
 *    Ariel Garcia     - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * Action dedicated to the submission of Grid jobs.
 */
public class TransferJsdl2JdlAction extends SelectionListenerAction {

  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  private List<IGridJobDescription> jobDescriptions;
  private List<IGridJobCreator> jobCreators;

  protected TransferJsdl2JdlAction( final IWorkbenchSite site ) {
    super( Messages.getString( "TransferJsdl2JdlAction.title" ) ); //$NON-NLS-1$
    this.site = site;
  }

  /*
   * (non-Javadoc)
   *
   *  This is the run method for the Transformation Action from JSDL to JDL.
   *  This method is executed for JSDL files, if the corresponding menu item in the
   *  popup menu is selected. 
   *   
   *  Currently no effort were put in "generality", but in fast prototyping. 
   *  
   *  This methods
   *  
   *  1. Checks if there is already an JDL with the name "*.jsdl.jdl"
   *    if true then delete it
   *    
   *  2. Creates an transformator from an XSL File. 
   *  
   *  3. performs the transformation
   *  
   *  4. put the JDL file in the workspace and opens it. 
   *  
   *  
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    
    try {
      
      /*
       *  select the resource to transform 
       */
      IGridJobDescription jsdl =  this.jobDescriptions.get( 0 );
      IFile jsdlFile = ( IFile )jsdl.getResource();
      InputStream jsdlStream = null;
      jsdlStream = jsdlFile.getContents( true );

      /* 
       * check if an JDL already exists for the JSDL file. 
       */
      String jdlName = jsdl.getName() + ".jdl";           //$NON-NLS-1$
      IFolder folder = ( IFolder )jsdl.getParent().getResource();
      IFile outfile = folder.getFile( jdlName );

      if (outfile.exists())
        outfile.delete( true, null ) ;

      /*
       * get the XSLT file from this plugin! 
       * Be careful, the XSLT is distributed in different plugins
       */
      Bundle bundle = Activator.getDefault().getBundle();
      IPath path = new Path( "jsdl2jdl.xslt" ); //$NON-NLS-1$
      InputStream xsltStream = null;
      xsltStream = FileLocator.openStream( bundle, path, false );

      /*
       * create an instance of TransformerFactory
       */
      javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
      javax.xml.transform.Transformer trans = null;
      InputStreamReader jsdlReader = new InputStreamReader( jsdlStream );
      javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource( jsdlReader );
      // setup xslt source
      javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource( xsltStream );
      // setup transformation result as jdl String
      Writer jdlWriter = new StringWriter();
      javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult( jdlWriter );

      trans = transFact.newTransformer( xsltSource );
      trans.transform( xmlSource, result );

      xsltStream.close();
      jsdlReader.close();
      jdlWriter.close();

      /*
       * put the result of the transformation in the new file 
       */
      // mathias: Changed to ByteArrayInputStream since StringBufferInputStream is deprecated.
      //InputStream inputStream = new StringBufferInputStream( jdlWriter.toString() );
      byte[] bytes = jdlWriter.toString().getBytes();
      ByteArrayInputStream inputStream = new ByteArrayInputStream( bytes );

      outfile.create( inputStream, false, null );
    
      /*
       * open the JDL editor
       */
      IWorkbenchPage page = this.site.getPage(); 
      IEditorDescriptor desc = PlatformUI.getWorkbench()
      .getEditorRegistry()
      .getDefaultEditor( outfile.getName() );
    
      page.openEditor( new FileEditorInput( outfile ), desc.getId() );
    } catch( CoreException e ) {
      ProblemDialog.openProblem( this.site.getShell(),
                                 Messages.getString("TransferJsdl2JdlAction.DialogTitleCoreException"), //$NON-NLS-1$
                                 Messages.getString("TransferJsdl2JdlAction.DialogMessageCoreException"), //$NON-NLS-1$
                                 e );
    } catch( IOException e ) {
      ProblemDialog.openProblem( this.site.getShell(),
                                 Messages.getString("TransferJsdl2JdlAction.DialogTitleIOException"), //$NON-NLS-1$
                                 Messages.getString("TransferJsdl2JdlAction.DialogMessageIOException"), //$NON-NLS-1$
                                 e );
    } catch( TransformerConfigurationException e ) {
      ProblemDialog.openProblem( this.site.getShell(),
                                 Messages.getString("TransferJsdl2JdlAction.DialogTitleTransformerConfigException"), //$NON-NLS-1$
                                 Messages.getString("TransferJsdl2JdlAction.DialogMessageTransformationConfigException"), //$NON-NLS-1$
                                 e );
    } catch( TransformerException e ) {
      ProblemDialog.openProblem( this.site.getShell(),
                                 Messages.getString("TransferJsdl2JdlAction.DialogTitleTransformerException"), //$NON-NLS-1$
                                 Messages.getString("TransferJsdl2JdlAction.DialogMessageTransformerException"), //$NON-NLS-1$
                                 e ); 
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.jobCreators = null;
    this.jobDescriptions = new ArrayList<IGridJobDescription>();
    boolean enabled = super.updateSelection( selection );
    Iterator<?> iter = selection.iterator();
    while( iter.hasNext() && enabled ) {
      Object element = iter.next();
      boolean isDescription = isJobDescription( element );
      enabled &= isDescription;
      if( isDescription ) {
        this.jobDescriptions.add( ( IGridJobDescription )element );
        List<IGridJobCreator> creators = GridModel.getJobCreators( ( IGridJobDescription )element );
        if( this.jobCreators == null ) {
          this.jobCreators = creators;
        } else {
          this.jobCreators = mergeCreators( this.jobCreators, creators );
        }
      }
    }
    return enabled && ( this.jobCreators != null );
  }

  protected boolean isJobDescription( final Object element ) {
    return element instanceof eu.geclipse.core.model.IGridJobDescription;
  }

  private List<IGridJobCreator> mergeCreators( final List<IGridJobCreator> orig,
                                               final List<IGridJobCreator> merge )
  {
    List<IGridJobCreator> result = new ArrayList<IGridJobCreator>();
    for( IGridJobCreator oCreator : orig ) {
      boolean found = false;
      for( IGridJobCreator mCreator : merge ) {
        if( oCreator.getClass().equals( mCreator.getClass() ) ) {
          found = true;
          break;
        }
      }
      if( found ) {
        result.add( oCreator );
      }
    }
    return result;
  }
}
