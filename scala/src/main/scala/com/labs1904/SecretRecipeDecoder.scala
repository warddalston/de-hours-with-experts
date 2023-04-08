package com.labs1904

import scala.collection.immutable.HashMap
import scala.io.Source
import java.io.File
import java.io.PrintWriter

/**
 * An ingredient has an amount and a description.
 * @param amount For example, "1 cup"
 * @param description For example, "butter"
 */
case class Ingredient(amount: String, description: String)


object SecretRecipeDecoder {
  val ENCODING: Map[String, String] = HashMap[String, String](
    "y" -> "a",
    "h" -> "b",
    "v" -> "c",
    "x" -> "d",
    "k" -> "e",
    "p" -> "f",
    "z" -> "g",
    "s" -> "h",
    "a" -> "i",
    "b" -> "j",
    "e" -> "k",
    "w" -> "l",
    "u" -> "m",
    "q" -> "n",
    "n" -> "o",
    "l" -> "p",
    "m" -> "q",
    "f" -> "r",
    "o" -> "s",
    "i" -> "t",
    "g" -> "u",
    "j" -> "v",
    "t" -> "w",
    "d" -> "x",
    "r" -> "y",
    "c" -> "z",
    "3" -> "0",
    "8" -> "1",
    "4" -> "2",
    "0" -> "3",
    "2" -> "4",
    "7" -> "5",
    "5" -> "6",
    "9" -> "7",
    "1" -> "8",
    "6" -> "9"
  )

  /**
   * Given a string named str, use the Caeser encoding above to return the decoded string.
   * @param str A caesar-encoded string.
   * @return
   */
  def decodeString(str: String): String = {
    str.map((s: Char) => SecretRecipeDecoder.ENCODING.getOrElse(s.toString, s)).mkString(sep="")
  }

  /**
   * Given an ingredient, decode the amount and description, and return a new Ingredient
   * @param line An encoded ingredient.
   * @return
   */
  def decodeIngredient(line: String): Ingredient = {
    val information: Array[String] = decodeString((line)).split("#")
    Ingredient(information(0), information(1))
  }

  /**
   * A program that decodes a secret recipe
   * @param args
   */
  def linesFromPath(path: String): Array[String] = {
    val source = Source.fromFile(path)
    val array_encoded = source.getLines.toArray
    source.close()
    array_encoded
  }

  def linesToNewFile(path: String, lines: Array[String]): Unit = {
    val file = new File(path)
    val writer = new PrintWriter(file)
    lines.map((l: String) => writer.write(l))
    writer.close()
  }

  def ingredientToString(i: Ingredient): String = {
    s"${i.amount} ${i.description}\n"
  }

  def main(args: Array[String]): Unit = {
    var a: Array[String] = linesFromPath("src/main/resources/secret_recipe.txt")
    a = a.map((s: String) => ingredientToString(decodeIngredient(s)))
    a(a.size-1) = a.last.replace("\n", "")
    linesToNewFile("src/main/resources/decoded_recipe.txt", a)
  }
}
