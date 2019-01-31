package com.valeriotor.BTV.capabilities;

import java.util.HashMap;
import java.util.List;

public interface IPlayerData {
	
	
	
	
	/** Sets the entity the player is talking to, for easy client side access
	 *  @param id 0 for null, 1 for Dweller Bartender, 2 for Dweller Carpenter, 3 for Dweller Lighthouse Keeper
	 */
	public void setDialogueType(int id);
	
	/** Gets the entity the player is talking to, for easy client side access
	 *  @param id 0 for null, 1 for Dweller Bartender, 2 for Dweller Carpenter, 3 for Dweller Lighthouse Keeper
	 */
	public int getDialogueType();
	
	public void addString(String string, boolean temporary);
	public void removeString(String string);
	public void setInteger(String key, int value, boolean temporary);
	public void removeInteger(String key);

	public boolean getString(String string);
	public int getInteger(String key);
	public int getOrSetInteger(String key, int value, boolean temporary);
	
	public HashMap<String, Integer> getInts(boolean temporary);
	public List<String> getStrings(boolean temporary);
	
}
