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
package dk.alexandra.fresco.framework.network;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SCENetworkImpl implements SCENetwork, SCENetworkSupplier {

  private int noOfParties;

  private Map<Integer, ByteBuffer> input;
  private Map<Integer, ByteArrayOutputStream> output;
  private Set<Integer> expectedInputForNextRound;

  public SCENetworkImpl(int noOfParties) {
    this.noOfParties = noOfParties;
    this.output = new HashMap<>();
    this.expectedInputForNextRound = new HashSet<>();
  }

  //ProtocolNetwork
  @Override
  public ByteBuffer receive(int id) {
    return this.input.get(id);
  }

  @Override
  public List<ByteBuffer> receiveFromAll() {
    List<ByteBuffer> res = new ArrayList<>();
    for (int i = 1; i <= noOfParties; i++) {
      res.add(this.input.get(i));
    }
    return res;
  }

  @Override
  public void send(int id, byte[] data) {
    if (id < 1) {
      throw new IllegalArgumentException("Cannot send to an Id smaller than 1");
    }
    ByteArrayOutputStream buffer =
        this.output.computeIfAbsent(id, k -> new ByteArrayOutputStream());
    buffer.write(data, 0, data.length);
  }

  @Override
  public void sendToAll(byte[] data) {
    for (int i = 1; i <= noOfParties; i++) {
      send(i, data);
    }
  }

  @Override
  public void expectInputFromPlayer(int id) {
    if (id < 1) {
      throw new IllegalArgumentException("Cannot send to an Id smaller than 1");
    }
    this.expectedInputForNextRound.add(id);
  }

  @Override
  public void expectInputFromAll() {
    for (int i = 1; i <= noOfParties; i++) {
      this.expectedInputForNextRound.add(i);
    }
  }

  //ProtocolNetworkSupplier

  @Override
  public void setInput(Map<Integer, ByteBuffer> inputForThisRound) {
    this.input = inputForThisRound;
  }

  @Override
  public Map<Integer, byte[]> getOutputFromThisRound() {
    Map<Integer, byte[]> res = new HashMap<>();
    for (int pid : this.output.keySet()) {
      res.put(pid, this.output.get(pid).toByteArray());
    }
    return res;
  }

  @Override
  public Set<Integer> getExpectedInputForNextRound() {
    return this.expectedInputForNextRound;
  }

  @Override
  public void nextRound() {
    this.output.clear();
    this.expectedInputForNextRound.clear();
  }
}