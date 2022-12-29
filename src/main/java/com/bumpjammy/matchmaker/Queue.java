package com.bumpjammy.matchmaker;

import java.util.HashMap;

import org.bukkit.entity.Player;

public abstract class Queue {
    private HashMap<Player, Integer> players;
    private String name;
    private int eloDiff = -1;
    private int maxPlayers = -1;

    public Queue(String name, int eloDiff, int maxPlayers) {
        this.players = new HashMap<Player, Integer>();
        this.name = name;
        this.eloDiff = eloDiff;
        this.maxPlayers = maxPlayers;
        asyncStartMatchCheck();
    }

    public Queue(String name) { 
        this(name, -1, -1);
    }

    public Queue(String name, int eloDiff) {
        this(name, eloDiff, -1);
    }

    public void addPlayer(Player player, int elo) {
        if(maxPlayers != -1 && players.size() >= maxPlayers) {
            return;
        }
        players.put(player, elo);
    }

    public void addPlayer(Player player) {
        addPlayer(player, -1);
    }
    
    public void removePlayer(Player player) {
        players.remove(player);
    }
    
    public boolean isPlayerInQueue(Player player) {
        return players.containsKey(player);
    }
    
    public int getSize() {
        return players.size();
    }

    public String getName() {
        return name;
    }

    public int getEloDiff() {
        return eloDiff;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setEloDiff(int eloDiff) {
        this.eloDiff = eloDiff;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean onMatchFound(Player player1, Player player2);

    private void checkForMatches() {
        for(Player player1 : players.keySet()) {
            for(Player player2 : players.keySet()) {
                if(player1 == player2) {
                    continue;
                }
                if(eloDiff == -1 || Math.abs(players.get(player1) - players.get(player2)) <= eloDiff) {
                    if(onMatchFound(player1, player2)) {
                        players.remove(player1);
                        players.remove(player2);
                    }
                }
            }
        }
    }

    public void asyncStartMatchCheck() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    checkForMatches();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
