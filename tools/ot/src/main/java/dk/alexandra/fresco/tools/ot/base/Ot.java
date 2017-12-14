package dk.alexandra.fresco.tools.ot.base;

import dk.alexandra.fresco.framework.util.StrictBitVector;

/**
 * Oblivious Transfer interface.
 * 
 * @author jot2re
 *
 */
public interface Ot {

  /**
   * Send two possible messages.
   * 
   * @param messageZero
   *          Message zero to send
   * @param messageOne
   *          Message one to send
   */
  public void send(StrictBitVector messageZero, StrictBitVector messageOne);

  /**
   * Receive one-out-of-two messages.
   * 
   * @param choiceBit
   *          Bit indicating which message to receive. False means message zero
   *          and true means message one.
   * @return The message indicated by the choice bit
   */
  public StrictBitVector receive(Boolean choiceBit);
}
