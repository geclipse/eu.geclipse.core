package eu.geclipse.ui.internal;

import java.security.cert.X509Certificate;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.security.ICertificateTrustVerifier;
import eu.geclipse.ui.internal.dialogs.CertificateTrustDialog;

public class UICertificateTrustVerifier
    implements ICertificateTrustVerifier {
  
  private static class DialogRunnable implements Runnable {
    
    private X509Certificate[] chain;
    
    private TrustMode result;
    
    public DialogRunnable( final X509Certificate[] chain ) {
      this.chain = chain;
    }
    
    public TrustMode getResult() {
      return this.result;
    }
    
    public void run() {
      
      Display display = Display.getDefault();
      Shell shell = display.getActiveShell();
      
      if ( shell == null ) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        if ( window != null ) {
          shell = window.getShell();
        }
      }
      
      this.result = CertificateTrustDialog.openDialog( shell, this.chain );
      
    }
    
  }

  public TrustMode verifyTrust( final X509Certificate[] chain ) {
    DialogRunnable runnable = new DialogRunnable( chain );
    Display.getDefault().syncExec( runnable );
    return runnable.getResult();
  }

}
