/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.aws.s3.IS3Problems;
import eu.geclipse.aws.s3.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * An abstract implementation of the {@link IObjectActionDelegate}. It provides
 * a convenience implementation of the
 * {@link #extractAWSVoFromCategory(IAction, ISelection)} method which extracts
 * the {@link AWSVirtualOrganization} from the underlying {@link IGridProject}.
 * Additionally the {@link #getWorkbenchPart()} stores the provided
 * {@link IWorkbenchPart} for later usage.
 * 
 * @author Moritz Post
 */
public abstract class AbstractAWSProjectAction implements IObjectActionDelegate
{

  /** The {@link IWorkbenchPart} to use for opening the dialog. */
  private IWorkbenchPart workbenchPart;

  /**
   * The {@link AWSVirtualOrganization} associated with the category that
   * invoked the action.
   */
  private AWSVirtualOrganization awsVo;

  abstract public void run( final IAction action );

  public void setActivePart( final IAction action,
                             final IWorkbenchPart targetPart )
  {
    this.workbenchPart = targetPart;
  }

  /**
   * If the selection was {@link Activator} category, this method extracts the
   * {@link AWSVirtualOrganization} from it.
   * <p>
   * The VO can be obtained by calling {@link #getAwsVo()}.
   * 
   * @param selectedCategory the selected category
   */
  public void extractAWSVoFromCategory( final ISelection selectedCategory ) {
    if( selectedCategory instanceof IStructuredSelection ) {
      IStructuredSelection strucSel = ( IStructuredSelection )selectedCategory;

      Object firstElement = strucSel.getFirstElement();

      if( firstElement instanceof IGridElement ) {
        IGridElement resourceCat = ( IGridElement )firstElement;

        IGridProject project = resourceCat.getProject();

        // if the selection is not a category the project might be null
        if( project != null ) {
          Object vo = project.getVO();

          if( vo instanceof IWrappedElement ) {
            IWrappedElement wrappedElement = ( IWrappedElement )vo;
            vo = wrappedElement.getWrappedElement();
          }
          if( vo instanceof AWSVirtualOrganization ) {
            AWSVirtualOrganization awsVo = ( AWSVirtualOrganization )vo;
            this.awsVo = awsVo;
          }
        }
      }
    }
  }

  /**
   * Traverses the grid model up to the root from the given {@link IGridElement}
   * by using {@link IGridElement#getParent()}. If any of the parents is an
   * {@link AWSVirtualOrganization} or an {@link IGridProject} containing a
   * (wrapped) AWS VO, the field {@link #awsVo} is set to this vo.
   * <p>
   * The found VO can be obtained by calling {@link #getAwsVo()}.
   * 
   * @param gridElement the {@link IGridElement} to act as the origin of the
   *          search
   */
  protected void extractAWSVoFromGridElement( final IGridElement gridElement ) {
    if( gridElement == null ) {
      this.awsVo = null;
    } else if( gridElement instanceof AWSVirtualOrganization ) {
      this.awsVo = ( AWSVirtualOrganization )gridElement;
    } else if( gridElement instanceof IGridProject ) {
      IGridProject gridProject = ( IGridProject )gridElement;
      extractAWSVoFromGridElement( gridProject.getVO() );
    } else if( gridElement instanceof IWrappedElement ) {
      IWrappedElement wrappedVo = ( IWrappedElement )gridElement;
      extractAWSVoFromGridElement( wrappedVo.getWrappedElement() );
    } else {
      extractAWSVoFromGridElement( gridElement.getParent() );
    }
  }

  /**
   * @return the workbenchPart
   */
  protected IWorkbenchPart getWorkbenchPart() {
    return this.workbenchPart;
  }

  /**
   * @return the awsVo
   */
  protected AWSVirtualOrganization getAwsVo() {
    return this.awsVo;
  }

  /**
   * Creates a new {@link EC2ProblemException} from the given {@link Throwable}
   * and displays it along side the description on a {@link ProblemDialog}.
   * 
   * @param throwable the reason for the exception
   * @param description a description of the problem
   */
  protected void processException( final Throwable throwable,
                                   final String description )
  {
    Throwable cause = throwable.getCause();
    if( cause == null ) {
      cause = throwable;
    }
    final ProblemException exception = new ProblemException( IS3Problems.S3_INTERACTION,
                                                             cause.getLocalizedMessage(),
                                                             throwable,
                                                             Activator.PLUGIN_ID );

    Display display = PlatformUI.getWorkbench().getDisplay();
    display.asyncExec( new Runnable() {

      public void run() {
        IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow();
        ProblemDialog.openProblem( workbenchWindow.getShell(),
                                   Messages.getString("AbstractAWSProjectAction.s3error_message"), //$NON-NLS-1$
                                   description,
                                   exception );
      }
    } );
  }
}
