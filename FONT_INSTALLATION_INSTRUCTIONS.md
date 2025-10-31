# Font Installation Instructions

## What I've Done:
✅ Updated the header background color to **#FF0000** (pure red)
✅ Configured `activity_main.xml` to use custom fonts:
   - **Header**: Poppins SemiBold (22sp, letterSpacing 0.05)
   - **Buttons**: Inter Medium (15sp, letterSpacing 0.02)

## What You Need to Do:

### Step 1: Delete Old Font Files
Navigate to: `app\src\main\res\font\`

Delete these files (they use Google's downloadable font method):
- `poppins_semibold.xml`
- `inter_medium.xml`

### Step 2: Add TTF Font Files
Download and place these TTF files in the same `app\src\main\res\font\` folder:

1. **Poppins-SemiBold.ttf** → Rename to: `poppins_semibold.ttf`
2. **Inter-Medium.ttf** → Rename to: `inter_medium.ttf`

**Important**: File names must be lowercase with underscores (no hyphens or capital letters).

### Font File Sources:
- **Poppins SemiBold**: Download from Google Fonts or similar source
- **Inter Medium**: Download from the Inter font family website

### Final Structure:
```
app/src/main/res/font/
    ├── poppins_semibold.ttf
    └── inter_medium.ttf
```

### Step 3: Rebuild the Project
After adding the TTF files, rebuild your project in Android Studio:
- Build → Clean Project
- Build → Rebuild Project

## Notes:
- These fonts will **ONLY** be used in `activity_main.xml` (main menu screen)
- Other screens will continue to use `RobotoMono-Regular.ttf` as configured
- The header now displays in pure red (#FF0000) instead of the previous red shade

