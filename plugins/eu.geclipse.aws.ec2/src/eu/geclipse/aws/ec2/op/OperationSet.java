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
import java.util.List;

/**
 * An {@link OperationSet} is a container for multiple {@link IOperation}
 * instances. These instances can by of the same type or a mixture of various
 * {@link IOperation} implementation.
 * <p>
 * The underlying data structure is an {@link ArrayList} and therefore not
 * thread safe.
 * 
 * @author Moritz Post
 */
public class OperationSet {

  /** The {@link List} containing the {@link IOperation}s. */
  private List<IOperation> opList;

  /**
   * Create a new {@link OperationSet} and initialize the underlying data
   * structure.
   */
  public OperationSet() {
    this.opList = new ArrayList<IOperation>();
  }

  /**
   * Create a new {@link OperationSet} and initialize the underlying data
   * structure with the {@link IOperation}s contained within the provided
   * {@link List}.
   * 
   * @param ops the {@link List} to initialize this {@link OperationSet} with
   */
  public OperationSet( final List<? extends IOperation> ops ) {
    this();
    for( IOperation operation : ops ) {
      addOp( operation );
    }
  }

  /**
   * Create a new {@link OperationSet} and initialize the underlying data
   * structure with the {@link IOperation}s contained within the provided
   * array.
   * 
   * @param ops the array to initialize this {@link OperationSet} with
   */
  public OperationSet( final IOperation[] ops ) {
    this();
    for( IOperation operation : ops ) {
      addOp( operation );
    }
  }

  /**
   * Add a new {@link IOperation} to this {@link OperationSet}.
   * 
   * @param op the {@link IOperation} to add
   */
  public void addOp( final IOperation op ) {
    this.opList.add( op );
  }

  /**
   * Remove the given {@link IOperation} from this {@link OperationSet}
   * 
   * @param op the {@link IOperation} to remove
   */
  public void removeOp( final IOperation op ) {
    this.opList.remove( op );
  }

  /**
   * Get a {@link List} of all contained {@link IOperation}s
   * 
   * @return the {@link IOperation} {@link List} to provide
   */
  public List<IOperation> getOps() {
    return this.opList;
  }

}
