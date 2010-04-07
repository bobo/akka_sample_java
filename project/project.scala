import sbt._

class FooProject(info: ProjectInfo) extends DefaultProject(info) {

val mavenLocal = "Local Maven Repository" at 
"file://home/bobo/.m2/repository"
}
