SSTP Finder 🛡️

یک کلاینت بومی (Native) اندروید برای مدیریت و بهینه‌سازی سرورهای SSTP.

این پروژه با هدف ساده‌سازی فرایند دریافت، تست و مدیریت لیست سرورهای VPN Gate توسعه یافته است. تمرکز معماری این اپلیکیشن بر Offline-first بودن و تجربه کاربری روان (UI/UX) با استفاده از Jetpack Compose است.

🛠 معماری و تکنولوژی‌ها
این پروژه با رویکرد مدرن توسعه اندروید (Modern Android Development) و معماری لایه‌ای (Layered Architecture) طراحی شده است:


لایه	تکنولوژی / ابزار
زبان	Kotlin
رابط کاربری	Jetpack Compose, Material 3
مدیریت داده	Room Database (Local Caching)
شبکه	Retrofit, OkHttp
پردازش پس‌زمینه	WorkManager (برای Sync دوره‌ای)
مدیریت وضعیت	ViewModel, StateFlow
تزریق وابستگی	(Manual DI / Hilt compatible)
✨ قابلیت‌های کلیدی
سینک هوشمند (Background Sync): استفاده از WorkManager برای به‌روزرسانی لیست سرورها در پس‌زمینه بدون درگیر کردن کاربر.
پینگ و تست سلامت: سیستم داخلی PingTester برای تشخیص سریع وضعیت پایداری سرورها قبل از اتصال.
کشینگ محلی: دسترسی سریع به لیست سرورها حتی بدون اتصال اینترنت (Offline Mode).
رابط کاربری بهینه: استفاده از فونت Vazirmatn برای خوانایی بهتر در زبان فارسی و طراحی مینیمال.
کپی آسان: ابزار داخلی برای کپی سریع پارامترهای اتصال.
🚀 شروع سریع
برای بیلد پروژه، مطمئن شوید که Android Studio Ladybug (یا جدیدتر) روی سیستم شما نصب است.

Clone کردن پروژه:
bash
   git clone https://github.com/your-username/vpngate-sstp.git
Sync کردن Gradle:

پروژه را در Android Studio باز کنید و اجازه دهید Dependencyها دانلود شوند.

اجرا:

یک شبیه‌ساز یا دستگاه واقعی (API 24+) را متصل کرده و دکمه Run را بزنید.

🏗 ساختار پوشه‌بندی
ساختار پروژه از اصل Clean Architecture پیروی می‌کند:

/data: شامل Repositoryها، API Clientها و Database Access Objects (DAO).
/ui: شامل Screens و Composableهای اختصاصی.
/worker: مدیریت تسک‌های زمان‌بندی شده (Background Tasks).
/util: ابزارهای کمکی (Network Monitor, Clipboard Helpers).
📝 لایسنس
این پروژه تحت لایسنس MIT منتشر شده است. استفاده از آن برای اهداف آموزشی و شخصی آزاد است.

توسعه یافته با ❤️ توسط محمد
