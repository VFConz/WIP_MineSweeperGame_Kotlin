package minesweeper

import kotlin.random.Random

class Map(mines:Int)
{
    private val mineCount = mines
    private val minePositions = MutableList<Int>(mineCount) {0}

    val map: MutableList<MutableList<Char>> = MutableList(9) { MutableList<Char>(9) {'/'} }
    var init: Boolean = true

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

        init = false
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

        if(init)
        {
            if(!minePositions.contains(target))
            {
                if (a[x][y] == '/' && !minePositions.contains(target))
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
            if(minePositions.contains(target)) return '.'
        }

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

    private fun getProximity(a: MutableList<MutableList<Char>>, x: Int, y: Int) {

        getArea(a, x, y).forEach()
        {
            val xCord: Int = it / 10
            val yCord: Int = it % 10
            println("x: $xCord y:$yCord")
            map[xCord][yCord] = numCheck(a,xCord,yCord)
        }
    }
    fun getArea(a: MutableList<MutableList<Char>>, x: Int, y: Int) : List<Int>
    {
        var xList = mutableMapOf<Int,Int>(
            0 to x,
            1 to x  ,
            2 to x+1,
            3 to x+1,
            4 to x+1,
            5 to x-1,
            6 to x-1,
            7 to x-1
        ).filter { (key, value) -> value in 0..8 }
        var yList = mutableMapOf<Int,Int>(
            0 to y+1,
            1 to y-1,
            2 to y-1,
            3 to y+1,
            4 to y,
            5 to y+1,
            6 to y-1,
            7 to y
        ).filter { (key, value) -> value in 0..8 }
        val returnList = mutableListOf<Int>()
        repeat(xList.size)
        {
            if(xList.containsKey(it)&&yList.containsKey(it))
            {
                returnList += xList.getValue(it) * 10 + (yList.getValue(it))
            }
        }
        return returnList

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