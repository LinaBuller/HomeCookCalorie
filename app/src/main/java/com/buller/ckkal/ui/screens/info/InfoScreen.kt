package com.buller.ckkal.ui.screens.info

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.Constants
import com.buller.ckkal.ui.screens.TextWithIconButton
import com.buller.ckkal.ui.theme.OceanGreen
import com.buller.ckkal.ui.theme.Orange
import com.buller.ckkal.ui.theme.Pink
import com.buller.ckkal.ui.theme.SalmonRed
import com.buller.ckkal.ui.theme.YellowD

@Composable
fun ProfileScreen() {
    var showRateDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val packageName = context.packageName
    val launchPlayStoreForApp = { launchPlayStoreForApp(context, packageName) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp).imePadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        HelloPart()
        Spacer(modifier = Modifier.height(32.dp))
        SendEmailButton(onCLick = {
            sendEmail(context = context, emailAddress = Constants.SUPPORT_EMAIL)
        })
        Spacer(modifier = Modifier.height(8.dp))
        RateAppButton(
            onCLick = {
                showRateDialog = true
            })
        Spacer(modifier = Modifier.height(8.dp))
        val linkText = stringResource(R.string.try_app)
        val shareText = stringResource(R.string.share_app)
        ShareAppButton(
            onCLick = {
                shareAppIntent(
                    context = context, linkText = linkText, shareText = shareText
                )
            })
        Spacer(modifier = Modifier.height(8.dp))
        PrivacyPolicyButton(
            onCLick = {
                openLink(context = context, Constants.PP_LINK)
            })

        Spacer(modifier = Modifier.height(8.dp))
        TermsButton(
            onCLick = {
                openLink(context = context, link = Constants.TERMS_LINK)
            })
        Spacer(modifier = Modifier.height(8.dp))
    }
    if (showRateDialog) {
        RateDialog(onDismissRequest = { showRateDialog = false }, onLaunchRateApp = {
            launchPlayStoreForApp()
        })
    }
}

@Composable
fun HelloPart() {
    Spacer(modifier = Modifier.height(32.dp))
    Image(painter = painterResource(id = R.drawable.chef_cook_icon), contentDescription = null)
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(R.string.hello_info),
        fontSize = 26.sp,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )

    Spacer(modifier = Modifier.height(32.dp))
    Text(
        text = stringResource(R.string.text_for_info_1),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.text_for_info_2),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(R.string.text_for_info_3),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.text_for_info_4),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.text_for_info_5),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(R.string.text_for_info_6),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
}

@Composable
fun SendEmailButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    TextWithIconButton(
        modifier = modifier,
        icon = Icons.Default.Email,
        text = R.string.send_email,
        onClick = onCLick,
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun RateAppButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    TextWithIconButton(
        modifier = modifier,
        icon = Icons.Default.ThumbUp,
        text = R.string.rate_app,
        onClick = onCLick,
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun ShareAppButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    TextWithIconButton(
        modifier = modifier,
        icon = Icons.Default.Share,
        text = R.string.share_app,
        onClick = onCLick,
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun PrivacyPolicyButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    TextWithIconButton(
        modifier = modifier,
        onClick = onCLick,
        icon = Icons.Default.Lock,
        text = R.string.privacy_policy,
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun TermsButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    TextWithIconButton(
        modifier = modifier,
        icon = Icons.Default.Info,
        text = R.string.terms_and_conditions,
        onClick = onCLick,
        buttonColors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

fun sendEmail(context: Context, emailAddress: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$emailAddress")
        putExtra(Intent.EXTRA_SUBJECT, "Question or suggestion")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

@Composable
fun RateDialog(
    onLaunchRateApp: () -> Unit, onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.like_app_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.like_app_subtitle_1),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.like_app_subtitle_2),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { onLaunchRateApp() },
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp),

                        ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.rate_star_1),
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp),
                            tint = SalmonRed
                        )
                    }
                    IconButton(
                        onClick = { onLaunchRateApp() },
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.rate_star_2),
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp),
                            tint = Orange

                        )
                    }
                    IconButton(
                        onClick = { onLaunchRateApp() },
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.rate_star_3),
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp),
                            tint = YellowD

                        )
                    }
                    IconButton(
                        onClick = { onLaunchRateApp() },
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.rate_star_4),
                            modifier = Modifier
                                .height(48.dp)
                                .width(48.dp),
                            tint = OceanGreen
                        )
                    }
                    IconButton(
                        onClick = { onLaunchRateApp() },
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(R.string.rate_star_5),
                            modifier = Modifier
                                .height(44.dp)
                                .width(44.dp),
                            tint = Pink

                        )
                    }

                }
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = { onLaunchRateApp() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    contentPadding = PaddingValues(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = stringResource(R.string.rate_us),
                    )
                    Text(
                        text = stringResource(R.string.rate_us),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        }
    }
}

fun launchPlayStoreForApp(context: Context, packageName: String) {
    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        )
    } catch (e: android.content.ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun shareAppIntent(context: Context, linkText: String, shareText: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TEXT,
            "${linkText}: https://play.google.com/store/apps/details?id=${context.packageName}"
        )
    }
    context.startActivity(Intent.createChooser(intent, shareText))
}

fun openLink(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
    //RateDialog(onDismissRequest = {}, onLaunchRateApp = {})
}