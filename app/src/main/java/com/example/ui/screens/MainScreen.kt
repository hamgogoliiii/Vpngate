package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VpnLock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.SstpServer
import com.example.ui.theme.GhostBorder
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SpaceGroteskFontFamily
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.VazirmatnFontFamily
import com.example.util.ClipboardHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedServerForDetails by remember { mutableStateOf<SstpServer?>(null) }
    var showFilterSheet by remember { mutableStateOf(false) }

    // Handle back button: if in sub-tab, return to SERVERS tab
    BackHandler(enabled = uiState.currentTab != AppTab.SERVERS) {
        viewModel.selectTab(AppTab.SERVERS)
    }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserMessage()
        }
    }

    Scaffold(
        containerColor = Surface,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    when (uiState.currentTab) {
                        AppTab.SERVERS -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceContainerHigh),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VpnLock,
                                        contentDescription = null,
                                        tint = Primary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Servers List",
                                        fontFamily = SpaceGroteskFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp,
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
                                            text = "برخط • مخزن VPN Gate",
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 11.sp,
                                            color = Secondary
                                        )
                                    }
                                }
                            }
                        }
                        AppTab.SYNC_AND_CACHE -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                IconButton(
                                    onClick = { viewModel.refreshServers() },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Sync, contentDescription = "Sync", tint = OnSurfaceVariant, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                        Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Secondary))
                                        Text("Sync And Cache", fontFamily = SpaceGroteskFontFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = OnSurface)
                                    }
                                    Text("SSTP DIRECT TUNNEL", fontFamily = JetBrainsMonoFontFamily, fontSize = 10.sp, color = OnSurfaceVariant)
                                }
                            }
                        }
                        AppTab.CONNECTION_GUIDE -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceContainerHigh),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.VpnLock, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                                }
                                Text("Connection Guide", fontFamily = SpaceGroteskFontFamily, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = OnSurface)
                            }
                        }
                        AppTab.SETTINGS -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                IconButton(
                                    onClick = { viewModel.selectTab(AppTab.SERVERS) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Back", tint = OnSurface, modifier = Modifier.size(22.dp))
                                }
                                Text("Settings", fontFamily = SpaceGroteskFontFamily, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = OnSurface)
                            }
                        }
                    }
                },
                actions = {
                    when (uiState.currentTab) {
                        AppTab.SERVERS -> {
                            IconButton(onClick = { viewModel.refreshServers() }) {
                                Icon(imageVector = Icons.Default.Sync, contentDescription = "Refresh", tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                            }
                            IconButton(onClick = { showFilterSheet = true }) {
                                Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filter", tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                            }
                            UserAvatarIcon()
                        }
                        AppTab.SYNC_AND_CACHE -> {
                            IconButton(onClick = { viewModel.refreshServers() }) {
                                Icon(imageVector = Icons.Default.CloudDone, contentDescription = "Cloud", tint = Primary, modifier = Modifier.size(20.dp))
                            }
                            UserAvatarIcon()
                        }
                        AppTab.CONNECTION_GUIDE -> {
                            IconButton(onClick = { viewModel.selectTab(AppTab.SETTINGS) }) {
                                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                            }
                            UserAvatarIcon()
                        }
                        AppTab.SETTINGS -> {
                            IconButton(onClick = { viewModel.selectTab(AppTab.CONNECTION_GUIDE) }) {
                                Icon(imageVector = Icons.Default.Help, contentDescription = "Help", tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                            }
                            UserAvatarIcon()
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceContainerHigh.copy(alpha = 0.95f),
                tonalElevation = 8.dp,
                modifier = Modifier.border(width = 1.dp, brush = SolidColor(GhostBorder), shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                // Tab 1: Servers
                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.SERVERS,
                    onClick = { viewModel.selectTab(AppTab.SERVERS) },
                    icon = { Icon(imageVector = Icons.Default.Dns, contentDescription = "سرورها", modifier = Modifier.size(22.dp)) },
                    label = { Text("سرورها", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, fontWeight = if (uiState.currentTab == AppTab.SERVERS) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Primary,
                        selectedTextColor = Primary,
                        unselectedIconColor = OnSurfaceVariant,
                        unselectedTextColor = OnSurfaceVariant,
                        indicatorColor = Primary.copy(alpha = 0.15f)
                    )
                )

                // Tab 2: Sync and Cache
                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.SYNC_AND_CACHE,
                    onClick = { viewModel.selectTab(AppTab.SYNC_AND_CACHE) },
                    icon = { Icon(imageVector = Icons.Default.Cached, contentDescription = "همگام‌سازی و کش", modifier = Modifier.size(22.dp)) },
                    label = { Text("همگام‌سازی و کش", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, fontWeight = if (uiState.currentTab == AppTab.SYNC_AND_CACHE) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Primary,
                        selectedTextColor = Primary,
                        unselectedIconColor = OnSurfaceVariant,
                        unselectedTextColor = OnSurfaceVariant,
                        indicatorColor = Primary.copy(alpha = 0.15f)
                    )
                )

                // Tab 3: Connection Guide
                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.CONNECTION_GUIDE,
                    onClick = { viewModel.selectTab(AppTab.CONNECTION_GUIDE) },
                    icon = { Icon(imageVector = Icons.Default.MenuBook, contentDescription = "راهنمای اتصال", modifier = Modifier.size(22.dp)) },
                    label = { Text("راهنمای اتصال", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, fontWeight = if (uiState.currentTab == AppTab.CONNECTION_GUIDE) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Primary,
                        selectedTextColor = Primary,
                        unselectedIconColor = OnSurfaceVariant,
                        unselectedTextColor = OnSurfaceVariant,
                        indicatorColor = Primary.copy(alpha = 0.15f)
                    )
                )

                // Tab 4: Settings
                NavigationBarItem(
                    selected = uiState.currentTab == AppTab.SETTINGS,
                    onClick = { viewModel.selectTab(AppTab.SETTINGS) },
                    icon = { Icon(imageVector = Icons.Default.Tune, contentDescription = "تنظیمات", modifier = Modifier.size(22.dp)) },
                    label = { Text("تنظیمات", fontFamily = VazirmatnFontFamily, fontSize = 12.sp, fontWeight = if (uiState.currentTab == AppTab.SETTINGS) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Primary,
                        selectedTextColor = Primary,
                        unselectedIconColor = OnSurfaceVariant,
                        unselectedTextColor = OnSurfaceVariant,
                        indicatorColor = Primary.copy(alpha = 0.15f)
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isRefreshing) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Primary,
                    trackColor = SurfaceContainerLowest
                )
            }

            AnimatedContent(
                targetState = uiState.currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "TabContentTransition"
            ) { targetTab ->
                when (targetTab) {
                    AppTab.SERVERS -> {
                        ServersTab(
                            uiState = uiState,
                            onRefreshClick = { viewModel.refreshServers() },
                            onSortSelected = { viewModel.setSortOption(it) },
                            onCountrySelected = { viewModel.setSelectedCountry(it) },
                            onBatchPingClick = { viewModel.testBatchPing() },
                            onSinglePingClick = { viewModel.testSingleServerPing(it) },
                            onCopyServerAddress = { addr ->
                                ClipboardHelper.copyToClipboard(context, "SSTP Address", addr, "آدرس سرور ($addr) کپی شد!")
                                viewModel.quickConnect(uiState.servers.firstOrNull { it.displayAddress == addr } ?: return@ServersTab)
                                scope.launch {
                                    snackbarHostState.showSnackbar("آدرس سرور ($addr) با موفقیت کپی شد!")
                                }
                            },
                            onCopyCredentials = {
                                ClipboardHelper.copyToClipboard(context, "SSTP Credentials", "Username: vpn\nPassword: vpn", "اطلاعات کاربری (vpn / vpn) کپی شد!")
                                scope.launch {
                                    snackbarHostState.showSnackbar("اطلاعات کاربری (vpn / vpn) در کلیپ‌بورد کپی شد!")
                                }
                            },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            onServerClick = { selectedServerForDetails = it }
                        )
                    }

                    AppTab.SYNC_AND_CACHE -> {
                        SyncAndCacheTab(
                            uiState = uiState,
                            onForceSyncClick = { viewModel.refreshServers() },
                            onBgSyncToggle = { viewModel.setBgSyncEnabled(it) },
                            onIntervalSelected = { viewModel.setSyncInterval(it) },
                            onWifiOnlyToggle = { viewModel.setWifiOnly(it) },
                            onBatteryNotLowToggle = { viewModel.setBatteryNotLow(it) },
                            onAutoPurgeToggle = { viewModel.setAutoPurgeDeadNodes(it) },
                            onMaxPingChanged = { viewModel.setMaxPingThreshold(it) },
                            onClearTempCacheClick = { viewModel.clearTempCache() },
                            onResetDatabaseClick = { viewModel.resetDatabase() }
                        )
                    }

                    AppTab.CONNECTION_GUIDE -> {
                        ConnectionGuideTab(
                            onGoToServersClick = { viewModel.selectTab(AppTab.SERVERS) }
                        )
                    }

                    AppTab.SETTINGS -> {
                        SettingsTab(
                            uiState = uiState,
                            onFontFamilySelected = { viewModel.setFontFamilyChoice(it) },
                            onFontSizeSelected = { viewModel.setFontSizeChoice(it) },
                            onBoldTextToggle = { viewModel.setBoldText(it) },
                            onPersianNumeralsToggle = { viewModel.setPersianNumerals(it) },
                            onGlassmorphismToggle = { viewModel.setGlassmorphism(it) },
                            onCompactDensityToggle = { viewModel.setCompactDensity(it) },
                            onAutoCopyToggle = { viewModel.setAutoCopyOnTap(it) },
                            onFastAlertToggle = { viewModel.setFastServerAlert(it) },
                            onClearCacheClick = { viewModel.clearTempCache() },
                            onResetPreferencesClick = { viewModel.resetPreferences() }
                        )
                    }
                }
            }
        }
    }

    // Detail BottomSheet
    selectedServerForDetails?.let { server ->
        ServerDetailBottomSheet(
            server = server,
            onDismiss = { selectedServerForDetails = null },
            onCopyField = { label, value ->
                ClipboardHelper.copyToClipboard(context, label, value, "$label کپی شد")
                scope.launch { snackbarHostState.showSnackbar("$label کپی شد: $value") }
            }
        )
    }

    // Search / Filter Sheet
    if (showFilterSheet) {
        SearchFilterBottomSheet(
            searchQuery = uiState.searchQuery,
            onSearchChange = { viewModel.setSearchQuery(it) },
            onDismiss = { showFilterSheet = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerDetailBottomSheet(
    server: SstpServer,
    onDismiss: () -> Unit,
    onCopyField: (String, String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceContainerHigh,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(42.dp, 5.dp)
                    .clip(CircleShape)
                    .background(OutlineVariant)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 12.dp)
                .padding(bottom = 36.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = server.countryFlag, fontSize = 36.sp)
                Column {
                    Text(
                        text = server.farsiRegionTitle,
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = OnSurface
                    )
                    Text(
                        text = "مشخصات فنی و امنیتی تونل SSTP",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 13.sp,
                        color = OnSurfaceVariant
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(GhostBorder))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DetailRow("آدرس سرور (Hostname)", server.displayAddress) { onCopyField("آدرس سرور", server.displayAddress) }
                    DetailRow("آدرس آی‌پی (IPv4)", server.ip) { onCopyField("آی‌پی", server.ip) }
                    DetailRow("پورت SSTP", "${server.port}") { onCopyField("پورت", "${server.port}") }
                    DetailRow("نام کاربری (Username)", "vpn") { onCopyField("نام کاربری", "vpn") }
                    DetailRow("رمز عبور (Password)", "vpn") { onCopyField("رمز عبور", "vpn") }
                    DetailRow("پهنای باند (Throughput)", server.formattedSpeed, null)
                    DetailRow("تاخیر پینگ گزارش‌شده", "${server.pingMs} ms", null)
                }
            }

            Button(
                onClick = { onCopyField("پروفایل کامل SSTP", server.fullProfileText) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary)
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("کپی کامل تنظیمات برای اندروید", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, onCopy: (() -> Unit)?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontFamily = VazirmatnFontFamily, fontSize = 13.sp, color = OnSurfaceVariant)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = value, fontFamily = JetBrainsMonoFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = OnSurface)
            if (onCopy != null) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy",
                    tint = Primary,
                    modifier = Modifier.size(16.dp).clickable { onCopy() }
                )
            }
        }
    }
}

@Composable
fun UserAvatarIcon() {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(32.dp)
            .clip(CircleShape)
            .background(Primary),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = OnPrimary, modifier = Modifier.size(18.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterBottomSheet(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceContainerHigh
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "جستجو و فیلتر سرورها",
                fontFamily = VazirmatnFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = OnSurface
            )
            androidx.compose.material3.OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("نام کشور، هاست یا آی‌پی...", fontFamily = VazirmatnFontFamily) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary)
            ) {
                Text("اعمال فیلتر", fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold)
            }
        }
    }
}
