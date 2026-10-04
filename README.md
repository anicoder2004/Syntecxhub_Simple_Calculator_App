# Simple Calculator App

A native Android calculator application built with Kotlin and XML layouts, following Clean Architecture principles. Supports basic arithmetic operations with proper edge case handling, light/dark themes, and both portrait/landscape orientations.

## Features

### Core Operations
- **Addition (+)**
- **Subtraction (−)**
- **Multiplication (×)**
- **Division (÷)**
- **Percentage (%)** - converts current value to percentage (divides by 100)
- **Continuous calculations** - pressing an operator after a result uses the result as the first operand

### Input & Display
- **Two-tier display**:
    - Upper tier: Shows the ongoing expression (e.g., "12 + 5 ×")
    - Lower tier: Shows current input or final result
- **Real-time updates** on every button press
- **Decimal support** with validation (prevents multiple decimals in one number)
- **Horizontal scrolling** for long expressions/results (up to 100 characters)
- **Backspace (C)** - removes last digit
- **All Clear (AC)** - resets entire calculator state

### Edge Case Handling
- **Divide by zero** - displays "Cannot divide by zero", resets on next number input
- **Empty operator press** - defaults first operand to 0
- **Maximum length** - 100 characters with auto-sizing text
- **Precision arithmetic** - Uses `BigDecimal` with `MathContext(100, RoundingMode.HALF_UP)`

### UI/UX
- **Light/Dark theme** - Automatic via `DayNight` theme system
- **Portrait layout** - Standard calculator grid
- **Landscape layout** - Split view (displays left, keypad right)
- **Material3 design** - Tonal buttons, rounded corners, proper contrast
- **System insets support** - Edge-to-edge display

## Architecture

```
app/
├── src/main/
│   ├── java/com/anisoft/simplecalculator/
│   │   ├── CalculatorEngine.kt    # Domain layer - pure Kotlin, no Android deps
│   │   └── MainActivity.kt        # Presentation layer - ViewBinding + Engine
│   ├── res/
│   │   ├── layout/activity_main.xml       # Portrait layout
│   │   ├── layout-land/activity_main.xml  # Landscape layout
│   │   ├── values/themes.xml              # Light theme
│   │   ├── values-night/themes.xml        # Dark theme
│   │   ├── values/colors.xml              # Color definitions
│   │   └── values/strings.xml             # String resources
│   └── AndroidManifest.xml
└── build.gradle.kts
```

### Separation of Concerns
- **Domain Layer** (`CalculatorEngine`): Pure Kotlin class handling all calculation logic, state management, and validation. Zero Android framework dependencies.
- **Presentation Layer** (`MainActivity`): XML ViewBinding, forwards UI events to Engine, updates displays with Engine output.
- **Layouts**: ConstraintLayout with MaterialButton, separate portrait/landscape variants.

## Technical Stack

| Component | Version |
|-----------|---------|
| Language | Kotlin 1.9.24 |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 37 |
| UI Framework | XML + ViewBinding |
| Architecture | Clean Architecture |
| Build System | Gradle (Kotlin DSL) |
| Dependencies | Material3, ConstraintLayout, AppCompat, Core-KTX |


## Requirements Compliance

- ✅ No `eval()` or JavaScript engines - all logic in Kotlin
- ✅ Clean Architecture with strict separation
- ✅ ViewBinding (no `findViewById`)
- ✅ Light/Dark theme via `@color` resources
- ✅ Portrait + Landscape layouts
- ✅ 100-char display with scrolling/auto-size
- ✅ BigDecimal precision (MathContext 100)
- ✅ Divide-by-zero handling
- ✅ Decimal validation
- ✅ Continuous calculation support

