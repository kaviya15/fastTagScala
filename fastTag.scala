import scala.collection.mutable._
import java.util.Date

class getFastTag(var registerdUsers:HashMap[String,HashMap[String,String]]){

    private def getDetailsFromUser(vehicleNumber:String):HashMap[String,String]= {
        // Map to store key-value pairs
        var userdetails:HashMap[String,String] =  HashMap()
        //Get owner name
        println("Enter Owner Name ")
        var name = scala.io.StdIn.readLine();
        //GET VEHICLE TYPE
        println("Enter vehicle type's Index")
        var vehicleModel= listModel
        // appending data to Map
        userdetails+= (("vehicleModel" ,vehicleModel), ("modelPrice",findModel(vehicleModel)) ,("name" ,name),("checkedInTime",new Date().getTime().toString()))
        return(userdetails)
  }
   private def listModel:String={
       var models = Array("car/jeep/van","bus/truck","1-3axlevehicle","4-6axlevehicle","HCM/EME ","7-moreaxlevehicle")
       models.zipWithIndex.foreach{ case(ind , element) =>  println(s"${ind}. ${element}") }

//        for(model <- 0  until models.length){
//            println(s"${model}. ${models(model)}")
//        }
      var vehicleModel = scala.io.StdIn.readInt()
      return(models(vehicleModel))
   }

    // register the vehicle 
    private def registerVehicle(vehicleNumber:String,vehicleDetails:HashMap[String,String]){
        registerdUsers+=((vehicleNumber,vehicleDetails))
    }
    
    // Get fare for particular Type 
    private def findModel(vehicleModel:String):String={
        var vehModel = Map("car/jeep/van" -> 85 ,
        "bus/truck"-> 285,"1-3axlevehicle"-> 315,"4-6axlevehicle"->450,"HCM/EME "->450 , "7-moreaxlevehicle"-> 550)
        return(vehModel.get(vehicleModel).get.toString())
    }

    // calculate the total bill amount
      def billFare(vehicleNumber:String,registered:Boolean){ 
        if(registered) {
                  var getVehicleData  = registerdUsers.get(vehicleNumber)
                  var billAmountForModel = getVehicleData.get("modelPrice").toInt
                  var timeSeconds = getVehicleData.get("checkedInTime").toLong/60000
                  lazy val time = new Date().getTime()/60000
                  if((timeSeconds-time)<=30)
                        println(s"Total bill = ${billAmountForModel/2}")
                  else
                        println(s"Total bill = ${billAmountForModel}")
        }

        else{
             // Get Details from user 
            var vehicleData:HashMap[String,String] =  getDetailsFromUser(vehicleNumber)
            var billAmountForModel = vehicleData.get("modelPrice").get.toInt
            println(s"Total bill = ${billAmountForModel+50} // current toll fare ${billAmountForModel} + 50 registration fee")
            // //register account
            registerVehicle(vehicleNumber,vehicleData)
        }
    }
    

}



object fastTag extends App{
    
    var registerdUsers:HashMap[String,HashMap[String,String]] =  HashMap()
    val fastTagUser:getFastTag = new getFastTag(registerdUsers)
    
    //GET VEHICLE NUMBER FROM USER..
    println("Enter the vehicle Number?..")
    val vehicleNumber:String = scala.io.StdIn.readLine().replace(" " ,"").toUpperCase;

    //FETCH THE USER DETAILS IF USER EXISTS
    if(registerdUsers.contains(vehicleNumber))
        fastTagUser.billFare(vehicleNumber,true)
    
    else
    {
         val questions =  Array("Vehicle Number you entered doesn't have a fast tag",
        "Choose 1 to Register for fastTag","Choose 2 to exit","Enter your option: ")
         questions.foreach(arr=>println(arr))

         val optionSelected:Int =  scala.io.StdIn.readInt();

         if(optionSelected==1){
                fastTagUser.billFare(vehicleNumber , false)
         }
    }
}

