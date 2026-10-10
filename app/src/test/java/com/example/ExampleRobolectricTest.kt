package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.remote.VpnGateParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("VPN Gate SSTP", appName)
    }

    @Test
    fun `parse csv correctly extracts sstp servers`() {
        val mockCsv = """
            #HostName,IP,Score,Ping,Speed,CountryLong,CountryShort,NumVpnSessions,Uptime,TotalUsers,TotalTraffic,LogType,Operator,Message,OpenVPN_ConfigData_Base64
            vg123.opengw.net,133.242.1.1,1000000,25,50000000,Japan,JP,10,86400,500,1000000,2w,TestOp,TestMsg,
            vg456.opengw.net,198.51.100.2,800000,45,30000000,United States,US,5,43200,200,500000,2w,TestOp2,TestMsg2,
            *
        """.trimIndent()

        val parsed = VpnGateParser.parse(mockCsv)
        assertEquals(2, parsed.size)
        val first = parsed.first()
        assertEquals("vg123.opengw.net", first.hostname)
        assertEquals("133.242.1.1", first.ip)
        assertEquals(443, first.port)
        assertEquals(1000000L, first.score)
        assertEquals("Japan", first.countryLong)
        assertEquals("JP", first.countryShort)
        assertEquals("🇯🇵", first.countryFlag)
    }
}
