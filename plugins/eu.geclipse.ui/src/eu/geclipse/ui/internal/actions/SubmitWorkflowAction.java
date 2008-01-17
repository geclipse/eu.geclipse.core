package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.IGridWorkflow;

public class SubmitWorkflowAction extends SelectionListenerAction {

	/**
	 * The workbench site this action belongs to.
	 */
	private IWorkbenchSite site;

	private ArrayList<IGridWorkflow> workflow;

	protected SubmitWorkflowAction(final IWorkbenchSite site) {
		super(Messages.getString("SubmitWorkflowAction.title")); //$NON-NLS-1$
		this.site = site;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
//		JobCreatorSelectionWizard wizard = new JobCreatorSelectionWizard(
//				this.workflow, this.workflow);
//		WizardDialog dialog = new WizardDialog(this.site.getShell(), wizard);
//		dialog.open();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		this.workflow = new ArrayList<IGridWorkflow>();
		boolean enabled = super.updateSelection(selection);
		Iterator<?> iter = selection.iterator();
		while (iter.hasNext() && enabled) {
			Object element = iter.next();
			boolean isWorkflowFile = isWorkflow(element);
			enabled &= isWorkflowFile;
			if (isWorkflowFile) {
				this.workflow.add((IGridWorkflow) element);
			}
		}
		return enabled && !this.workflow.isEmpty();
	}

	protected boolean isWorkflow(final Object element) {
		return element instanceof eu.geclipse.core.model.IGridWorkflow;
	}

}
