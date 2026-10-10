package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Error
import com.example.ui.theme.GhostBorder
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.Outline
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.VazirmatnFontFamily
import com.example.ui.theme.formatPersianDigits

@Composable
fun SyncAndCacheTab(
    uiState: MainUiState,
    onForceSyncClick: () -> Unit,
    onBgSyncToggle: (Boolean) -> Unit,
    onIntervalSelected: (Long) -> Unit,
    onWifiOnlyToggle: (Boolean) -> Unit,
    onBatteryNotLowToggle: (Boolean) -> Unit,
    onAutoPurgeToggle: (Boolean) -> Unit,
    onMaxPingChanged: (Int) -> Unit,
    onClearTempCacheClick: () -> Unit,
    onResetDatabaseClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp)
    ) {
        // 1. Live Cache Health Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("cache_health_hero_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Status Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Secondary)
                            )
                            Text(
                                text = "وضعیت کش محلی: سالم و به‌روز",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Secondary
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(9999.dp),
                            color = SurfaceContainerHighest
                        ) {
                            Text(
                                text = "ROOM DB: v4.2",
                                fontFamily = JetBrainsMonoFontFamily,
                                fontSize = 11.sp,
                                color = OnSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // 4-item Telemetry Grid
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceContainerLowest.copy(alpha = 0.8f),
                        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "سرورهای ذخیره شده",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = Outline
                                    )
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text(
                                            text = formatPersianDigits("${uiState.servers.size}", uiState.isPersianNumerals),
                                            fontFamily = JetBrainsMonoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = Primary
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "نود SSTP فعال",
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 12.sp,
                                            color = OnSurfaceVariant
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "آخرین همگام‌سازی",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = Outline
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = Secondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "۳ دقیقه پیش",
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 13.sp,
                                            color = OnSurface
                                        )
                                    }
                                }
                            }

                            // WorkManager Next Cycle Progress
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "چرخه بعدی WorkManager",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = Outline
                                    )
                                    Text(
                                        text = "${formatPersianDigits("۲۷", uiState.isPersianNumerals)} دقیقه دیگر",
                                        fontFamily = JetBrainsMonoFontFamily,
                                        fontSize = 12.sp,
                                        color = Tertiary
                                    )
                                }
                                LinearProgressIndicator(
                                    progress = { 0.32f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(9999.dp)),
                                    color = Primary,
                                    trackColor = SurfaceContainerHighest
                                )
                            }
                        }
                    }

                    // Force Sync Button
                    val rotation = if (uiState.isRefreshing) {
                        val transition = rememberInfiniteTransition(label = "spin")
                        val angle by transition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(tween(900, easing = LinearEasing)),
                            label = "spinAngle"
                        )
                        angle
                    } else 0f

                    Button(
                        onClick = onForceSyncClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(rotation),
                            tint = OnPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "همگام‌سازی دستی فوری (Force Sync)",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = OnPrimary
                        )
                    }
                }
            }
        }

        // 2. Background Sync / WorkManager Configuration
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Autorenew, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                            Text(
                                text = "همگام‌سازی پس‌زمینه (WorkManager)",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = OnSurface
                            )
                        }

                        Switch(
                            checked = uiState.bgSyncEnabled,
                            onCheckedChange = onBgSyncToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = OnPrimary,
                                checkedTrackColor = PrimaryContainer
                            )
                        )
                    }

                    Text(
                        text = "دریافت مستمر و اعتبارسنجی خودکار تونل‌های SSTP جهت اتصال بدون تاخیر حتی در زمان بسته بودن برنامه.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = OnSurfaceVariant
                    )

                    // Interval Frequency Buttons
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "فاصله زمانی به‌روزرسانی نودها:",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            color = Outline
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                15L to "۱۵ دقیقه",
                                30L to "۳۰ دقیقه",
                                60L to "۱ ساعت",
                                360L to "۶ ساعت"
                            ).forEach { (minutes, label) ->
                                val isSelected = uiState.syncIntervalMinutes == minutes
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onIntervalSelected(minutes) },
                                    color = if (isSelected) PrimaryContainer else SurfaceContainer
                                ) {
                                    Text(
                                        text = label,
                                        fontFamily = VazirmatnFontFamily,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp,
                                        color = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    // Constraints Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "قیود اجرای خودکار (Constraints):",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            color = Outline
                        )

                        // Wi-Fi Only
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Wifi, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                                Text(
                                    text = "تنها هنگام اتصال به وای‌فای (Wi-Fi Only)",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 14.sp,
                                    color = OnSurface
                                )
                            }
                            Switch(
                                checked = uiState.wifiOnly,
                                onCheckedChange = onWifiOnlyToggle,
                                colors = SwitchDefaults.colors(checkedThumbColor = OnPrimary, checkedTrackColor = PrimaryContainer)
                            )
                        }

                        // Battery Not Low
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.BatteryChargingFull, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                                Text(
                                    text = "عدم اجرا در حالت کمبود باتری",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 14.sp,
                                    color = OnSurface
                                )
                            }
                            Switch(
                                checked = uiState.batteryNotLow,
                                onCheckedChange = onBatteryNotLowToggle,
                                colors = SwitchDefaults.colors(checkedThumbColor = OnPrimary, checkedTrackColor = PrimaryContainer)
                            )
                        }

                        // Backoff Badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceContainer
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Replay, contentDescription = null, tint = Secondary, modifier = Modifier.size(18.dp))
                                    Text(
                                        text = "تلاش مجدد تصاعدی در صورت خطا (Backoff)",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = OnSurface
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(9999.dp),
                                    color = SecondaryContainer
                                ) {
                                    Text(
                                        text = "فعال",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Ingestion & Protocol Filters
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                        Text(
                            text = "فیلترهای کش و دریافت سرور",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = OnSurface
                        )
                    }

                    // Strict SSTP
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = SurfaceContainer
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "فیلتر سخت‌گیرانه پروتکل SSTP",
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = OnSurface
                                )
                                Text(
                                    text = "فقط سرورهای دارای پورت ۴۴۳ SSL ذخیره می‌شوند",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 12.sp,
                                    color = Outline
                                )
                            }
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                        }
                    }

                    // Auto Purge Dead Nodes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "حذف خودکار سرورهای منقضی (Dead Nodes)",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 14.sp,
                                color = OnSurface
                            )
                            Text(
                                text = "پاکسازی رکوردهایی که بیش از ۳ بار پینگ نگرفته‌اند",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 12.sp,
                                color = Outline
                            )
                        }
                        Switch(
                            checked = uiState.autoPurgeDeadNodes,
                            onCheckedChange = onAutoPurgeToggle,
                            colors = SwitchDefaults.colors(checkedThumbColor = OnPrimary, checkedTrackColor = PrimaryContainer)
                        )
                    }

                    // Max Latency Slider
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "حداکثر تاخیر مجاز برای کش:",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 14.sp,
                                color = OnSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SurfaceContainerHighest
                            ) {
                                Text(
                                    text = "< ${formatPersianDigits("${uiState.maxPingThreshold}", uiState.isPersianNumerals)} ms",
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Secondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Slider(
                            value = uiState.maxPingThreshold.toFloat(),
                            onValueChange = { onMaxPingChanged(it.toInt()) },
                            valueRange = 80f..300f,
                            steps = 21,
                            colors = SliderDefaults.colors(
                                thumbColor = Primary,
                                activeTrackColor = Primary,
                                inactiveTrackColor = SurfaceContainerHighest
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("80ms", fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Outline)
                            Text("150ms", fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Outline)
                            Text("300ms", fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Outline)
                        }
                    }
                }
            }
        }

        // 4. Storage & Maintenance
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Storage, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                        Text(
                            text = "مدیریت حافظه و پایگاه داده",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = OnSurface
                        )
                    }

                    // 2 Stat cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            color = SurfaceContainer
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("پایگاه داده (Room DB)", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = Outline)
                                Text(uiState.roomDbSizeText, fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OnSurface)
                                Text("شامل هش و لاگ‌ها", fontFamily = VazirmatnFontFamily, fontSize = 10.sp, color = Secondary)
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            color = SurfaceContainer
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("کش موقت حافظه (RAM)", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = Outline)
                                Text(uiState.ramCacheSizeText, fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OnSurface)
                                Text("پاسخ‌های فشرده", fontFamily = VazirmatnFontFamily, fontSize = 10.sp, color = Primary)
                            }
                        }
                    }

                    // Action buttons
                    Button(
                        onClick = onClearTempCacheClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh, contentColor = OnSurface)
                    ) {
                        Icon(imageVector = Icons.Default.CleaningServices, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("پاک‌سازی حافظه کش موقت (Clear Temp)", fontFamily = VazirmatnFontFamily, fontSize = 14.sp)
                    }

                    Button(
                        onClick = onResetDatabaseClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Error.copy(alpha = 0.2f), contentColor = Error)
                    ) {
                        Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("بازنشانی کامل دیتابیس و دریافت مجدد (Purge All)", fontFamily = VazirmatnFontFamily, fontSize = 14.sp)
                    }
                }
            }
        }

        // 5. Recent Sync Activity Logs
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.History, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                            Text(
                                text = "گزارش رویدادهای اخیر (Sync Log)",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = OnSurface
                            )
                        }
                        Text("۳ رویداد آخر", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = Outline)
                    }

                    uiState.syncLogs.forEach { log ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SurfaceContainer
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (log.isWarning) Icons.Default.Warning else Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = if (log.isWarning) Tertiary else Secondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(log.title, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = OnSurface)
                                        Text(log.time, fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Outline)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(log.description, fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                                        Text(log.durationOrStatus, fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = if (log.isWarning) Tertiary else Secondary)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
