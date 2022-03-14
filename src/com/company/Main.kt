package com.company

import java.io.File
import java.io.InputStream
import kotlin.jvm.JvmStatic

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val notInUse = "_".toCharArray().toSet()
        val wrongPlace = listOf("_", "_", "_", "_", "_")
        val rightPlace = ".....".toRegex()

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

        val wrongPlaceRegEx = ("[" +
                wrongPlace[0] + "]....||.[" +
                wrongPlace[1] + "]...||..[" +
                wrongPlace[2] + "]..||...[" +
                wrongPlace[3] + "].||....[" +
                wrongPlace[4] + "]").toRegex()
        val wrongPlaceFiltered = notInUseFiltered.filter { word ->
            !wrongPlaceRegEx.matches(word)
        }.filter { word ->
            wrongPlace.fold(true) { r, t ->
                r && (t == "_" || t.toRegex().find(word)?.value != null)
            }
        }

        println("Wrong place: " + wrongPlaceFiltered.size + ", " + wrongPlaceFiltered)

        val rightPlaceFiltered = wrongPlaceFiltered.filter { word ->
            rightPlace.matches(word)
        }
        println("Right place: " + rightPlaceFiltered.size + ", " + rightPlaceFiltered)

        val rankedSortedList = rankList(rightPlaceFiltered)
        println("Ranked: $rankedSortedList")
    }

    private fun rankList(wordsToRank: List<String>): List<String> {
        var charMap = mutableMapOf<Char, Int>()
        wordsToRank.forEach { word ->
            word.toCharArray().forEach { c ->
                charMap[c] = 1 + (charMap[c] ?: 0)
            }
        }
        var rankedList = wordsToRank.map { word ->
            val rank = word.toCharArray().toSet().toCharArray().fold(0) { r, t -> r + (charMap[t] ?: 0) }
            Pair(word, rank)
        }
        val rankedSortedList = rankedList.sortedByDescending { t -> t.second }
        return rankedSortedList.map { p ->
            p.first
        }
    }
}