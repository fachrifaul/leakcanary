package shark

data class DominatorNode(
  val shallowSize: Int,
  val retainedSize: Int,
  val retainedCount: Int,
  val dominatedObjectIds: List<Long>
)