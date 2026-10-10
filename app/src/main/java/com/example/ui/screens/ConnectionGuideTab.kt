package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.Tertiary
import com.example.ui.theme.VazirmatnFontFamily
import com.example.util.ClipboardHelper

@Composable
fun ConnectionGuideTab(
    onGoToServersClick: () -> Unit
) {
    val context = LocalContext.current
    var expandedFaq1 by remember { mutableStateOf(false) }
    var expandedFaq2 by remember { mutableStateOf(false) }
    var expandedFaq3 by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp)
    ) {
        // 1. Overview Telemetry Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("guide_overview_banner"),
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
                        Surface(
                            shape = RoundedCornerShape(9999.dp),
                            color = Secondary.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Secondary))
                                Text(
                                    text = "نسخه ۴.۲ • تست‌شده و آماده",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 12.sp,
                                    color = Secondary
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(9999.dp),
                            color = Primary.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("SSL / TLS 1.3", fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Primary)
                                Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = Primary, modifier = Modifier.size(13.dp))
                            }
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "راهنمای جامع اتصال به سرورهای SSTP در اندروید",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = OnSurface
                        )
                        Text(
                            text = "پروتکل SSTP به طور بومی از پورت 443 (HTTPS) بهره می‌برد؛ این ساختار رمزنگاری شده مانند ترافیک عادی وب نمایان شده و به راحتی از محدودیت‌های شبکه و دیواره‌های آتش عبور می‌کند.",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 13.sp,
                            lineHeight = 21.sp,
                            color = OnSurfaceVariant
                        )
                    }

                    // Micro Stats Ribbon (3 columns)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GuideStatBox(label = "پورت اتصال", value = "TCP 443", valueColor = Secondary, modifier = Modifier.weight(1f))
                        GuideStatBox(label = "نوع رمزنگاری", value = "AES-256", valueColor = Primary, modifier = Modifier.weight(1f))
                        GuideStatBox(label = "ریشه سرویس", value = "Tsukuba Uni", valueColor = Tertiary, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // 2. Client Apps Download Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                    Text(
                        text = "دانلود کلاینت‌های پیشنهادی SSTP",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = OnSurface
                    )
                }
                Text(
                    text = "جهت اتصال مستقیم به سرورها، یکی از برنامه‌های تایید شده زیر را از گوگل پلی دریافت کنید:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    color = OnSurfaceVariant
                )

                // App 1: SSTP VPN Client
                GuideClientCard(
                    title = "SSTP VPN Client",
                    badge = "پیشنهاد برتر",
                    rating = "4.5",
                    author = "Freedom Systems",
                    description = "سبک، پایدار، سرعت بالا در برقراری تونل امن و سازگاری کامل بدون نیاز به دسترسی روت در اندروید ۵ به بالا.",
                    statusNote = "تست سازگاری ۱۰۰٪",
                    btnText = "دریافت از Google Play",
                    icon = Icons.Default.LockOpen,
                    isPrimary = true,
                    onClick = { openPlayStore(context, "it.colucciweb.sstpvpnclient", "sstp vpn client") }
                )

                // App 2: MS-SSTP
                GuideClientCard(
                    title = "MS-SSTP VPN Client",
                    badge = "پیشرفته",
                    rating = "4.3",
                    author = "Routing Control",
                    description = "پشتیبانی فنی از قابلیت Split Tunneling، مسیریابی اختصاصی دامنه‌ها و تنظیمات پیشرفته DNS درون‌برنامه‌ای.",
                    statusNote = "حجم: ~۴ مگابایت",
                    btnText = "نصب رایگان",
                    icon = Icons.Default.AltRoute,
                    isPrimary = false,
                    onClick = { openPlayStore(context, "org.toruneko.ms_sstp_vpn", "ms sstp vpn") }
                )

                // App 3: VPNGate SSTP Connect
                GuideClientCard(
                    title = "VPNGate SSTP Connect",
                    badge = "سازگار ویژه",
                    rating = "4.2",
                    author = "Academic Mirror",
                    description = "پیکربندی شده به منظور حداکثر سازگاری با سرورهای رله دانشگاه تسوکوبا و گواهی‌های خودامضا.",
                    statusNote = "پشتیبانی TLS 1.2+",
                    btnText = "نصب سریع",
                    icon = Icons.Default.Hub,
                    isPrimary = false,
                    onClick = { openPlayStore(context, "com.vpngate.sstp", "vpngate sstp") }
                )

                // Security Shield Note
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SurfaceContainerLowest
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = Secondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "تمامی برنامه‌ها توسط گوگل‌پلی پروتکت اسکن و تایید شده‌اند",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            color = Outline
                        )
                    }
                }
            }
        }

        // 3. Credentials Quick-Copy Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
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
                            Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                            Text(
                                text = "مشخصات پیش‌فرض اتصال (تمام سرورها)",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = OnSurface
                            )
                        }

                        Surface(shape = RoundedCornerShape(9999.dp), color = SurfaceContainerHighest) {
                            Text(
                                text = "Global Public",
                                fontFamily = JetBrainsMonoFontFamily,
                                fontSize = 10.sp,
                                color = Secondary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "تمام سرورهای پروژه دانشگاهی VPN Gate نیازمند نام کاربری و رمز عبور یکسان زیر می‌باشند:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 13.sp,
                        color = OnSurfaceVariant
                    )

                    // Username Row
                    CredentialRow(
                        label = "نام کاربری (Username)",
                        value = "vpn",
                        onCopy = { ClipboardHelper.copyToClipboard(context, "Username", "vpn", "نام کاربری vpn کپی شد") }
                    )

                    // Password Row
                    CredentialRow(
                        label = "رمز عبور (Password)",
                        value = "vpn",
                        onCopy = { ClipboardHelper.copyToClipboard(context, "Password", "vpn", "رمز عبور vpn کپی شد") }
                    )

                    // Protocol summary
                    Surface(shape = RoundedCornerShape(8.dp), color = SurfaceContainerLowest) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.Tune, contentDescription = null, tint = Tertiary, modifier = Modifier.size(16.dp))
                                Text("پروتکل: SSTP (SSL Tunnel)", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, color = OnSurfaceVariant)
                            }
                            Text("Port: 443", fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Primary)
                        }
                    }
                }
            }
        }

        // 4. Step-by-Step Setup Timeline (1..4)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.Route, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                    Text(
                        text = "مراحل گام‌به‌گام راه‌اندازی و اتصال",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = OnSurface
                    )
                }

                TimelineStepItem(
                    stepNumber = "۱",
                    stepColor = Primary,
                    title = "کپی آدرس سرور فعال",
                    description = "وارد تب سرورها شده و با لمس دکمه کپی، هاست نیم سرور را ذخیره کنید.",
                    exampleAddress = "public-vpn-92.opengw.net:443"
                )

                TimelineStepItem(
                    stepNumber = "۲",
                    stepColor = PrimaryContainer,
                    title = "ایجاد پروفایل در کلاینت SSTP",
                    description = "کلاینت دانلود شده را باز کرده و علامت افزودن کانکشن جدید (+ یا New Profile) را لمس نمایید."
                )

                TimelineStepItem(
                    stepNumber = "۳",
                    stepColor = PrimaryContainer,
                    title = "درج مشخصات اتصال",
                    description = "آدرس کپی شده را در فیلد Server / Host چسبانده و فیلدهای نام‌کاربری و رمزعبور را هر دو برابر با vpn قرار دهید."
                )

                TimelineStepItem(
                    stepNumber = "۴",
                    stepColor = Secondary,
                    title = "برقراری اتصال امن",
                    description = "دکمه Connect را لمس کرده و در پنجره پیام سیستمی اندروید، مجوز امنیتی تونل VPN را تایید کنید."
                )
            }
        }

        // 5. Troubleshooting & FAQs
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(imageVector = Icons.Default.ContactSupport, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                    Text(
                        text = "نکات مهم و رفع اشکال متداول",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = OnSurface
                    )
                }

                FaqAccordionItem(
                    title = "چرا اتصال پس از چند ثانیه برقرار نمی‌شود؟",
                    isExpanded = expandedFaq1,
                    onToggle = { expandedFaq1 = !expandedFaq1 },
                    content = "سرورهای پروژه VPN Gate به صورت داوطلبانه اداره می‌شوند و ممکن است سرور مورد نظر پر یا موقتاً آفلاین باشد. به تب سرورها مراجعه کرده و از دکمه تست پینگ استفاده کنید تا سرورهای فعال با تاخیر زیر ۱۵۰ میلی‌ثانیه را انتخاب نمایید.",
                    icon = Icons.Default.ErrorOutline,
                    iconColor = Tertiary
                )

                FaqAccordionItem(
                    title = "آیا نیاز به روت کردن گوشی اندرویدی است؟",
                    isExpanded = expandedFaq2,
                    onToggle = { expandedFaq2 = !expandedFaq2 },
                    content = "خیر، هیچ‌کدام از کلاینت‌های معرفی‌شده نیازی به دسترسی روت (Root) ندارند. تمامی این برنامه‌ها با استفاده از سرویس استاندارد و بومی VpnService در سیستم‌عامل اندروید بدون خطر امنیتی عمل می‌کنند.",
                    icon = Icons.Default.Android,
                    iconColor = Secondary
                )

                FaqAccordionItem(
                    title = "خطای گواهی امنیتی SSL (Certificate Error)",
                    isExpanded = expandedFaq3,
                    onToggle = { expandedFaq3 = !expandedFaq3 },
                    content = "بسیاری از سرورهای داوطلب از گواهینامه‌های خودامضا (Self-Signed) استفاده می‌کنند. در تنظیمات پروفایل کلاینت خود گزینه \"Trust all certificates\" یا \"Bypass SSL Verification\" را فعال کنید تا خطای اعتبارسنجی برطرف شود.",
                    icon = Icons.Default.LockReset,
                    iconColor = Primary
                )
            }
        }

        // 6. Bottom Sticky Action: Go to Servers
        item {
            Button(
                onClick = onGoToServersClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer, contentColor = OnPrimaryContainer)
            ) {
                Icon(imageVector = Icons.Default.Dns, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "رفتن به لیست سرورها برای انتخاب سرور",
                    fontFamily = VazirmatnFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun GuideStatBox(label: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
        color = SurfaceContainer
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = valueColor)
        }
    }
}

