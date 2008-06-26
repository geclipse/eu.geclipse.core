/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
package eu.geclipse.batch.ui.editors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.model.qdl.util.QdlAdapterFactory;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.pages.AdvancedQueueConfigPage;
import eu.geclipse.batch.ui.internal.pages.SimpleQueueConfigPage;



/**
 * The Editor for a qdl file
 *
 */
public class QueueEditor extends FormEditor implements IEditingDomainProvider {
  
  protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<Resource, Diagnostic>();
  protected Collection<Resource> savedResources = new ArrayList<Resource>();
  protected Collection<Resource> removedResources = new ArrayList<Resource>();
  protected Collection<Resource> changedResources = new ArrayList<Resource>();
  protected ISelection editorSelection = StructuredSelection.EMPTY;
  protected MarkerHelper markerHelper = new EditUIMarkerHelper();
  protected AdapterFactoryEditingDomain editingDomain;
  protected boolean updateProblemIndication = true;
  protected ComposedAdapterFactory adapterFactory;  
  protected QueueType queue = null;
  
  protected IResourceChangeListener resourceChangeListener =
    new IResourceChangeListener() {
      public void resourceChanged(final IResourceChangeEvent event) {
        // Only listening to these.
        // if (event.getType() == IResourceDelta.POST_CHANGE)
        {
          IResourceDelta delta = event.getDelta();
          try {
            class ResourceDeltaVisitor implements IResourceDeltaVisitor {
              protected ResourceSet resourceSet = 
                                 QueueEditor.this.editingDomain.getResourceSet();
              protected Collection< Resource > changedRes= 
                                                    new ArrayList< Resource >();
              protected Collection< Resource > removedRes =
                                                    new ArrayList< Resource >();

              public boolean visit( final IResourceDelta deltaIn ) {
                if ( deltaIn.getFlags() != IResourceDelta.MARKERS 
                    &&
                    deltaIn.getResource().getType() == IResource.FILE ) {
                  if ( ( deltaIn.getKind() & ( IResourceDelta.CHANGED | IResourceDelta.REMOVED ) ) != 0 ) {
                    Resource resource = 
                      this.resourceSet.getResource( URI.createURI( deltaIn.getFullPath().toString() ), false );
                    if ( resource != null ) {
                      if ( ( deltaIn.getKind() & IResourceDelta.REMOVED) != 0 ) {
                        this.removedRes.add( resource );
                      }
                      else if ( !QueueEditor.this.savedResources.remove( resource ) ) {
                        this.changedRes.add( resource );
                      }
                    }
                  }
                }

                return true;
              }

              public Collection< Resource > getChangedResources() {
                return this.changedRes;
              }

              public Collection< Resource > getRemovedResources() {
                return this.removedRes;
              }
            }

            ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
            delta.accept(visitor);

            if ( !visitor.getRemovedResources().isEmpty() ) {
              QueueEditor.this.removedResources.addAll( visitor.getRemovedResources() );
              if ( !isDirty() ) {
                getSite().getShell().getDisplay().asyncExec
                  ( new Runnable()
                   {
                     public void run() {
                       getSite().getPage().closeEditor( QueueEditor.this, false );
                       QueueEditor.this.dispose();
                     }
                   });
              }
            }

            if ( !visitor.getChangedResources().isEmpty() ) {
              QueueEditor.this.changedResources.addAll( visitor.getChangedResources() );
              if ( getSite().getPage().getActiveEditor() == QueueEditor.this ) {
                getSite().getShell().getDisplay().asyncExec
                  ( new Runnable() {
                     public void run() {
                       handleActivate();
                     }
                   });
              }
            }
          } catch ( CoreException exception ) {
            Activator.logException( exception );
          }
        }
      }
    };
    
    
    protected EContentAdapter problemIndicationAdapter = 
      new EContentAdapter()
      {
        @Override
        public void notifyChanged( final Notification notification ) {
          if ( notification.getNotifier() instanceof Resource ) {
            switch ( notification.getFeatureID( Resource.class ) ) {
              case Resource.RESOURCE__IS_LOADED:
              case Resource.RESOURCE__ERRORS:
              case Resource.RESOURCE__WARNINGS: {
                Resource resource = ( Resource )notification.getNotifier();
                Diagnostic diagnostic = analyzeResourceProblems( ( Resource )notification.getNotifier(), null );
                if ( diagnostic.getSeverity() != Diagnostic.OK ) {
                  QueueEditor.this.resourceToDiagnosticMap.put( resource, diagnostic );
                }
                else {
                  QueueEditor.this.resourceToDiagnosticMap.remove( resource );
                }

                if ( QueueEditor.this.updateProblemIndication ) {
                  getSite().getShell().getDisplay().asyncExec
                    ( new Runnable() {
                       public void run() {
                         updateProblemIndication();
                       }
                     });
                }
              }
            }
          }
          else {
            super.notifyChanged( notification );
          }
        }

        @Override
        protected void setTarget(final Resource target) {
          basicSetTarget( target );
        }

        @Override
        protected void unsetTarget( final Resource targetIn ) {
          basicUnsetTarget( targetIn );
        }
        
      };  
  
  
  private StructuredTextEditor editor = null;
  private int sourcePageIndex;  
  private boolean refreshedModel = false;
  private boolean isDirtyFlag = false;
  private SimpleQueueConfigPage simpleQueueConfigPage = new SimpleQueueConfigPage(this);
  private AdvancedQueueConfigPage advancedQueueConfigPage = new AdvancedQueueConfigPage(this);
  
  
  
