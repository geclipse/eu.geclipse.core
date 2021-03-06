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

import java.net.URL;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.impl.GenericVirtualOrganization;
import eu.geclipse.core.model.impl.GenericVoCreator;
import eu.geclipse.ui.internal.Activator;


/**
 * Wizard page for the {@link GenericVirtualOrganization}.
 */
public class GenericVoWizardPage
    extends WizardPage {
  
  private GenericVirtualOrganization initialVo;
  
  private Text nameText;
  
  /** A pattern for matching the VO name */
  private Pattern voNamePattern = Pattern.compile( "^[\\w.-]+$" ); //$NON-NLS-1$
  
  
  /**
   * Standard constructor.
   */
  public GenericVoWizardPage() {
    super( "genericVOPage", //$NON-NLS-1$
           "Generic VO",
           null );
    setDescription( "Specify the attributes of your VO" );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/vo_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label nameLabel = new Label( mainComp, SWT.NULL );
    nameLabel.setText( "&VO Name:" );
    gData = new GridData();
    nameLabel.setLayoutData( gData );
    
    this.nameText = new Text( mainComp, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.nameText.setLayoutData( gData );
    
    if ( this.initialVo != null ) {
      initVo( this.initialVo );
    }
    
    setControl( mainComp );
    
  }
  
  public IStatus apply( final GenericVoCreator creator ) {
    
    IStatus result = Status.OK_STATUS;
    
    String name = this.nameText.getText();
    if ( ! validateVoName( name ) ) {
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, "You have to specify a valid VO name" );
    } else if ( ( this.initialVo == null )
        && ( GridModel.getVoManager().findChild( name ) != null ) ) {
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, "A VO with the same name already exists" );
    } else {
      creator.setVoName( name );
    }
    
    return result;
    
  }
  
  /**
   * Initializes the controls of this wizard page with the attributes
   * of the specified VO.
   * 
   * @param vo The VO whose attributes should be set to the page's controls.
   */
  protected void initVo( final GenericVirtualOrganization vo ) {
    this.nameText.setText( vo != null ? vo.getName() : "" ); //$NON-NLS-1$
    this.nameText.setEnabled( vo == null );
  }
  
  /**
   * Set the specified VO as initial VO. This means that the controls
   * of the page will be initialized with the attributes of the specified
   * VO.
   * 
   * @param vo The initial VO.
   */
  protected void setInitialVo( final GenericVirtualOrganization vo ) {
    this.initialVo = vo;
  }
  
  /**
   * Helper method to validate the VO name entered by the user.
   * 
   * @param voName the VO name to validate
   * @return true if the VO name matches the allowed pattern
   */
  private boolean validateVoName( final String voName ) {
    return this.voNamePattern.matcher( voName ).matches();
  }

}
