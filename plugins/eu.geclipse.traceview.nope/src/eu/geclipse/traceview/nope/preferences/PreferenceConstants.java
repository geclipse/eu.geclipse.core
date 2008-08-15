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

package eu.geclipse.traceview.nope.preferences;

/**
 * Nope Event Preferences
 */
public interface PreferenceConstants {

  /**  Vector Clock Preference */
  public static final String vectorClocks = "vector"; //$NON-NLS-1$
  /** Color Preference */
  public static final String color = "color"; //$NON-NLS-1$
  /** Enabled Preference */
  public static final String enabled = "enabled"; //$NON-NLS-1$
  /** Shape Preference */
  public static final String shape = "shape"; //$NON-NLS-1$
  /** Nope Event Sub Type Codes */
  public static final int[] Codes = {
    0x00,
    0x01,
    0x02,
    0x03,
    0x04,
    0xa0,
    0xa1,
    0xa2,
    0xa3,
    0xa4,
    0xa5,
    0xa6,
    0xa7,
    0xa8,
    0xa9,
    0xaa,
    0xab,
    0xac,
    0xad,
    0xae,
    0xaf,
    0xb0,
    0xb1,
    0xb2,
    0xb3,
    0xb4,
    0xb5,
    0xb6,
    0xb7,
    0xb8,
    0xb9,
    0xba,
    0xbb,
    0xbc,
    0xbd,
    0xbe,
    0xbf,
    0xc0,
    0xc1
  };
  /** Nope Event Sub Type Names */
  public static final String[] Names = {
    "NCube_NREAD", //$NON-NLS-1$
    "NCube_NREADP", //$NON-NLS-1$
    "NCube_NWRITE", //$NON-NLS-1$
    "NCube_NWRITEP", //$NON-NLS-1$
    "NCube_NTEST", //$NON-NLS-1$
    "MPI_RECV", //$NON-NLS-1$
    "MPI_IRECV", //$NON-NLS-1$
    "MPI_SEND", //$NON-NLS-1$
    "MPI_BSEND", //$NON-NLS-1$
    "MPI_RSEND", //$NON-NLS-1$
    "MPI_SSEND", //$NON-NLS-1$
    "MPI_ISEND", //$NON-NLS-1$
    "MPI_IBSEND", //$NON-NLS-1$
    "MPI_IRSEND", //$NON-NLS-1$
    "MPI_ISSEND", //$NON-NLS-1$
    "MPI_TEST", //$NON-NLS-1$
    "MPI_WAIT", //$NON-NLS-1$
    "MPI_PROBE", //$NON-NLS-1$
    "MPI_IPROBE", //$NON-NLS-1$
    "MPI_TESTANY", //$NON-NLS-1$
    "MPI_WAITANY", //$NON-NLS-1$
    "MPI_TESTALL", //$NON-NLS-1$
    "MPI_WAITALL", //$NON-NLS-1$
    "MPI_TESTSOME", //$NON-NLS-1$
    "MPI_WAITSOME", //$NON-NLS-1$
    "MPI_CANCEL", //$NON-NLS-1$
    "MPI_TEST_CANCELLED", //$NON-NLS-1$
    "MPI_SEND_INIT", //$NON-NLS-1$
    "MPI_BSEND_INIT", //$NON-NLS-1$
    "MPI_RSEND_INIT", //$NON-NLS-1$
    "MPI_SSEND_INIT", //$NON-NLS-1$
    "MPI_RECV_INIT", //$NON-NLS-1$
    "MPI_START", //$NON-NLS-1$
    "MPI_STARTALL", //$NON-NLS-1$
    "MPI_SENDRECV", //$NON-NLS-1$
    "MPI_SENDRECV_REPLACE", //$NON-NLS-1$
    "MPI_REQUEST_FREE", //$NON-NLS-1$
    "MPI_BCAST", //$NON-NLS-1$
    "MPI_SCATTER" //$NON-NLS-1$
  };
}
