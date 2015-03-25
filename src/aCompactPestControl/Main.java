package aCompactPestControl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;

@ScriptManifest(author = "Atex", category = Category.MINIGAMES, description = "Gets you full void, the proper way.", name = "aCompactPestControl", servers = { "Ikov" }, version = 0.1)

public class Main extends Script implements Paintable {
	public long startTime = System.currentTimeMillis();
	private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();
	
	@Override
	public boolean onExecute() {
		startTime = System.currentTimeMillis();
		strategies.add(new Action());
		provide(strategies);
		return true;
	}
	
	@Override
	public void onFinish() {
		
	}
	
	public void paint(Graphics iFace) { 
    	iFace.setColor(Color.white);
    	iFace.setFont(new Font("Verdana", Font.ITALIC, 12));
    	iFace.drawString("Pest Control", 390, 67);
    	iFace.drawString(runTime(startTime), 390, 80);
    	iFace.drawString("Points: "+(Action.points), 390, 93);
    	iFace.drawString((new StringBuilder("")).append("Points /hr: ").append(pointRate(Action.points)).toString(), 390, 106);
    	iFace.drawString("Failed: "+(Action.fails), 390, 119);
    }
	public int pointRate(int points) {
         return (int)(((double)(points - 0) * 3600000D) / (double)(System.currentTimeMillis() - startTime));
    }
	public static String runTime(long start) {
		DecimalFormat df = new DecimalFormat("00");
		long currentTime = System.currentTimeMillis() - start;
		long hours = currentTime / (3600000);
		currentTime -= hours * (3600000);
		long minutes = currentTime / (60000);
		currentTime -= minutes * (60000);
		long seconds = currentTime / (1000);
		return df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds);
	}
}


