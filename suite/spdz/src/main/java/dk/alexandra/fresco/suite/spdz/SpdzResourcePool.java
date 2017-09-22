package dk.alexandra.fresco.suite.spdz;

import dk.alexandra.fresco.framework.network.serializers.BigIntegerSerializer;
import dk.alexandra.fresco.framework.sce.resources.ResourcePool;
import dk.alexandra.fresco.suite.spdz.gates.SpdzOutputProtocol;
import dk.alexandra.fresco.suite.spdz.storage.SpdzStorage;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * The resource pool for Spdz. Represents the resources used for on invocation of the
 * spdz protocol suite.
 */
public interface SpdzResourcePool extends ResourcePool {

  /**
   * Gets the modulus.
   *
   * @return modulus
   */
  BigInteger getModulus();

  /**
   * Gets a serializer for big integer that is aligned with the current system settings in this
   * invocation - hence byte length of big integer.
   *
   * @return the serializer
   */
  BigIntegerSerializer getSerializer();

  /**
   * Gets the Spdz store.
   *
   * @return the store
   */
  SpdzStorage getStore();


  /**
   * Gets the message digest for this protocol suite invocation.
   *
   * @return the message digest
   */
  MessageDigest getMessageDigest();

  /**
   * Call to set the current state of the resource pool.
   *
   * @param p The output protocol to evaluate once the batch is done.
   */
  void addOutputProtocolToBatch(SpdzOutputProtocol<?> p);

  List<SpdzOutputProtocol<?>> getOutputProtocolsInBatch();

  /**
   * Is true if there was an output protocol in the batch.
   *
   * @return true if output
   */
  boolean isOutputProtocolInBatch();

  BigInteger convertRepresentation(BigInteger b);
}
