import scala.collection.mutable._
import java.util.Date;
import scala.util.control.Breaks._


class profileCreation( var userEmail:String,var userName:String , var listOfFriends:Set[profileCreation]  = Set(),var listOfPosts:HashMap[Int,Post] = HashMap())
 

class Post(val id:Int , val post:String , val date:Date , val like:Int, val hideFeed:Boolean = false , val disLike:Int)
   

class connectWorld(var users:HashMap[String, profileCreation] ){

    var listOfUsers:HashMap[String, profileCreation]  = users

    var userId:String = null;
    var generateId = 1;
    // Register the user 

    def registerUser={
        println("Enter your Email...")
        var email = scala.io.StdIn.readLine();
        if(listOfUsers.contains(email)){
           println("user id had already taken ")
        }
        else{
            println("Enter your name...")
            var name = scala.io.StdIn.readLine();
            var node = new profileCreation(email,name);
            userId = node.userEmail;
            listOfUsers+= ((userId , node))
            println("Account Registerd Successfully..")
        }


    }

    def followUser(friendId:String){
       
        if(listOfUsers.contains(friendId)){
            listOfUsers.get(userId).get.listOfFriends+=(listOfUsers.get(friendId).get)
            println(listOfUsers.get(userId).get.listOfFriends)
        }
        else
            println("No such user Found!!!")
    }


    def postFeed={
        
        println("Upload your Feed")
        var feed = scala.io.StdIn.readLine();
        var id = generateId
        var postFeed = new Post(id = id,post=feed,date = new Date(),like=0,disLike=0)
        listOfUsers.get(userId).get.listOfPosts+=((postFeed.id ,postFeed ))
        println(s"Post successfully saved and yout post ID is ${id}")
        generateId+=1
       // print(listOfUsers.get(userId).get.listOfPosts)

    }

    def deleteUser={

        println("Enter userId to delete")
        var userId = scala.io.StdIn.readLine();
        if(listOfUsers.contains(userId)){
        listOfUsers-=(userId)
        println("Deleted Successfully...")
        }
        else 
            println("No such user Found...")
    }

    def unFollowUser(friendId:String){
        if(listOfUsers.contains(friendId)){
         listOfUsers.get(userId).get.listOfFriends -=  listOfUsers.get(friendId).get
        }
        else{
            println("No user!!")
        }

    }

    def getFeedLikesCount(feedId:Int):Any={

        if(listOfUsers.get(userId).get.listOfPosts.contains(feedId)){
        return(listOfUsers.get(userId).get.listOfPosts.get(feedId).get.like) 
        }
        else{
            return("No Post found!!")
    }
    }

    def getMyFeeds={
        println(listOfUsers.get(userId).get.listOfPosts)
    }
    
    def getUserFeeds(usersId:String){
      
        if(listOfUsers.contains(usersId)){
        println(listOfUsers.get(usersId).get.listOfPosts)  
        }
        else {
            println("No user feeds")
        }
    }

}


object  socialMedia extends App{
// List of users 
var listUsers:HashMap[String, profileCreation]  = HashMap()

var user :connectWorld = null
 
def repeated_process={
println("Enter your choice (Choose the index number)")
println(" 0.Register Account \n 1.Follow user \n 2.postfeeds \n 3.getFeedLikesCount  \n 4.unFollow user \n 5.Get user Feeds \n 6.Get my feeds \n 7.Delete user \n 8.Exit")
var getOption = scala.io.StdIn.readInt()

getOption match {
    case 0 => {
       user =  new connectWorld(listUsers)
       user.registerUser;
       repeated_process;
    }
    case 1 => { 
        println("Enter the userId needed to be followed...")
        user.followUser(scala.io.StdIn.readLine()); 
        repeated_process;
    }
    case 2 => { 
        user.postFeed; 
        repeated_process;
    }
    case 3 => { 
        println("Enter the Feed Id..")
        println(user.getFeedLikesCount(scala.io.StdIn.readInt())); 
        repeated_process;
    }
    case 4 => {
         println("Enter the userId needed to be Unfollowed...")
         user.unFollowUser( scala.io.StdIn.readLine()); 
         repeated_process;
        }
    case 5 => {
          
          println("Enter the user Id to get their Feeds...")
          user.getUserFeeds(scala.io.StdIn.readLine());
          repeated_process;
        }
    case 6 => {
         user.getMyFeeds; 
         repeated_process;
        }
    case 7 => {
         user.deleteUser; 
         repeated_process;
        }
    case 8=> {println("exited")}
    case _ => { 
               println("select the correct option");
               repeated_process
    }            
}
}
repeated_process;
}
