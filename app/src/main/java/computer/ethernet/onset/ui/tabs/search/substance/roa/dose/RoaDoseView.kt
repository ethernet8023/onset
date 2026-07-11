package computer.ethernet.onset.ui.tabs.search.substance.roa.dose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import computer.ethernet.onset.data.substances.classes.roa.RoaDose

@Preview(showBackground = true)
@Composable
fun RoaDoseViewPreview(
    @PreviewParameter(RoaDosePreviewProvider::class) roaDose: RoaDose,
) {
    RoaDoseView(
        roaDose = roaDose,
    )
}

@Composable
fun RoaDoseView(
    roaDose: RoaDose,
    modifier: Modifier = Modifier
) {
    DoseClassificationRow(
        modifier = modifier,
        lightMin = roaDose.lightMin,
        commonMin = roaDose.commonMin,
        strongMin = roaDose.strongMin,
        heavyMin = roaDose.heavyMin,
        unit = roaDose.units
    )
}