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
        println("Enter vehicle type")
        var vehicleModel = scala.io.StdIn.readLine()
        // appending data to Map
        userdetails+= (("vehicleModel" ,vehicleModel),("name" ,name),("checkedInTime",new Date().getTime().toString()))
        return(userdetails)
  }

    // register the vehicle 
    private def registerVehicle(vehicleNumber:String,vehicleDetails:HashMap[String,String]){
        registerdUsers+=((vehicleNumber,vehicleDetails))
    }
    
    // Get fare for particular Type 
    private def findModel(vehicleModel:String):Int={
         var vehModel = vehicleModel.toLowerCase()
         var carTypes:Array[String] = Array("car","jeep","van")
         var busTypes:Array[String] = Array("bus","truck")
         var axleTypes:Array[String] = Array("1-axlevehicle","2-axlevehicle","3-axlevehicle")
         var highAxleTypes:Array[String] = Array("4-axlevehicle","5-axlevehicle","6-axlevehicle","hcm","eme")
         if(vehicleModel.toLowerCase() == "lcv") return(135)
         else if(carTypes.indexOf(vehModel)>=0)return(85)
         else if (busTypes.indexOf(vehModel)>=0) return(285)
         else if (axleTypes.indexOf(vehModel)>=0)return(315)
         else if (highAxleTypes.indexOf(vehModel)>=0)return(450)    
         else if (vehModel.split('-')(1) == "axlevehicle")return(550)
         else return(0)
    }

    // calculate the total bill amount
      def billFare(vehicleNumber:String,registered:Boolean){ 
        if(registered) {
                  var getVehicleData  = registerdUsers.get(vehicleNumber)
                  var billAmountForModel:Int = findModel(getVehicleData.get("vehicleModel"))
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
            var billAmountForModel = findModel(vehicleData.get("vehicleModel").get)
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
         if(optionSelected==1)
                fastTagUser.billFare(vehicleNumber , false)
         
    }
}
