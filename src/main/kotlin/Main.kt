import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.bytehole.channel.ByteHole
import com.github.bytehole.channel.IDebugger

private val debuggerDefault by lazy {
    object : IDebugger {
        override fun onBroadcastReady() {
            println("onBroadcastReady")
        }

        override fun onBroadcastReceived(fromIp: String, data: ByteArray) {
            println("onBroadcastReceived fromIp=$fromIp text=${String(data)}")
        }

        override fun onMessageReady() {
            println("onMessageReady")
        }

        override fun onMessageReceived(fromIp: String, data: ByteArray) {
            println("onMessageReceived fromIp=$fromIp text=${String(data)}")
        }
    }
}
private val byteHole by lazy {
    object : ByteHole() {
        override val debugger: IDebugger
            get() = debuggerDefault
    }
}

@Composable
@Preview
fun App() {

    MaterialTheme {
        Column {

        }
    }
}

fun main() = application {
    byteHole.startup()
    Window(title = "ByteHole Debugger", onCloseRequest = {
        byteHole.shutdown()
        exitApplication()
    }) {
        App()
    }
}
