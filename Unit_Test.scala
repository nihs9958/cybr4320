import org.scalatest._

class KMediansSpec extends FlatSpec with Matchers {
  "A KMedians operator" should "generate the correct C++ code" in {
    val memory = Map(
      "CurrentLStream" -> "input_stream",
      "CurrentRStream" -> "output_stream",
      "input_streamTupleType" -> "tuple",
      "input_streamNumKeys" -> "1",
      "input_streamKeyStr0" -> "key"
    )

    val exp = KMediansExp("field", 5, scala.collection.mutable.HashMap(memory.toSeq: _*))
    val opString = exp.createOpString()

    val expectedOpString = """  identifier = "input_stream";
    auto input_stream = std::make_shared<KMedians<tuple>>(5);
    registerStream(input_stream);
    registerStream(output_stream);
    registerOperator(input_stream);
    registerOperator(output_stream);
    input_stream->registerConsumer(output_stream);
    input_stream->initialize();
"""

    opString shouldEqual expectedOpString
  }
}
