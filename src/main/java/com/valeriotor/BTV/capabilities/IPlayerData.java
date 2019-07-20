package com.valeriotor.BTV.capabilities;

import java.util.HashMap;
import java.util.List;

public interface IPlayerData {
	
	public void addString(String string, boolean temporary);
	public void removeString(String string);
	public void removeAllStrings();
	public void setInteger(String key, int value, boolean temporary);
	public void incrementOrSetInteger(String key, int amount, int value, boolean temporary);
	public void removeInteger(String key);

	public boolean getString(String string);
	public Integer getInteger(String key);
	public Integer getOrSetInteger(String key, int value, boolean temporary);
	
	public HashMap<String, Integer> getInts(boolean temporary);
	public List<String> getStrings(boolean temporary);
	
}
