package com.example.vimos.catalog.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryData(
    @SerialName("id")
    val id: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("seo_title")
    val seoTitle: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("depth")
    val depth: Int?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("seo_description")
    val seoDescription: String?,
    @SerialName("banner_image")
    val bannerImage: String?,
    @SerialName("banner_mobile_image")
    val bannerMobileImage: String?,
    @SerialName("banner_href")
    val bannerHref: String?,
    @SerialName("subCategories")
    val subCategories: List<CategoryData>?,
)

@Serializable
data class SlugElement(
    @SerialName("slug")
    val slug: String?,
)

@Serializable
data class ProductElement(
    @SerialName("id")
    val id: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("images")
    val images: List<Images>?,
    @SerialName("purchase")
    val purchase: Purchase?,
    @SerialName("sku")
    val sku: Int?,
    @SerialName("units")
    val units: String?,
)

@Serializable
data class Purchase(
    @SerialName("price")
    val price: Int?,
    @SerialName("price_old")
    val priceOld: Int?,
    @SerialName("size_discount")
    val discount: Int?
)

@Serializable
data class Images(
    @SerialName("original")
    val original: String?
)