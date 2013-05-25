package com.amos.project4.test;

import java.util.List;

import twitter4j.Friendship;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class testTwitter {
	/**
	* Usage: java twitter4j.examples.user.LookupFriendships [screen name[,screen name..]]
	*
	* @param args message
	*/
//	    public static void main(String[] args) {
//	        if (args.length < 1) {
//	            System.out.println(
//	                    "Usage: java twitter4j.examples.user.LookupFriendships [screen name[,screen name..]]");
//	            System.exit(-1);
//	        }
//	        try {
//	            Twitter twitter = new TwitterFactory().getInstance();
//	            ResponseList<Friendship> friendships = twitter.lookupFriendships(args[0].split(","));
//	            for (Friendship friendship : friendships) {
//	                System.out.println("@" + friendship.getScreenName()
//	                        + " following: " + friendship.isFollowing()
//	                        + " followed_by: " + friendship.isFollowedBy());
//	            }
//	            System.out.println("Successfully looked up friendships [" + args[0] + "].");
//	            System.exit(0);
//	        } catch (TwitterException te) {
//	            te.printStackTrace();
//	            System.out.println("Failed to lookup friendships: " + te.getMessage());
//	            System.exit(-1);
//	        }
//	    }
	
	public static void main(String[] args) {
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            List<Status> statuses;
            String user;
            if (args.length == 1) {
                user = args[0];
                statuses = twitter.getUserTimeline(user);
            } else {
                user = twitter.verifyCredentials().getScreenName();
                statuses = twitter.getUserTimeline();
            }
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

}
