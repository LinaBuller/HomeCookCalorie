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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HelloPart()
        Spacer(modifier = Modifier.padding(16.dp))
        SendEmailButton(onCLick = {
            sendEmail(context = context, emailAddress = Constants.SUPPORT_EMAIL)
        })
        Spacer(modifier = Modifier.padding(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            RateAppButton(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), onCLick = {
                showRateDialog = true
            })
            Spacer(modifier = Modifier.width(4.dp))
            val linkText = stringResource(R.string.try_app)
            val shareText = stringResource(R.string.share_app)
            ShareAppButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onCLick = {
                    shareAppIntent(
                        context = context,
                        linkText = linkText,
                        shareText = shareText
                    )
                })
        }

        Spacer(modifier = Modifier.padding(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            PrivacyPolicyButton(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), onCLick = {
                openLink(context = context, Constants.PP_LINK)
            })
            Spacer(modifier = Modifier.width(4.dp))
            TermsButton(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), onCLick = {
                openLink(context = context, link = Constants.TERMS_LINK)
            })
        }
    }
    if (showRateDialog) {
        RateDialog(onDismissRequest = { showRateDialog = false }, onLaunchRateApp = {
            launchPlayStoreForApp()
        })
    }
}

@Composable
fun HelloPart() {
    Text(
        text = stringResource(R.string.hello_info),
        fontSize = 26.sp,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Image(painter = painterResource(id = R.drawable.chef_cook_icon), contentDescription = null)
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = stringResource(R.string.text_for_info_1),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = stringResource(R.string.text_for_info_2),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.padding(4.dp))
    Text(
        text = stringResource(R.string.text_for_info_3),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = stringResource(R.string.text_for_info_4),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.padding(4.dp))
    Text(
        text = stringResource(R.string.text_for_info_5),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
    Spacer(modifier = Modifier.padding(4.dp))
    Text(
        text = stringResource(R.string.text_for_info_6),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalContentColor.current
    )
}

@Composable
fun SendEmailButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.primary
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Default.Email,
            contentDescription = stringResource(R.string.send_email),
            tint = LocalContentColor.current
        )
        Text(
            text = stringResource(R.string.send_email),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current
        )
    }
}

@Composable
fun RateAppButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.primary
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Default.ThumbUp,
            contentDescription = stringResource(R.string.rate_app),
            tint = LocalContentColor.current
        )
        Text(
            text = stringResource(R.string.rate_app),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current
        )
    }
}

@Composable
fun ShareAppButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.primary
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(R.string.share_app),
            tint = LocalContentColor.current
        )
        Text(
            text = stringResource(R.string.share_app),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current
        )
    }
}

@Composable
fun PrivacyPolicyButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.primary
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Default.Lock,
            contentDescription = "",
            tint = LocalContentColor.current
        )
        Text(
            text = stringResource(R.string.privacy_policy),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current
        )
    }
}

@Composable
fun TermsButton(modifier: Modifier = Modifier, onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.primary
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Default.Info,
            contentDescription = stringResource(R.string.terms_and_conditions),
            tint = LocalContentColor.current
        )
        Text(
            text = stringResource(R.string.terms_and_conditions),
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current
        )
    }
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