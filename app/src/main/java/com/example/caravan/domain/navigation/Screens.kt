package com.example.caravan.domain.navigation


sealed class Screens(val route: String) {
    object Login: Screens(route = "login_screen")
    object SelectUserType: Screens(route = "select_screen")

    object InfoBuyer: Screens(route = "info_buyer_screen")
    object InfoSeller: Screens(route = "info_seller_screen")
    object InfoRep: Screens(route = "info_rep_screen")
    object Wait: Screens(route = "wait_screen")

    object HomeBuyer: Screens(route = "home_buyer_screen")
    object CartBuyer: Screens(route = "cart_buyer_screen")
    object ProductBuyer: Screens(route = "product_buyer_screen/{index}"){
        fun passItem(index: String): String{
            return "product_buyer_screen/$index"
        }
    }


    object ProductsListSeller: Screens(route = "order_seller_screen")
    object OrdersSeller: Screens(route = "product_list_seller_screen")
    object HomeSeller: Screens(route = "home_seller_screen")

    object ProductSeller: Screens(route = "product_seller_screen/{item}"){
        fun passItem(item: String): String{
            return "product_seller_screen/$item"
        }
    }


    object HomeRep: Screens(route = "home_rep_screen")



    object NoNetError: Screens(route = "no_net_screen")
    object SWError: Screens(route = "all_error_screen")

    object Main: Screens(route = "main_screen/{userId}"){
        fun passItem(userId: String): String{
            return "main_screen/$userId"
        }
}



object MySellers: Screens(route = "rep_my_sellers")
    object MyBuyers: Screens(route = "rep_my_buyers")

}