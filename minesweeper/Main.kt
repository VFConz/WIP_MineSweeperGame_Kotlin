package minesweeper

import kotlin.math.abs
import kotlin.random.Random


fun getProximity(a: MutableList<MutableList<Char>>, x: Int, y: Int ) {

    when {

        x == 0 && y == 0 -> // Top Left corner
        {
            a[x][y+1] = numCheck(a[x][y+1] )
            a[x+1][y+1] = numCheck(a[x+1][y+1])
            a[x+1][y] = numCheck(a[x+1][y] )
        }
        x == 8 && y == 0 -> // Top Right corner
        {
            a[x][y+1] = numCheck(a[x][y+1] )
            a[x-1][y+1] = numCheck(a[x-1][y+1] )
            a[x-1][y] = numCheck(a[x-1][y] )
        }
        x == 0 && y == 8 -> // Bottom Left
        {
            a[x][y-1] = numCheck(a[x][y-1] )
            a[x+1][y-1] = numCheck(a[x+1][y-1])
            a[x+1][y] = numCheck(a[x+1][y] )
        }
        x == 8 && y == 8 -> // Bottom Right
        {
            a[x][y-1] = numCheck(a[x][y-1] )
            a[x-1][y-1] = numCheck(a[x-1][y-1] )
            a[x-1][y] = numCheck(a[x-1][y] )
        }

        (x == 0 && y in 1..7) -> // Left Border
        {
            a[x][y+1] = numCheck(a[x][y+1] )
            a[x][y-1] = numCheck(a[x][y-1] )
            a[x+1][y-1] = numCheck(a[x+1][y-1])
            a[x+1][y+1] = numCheck(a[x+1][y+1])
            a[x+1][y] = numCheck(a[x+1][y] )
        }

        (x == 8 && y in 1..7) -> // Bottom Border
        {
            a[x][y+1] = numCheck(a[x][y+1] )
            a[x][y-1] = numCheck(a[x][y-1] )
            a[x-1][y+1] = numCheck(a[x-1][y+1] )
            a[x-1][y-1] = numCheck(a[x-1][y-1] )
            a[x-1][y] = numCheck(a[x-1][y] )
        }
        (x in 1..7 && y == 0) -> // Top Side
        {
            a[x][y+1] = numCheck(a[x][y+1] )
            a[x+1][y+1] = numCheck(a[x+1][y+1])
            a[x+1][y] = numCheck(a[x+1][y] )
            a[x-1][y+1] = numCheck(a[x-1][y+1] )
            a[x-1][y] = numCheck(a[x-1][y] )
        }

        x in 1..7 && y  == 8 -> // Right Side
        {
            a[x][y-1] = numCheck(a[x][y-1] )
            a[x+1][y-1] = numCheck(a[x+1][y-1])
            a[x+1][y] = numCheck(a[x+1][y] )
            a[x-1][y-1] = numCheck(a[x-1][y-1] )
            a[x-1][y] = numCheck(a[x-1][y] )
        }

        (x in 1..7 && y in 1..7) -> // Center
        {
            a[x][y+1] = numCheck(a[x][y+1])
            a[x][y-1] = numCheck(a[x][y-1] )
            a[x+1][y-1] = numCheck(a[x+1][y-1])
            a[x+1][y+1] = numCheck(a[x+1][y+1])
            a[x+1][y] = numCheck(a[x+1][y] )
            a[x-1][y+1] = numCheck(a[x-1][y+1] )
            a[x-1][y-1] = numCheck(a[x-1][y-1] )
            a[x-1][y] = numCheck(a[x-1][y] )
        }
    }
}
fun numCheck(a:Char):Char {
    if (a.isDigit()) {
        var f = a.digitToInt()
        f += 1
        return f.digitToChar()
    }
    else if (a == '.')
    {
        return '1'
    }
    else
    {
        return a
    }
}

fun main() {
    val map: MutableList<MutableList<Char>> = MutableList(9) { MutableList<Char>(9) {'.'} }
    print("How many mines do you want on the field? ")
    var mineCount = readln().toInt()
    while (mineCount != 0) {
        val posA = Random.nextInt(0,9)
        val posB = Random.nextInt(0,9)
        if((map[posA][posB]) != 'X')
        {
            map[posA][posB] = 'X'
            getProximity(map, posA, posB)
            mineCount--
        }
    }
    for(i in map) println(i.joinToString(""))

}