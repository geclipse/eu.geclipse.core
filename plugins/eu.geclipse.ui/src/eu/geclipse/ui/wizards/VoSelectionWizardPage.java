/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.ExtPointWizardSelectionListPage;

public class VoSelectionWizardPage extends WizardPage {
  
  private List voList;
  
  private Text infoText;
  
  private boolean allowMultiSelection;
  
  private Class< ? > voType;
  
  public VoSelectionWizardPage( final boolean allowMultiSelection ) {
    this( allowMultiSelection, null );
  }
  
  public VoSelectionWizardPage( final boolean allowMultiSelection,
                                final Class< ? > voType ) {
    super( "voPage", //$NON-NLS-1$
           "VO Selection Page",
           null );
    setDescription( "Specify the VO that should be used" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newtoken_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    this.allowMultiSelection = allowMultiSelection;
    this.voType = voType;
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    mainComp.setLayoutData( gData );
    
    Group voGroup = new Group( mainComp, SWT.SHADOW_OUT );
    voGroup.setLayout( new GridLayout(1, false ) );
    voGroup.setText( "&Available VOs" );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.heightHint = 120;
    voGroup.setLayoutData( gData );
    
    int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
    if ( this.allowMultiSelection ) {
      style |= SWT.MULTI;
    } else {
      style |= SWT.SINGLE;
    }
    
    this.voList = new List( voGroup, style );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.voList.setLayoutData( gData );
    this.voList.addSelectionListener( new SelectionAdapter() {
      
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        showSelectedInfo();
        IVirtualOrganization[] selectedVo = getSelectedVos();
        setPageComplete( selectedVo != null );
      }
      
      @Override
      public void widgetDefaultSelected( final SelectionEvent e ) {
        getWizard().getContainer().showPage( getNextPage() );
      }
      
    } );
    
    Button newButton = new Button( voGroup, SWT.PUSH );
    newButton.setText( "New &VO..." );
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    gData.verticalAlignment = GridData.BEGINNING;
    newButton.setLayoutData( gData );
    newButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        createNewVO();
        updateVoList();
        showSelectedInfo();
        setPageComplete( getSelectedVos() != null );
      }
    } );
    
    Group infoGroup = new Group( mainComp, SWT.SHADOW_OUT );
    infoGroup.setLayout( new GridLayout(1, false ) );
    infoGroup.setText( "&Info about selected VO" );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.heightHint = 120;
    infoGroup.setLayoutData( gData );
    
    this.infoText = new Text( infoGroup, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
    this.infoText.setEditable( false );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.infoText.setLayoutData( gData );
    
    updateVoList();
    showSelectedInfo();
    setPageComplete( getSelectedVos() != null );
    
    setControl( mainComp );
    
  }
  
  public IVirtualOrganization[] getSelectedVos() {
    
    java.util.List< IVirtualOrganization > selectedVos = new ArrayList< IVirtualOrganization >();
    String[] selection = this.voList.getSelection();
    
    if ( ( selection != null ) && ( selection.length != 0 ) ) {
      IVoManager voManager = GridModel.getVoManager();
      for ( String voName : selection ) {
        IVirtualOrganization vo
          = ( IVirtualOrganization ) voManager.findChild( voName );
        if ( vo != null ) {
          selectedVos.add( vo );
        }
      }
    }
    
    if ( selectedVos.isEmpty() ) {
      setErrorMessage( "No valid VO is selected" );
    } else {
      setErrorMessage( null );
    }
    
    return
      selectedVos.isEmpty()
      ? null
      : selectedVos.toArray( new IVirtualOrganization[ selectedVos.size() ] );
    
  }
  
  protected void createNewVO() {
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newtoken_wiz.gif" ); //$NON-NLS-1$
    Wizard wizard = new Wizard() {
      @Override
      public void addPages() {
        ExtPointWizardSelectionListPage page = new ExtPointWizardSelectionListPage(
            "pagename",
            "eu.geclipse.ui.newVoWizards",
            "Create a new VO",
            "Create a new Virtual Organization of the selected type.",
            "No VO providers registered." );
        addPage( page );
      }
      
      @Override
      public boolean performFinish() {
        return false;
      }
    };
    wizard.setForcePreviousAndNextButtons( true );
    wizard.setNeedsProgressMonitor( true );
    wizard.setWindowTitle( "Create a new VO" );
    wizard.setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    WizardDialog dialog = new WizardDialog( this.getShell(), wizard );
    if ( dialog.open() == Window.OK ) {
      try {
        GridModel.getVoManager().saveElements();
      } catch( GridModelException gmExc ) {
        setErrorMessage( "Error while saving new element: "
                         + gmExc.getLocalizedMessage() );
      }
    }
  }
  
  protected void showSelectedInfo() {
    IVirtualOrganization[] vos = getSelectedVos();
    if ( vos != null ) {
      try {
        String text = new String();
        text += "Name:\n\t" + vos[0].getName() + '\n';
        text += "Type:\n\t" + vos[0].getTypeName() + '\n';
        IGridInfoService infoService = vos[0].getInfoService();
        if ( infoService != null ) {
          text += "\nInformation Service:\n\tName:\n\t\t" + infoService.getName();
          text += "\n\tURI:\n\t\t" + infoService.getURI().toString(); 
        }
        IGridService[] services = vos[0].getServices();
        text += "\n\nOther Services:";
        if ( ( services != null ) && ( services.length > 1 ) ) {
          for ( IGridService service : services ) {
            if ( service != infoService ) {
              text += "\n\tName:\n\t\t" + service.getName();
              text += "\n\tURI:\n\t\t" + service.getURI().toString();
            }
          }
        } else {
          text += "\n\tNone";
        }
        this.infoText.setText( text );
      } catch ( GridModelException gmExc ) {
        NewProblemDialog.openProblem( getShell(),
                                      "VO info problem",
                                      "Unable to query services for VO " + vos[0].getName(),
                                      gmExc );
      }
    } else {
      this.infoText.setText( "" ); //$NON-NLS-1$
    }
  }
  
  protected void updateVoList() {
    this.voList.removeAll();
    IVoManager manager = GridModel.getVoManager();
    try {
      IGridElement[] vos = manager.getChildren( null );
      java.util.List< String > nameList = new ArrayList< String >();
      for ( IGridElement vo : vos ) {
        if ( ( this.voType == null ) || this.voType.isAssignableFrom( vo.getClass() ) ) {
          nameList.add( vo.getName() );
        }
      }
      Collections.sort( nameList );
      for ( String name : nameList ) {
        this.voList.add( name );
      }
      IGridElement defaultVo = manager.getDefault();
      if ( ( defaultVo != null ) && ( ( this.voType == null ) || voType.isAssignableFrom( defaultVo.getClass() ) ) ) {
        this.voList.setSelection( new String[] { defaultVo.getName() } );
      } else if ( this.voList.getItemCount() > 0 ){
        this.voList.setSelection( 0 );
      }
    } catch ( GridModelException gmExc ) {
      NewProblemDialog.openProblem( getShell(),
                                    "VO list problem",
                                    "Unable to query registered VOs",
                                    gmExc );
    }
  }
  
}
