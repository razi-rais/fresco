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
package dk.alexandra.fresco.lib.helper;


import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.field.integer.BasicNumericFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Helper for various matrix and vector/array operations.
 */
public class AlgebraUtil {


  /**
   * Convert an array of arrays into a list of lists.
   *
   * @param array The array to convert
   * @return An ArrayList
   */
  public static <T> List<List<T>> arrayToList(T[][] array) {
    List<List<T>> output = new ArrayList<>(array.length);
    for (T[] anArray : array) {
      output.add(arrayToList(anArray));
    }
    return output;
  }

  /**
   * Convert an array into a list.
   *
   * @param array The array to convert
   * @return An ArrayList
   */
  public static <T> List<T> arrayToList(T[] array) {
    List<T> output = new ArrayList<>(array.length);
    Collections.addAll(output, array);
    return output;
  }

  /**
   * Fill an array with SInts, using the provided factory. Existing SInts
   * will be overwritten.
   *
   * @param vector The output SInt array
   * @param provider The factory for creating the SInts.
   * @return The output SInt array
   */
  public static SInt[] sIntFill(SInt[] vector, BasicNumericFactory provider) {
    for (int i = 0; i < vector.length; i++) {
      vector[i] = provider.getSInt();
    }
    return vector;
  }

  /**
   * Fill the provided matrix with random numbers
   *
   * @param matrix The output matrix
   * @param bitLenght The size of the random rumbers
   * @param rand A source of randomness
   */
  public static void randomFill(BigInteger[][] matrix, int bitLenght, Random rand) {
    for (BigInteger[] vector : matrix) {
      randomFill(vector, bitLenght, rand);
    }
  }

  /**
   * Fill the provided array with random numbers
   *
   * @param vector The output array
   * @param bitLength The size of the random rumbers
   * @param rand A source of randomness
   */
  private static void randomFill(BigInteger[] vector, int bitLength, Random rand) {
    for (int i = 0; i < vector.length; i++) {
      vector[i] = new BigInteger(bitLength, rand);
    }
  }

}