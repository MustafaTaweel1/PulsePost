# PulsePost 📱✨
> A modern, lightweight Android social feed application built with **Kotlin**, **Jetpack Compose**, **Material 3**, and **Room Database**.

---

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-4285F4.svg?style=flat&logo=android)](https://developer.android.com/jetpack/compose)
[![Room Database](https://img.shields.io/badge/Database-Room%202.6.1-47A248.svg?style=flat&logo=sqlite)](https://developer.android.com/training/data-storage/room)
[![Android SDK](https://img.shields.io/badge/Min%20SDK-27%20|%20Target%20SDK-35-green.svg?style=flat&logo=android)](https://developer.android.com)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM-orange.svg?style=flat)]()

---

## 📖 Overview

**PulsePost** (formerly *FinalProject*) is an Android micro-blogging and social networking application designed with a clean **MVVM (Model-View-ViewModel)** architecture. It allows users to register, log in, create custom text posts, interact through collapsible animated comments, and explore social feeds locally with seamless offline persistence powered by **Room Database**.

---

## ✨ Features

- 🔐 **User Authentication & Profiles**:
  - Full user registration flow with detailed profile fields (name, email, phone, gender, birthday).
  - Secure login with validation and session management.
- 👤 **Guest Mode**:
  - Instant guest preview option from the welcome screen without requiring immediate registration.
- 📝 **Post Management**:
  - Create, publish, view, and delete posts with custom titles and content.
  - Interactive feed sorted and presented with smooth Jetpack Compose lazy lists.
- 💬 **Interactive Comments**:
  - Comment on individual posts in real time.
  - Smooth toggleable animated comment drawers (`AnimatedVisibility` with fade-in/out transitions).
  - Dedicated Post Details view with focused comment threads.
- 💾 **Offline-First Persistence**:
  - Local database (`SocialDB`) using Android Room ORM with DAOs, coroutines, and reactive Kotlin Flow.
- 🎨 **Modern Material 3 Design**:
  - Fully responsive Compose UI with dynamic theming, custom color palettes, edge-to-edge support, and custom typography.

---

## 🏗️ Architecture & Tech Stack

The application follows Android development best practices using the **MVVM (Model-View-ViewModel)** architectural pattern:

```
                  ┌─────────────────────────────────┐
                  │      Jetpack Compose UI         │
                  │  (Activities & Composables)     │
                  └───────────────┬─────────────────┘
                                  │
                                  ▼
                  ┌─────────────────────────────────┐
                  │       ViewModel Layer           │
                  │ (User, Post, Comment, Session)  │
                  └───────────────┬─────────────────┘
                                  │
                                  ▼
                  ┌─────────────────────────────────┐
                  │          Room Database          │
                  │  (DAOs, Entities, SQLite Engine)│
                  └─────────────────────────────────┘
```

### Core Technologies
- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with [Material 3](https://m3.material.io/)
- **Architecture**: MVVM with Kotlin Coroutines & Flow
- **Local Storage**: [Room Database](https://developer.android.com/training/data-storage/room) & [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html)
- **Navigation**: Jetpack Navigation & Android Activity Intents

---

## 📁 Project Structure

```text
app/src/main/java/com/example/finalproject/
├── DB/
│   └── AppDatabase.kt          # Room Database setup & migration config ("SocialDB")
├── Model/
│   ├── Users.kt                # User Entity (id, name, email, credentials, details)
│   ├── Post.kt                 # Post Entity (id, title, body, userId)
│   ├── Comment.kt              # Comment Entity (id, UserID, PostID, body)
│   ├── Friend.kt               # Friend Relationship Entity
│   └── DAO/
│       ├── UserDao.kt          # User database operations
│       ├── PostDao.kt          # Post queries, insertions & deletions
│       └── CommentDao.kt       # Comment queries by post ID
├── ModelView/
│   ├── UserViewModel.kt        # User auth, registration & profile data logic
│   ├── PostViewModel.kt        # Posts state handling & CRUD operations
│   ├── CommentViewModel.kt     # Reactive comments flow & additions
│   └── sessionViewModel.kt     # User login state & active session management
├── PostView/
│   ├── HomeActivity.kt         # Main posts feed, comment drawer & actions
│   ├── InsertActivity.kt       # Post creation screen
│   ├── PostDetailActivity.kt   # Detailed post view with comment thread
│   └── ListPostActivity.kt     # Secondary post listing screen
├── ui/
│   └── theme/                  # Material 3 Color, Shape, Typography & Theme definitions
├── MainActivity.kt             # Welcome / Landing screen with Login & Guest options
├── LoginActivity.kt            # User login screen
├── RegisterActivity.kt         # New account registration form
└── GuestActivity.kt            # Guest visitor landing view
```

---

## 🗄️ Database Schema

### `Users` Table
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` (PK, Auto) | Unique user ID |
| `firstName` | `TEXT` | User's first name |
| `lastName` | `TEXT` | User's last name |
| `email` | `TEXT` | User's email (login identifier) |
| `password` | `TEXT` | User's password |
| `phoneNumber` | `TEXT` | Contact phone number |
| `gender` | `TEXT` | Gender specification |
| `birthday` | `TEXT` | Date of birth |

### `Post` Table
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` (PK, Auto) | Unique post identifier |
| `title` | `TEXT` | Post headline |
| `body` | `TEXT` | Post text content |
| `userId` | `INTEGER` | Foreign key referencing `Users.id` |

### `Comment` Table
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` (PK, Auto) | Unique comment identifier |
| `UserID` | `INTEGER` | ID of commenter |
| `PostID` | `INTEGER` | Post ID associated with comment |
| `body` | `TEXT` | Comment text content |

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Koala / Ladybug or newer recommended)
- **JDK**: Java 11 or higher
- **Android Device / Emulator**: Android 8.1 (API Level 27) or higher

### Installation & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/MustafaTaweel1/FinalProject.git
   cd FinalProject
   ```

2. **Open in Android Studio**:
   - Open Android Studio -> Select **Open an Existing Project** -> Navigate to the cloned folder.

3. **Sync Gradle**:
   - Allow Gradle to sync dependencies and download required libraries.

4. **Build and Run**:
   - Select your target device / emulator and click **Run (Shift + F10)**.

---

## 📱 Navigation Flow

1. **Welcome Screen** (`MainActivity`)
   - ➡️ **Login** (`LoginActivity`) ➡️ **Register** (`RegisterActivity`)
   - ➡️ **Guest Mode** (`GuestActivity`)
2. **Main Feed** (`HomeActivity`)
   - ➡️ **Create Post** (`InsertActivity`)
   - ➡️ **Post Details & Discussion** (`PostDetailActivity`)
   - 🗑️ Toggle delete mode to remove posts

---

## 📄 License
This project was developed for academic and personal portfolio demonstration. Feel free to use and adapt it according to standard educational guidelines.
