//import na spracovanie suborov
import java.io.File

val hangman = listOf("""
    ````
    |  
    |  
    | 
    | 
    | 
    |
    ``````````
""".trimIndent(), """
    ````
    |  |
    |  
    | 
    |  
    | 
    |
    ``````````
""".trimIndent(), """
    ````
    |  |
    |  °
    | 
    |  
    | 
    |
    ``````````
""".trimIndent(),"""
    ````
    |  |
    |  °
    | /
    |  
    | 
    |
    ``````````
""".trimIndent(),"""
    ````
    |  |
    |  °
    | / \
    |  
    | 
    |
    ``````````
""".trimIndent(),"""
    ````
    |  |
    |  °
    | /|\
    |  
    | 
    |
    ``````````
""".trimIndent(),"""
    ````
    |  |
    |  °
    | /|\
    |  |
    | 
    |
    ``````````
""".trimIndent(),"""
    ````
    |  |
    |  °
    | /|\
    |  |
    | / 
    |
    ``````````
""".trimIndent(), """
    ````
    |  |
    |  °
    | /|\
    |  |
    | / \
    |
    ``````````
""".trimIndent())

fun main(){
    val word = File("words.txt").readLines().random();
    var status = ".".repeat(word.length).toCharArray();
    var life = hangman.lastIndex 
    println(word)
    println(status)
    println(life)

    while(life > 0 && String(status) != word){
       print(hangman[life])
       print(status)
       println(life)

       var input = readLine()!!
  //   println(input)
  //   life--;

       if(input.length > 1){
         if(input == word){
            status = input.toCharArray()
         } else life--
       } else if( input in word ){
        word.forEachIndexed { index, c ->
        if ( c in input ) {
            status[index] = c
         }
        }
       } else life --

    }

    if(life > 0) println("hra ukoncena, vyhral si")
    else println("hra ukoncena prehral si. slovo bolo $word")


    println("hra ukoncena")


}