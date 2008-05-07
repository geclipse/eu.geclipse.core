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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.jobdetails;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.ide.IDE;

import eu.geclipse.core.model.IGridJob;
import eu.geclipse.ui.internal.Activator;



/**
 * Job detail for showing complex values.
 * {@link JobDetailsView} show only simple form of complex value.
 * Whole value is shown in editor after pressing button 
 */
abstract public class JobComplexDetail extends JobTextDetail {
  private static Image image;
  String editorId;
  private Button button;
  private Composite composite;
  
  
  /**
   * @param section the section, in which detail will be shown 
   * @param name the detail name
   * @param editorId id of editor, which should be opened after pressing "Show" button
   */
  public JobComplexDetail( final IJobDetailsSection section, final String name, final String editorId ) {
    super( section, name );
    this.editorId = editorId;
  }  
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.JobTextDetail#createWidgets(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
   */
  @Override
  protected void createWidgets( final Composite parent, final FormToolkit formToolkit ) {
    super.createWidgets( parent, formToolkit );
    
    this.composite = formToolkit.createComposite( parent );
    GridLayout layout = new GridLayout( 3, false );
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    this.composite.setLayout( layout );
    
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.verticalAlignment = SWT.TOP;        
    this.composite.setLayoutData( gridData );
   
    getDetailText().setParent( this.composite );
    GridData layoutData = (GridData)getDetailText().getLayoutData();
    layoutData.heightHint = 40;    
    
    this.button = createButton( this.composite, formToolkit );            
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.JobTextDetail#dispose()
   */
  @Override
  public void dispose() {
    dispose( this.button );
    dispose( this.composite );
    this.button = null;
    this.composite = null;
    super.dispose();
  }

  private Button createButton( final Composite parent,
                               final FormToolkit formToolkit )
  {
    Button createdButton = formToolkit.createButton( parent, null, SWT.NONE );
    createdButton.setImage( getButtonImage() );
    createdButton.addSelectionListener( new SelectionAdapter() {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
       */
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        try {
          IGridJob inputJob = getInputJob();
          if( inputJob != null ) {
            String value = getValue( inputJob );
            if( value != null ) {
              IDE.openEditor( PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow()
                                .getActivePage(),
                                createEditorInput( inputJob, value ),
                              getEditorId() );
            }
          }
        } catch( PartInitException exception ) {
          Activator.logException( exception );
        }
      }
    } );
    return createdButton;
  }
  
  protected IEditorInput createEditorInput( final IGridJob inputJob, final String value ) {
    return new EditorInput( inputJob, getName(), value );
  }

  private class EditorInput implements IStorageEditorInput {

    private IGridJob inputJob;
    private String detailName;
    private String detailValue;
    private IStorage storage;

    EditorInput( final IGridJob inputJob,
                 final String detailName,
                 final String detailValue )
    {
      super();
      this.detailName = detailName;
      this.inputJob = inputJob;
      this.detailValue = detailValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
      boolean equals = false;
      if( obj instanceof EditorInput ) {
        EditorInput otherInput = ( EditorInput )obj;
        equals = otherInput.inputJob == this.inputJob
                 && otherInput.detailName.equals( this.detailName );
      }
      return equals;
    }
    
    public IStorage getStorage() throws CoreException {
      if( this.storage == null ) {
        this.storage = createStorage( this.detailValue );
        
      }
      return this.storage;
    }

    public boolean exists() {
      return false;
    }

    public ImageDescriptor getImageDescriptor() {
      return ImageDescriptor.getMissingImageDescriptor();
    }

    public String getName() {
      return this.detailName;
    }

    public IPersistableElement getPersistable() {
      return null;
    }

    public String getToolTipText() {
      return String.format( Messages.JobEditedDetail_editorNameFormat, JobComplexDetail.this.getName(), this.inputJob.getName() );
    }

    @SuppressWarnings("unchecked")
    public Object getAdapter( final Class adapter ) {
      Object adaptableObject = null;
      if( adapter.isAssignableFrom( IStorage.class ) ) {
        adaptableObject = this.storage;        
      }
      return adaptableObject;
    }
    
    private IStorage createStorage( final String string ) {
      return new IStorage() {

        public InputStream getContents() throws CoreException {
          return new ByteArrayInputStream( string.getBytes() );
        }

        public IPath getFullPath() {
          return null;
        }

        public String getName() {
          return null;
        }

        public boolean isReadOnly() {
          return true;
        }

        @SuppressWarnings("unchecked")
        public Object getAdapter( final Class adapter ) {
          return null;
        }
      };
    }     
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.JobTextDetail#setVisible(boolean)
   */
  @Override
  protected void setVisible( final boolean visible ) {
    setVisible( this.composite, visible );
    setVisible( this.button, visible );
    super.setVisible( visible );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.JobTextDetail#reuseWidgets(eu.geclipse.ui.views.jobdetails.IJobDetail)
   */
  @Override
  public void reuseWidgets( final IJobDetail oldDetail ) {
    JobComplexDetail detail = (JobComplexDetail)oldDetail;
    this.composite = detail.composite;
    this.button = detail.button;
    super.reuseWidgets( oldDetail );
  }

  private Image getButtonImage() {
    if( image == null ) {
      ImageDescriptor imageDescriptor = Activator.getDefault()
        .getImageRegistry()
        .getDescriptor( Activator.IMG_SEE );
      image = imageDescriptor.createImage();
    }
    return image;
  }

  protected String getEditorId() {
    return JobComplexDetail.this.editorId;
  }
}
