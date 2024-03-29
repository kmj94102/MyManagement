package com.example.mymanagement.util

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymanagement.view.compose.ui.theme.Black
import com.example.mymanagement.view.compose.ui.theme.myFont

fun textStyle12(): TextStyle = TextStyle(
    color = Black,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

fun textStyle12B(): TextStyle = TextStyle(
    color = Black,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp
)

fun textStyle16(): TextStyle = TextStyle(
    color = Black,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

fun textStyle16B(): TextStyle = TextStyle(
    color = Black,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

fun textStyle24(): TextStyle = TextStyle(
    color = Black,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp
)

fun textStyle24B(): TextStyle = TextStyle(
    color = Black,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp
)

fun Modifier.nonRippleClickable(
    onClick: () -> Unit
) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

object TopWithFooter: Arrangement.Vertical {
    override fun Density.arrange(totalSize: Int, sizes: IntArray, outPositions: IntArray) {
        var y = 0
        sizes.forEachIndexed { index, size ->
            outPositions[index] = y
            y += size
        }
        if (y < totalSize) {
            outPositions[outPositions.lastIndex] = totalSize - sizes.last()
        }
    }

}

fun getColorWithHexCode(hexCode: String) = Color(java.lang.Long.decode(hexCode))

// https://betterprogramming.pub/gridview-and-lazycolum-integration-with-jetpack-compose-e90849aeb6d3
fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalSpacer: Dp = 5.dp,
    itemContent: @Composable BoxScope.(T, Int) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = false),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex], itemIndex)
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
        Spacer(modifier = Modifier.height(verticalSpacer))
    }
}