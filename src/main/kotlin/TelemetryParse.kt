package main.kotlin.hab.parse

import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.FileReader


private val columns = arrayOf(
    "count",
    "time",
    "latitude",
    "longitude",
    "altitude",
    "speed",
    "heading",
    "satellites",
    "internal temp",
    "voltage",
    "current",
    "BMEtemp",
    "pressure",
    "external temp"
)

fun main() {
    val bufferedReader = BufferedReader(FileReader(path))
    val packets: MutableList<TelemetryPacket> = mutableListOf()
    var line: List<String>

    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("data")

    val headerFont = workbook.createFont()
    headerFont.bold = true
    headerFont.fontHeightInPoints = 14.toShort()
    headerFont.color = IndexedColors.RED.getIndex()

    val headerCellStyle = workbook.createCellStyle()
    headerCellStyle.setFont(headerFont)

    val headerRow = sheet.createRow(0)

    for (i in 0 until columns.size) {
        val cell = headerRow.createCell(i)
        cell.setCellValue(columns[i])
        cell.cellStyle = headerCellStyle
    }


    bufferedReader.forEachLine {

        line = it.split(",")

        val packet = TelemetryPacket(
            line[1].toInt(),
            line[2].convertTime(),
            line[3].toDouble(),
            line[4].toDouble(),
            line[5].toInt(),
            line[6].toDouble(),
            line[7].toDouble(),
            line[8].toInt(),
            line[9].toDouble(),
            line[10].toDouble(),
            line[11].toDouble(),
            line[12].toDouble(),
            line[13].toInt(),
            line[14].split("*")[0].toDouble()
        )

        packets.add(packet)
    }


    var rowNum = 1
    for (p in packets) {
        val row = sheet.createRow(rowNum++)

        row.createCell(0)
            .setCellValue(p.count.toDouble())

        row.createCell(1)
            .setCellValue(p.time.toDouble())

        row.createCell(2)
            .setCellValue(p.lat)

        row.createCell(3)
            .setCellValue(p.long)

        row.createCell(4)
            .setCellValue(p.altitude.toDouble())

        row.createCell(5)
            .setCellValue(p.speed)

        row.createCell(6)
            .setCellValue(p.heading)

        row.createCell(7)
            .setCellValue(p.satellites.toDouble())

        row.createCell(8)
            .setCellValue(p.internalTemp)

        row.createCell(9)
            .setCellValue(p.voltage)

        row.createCell(10)
            .setCellValue(p.current)

        row.createCell(11)
            .setCellValue(p.BMEtemp)

        row.createCell(12)
            .setCellValue(p.pressure.toDouble())

        row.createCell(13)
            .setCellValue(p.externalTemp)

    }

    for (i in 0 until columns.size) {
        sheet.autoSizeColumn(i)
    }

    FileOutputStream("data.xlsx").use { fileOut -> workbook.write(fileOut) }
}

fun String.convertTime(): Int {
    val timestamp: List<String> = split(":")
    val hours = timestamp[0].toInt()
    val minutes = timestamp[1].toInt()
    val seconds = timestamp[2].toInt()

    return (seconds + 60 * minutes + 3600 * hours) - initTime
}

data class TelemetryPacket(
    val count: Int,
    val time: Int,
    val lat: Double,
    val long: Double,
    val altitude: Int,
    val speed: Double,
    val heading: Double,
    val satellites: Int,
    val internalTemp: Double,
    val voltage: Double,
    val current: Double,
    val BMEtemp: Double,
    val pressure: Int,
    val externalTemp: Double
)