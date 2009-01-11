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

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

import eu.geclipse.aws.ec2.internal.Activator;

/**
 * The {@link OperationExecuter} acts as runner for {@link IOperation} objects.
 * It takes a single {@link IOperation} or a set of {@link IOperation}s
 * embedded in a {@link OperationSet} and runs the corresponding
 * {@link Runnable#run()} method(s).
 * <p>
 * When executing an {@link IOperation}, the main thread is forked and the
 * operation run in its own thread. The same applies to the {@link OperationSet}.
 * Each contained {@link IOperation} is run in its own thread. To sync op with
 * the running {@link IOperation} instances, the methods
 * {@link #execOp(IOperation)} and {@link #execOpGroup(OperationSet)} block
 * until all contained {@link IOperation} objects have terminated (naturally or
 * unnaturally).
 * <p>
 * The implementation is thread safe and can be reused after its initial
 * invocation.
 * <p>
 * TODO: Refactor the implementation of the operation infrastructure to make use
 * of the <code>java.util.concurrent</code> package. Especially the
 * {@link FutureTask}, the {@link ExecutorService} and the
 * {@link CyclicBarrier}.
 * 
 * @author Moritz Post
 * @see IOperation
 */
public class OperationExecuter {

  /** The list of currently managed threads. */
  private ArrayList<Thread> threadList;

  /**
   * Executes the {@link Runnable#run()} method of the given {@link IOperation}
   * in its own Thread and blocks until the execution is complete.
   * 
   * @param op the {@link IOperation} to run
   * @return the {@link IOperation} which has been passed into this method
   */
  public synchronized IOperation execOp( final IOperation op ) {
    this.threadList = new ArrayList<Thread>();
    exec( op );
    waitForOp();
    this.threadList.clear();
    return op;
  }

  /**
   * Executes the {@link Runnable#run()} method of the given {@link IOperation}
   * instances contained within the passed {@link OperationSet}.
   * 
   * @param opGroup the {@link OperationSet} containing the {@link IOperation}s
   *            to run
   * @return the {@link OperationSet} which has been passed into this method
   */
  public synchronized OperationSet execOpGroup( final OperationSet opGroup ) {
    this.threadList = new ArrayList<Thread>();
    for( IOperation op : opGroup.getOps() ) {
      exec( op );
    }
    waitForOp();
    this.threadList.clear();
    return opGroup;
  }

  /**
   * Creates a new {@link Thread} which in turn invokes the
   * {@link Runnable#run()} method of the {@link IOperation} provided.
   * 
   * @param op the {@link IOperation} to run
   */
  private void exec( final IOperation op ) {
    Thread thread = new Thread( op, op.getClass().getName() );
    thread.run();
    this.threadList.add( thread );
  }

  /**
   * Calls {@link Thread#join()} on any thread in the {@link #threadList}
   * managed by this {@link OperationExecuter}.
   */
  private void waitForOp() {
    for( Thread thread : this.threadList ) {
      try {
        thread.join();
      } catch( InterruptedException interruptedExc ) {
        Activator.log( "Interrupted while waiting for Operation on " + thread.getName(), //$NON-NLS-1$
                       interruptedExc );
      }
    }
  }
}
