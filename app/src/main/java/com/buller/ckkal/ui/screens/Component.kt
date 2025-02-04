package com.buller.ckkal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Component: ImageVector
    get() {
        if (_component != null) {
            return _component!!
        }
        _component = Builder(name = "Component", defaultWidth = 547.952.dp, defaultHeight =
                513.494.dp, viewportWidth = 547.952f, viewportHeight = 513.494f).apply {
            path(fill = SolidColor(Color(0xFF4d45c4)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(236.156f, 94.5f)
                arcToRelative(66.5f, 64.5f, 0.0f, true, false, 133.0f, 0.0f)
                arcToRelative(66.5f, 64.5f, 0.0f, true, false, -133.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF4d45c4)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(238.656f, 94.5f)
                arcToRelative(64.0f, 62.0f, 0.0f, true, false, 128.0f, 0.0f)
                arcToRelative(64.0f, 62.0f, 0.0f, true, false, -128.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFd4134e)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(129.156f, 216.5f)
                arcToRelative(115.5f, 111.5f, 0.0f, true, false, 231.0f, 0.0f)
                arcToRelative(115.5f, 111.5f, 0.0f, true, false, -231.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFd4134e)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(131.656f, 216.5f)
                arcToRelative(113.0f, 109.0f, 0.0f, true, false, 226.0f, 0.0f)
                arcToRelative(113.0f, 109.0f, 0.0f, true, false, -226.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFd14fe3)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(300.511f, 287.329f)
                arcToRelative(63.795f, 64.183f, 83.0f, true, false, 127.409f, -15.644f)
                arcToRelative(63.795f, 64.183f, 83.0f, true, false, -127.409f, 15.644f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFd14fe3)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(302.992f, 287.025f)
                arcToRelative(61.295f, 61.683f, 83.0f, true, false, 122.446f, -15.035f)
                arcToRelative(61.295f, 61.683f, 83.0f, true, false, -122.446f, 15.035f)
                close()
            }
            path(fill = SolidColor(Color(0xFFf3e064)), stroke = SolidColor(Color(0xFFf3e064)),
                    strokeLineWidth = 5.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(52.542f, 346.555f)
                arcToRelative(58.939f, 59.297f, 85.996f, true, false, 118.304f, -8.281f)
                arcToRelative(58.939f, 59.297f, 85.996f, true, false, -118.304f, 8.281f)
                close()
            }
        }
        .build()
        return _component!!
    }

private var _component: ImageVector? = null

@Preview(showBackground = false)
@Composable
fun ComponentPreview() {
    Image(
        painter = rememberVectorPainter(image = Component),
        contentDescription = null)
}
