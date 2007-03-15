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
  *      - Emilia Stamou (emstamou@cs.ucy.ac.cy)
  *****************************************************************************/


package eu.geclipse.ui.editors;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.FileEditorInput;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.jsdl.ApplicationType;
import eu.geclipse.jsdl.JobDefinitionType;
import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JobIdentificationType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;
import eu.geclipse.jsdl.posix.provider.PosixItemProviderAdapterFactory;
import eu.geclipse.jsdl.provider.JsdlItemProviderAdapterFactory;
import eu.geclipse.ui.jsdl.editor.pages.JobApplicationPage;
import eu.geclipse.ui.jsdl.editor.pages.JobDefinitionPage;



public class JsdlMultiPageEditor extends FormEditor {
  
  public static final JsdlMultiPageEditor INSTANCE = new JsdlMultiPageEditor();
  
  protected AdapterFactoryEditingDomain editingDomain;
  protected Collection savedResources = new ArrayList();
  protected Map resourceToDiagnosticMap = new LinkedHashMap();
  protected boolean updateProblemIndication = true;
  protected MarkerHelper markerHelper = new EditUIMarkerHelper();
  
  protected ComposedAdapterFactory adapterFactory;
  protected JobDefinitionType jobDefType;
  
  protected ArrayList<EObject> jobDefList = new ArrayList<EObject>();
  protected ArrayList<EObject> applList = new ArrayList<EObject>();
  protected ArrayList<EObject> posixApplList = new ArrayList<EObject>();
  
  public JsdlMultiPageEditor(){
    // Create an adapter factory that yields item providers.
    //
    List factories = new ArrayList();
    factories.add(new ResourceItemProviderAdapterFactory());
    factories.add(new JsdlItemProviderAdapterFactory());
    factories.add(new PosixItemProviderAdapterFactory());
    factories.add(new ReflectiveItemProviderAdapterFactory());

    this.adapterFactory = new ComposedAdapterFactory(factories);

    // Create the command stack that will notify this editor as commands are executed.
    //
    BasicCommandStack commandStack = new BasicCommandStack();
   
    // Create the editing domain with a special command stack.
    //
    this.editingDomain = new AdapterFactoryEditingDomain(this.adapterFactory, commandStack, new HashMap());
    
    
      }

  @Override
  protected void addPages()
  {
    createJsdlModel();
     try {
      addPage(new JobDefinitionPage(this,this.jobDefList,this.editingDomain));
      addPage(new JobApplicationPage(this,this.applList,this.posixApplList));
         }
   catch (PartInitException e) {
    //
   }
    
  }
 

  
  @Override
  public void init(final IEditorSite site, final IEditorInput editorInput){
    setSite(site);
    setInputWithNotify(editorInput);
    setPartName(editorInput.getName());
   
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
        //
        public void execute(final IProgressMonitor monitor)
        {
          // Save the resources to the file system.
          //
          boolean first = true;
          for (Iterator i = JsdlMultiPageEditor.this.editingDomain.getResourceSet().getResources().iterator(); i.hasNext(); )
          {
            Resource resource = (Resource)i.next();
            if ((first || !resource.getContents().isEmpty() || isPersisted(resource)) && !editingDomain.isReadOnly(resource))
            {
              try
              {
                savedResources.add(resource);
                resource.save(Collections.EMPTY_MAP);
              }
              catch (Exception exception)
              {
                resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
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
        doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString()), new FileEditorInput(file));
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
  
  @Override
  protected FormToolkit createToolkit(final Display display) {
    // Create a toolkit that shares colors between editors.
    return new FormToolkit(Activator.getDefault().getFormColors(display));
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
      for (Iterator i = this.resourceToDiagnosticMap.values().iterator(); i.hasNext(); )
      {
        Diagnostic childDiagnostic = (Diagnostic)i.next();
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
  
  
  
    
  
  public void createJsdlModel(){
    
    // Assumes that the input is a file object.
    //
    IFileEditorInput modelFile = (IFileEditorInput)getEditorInput();
    URI resourceURI = URI.createPlatformResourceURI(modelFile.getFile().getFullPath().toString());
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
      resourceToDiagnosticMap.put(resource,  analyzeResourceProblems(resource, exception));
    }
      editingDomain.getResourceSet().eAdapters().add(problemIndicationAdapter);
    
    getImpl(resource);
    
  }
    
  
  /*
   This method parses the resource in order to find which JSDL types
   appear. Each JSDL type is then passed as a reference parameter (EList) in the 
   appropriate page of the JSDL editor.   
  */
  private void getImpl(final Resource resource){
   
    // Get an iterator to iterate through all contents of the resource.
    TreeIterator iterator = resource.getAllContents();
    
     while ( iterator.hasNext (  )  )  {  

       EObject testType = (EObject) iterator.next();           

       // Instaceof checks for each EObject that appears in the resource.
      
       if (testType instanceof JobDefinitionType){
         this.jobDefList.add(testType);
       } else if (testType instanceof JobDescriptionType){
         this.jobDefList.add(testType);
       } else if (testType instanceof JobIdentificationType){
         this.jobDefList.add(testType);
       } else if (testType instanceof ApplicationType){
         this.applList.add(testType);
       }
         else if (testType instanceof POSIXApplicationType){
         this.posixApplList.add(testType);
       }
       else {
         // Do Nothing
       }
     
      }
         
     }  
      
 
  // Returns a dignostic describing the errors and warnings listed in the resource
  // and the specified exception
  //FIXME - Change the Diagnostics ERROR....get plugin properties file.
  public Diagnostic analyzeResourceProblems(final Resource resource, final Exception exception) 
      {
        if (!resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty())
        {
          BasicDiagnostic basicDiagnostic =
            new BasicDiagnostic
              (Diagnostic.ERROR,
               "eu.geclipse.ui", //$NON-NLS-1$
               0,
               Messages.getString("JsdlMultiPageEditor.CreateModelErrorMessage"), //$NON-NLS-1$
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
               Messages.getString("JsdlMultiPageEditor.CreateModelErrorMessage"), //$NON-NLS-1$
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
  
  
    @Override
    protected void pageChange(final int pageIndex)
      {
        super.pageChange(pageIndex);
        IProgressMonitor progressMonitor = new NullProgressMonitor();
        doSave(progressMonitor);
       
      
      }
    
    
  
}
