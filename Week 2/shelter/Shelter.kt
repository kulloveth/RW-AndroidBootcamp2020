package shelter

import cafee.product.Cat

data class Shelter(val shelter_id : Int,
              val address : String,
              val phone : String,
              val cats : List<Cat>)