@Composable
fun GuideClientCard(
    title: String,
    badge: String,
    rating: String,
    author: String,
    description: String,
    statusNote: String,
    btnText: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isPrimary: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (isPrimary) SurfaceContainer else SurfaceContainerLow,
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(if (isPrimary) Primary.copy(alpha = 0.3f) else GhostBorder))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
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
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isPrimary) SurfaceContainerHigh else SurfaceContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (isPrimary) Primary else Secondary, modifier = Modifier.size(24.dp))
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(title, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = OnSurface)
                            Surface(shape = RoundedCornerShape(4.dp), color = Primary.copy(alpha = 0.15f)) {
                                Text(badge, fontFamily = VazirmatnFontFamily, fontSize = 10.sp, color = Primary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Tertiary, modifier = Modifier.size(13.dp))
                            Text(rating, fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Tertiary)
                            Text("• $author", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                        }
                    }
                }
            }

            Text(description, fontFamily = VazirmatnFontFamily, fontSize = 12.sp, lineHeight = 19.sp, color = OnSurfaceVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(statusNote, fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Secondary)
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isPrimary) Primary else SurfaceContainerHigh, contentColor = if (isPrimary) OnPrimary else Primary)
                ) {
                    Text(btnText, fontFamily = VazirmatnFontFamily, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = if (isPrimary) Icons.Default.OpenInNew else Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun CredentialRow(label: String, value: String, onCopy: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = SurfaceContainerHigh
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(label, fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = OnSurfaceVariant)
                Text(value, fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Primary)
            }
            Button(
                onClick = onCopy,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHighest, contentColor = OnSurface)
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("کپی", fontFamily = VazirmatnFontFamily, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun TimelineStepItem(
    stepNumber: String,
    stepColor: Color,
    title: String,
    description: String,
    exampleAddress: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(stepColor),
            contentAlignment = Alignment.Center
        ) {
            Text(stepNumber, fontFamily = SpaceGroteskFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 2.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(title, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = OnSurface)
            Text(description, fontFamily = VazirmatnFontFamily, fontSize = 12.sp, lineHeight = 19.sp, color = OnSurfaceVariant)
            if (exampleAddress != null) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SurfaceContainerLow
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = Primary, modifier = Modifier.size(13.dp))
                            Text(exampleAddress, fontFamily = JetBrainsMonoFontFamily, fontSize = 11.sp, color = Primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FaqAccordionItem(
    title: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = SurfaceContainerLow,
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggle() }
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
                    Text(title, fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = OnSurface)
                }
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(if (isExpanded) 180f else 0f),
                    tint = Outline
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Text(
                    text = content,
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
