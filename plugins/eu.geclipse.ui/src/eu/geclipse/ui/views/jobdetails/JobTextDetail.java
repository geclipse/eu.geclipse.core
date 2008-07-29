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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.core.model.IGridJob;

/**
 * Detail of Job, which show value using simple text
 */
abstract public class JobTextDetail extends JobDetail {

  private final String name;
  private Label descriptionLabel;
  private Text detailText;

  /**
   * @param section in which details will be shown
   * @param name detail name
   */
  public JobTextDetail( final IJobDetailsSection section, final String name ) {
    super( section );
    this.name = name;
  }

  /**
   * Gets detail value from passed gridJob
   * @param gridJob job, from which detail should be obtained 
   * @return detail value, which should be shown for user 
   */
  abstract protected String getValue( final IGridJob gridJob );

  @Override
  protected void createWidgets( final Composite parent,
                                final FormToolkit formToolkit )
  {
    this.descriptionLabel = formToolkit.createLabel( parent, this.name + ":" ); //$NON-NLS-1$
    GridData layoutData = new GridData();
    layoutData.verticalAlignment = SWT.TOP;
    this.descriptionLabel.setLayoutData( layoutData );
    // we don't want borders, so we creates Text widget directly without formToolkit help
    this.detailText = new Text( parent, SWT.READ_ONLY | SWT.MULTI );
    GridData gridData = new GridData();
    gridData.widthHint = 250;
    gridData.horizontalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    this.detailText.setLayoutData( gridData );
  }

  @Override
  protected boolean isWidgetCreated() {
    return this.descriptionLabel != null && !this.descriptionLabel.isDisposed();
  }

  @Override
  protected boolean refresh( final IGridJob gridJob,
                             final IViewConfiguration viewConfiguration )
  {
    String detail = null;
    if( gridJob != null ) {
      detail = getValue( gridJob );
    }
    if( detail == null ) {
      this.detailText.setText( "" ); //$NON-NLS-1$
      setVisible( viewConfiguration.isShowEmptyEnabled() );
    } else {
      this.detailText.setText( detail );
      setVisible( true );
    }
    return detail != null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.jobdetails.IJobDetail#getId()
   */
  public String getId() {
    return this.name;    // as id just return name
  }  

  public void reuseWidgets( final IJobDetail oldDetail ) {
    JobTextDetail textDetail = ( JobTextDetail )oldDetail;
    this.descriptionLabel = textDetail.descriptionLabel;
    this.detailText = textDetail.detailText;
  }

  public void dispose() {
    dispose( this.descriptionLabel );
    dispose( this.detailText );
    this.descriptionLabel = null;
    this.detailText = null;
  }
  
  protected void dispose( final Widget widget) {
    if( widget != null ) {
      if( !widget.isDisposed() ) {
        widget.dispose();
      }      
    }
  }

  protected void setVisible( final boolean visible ) {
    setVisible( this.descriptionLabel, visible );
    setVisible( this.detailText, visible );
  }

  
  /**
   * @return the descriptionLabel
   */
  protected Label getDescriptionLabel() {
    return this.descriptionLabel;
  }

  
  /**
   * @return the detailText
   */
  protected Text getDetailText() {
    return this.detailText;
  }  
  
  /**
   * @return the name
   */
  protected String getName() {
    return this.name;
  }
}
