package eu.geclipse.aws.ec2.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2Instance;

public class CopyHostname implements IObjectActionDelegate {

	/** The {@link IWorkbenchPart} to use for opening the dialog. */
	private IWorkbenchPart workbenchPart;

	/** The list of selected {@link EC2Instance}s. */
	private List<EC2Instance> instanceList;

	/**
		   * 
		   */
	public CopyHostname() {
		instanceList = new ArrayList<EC2Instance>();
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.workbenchPart = targetPart;

	}

	public void run(IAction action) {

		String hostname = null;
		for (int i = 0; i < instanceList.size(); i++) {
			EC2Instance inst = instanceList.get(i);
			hostname = inst.getHostName();
		}

		// create the clipboard and put the hostname!

		Display dis = Display.getCurrent();
		Clipboard cb = new Clipboard(dis);
		cb.clearContents();
		TextTransfer textTransfer = TextTransfer.getInstance();

		if (hostname.equals("") || hostname == null) {
			hostname = "NO HOSTNAME AT THIS MOMENT IN TIME";
		}
		cb.setContents(new Object[] { hostname },
				new Transfer[] { textTransfer });
	}

	public void selectionChanged(IAction action, ISelection selection) {
		boolean enable = false;
		this.instanceList.clear();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object element : structuredSelection.toList()) {
				if (element instanceof EC2Instance) {
					EC2Instance ec2Instance = (EC2Instance) element;
					this.instanceList.add(ec2Instance);
				}
			}
		}
		if (this.instanceList.size() > 0) {
			enable = true;
		}
		action.setEnabled(enable);
	}
}
