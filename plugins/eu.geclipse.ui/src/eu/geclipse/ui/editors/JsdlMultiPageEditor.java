/******************************************************************************
  * Copyright (c) 2007 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     UCY (http://www.ucy.cs.ac.cy)
  *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
  *      
  *****************************************************************************/


package eu.geclipse.ui.editors;


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
import org.eclipse.emf.common.notify.AdapterFactory;
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
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import eu.geclipse.jsdl.JobDefinitionType;
import eu.geclipse.jsdl.posix.provider.PosixItemProviderAdapterFactory;
import eu.geclipse.jsdl.provider.JsdlItemProviderAdapterFactory;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.jsdl.editor.pages.DataStagingPage;
import eu.geclipse.ui.jsdl.editor.pages.JobApplicationPage;
import eu.geclipse.ui.jsdl.editor.pages.JobDefinitionPage;
import eu.geclipse.ui.jsdl.editor.pages.ResourcesPage;



public class JsdlMultiPageEditor extends FormEditor implements IEditingDomainProvider{
  
  public static final JsdlMultiPageEditor INSTANCE = new JsdlMultiPageEditor();
  
  protected AdapterFactoryEditingDomain editingDomain;
  protected Collection<Resource> savedResources = new ArrayList<Resource>();
  protected Viewer currentViewer;
  protected Collection<Resource> removedResources = new ArrayList<Resource>();
  protected Collection<Resource> changedResources = new ArrayList<Resource>();
  protected ISelection editorSelection = StructuredSelection.EMPTY;
  protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<Resource, Diagnostic>();
  protected boolean updateProblemIndication = true;
  protected MarkerHelper markerHelper = new EditUIMarkerHelper();
  protected ComposedAdapterFactory adapterFactory;
  protected JobDefinitionType jobDefType = null;  
  private TextEditor editor = null;
  private int sourcePageIndex;
  private JobDefinitionPage jobDefPage = new JobDefinitionPage(this);
  private JobApplicationPage jobApplicationPage = new JobApplicationPage(this);
  private DataStagingPage dataStagingPage = new DataStagingPage(this);
  private ResourcesPage resourcesPage = new ResourcesPage(this);
  private boolean refreshedModel = false;
  
