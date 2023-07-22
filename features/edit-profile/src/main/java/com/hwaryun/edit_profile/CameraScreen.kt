package com.hwaryun.edit_profile

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hwaryun.designsystem.R
import com.hwaryun.designsystem.ui.asphalt.AsphaltTheme
import com.hwaryun.edit_profile.utils.bitmapToFile
import com.hwaryun.edit_profile.utils.imageProxyToBitmap
import com.hwaryun.edit_profile.utils.rotateBitmap
import kotlinx.coroutines.launch
import java.io.File

@Composable
internal fun CameraRoute(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: EditProfileViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CameraScreen(
        popBackStack = popBackStack,
        onShowSnackbar = onShowSnackbar,
        onChangePhoto = viewModel::changePhoto,
    )
}

@Composable
fun CameraScreen(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onChangePhoto: (photo: File?) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val coroutineScope = rememberCoroutineScope()
    val cameraController = remember { LifecycleCameraController(context) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(Color.BLACK)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            }
        )
        Column {
            OutlinedIconButton(
                modifier = Modifier.size(60.dp),
                colors = IconButtonDefaults.outlinedIconButtonColors(AsphaltTheme.colors.pure_white_500),
                onClick = {
                    val mainExecutor = ContextCompat.getMainExecutor(context)
                    cameraController.takePicture(
                        mainExecutor,
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                val rotatedBitmap = imageProxyToBitmap(image)
                                    ?.rotateBitmap(image.imageInfo.rotationDegrees)

                                rotatedBitmap?.let { bitmap ->
                                    val file = bitmapToFile(bitmap, context)
                                    onChangePhoto(file)

                                    image.close()
                                    popBackStack()
                                }
                            }

                            override fun onError(exception: ImageCaptureException) {
                                coroutineScope.launch {
                                    onShowSnackbar(
                                        exception.message.toString(),
                                        null
                                    )
                                }
                            }
                        }
                    )
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}