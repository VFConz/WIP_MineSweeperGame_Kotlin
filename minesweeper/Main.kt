package minesweeper

import kotlin.random.Random

class Map(mines:Int)
{
    private val mineCount = mines
    private val minePositions = MutableList<Int>(mineCount) {0}

    val map: MutableList<MutableList<Char>> = MutableList(9) { MutableList<Char>(9) {'.'} }

    init {
        var iter = 0
        var lastA:Int = 0
        var lastB:Int = 0
        for (i in mines downTo 1) {
            var posA = 0
            var posB = 0
            do {
                posA = Random.nextInt(0, 9)
                posB = Random.nextInt(0, 9)
            }while (posA == lastA || posB == lastB)
            val coord = (posA * 10) + posB
            minePositions[iter++] = coord
            lastA = posA
            lastB = posB
            }
            minePositions.forEach()
            {
                var a = it/10
                var b = it%10
                getProximity(map, a, b)
            }
        }


    fun printMap()
    {
        var numCounter = 1
        println(" |123456789|\n-|---------|")
        map.forEach { println("${numCounter++}|${it.joinToString("")}|") }
        println("-|---------|")
    }

    var finalCount = 0
    fun mineCheck(a: Int, b: Int): Int
    {
        val x = a-1
        val y = b-1
        val target = (x*10)+y
        var done = true
        if(minePositions.contains(target)) finalCount++

        if(finalCount == mineCount)
        {
            return 2
        }

        if(map[x][y] == '.')
        {
            map[x][y] = '*'
            return 1
        }
        else if(map[x][y] == '*')
        {
            map[x][y] = '.'
            return 1
        }
        return 0
    }

    private fun numCheck(a: MutableList<MutableList<Char>>, x: Int, y: Int): Char {
        println("MinePositions")
        println(minePositions.toString())
        val target = (x*10)+y
        println("Target: $target")
            if(!minePositions.contains(target))
            {
                if (a[x][y] == '.' && !minePositions.contains(target))
            {
                return '1'
            }
                else if (a[x][y].isDigit()) {
                    var f = a[x][y].digitToInt()
                    f+= 1
                    if(f > 10) {f = 9}
                    return f.digitToChar()
                }
                else
                {
                    return a[x][y]
                }
            }
        return a[x][y]
    }

    private fun getProximity(a: MutableList<MutableList<Char>>, x: Int, y: Int ) {

        when {
            x == 0 && y == 0 -> // Top Left corner
            {
                a[x][y+1] = numCheck(a,x,y+1)
                a[x+1][y+1] = numCheck(a,x+1,y+1)
                a[x+1][y] = numCheck(a,x+1,y )
            }

            x == 8 && y == 0 -> // Top Right corner
            {
                a[x][y+1] = numCheck(a,x,y+1 )
                a[x-1][y+1] = numCheck(a,x-1,y+1 )
                a[x-1][y] = numCheck(a,x-1,y )
            }
            x == 0 && y == 8 -> // Bottom Left
            {
                a[x][y-1] = numCheck(a,x,y-1 )
                a[x+1][y-1] = numCheck(a,x+1,y-1)
                a[x+1][y] = numCheck(a,x+1,y )
            }
            x == 8 && y == 8 -> // Bottom Right
            {
                a[x][y-1] = numCheck(a,x,y-1 )
                a[x-1][y-1] = numCheck(a,x-1,y-1 )
                a[x-1][y] = numCheck(a,x-1,y )
            }

            (x == 0 && y in 1..7) -> // Left Border
            {
                a[x][y+1] = numCheck(a,x,y+1)
                a[x][y-1] = numCheck(a,x,y-1 )
                a[x+1][y-1] = numCheck(a,x+1,y-1)
                a[x+1][y+1] = numCheck(a,x+1,y+1)
                a[x+1][y] = numCheck(a,x+1,y )
            }

            (x == 8 && y in 1..7) -> // Bottom Border
            {
                a[x][y+1] = numCheck(a,x,y+1 )
                a[x][y-1] = numCheck(a,x,y-1 )
                a[x-1][y+1] = numCheck(a,x-1,y+1 )
                a[x-1][y-1] = numCheck(a,x-1,y-1 )
                a[x-1][y] = numCheck(a,x-1,y )
            }
            (x in 1..7 && y == 0) -> // Top Side
            {
                a[x][y+1] = numCheck(a,x,y+1 )
                a[x+1][y+1] = numCheck(a,x+1,y+1)
                a[x+1][y] = numCheck(a,x+1,y )
                a[x-1][y+1] = numCheck(a,x-1,y+1 )
                a[x-1][y] = numCheck(a,x-1,y )
            }

            x in 1..7 && y  == 8 -> // Right Side
            {
                a[x][y-1] = numCheck(a,x,y-1 )
                a[x+1][y-1] = numCheck(a,x+1,y-1)
                a[x+1][y] = numCheck(a,x+1,y )
                a[x-1][y-1] = numCheck(a,x-1,y-1 )
                a[x-1][y] = numCheck(a,x-1,y )
            }

            (x in 1..7 && y in 1..7) -> // Center
            {
                a[x][y+1] = numCheck(a,x,y+1)
                a[x][y-1] = numCheck(a,x,y-1 )
                a[x+1][y-1] = numCheck(a,x+1,y-1)
                a[x+1][y+1] = numCheck(a,x+1,y+1)
                a[x+1][y] = numCheck(a,x+1,y )
                a[x-1][y+1] = numCheck(a,x-1,y+1 )
                a[x-1][y-1] = numCheck(a,x-1,y-1 )
                a[x-1][y] = numCheck(a,x-1,y )
            }
        }
    }
}

fun main()
{
    var PlayerWon = false
    var PlayCount = 0
    print("How many mines do you want on the field? ")
    val MineMap = Map(readln().toInt())
    MineMap.printMap()

    while(!PlayerWon)
    {

        print("Set/delete mines marks (x and y coordinates): >")
        var xy = listOf<String>()
        do{
             xy = readln().split(" ")
        }while (xy[0].toIntOrNull() == null || xy[1].toIntOrNull() == null)

        when(MineMap.mineCheck(xy[1].toInt(), xy[0].toInt()))
        {
            2 -> {
                println("Congratulations! You found all the mines!\n")
                PlayerWon = true
            }
            1 -> {
                println("Successfully Set a Marker")
                MineMap.printMap()
            }
            0 -> {
                println("Placed on a Number Please input on an EMPTY ('.') or MARKED ('*') Field")
            }
        }
    }






}