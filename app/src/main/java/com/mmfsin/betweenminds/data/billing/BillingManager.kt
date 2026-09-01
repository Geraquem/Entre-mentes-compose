package com.mmfsin.betweenminds.data.billing

import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryPurchasesParams
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class BillingManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val billingClient = BillingClient.newBuilder(context)
        .setListener { _, _ -> }
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder()
                .enableOneTimeProducts()
                .build()
        )
        .build()

    fun connect(
        onPurchaseChecked: (Boolean) -> Unit
    ) {
        billingClient.startConnection(
            object : BillingClientStateListener {

                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        checkPurchase(onPurchaseChecked)
                    } else onPurchaseChecked(false)
                }

                override fun onBillingServiceDisconnected() {
                    // Se ha perdido la conexión
                    val a = 2
                }
            }
        )
    }

    fun checkPurchase(onResult: (Boolean) -> Unit) {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        billingClient.queryPurchasesAsync(params) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val isPurchased = purchases.any { purchase ->
                    purchase.products.contains("all_packs") && purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                }
                onResult(isPurchased)
            } else {
                onResult(false)
            }
        }
    }

    suspend fun isAllPacksPurchased(): Boolean =
        suspendCancellableCoroutine { continuation ->

            fun checkPurchase() {
                val params = QueryPurchasesParams.newBuilder()
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()

                billingClient.queryPurchasesAsync(params) { billingResult, purchases ->

                    if (billingResult.responseCode ==
                        BillingClient.BillingResponseCode.OK
                    ) {
                        val purchased = purchases.any { purchase ->
                            purchase.products.contains("all_packs") &&
                                    purchase.purchaseState == Purchase.PurchaseState.PURCHASED
                        }

                        continuation.resume(purchased)
                    } else {
                        continuation.resume(false)
                    }
                }
            }

            if (billingClient.isReady) {
                checkPurchase()
            } else {
                billingClient.startConnection(
                    object : BillingClientStateListener {

                        override fun onBillingSetupFinished(
                            billingResult: BillingResult
                        ) {
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                checkPurchase()
                            } else {
                                continuation.resume(false)
                            }
                        }

                        override fun onBillingServiceDisconnected() {
                            val a = 2
                        }
                    }
                )
            }
        }
}