package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SstpServer
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
import com.example.ui.theme.SpaceGroteskFontFamily
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TelemetryFastPing
import com.example.ui.theme.TelemetryMediumPing
import com.example.ui.theme.TelemetrySlowPing
import com.example.ui.theme.Tertiary
import com.example.ui.theme.VazirmatnFontFamily
import com.example.ui.theme.formatPersianDigits
import com.example.util.ClipboardHelper

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServersTab(
    uiState: MainUiState,
    onRefreshClick: () -> Unit,
    onSortSelected: (ServerSortOption) -> Unit,
    onCountrySelected: (String?) -> Unit,
    onBatchPingClick: () -> Unit,
    onSinglePingClick: (SstpServer) -> Unit,
    onCopyServerAddress: (String) -> Unit,
    onCopyCredentials: () -> Unit,
    onToggleFavorite: (SstpServer) -> Unit,
    onServerClick: (SstpServer) -> Unit
) {
    val context = LocalContext.current
    var showClientsCard by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 28.dp)
    ) {
        // 1. Status & Cache Sync Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("status_cache_banner"),
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
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SurfaceContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Storage,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "حافظه محلی: ${formatPersianDigits("${uiState.servers.size}", uiState.isPersianNumerals)} سرور ذخیره شده",
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = OnSurface
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(7.dp)
                                            .clip(CircleShape)
                                            .background(Secondary)
                                    )
                                    Text(
                                        text = "آخرین به‌روزرسانی: ۳ دقیقه پیش",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = onRefreshClick,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainer)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Refresh",
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(9999.dp),
                            color = SurfaceContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Sync,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "همگام‌سازی پس‌زمینه فعال (WorkManager)",
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = Secondary
                                )
                            }
                        }

                        Text(
                            text = "SSTP / Port 443",
                            fontFamily = JetBrainsMonoFontFamily,
                            fontSize = 12.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
        }

        // 2. Horizontal Filter Chips (Speed first and active)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Speed Chip (Default active)
                FilterChip(
                    selected = uiState.sortOption == ServerSortOption.SPEED,
                    onClick = { onSortSelected(ServerSortOption.SPEED) },
                    label = {
                        Text(
                            text = "بیشترین سرعت",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(9999.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = SurfaceContainer,
                        selectedContainerColor = Primary,
                        labelColor = OnSurfaceVariant,
                        selectedLabelColor = OnPrimary
                    )
                )

                // Ping Chip (< 80ms)
                FilterChip(
                    selected = uiState.sortOption == ServerSortOption.PING,
                    onClick = { onSortSelected(ServerSortOption.PING) },
                    label = {
                        Text(
                            text = "کمترین پینگ (< 80ms)",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Secondary
                        )
                    },
                    shape = RoundedCornerShape(9999.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = SurfaceContainer,
                        selectedContainerColor = Primary,
                        labelColor = OnSurfaceVariant,
                        selectedLabelColor = OnPrimary
                    )
                )

                // All Servers Chip
                FilterChip(
                    selected = uiState.sortOption == ServerSortOption.ALL && uiState.selectedCountry == null,
                    onClick = {
                        onSortSelected(ServerSortOption.ALL)
                        onCountrySelected(null)
                    },
                    label = {
                        Text(
                            text = "همه سرورها (${formatPersianDigits("${uiState.servers.size}", uiState.isPersianNumerals)})",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Dns,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    shape = RoundedCornerShape(9999.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = SurfaceContainer,
                        selectedContainerColor = Primary,
                        labelColor = OnSurfaceVariant,
                        selectedLabelColor = OnPrimary
                    )
                )

                // Country Chips: Japan, Korea, USA
                listOf("JP" to "ژاپن (JP)", "KR" to "کره جنوبی (KR)", "US" to "آمریکا (US)", "SG" to "سنگاپور (SG)", "DE" to "آلمان (DE)").forEach { (code, title) ->
                    val isSelected = uiState.selectedCountry == code
                    FilterChip(
                        selected = isSelected,
                        onClick = { onCountrySelected(if (isSelected) null else code) },
                        label = {
                            Text(
                                text = title,
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        },
                        shape = RoundedCornerShape(9999.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = SurfaceContainer,
                            selectedContainerColor = PrimaryContainer,
                            labelColor = OnSurfaceVariant,
                            selectedLabelColor = OnPrimaryContainer
                        )
                    )
                }
            }
        }

        // 3. Best Performing Featured Server Banner (Tokyo Relay #1 VIP Hero Card)
        item {
            val featured = uiState.featuredServer ?: uiState.servers.firstOrNull()
            featured?.let { server ->
                FeaturedServerHeroCard(
                    server = server,
                    isPersianNumerals = uiState.isPersianNumerals,
                    onCopyAddress = { onCopyServerAddress(server.displayAddress) },
                    onCopyCredentials = onCopyCredentials
                )
            }
        }

        // 4. Server Directory Title & Batch Ping Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "سرورهای آماده اتصال",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = OnSurface
                    )
                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = SurfaceContainerHighest
                    ) {
                        Text(
                            text = "${formatPersianDigits("${uiState.filteredServers.size}", uiState.isPersianNumerals)} گره",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(9999.dp),
                        color = SurfaceContainerHigh,
                        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(Primary.copy(alpha = 0.3f))),
                        modifier = Modifier.clickable { onBatchPingClick() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (uiState.isBatchTestingPing) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    strokeWidth = 2.dp,
                                    color = Primary
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(
                                text = "تست پینگ همگانی",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = Primary
                            )
                        }
                    }
                }
            }
        }

        // 5. Server List Cards
        items(
            items = uiState.filteredServers,
            key = { it.id }
        ) { server ->
            val isTesting = uiState.testingServerIds.contains(server.id)
            val testedAgo = uiState.testedAgoMap[server.id]

            ServerCardItem(
                server = server,
                isTesting = isTesting,
                testedAgoText = testedAgo,
                isPersianNumerals = uiState.isPersianNumerals,
                onCopyAddress = { onCopyServerAddress(server.displayAddress) },
                onSinglePing = { onSinglePingClick(server) }
            )
        }

        // 6. SSTP Clients Google Play Download Section (Dismissable)
        if (showClientsCard) {
            item {
                SstpClientsDownloadSection(
                    onDismiss = { showClientsCard = false }
                )
            }
        }

        // 7. Android Quick Guide Tip Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Secondary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "راهنمای راه‌اندازی سریع در اندروید",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = OnSurface
                        )
                        Text(
                            text = "آدرس سرور را کپی کنید، به بخش VPN سیستم‌عامل رفته و با انتخاب پروتکل SSTP، نام کاربری و گذرواژه را vpn وارد نمایید.",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedServerHeroCard(
    server: SstpServer,
    isPersianNumerals: Boolean,
    onCopyAddress: () -> Unit,
    onCopyCredentials: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("featured_vip_hero_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(Primary.copy(alpha = 0.4f)))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header: Star Title & Live Ping Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Tertiary.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "★",
                            color = Tertiary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "سرور برگزیده هوشمند • بالاترین پهنای باند",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Tertiary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Secondary.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(Secondary)
                        )
                        Text(
                            text = "${formatPersianDigits("42", isPersianNumerals)} ms • زنده",
                            fontFamily = JetBrainsMonoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = Secondary
                        )
                    }
                }
            }

            // Country & Host Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "🇯🇵", fontSize = 28.sp)
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "ژاپن (Tokyo Relay #1)",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = OnSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Primary.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "VIP",
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                )
                            }
                        }
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = "public-vpn-92.opengw.net:443",
                                fontFamily = JetBrainsMonoFontFamily,
                                fontSize = 13.sp,
                                color = Primary
                            )
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "پروتکل شبکه",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = "SSTP : 443",
                        fontFamily = JetBrainsMonoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Secondary
                    )
                }
            }

            // 3 Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HeroStatBlock(
                    label = "بالاترین سرعت",
                    value = "${formatPersianDigits("85.4", isPersianNumerals)} Mbps",
                    valueColor = Primary,
                    modifier = Modifier.weight(1f)
                )
                HeroStatBlock(
                    label = "پایداری اتصال",
                    value = "${formatPersianDigits("99.8", isPersianNumerals)}%",
                    valueColor = Secondary,
                    modifier = Modifier.weight(1f)
                )
                HeroStatBlock(
                    label = "وضعیت دسترسی",
                    value = "آزاد (Ready)",
                    valueColor = Secondary,
                    modifier = Modifier.weight(1f)
                )
            }

            // Actions
            Button(
                onClick = onCopyAddress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary)
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp), tint = OnPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "کپی آدرس سرور و اتصال مستقیم",
                    fontFamily = VazirmatnFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = OnPrimary
                )
            }

            OutlinedButton(
                onClick = onCopyCredentials,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(OutlineVariant))
            ) {
                Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = Tertiary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "کپی اطلاعات ورود (شناسه: vpn / گذرواژه: vpn)",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    color = OnSurface
                )
            }
        }
    }
}

