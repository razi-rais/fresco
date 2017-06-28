/*
 * Copyright (c) 2015, 2016 FRESCO (http://github.com/aicis/fresco).
 *
 * This file is part of the FRESCO project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * FRESCO uses SCAPI - http://crypto.biu.ac.il/SCAPI, Crypto++, Miracl, NTL,
 * and Bouncy Castle. Please see these projects for any further licensing issues.
 *******************************************************************************/
package dk.alexandra.fresco.lib.compare.zerotest;

import dk.alexandra.fresco.framework.Computation;
import dk.alexandra.fresco.framework.ProtocolProducer;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.compare.RandomAdditiveMaskFactory;
import dk.alexandra.fresco.lib.field.integer.BasicNumericFactory;
import dk.alexandra.fresco.lib.helper.SimpleProtocolProducer;
import dk.alexandra.fresco.lib.helper.sequential.SequentialProtocolProducer;
import dk.alexandra.fresco.lib.math.integer.HammingDistanceFactory;
import java.math.BigInteger;

public class ZeroTestReducerProtocolImpl extends SimpleProtocolProducer implements
    ZeroTestReducerProtocol {

  private final int bitLength;
  private final int securityParameter;
  private final SInt input, output;
  private final RandomAdditiveMaskFactory maskFactory;
  private final BasicNumericFactory factory;
  private HammingDistanceFactory hammingFactory;

  ZeroTestReducerProtocolImpl(
      int bitLength,
      int securityParameter,
      SInt input, SInt output,
      RandomAdditiveMaskFactory maskFactory,
      BasicNumericFactory factory,
      HammingDistanceFactory hammingFactory) {
    this.bitLength = bitLength;
    this.securityParameter = securityParameter;

    this.maskFactory = maskFactory;
    this.factory = factory;
    this.hammingFactory = hammingFactory;

    this.input = input;
    this.output = output;
  }

  @Override
  protected ProtocolProducer initializeProtocolProducer() {

    SequentialProtocolProducer sequentialProtocolProducer = new SequentialProtocolProducer();

    // LOAD r
    SInt[] bits = new SInt[bitLength];
    for (int i = 0; i < bitLength; i++) {
      bits[i] = factory.getSInt();
    }
    SInt r = factory.getSInt();
    sequentialProtocolProducer.append(
        maskFactory.getRandomAdditiveMaskProtocol(securityParameter, bits, r));

    SInt mS = factory.getSInt();
    sequentialProtocolProducer.append(factory.getAddProtocol(input, r, mS));
    // open m
    Computation<BigInteger> m0 = factory.getOpenProtocol(mS);
    sequentialProtocolProducer.append(m0);
    // result = Hamming-dist_l(z,r);
    sequentialProtocolProducer.append(hammingFactory.getHammingdistanceProtocol(bits, m0, output));

    return sequentialProtocolProducer;
  }
}
