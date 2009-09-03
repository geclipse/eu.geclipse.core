package eu.geclipse.traceview.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ITrace;

class EventMarkerEntry {

  String id;
  boolean enabled;
  String label;
  IEventMarker marker;
  int priority = Integer.MAX_VALUE;
}

public class EventMarkers {

  protected List<EventMarkerEntry> eventMarkerEntries;
  protected List<IEventMarker> eventMarkers;

  public EventMarkers( final ITrace trace ) {
    if( this.eventMarkerEntries == null ) {
      this.eventMarkerEntries = new ArrayList<EventMarkerEntry>();
      for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
        .getConfigurationElementsFor( "eu.geclipse.traceview.EventMarker" ) ) { //$NON-NLS-1$
        try {
          EventMarkerEntry entry = new EventMarkerEntry();
          entry.marker = ( IEventMarker )configurationElement.createExecutableExtension( "class" ); //$NON-NLS-1$
          entry.id = configurationElement.getAttribute( "id" ); //$NON-NLS-1$
          entry.label = configurationElement.getAttribute( "label" ); //$NON-NLS-1$
          entry.enabled = true;
          String priority = configurationElement.getAttribute( "priority" ); //$NON-NLS-1$
          if( priority != null ) {
            entry.priority = Integer.parseInt( priority );
          }
          String defaultEnabled = configurationElement.getAttribute( "defaultEnabled" ); //$NON-NLS-1$
          if( defaultEnabled != null ) {
            entry.enabled = Boolean.parseBoolean( defaultEnabled );
          }
          entry.marker.setTrace( trace );
          boolean added = false;
          for( int i = 0; i < this.eventMarkerEntries.size(); i++ ) {
            if( entry.priority < this.eventMarkerEntries.get( i ).priority ) {
              this.eventMarkerEntries.add( i, entry );
              added = true;
              break;
            }
          }
          if( !added ) {
            this.eventMarkerEntries.add( entry );
          }
        } catch( CoreException coreException ) {
          Activator.logException( coreException );
        }
      }
    }
    buildEventMarkersList();
  }

  public void buildEventMarkersList() {
    this.eventMarkers = new ArrayList<IEventMarker>();
    for( EventMarkerEntry entry : this.eventMarkerEntries ) {
      if( entry.enabled ) {
        this.eventMarkers.add( entry.marker );
      }
    }
  }

  public List<IEventMarker> getEventMarkers() {
    return this.eventMarkers;
  }

  public List<EventMarkerEntry> getEventMarkerEntries() {
    return this.eventMarkerEntries;
  }
}
