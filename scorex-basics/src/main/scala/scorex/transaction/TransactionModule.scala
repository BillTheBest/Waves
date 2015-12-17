package scorex.transaction

import scorex.block.{Block, BlockProcessingModule}

trait TransactionModule[TransactionBlockData] extends BlockProcessingModule[TransactionBlockData] {

  val blockStorage: BlockStorage

  def isValid(block: Block): Boolean

  def isValid(block: Transaction): Boolean

  def transactions(block: Block): Seq[Transaction]

  def packUnconfirmed(): TransactionBlockData

  def clearFromUnconfirmed(data: TransactionBlockData): Unit

  lazy val balancesSupport: Boolean = blockStorage.state match {
    case _: State with BalanceSheet => true
    case _ => false
  }

  lazy val accountWatchingSupport: Boolean = blockStorage.state match {
    case _: State with AccountTransactionsHistory => true
    case _ => false
  }
}