package com.dicoding.harvestscan.data.local

import androidx.annotation.StringRes
import com.dicoding.harvestscan.R

data class DiseaseInfo(
    @StringRes val description: Int,
    @StringRes val tips: Int
)

object DiseaseData {
    val diseaseDetails = mapOf(
        "Apple: Apple_scab" to DiseaseInfo(
            description = R.string.desc_1,
            tips = R.string.tips_1
        ),
        "Apple: Black_rot" to DiseaseInfo(
            description = R.string.desc_2,
            tips = R.string.tips_2
        ),
        "Apple: Cedar_apple_rust" to DiseaseInfo(
            description = R.string.desc_3,
            tips = R.string.tips_3
        ),
        "Apple: healthy" to DiseaseInfo(
            description = R.string.desc_4,
            tips = R.string.tips_4
        ),
        "Blueberry: healthy" to DiseaseInfo(
            description = R.string.desc_5,
            tips = R.string.tips_5
        ),
        "Cherry (including_sour): Powdery mildew" to DiseaseInfo(
            description = R.string.desc_6,
            tips = R.string.tips_6
        ),
        "Cherry (including_sour): healthy" to DiseaseInfo(
            description = R.string.desc_7,
            tips = R.string.tips_7
        ),
        "Corn (maize): Cercospora leaf spot, Gray leaf spot" to DiseaseInfo(
            description = R.string.desc_8,
            tips = R.string.tips_8
        ),
        "Corn (maize): Common_rust_" to DiseaseInfo(
            description = R.string.desc_9,
            tips = R.string.tips_9
        ),
        "Corn (maize): Northern_Leaf_Blight" to DiseaseInfo(
            description = R.string.desc_10,
            tips = R.string.tips_10
        ),
        "Corn (maize): healthy" to DiseaseInfo(
            description = R.string.desc_11,
            tips = R.string.tips_11
        ),
        "Grape: Black rot" to DiseaseInfo(
            description = R.string.desc_12,
            tips = R.string.tips_12
        ),
        "Grape: Esca (Black Measles)" to DiseaseInfo(
            description = R.string.desc_13,
            tips = R.string.tips_13
        ),
        "Grape: Leaf blight (Isariopsis Leaf Spot)" to DiseaseInfo(
            description = R.string.desc_14,
            tips = R.string.tips_14
        ),
        "Grape: healthy" to DiseaseInfo(
            description = R.string.desc_15,
            tips = R.string.tips_15
        ),
        "Orange: Haunglongbing (Citrus greening)" to DiseaseInfo(
            description = R.string.desc_16,
            tips = R.string.tips_16
        ),
        "Peach: Bacterial spot" to DiseaseInfo(
            description = R.string.desc_17,
            tips = R.string.tips_17
        ),
        "Peach: healthy" to DiseaseInfo(
            description = R.string.desc_18,
            tips = R.string.tips_18
        ),
        "Pepper, bell: Bacterial_spot" to DiseaseInfo(
            description = R.string.desc_19,
            tips = R.string.tips_19
        ),
        "Pepper, bell: healthy" to DiseaseInfo(
            description = R.string.desc_20,
            tips = R.string.tips_20
        ),
        "Potato: Early blight" to DiseaseInfo(
            description = R.string.desc_21,
            tips = R.string.tips_21
        ),
        "Potato: Late blight" to DiseaseInfo(
            description = R.string.desc_22,
            tips = R.string.tips_22
        ),
        "Potato: healthy" to DiseaseInfo(
            description = R.string.desc_23,
            tips = R.string.tips_23
        ),
        "Raspberry: healthy" to DiseaseInfo(
            description = R.string.desc_24,
            tips = R.string.tips_24
        ),
        "Soybean: healthy" to DiseaseInfo(
            description = R.string.desc_25,
            tips = R.string.tips_25
        ),
        "Squash: Powdery_mildew" to DiseaseInfo(
            description = R.string.desc_26,
            tips = R.string.tips_26
        ),
        "Strawberry: Leaf scorch" to DiseaseInfo(
            description = R.string.desc_27,
            tips = R.string.tips_27
        ),
        "Strawberry: healthy" to DiseaseInfo(
            description = R.string.desc_28,
            tips = R.string.tips_28
        ),
        "Tomato: Bacterial_spot" to DiseaseInfo(
            description = R.string.desc_29,
            tips = R.string.tips_29
        ),
        "Tomato: Early_blight" to DiseaseInfo(
            description = R.string.desc_30,
            tips = R.string.tips_30
        ),
        "Tomato: Late blight" to DiseaseInfo(
            description = R.string.desc_31,
            tips = R.string.tips_31
        ),
        "Tomato: Leaf Mold" to DiseaseInfo(
            description = R.string.desc_32,
            tips = R.string.tips_32
        ),
        "Tomato: Septoria leaf spot" to DiseaseInfo(
            description = R.string.desc_33,
            tips = R.string.tips_33
        ),
        "Tomato: Spider mites, Two-spotted spider mite" to DiseaseInfo(
            description = R.string.desc_34,
            tips = R.string.tips_34
        ),
        "Tomato: Target Spot" to DiseaseInfo(
            description = R.string.desc_35,
            tips = R.string.tips_35
        ),
        "Tomato: Tomato Yellow Leaf Curl Virus" to DiseaseInfo(
            description = R.string.desc_36,
            tips = R.string.tips_36
        ),
        "Tomato: Tomato mosaic virus" to DiseaseInfo(
            description = R.string.desc_37,
            tips = R.string.tips_37
        ),
        "Tomato: healthy" to DiseaseInfo(
            description = R.string.desc_38,
            tips = R.string.tips_38
        )
    )
}
