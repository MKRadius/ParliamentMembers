
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit,
//    backgroundColor: Color = MaterialTheme.colorScheme.primary,
//    contentColor: Color = Color.White,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
//            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (canNavigateBack) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Transparent)
                        .clickable(onClick = onNavigateUp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "←",
                        style = MaterialTheme.typography.titleMedium,
//                        color = contentColor
                    )
                }
            }

            Box(
                modifier = Modifier.weight(2f).fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
//                    color = contentColor
                )
            }

            Box(modifier = Modifier.weight(1f).fillMaxHeight())
        }
    }
}
