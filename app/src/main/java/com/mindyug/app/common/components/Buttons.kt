package com.mindyug.app.common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindyug.app.ui.theme.MindYugTheme

val horizontalGradientBrush = Brush.horizontalGradient(
    colors = listOf(
        Color(0xff6C92F4),
        Color(0xff2CE07F),
    )
)

val horizontalDisabledGradientBrush = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFFB0BBD8),
        Color(0xFFB4DBC6),
    )
)

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ButtonShape,
    brush1: Brush = horizontalGradientBrush,
    brush2: Brush = horizontalDisabledGradientBrush,

    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        shape = shape,
        color = Color.Transparent,
        border = border,
        modifier = modifier
            .shadow(8.dp)
            .clip(shape)
            .background(
                if (enabled) brush1 else brush2
            )
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            ),
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.button
        ) {

            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .indication(interactionSource, rememberRipple())
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = if (!isLoading) {
                    content
                } else {
                    {
                        Spacer(modifier = Modifier.width(32.dp))
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 3.dp,
                        )
                        Spacer(modifier = Modifier.width(32.dp))


                    }
                }
            )

        }
    }
}


private val ButtonShape = RoundedCornerShape(percent = 50)

@Preview("default", "round")
@Preview("dark theme", "round", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", "round", fontScale = 2f)
@Composable
private fun ButtonPreview() {
    MindYugTheme {
        GradientButton(onClick = {}, enabled = false) {
            Text(text = "Demo")
        }
    }
}

@Preview("default", "rectangle")
@Preview("dark theme", "rectangle", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", "rectangle", fontScale = 2f)
@Composable
private fun RectangleButtonPreview() {
    MindYugTheme {
        GradientButton(onClick = {}, shape = RectangleShape) {
            Text(text = "Demo")
        }
    }
}
