AC32007-MrBlabby
================

Repository for AC32007 twitter clone assignment - MySql by Kari McMahon

Description of my assignment :

Account you can log into and you can see more to log into with in the conn.java class:

Username : Beyonce Password : blueivy22 who has a permission of 1 and is a user.

Username : tomcat  Password : mn2er3 who has a permission of 2 and is admin.

Username : kimk    Password : lmaolol who has a permission of 3 and is a developer.

Different permission means different abilitys to delete users. You can also register which will give you a permission of 1 which means they are a regular user.

Pages:

TweetTwoo/Tweet/doGet - Get's tweets of all the people the user is following

TweetTwoo/Tweet/doPost - Posts the logged in users tweets to the database

TweetTwoo/Tweet/id - Gets tweets with that id

TweetTwoo/Tweet/username -Gets tweets with that username

TweetTwoo/Tweet/doDelete - Delete's the requested users tweets

TweetTwoo/Tweet/json - Gets a json object of tweets (Must have gson jar in tomcat lib for this to work)


TweetTwoo/Login/doPost - Attempts to log user in

TweetTwoo/Register/doPost - Attempts to register user

TweetTwoo/Logout/doGet - Logs user out


TweetTwoo/Profile/doGet - Gets users profile for display and gets users own tweets for display

TweetTwoo/Profile/id - Gets users profile based on the users id

TweetTwoo/Profile/username - Gets users profile based on users username

TweetTwoo/Profile/json - Gets a json object of users own tweets (Must have gson jar in tomcat lib for this to work)

Depending on the users permission they will be shown a delete button which enables the user to delete the other users account.


TweetTwoo/EditProfile/doGet - Gets users profile information for edit textboxes

TweetTwoo/EditProfile/doPost - Attempts to update users profile

TweetTwoo/EditProfile/doDelete - Attempts to delete users account


TweetTwoo/Follower/doGet - Gets users followers

TweetTwoo/Follower/doPost - Enables user to follow a user

TweetTwoo/Follower/json - Gets a json object followers (Must have gson jar in tomcat lib for this to work)


TweetTwoo/Following/doGet - Gets who user is following

TweetTwoo/Following/doPost - Enables user to unfollow user

TweetTwoo/Following/json - Gets a json object followers (Must have gson jar in tomcat lib for this to work)


TweetTwoo/Suggestions/doGet -Gets suggestions for user based on who they are following


TweetTwoo/Search/doGet -Simply directs to search page

TweetTwoo/Search/doPost - Gets information based on who the user has searched for

In search if the user permission is 1 they are allowed to follow/unfollow users found in search

In search if the user permission is 2 they are allowed to follow/unfollow and delete users as they are admin.

In search if the user permission is 3 they are developers and are allowed to follow/unfollow users and delete users and admin accounts


TweetTwoo/User/doDelete - Deletes a user account based on information it recieves


