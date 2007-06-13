/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.view.factories;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.DiagramViewFactory;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class WorkflowViewFactory extends DiagramViewFactory {

  /**
   * @generated
   */
  @Override
  protected List createStyles( View view ) {
    List styles = new ArrayList();
    styles.add( NotationFactory.eINSTANCE.createDiagramStyle() );
    return styles;
  }

  /**
   * @generated
   */
  @Override
  protected MeasurementUnit getMeasurementUnit() {
    return MeasurementUnit.PIXEL_LITERAL;
  }
}
