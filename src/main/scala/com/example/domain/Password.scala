package com.example.domain

import scala.util.matching.Regex

class Password(toTransform: String) {
  private final val SALT = "SALTY" //TODO

  /**
   * Checks, whether the password is matches regex
   */
  private def isRegexValid(toCheck: String) = {
    val passwordRegex: Regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])$".r
    passwordRegex.findFirstMatchIn(toCheck) match {
      case Some(_) => true
      case None => false
    }
  }

  /**
   * Translates a string to its md5 version
   */
  private def md5HashString(s: String): String = {
    import java.security.MessageDigest
    import java.math.BigInteger
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(s.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  }

  toTransform match {
    case str if str.length < 5 || str.length > 256 =>
      throw new Exception("Password length should be 5 <= n <= 256 ") //TODO
    case str if isRegexValid(str) =>
      throw new Exception("Password should contain at least a number, and a letter in upperCase and lowerCase")
  }

  lazy val value: String = md5HashString(md5HashString(SALT + toTransform))

  override def toString = value.take(3) + "***"
}