@Composable
fun HeroStatBlock(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(10.dp)),
        color = SurfaceContainerHigh
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontFamily = VazirmatnFontFamily,
                fontSize = 11.sp,
                color = OnSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontFamily = JetBrainsMonoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = valueColor
            )
        }
    }
}

@Composable
fun ServerCardItem(
    server: SstpServer,
    isTesting: Boolean,
    testedAgoText: String?,
    isPersianNumerals: Boolean,
    onCopyAddress: () -> Unit,
    onSinglePing: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("server_card_${server.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(if (server.isUsed) Error.copy(alpha = 0.35f) else OutlineVariant)
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Flag, Name, NodeCode, Address, Copy Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = server.countryFlag, fontSize = 20.sp)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = server.farsiRegionTitle,
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = OnSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SurfaceContainerHigh
                            ) {
                                Text(
                                    text = server.nodeCode,
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontSize = 10.sp,
                                    color = OnSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = server.displayAddressWithPort,
                                fontFamily = JetBrainsMonoFontFamily,
                                fontSize = 12.sp,
                                color = OnSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onCopyAddress,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Hostname",
                        tint = Primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Telemetry Badges Strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Speed Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Primary.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null, tint = Primary, modifier = Modifier.size(13.dp))
                        Text(
                            text = "${formatPersianDigits(String.format("%.1f", server.speedMbps), isPersianNumerals)} Mbps",
                            fontFamily = JetBrainsMonoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Primary
                        )
                    }
                }

                // Ping Badge
                val effectivePing = server.measuredPingMs ?: server.pingMs
                val (pingColor, pingDotColor) = when {
                    effectivePing < 100 -> TelemetryFastPing to TelemetryFastPing
                    effectivePing <= 250 -> TelemetryMediumPing to TelemetryMediumPing
                    else -> TelemetrySlowPing to TelemetrySlowPing
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = pingColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(pingDotColor))
                        Text(
                            text = "${formatPersianDigits("$effectivePing", isPersianNumerals)} ms",
                            fontFamily = JetBrainsMonoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = pingColor
                        )
                    }
                }

                // Users or Stability
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SurfaceContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Group, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(13.dp))
                        Text(
                            text = "${formatPersianDigits("${server.numVpnSessions}", isPersianNumerals)} کاربر",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }

                // Ping Test Action Pill
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (testedAgoText != null) Secondary.copy(alpha = 0.15f) else SurfaceContainerHigh,
                    modifier = Modifier
                        .clickable(enabled = !isTesting) { onSinglePing() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (isTesting) {
                            CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.5.dp, color = Primary)
                            Text("در حال پینگ...", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = Primary)
                        } else if (testedAgoText != null) {
                            Icon(imageVector = Icons.Default.DoneAll, contentDescription = null, tint = Secondary, modifier = Modifier.size(14.dp))
                            Text(testedAgoText, fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = Secondary)
                        } else {
                            Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = Tertiary, modifier = Modifier.size(14.dp))
                            Text("تست پینگ", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                    }
                }
            }

            // Footer Row: Credentials / Status Badge + 70% Width Copy Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "شناسه: vpn / vpn",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 12.sp,
                        color = OnSurfaceVariant
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (server.isUsed) Error else Secondary)
                        )
                        Text(
                            text = if (server.isUsed) "استفاده شده" else "آماده اتصال",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            color = if (server.isUsed) Error else Secondary
                        )
                    }
                }

                Button(
                    onClick = onCopyAddress,
                    modifier = Modifier
                        .fillMaxWidth(0.72f)
                        .height(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (server.isUsed) SurfaceContainerHigh else Primary.copy(alpha = 0.2f),
                        contentColor = Primary
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = SolidColor(if (server.isUsed) OutlineVariant else Primary.copy(alpha = 0.4f))
                    )
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp), tint = Primary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "کپی آدرس و اتصال",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Primary
                    )
                }
            }
        }
    }
}

