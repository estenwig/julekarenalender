import AssemblyKeys._

assemblySettings

jarName in assembly <<= (name, version) map { (name, version) => name + "-" + version + ".jar" }

mainClass in assembly := Some("com.github.julekarenalender.Main")

mergeStrategy in assembly <<= (mergeStrategy in assembly) {
  (old) => {
    case PathList("example", xs @ _*) => MergeStrategy.discard
    case x => old(x)
  }
}
