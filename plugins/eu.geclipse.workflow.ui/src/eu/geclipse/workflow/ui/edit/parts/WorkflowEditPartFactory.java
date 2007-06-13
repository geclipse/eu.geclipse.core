/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;

/**
 * @generated
 */
public class WorkflowEditPartFactory implements EditPartFactory {

  /**
   * @generated
   */
  public EditPart createEditPart( EditPart context, Object model ) {
    if( model instanceof View ) {
      View view = ( View )model;
      switch( WorkflowVisualIDRegistry.getVisualID( view ) ) {
        case WorkflowEditPart.VISUAL_ID:
          return new WorkflowEditPart( view );
        case WorkflowJobEditPart.VISUAL_ID:
          return new WorkflowJobEditPart( view );
        case WorkflowJobNameEditPart.VISUAL_ID:
          return new WorkflowJobNameEditPart( view );
        case OutputPortEditPart.VISUAL_ID:
          return new OutputPortEditPart( view );
        case InputPortEditPart.VISUAL_ID:
          return new InputPortEditPart( view );
        case LinkEditPart.VISUAL_ID:
          return new LinkEditPart( view );
      }
    }
    return createUnrecognizedEditPart( context, model );
  }

  /**
   * @generated
   */
  private EditPart createUnrecognizedEditPart( EditPart context, Object model )
  {
    // Handle creation of unrecognized child node EditParts here
    return null;
  }

  /**
   * @generated
   */
  public static CellEditorLocator getTextCellEditorLocator( ITextAwareEditPart source )
  {
    if( source.getFigure() instanceof WrapLabel )
      return new TextCellEditorLocator( ( WrapLabel )source.getFigure() );
    else {
      return new LabelCellEditorLocator( ( Label )source.getFigure() );
    }
  }
  /**
   * @generated
   */
  static private class TextCellEditorLocator implements CellEditorLocator {

    /**
     * @generated
     */
    private WrapLabel wrapLabel;

    /**
     * @generated
     */
    public TextCellEditorLocator( WrapLabel wrapLabel ) {
      this.wrapLabel = wrapLabel;
    }

    /**
     * @generated
     */
    public WrapLabel getWrapLabel() {
      return wrapLabel;
    }

    /**
     * @generated
     */
    public void relocate( CellEditor celleditor ) {
      Text text = ( Text )celleditor.getControl();
      Rectangle rect = getWrapLabel().getTextBounds().getCopy();
      getWrapLabel().translateToAbsolute( rect );
      if( getWrapLabel().isTextWrapped()
          && getWrapLabel().getText().length() > 0 )
      {
        rect.setSize( new Dimension( text.computeSize( rect.width, SWT.DEFAULT ) ) );
      } else {
        int avr = FigureUtilities.getFontMetrics( text.getFont() )
          .getAverageCharWidth();
        rect.setSize( new Dimension( text.computeSize( SWT.DEFAULT, SWT.DEFAULT ) ).expand( avr * 2,
                                                                                            0 ) );
      }
      if( !rect.equals( new Rectangle( text.getBounds() ) ) ) {
        text.setBounds( rect.x, rect.y, rect.width, rect.height );
      }
    }
  }
  /**
   * @generated
   */
  private static class LabelCellEditorLocator implements CellEditorLocator {

    /**
     * @generated
     */
    private Label label;

    /**
     * @generated
     */
    public LabelCellEditorLocator( Label label ) {
      this.label = label;
    }

    /**
     * @generated
     */
    public Label getLabel() {
      return label;
    }

    /**
     * @generated
     */
    public void relocate( CellEditor celleditor ) {
      Text text = ( Text )celleditor.getControl();
      Rectangle rect = getLabel().getTextBounds().getCopy();
      getLabel().translateToAbsolute( rect );
      int avr = FigureUtilities.getFontMetrics( text.getFont() )
        .getAverageCharWidth();
      rect.setSize( new Dimension( text.computeSize( SWT.DEFAULT, SWT.DEFAULT ) ).expand( avr * 2,
                                                                                          0 ) );
      if( !rect.equals( new Rectangle( text.getBounds() ) ) ) {
        text.setBounds( rect.x, rect.y, rect.width, rect.height );
      }
    }
  }
}
