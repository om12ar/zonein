package com.swe.zonein.zonein.Models;

import com.swe.zonein.zonein.Controllers.MainController;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Notification Model creat an instaneous 
 * of Notification and add to it the information
 * needed for the notification be saved and sent.
 * @author Menna
 * @since 24-12-2015
 *
 */
public class NotificationModel {

    int ID;
    String notification;
    int contentID;
    int usrID;
    int postID;

    /**
     * Construction used to construct an instanteous of object with data.
     * @param notification . string contain the text field of notification.
     * @param ID . the sending ID set the user ID.
     * @param contentID .unique integer of the notification .
     * @param userID . unique integer of user that notificatin sent to.
     */
    NotificationModel(String notification,int ID,int contentID, int userID )
    {
        this.notification=notification;
        this.ID=ID;
        this.contentID=contentID;
        this.usrID = userID;
    }

    /**
     * default construction.
     */
    public NotificationModel(){

    }


    /**
     * Copy constructor that copy data of other notification in
     * the instantous value.
     */
    public NotificationModel(NotificationModel other)
    {
        this.ID=other.ID;
        this.ID=other.ID;
        this.contentID=other.contentID;
    }

    public NotificationModel(JSONObject jsonObject) {

        try {
            usrID = jsonObject.getInt("FromID");
            contentID = jsonObject.getInt("notfid");
            ID = MainController.user.getID();
            notification = jsonObject.getString("txt");
            postID = jsonObject.getInt("postID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public NotificationModel(String text) {
        this.notification = text;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    /**
     * Getter int that return an unique positive value that represent the
     * the data hold .
     * @return ID. a positive unique number.
     */
    public int getID()
    {
        return ID;
    }

    /**
     * set ID
     * @param ID . a positive unique integer that represent the data.
     */
    public void setID(int ID)
    {
        this.ID = ID;
    }

    /**
     * Return a string contain the text in the String.
     * @return notification. String contain the data in notification.
     */
    public String getNotification()
    {
        return notification;
    }

    /**
     * Set the text of notification to the notification instaneous.
     * @param notification . String have the data of notification.
     */
    public void setNotification(String notification)
    {
        this.notification = notification;
    }

    /**
     * Return a positive integer a unique represent the user id .
     * @return userID . unique positive value.
     */

    public int getUserID()
    {
        return contentID;
    }

    /**
     * set userID to the the instaneous . unique int used to represent data.
     * @param UserID . unique postive number.
     */
    public void setUserID(int UserID)
    {
        usrID = UserID;
    }

    /**
     * Return ID of the content.
     * @return contentID. positive integer represent the ID. 
     */
    public int getContentID() {
        return contentID;
    }

    /**
     * Set integer to the content of the notification.
     * @param contentID . a unique positive integer.
     */
    public void setContentID(int contentID) {
        this.contentID = contentID;
    }


}
