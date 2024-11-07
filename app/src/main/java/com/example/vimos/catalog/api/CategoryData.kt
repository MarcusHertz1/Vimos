package com.example.vimos.catalog.api

import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("seo_title")
    val seoTitle: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("depth")
    val depth: Int?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("seo_description")
    val seoDescription: String?,
    @SerializedName("banner_image")
    val bannerImage: String?,
    @SerializedName("banner_mobile_image")
    val bannerMobileImage: String?,
    @SerializedName("banner_href")
    val bannerHref: String?,
    @SerializedName("subCategories")
    val subCategories: List<CategoryData>?,
)

data class SlugElement(
    @SerializedName("slug")
    val slug: String?,
)

data class ProductElement(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("images")
    val images: List<Images>?,
    @SerializedName("purchase")
    val purchase: Purchase?,
    @SerializedName("sku")
    val sku: Int?,
    @SerializedName("units")
    val units: String?,
)

data class Purchase(
    @SerializedName("price")
    val price: Int?,
    @SerializedName("price_old")
    val priceOld: Int?,
    @SerializedName("size_discount")
    val discount: Double?
)

data class Images(
    @SerializedName("original")
    val original: String?
)