package dev.brokenstudio.brokenapi.scoreboard;

import dev.brokenstudio.brokenapi.BrokenAPI;
import dev.brokenstudio.brokenapi.player.BrokenPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import dev.brokenstudio.pinklib.function.TriConsumer;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BrokenBoard {

    public interface Action extends TriConsumer<Player, BrokenPlayer, AtomicReference<String>> {
    }

    private final String displayname;
    private final ArrayList<Action> actions;
    private final ArrayList<String> teamEntries;
    private final ArrayList<Player> scoreboardActive;
    private final boolean update;
    private final boolean useBrokenPlayer;
    private final int updateInterval;

    public BrokenBoard(String displayname, boolean useBrokenPlayer, boolean update, int updateInterval) {
        this.displayname = displayname;
        this.update = update;
        this.useBrokenPlayer = useBrokenPlayer;
        this.updateInterval = updateInterval;
        scoreboardActive = new ArrayList<>();
        this.actions = new ArrayList<>();
        teamEntries = new ArrayList<>(List.of(ChatColor.DARK_GRAY + "" + ChatColor.WHITE,
                ChatColor.BLACK + "" + ChatColor.WHITE,
                ChatColor.BLUE + "" + ChatColor.WHITE,
                ChatColor.DARK_AQUA + "" + ChatColor.BLACK,
                ChatColor.YELLOW + "" + ChatColor.WHITE,
                ChatColor.WHITE + "" + ChatColor.WHITE,
                ChatColor.DARK_BLUE + "" + ChatColor.BLACK,
                ChatColor.GOLD + "" + ChatColor.WHITE,
                ChatColor.DARK_PURPLE + "" + ChatColor.WHITE,
                ChatColor.DARK_RED + "" + ChatColor.WHITE,
                ChatColor.DARK_GREEN + "" + ChatColor.BLACK,
                ChatColor.LIGHT_PURPLE + "" + ChatColor.WHITE,
                ChatColor.GRAY + "" + ChatColor.WHITE,
                ChatColor.DARK_PURPLE + "" + ChatColor.BLACK,
                ChatColor.DARK_PURPLE + "" + ChatColor.GOLD));
                if(update){

                }
    }

    public BrokenBoard(String displayname, boolean useBrokenPlayer){
        this(displayname, useBrokenPlayer, false, 1);
    }

    public void addEntry(String entry){
        actions.add((p, pp ,reference)->{
           reference.set(entry);
        });
    }

    public void addEmpty(){
        addEntry(" ");
    }

    public void addEntry(Action action){
        actions.add(action);
    }

    public void addLine(int length){
        actions.add((p,cp,s)->{
           String sx = "§8§m";
           for(int i = 0; i < length; i++){
               sx += " ";
            }
           s.set(sx);
        });
    }

    public void setBoard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.registerNewObjective("lobby", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(displayname);
        BrokenPlayer brokenPlayer = useBrokenPlayer ? BrokenAPI.api().getBrokenPlayerHandler().getBrokenPlayer(player.getUniqueId()) : null;
        int score = actions.size();
        for(int i = 0; i < actions.size(); i++){
            Team team = scoreboard.registerNewTeam("sidebar" + i);
            AtomicReference<String> reference = new AtomicReference<>();
            actions.get(i).accept(player, brokenPlayer, reference);
            team.setPrefix(reference.get());
            team.addEntry(teamEntries.get(i));
            objective.getScore(teamEntries.get(i)).setScore(score - i);

        }
        player.setScoreboard(scoreboard);
    }

    public void update(){
        scoreboardActive.forEach(cr -> {
            BrokenPlayer brokenPlayer = useBrokenPlayer ? BrokenAPI.api().getBrokenPlayerHandler().getBrokenPlayer(cr.getUniqueId()) : null;
            Scoreboard scoreboard = cr.getScoreboard();
            for(int i = 0; i < actions.size(); i++){
                AtomicReference<String> reference = new AtomicReference<>();
                actions.get(i).accept(cr, brokenPlayer, reference);
                scoreboard.getTeam("sidebar" + i).setPrefix(reference.get());
            }
        });
    }
    
    public void update(Player player){
        BrokenPlayer brokenPlayer = useBrokenPlayer ? BrokenAPI.api().getBrokenPlayerHandler().getBrokenPlayer(player.getUniqueId()) : null;
        Scoreboard scoreboard = player.getScoreboard();
        for(int i = 0; i < actions.size(); i++){
            AtomicReference<String> reference = new AtomicReference<>();
            actions.get(i).accept(player, brokenPlayer, reference);
            scoreboard.getTeam("sidebar" + i).setPrefix(reference.get());
        }
    }

}
