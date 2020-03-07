package com.valeriotor.BTV.capabilities;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IPlayerData {
	
	public void addString(String string, boolean temporary);
	public void removeString(String string);
	
	
	
	public void setInteger(String key, int value, boolean temporary);
	public Integer incrementOrSetInteger(String key, int amount, int value, boolean temporary);
	public void removeInteger(String key);
	
	public void setLong(String key, long value);
	public Long removeLong(String key);
	
	public boolean getString(String string);
	public Integer getInteger(String key);
	public Integer getOrSetInteger(String key, int value, boolean temporary);
	public Long getLong(String key);
	public Long getOrSetLong(String key, long value);
	
	public HashMap<String, Integer> getInts(boolean temporary);
	public HashMap<String, Long> getLongs();
	public Set<String> getStrings(boolean temporary);
	
	public void addKeyedString(String key, String value);
	public String getKeyedString(String key);
	public String getOrSetKeyedString(String key, String value);
	public boolean hasKeyedString(String key);
	public HashMap<String, String> getKeyedStrings();
	
	/** To be used only client-side
	 * 
	 */
	public void removeAllStrings();
	/** To be used only client-side
	 * 
	 */
	public void removeAllInts();
	
}
