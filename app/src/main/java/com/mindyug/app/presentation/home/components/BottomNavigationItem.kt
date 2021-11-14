package com.mindyug.app.presentation.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp




@Composable
fun RowScope.MindYugBottomNavigationItem(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    selectedContentColor: Color = LocalContentColor.current,
){
    val styledLabel: @Composable (() -> Unit)? = label?.let {
        @Composable {
            val style = MaterialTheme.typography.caption.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(style, content = label)
        }
    }
    // The color of the Ripple should always the selected color, as we want to show the color
    // before the item is considered selected, and hence before the new contentColor is
    // provided by BottomNavigationTransition.
    val ripple = rememberRipple(bounded = false, color = selectedContentColor)

    Box(
        modifier.weight(1f),
        contentAlignment = Alignment.Center
    )
 {
            BottomNavigationItemBaselineLayout(
                icon = content,
                label = styledLabel,
            )
        }
    }


@Composable
private fun BottomNavigationItemBaselineLayout(
    icon: @Composable () -> Unit,
    label: @Composable (() -> Unit)?,
    /*@FloatRange(from = 0.0, to = 1.0)*/
) {
    Layout(
        {
            Box(Modifier.layoutId("icon")) { icon() }
            if (label != null) {
                Box(
                    Modifier
                        .layoutId("label")
                        .padding(horizontal = BottomNavigationItemHorizontalPadding)
                ) { label() }
            }
        }
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "icon" }.measure(constraints)

        val labelPlaceable = label?.let {
            measurables.first { it.layoutId == "label" }.measure(
                // Measure with loose constraints for height as we don't want the label to take up more
                // space than it needs
                constraints.copy(minHeight = 0)
            )
        }

        // If there is no label, just place the icon.
        placeIcon(iconPlaceable, constraints)

    }
}

private val BottomNavigationItemHorizontalPadding = 12.dp

private fun MeasureScope.placeIcon(
    iconPlaceable: Placeable,
    constraints: Constraints
): MeasureResult {
    val height = constraints.maxHeight
    val iconY = (height - iconPlaceable.height) / 2
    return layout(iconPlaceable.width, height) {
        iconPlaceable.placeRelative(0, iconY)
    }
}