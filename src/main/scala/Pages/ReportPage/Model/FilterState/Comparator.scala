package Pages.ReportPage.Model.FilterState

sealed abstract class Comparator {
  def getOtherOne: Comparator
  def getSymbol: String
}

object Comparator {
  case object AND extends Comparator {
    override def toString: String = "AND"
    def getOtherOne: Comparator = OR
    def getSymbol = "%"
  }
  case object OR extends Comparator {
    override def toString: String = "OR"
    def getOtherOne: Comparator = AND
    def getSymbol = "|"
  }
}

