package aCompactPestControl;


import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.Walking;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.Player;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

public class Action implements Strategy {
	Tile lobby = new Tile(2660, 2639);
	Tile centerIsland = new Tile(2657, 2599);
	Tile outsideBoat = new Tile(2657,2639);
	Tile tPurple = new Tile(2629,2592);
	Tile tWhite = new Tile(2680, 2589);
	Tile tYellow = new Tile(2669,2571);
	Tile tPink = new Tile(2645,2570);
	
	boolean doneWhite = false;
	boolean doneYellow = false;
	boolean donePink = false;
	boolean donePurple = false;
	boolean onIsland = false;
	boolean onBoat = false;
	
	public static int points;
	public static int fails;
	
	@Override
	public boolean activate() {
		return !Inventory.isFull();
	}
	public void execute() {
		
		Npc pPurple = Npcs.getClosest(6142);
		Npc pWhite = Npcs.getClosest(6143);
		Npc pYellow = Npcs.getClosest(6144);
		Npc pPink = Npcs.getClosest(6145);
		SceneObject bridge = SceneObjects.getClosest(14315);
		Player me = Players.getMyPlayer();
		
		if(bridge != null && !(me.getLocation() == lobby) && !onBoat) {
			bridge.interact(1);
			onBoat = true;
			Time.sleep(2000);
		}
		if(outsideBoat.distanceTo() < 2 && onBoat && Game.getOpenBackDialogId() == -1) {
			reset();
			fails++;
			Time.sleep(2000);
		}
		if(Game.getOpenBackDialogId() == 4893) {
			reset();
			points += 10;
			Time.sleep(2000);
		}
		if(Game.getOpenBackDialogId() == 4900) {
			onBoat=false;
			onIsland=true;
			Walking.walkTo(centerIsland);
			Time.sleep(2000);
		}
		
		if(onIsland && pWhite == null && pYellow == null && pPink == null && pPurple == null) {
			if(!doneWhite && !nearPortal()) {
				Walking.walkTo(tWhite);
				Time.sleep(4000);
				if(tWhite.distanceTo()<5) {
					doneWhite=true;
				}
			}
			if(!doneYellow && !nearPortal()) {
				Walking.walkTo(tYellow);
				Time.sleep(4000);
				if(tYellow.distanceTo()<5) {
					doneYellow=true;
				}
			}
			if(!donePink && !nearPortal()) {
				Walking.walkTo(tPink);
				Time.sleep(4000);
				if(tPink.distanceTo()<5) {
					donePink=true;
				}
			}
			if(!donePurple && !nearPortal()) {
				Walking.walkTo(tPurple);
				Time.sleep(4000);
				if(tPurple.distanceTo()<5) {
					donePurple=true;
				}
			}
		}
		if(onIsland)
		{
			if(pWhite != null) {
				if(pWhite.distanceTo() < 15) {
					pWhite.interact(1);
					doneWhite=true;
					Time.sleep(1000);
					Time.sleep(new SleepCondition() {
						@Override
						public boolean isValid() {
							return Players.getMyPlayer().getAnimation() != -1 & !Players.getMyPlayer().isInCombat();
						}
					},2000);
				}
			}
			
			if(pYellow != null) {
				if(pYellow.distanceTo() < 15) {
					pYellow.interact(1);
					doneYellow=true;
					Time.sleep(1000);
					Time.sleep(new SleepCondition() {
						@Override
						public boolean isValid() {
							return Players.getMyPlayer().getAnimation() != -1 & !Players.getMyPlayer().isInCombat();
						}
					},2000);
				}
				
			}
			
			if(pPink != null) {
				pPink.interact(1);
				donePink = true;
				Time.sleep(1000);
				Time.sleep(new SleepCondition() {
					@Override
					public boolean isValid() {
						return Players.getMyPlayer().getAnimation() != -1 & !Players.getMyPlayer().isInCombat();
					}
				},2000);
			}
			
			if(pPurple != null) {
				pPurple.interact(1);
				donePurple = true;
				Time.sleep(1000);
				Time.sleep(new SleepCondition() {
					@Override
					public boolean isValid() {
						return Players.getMyPlayer().getAnimation() != -1 & !Players.getMyPlayer().isInCombat();
					}
				},2000);
			}
		}
		
	}
	public void reset() {
		doneWhite = false;
		doneYellow = false;
		donePink = false;
		donePurple = false;
		onIsland = false;
		onBoat = false;
	}
	public boolean nearPortal() {
		boolean out = false;
		if(Npcs.getClosest(6142) != null) {out=true;}
		if(Npcs.getClosest(6143) != null) {out=true;}
		if(Npcs.getClosest(6144) != null) {out=true;}
		if(Npcs.getClosest(6145) != null) {out=true;}
		return out;
	}
}
