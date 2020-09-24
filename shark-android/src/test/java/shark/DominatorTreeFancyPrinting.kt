package shark

import org.junit.Test
import shark.AndroidReferenceMatchers.Companion.buildKnownReferences
import shark.AndroidReferenceMatchers.FINALIZER_WATCHDOG_DAEMON
import shark.AndroidReferenceMatchers.REFERENCES
import shark.HeapObject.HeapClass
import shark.HeapObject.HeapInstance
import shark.HeapObject.HeapObjectArray
import shark.HeapObject.HeapPrimitiveArray
import shark.HprofHeapGraph.Companion.openHeapGraph
import java.io.File
import java.lang.StringBuilder
import java.util.EnumSet

class DominatorTreeFancyPrinting {

  @Test fun `fancy o`() {
    val hprofFile = "leak_asynctask_o.hprof".classpathFile()
    fancyPrintDominatorTree(hprofFile, 2000)
  }

  @Test fun `fancy m`() {
    val hprofFile = "leak_asynctask_m.hprof".classpathFile()
    fancyPrintDominatorTree(hprofFile, 2000)
  }

  private fun fancyPrintDominatorTree(
    hprofFile: File,
    minRetainedSize: Int
  ) {
    val weakAndFinalizerRefs = EnumSet.of(REFERENCES, FINALIZER_WATCHDOG_DAEMON)
    val ignoredRefs = buildKnownReferences(weakAndFinalizerRefs).map { matcher ->
      matcher as IgnoredReferenceMatcher
    }

    hprofFile.openHeapGraph().use { graph ->
      val thing = DominatorThing()
      println(thing.renderDominatorTree(graph, ignoredRefs, minRetainedSize))
    }
  }
}