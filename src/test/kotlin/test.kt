import org.hydev.MIUITheme

fun main() {
    val miuiTheme = MIUITheme.get("http://zhuti.xiaomi.com/detail/d555981b-e6af-4ea9-9eb2-e47cfbc3edfa")
    print(miuiTheme?.fileName)
}