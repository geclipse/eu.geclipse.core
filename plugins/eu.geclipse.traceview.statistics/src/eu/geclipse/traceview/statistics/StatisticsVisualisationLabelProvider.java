/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.statistics;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.traceview.statistics.chartbuilder.AbstractChartBuilder;
import eu.geclipse.traceview.statistics.providers.IStatistics;

/**
 * Label Provider
 */
public class StatisticsVisualisationLabelProvider implements ILabelProvider {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
   */
  public Image getImage( final Object element ) {
    Image result = null;
    if( element instanceof TreeNode ) {
      TreeNode treeNode = ( TreeNode )element;
      if( treeNode.getValue() instanceof AbstractChartBuilder ) {
        AbstractChartBuilder statistics = ( AbstractChartBuilder )treeNode.getValue();
        result = statistics.getImage();
      } else if( treeNode.getValue() instanceof IStatistics ) {
        IStatistics statistics = ( IStatistics )treeNode.getValue();
        result = statistics.getImage();
      }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
   */
  public String getText( final Object element ) {
    String result = null;
    if( element instanceof TreeNode ) {
      TreeNode treeNode = ( TreeNode )element;
      if( treeNode.getValue() instanceof IStatistics ) {
        IStatistics statistics = ( IStatistics )treeNode.getValue();
        result = statistics.getName();
      } else if( treeNode.getValue() instanceof AbstractChartBuilder ) {
        AbstractChartBuilder statistics = ( AbstractChartBuilder )treeNode.getValue();
        result = statistics.getName();
      }
      // else if (treeNode.getValue() instanceof String){
      //        
      // }
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
   */
  public void addListener( final ILabelProviderListener listener ) {
    // empty
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
   */
  public void removeListener( final ILabelProviderListener listener ) {
    // empty
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
   *      java.lang.String)
   */
  public boolean isLabelProperty( final Object element, final String property )
  {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
   */
  public void dispose() {
    // empty
  }
}