  /**
   * QueueEditor Default Constructor
   */
  public QueueEditor() {
    
    List<AdapterFactoryImpl> factories = new ArrayList<AdapterFactoryImpl>();
    factories.add(new ResourceItemProviderAdapterFactory() );
    factories.add(new QdlAdapterFactory());
    factories.add(new ReflectiveItemProviderAdapterFactory());
    
    this.adapterFactory = new ComposedAdapterFactory(factories);
    
    
    /*
     * Create the command stack that will notify this editor as commands 
     * are executed.
     */
     BasicCommandStack commandStack = new BasicCommandStack();
     
     /*
      * Add a listener to set the most recent command's affected objects to be 
      * the selection of the viewer with focus.
      * 
      */    

      commandStack.addCommandStackListener
       (new CommandStackListener()
        {
          @SuppressWarnings("synthetic-access")
          public void commandStackChanged(final EventObject event)
          {
            Composite container2 = getContainer();
            container2.getDisplay().asyncExec
              (new Runnable()
               {
                 public void run()
                 {
                   
                   firePropertyChange( IEditorPart.PROP_DIRTY );

                 }
               });
          }
        });
     
     
    
     /*
      *  Create the editing domain with a special command stack.
      */
     
     this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, 
       commandStack, new HashMap<Resource, Boolean>()); 
  }
  
  
  protected void cleanDirtyState() {
    this.simpleQueueConfigPage.setDirty( false );
    this.advancedQueueConfigPage.setDirty( false );
  }
  
  
  
  /**
   * This method set's the dirty status of the editor.
   * 
   * @param dirtyFlag
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirtyFlag ) {
    
   if ( this.isDirtyFlag != dirtyFlag ) {
     this.isDirtyFlag = dirtyFlag;     
     this.editorDirtyStateChanged();
   }
   
  } // End void setDirty()
  
  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
   */
  @Override
  protected void addPages() {
    
    getQdlModel();
    
    try {
      addPage( this.simpleQueueConfigPage );
      addPage( this.advancedQueueConfigPage );
      addResourceEditorPage();
      pushContentToPages();
    } catch( PartInitException e ) {
      Activator.logException( e );
    }
  }

  
  
  private void pushContentToPages() {
    this.simpleQueueConfigPage.setPageContent( this.queue, isModelRefreshed() );
    this.advancedQueueConfigPage.setPageContent( this.queue, isModelRefreshed() );
  }
  
  
  
  protected void refreshEditor() {
    
    this.refreshedModel = true;
    pushContentToPages();
    this.refreshedModel = false;
    
  }
  
  
  
  protected void doTextEditorSave(){
    
    this.editor.doSave( null );
    
  }
  
    
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void doSave( final IProgressMonitor monitor ) {
    /* Do the work within an operation because this is a long running activity
     * that modifies the workbench.
     */

    WorkspaceModifyOperation operation =
      new WorkspaceModifyOperation()
      {
        // This is the method that gets invoked when the operation runs.

      
        @Override
        public void execute( final IProgressMonitor monitorIn )
        {
          // Save the resources to the file system.
          //
          boolean first = true;
          for ( Iterator<?> i = QueueEditor.this.editingDomain.getResourceSet().getResources().iterator(); 
                i.hasNext(); ) {
            Resource resource = (Resource)i.next();
            if ( ( first || !resource.getContents().isEmpty() 
                || isPersisted(resource)) && !QueueEditor.this.editingDomain.isReadOnly(resource)) {
              try {
                QueueEditor.this.savedResources.add(resource);
                resource.save(Collections.EMPTY_MAP);
              }
              catch (Exception exception) {
                QueueEditor.this.resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));

                //setDirty( false );
                doTextEditorSave();
                cleanDirtyState();
                refreshEditor();

              }
              first = false;
            }
          }
        }
      };

    this.updateProblemIndication = false;
    try {
      // This runs the options, and shows progress.

      new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

      // Refresh the necessary state.

      ((BasicCommandStack)this.editingDomain.getCommandStack()).saveIsDone();

      //setDirty( false );
      doTextEditorSave();
      cleanDirtyState();
      refreshEditor();

    }
    catch (Exception exception) {
      // Something went wrong that shouldn't.

      Activator.logException( exception );
    }
    
    this.updateProblemIndication = true;
    updateProblemIndication();
    
  }
  
  
  @Override
  public void dispose() {
    
    this.updateProblemIndication = false;

    ResourcesPlugin.getWorkspace().removeResourceChangeListener( this.resourceChangeListener );
    
    this.adapterFactory.dispose();
    
    super.dispose();

    if( this.queue != null ) {
      this.queue.eResource().unload();
      this.queue = null;
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.EditorPart#doSaveAs()
   */
  @Override
  public void doSaveAs() {
    SaveAsDialog saveAsDialog= new SaveAsDialog(getSite().getShell());
    saveAsDialog.open();
    IPath path= saveAsDialog.getResult();
    if ( path != null ) {
      IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
      if ( file != null ) {
        doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString(), false), new FileEditorInput(file));
      }
    }
  }
  
  
  /* Save the QDL file (used as the editor input) as a different name. */
  protected void doSaveAs( final URI uri, final IEditorInput editorInput )  {
    this.editingDomain.getResourceSet().getResources().get( 0 ).setURI( uri );
    setInputWithNotify( editorInput );
    setPartName( editorInput.getName() );
    IProgressMonitor progressMonitor = new NullProgressMonitor();
    doSave( progressMonitor );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
   */
  @Override
  public boolean isSaveAsAllowed() {
    return true;
  }
  
  public EditingDomain getEditingDomain() {
    return this.editingDomain;
  }
  
  /**
   * @return true if the the QDL Model was refreshed / changed.
   * This could be caused by an external editor.
   */
  public boolean isModelRefreshed() {
    return this.refreshedModel;
  }  
  
  /*
   * This method adds the XML Resource Editor Page to the Queue editor
   */
  private void addResourceEditorPage()throws PartInitException{
    this.sourcePageIndex = addPage( getSourceEditor(), getEditorInput() );
    
    setPageText( this.sourcePageIndex, getEditorInput().getName() );

    getSourceEditor().setInput( getEditorInput() );
  }
  
  /*
   * This method returns a Text Editor for addResourceEditorPage method.
   */
  private StructuredTextEditor getSourceEditor() {  
    if ( this.editor == null ) {
      this.editor = new StructuredTextEditor();   
      this.editor.setEditorPart( this );
    }
    return this.editor;
  }
  
  /**
   * @see org.eclipse.ui.part.MultiPageEditorPart#createSite(org.eclipse.ui.IEditorPart)
   */
  @Override
  protected IEditorSite createSite( final IEditorPart page ) {
    IEditorSite site = null;
    if ( page == this.editor ) {
      site = new MultiPageEditorSite( this, page ) {
        @Override
        public String getId() {
          // Sets this ID so nested editor is configured for XML source          
          return "org.eclipse.core.runtime.xml" + ".source";     //$NON-NLS-1$ //$NON-NLS-2$
        }
      };
    } else {
      site = super.createSite( page );
    }
    return site;
  }
  
  
  @Override
  public void init( final IEditorSite site, final IEditorInput editorInput ) {
    setSite(site);
    setInputWithNotify( editorInput );
    setPartName( editorInput.getName() );
    ResourcesPlugin.getWorkspace()
                        .addResourceChangeListener( this.resourceChangeListener,
                                              IResourceChangeEvent.POST_CHANGE );
  }
  
    
    
  protected void handleActivate() {
    // Recompute the read only state.
    if ( this.editingDomain.getResourceToReadOnlyMap() != null ) {
      this.editingDomain.getResourceToReadOnlyMap().clear();

      // Refresh any actions that may become enabled or disabled.
      setSelection( getSelection() );
    }

    if ( !this.removedResources.isEmpty() ) {
      if ( handleDirtyConflict() ) {
        getSite().getPage().closeEditor( QueueEditor.this, false );
        QueueEditor.this.dispose();
      } else {
        this.removedResources.clear();
        this.changedResources.clear();
        this.savedResources.clear();
      }
    } else if ( !this.changedResources.isEmpty() ) {
      this.changedResources.removeAll( this.savedResources );
      handleChangedResources();
      this.changedResources.clear();
      this.savedResources.clear();
    }
  }

  protected void handleChangedResources() {
    if ( !this.changedResources.isEmpty() && ( !isDirty() || handleDirtyConflict() ) ) {
      this.editingDomain.getCommandStack().flush();

      this.updateProblemIndication = false;
      for ( Iterator< Resource > i = this.changedResources.iterator(); i.hasNext(); ) {
        Resource resource = i.next();
        if ( resource.isLoaded() ) {
          resource.unload();
          
          try {
            resource.load(Collections.EMPTY_MAP);
          } catch ( IOException exception ) {
            if (!this.resourceToDiagnosticMap.containsKey( resource ) ) {
              this.resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
            }
          }
        }
      }
      
      this.updateProblemIndication = true;
      updateProblemIndication();          
      getQdlModel();          
    }
  }

  protected boolean handleDirtyConflict() {
    return
        MessageDialog.openQuestion
          ( getSite().getShell(),
           "QueueEditor.FileConflict.label", //$NON-NLS-1$
           "Queue.WARN.FileConflict" ); //$NON-NLS-1$
  }

  
  
  /**
   * @return editorSelection
   */
  public ISelection getSelection() {
    return this.editorSelection;
  }
  
  
  
  /**
   * @param selection
   */
  public void setSelection( final ISelection selection ) {
    this.editorSelection = selection;
  }
  
  
  /* Method triggered when there are changes between the form pages.*/
  @Override
  protected void pageChange( final int pageIndex ) {      
    super.pageChange( pageIndex );
  }
  
  
  
  /**
   * Responsible for de-serializing the model from the resource file.
   * The resource is passed to the getResourceRoot method.
   */
  
   public void getQdlModel(){
     
     // Assumes that the input is a file object.
     //
     IFileEditorInput modelFile = ( IFileEditorInput )getEditorInput();
     URI resourceURI = URI.createPlatformResourceURI( modelFile.getFile().getFullPath().toString(), false );
     Exception exception = null;
     Resource resource = null;
     try {
       // Load the resource through the editing domain.
       //
       resource = this.editingDomain.getResourceSet().getResource( resourceURI, true );
       
     } catch ( Exception e ) {
       exception = e;
       resource = this.editingDomain.getResourceSet().getResource( resourceURI, false );
     }

     Diagnostic diagnostic = analyzeResourceProblems( resource, exception );
     if ( diagnostic.getSeverity() != Diagnostic.OK ) {
       this.resourceToDiagnosticMap.put( resource,  analyzeResourceProblems( resource, exception ) );
     }

     this.editingDomain.getResourceSet().eAdapters().add(this.problemIndicationAdapter);

     getResourceRoot( resource ); 
     
     // This means the file was edited from an external editor so
     // push the new QDL model to the pages.
     if ( !this.changedResources.isEmpty() ){
         refreshEditor();
     }
   }
   
   
   /*
   This method parses the resource in order to find which QDL types
   appear. Each QDL type is then passed as a reference parameter (EList) in the 
   appropriate page of the Queue editor.   
  */
  private void getResourceRoot( final Resource resource ) {
    // Get an iterator to iterate through all contents of the resource.
    TreeIterator <EObject> iterator = resource.getAllContents();
    
    while ( iterator.hasNext (  )  )  {  

      EObject testElement = iterator.next();           

      /* Instace-of checks for each EObject that appears in the resource. 
       * We want to get the JobDefinition EObject which is the root Element of 
       * a QDL Document.
       */
      
      if ( testElement instanceof QueueType ) {         
        this.queue = ( QueueType ) testElement;  
      }
    }  
  }
  
  
  /**
   * This looks up a string in plug-in.properties, making a substitution.
   *  Returns a diagnostic describing the errors and warnings listed in 
   *  the resource and the specified exception
   * @param resource 
   * @param exception 
   * @return Diagnostic
   */
  public Diagnostic analyzeResourceProblems( final Resource resource, final Exception exception ) {
    Diagnostic basicDiagnostic = null;
    
    if ( !resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty() ) {
      basicDiagnostic =
        new BasicDiagnostic
          ( Diagnostic.ERROR,
           Activator.PLUGIN_ID,
           0,
           Messages.getString( "QueueEditor.CreateModelErrorMessage" ),  //$NON-NLS-1$
           new Object [] { exception == null ? ( Object )resource : exception });
      
      ( ( BasicDiagnostic ) basicDiagnostic ).merge( EcoreUtil.computeDiagnostic( resource, true ) );
    }
    else if ( exception != null ) {
      basicDiagnostic =
        new BasicDiagnostic
          (Diagnostic.ERROR,
              Activator.PLUGIN_ID,
           0,
           Messages.getString( "QueueEditor.CreateModelErrorMessage" ),  //$NON-NLS-1$
           new Object[] { exception });
    }
    else {
      basicDiagnostic = Diagnostic.OK_INSTANCE;
    }
    return basicDiagnostic;
  }



  protected void updateProblemIndication() {
    if ( this.updateProblemIndication )  {
      BasicDiagnostic diagnostic =
        new BasicDiagnostic
          ( Diagnostic.OK,
           Activator.PLUGIN_ID,
           0,
           null,
           new Object [] { this.editingDomain.getResourceSet() } );
      for (Iterator<Diagnostic> i = this.resourceToDiagnosticMap.values().iterator(); i.hasNext(); ) {
        Diagnostic childDiagnostic = i.next();
        if ( childDiagnostic.getSeverity() != Diagnostic.OK ) {
          diagnostic.add( childDiagnostic );
        }
      }

      int lastEditorPage = getPageCount() - 1;
      if ( lastEditorPage >= 0 && getEditor( lastEditorPage ) instanceof ProblemEditorPart ) {
        ( ( ProblemEditorPart )getEditor( lastEditorPage ) ).setDiagnostic( diagnostic );
        if ( diagnostic.getSeverity() != Diagnostic.OK ) {
          setActivePage(lastEditorPage);
        }
      } else if ( diagnostic.getSeverity() != Diagnostic.OK ) {
        ProblemEditorPart problemEditorPart = new ProblemEditorPart();
        problemEditorPart.setDiagnostic(diagnostic);
        problemEditorPart.setMarkerHelper(this.markerHelper);

        try {
          addPage(++lastEditorPage, problemEditorPart, getEditorInput());
          setPageText(lastEditorPage, problemEditorPart.getPartName());
          setActivePage(lastEditorPage);
        } catch ( PartInitException exception ) {
          Activator.logException( exception );        
        }
      }

      if ( this.markerHelper.hasMarkers( this.editingDomain.getResourceSet() ) ) {
        this.markerHelper.deleteMarkers( this.editingDomain.getResourceSet() );
        if ( diagnostic.getSeverity() != Diagnostic.OK ) {
          try {
            this.markerHelper.createMarkers(diagnostic);
          } catch ( CoreException exception ) {
            Activator.logException( exception );            
          }
        }
      }
    }
  }



  protected boolean isPersisted( final Resource resource ) {
    boolean result = false;
    try {
      InputStream stream = this.editingDomain.getResourceSet().getURIConverter().createInputStream(resource.getURI());
      if ( stream != null ) {
        result = true;
        stream.close();
      }
    }
    catch (IOException e)
    {
      Activator.logException( e );
    }
    return result;
  }

  
} // End QueueEditor Class
