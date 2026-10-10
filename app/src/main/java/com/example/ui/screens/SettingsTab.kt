package com.example.ui.screens

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
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.ViewCompact
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Error
import com.example.ui.theme.GhostBorder
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondary
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
import com.example.ui.theme.TertiaryContainer
import com.example.ui.theme.VazirmatnFontFamily
import com.example.ui.theme.formatPersianDigits
import com.example.ui.theme.getFontFamilyByChoice

@Composable
fun SettingsTab(
    uiState: MainUiState,
    onFontFamilySelected: (String) -> Unit,
    onFontSizeSelected: (String) -> Unit,
    onBoldTextToggle: (Boolean) -> Unit,
    onPersianNumeralsToggle: (Boolean) -> Unit,
    onGlassmorphismToggle: (Boolean) -> Unit,
    onCompactDensityToggle: (Boolean) -> Unit,
    onAutoCopyToggle: (Boolean) -> Unit,
    onFastAlertToggle: (Boolean) -> Unit,
    onClearCacheClick: () -> Unit,
    onResetPreferencesClick: () -> Unit
) {
    val activeFontFamily = getFontFamilyByChoice(uiState.fontFamilyChoice)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp)
    ) {
        // Status & Engine Overview Hero Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("settings_hero_card"),
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
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = Primary, modifier = Modifier.size(24.dp))
                            }
                            Column {
                                Text(
                                    text = "تنظیمات هسته SSTP Finder",
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = OnSurface
                                )
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Secondary))
                                    Text(
                                        text = "وضعیت کلی برنامه: آماده به‌کار و پایدار",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = Secondary
                                    )
                                }
                            }
                        }

                        Surface(shape = RoundedCornerShape(9999.dp), color = SurfaceContainerHigh) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Primary, modifier = Modifier.size(13.dp))
                                Text("v2.4.0 (184)", fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = OnSurface)
                            }
                        }
                    }

                    // 3 Micro stats bar
                    Surface(shape = RoundedCornerShape(10.dp), color = SurfaceContainer.copy(alpha = 0.6f)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("موتور رندر", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                                Text("Vazirmatn M3", fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Primary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("پروتکل پایه", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                                Text("SSTP/SSL 443", fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Secondary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("پاسخ‌دهی پینگ", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                                Text("Real-time", fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Tertiary)
                            }
                        }
                    }
                }
            }
        }

        // Section 1: Typography & Font Engine (Primary Spotlight)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.FontDownload, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                            Text(
                                text = "شخصی‌سازی فونت‌های عمومی",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = OnSurface
                            )
                        }
                        Surface(shape = RoundedCornerShape(9999.dp), color = PrimaryContainer) {
                            Text(
                                text = "جدید",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = OnPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "فونت تمامی عنوان‌ها، جدول‌های مانیتورینگ تله‌متری و داده‌های اتصال را متناسب با سلیقه خود تغییر دهید.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 13.sp,
                        color = OnSurfaceVariant
                    )

                    // 4 Font Choices
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FontChoiceCard(
                            title = "وزیرمتن (Vazirmatn)",
                            sub = "طراحی مدرن، خوانایی بی‌نقص در صفحه‌نمایش‌های Amoled",
                            tag = "پیش‌فرض",
                            avatarLetter = "ف",
                            isSelected = uiState.fontFamilyChoice == "Vazirmatn",
                            onClick = { onFontFamilySelected("Vazirmatn") }
                        )

                        FontChoiceCard(
                            title = "ایران‌سنس (IRANSansX)",
                            sub = "حرفه‌ای، تراز و متقارن برای داشبوردهای سازمانی",
                            avatarLetter = "ای",
                            isSelected = uiState.fontFamilyChoice == "IRANSansX",
                            onClick = { onFontFamilySelected("IRANSansX") }
                        )

                        FontChoiceCard(
                            title = "شبنم (Shabnam)",
                            sub = "هندسی، صمیمی با توازن دلنشین کلمات",
                            avatarLetter = "ش",
                            isSelected = uiState.fontFamilyChoice == "Shabnam",
                            onClick = { onFontFamilySelected("Shabnam") }
                        )

                        FontChoiceCard(
                            title = "سنس سیستمی (System / Roboto)",
                            sub = "فونت پیش‌فرض فریم‌ورک سیستم‌عامل اندروید",
                            avatarLetter = "Aa",
                            isSelected = uiState.fontFamilyChoice == "System",
                            onClick = { onFontFamilySelected("System") }
                        )
                    }

                    // Live Interactive Preview Box
                    Surface(shape = RoundedCornerShape(12.dp), color = SurfaceContainerLowest) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                    Text("پیش‌نمایش زنده فونت", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = Primary)
                                }
                                Text("${uiState.fontFamilyChoice} • Normal", fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                            }

                            val previewFontSize = when (uiState.fontSizeChoice) {
                                "small" -> 13.sp
                                "large" -> 17.sp
                                "xlarge" -> 19.sp
                                else -> 15.sp
                            }

                            Text(
                                text = "نمونه متن: اتصال به سرورهای SSTP پرسرعت و امن با پورت ۴۴۳",
                                fontFamily = activeFontFamily,
                                fontWeight = if (uiState.isBoldText) FontWeight.Bold else FontWeight.Normal,
                                fontSize = previewFontSize,
                                lineHeight = 24.sp,
                                color = OnSurface,
                                modifier = Modifier.padding(top = 2.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (uiState.isPersianNumerals) "۰ ۱ ۲ ۳ ۴ ۵ ۶ ۷ ۸ ۹" else "0 1 2 3 4 5 6 7 8 9",
                                    fontFamily = activeFontFamily,
                                    fontSize = 13.sp,
                                    color = Secondary
                                )
                                Text(
                                    text = "Ping: 34ms | SSL 1.3",
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontSize = 11.sp,
                                    color = Primary
                                )
                            }
                        }
                    }

                    // Font Size Scale Segmented Controls
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.FormatSize, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                                Text("اندازه قلم متون", fontFamily = VazirmatnFontFamily, fontSize = 14.sp, color = OnSurface)
                            }
                            Surface(shape = RoundedCornerShape(9999.dp), color = Primary.copy(alpha = 0.15f)) {
                                val label = when (uiState.fontSizeChoice) {
                                    "small" -> "کوچک (۸۵٪)"
                                    "large" -> "بزرگ (۱۱۵٪)"
                                    "xlarge" -> "خیلی بزرگ (۱۳۰٪)"
                                    else -> "استاندارد (۱۰۰٪)"
                                }
                                Text(label, fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = Primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainer)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf(
                                "small" to "کوچک",
                                "standard" to "استاندارد",
                                "large" to "بزرگ",
                                "xlarge" to "خیلی بزرگ"
                            ).forEach { (sizeKey, sizeLabel) ->
                                val isSelected = uiState.fontSizeChoice == sizeKey
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onFontSizeSelected(sizeKey) },
                                    color = if (isSelected) Primary else Color.Transparent
                                ) {
                                    Text(
                                        text = sizeLabel,
                                        fontFamily = VazirmatnFontFamily,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 12.sp,
                                        color = if (isSelected) OnPrimary else OnSurfaceVariant,
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    // Toggles: Bold & Persian Digits
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Bold Toggle
                        SettingToggleRow(
                            icon = Icons.Default.FormatBold,
                            title = "افزایش ضخامت خطوط (Bold)",
                            subtitle = "خوانایی بالاتر تیترها و برچسب‌های شبکه",
                            checked = uiState.isBoldText,
                            onCheckedChange = onBoldTextToggle
                        )

                        // Persian Digits Toggle
                        SettingToggleRow(
                            icon = Icons.Default.Pin,
                            title = "ارقام فارسی",
                            subtitle = "نمایش اعداد به صورت فارسی (۱۲۳۴۵۶۷۸۹۰)",
                            checked = uiState.isPersianNumerals,
                            onCheckedChange = onPersianNumeralsToggle
                        )
                    }
                }
            }
        }

        // Section 2: General UI & Appearance
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Palette, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                        Text("ظاهر و رابط کاربری", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = OnSurface)
                    }

                    // OLED Dark Mode
                    Surface(shape = RoundedCornerShape(10.dp), color = SurfaceContainer) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceContainerHigh),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.DarkMode, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text("حالت تم تاریک پیشرفته", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurface)
                                    Text("بهینه‌سازی شده برای پنل‌های اولد (OLED Deep Dark)", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                                }
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = SurfaceContainerHigh) {
                                Text("فعال", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = Primary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    }

                    // Card Density
                    Surface(shape = RoundedCornerShape(10.dp), color = SurfaceContainer) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceContainerHigh),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.ViewCompact, contentDescription = null, tint = OnSurface, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text("تراکم کارت‌های سرور", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurface)
                                    Text("نمایش فشرده برای مشاهده همزمان گره‌های بیشتر", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceContainerHigh)
                                    .padding(2.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (uiState.isCompactDensity) Primary else Color.Transparent,
                                    modifier = Modifier.clickable { onCompactDensityToggle(true) }
                                ) {
                                    Text("فشرده", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = if (uiState.isCompactDensity) OnPrimary else OnSurfaceVariant, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                }
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (!uiState.isCompactDensity) Primary else Color.Transparent,
                                    modifier = Modifier.clickable { onCompactDensityToggle(false) }
                                ) {
                                    Text("استاندارد", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = if (!uiState.isCompactDensity) OnPrimary else OnSurfaceVariant, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                                }
                            }
                        }
                    }

                    // Glassmorphism
                    SettingToggleRow(
                        icon = Icons.Default.BlurOn,
                        title = "جلوه‌های محو و شیشه‌ای",
                        subtitle = "رندر انیمیشن‌ها و پس‌زمینه شیشه‌ای بلور",
                        checked = uiState.isGlassmorphism,
                        onCheckedChange = onGlassmorphismToggle
                    )
                }
            }
        }

        // Section 3: Network & SSTP Preferences
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Lan, contentDescription = null, tint = Secondary, modifier = Modifier.size(22.dp))
                        Text("تنظیمات شبکه و اتصال", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = OnSurface)
                    }

                    // Port Default
                    Surface(shape = RoundedCornerShape(10.dp), color = SurfaceContainer) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("پورت پیش‌فرض SSTP", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurface)
                                Text("اتصال امن تونل از طریق SSL/TLS", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = SecondaryContainer.copy(alpha = 0.2f)) {
                                Text("443 SSL", fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Secondary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    }

                    // Auto Copy on Tap
                    SettingToggleRow(
                        icon = Icons.Default.ContentCopy,
                        title = "کپی خودکار آدرس هاست",
                        subtitle = "رونوشت لحظه‌ای آدرس سرور به کلیپ‌بورد با لمس کارت",
                        checked = uiState.autoCopyOnTap,
                        onCheckedChange = onAutoCopyToggle
                    )

                    // Timeout
                    Surface(shape = RoundedCornerShape(10.dp), color = SurfaceContainer) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("مهلت بررسی پینگ (Timeout)", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurface)
                                Text("حداکثر بازه زمانی آزمون پاسخ‌دهی سرورها", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = TertiaryContainer.copy(alpha = 0.2f)) {
                                Text("۳ ثانیه", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = Tertiary, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
                    }

                    // Fast Alert
                    SettingToggleRow(
                        icon = Icons.Default.Bolt,
                        title = "اعلان سرورهای پرسرعت",
                        subtitle = "هشدار فوری در صورت یافتن سرور زیر ۵۰ میلی‌ثانیه",
                        checked = uiState.fastServerAlert,
                        onCheckedChange = onFastAlertToggle
                    )
                }
            }
        }

        // Section 4: Storage & Maintenance
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Inventory2, contentDescription = null, tint = Primary, modifier = Modifier.size(22.dp))
                        Text("مدیریت حافظه و بازنشانی", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = OnSurface)
                    }

                    // Clear Cache Action
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = SurfaceContainer,
                        modifier = Modifier.clickable { onClearCacheClick() }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = Icons.Default.CleaningServices, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                                Column {
                                    Text("پاکسازی کش موقت و لیست سرورها", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurface)
                                    Text("حجم ذخیره شده: ۴.۸ مگابایت", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                                }
                            }
                            Surface(shape = RoundedCornerShape(6.dp), color = SurfaceContainerHigh) {
                                Text("پاکسازی", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                            }
                        }
                    }

                    // Reset Preferences Action
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = SurfaceContainer,
                        modifier = Modifier.clickable { onResetPreferencesClick() }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null, tint = Error, modifier = Modifier.size(20.dp))
                                Column {
                                    Text("بازنشانی تمامی تنظیمات", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Error)
                                    Text("بازگشت به مقادیر اولیه کارخانه و فونت استاندارد", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                                }
                            }
                            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        // Section 5: Community Disclaimer
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerLowest.copy(alpha = 0.8f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.Public, contentDescription = null, tint = Primary, modifier = Modifier.size(18.dp))
                        Text("پروژه دانشگاهی و داوطلبانه VPN Gate", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OnSurface)
                    }
                    Text(
                        text = "این نرم‌افزار به‌صورت غیرانتفاعی جهت ارتقای دسترسی آزاد به اینترنت و ارتباط با سرورهای SSTP دانشگاه تسوکوبا ژاپن توسعه یافته است.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = OnSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun FontChoiceCard(
    title: String,
    sub: String,
    tag: String? = null,
    avatarLetter: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) SurfaceContainer else SurfaceContainerLow,
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(if (isSelected) Primary.copy(alpha = 0.3f) else GhostBorder)),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
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
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) Primary.copy(alpha = 0.2f) else SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Text(avatarLetter, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (isSelected) Primary else OnSurfaceVariant)
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(title, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OnSurface)
                        if (tag != null) {
                            Surface(shape = RoundedCornerShape(4.dp), color = SurfaceContainerHigh) {
                                Text(tag, fontFamily = VazirmatnFontFamily, fontSize = 10.sp, color = Primary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                            }
                        }
                    }
                    Text(sub, fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Primary else SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun SettingToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(shape = RoundedCornerShape(10.dp), color = SurfaceContainer) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
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
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = OnSurface, modifier = Modifier.size(18.dp))
                }
                Column {
                    Text(title, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurface)
                    Text(subtitle, fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = OnPrimary, checkedTrackColor = Primary)
            )
        }
    }
}
