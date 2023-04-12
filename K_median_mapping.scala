package sal.parsing.sam.operators

import scala.collection.mutable.HashMap
import sal.parsing.sam.BaseParsing
import sal.parsing.sam.Constants
import sal.parsing.sam.Util

trait KMedians extends BaseParsing {
  val kMediansKeyWord: String = "kmedians"
  
  def kMediansOperator: Parser[KMediansExp] =
    kMediansKeyWord ~ "(" ~ identifier ~ "," ~ int ~ ")" ^^
      { case kmed ~ lpar ~ id ~ c1 ~ k ~ rpar =>
        KMediansExp(id, k, memory)
      }
}

case class KMediansExp(field: String, k: Int, memory: HashMap[String, String])
    extends OperatorExp(field, memory) with Util {
  
  override def createOpString(): String = {
    val lstream = memory.get(Constants.CurrentLStream).get
    val rstream = memory.get(Constants.CurrentRStream).get

    // Get the tuple type of the input stream
    val tupleType = memory.get(lstream + Constants.TupleType).get

    // Generate SAM code for the K-medians operator
    val opString = s"""  identifier = "$lstream";
    auto $lstream = std::make_shared<KMedians<$tupleType>>($k);
    ${addRegisterStatements(lstream, rstream, memory)}"""
  
    opString
  }
}
