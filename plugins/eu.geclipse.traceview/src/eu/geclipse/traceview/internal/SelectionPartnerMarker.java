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

package eu.geclipse.traceview.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.utils.AbstractEventMarker;

public class SelectionPartnerMarker extends AbstractEventMarker {
  private Set<IEvent> selectedEvents = new HashSet<IEvent>();
  private Set<IEvent> partnerEvents = new HashSet<IEvent>();
  private final Color fgColor = Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
  private final Color bgColor = Display.getDefault().getSystemColor( SWT.COLOR_GRAY );
  
  public SelectionPartnerMarker() {
    ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
    selectionService.addSelectionListener( "eu.geclipse.traceview.views.TraceView", new ISelectionListener() {
      public void selectionChanged( IWorkbenchPart part, ISelection selection ) {
        StructuredSelection sSel = ( StructuredSelection )selection;
        Set<IEvent> newSelectedEvents = new HashSet<IEvent>();
        Set<IEvent> newPartnerEvents = new HashSet<IEvent>();
        for (Object obj : sSel.toList()) {
          if (obj instanceof IEvent) {
            IEvent event = ( IEvent )obj;
            if (event.getProcess().getTrace() == trace) {
              newSelectedEvents.add( event );
              if (event.getPartnerEvent() != null) {
                newPartnerEvents.add( event.getPartnerEvent() );
              }
            }
          }
        }
        SelectionPartnerMarker.this.selectedEvents = newSelectedEvents;
        SelectionPartnerMarker.this.partnerEvents = newPartnerEvents;
      }
    });
  }

  @Override
  public Color getBackgroundColor( final int type ) {
    return bgColor;
  }

  @Override
  public Color getForegroundColor( final int type ) {
    return fgColor;
  }

  @Override
  public int mark( final IEvent event ) {
    int result = 0;
    for (IEvent parEvent : this.partnerEvents) {
      if (parEvent.equals( event )) {
        result = IEventMarker.Ellipse_Event;
        break;
      }
    }
    if (result == 0) {
      if(event.getPartnerEvent() != null &&
         this.selectedEvents.contains( event.getPartnerEvent() ) ) {
        result = IEventMarker.Ellipse_Event;
      }
    }
    return result;
  }
}
