/*
 * Copyright (c) 2015, 2016 FRESCO (http://github.com/aicis/fresco).
 *
 * This file is part of the FRESCO project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * FRESCO uses SCAPI - http://crypto.biu.ac.il/SCAPI, Crypto++, Miracl, NTL, and Bouncy Castle.
 * Please see these projects for any further licensing issues.
 *******************************************************************************/
package dk.alexandra.fresco.lib.conditional;

import java.util.ArrayList;

import dk.alexandra.fresco.framework.Computation;
import dk.alexandra.fresco.framework.builder.ComputationBuilderParallel;
import dk.alexandra.fresco.framework.builder.ProtocolBuilderNumeric.ParallelNumericBuilder;
import dk.alexandra.fresco.framework.value.SInt;

public class ConditionalSelectRow
    implements ComputationBuilderParallel<ArrayList<Computation<SInt>>> {

  private final Computation<SInt> selector;
  private final ArrayList<Computation<SInt>> a, b;

  /**
   * Selects a or b based on selector. Selector must be 0 or 1.
   * 
   * If selector is 0 b is selected, otherwise a.
   * 
   * @param selector
   * @param a
   * @param b
   */
  public ConditionalSelectRow(Computation<SInt> selector, ArrayList<Computation<SInt>> a,
      ArrayList<Computation<SInt>> b) {
    // TODO: throw if different sizes
    this.selector = selector;
    this.a = a;
    this.b = b;
  }

  @Override
  public Computation<ArrayList<Computation<SInt>>> build(ParallelNumericBuilder builder) {
    ArrayList<Computation<SInt>> selected = new ArrayList<>();
    for (int i = 0; i < this.a.size(); i++) {
      selected
          .add(builder.createSequentialSub(new ConditionalSelect(selector, a.get(i), b.get(i))));
    }
    return () -> selected;
  }
}
