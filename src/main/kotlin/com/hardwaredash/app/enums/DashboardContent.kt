package com.hardwaredash.app.enums

enum class DashboardContent(value: String, id: Int) {
    CONFIG("Configuration", 1),
    PRODUCT("Product", 2);
}

enum class ProductUnit(name: String, shortName: String) {
    GRAM("Gram", "gm"),
    LITRE("Litre", "ltr"),
    PIECE("Piece", "pcs"),
    FEET("Feet", "ft");
}