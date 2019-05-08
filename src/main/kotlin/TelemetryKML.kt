package main.kotlin.hab.parse

import java.io.*

fun main() {
    val bufferedReader = BufferedReader(FileReader(File(path)))
    val printWriter = PrintWriter(FileWriter("path.kml"))
    var line1: List<String>
    var line2: List<String>


    printWriter.println(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "\n<kml xmlns=\"http://www.opengis.net/kml/2.2\"" +
                " xmlns:gx=\"http://www.google.com/kml/ext/2.2\"" +
                " xmlns:atom=\"http://www.w3.org/2005/Atom\">" +
                "\n<Document>" +
                "\n<name>path.kml</name>" +
                "\n<open>1</open>\n<Style id=\"style\">\n" +
                "<PolyStyle>\n" +
                "<color>ff0000cc</color>\n" +
                "<colorMode>normal</colorMode>\n" +
                "</PolyStyle>\n" +
                "</Style>"
    )

    line2 = bufferedReader.readLine().split(",")

    for (i in 0..countLines(path)) {

        line1 = line2

        printWriter.print(
            "<Placemark>\n<name>$i</name>" +
                    "\n<styleUrl>#style</styleUrl>" +
                    "\n<LineString>\n<extrude>1</extrude>" +
                    "\n<altitudeMode>relativeToSeaFloor</altitudeMode>" +
                    "\n<coordinates>" +
                    "\n${line1[4]},${line1[3]},${line1[5]}"
        )

        line2 = bufferedReader.readLine().split(",")

        printWriter.print(
            " ${line2[4]},${line2[3]},${line2[5]}$" +
                    "\n</coordinates>" +
                    "\n</LineString>" +
                    "\n</Placemark>\n"
        )
    }
    printWriter.print(
        "</Document>" +
                "\n</kml>"
    )

    printWriter.close()
}


fun countLines(filename: String): Int {
    val reader = LineNumberReader(FileReader(filename))
    val cnt: Int
    while (reader.readLine() != null) {
    }

    cnt = reader.lineNumber
    reader.close()
    return cnt - 2
}