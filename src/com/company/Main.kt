package com.company

import java.io.File
import java.io.InputStream
import kotlin.jvm.JvmStatic

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val notInUse = "aonirpy".toCharArray().toSet()
        val wrongPlace = listOf("te", "e", "m", "et", "e")
        val rightPlace = ".m...".toRegex()

        val inputStream: InputStream = File("corncob_lowercase_5_char_words.txt").inputStream()
        var fullList: MutableList<String> = arrayListOf()
        inputStream.bufferedReader().forEachLine { word ->
            fullList.add(word)
        }

        println("Full list: " + fullList.size + ", " + fullList)

        val notInUseEx = ("[" + notInUse.fold("") { R, T -> R + T } + "]").toRegex()
        println("notInUseEx $notInUseEx")
        val notInUseFiltered = fullList.filter { word ->
            notInUseEx.find(word)?.value == null
        }
        println("Not in use: " + notInUseFiltered.size + ", " + notInUseFiltered)

        val wrongPlaceRegEx =
            ("[" + wrongPlace[0] + "]....||.[" + wrongPlace[1] + "]...||..[" + wrongPlace[2] + "]..||...[" + wrongPlace[3] + "].||....[" + wrongPlace[4] + "]"
                    ).toRegex()
        val wrongPlaceFiltered = notInUseFiltered.filter { word ->
            !wrongPlaceRegEx.matches(word)

        }
        println("Wrong place: " + wrongPlaceFiltered.size + ", " + wrongPlaceFiltered)

        val rightPlaceFiltered = wrongPlaceFiltered.filter { word ->
            rightPlace.matches(word)
        }
        println("Right place: " + rightPlaceFiltered.size + ", " + rightPlaceFiltered)

    }
}