package snippets

import scala.runtime.RichBoolean

object Application {
    def main(args: Array[String]): Unit = {
        fizzbuzz(_ % 3 == 0, _ % 5 == 0)
        fizzbuzz(n => n % 3 == 0 || n.toString().contains('3'), n => n % 5 == 0 || ((n toString) contains '5'))
    }
    
    def fizzbuzz (fizz: Int => Boolean, buzz: Int => Boolean) : Unit = {
      for (i <- 1 to 100){
        val (f, b) = (if (fizz(i)) "Fizz" else "", if (buzz(i)) "Buzz" else "") 
        println(if (f+b == "") i else f+b)
      }
    }
}