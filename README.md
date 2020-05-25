# MIUITheme
A library for MIUI theme download link, file name, etc.

## Usage
`````kotlin
val themeShareLink = "http://zhuti.xiaomi.com/detail/d555981b-e6af-4ea9-9eb2-e47cfbc3edfa"

// Can also specify MIUI version V10 - V12
val miuiTheme = MIUITheme.get(themeShareLink)
val miuiTheme = MIUITheme.get(themeShareLink, MIUITheme.MIUIVersion.V10)

// Also can get downloadUrl, fileHash, fileSize.
print(miuiTheme?.fileName)
`````

## KDoc
[https://vergedx.github.io/MIUITheme/-m-i-u-i-theme-tools/index.html](https://vergedx.github.io/MIUITheme/-m-i-u-i-theme-tools/index.html)