  public JsdlMultiPageEditor(){
    // Create an adapter factory that yields item providers.
    //
    List<AdapterFactoryImpl> factories = new ArrayList<AdapterFactoryImpl>();
    factories.add(new ResourceItemProviderAdapterFactory());
    factories.add(new JsdlItemProviderAdapterFactory());
    factories.add(new PosixItemProviderAdapterFactory());
    factories.add(new ReflectiveItemProviderAdapterFactory());

    this.adapterFactory = new ComposedAdapterFactory(factories);

    // Create the command stack that will notify this editor as commands are executed.
    //
    BasicCommandStack commandStack = new BasicCommandStack();
    
    // Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
    //
    commandStack.addCommandStackListener
      (new CommandStackListener()
       {
         public void commandStackChanged(final EventObject event)
         {
           getContainer().getDisplay().asyncExec
             (new Runnable()
              {
                public void run()
                {
                  firePropertyChange(IEditorPart.PROP_DIRTY);

                  // Try to select the affected objects.
                  //
                  //Command mostRecentCommand = ((CommandStack)event.getSource()).getMostRecentCommand();

                }
              });
         }
       });
    
    
   
    // Create the editing domain with a special command stack.
    //
    this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, 
      commandStack, new HashMap<Resource, Boolean>()); 
        
      }
  

  @Override
  protected void addPages()
  {
    getJsdlModel();
   
      
     try {
      addPage(this.jobDefPage);      
      addPage(this.jobApplicationPage);
      addPage(this.resourcesPage);
      addPage(this.dataStagingPage);
      addResourceEditorPage();
      pushContentToPages();
         }
   catch (PartInitException e) {
      Activator.logException( e );
   }
    
  }
  
  /*
   * This method is responsible for pushing content to the 
   * pages. The content pushed is actually the JobDefinition
   * element, which is the root element.
   */
  private void pushContentToPages(){    
    this.jobDefPage.setPageContent( this.jobDefType, isModelRefreshed());
    this.jobApplicationPage.setPageContent( this.jobDefType, isModelRefreshed());
    //this.dataStagingPage.setPageContent( this.jobDefType, isModelRefreshed());
  }
  
  /*
   * Returns true if the Model was refreshed/changed.
   * This could be caused by an external editor
  */ 
  public boolean isModelRefreshed(){
     
    return refreshedModel;
  }
  
 
  /*
   * This method adds the Resource Editor Page to the Multi-page editior
   */
  private void addResourceEditorPage()throws PartInitException{
    
    this.sourcePageIndex = addPage(getSourceEditor(), getEditorInput());
    
    setPageText(this.sourcePageIndex, getEditorInput().getName());

    getSourceEditor().setInput(getEditorInput());
    
  }
   
  /*
   * This method returns a Text Editor for addResourceEditorPage method.
   */

  private TextEditor getSourceEditor()
  {  
     if (this.editor == null)
      {
          this.editor = new TextEditor();
      }
      return this.editor;
  }

  
  @Override
  public void init(final IEditorSite site, final IEditorInput editorInput){
    setSite(site);
    setInputWithNotify(editorInput);
    setPartName(editorInput.getName());
    ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE);
}


  protected IResourceChangeListener resourceChangeListener =
    new IResourceChangeListener()
    {
      public void resourceChanged(final IResourceChangeEvent event)
      {
        // Only listening to these.
        // if (event.getType() == IResourceDelta.POST_CHANGE)
        {
          IResourceDelta delta = event.getDelta();
          try
          {
            class ResourceDeltaVisitor implements IResourceDeltaVisitor
            {
              protected ResourceSet resourceSet = JsdlMultiPageEditor.this.editingDomain.getResourceSet();
              protected Collection< Resource > changedResources = new ArrayList< Resource >();
              protected Collection< Resource > removedResources = new ArrayList< Resource >();

              public boolean visit(final IResourceDelta delta)
              {
                if (delta.getFlags() != IResourceDelta.MARKERS 
                    &&
                    delta.getResource().getType() == IResource.FILE)
                {
                  if ((delta.getKind() & (IResourceDelta.CHANGED | IResourceDelta.REMOVED)) != 0)
                  {
                    Resource resource = this.resourceSet.getResource(URI.createURI(delta.getFullPath().toString()), false);
                    if (resource != null)
                    {
                      if ((delta.getKind() & IResourceDelta.REMOVED) != 0)
                      {
                        this.removedResources.add(resource);
                      }
                      else if (!JsdlMultiPageEditor.this.savedResources.remove(resource))
                      {
                        this.changedResources.add(resource);
                      }
                    }
                  }
                }

                return true;
              }

              public Collection< Resource > getChangedResources()
              {
                return this.changedResources;
              }

              public Collection< Resource > getRemovedResources()
              {
                return this.removedResources;
              }
            }

            ResourceDeltaVisitor visitor = new ResourceDeltaVisitor();
            delta.accept(visitor);

            if (!visitor.getRemovedResources().isEmpty())
            {
              JsdlMultiPageEditor.this.removedResources.addAll(visitor.getRemovedResources());
              if (!isDirty())
              {
                getSite().getShell().getDisplay().asyncExec
                  (new Runnable()
                   {
                     public void run()
                     {
                       getSite().getPage().closeEditor(JsdlMultiPageEditor.this, false);
                       JsdlMultiPageEditor.this.dispose();
                     }
                   });
              }
            }

            if (!visitor.getChangedResources().isEmpty())
            {
              JsdlMultiPageEditor.this.changedResources.addAll(visitor.getChangedResources());
              if (getSite().getPage().getActiveEditor() == JsdlMultiPageEditor.this)
              {
                getSite().getShell().getDisplay().asyncExec
                  (new Runnable()
                   {
                     public void run()
                     {
                       handleActivate();
                     }
                   });
              }
            }
          }
          catch (CoreException exception)
          {
            Activator.logException( exception);
          }
        }
      }
    };
  
    protected void handleActivate()
      {
        // Recompute the read only state.
        //
        if (this.editingDomain.getResourceToReadOnlyMap() != null)
        {
          this.editingDomain.getResourceToReadOnlyMap().clear();

          // Refresh any actions that may become enabled or disabled.
          //
          setSelection(getSelection());
        }

        if (!this.removedResources.isEmpty())
        {
          if (handleDirtyConflict())
          {
            getSite().getPage().closeEditor(JsdlMultiPageEditor.this, false);
            JsdlMultiPageEditor.this.dispose();
          }
          else
          {
            this.removedResources.clear();
            this.changedResources.clear();
            this.savedResources.clear();
          }
        }
        else if (!this.changedResources.isEmpty())
        {
          this.changedResources.removeAll(this.savedResources);
          handleChangedResources();
          this.changedResources.clear();
          this.savedResources.clear();
        }
      }
    
    protected void handleChangedResources()
      {
        if (!this.changedResources.isEmpty() && (!isDirty() || handleDirtyConflict()))
        {
          this.editingDomain.getCommandStack().flush();

          this.updateProblemIndication = false;
          for (Iterator< Resource > i = this.changedResources.iterator(); i.hasNext(); )
          {
            Resource resource = i.next();
            if (resource.isLoaded())
            {
              resource.unload();
              try
              {
                resource.load(Collections.EMPTY_MAP);
              }
              catch (IOException exception)
              {
                if (!this.resourceToDiagnosticMap.containsKey(resource))
                {
                  this.resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
                }
              }
            }
          }
          this.updateProblemIndication = true;
          updateProblemIndication();          
          getJsdlModel();          
        }
      }

    protected boolean handleDirtyConflict()
      {
        return
          MessageDialog.openQuestion
            (getSite().getShell(),
             "_UI_FileConflict_label",
             "_WARN_FileConflict");
      }
  
    public ISelection getSelection()
      {
        return this.editorSelection;
      }
    
    public void setSelection(final ISelection selection)
      {
        this.editorSelection = selection;

      }
        
    
    
  @Override
  public void doSave(final IProgressMonitor monitor )
  {
//  Do the work within an operation because this is a long running activity that modifies the workbench.
    //
    WorkspaceModifyOperation operation =
      new WorkspaceModifyOperation()
      {
        // This is the method that gets invoked when the operation runs.
        
        @Override
        public void execute(final IProgressMonitor monitor)
        {
          // Save the resources to the file system.
          //
          boolean first = true;
          for (Iterator<?> i = JsdlMultiPageEditor.this.editingDomain.getResourceSet().getResources().iterator(); i.hasNext(); )
          {
            Resource resource = (Resource)i.next();
            if ((first || !resource.getContents().isEmpty() || isPersisted(resource)) && !JsdlMultiPageEditor.this.editingDomain.isReadOnly(resource))
            {
              try
              {
                JsdlMultiPageEditor.this.savedResources.add(resource);
                resource.save(Collections.EMPTY_MAP);
              }
              catch (Exception exception)
              {
                JsdlMultiPageEditor.this.resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
              }
              first = false;
            }
          }
        }
      };

    this.updateProblemIndication = false;
    try
    {
      // This runs the options, and shows progress.
      //
      new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

      // Refresh the necessary state.
      //
      ((BasicCommandStack)this.editingDomain.getCommandStack()).saveIsDone();
      firePropertyChange(IEditorPart.PROP_DIRTY);
    }
    catch (Exception exception)
    {
      // Something went wrong that shouldn't.
      //
      Activator.logException( exception );
    }
    this.updateProblemIndication = true;
    updateProblemIndication();
    
  }
  
  

  @Override
  public void doSaveAs()
  {
    SaveAsDialog saveAsDialog= new SaveAsDialog(getSite().getShell());
    saveAsDialog.open();
    IPath path= saveAsDialog.getResult();
    if (path != null)
    {
      IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
      if (file != null)
      {
        doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString(), false), new FileEditorInput(file));
      }
    }
    
  }
  
  // Save the JSDL file (used as the editor input) as a different name.
  
  protected void doSaveAs(final URI uri, final IEditorInput editorInput)
  {
    ((Resource)this.editingDomain.getResourceSet().getResources().get(0)).setURI(uri);
    setInputWithNotify(editorInput);
    setPartName(editorInput.getName());
    IProgressMonitor progressMonitor = new NullProgressMonitor();
    doSave(progressMonitor);
  }
  

  @Override
  public boolean isSaveAsAllowed()
  {
    // TODO Auto-generated method stub
    return true;
  }
 
 
  protected void updateProblemIndication()
  {
    if (this.updateProblemIndication)
    {
      BasicDiagnostic diagnostic =
        new BasicDiagnostic
          (Diagnostic.OK,
           "eu.geclipse.ui", //$NON-NLS-1$
           0,
           null,
           new Object [] { this.editingDomain.getResourceSet() });
      for (Iterator<Diagnostic> i = this.resourceToDiagnosticMap.values().iterator(); i.hasNext(); )
      {
        Diagnostic childDiagnostic = i.next();
        if (childDiagnostic.getSeverity() != Diagnostic.OK)
        {
          diagnostic.add(childDiagnostic);
        }
      }

      int lastEditorPage = getPageCount() - 1;
      if (lastEditorPage >= 0 && getEditor(lastEditorPage) instanceof ProblemEditorPart)
      {
        ((ProblemEditorPart)getEditor(lastEditorPage)).setDiagnostic(diagnostic);
        if (diagnostic.getSeverity() != Diagnostic.OK)
        {
          setActivePage(lastEditorPage);
        }
      }
      else if (diagnostic.getSeverity() != Diagnostic.OK)
      {
        ProblemEditorPart problemEditorPart = new ProblemEditorPart();
        problemEditorPart.setDiagnostic(diagnostic);
        problemEditorPart.setMarkerHelper(this.markerHelper);
        try
        {
          addPage(++lastEditorPage, problemEditorPart, getEditorInput());
          setPageText(lastEditorPage, problemEditorPart.getPartName());
          setActivePage(lastEditorPage);
        }
        catch (PartInitException exception)
        {
          Activator.logException( exception );        
          
        }
      }

      if (this.markerHelper.hasMarkers(this.editingDomain.getResourceSet()))
      {
        this.markerHelper.deleteMarkers(this.editingDomain.getResourceSet());
        if (diagnostic.getSeverity() != Diagnostic.OK)
        {
          try
          {
            this.markerHelper.createMarkers(diagnostic);
          }
          catch (CoreException exception)
          {
            Activator.logException( exception );            
          }
        }
      }
    }
  }
  
 
    
   protected EContentAdapter problemIndicationAdapter = 
    new EContentAdapter()
    {
      @Override
      public void notifyChanged(final Notification notification)
      {
        if (notification.getNotifier() instanceof Resource)
        {
          switch (notification.getFeatureID(Resource.class))
          {
            case Resource.RESOURCE__IS_LOADED:
            case Resource.RESOURCE__ERRORS:
            case Resource.RESOURCE__WARNINGS:
            {
              Resource resource = (Resource)notification.getNotifier();
              Diagnostic diagnostic = analyzeResourceProblems((Resource)notification.getNotifier(), null);
              if (diagnostic.getSeverity() != Diagnostic.OK)
              {
                JsdlMultiPageEditor.this.resourceToDiagnosticMap.put(resource, diagnostic);
              }
              else
              {
                JsdlMultiPageEditor.this.resourceToDiagnosticMap.remove(resource);
              }

              if (JsdlMultiPageEditor.this.updateProblemIndication)
              {
                getSite().getShell().getDisplay().asyncExec
                  (new Runnable()
                   {
                     public void run()
                     {
                       updateProblemIndication();
                     }
                   });
              }
            }
          }
        }
        else
        {
          super.notifyChanged(notification);
        }
      }

      @Override
      protected void setTarget(final Resource target)
      {
        basicSetTarget(target);
      }

      @Override
      protected void unsetTarget(final Resource target)
      {
        basicUnsetTarget(target);
      }
    };
  
  
    
    /*
     * Responsible for de-serializing the model from the resource file.
     * The resource is passed to the getResourceRoot method.     * 
     * 
     */
  public void getJsdlModel(){
    
    // Assumes that the input is a file object.
    //
    IFileEditorInput modelFile = (IFileEditorInput)getEditorInput();
    URI resourceURI = URI.createPlatformResourceURI(modelFile.getFile().getFullPath().toString(), false);
    Exception exception = null;
    Resource resource = null;
    try
    {
      // Load the resource through the editing domain.
      //
      resource = this.editingDomain.getResourceSet().getResource(resourceURI, true);
    }
    catch (Exception e)
    {
      exception = e;
      resource = this.editingDomain.getResourceSet().getResource(resourceURI, false);
    }

    Diagnostic diagnostic = analyzeResourceProblems(resource, exception);
    if (diagnostic.getSeverity() != Diagnostic.OK)
    {
      this.resourceToDiagnosticMap.put(resource,  analyzeResourceProblems(resource, exception));
    }
      this.editingDomain.getResourceSet().eAdapters().add(this.problemIndicationAdapter);

    getResourceRoot(resource); 
    
    // This means the file was edited from an external editor so
    // push the new JSDL model to the pages.
    if (!this.changedResources.isEmpty()){      
      this.refreshedModel = true;
      pushContentToPages();
      this.refreshedModel = false;
    }
    
  }
    
  
  /*
   This method parses the resource in order to find which JSDL types
   appear. Each JSDL type is then passed as a reference parameter (EList) in the 
   appropriate page of the JSDL editor.   
  */
  private void getResourceRoot(final Resource resource){
   
    // Get an iterator to iterate through all contents of the resource.
    TreeIterator iterator = resource.getAllContents();
    
     while ( iterator.hasNext (  )  )  {  

       EObject testType = (EObject) iterator.next();           

       // Instaceof checks for each EObject that appears in the resource.
      
       if (testType instanceof JobDefinitionType){         
         this.jobDefType = (JobDefinitionType) testType;
       }       
       else {
         //do nothing
       }
     
      }
         
     } 
  
   

     /**
       * This looks up a string in plugin.properties, making a substitution.
       * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
       * @generated
       */
         
 
  // Returns a dignostic describing the errors and warnings listed in the resource
  // and the specified exception
  public Diagnostic analyzeResourceProblems(final Resource resource, final Exception exception) 
      {
        if (!resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty())
        {
          BasicDiagnostic basicDiagnostic =
            new BasicDiagnostic
              (Diagnostic.ERROR,
               "eu.geclipse.ui", //$NON-NLS-1$
               0,
               Messages.getString("JsdlMultiPageEditor.CreateModelErrorMessage"), 
               new Object [] { exception == null ? (Object)resource : exception });
          basicDiagnostic.merge(EcoreUtil.computeDiagnostic(resource, true));
          return basicDiagnostic;
        }
        else if (exception != null)
        {
          return
            new BasicDiagnostic
              (Diagnostic.ERROR,
               "eu.geclipse.ui", //$NON-NLS-1$
               0,
               Messages.getString("JsdlMultiPageEditor.CreateModelErrorMessage"), 
               new Object[] { exception });
        }
        else
        {
          return Diagnostic.OK_INSTANCE;
        }
      }
  
  
  public EditingDomain getEditingDomain()
  {
    return this.editingDomain;
  }
  
  protected boolean isPersisted(final Resource resource)
  {
    boolean result = false;
    try
    {
      InputStream stream = this.editingDomain.getResourceSet().getURIConverter().createInputStream(resource.getURI());
      if (stream != null)
        

      {
        result = true;
        stream.close();
      }
    }
    catch (IOException e)
    {
      //
    }
    return result;
  }
  
  // Method triggered when there are changes between the form pages.
    @Override
    protected void pageChange(final int pageIndex)
     {      
        super.pageChange(pageIndex);        
        IProgressMonitor progressMonitor = new NullProgressMonitor();
        doSave(progressMonitor);     
     }
    
    
    public class ReverseAdapterFactoryContentProvider extends AdapterFactoryContentProvider 
    {
        public ReverseAdapterFactoryContentProvider(final AdapterFactory adapterFactory) {
            super(adapterFactory);
        }

        public Object [] getElements(final Object object) {
            Object parent = super.getParent(object);
            return (parent == null ? Collections.EMPTY_SET : Collections.singleton(parent)).toArray();
        }

        public Object [] getChildren(final Object object) {
            Object parent = super.getParent(object);
            return (parent == null ? Collections.EMPTY_SET : Collections.singleton(parent)).toArray();
        }

        public boolean hasChildren(final Object object) {
            Object parent = super.getParent(object);
            return parent != null;
        }

        public Object getParent(final Object object) {
            return null;
        }
    }
    
  
} // End Class
