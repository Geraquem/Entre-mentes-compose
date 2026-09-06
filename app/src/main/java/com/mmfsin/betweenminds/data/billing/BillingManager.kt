package com.mmfsin.betweenminds.data.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class BillingManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val ALL_PACKS_ID = "all_packs"

    private val billingClient = BillingClient.newBuilder(context)
        .setListener { _, _ -> }
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder()
                .enableOneTimeProducts()
                .build()
        )
        .build()

    suspend fun getAllPacksInfo(): Pair<Boolean, String?> =
        suspendCancellableCoroutine { continuation ->

            /** Pair<Boolean, String>
             *  Boolean -> si está comprado
             *  String -> precio del paquete
             */

            fun query() {
                // 1. Consultar si está comprado
                val purchaseParams = QueryPurchasesParams.newBuilder()
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()

                billingClient.queryPurchasesAsync(purchaseParams) { purchaseResult, purchases ->

                    val isPurchased =
                        purchaseResult.responseCode == BillingClient.BillingResponseCode.OK &&
                                purchases.any {
                                    it.products.contains(ALL_PACKS_ID) && it.purchaseState == Purchase.PurchaseState.PURCHASED
                                }

                    // 2. Consultar el precio
                    val product = QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(ALL_PACKS_ID)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()

                    val productParams = QueryProductDetailsParams.newBuilder()
                        .setProductList(listOf(product))
                        .build()

                    billingClient.queryProductDetailsAsync(productParams) { productResult, result ->

                        val price =
                            if (productResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                result.productDetailsList
                                    .firstOrNull()
                                    ?.oneTimePurchaseOfferDetails
                                    ?.formattedPrice
                            } else null

                        continuation.resume(Pair(isPurchased, price))
                    }
                }
            }

            if (billingClient.isReady) {
                query()
            } else {
                billingClient.startConnection(
                    object : BillingClientStateListener {

                        override fun onBillingSetupFinished(
                            billingResult: BillingResult
                        ) {
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                query()
                            } else continuation.resume(Pair(false, null))
                        }

                        override fun onBillingServiceDisconnected() {
                            // ...
                        }
                    }
                )
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
                            purchase.products.contains(ALL_PACKS_ID) &&
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
                            } else continuation.resume(false)
                        }

                        override fun onBillingServiceDisconnected() {
                            println("Billing service error and disconnected")
                        }
                    }
                )
            }
        }

    fun purchaseAllPacks(activity: Activity) {
        val product = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(ALL_PACKS_ID)
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(listOf(product))
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, result ->

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                return@queryProductDetailsAsync
            }

            val productDetails = result.productDetailsList.firstOrNull() ?: return@queryProductDetailsAsync

            val productDetailsParams =
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .build()

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    listOf(productDetailsParams)
                )
                .build()

            billingClient.launchBillingFlow(
                activity,
                billingFlowParams
            )
        }
    }
}