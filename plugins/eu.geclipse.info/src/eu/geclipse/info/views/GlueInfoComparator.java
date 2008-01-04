package eu.geclipse.info.views;

import org.eclipse.jface.viewers.IElementComparer;

import eu.geclipse.info.views.GlueInfoViewer.TreeParent;


public class GlueInfoComparator implements IElementComparer
{

  public boolean equals( final Object a, final Object b ) {
    
    boolean result = false;
    
    if (a != null && a instanceof TreeParent 
        && b!= null && b instanceof TreeParent 
        && ((TreeParent)a).getName().equals( ((TreeParent )b).getName()))
      result = true;
    else if (a != null)
      result =  a.equals( b );
    
    return result;
  }

  public int hashCode( final Object element ) {
    
    return 0;
  }
  
}