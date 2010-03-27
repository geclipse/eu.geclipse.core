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

package eu.geclipse.traceview.nope.tracereader;

/**
 * Nope Event Subtypes
 */
final public class EventSubtype {

  /** */
  public final static int NCube_NREAD = 0x00;
  /** */
  public final static int NCube_NREADP = 0x01;
  /** */
  public final static int NCube_NWRITE = 0x02;
  /** */
  public final static int NCube_NWRITEP = 0x03;
  /** */
  public final static int NCube_NTEST = 0x04;
  /** */
  public final static int MPI_RECV = 0xa0;
  /** */
  public final static int MPI_IRECV = 0xa1;
  /** */
  public final static int MPI_SEND = 0xa2;
  /** */
  public final static int MPI_BSEND = 0xa3;
  /** */
  public final static int MPI_RSEND = 0xa4;
  /** */
  public final static int MPI_SSEND = 0xa5;
  /** */
  public final static int MPI_ISEND = 0xa6;
  /** */
  public final static int MPI_IBSEND = 0xa7;
  /** */
  public final static int MPI_IRSEND = 0xa8;
  /** */
  public final static int MPI_ISSEND = 0xa9;
  /** */
  public final static int MPI_TEST = 0xaa;
  /** */
  public final static int MPI_WAIT = 0xab;
  /** */
  public final static int MPI_PROBE = 0xac;
  /** */
  public final static int MPI_IPROBE = 0xad;
  /** */
  public final static int MPI_TESTANY = 0xae;
  /** */
  public final static int MPI_WAITANY = 0xaf;
  /** */
  public final static int MPI_TESTALL = 0xb0;
  /** */
  public final static int MPI_WAITALL = 0xb1;
  /** */
  public final static int MPI_TESTSOME = 0xb2;
  /** */
  public final static int MPI_WAITSOME = 0xb3;
  /** */
  public final static int MPI_CANCEL = 0xb4;
  /** */
  public final static int MPI_TEST_CANCELLED = 0xb5;
  /** */
  public final static int MPI_SEND_INIT = 0xb6;
  /** */
  public final static int MPI_BSEND_INIT = 0xb7;
  /** */
  public final static int MPI_RSEND_INIT = 0xb8;
  /** */
  public final static int MPI_SSEND_INIT = 0xb9;
  /** */
  public final static int MPI_RECV_INIT = 0xba;
  /** */
  public final static int MPI_START = 0xbb;
  /** */
  public final static int MPI_STARTALL = 0xbc;
  /** */
  public final static int MPI_SENDRECV = 0xbd;
  /** */
  public final static int MPI_SENDRECV_REPLACE = 0xbe;
  /** */
  public final static int MPI_REQUEST_FREE = 0xbf;
  /** */
  public final static int MPI_BCAST = 0xc0;
  /** */
  public final static int MPI_SCATTER = 0xc1;

  /**
   * Returns the name of the communication event subtype.
   * 
   * @param eventSubType integer value of the subtype
   * @return name of the sub type
   */
  public static String getCommunicationEventName( final int eventSubType ) {
    String result = null;
    switch( eventSubType ) {
      case 0x00:
        result = "NCube_NREAD"; //$NON-NLS-1$
      break;
      case 0x01:
        result = "NCube_NREADP"; //$NON-NLS-1$
      break;
      case 0x02:
        result = "NCube_NWRITE"; //$NON-NLS-1$
      break;
      case 0x03:
        result = "NCube_NWRITEP"; //$NON-NLS-1$
      break;
      case 0x04:
        result = "NCube_NTEST"; //$NON-NLS-1$
      break;
      case 0xa0:
        result = "MPI_RECV"; //$NON-NLS-1$
      break;
      case 0xa1:
        result = "MPI_IRECV"; //$NON-NLS-1$
      break;
      case 0xa2:
        result = "MPI_SEND"; //$NON-NLS-1$
      break;
      case 0xa3:
        result = "MPI_BSEND"; //$NON-NLS-1$
      break;
      case 0xa4:
        result = "MPI_RSEND"; //$NON-NLS-1$
      break;
      case 0xa5:
        result = "MPI_SSEND"; //$NON-NLS-1$
      break;
      case 0xa6:
        result = "MPI_ISEND"; //$NON-NLS-1$
      break;
      case 0xa7:
        result = "MPI_IBSEND"; //$NON-NLS-1$
      break;
      case 0xa8:
        result = "MPI_IRSEND"; //$NON-NLS-1$
      break;
      case 0xa9:
        result = "MPI_ISSEND"; //$NON-NLS-1$
      break;
      case 0xaa:
        result = "MPI_TEST"; //$NON-NLS-1$
      break;
      case 0xab:
        result = "MPI_WAIT"; //$NON-NLS-1$
      break;
      case 0xac:
        result = "MPI_PROBE"; //$NON-NLS-1$
      break;
      case 0xad:
        result = "MPI_IPROBE"; //$NON-NLS-1$
      break;
      case 0xae:
        result = "MPI_TESTANY"; //$NON-NLS-1$
      break;
      case 0xaf:
        result = "MPI_WAITANY"; //$NON-NLS-1$
      break;
      case 0xb0:
        result = "MPI_TESTALL"; //$NON-NLS-1$
      break;
      case 0xb1:
        result = "MPI_WAITALL"; //$NON-NLS-1$
      break;
      case 0xb2:
        result = "MPI_TESTSOME"; //$NON-NLS-1$
      break;
      case 0xb3:
        result = "MPI_WAITSOME"; //$NON-NLS-1$
      break;
      case 0xb4:
        result = "MPI_CANCEL"; //$NON-NLS-1$
      break;
      case 0xb5:
        result = "MPI_TEST_CANCELLED"; //$NON-NLS-1$
      break;
      case 0xb6:
        result = "MPI_SEND_INIT"; //$NON-NLS-1$
      break;
      case 0xb7:
        result = "MPI_BSEND_INIT"; //$NON-NLS-1$
      break;
      case 0xb8:
        result = "MPI_RSEND_INIT"; //$NON-NLS-1$ 
      break;
      case 0xb9:
        result = "MPI_SSEND_INIT"; //$NON-NLS-1$
      break;
      case 0xba:
        result = "MPI_RECV_INIT"; //$NON-NLS-1$
      break;
      case 0xbb:
        result = "MPI_START"; //$NON-NLS-1$
      break;
      case 0xbc:
        result = "MPI_STARTALL"; //$NON-NLS-1$
      break;
      case 0xbd:
        result = "MPI_SENDRECV"; //$NON-NLS-1$
      break;
      case 0xbe:
        result = "MPI_SENDRECV_REPLACE"; //$NON-NLS-1$
      break;
      case 0xbf:
        result = "MPI_REQUEST_FREE"; //$NON-NLS-1$
      break;
      case 0xc0:
        result = "MPI_BCAST"; //$NON-NLS-1$
      break;
      case 0xc1:
        result = "MPI_SCATTER"; //$NON-NLS-1$
      break;
      default:
        result = "unknown"; //$NON-NLS-1$
      break;
    }
    return result;
  }
}
