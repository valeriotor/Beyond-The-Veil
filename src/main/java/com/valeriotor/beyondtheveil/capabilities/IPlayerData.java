package com.valeriotor.beyondtheveil.capabilities;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IPlayerData {
	
	void addString(String string, boolean temporary);
	void addStringFromNBT(String string);
	void removeString(String string);
	
	
	
	void setInteger(String key, int value, boolean temporary);
	Integer incrementOrSetInteger(String key, int amount, int value, boolean temporary);
	void removeInteger(String key);
	
	void setLong(String key, long value);
	Long removeLong(String key);
	
	boolean getString(String string);
	Integer getInteger(String key);
	Integer getOrSetInteger(String key, int value, boolean temporary);
	Long getLong(String key);
	Long getOrSetLong(String key, long value);
	
	HashMap<String, Integer> getInts(boolean temporary);
	HashMap<String, Long> getLongs();
	Set<String> getStrings(boolean temporary);
	
	void addKeyedString(String key, String value);
	String getKeyedString(String key);
	String getOrSetKeyedString(String key, String value);
	boolean hasKeyedString(String key);
	HashMap<String, String> getKeyedStrings();
	
	/** To be used only client-side
	 * 
	 */
	void removeAllStrings();
	/** To be used only client-side
	 * 
	 */
	void removeAllInts();
	
}
