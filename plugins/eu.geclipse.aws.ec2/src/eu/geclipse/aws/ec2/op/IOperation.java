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

package eu.geclipse.aws.ec2.op;

/**
 * An {@link IOperation} is a worker Runnable, which fulfills some task in its
 * {@link Runnable#run()} and afterwards stores the result to be later retried
 * by {@link #getResult()}. In the case of an error or an exception, which
 * might come up during the execution, the {@link IOperation} stores this
 * {@link Exception} which can afterwards be obtained by a call to
 * {@link #getException()}.
 * <p>
 * No assumption is made about the context in which the {@link IOperation} is
 * executed but usually the invocation in a {@link Thread} is a common scenario.
 * The Implementation should consider this circumstance.
 * <p>
 * <b>Classes implementing this interface should ensure to cleanup any resources
 * so that the instance of the {@link IOperation} can be rerun.</b>
 * 
 * @author Moritz Post
 * @see OperationExecuter
 */
public interface IOperation extends Runnable {

  /**
   * Returns the result of the operation executed in the {@link Runnable#run()}
   * method of this {@link IOperation}.
   * 
   * @return the result of the operation or <code>null</code> if an error
   *         occurred
   */
  public Object getResult();

  /**
   * Returns any exception which came up during the execution of the
   * {@link Runnable#run()} method.
   * 
   * @return the exception which might have come up or <code>null</code> if no
   *         error occurred
   */
  public Exception getException();
}
