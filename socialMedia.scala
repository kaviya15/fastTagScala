import scala.collection.mutable._
import java.util.Date;
import scala.util.control.Breaks._

class nodeCreation( email:String,name:String){ 

    var userName:String = name;
    var userEmail:String = email;
    var listOfFriends:Set[nodeCreation]  = Set();
    var listOfPosts:HashMap[Int,Post] = HashMap();
}

class Post(Id:Int , feed:String , Date:Date , Like:Int,hideFeed:Boolean = false , dislike:Int){
    var id = Id;
    var post = feed;
    var date = Date;
    var like = Like;
    var disLike = dislike;
    var hide = hideFeed;
}

class connectWorld(users:HashMap[String, nodeCreation] ){

    var listOfUsers:HashMap[String, nodeCreation]  = users

    var userId:String = null;

    // Register the user 

    def registerUser(){
        println("Enter your Email...")
        var email = scala.io.StdIn.readLine();
        if(listOfUsers.contains(email)){
           println("user id had already taken ")
        }
        else{
            println("Enter your name...")
            var name = scala.io.StdIn.readLine();
            var node = new nodeCreation(email,name);
            userId = node.userEmail;
            listOfUsers+= ((userId , node))
            println("Account Registerd Successfully..")
        }


    }

    def followUser(){

        println("Enter the userId needed to be followed...")
        var friendId = scala.io.StdIn.readLine();
        if(listOfUsers.contains(friendId)){
            listOfUsers.get(userId).get.listOfFriends+=(listOfUsers.get(friendId).get)
            println(listOfUsers.get(userId).get.listOfFriends)
        }
        else
            println("No such user Found!!!")
    }


    def postFeed(){

        println("Enter Id for post")
        var id = scala.io.StdIn.readInt();
        println("Upload your Feed")
        var feed = scala.io.StdIn.readLine();
        var postFeed = new Post(id,feed,new Date(),Like=0,dislike=0)
        listOfUsers.get(userId).get.listOfPosts+=((postFeed.id ,postFeed ))
        print(listOfUsers.get(userId).get.listOfPosts)

    }

    def deleteUser(){

        println("Enter userId to delete")
        var userId = scala.io.StdIn.readLine();
        if(listOfUsers.contains(userId)){
        listOfUsers-=(userId)
        println("Deleted Successfully...")
        }
        else 
            println("No such user Found...")
    }

    def unFollowUser(){
         println("Enter the userId needed to be Unfollowed...")
         var friendId = scala.io.StdIn.readLine();
        if(listOfUsers.contains(friendId)){
         listOfUsers.get(userId).get.listOfFriends -=  listOfUsers.get(friendId).get
        }
        else{
            println("No user!!")
        }

    }

    def getFeedLikesCount():Any={

        println("Enter the Feed Id..")
        var feedId = scala.io.StdIn.readInt()
        if(listOfUsers.get(userId).get.listOfPosts.contains(feedId)){
        return(listOfUsers.get(userId).get.listOfPosts.get(feedId).get.like) 
        }
        else{
            return("No Post found!!")
    }
    }

    def getMyFeeds(){
        println(listOfUsers.get(userId).get.listOfPosts)
    }
    
    def getUserFeeds(){
        
        println("Enter the user Id to get their Feeds...")
        var usersId = scala.io.StdIn.readLine()
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
var listUsers:HashMap[String, nodeCreation]  = HashMap()

var user :connectWorld = null
def repeated_process(){
println("Enter your choice (Choose the index number)")
println(" 0.Register Account \n 1.Follow user \n 2.postfeeds \n 3.getFeedLikesCount  \n 4.unFollow user \n 5.Get user Feeds \n 6.Get my feeds \n 7.Delete user \n 8.Exit")
var getOption = scala.io.StdIn.readInt()
getOption match {
    case 0 => {
       user =  new connectWorld(listUsers)
       user.registerUser()
       repeated_process()
    }
    case 1 => { user.followUser(); repeated_process()}
    case 2 => { user.postFeed(); repeated_process()}
    case 3 => { println(user.getFeedLikesCount()); repeated_process()}
    case 4 => { user.unFollowUser(); repeated_process()}
    case 5 => { user.getUserFeeds(); repeated_process()}
    case 6 => { user.getMyFeeds(); repeated_process()}
    case 7 => { user.deleteUser(); repeated_process()}
    case 8=> {println("exited")}
    case _ => { println("select the correct option")   ; repeated_process()}            
}
}
repeated_process()
}
