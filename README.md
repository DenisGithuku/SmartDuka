# SmartDuka – Duka Management App
**SmartDuka** is an offline-first Android application designed to help small shop owners (dukas) in Kenya manage inventory, record sales, and generate actionable reports. It uses **Supabase** for cloud synchronization and **Room DB** for offline-first functionality.

SmartDuka is a **portfolio project** demonstrating modern Android development practices: Jetpack Compose UI, MVVM architecture, offline-first data handling, background workers, and modular app design.

---

## **Features**

- **Inventory Management:** Add, edit, and delete products with stock tracking
- **Sales Recording:** Record transactions and automatically update stock
- **Low Stock Notifications:** Reminders for products running low
- **Offline-First:** Full functionality without internet, syncs when online
- **Reports & Analytics:**
    - Daily, weekly, monthly sales summaries
    - Top-selling products
    - Inventory movement and stock value
- **Modular Architecture:** Easy to maintain, scale, and extend

---

## **Screenshots / Demo**

_Coming soon._

|Inventory|Sales|Reports|Notifications|
|---|---|---|---|
|||||

---

## **Tech Stack**

- **Android:** Kotlin + Jetpack Compose
- **Architecture:** MVVM + Coroutines + Flow
- **Database:** Room (offline-first)
- **Backend:** Supabase (Auth + Postgres + Sync)
- **Background Workers:** WorkManager
- **Charts & Reports:** MPAndroidChart / Jetpack Compose Charts
    

---

## **Modules**

- **app:** Main app module and navigation
- **core:** Shared utilities, DI, and network helpers
- **auth:** Supabase authentication
- **inventory:** Product and stock management
- **sales:** Record and manage sales
- **reports:** Generate reports and analytics
- **notifications:** Background workers for low-stock reminders and sync
    

---

## **Architecture**

- Offline-first: Room DB as the source of truth
- Modular app structure for maintainability and scalability
- Background sync with Supabase ensures multi-device consistency
- WorkManager handles scheduled tasks and notifications
    

---

## **Roadmap / Planned Features**

- Multi-store support
- Product categorization for better analytics
- Export reports to PDF / Excel
- Enhanced charts and dashboards
- User-friendly setup wizard for first-time shop owners
    
---
## **License**

This project is licensed under the MIT License. See the LICENSE file for details.
