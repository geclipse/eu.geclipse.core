/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initialia development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;


public class ProblemPage extends FormPage {
  
  protected static final String PAGE_ID = "PROBLEM_PAGE"; //$NON-NLS-1$
  protected Diagnostic diagnostic;
  protected MarkerHelper markerUtil;
  protected Composite body = null;
  protected Label imageLabel = null; 
  protected Button button = null;
  protected Composite problemsSection = null;
  protected TreeViewer problemsViewer = null;
  private final int WIDGET_HEIGHT = 250;
  

  public ProblemPage( final FormEditor editor ) {
    super( editor, PAGE_ID, Messages.getString("ProblemPage_PageTitle") );    
    
  }
  
  
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    
    
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "ProblemsPage_Title" ) );  //$NON-NLS-1$
    
    
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2) );
    
    this.problemsSection = toolkit.createComposite( this.body );
    this.problemsSection.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.problemsSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

    /* Create the Stage-In Section */
    createProblemsSection( this.problemsSection , toolkit );
    
  
  } // end void createFormContent()
  
  
  
  /* This function creates the Problems Section of the Problems Page */
  private void createProblemsSection( final Composite parent,
                                     final FormToolkit toolkit ) {
   
   String sectionTitle = Messages.getString( "ProblemsPage_DiscoverdProblems" ); //$NON-NLS-1$
   String sectionDescription = Messages.getString( "ProblemsPage_ProblemsDescr" );   //$NON-NLS-1$
   
   GridData gd;
      
   Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                          parent,
                                          sectionTitle,
                                          sectionDescription,
                                          3 );
     
   gd = new GridData();
   gd.horizontalSpan = 3;
   gd.grabExcessHorizontalSpace = true;
   gd.grabExcessVerticalSpace = true;   
   gd.horizontalAlignment = GridData.FILL;
   gd.verticalAlignment = GridData.FILL;
   imageLabel = toolkit.createLabel( client, "" );
   imageLabel.setLayoutData( gd );
   
   button = toolkit.createButton( client, "Create Markers", SWT.NONE );
   button.addSelectionListener( new SelectionListener(){

    public void widgetDefaultSelected( final SelectionEvent e ) {
      // TODO Auto-generated method stub
      
    }

    public void widgetSelected( final SelectionEvent e ) {

      createMarkers();
    }
     
   });
   button.setLayoutData( gd );
   
   gd = new GridData();
   gd.grabExcessHorizontalSpace = true;
   gd.grabExcessVerticalSpace = true;   
   gd.horizontalAlignment = GridData.FILL;
   gd.verticalAlignment = GridData.FILL;
   gd.verticalSpan = 3;
   gd.horizontalSpan = 1;
   gd.widthHint = 600;
   gd.heightHint = this.WIDGET_HEIGHT;
   
   //FIXME nloulloud - This is a work-around for the Bug#: 201705 for Windows.
   this.problemsViewer = new TreeViewer( client, SWT.BORDER );     
   this.problemsViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH 
                                                            | GridData.GRAB_VERTICAL 
                                                            | GridData.VERTICAL_ALIGN_BEGINNING));
   
   this.problemsViewer.setContentProvider( new ITreeContentProvider()
   {
     private boolean isRootElement = true;

     public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput )
     {
     }

     public void dispose()
     {
     }

     public Object getParent( final Object element )
     {
       return null;
     }

     public Object[] getElements( final Object inputElement ) {
       
       Object[] result = null;
       
       if (isRootElement) {
         
         isRootElement = false;
         Diagnostic diag = (Diagnostic)inputElement;
         if (diag.getMessage() != null || diag.getException() != null) {
           result = new Object []{ diag };
         }
       }
       else {
         result = getChildren(inputElement);
       }
       return result;
     }

     
     
     public boolean hasChildren(final Object element)
     {
       return !((Diagnostic)element).getChildren().isEmpty();
     }

     public Object[] getChildren(final Object parentElement)
     {
       return ((Diagnostic)parentElement).getChildren().toArray();
     }
   });

 this.problemsViewer.setLabelProvider(new LabelProvider()
   {
     public String getText(final Object element)
     {
       Diagnostic diag = (Diagnostic)element;
       String message = diag.getMessage();
       if (message == null)
       {
         switch (diag.getSeverity())
         {
           case Diagnostic.ERROR:
             message = Messages.getString("ProblemsPage_DiagnosticError_label");
             break;
           case Diagnostic.WARNING:
             message = Messages.getString("ProblemsPage_DiagnosticWarning_label");
             break;
           default:
             message = Messages.getString("ProblemsPage_Diagnostic_label");
             break;
         }
       }
       return message;
     }
   });
 
   this.problemsViewer.getTree().setLayoutData( gd );

   
         
   toolkit.paintBordersFor( client );  
     
 }
  
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ){
      refresh();
    } // end_if active
    
  } //End void setActive()
  
  
  public boolean isSaveAsAllowed() {
    return false;
  }
  
  
  public Diagnostic getDiagnostic() {
    return diagnostic;
  }
  

  
  public void setDiagnostic( final Diagnostic diagnostic ) {
    this.diagnostic = diagnostic;
    refresh();
  }
  
  
  
  protected Image getImage()
  {
    Image image = null;
    Display display = Display.getCurrent();
    switch (diagnostic.getSeverity())
    {
      case Diagnostic.ERROR:
        image = display.getSystemImage(SWT.ICON_ERROR);
      break;
      case Diagnostic.WARNING:
        image = display.getSystemImage(SWT.ICON_WARNING);
      break;
      default:
        image = display.getSystemImage(SWT.ICON_INFORMATION);
      break;
    }
    return image;
  }
  
  
  protected void refresh() {
    
    if (diagnostic != null ) {
      
      Image image = getImage();
      if (image != null && imageLabel != null)
      {
//        this.setTsetTitleImage( image );        
      }
      
      
      if (problemsViewer != null && problemsViewer.getInput() != diagnostic) {
        problemsViewer.setInput(diagnostic);
        
      }
    }
  }
  
  
  protected void createMarkers()
  {
    if (markerUtil != null)
    {
      markerUtil.deleteMarkers(diagnostic);
      if (diagnostic.getSeverity() != Diagnostic.OK)
      {
        try
        {
          markerUtil.createMarkers(diagnostic);
        }
        catch (CoreException exception)
        {
          openErrorDialog(Messages.getString("ProblemsPage__CreateMarkerError_message"), exception);
        }
      }
    }
  }
  
  
  
  public void setMarkerHelper( final MarkerHelper markerHelper ) {
    this.markerUtil = markerHelper;
  }
  
  
  
  protected void openErrorDialog( final String message, final Exception exception ) {
    ErrorDialog.openError(
      Display.getCurrent().getActiveShell(),
      Messages.getString( "ProblemsPage_Error_label" ),
      message,
      exception instanceof CoreException
        ? ((CoreException)exception).getStatus() : BasicDiagnostic.toIStatus(BasicDiagnostic.toDiagnostic(exception)));
  }
  
  
} // end ProblemPage class
