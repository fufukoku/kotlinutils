package com.fufukoku.utils

import java.io.InputStream

/**
 * FastScanner for competitive programming / fast stdin parsing.
 *
 * - Token-based reading (whitespace separated)
 * - Efficient number parsing without intermediate String allocation
 * - Provides nextLine() with CRLF/LF handling
 */
class FastScanner(
    private val input: InputStream = System.`in`,
    bufferSize: Int = DEFAULT_BUFFER_SIZE
) {
    private val buffer: ByteArray = ByteArray(bufferSize)
    private var bufferLimit: Int = 0     // number of bytes currently in buffer
    private var bufferIndex: Int = 0     // next read position

    // One-byte pushback so nextLine() can handle \r\n cleanly.
    private var pushedBackByte: Int = NO_PUSHBACK

    private fun readByte(): Int {
        if (pushedBackByte != NO_PUSHBACK) {
            val value = pushedBackByte
            pushedBackByte = NO_PUSHBACK
            return value
        }

        if (bufferIndex >= bufferLimit) {
            bufferLimit = input.read(buffer)
            bufferIndex = 0
            if (bufferLimit <= 0) return EOF
        }
        return buffer[bufferIndex++].toInt()
    }

    private fun pushBack(byteValue: Int) {
        pushedBackByte = byteValue
    }

    private fun readNextNonWhitespaceByte(): Int {
        var byteValue = readByte()
        while (byteValue != EOF && byteValue <= SPACE) {
            byteValue = readByte()
        }
        return byteValue
    }

    fun nextInt(): Int? {
        var byteValue = readNextNonWhitespaceByte()
        if (byteValue == EOF) return null

        var sign = 1
        if (byteValue == MINUS) {
            sign = -1
            byteValue = readByte()
        }

        var result = 0
        while (byteValue > SPACE && byteValue != EOF) {
            result = result * 10 + (byteValue - ZERO)
            byteValue = readByte()
        }
        return result * sign
    }

    fun nextLong(): Long? {
        var byteValue = readNextNonWhitespaceByte()
        if (byteValue == EOF) return null

        var sign = 1L
        if (byteValue == MINUS) {
            sign = -1L
            byteValue = readByte()
        }

        var result = 0L
        while (byteValue > SPACE && byteValue != EOF) {
            result = result * 10L + (byteValue - ZERO).toLong()
            byteValue = readByte()
        }
        return result * sign
    }

    fun nextDouble(): Double? {
        // Double parsing is rarely the bottleneck; parse token as String for simplicity.
        val token = nextString() ?: return null
        return token.toDouble()
    }

    fun nextChar(): Char? {
        val byteValue = readNextNonWhitespaceByte()
        return if (byteValue == EOF) null else byteValue.toChar()
    }

    fun nextString(): String? {
        var byteValue = readNextNonWhitespaceByte()
        if (byteValue == EOF) return null

        val sb = StringBuilder(16)
        while (byteValue > SPACE && byteValue != EOF) {
            sb.append(byteValue.toChar())
            byteValue = readByte()
        }
        return sb.toString()
    }

    /**
     * Reads the rest of the current line (or next line if currently at line break).
     * Handles both \n and \r\n.
     *
     * Returns null on EOF (no more data).
     */
    fun nextLine(): String? {
        var byteValue = readByte()
        if (byteValue == EOF) return null

        // If we are at line breaks, consume them first (support blank lines too).
        while (byteValue == LF || byteValue == CR) {
            // Handle \r\n as a single line break
            if (byteValue == CR) {
                val next = readByte()
                if (next != LF && next != EOF) pushBack(next)
            }
            byteValue = readByte()
            if (byteValue == EOF) return "" // line break at end -> empty last line
        }

        val sb = StringBuilder(32)
        while (byteValue != EOF && byteValue != LF && byteValue != CR) {
            sb.append(byteValue.toChar())
            byteValue = readByte()
        }

        // If line ended with \r, optionally consume a following \n
        if (byteValue == CR) {
            val next = readByte()
            if (next != LF && next != EOF) pushBack(next)
        }

        return sb.toString()
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 1 shl 16 // 65536
        private const val EOF = -1
        private const val NO_PUSHBACK = Int.MIN_VALUE

        private const val SPACE = 32
        private const val ZERO = 48
        private const val MINUS = 45
        private const val LF = 10
        private const val CR = 13
    }
}
