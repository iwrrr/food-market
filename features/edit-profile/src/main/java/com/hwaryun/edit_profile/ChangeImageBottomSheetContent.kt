package com.hwaryun.edit_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.components.atoms.AsphaltText
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeImageBottomSheetContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val configuration = LocalConfiguration.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        containerColor = Color.Transparent,
        dragHandle = null,
    ) {
        FilledIconButton(
            modifier = Modifier.align(Alignment.End),
            colors = IconButtonDefaults.filledIconButtonColors(AsphaltTheme.colors.cool_gray_1cCp_100),
            onClick = onDismiss
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = modifier
                .clip(AsphaltTheme.shapes.small)
                .background(AsphaltTheme.colors.pure_white_500)
                .heightIn(max = (configuration.screenHeightDp * 0.25).dp)
                .fillMaxSize()
        ) {
            AsphaltText(
                text = "Ganti foto profil",
                style = AsphaltTheme.typography.titleLarge
            )
        }
    }
}