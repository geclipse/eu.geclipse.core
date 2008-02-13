package eu.geclipse.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class MasterMonitor implements IProgressMonitor {
  
  private List< IProgressMonitor > slaves;
  
  public MasterMonitor() {
    this( null, null );
  }
  
  public MasterMonitor( final IProgressMonitor slave ) {
    this( slave, null );
  }
  
  public MasterMonitor( final IProgressMonitor slave1, final IProgressMonitor slave2 ) {
    this.slaves = new ArrayList< IProgressMonitor >();
    addSlave( slave1 );
    addSlave( slave2 );
  }
  
  public void addSlave( final IProgressMonitor slave ) {
    if ( ( slave != null ) && ! ( slave instanceof NullProgressMonitor ) ) {
      this.slaves.add( slave );
    }
  }

  public void beginTask( final String name, final int totalWork ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.beginTask( name, totalWork );
    }
  }

  public void done() {
    for ( IProgressMonitor m : this.slaves ) {
      m.done();
    }
  }

  public void internalWorked( final double work ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.internalWorked( work );
    }
  }

  public boolean isCanceled() {
    
    boolean result = false;
    
    for ( IProgressMonitor m : this.slaves ) {
      result |= m.isCanceled();
      if ( result ) {
        break;
      }
    }
    
    if ( result ) {
      for ( IProgressMonitor m : this.slaves ) {
        m.setCanceled( true );
      }
    }
    
    return result;
    
  }

  public void setCanceled( final boolean value ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.setCanceled( value );
    }
  }

  public void setTaskName( final String name ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.setTaskName( name );
    }
  }

  public void subTask( final String name ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.subTask( name );
    }
  }

  public void worked( final int work ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.worked( work );
    }
  }

}
