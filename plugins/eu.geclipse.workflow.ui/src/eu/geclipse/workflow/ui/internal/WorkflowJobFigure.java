/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.internal;


import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
//import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.swt.graphics.Color;

/**
 * Holder of a GEF Figure for a WorkflowJob
 * 
 * @author athandavan
 *
 */
public class WorkflowJobFigure extends RoundedRectangle {

    
    /**
     * The preferred size for this figure
     */
    private static final Dimension SIZE = new Dimension( 100, 100 );


    private Label figWorkflowJobName;
    private Label figWorkflowJobDescription;
//    private WrapLabel figWorkflowJobDescription;

    final static Color THIS_FORE = new Color( null, 220, 220, 240 );
    static final Color JOB_DESC_BACK = new Color( null, 250, 250, 220 );

    /**
     * Constructor; creates the WorkflowJobFigure
     * 
     * Note to developer: FlowLayout does not allow the WrapLabel to wrap its text. 
     */
    public WorkflowJobFigure() {
      ToolbarLayout layoutThis = new ToolbarLayout();
      
      layoutThis.setStretchMinorAxis( false );
      layoutThis.setMinorAlignment( ToolbarLayout.ALIGN_TOPLEFT );
      layoutThis.setSpacing( 10 );
      layoutThis.setVertical( true );
      
      this.setLayoutManager( layoutThis );
      this.setPreferredSize( SIZE );
      this.setCornerDimensions( new Dimension( 15, 15 ) );
//      this.setForegroundColor( THIS_FORE );
      this.setOpaque( true );

      createContents();
    }

    /**
     * Creates the contents of the WorkflowJob figure
     */
    private void createContents() {
      Label workflowJobNameFigure = new Label();
      workflowJobNameFigure.setText( "<...>" );
      this.add( workflowJobNameFigure );
      this.figWorkflowJobName = workflowJobNameFigure;

      Label workflowJobDescFigure = new Label();
      workflowJobDescFigure.setBackgroundColor( JOB_DESC_BACK );
      workflowJobDescFigure.setText( "<..>" );
      workflowJobDescFigure.setEnabled( true );
      workflowJobDescFigure.setFocusTraversable( true );
      workflowJobDescFigure.setVisible( false );
      this.add( workflowJobDescFigure );
      this.figWorkflowJobDescription = workflowJobDescFigure;
      
//      WrapLabel workflowJobDescriptionFigure = new WrapLabel();
//      workflowJobDescriptionFigure.setText( "<..>" );
//      workflowJobDescriptionFigure.setTextWrap( true );
//      workflowJobDescriptionFigure.setTextAlignment( PositionConstants.LEFT );
//      workflowJobDescriptionFigure.setBackgroundColor( JOB_DESC_BACK );
//      workflowJobDescriptionFigure.setOpaque( true );
//      workflowJobDescriptionFigure.setTextWrapWidth( 80 );
//      workflowJobDescriptionFigure.setFocus( true );
//      this.add( workflowJobDescriptionFigure );
//      this.figWorkflowJobDescription = workflowJobDescriptionFigure;
    }


//    private boolean myUseLocalCoordinates = false;


//    protected boolean useLocalCoordinates() {
//      return this.myUseLocalCoordinates;
//    }

    /**
     * @generated
     */
//    protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
//      this.myUseLocalCoordinates = useLocalCoordinates;
//    }

    /**
     * @generated
     */
    public Label getFigureWorkflowJobNameFigure() {
      return this.figWorkflowJobName;
    }

    /**
     * @generated
     */
    public Label getFigureWorkflowJobDescriptionFigure() {
      return this.figWorkflowJobDescription;
    }
    
    /**
     * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
     * @param wHint width hint
     * @param hHint height hint
     * @return Returns the preferred dimensions.
     */
    @Override
    public Dimension getPreferredSize( final int wHint, final int hHint ) {
      return SIZE;
    }

  }