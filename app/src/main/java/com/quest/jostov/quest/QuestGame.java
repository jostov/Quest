package com.quest.jostov.quest;

import java.util.ArrayList;

/**
 * Created by jostov on 7/20/15.
 */
public class QuestGame
{
    private double goalLongitude;
    private double goalLatitude;
    private int goalSize = 10;
    private String gameId;
    private ArrayList<player> players = null;

    public QuestGame(String game_id)
    {
        gameId = game_id;
    }

    public void addPlayer(double lon, double lat, String id)
    {
        if (players == null)
        {
            players = new ArrayList<player>();
        }
        players.add(new player(lon, lat, id));
    }


    public void updatePlayer(double lon, double lat, String id)
    {
        for (player each : players){
            if (each.getPlayerID().equals(id)) {
                each.setLatitude(lat);
                each.setLongitude(lon);
                break;
            }
        }
    }
    private void checkVictor(){

    }


    public class player{
        public String getPlayerID()
        {
            return playerID;
        }

        public void setPlayerID(String playerID)
        {
            this.playerID = playerID;
        }

        public double getLongitude()
        {
            return longitude;
        }

        public void setLongitude(double longitude)
        {
            this.longitude = longitude;
        }

        public double getLatitude()
        {
            return latitude;
        }

        public void setLatitude(double latitude)
        {
            this.latitude = latitude;
        }

        private String playerID;
        private double longitude;
        private double latitude;

        public player(double lon, double lat, String id){
            playerID = id;
            longitude = lon;
            latitude = lat;
        }
    }
}
