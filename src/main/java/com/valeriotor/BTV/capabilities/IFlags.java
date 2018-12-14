package com.valeriotor.BTV.capabilities;

public interface IFlags {
	
	public void setTimesDreamt(int times);
	
	public int getTimesDreamt();
	
	/** Sets the entity the player is talking to, for easy client side access
	 *  @param id 0 for null, 1 for Dweller Bartender, 2 for Dweller Carpenter, 3 for Dweller Lighthouse Keeper
	 */
	public void setDialogueType(int id);
	
	public int getDialogueType();
	
	
}
