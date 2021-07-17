import scala.collection.mutable._
import java.util.Date


class fastTag{


    def getDetailsFromUser(vehicleNumber:String):HashMap[String,String]= {
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
    def registerVehicle(registerdUsers:HashMap[String,HashMap[String,String]],vehicleNumber:String){
        var data:HashMap[String,String] = getDetailsFromUser(vehicleNumber);
        registerdUsers+=((vehicleNumber,data))
        billFare(registerdUsers.get(vehicleNumber).get("checkedInTime"),registerdUsers.get(vehicleNumber).get("vehicleModel"),true)
    }
    
    // Get fare for particular Type 
    def findModel(vehicleModel:String):Int={
        var vehModel = vehicleModel.toLowerCase()
        var carTypes = Array("car","jeep","van")
        var busTypes = Array("bus","truck")
        var axleTypes = Array("1-axlevehicle","2-axlevehicle","3-axlevehicle")
        var highAxleTypes = Array("4-axlevehicle","5-axlevehicle","6-axlevehicle","hcm","eme")

         if(vehicleModel.toLowerCase() == "lcv") return(135)

         else if(carTypes.indexOf(vehModel)>=0)return(85)

         else if (busTypes.indexOf(vehModel)>=0) return(285)

         else if (axleTypes.indexOf(vehModel)>=0)return(315)
        
         else if (highAxleTypes.indexOf(vehModel)>=0)return(450)
                
         else if (vehModel.split('-')(1) == "axlevehicle")return(550)

         else return(0)
    }

    // calculate the total bill amount
    def billFare(Time:String,vehicleModel:String,registered:Boolean){   
        var bill = findModel(vehicleModel)
        if(registered){
            println(s"Total bill = ${bill+50} // current toll fare ${bill} + 50 registration fee")
        }
        else{
             // do some math for time.....
             lazy val time = new Date().getTime()/60000
             var timeSeconds= Time.toLong/60000
             if((timeSeconds-time)<=30){
              println(s"Total bill = ${bill/2}")

             }
             else{
                 println(s"Total bill = ${bill}")
             }  
        }
    }



}



object fastTag extends App{

            
    val fastTagUser:fastTag = new fastTag;

    var registerdUsers:HashMap[String,HashMap[String,String]] =  HashMap()
   

    //GET VEHICLE NUMBER FROM USER..
    println("Enter the vehicle Number?..")

    val vehicleNumber:String = scala.io.StdIn.readLine().replace(" " ,"").toUpperCase;

    //FETCH THE USER DETAILS IF USER EXISTS
    if(registerdUsers.contains(vehicleNumber))
    {
 
        fastTagUser.billFare(registerdUsers.get(vehicleNumber).get("checkedInTime"),  registerdUsers.get(vehicleNumber).get("vehicleModel"),false)
    }
    else
    {
         val questions =  Array("Vehicle Number you entered doesn't have a fast tag",
        "Choose 1 to Register for fastTag","Choose 2 to exit","Enter your option: ")
         questions.foreach(arr=>println(arr))

         val optionSelected:Int =  scala.io.StdIn.readInt();

         if(optionSelected==1){
           fastTagUser.registerVehicle(registerdUsers,vehicleNumber)
         }
    }
}







 // var users = HashMap(("vehicleModel","van") ,("name","pooja"),("checkedInTime", "1626535430215"))
    // registerdUsers+=(("TN73K5869" , users))



// abstract class abstractFastTag{
// //   def findVehicle(vehicleNumber:String);
//   def registerVehicle();
// //   def billFare();
// }
