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


import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;

/**
 * The GEF Figure for a WorkflowJob
 * 
 * @author athandavan
 *
 */
public class WorkflowJobFigure extends RoundedRectangle {

    
    /**
     * The preferred size for this figure
     */
    private static final Dimension SIZE = new Dimension( 100, 40 );

    private boolean myUseLocalCoordinates = false;

    private Label figWorkflowJobName;

    final static Color THIS_FORE = new Color( null, 220, 220, 240 );
    final static Color JOB_NAME_BACK = new Color( null, 250, 250, 220 );

    /**
     * Default Constructor. This creates the WorkflowJobFigure.
     */
    public WorkflowJobFigure() {
      FlowLayout layoutThis = new FlowLayout();
      
      layoutThis.setStretchMinorAxis( false );
      layoutThis.setMajorAlignment( FlowLayout.ALIGN_CENTER );
      layoutThis.setMinorAlignment( FlowLayout.ALIGN_LEFTTOP );
      layoutThis.setMajorSpacing( 5 );
      
      this.setLayoutManager( layoutThis );
      this.setPreferredSize( SIZE );
      this.setCornerDimensions( new Dimension( 15, 15 ) );
      this.setForegroundColor( THIS_FORE );
      this.setOpaque( true );

      createContents();
    }
 
    
    public WorkflowJobFigure(final String text) {
      FlowLayout layoutThis = new FlowLayout();
      
      layoutThis.setStretchMinorAxis( false );
      layoutThis.setMajorAlignment( FlowLayout.ALIGN_CENTER );
      layoutThis.setMinorAlignment( FlowLayout.ALIGN_LEFTTOP );
      layoutThis.setMajorSpacing( 5 );
      
      this.setLayoutManager( layoutThis );
      this.setPreferredSize( SIZE );
      this.setCornerDimensions( new Dimension( 15, 15 ) );
      this.setForegroundColor( THIS_FORE );
      this.setOpaque( true );

      createContents(text);
    }

    /**
     * Creates the contents of the WorkflowJob figure
     */
    private void createContents() {
      Label workflowJobNameFigure = new Label();
      workflowJobNameFigure.setText( "<...>" );
      workflowJobNameFigure.setBackgroundColor( JOB_NAME_BACK );
      this.add( workflowJobNameFigure );
      this.figWorkflowJobName = workflowJobNameFigure;
    }
    
    /**
     * Creates the contents of the WorkflowJob figure
     */
    private void createContents(final String text) {
      Label workflowJobNameFigure = new Label();
      workflowJobNameFigure.setText( text );
      workflowJobNameFigure.setBackgroundColor( JOB_NAME_BACK );
      this.add( workflowJobNameFigure );
      this.figWorkflowJobName = workflowJobNameFigure;
    }


    @Override
    protected boolean useLocalCoordinates() {
      return this.myUseLocalCoordinates;
    }

    protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
      this.myUseLocalCoordinates = useLocalCoordinates;
    }

    /**
     * @generated
     */
    public Label getFigureWorkflowJobNameFigure() {
      return this.figWorkflowJobName;
    }

//    /**
//     * @generated
//     */
//    public Label getFigureWorkflowJobDescriptionFigure() {
//      return this.figWorkflowJobDescription;
//    }
    
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