@Composable
fun SstpClientsDownloadSection(onDismiss: () -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(OutlineVariant.copy(alpha = 0.3f)))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Shop, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "دانلود کلاینت‌های SSTP از گوگل پلی",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = OnSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(9999.dp),
                                color = Secondary.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "رسمی و رایگان",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    color = Secondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "برای اتصال مستقیم به سرورهای فوق، یکی از اپلیکیشن‌های زیر را نصب کنید",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                }
            }

            // App 1: SSTP VPN Client (Top Recommended)
            ClientAppDownloadCard(
                title = "SSTP VPN Client",
                badge = "پیشنهاد برتر",
                rating = "4.5",
                description = "توسعه‌دهنده Freedom Systems • سبک، سریع، سازگار کامل با پروتکل اندروید بدون نیاز به روت",
                buttonText = "دریافت از Google Play",
                icon = Icons.Default.VpnLock,
                isPrimary = true,
                onDownload = {
                    openPlayStore(context, "it.colucciweb.sstpvpnclient", "sstp vpn client")
                }
            )

            // App 2: MS-SSTP VPN Client
            ClientAppDownloadCard(
                title = "MS-SSTP VPN Client",
                badge = "پیشرفته",
                rating = "4.3",
                description = "مسیریابی پیشرفته دامنه‌ها (Split Tunneling)، اتصال مجدد خودکار و پایداری در شبکه همراه",
                buttonText = "نصب رایگان",
                icon = Icons.Default.Tune,
                isPrimary = false,
                onDownload = {
                    openPlayStore(context, "org.toruneko.ms_sstp_vpn", "ms sstp vpn")
                }
            )

            // App 3: VPNGate SSTP Connect
            ClientAppDownloadCard(
                title = "VPNGate SSTP Connect",
                badge = "ویژه VPN Gate",
                rating = "4.2",
                description = "سازگار و بهینه‌سازی شده اختصاصی برای گره‌های رله عمومی و درگاه ۴۴۳ دانشگاه تسوکوبا",
                buttonText = "نصب رایگان",
                icon = Icons.Default.VpnLock,
                isPrimary = false,
                onDownload = {
                    openPlayStore(context, "com.vpngate.sstp", "vpngate sstp")
                }
            )

            // Footer Protect Note
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = Secondary, modifier = Modifier.size(16.dp))
                    Text(
                        text = "تمامی برنامه‌ها توسط گوگل‌پلی پروتکت اسکن و تایید شده‌اند",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = OnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ClientAppDownloadCard(
    title: String,
    badge: String,
    rating: String,
    description: String,
    buttonText: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPrimary: Boolean,
    onDownload: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceContainer,
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(if (isPrimary) Primary.copy(alpha = 0.3f) else OutlineVariant.copy(alpha = 0.2f))
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isPrimary) PrimaryContainer else SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (isPrimary) OnPrimaryContainer else Primary, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = title,
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = OnSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (isPrimary) Primary.copy(alpha = 0.15f) else SurfaceContainerHigh
                            ) {
                                Text(
                                    text = badge,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 10.sp,
                                    color = if (isPrimary) Primary else OnSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Tertiary, modifier = Modifier.size(13.dp))
                                Text(text = rating, fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Tertiary)
                            }
                        }
                    }
                }
            }

            Text(
                text = description,
                fontFamily = VazirmatnFontFamily,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = OnSurfaceVariant
            )

            Button(
                onClick = onDownload,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPrimary) Primary else SurfaceContainerHigh,
                    contentColor = if (isPrimary) OnPrimary else OnSurface
                )
            ) {
                Icon(
                    imageVector = if (isPrimary) Icons.Default.Download else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = buttonText,
                    fontFamily = VazirmatnFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

fun openPlayStore(context: Context, packageName: String, query: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (_: Exception) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=$query&c=apps"))
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(webIntent)
    }
}
