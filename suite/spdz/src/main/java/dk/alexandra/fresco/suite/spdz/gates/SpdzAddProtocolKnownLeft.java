package dk.alexandra.fresco.suite.spdz.gates;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.suite.spdz.SpdzResourcePool;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzElement;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzSInt;
import java.math.BigInteger;

public class SpdzAddProtocolKnownLeft extends SpdzNativeProtocol<SInt> {

  private BigInteger left;
  private DRes<SInt> right;
  private SpdzSInt out;

  public SpdzAddProtocolKnownLeft(BigInteger left, DRes<SInt> right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public SpdzSInt out() {
    return out;
  }

  @Override
  public EvaluationStatus evaluate(int round, SpdzResourcePool spdzResourcePool,
      Network network) {
    SpdzElement left = SpdzKnownSIntProtocol.createKnownSpdzElement(spdzResourcePool, this.left);
    SpdzSInt right = (SpdzSInt) this.right.out();
    this.out = new SpdzSInt(left.add(right.value));
    return EvaluationStatus.IS_DONE;
  }
}
