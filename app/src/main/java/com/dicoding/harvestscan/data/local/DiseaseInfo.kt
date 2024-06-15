package com.dicoding.harvestscan.data.local

data class DiseaseInfo(
    val description: String,
    val tips: String
)

object DiseaseData {
    val diseaseDetails = mapOf(
        "Apple: Apple_scab" to DiseaseInfo(
            description = "A fungal disease causing dark, velvety spots on leaves.",
            tips = "Use fungicides and resistant varieties to manage apple scab."
        ),
        "Apple: Black_rot" to DiseaseInfo(
            description = "A fungal disease that causes black, rotten spots on apples.",
            tips = "Remove and destroy infected fruits and use fungicides."
        ),
        "Apple: Cedar_apple_rust" to DiseaseInfo(
            description = "A fungal infection causing reddish-brown spots on leaves.",
            tips = "Remove infected leaves and use fungicides."
        ),
        "Apple: healthy" to DiseaseInfo(
            description = "Healthy apple with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Blueberry: healthy" to DiseaseInfo(
            description = "Healthy blueberry plant.",
            tips = "Provide adequate water and nutrients for healthy growth."
        ),
        "Cherry (including_sour): Powdery mildew" to DiseaseInfo(
            description = "A fungal disease causing white, powdery spots on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Cherry (including_sour): healthy" to DiseaseInfo(
            description = "Healthy cherry plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Corn (maize): Cercospora leaf spot, Gray leaf spot" to DiseaseInfo(
            description = "A fungal disease causing gray, necrotic spots on corn leaves.",
            tips = "Use resistant varieties and apply fungicides."
        ),
        "Corn (maize): Common_rust_" to DiseaseInfo(
            description = "A fungal disease causing reddish-brown pustules on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Corn (maize): Northern_Leaf_Blight" to DiseaseInfo(
            description = "A fungal disease causing large, irregular lesions on leaves.",
            tips = "Use resistant varieties and apply fungicides."
        ),
        "Corn (maize): healthy" to DiseaseInfo(
            description = "Healthy corn plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Grape: Black rot" to DiseaseInfo(
            description = "A fungal disease causing black spots on grape leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Grape: Esca (Black Measles)" to DiseaseInfo(
            description = "A fungal disease causing black measles on grapevines.",
            tips = "Prune infected vines and apply fungicides."
        ),
        "Grape: Leaf blight (Isariopsis Leaf Spot)" to DiseaseInfo(
            description = "A fungal disease causing necrotic lesions on grape leaves.",
            tips = "Remove infected leaves and apply fungicides."
        ),
        "Grape: healthy" to DiseaseInfo(
            description = "Healthy grape plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Orange: Haunglongbing (Citrus greening)" to DiseaseInfo(
            description = "A bacterial disease causing yellowing and greening of citrus leaves.",
            tips = "Remove infected leaves and use insecticides."
        ),
        "Peach: Bacterial spot" to DiseaseInfo(
            description = "A bacterial disease causing dark, water-soaked lesions on leaves.",
            tips = "Apply bactericides and remove infected leaves."
        ),
        "Peach: healthy" to DiseaseInfo(
            description = "Healthy peach plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Pepper, bell: Bacterial_spot" to DiseaseInfo(
            description = "A bacterial disease causing dark, water-soaked lesions on leaves.",
            tips = "Apply bactericides and remove infected leaves."
        ),
        "Pepper, bell: healthy" to DiseaseInfo(
            description = "Healthy pepper plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Potato: Early blight" to DiseaseInfo(
            description = "A fungal disease causing dark, sunken spots on leaves and tubers.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Potato: Late blight" to DiseaseInfo(
            description = "A fungal disease causing water-soaked lesions on leaves and tubers.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Potato: healthy" to DiseaseInfo(
            description = "Healthy potato plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Raspberry: healthy" to DiseaseInfo(
            description = "Healthy raspberry plant.",
            tips = "Provide adequate water and nutrients for healthy growth."
        ),
        "Soybean: healthy" to DiseaseInfo(
            description = "Healthy soybean plant.",
            tips = "Provide adequate water and nutrients for healthy growth."
        ),
        "Squash: Powdery_mildew" to DiseaseInfo(
            description = "A fungal disease causing white, powdery spots on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Strawberry: Leaf scorch" to DiseaseInfo(
            description = "A fungal disease causing dark, necrotic lesions on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Strawberry: healthy" to DiseaseInfo(
            description = "Healthy strawberry plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        ),
        "Tomato: Bacterial_spot" to DiseaseInfo(
            description = "A bacterial disease causing dark, water-soaked lesions on leaves.",
            tips = "Apply bactericides and remove infected leaves."
        ),
        "Tomato: Early_blight" to DiseaseInfo(
            description = "A fungal disease causing dark, sunken spots on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Tomato: Late blight" to DiseaseInfo(
            description = "A fungal disease causing water-soaked lesions on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Tomato: Leaf Mold" to DiseaseInfo(
            description = "A fungal disease causing white, powdery spots on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Tomato: Septoria leaf spot" to DiseaseInfo(
            description = "A fungal disease causing dark, necrotic lesions on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Tomato: Spider mites, Two-spotted spider mite" to DiseaseInfo(
            description = "A mite infestation causing white, speckled leaves.",
            tips = "Use miticides and remove infected leaves."
        ),
        "Tomato: Target Spot" to DiseaseInfo(
            description = "A fungal disease causing dark, necrotic lesions on leaves.",
            tips = "Apply fungicides and remove infected leaves."
        ),
        "Tomato: Tomato Yellow Leaf Curl Virus" to DiseaseInfo(
            description = "A viral disease causing yellowing and curling of leaves.",
            tips = "Use insecticides and remove infected leaves."
        ),
        "Tomato: Tomato mosaic virus" to DiseaseInfo(
            description = "A viral disease causing mottling and mosaic patterns on leaves.",
            tips = "Use insecticides and remove infected leaves."
        ),
        "Tomato: healthy" to DiseaseInfo(
            description = "Healthy tomato plant with no signs of disease.",
            tips = "Ensure proper care and maintenance for healthy growth."
        )
    )
}
