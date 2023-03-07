package sal.parsing.sam.operators

import org.scalatest._
import scala.collection.mutable.HashMap
import sal.parsing.sam.Util
import sal.parsing.sam.SAM._
import sal.parsing.sam.Constants._

class KMediansSpec extends FlatSpec with Matchers {

  "K-medians operator" should "compute the median of a stream of integers" in {

    // Define the input data
    val inputData = List((1, 5), (2, 2), (3, 8), (4, 1), (5, 7))

    // Define the expected output
    val expectedOutput = List((3, 5))

    // Create an instance of the SAM machine
    val sam = new SAM()

    // Register the K-medians operator with the SAM machine
    val kmediansExp = KMediansExp("field", 1, new HashMap[String, String]())
    val opString = kmediansExp.createOpString()
    val op = sam.compileOperator(opString)
    sam.register(op)

    // Use the input stream of the K-medians operator to feed the sample input data
    sam.addTuple(inputData)

    // Get the output from the SAM machine
    val output = sam.getOutputStream(op).toList

    // Verify that the output matches the expected output
    output shouldEqual expectedOutput
  }

}
