package com.hwaryun.transaction_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hwaryun.transaction_detail.TransactionDetailRoute

const val TRANSACTION_ID = "transaction_id"

const val transactionDetailRoute = "transaction_detail_route/{$TRANSACTION_ID}"

fun NavController.navigateToTransactionDetailGraph(
    transactionId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate("transaction_detail_route/$transactionId", navOptions)
}

fun NavGraphBuilder.transactionDetailsScreen(
    popBackStack: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = transactionDetailRoute,
        arguments = listOf(
            navArgument(TRANSACTION_ID) { type = NavType.IntType },
        ),
    ) {
        TransactionDetailRoute(
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar
        )
    }
}