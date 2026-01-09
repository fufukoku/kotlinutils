# kotlinutils

A lightweight Kotlin utilities library for fast input parsing (competitive programming / fast stdin).

Current version: `v0.1.0`

## Features

- Fast token-based input scanner (buffered `InputStream`)
- Low allocation: numbers are parsed without converting to `String`
- Simple API: `nextInt()`, `nextLong()`, `nextString()`, `nextLine()` ...
- Package: `com.fufukoku.utils`

## Installation (Gradle + JitPack)

### 1) Add JitPack repository

#### Option A (Recommended, Gradle 7+): `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

#### Option B: root `build.gradle.kts`

```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}
```

### 2) Add dependency

In your module `build.gradle.kts` (e.g., `app/build.gradle.kts`):

```kotlin
dependencies {
    implementation("com.github.fufukoku:kotlinutils:v0.1.0")
}
```

## Usage

```kotlin
import com.fufukoku.utils.FastScanner

fun main() {
    val fs = FastScanner()
    val n = fs.nextInt() ?: return
    println(n)
}
```

## Release

```bash
git tag v0.1.1
git push --tags
```

## License

This project is released under the MIT License.
