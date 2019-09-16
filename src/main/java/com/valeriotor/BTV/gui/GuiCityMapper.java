package com.valeriotor.BTV.gui;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;
import com.valeriotor.BTV.lib.References;
import com.valeriotor.BTV.network.BTVPacketHandler;
import com.valeriotor.BTV.network.MessageCityMapper;
import com.valeriotor.BTV.shoggoth.FlatBuilding;
import com.valeriotor.BTV.shoggoth.BuildingRegistry;
import com.valeriotor.BTV.shoggoth.BuildingTemplate;
import com.valeriotor.BTV.shoggoth.FlatLongBuilding;
import com.valeriotor.BTV.tileEntities.TileCityMapper;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiCityMapper extends GuiScreen{
	
	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/gui/city_mapper.png");
	private final BlockPos pos;
	private TileCityMapper te;
	private final List<BuildingTemplate> availableBuildings;
	private int scrollOffset = 0;
	private FlatBuilding selectedBuilding = null;
	private int selectedIndex = -1;
	private final DynamicTexture map;
	private boolean changes = false;
	private Point placedEnd = null;
	private int mapTopLeftX = 0;
	private int mapTopLeftY = 0;
	
	public GuiCityMapper(BlockPos pos) {
		this.mc = Minecraft.getMinecraft();
		this.pos = pos;
		TileEntity te = Minecraft.getMinecraft().player.world.getTileEntity(pos);
		if(te instanceof TileCityMapper) {
			this.te = (TileCityMapper)te; 
		}
		List<BuildingTemplate> temp = new ArrayList<>();
		for(BuildingTemplate template : BuildingRegistry.templates) {
			if(template == null) break;
			if(template.isDefault() || template.playerKnowsBuilding(Minecraft.getMinecraft().player))
				temp.add(template);
		}
		availableBuildings = ImmutableList.copyOf(temp);
		BufferedImage map = new BufferedImage(201, 201, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < 201; x++) {
			for(int y = 0; y < 201; y++) {
				int rgb = MapColor.COLORS[this.te.colors[x][y]].colorValue;
				int height = this.te.heights[x][y] + 128;
				int offset = inBetween(-40, 40, (pos.getY() - height) * 4);
				int r = inBetween(0, 255, ((rgb >> 16) & 255) - offset);
				int g = inBetween(0, 255, ((rgb >> 8) & 255) - offset);
				int b = inBetween(0, 255, ((rgb) & 255) - offset);
				map.setRGB(x, y, 0xFF000000 | (r << 16) | (g << 8) | b);
			}
		}
		this.map = new DynamicTexture(map);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.mapTopLeftX = this.width / 2 - 115;
		this.mapTopLeftY = this.height / 2 - 100;
		this.buttonList.add(new GuiButton(0, this.mapTopLeftX - 107, this.height/2 + 99 - 18, 100, 20, I18n.format("gui.city_mapper.create")));
		this.buttonList.add(new GuiButton(1, this.mapTopLeftX - 107, this.height/2 + 99 - 37, 100, 20, I18n.format("gui.city_mapper.save")));
		this.buttonList.add(new GuiButton(2, this.mapTopLeftX - 107, this.height/2 + 99 - 56, 100, 20, I18n.format("gui.city_mapper.reloadmap")));
		this.buttonList.get(1).enabled = changes;
		this.buttonList.get(0).enabled = !changes;
	}
	
	@Override
	public void updateScreen() {
		this.te.viewingPlayer = null;
		this.buttonList.get(1).enabled = changes;
		this.buttonList.get(0).enabled = !changes;
		if(!(this.mc.player.world.getTileEntity(pos) instanceof TileCityMapper)) this.mc.displayGuiScreen(null);
		super.updateScreen();
	}
	
	@Override
	public void onGuiClosed() {
		BTVPacketHandler.INSTANCE.sendToServer(new MessageCityMapper(MessageCityMapper.RESET_PLAYER, this.pos));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(this.mc.gameSettings.guiScale == 1 || this.mc.gameSettings.guiScale == 2) {
			GuiHelper.drawCenteredParagraph(this, mc.fontRenderer, I18n.format("gui.city_mapper.incsize"), 90, 10, 10);
		}
		int mapX = this.getMapX(mouseX), mapY = this.getMapY(mouseY);
		if(this.isPointInsideMap(mouseX, mouseY)) {
			drawCenteredString(mc.fontRenderer, String.format("x: %d   z: %d", mapX + this.pos.getX() - 100, mapY + this.pos.getZ() - 100), this.mapTopLeftX + 100, this.height / 2 - 115, 0xFFFFFFFF);
		}
		int numBuildings = this.availableBuildings.size();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawModalRectWithCustomSizedTexture(this.width / 2 - 229, this.height/2 - 107, 0, 0, 466, 215, 512, 512);
		int scrollBarOffset = this.mapTopLeftY;
		if(numBuildings > 2) {
			scrollBarOffset += 182 * this.scrollOffset / ((numBuildings-1) / 2);
		}
		drawModalRectWithCustomSizedTexture(this.width / 2 + 93 + 129, scrollBarOffset, 467, 0, 9, 19, 512, 512);
		if(this.selectedBuilding != null) {
			int xOffset = this.selectedIndex == -1 ? 0 : this.pos.getX() - 100;
			int yOffset = this.selectedIndex == -1 ? 0 : this.pos.getZ() - 100;
			drawString(Minecraft.getMinecraft().fontRenderer, this.selectedBuilding.getLocalizedName(), this.width/2 - 220, this.height/2 - 98, 0xFFFFFFFF);
			drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildingx", xOffset + this.selectedBuilding.centerX), this.width/2 - 220, this.height/2 - 83, 0xFFFFFFFF);
			drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildingy", yOffset + this.selectedBuilding.centerY), this.width/2 - 220, this.height/2 - 68, 0xFFFFFFFF);
			if(!(this.selectedBuilding instanceof FlatLongBuilding))
				drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildingrot", I18n.format(String.format("gui.city_mapper.rot%d", this.selectedBuilding.rotation))), this.width/2 - 220, this.height/2 - 53, 0xFFFFFFFF);
			else
				drawString(Minecraft.getMinecraft().fontRenderer, I18n.format("gui.city_mapper.buildinglength", ((FlatLongBuilding)selectedBuilding).getLength()), this.width/2 - 220, this.height/2 - 53, 0xFFFFFFFF);
		}
		int i = this.getHoveredMenuBuilding(mouseX, mouseY);
		if(i != -1) {
			int left = this.width / 2 + 93 + 64 * (i%2);
			int top = this.height / 2 - 100 + 64 * (i/2 - this.scrollOffset);
			drawRect(left, top, left + 64, top + 64, 0x55FFFFFF);
		}
		GlStateManager.bindTexture(map.getGlTextureId());
		drawModalRectWithCustomSizedTexture(this.mapTopLeftX, this.mapTopLeftY, 0, 0, 201, 201, 201, 201);
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1);
		for(i = this.scrollOffset*2; i < numBuildings && i < this.scrollOffset*2 + 6; i++) {
			availableBuildings.get(i).drawTexture(this, this.width / 2 + 93 + 64 * (i%2), this.mapTopLeftY + 64 * (i/2 - this.scrollOffset));
		}
		
		GlStateManager.color(1, 1, 1, 1);
		for(FlatBuilding b : te.buildings) b.render(this);
		if(this.selectedBuilding != null) {
			if(!(this.selectedBuilding instanceof FlatLongBuilding)) {
				int hwidth = selectedBuilding.getWidth()/2, hheight = selectedBuilding.getHeight()/2;
				drawHover(mouseX, mouseY, hwidth, hheight, !this.intersects(mapX, mapY) && this.isSelectedInsideMap(mouseX, mouseY));
			} else {
				FlatLongBuilding building = (FlatLongBuilding) this.selectedBuilding;
				if(this.placedEnd == null) {
					int hwidth = building.getDefaultWidth()/2, hheight = building.getDefaultHeight()/2;
					drawHover(mouseX, mouseY, hwidth, hheight, !this.intersects(mapX, mapY) && this.isSelectedInsideMap(mouseX, mouseY));
				} else {
					int screenPlacedX = this.getScreenX(placedEnd.x), screenPlacedY = this.getScreenY(placedEnd.y);
					boolean horizontal = isLongBuildingHorizontal(mouseX, mouseY);
					int width = horizontal ? Math.abs(this.getMapX(mouseX) - placedEnd.x) : building.getDefaultWidth();
					int height = horizontal ? building.getDefaultHeight() :  Math.abs(this.getMapY(mouseY) - placedEnd.y);
					int x = horizontal ? (placedEnd.x + this.getMapX(mouseX))/2 : placedEnd.x; // Coords of the building's center
					int y = horizontal ? placedEnd.y : (placedEnd.y + this.getMapY(mouseY))/2;
					int left = screenPlacedX, top = screenPlacedY, right = horizontal ? mouseX : screenPlacedX, bottom =  horizontal ? screenPlacedY : mouseY;
					if(horizontal) {
						top -= building.getDefaultHeight()/2;
						bottom += building.getDefaultHeight()/2;
					}
					else {
						left -= building.getDefaultWidth()/2;
						right += building.getDefaultWidth()/2;
					}
					drawRect(left, top, right, bottom, this.intersectsLong(this.getMapY(top), this.getMapX(left), this.getMapY(bottom), this.getMapX(right)) || !this.isSelectedInsideMap(mouseX, mouseY) ? 0x99FF0000 : 0x9900FF00);
				}
			}
		} else {
			int b = this.getHoveredMapBuilding(mapX, mapY);
			if(b != -1) te.buildings.get(b).highlight(this);
		}
		GlStateManager.disableBlend();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void drawHover(int centerX, int centerY, int hwidth, int hheight, boolean green) {
		drawRect(centerX - hwidth, centerY - hheight, centerX + hwidth, centerY + hheight, !green ? 0x99FF0000 : 0x9900FF00);
	}
	
	private int inBetween(int lowerEnd, int higherEnd, int value) {
		return Math.min(higherEnd, Math.max(lowerEnd, value));
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == 18) {
			mc.displayGuiScreen(null);
			return;
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		int i = Integer.signum(Mouse.getEventDWheel());
		if(i != 0) {
			if(this.selectedBuilding == null) {
				this.scrollOffset = inBetween(0, (availableBuildings.size() - 1)/2, this.scrollOffset - i);
			}
			else if(!(this.selectedBuilding instanceof FlatLongBuilding)){
				this.selectedBuilding.rotation = (this.selectedBuilding.rotation + i + 4)%4;
				if(this.selectedIndex != -1) this.changes = true;
			}
		}	
		super.handleMouseInput();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int mapX = this.getMapX(mouseX), mapY = this.getMapY(mouseY);
		if(this.selectedBuilding == null) {
			if(mouseButton == 0) {
				int i = this.getHoveredMenuBuilding(mouseX, mouseY);
				if(i != -1) {
					this.selectedBuilding = FlatBuilding.getFromTemplate(this.availableBuildings.get(i));
				} else {
					int j = this.getHoveredMapBuilding(mapX, mapY);
					if(j != -1) {
						this.selectedBuilding = te.buildings.get(j);
						this.selectedIndex = j;
					}
				}
			}
		} else {
			if(mouseButton == 1) {
				this.selectedBuilding = null;
				this.selectedIndex = -1;
				this.placedEnd = null;			
			}
			else if(isSelectedInsideMap(mouseX, mouseY)) {
				if(!(this.selectedBuilding instanceof FlatLongBuilding)) {
					if(this.intersects(mapX, mapY)) return;
					this.selectedBuilding.setCenter(mapX, mapY);
					if(this.selectedIndex == -1) te.buildings.add(this.selectedBuilding);
					this.selectedBuilding = null;
					this.selectedIndex = -1;
					this.changes = true;
				} else {
					if(this.placedEnd == null) {
						if(!this.intersects(mapX, mapY))
							this.placedEnd = new Point(mapX, mapY);
					}
					else {
						if(mapX == placedEnd.x && mapY == placedEnd.y) return;
						boolean horizontal = this.isLongBuildingHorizontal(mouseX, mouseY);
						FlatLongBuilding building = (FlatLongBuilding) this.selectedBuilding;
						int mleft = placedEnd.x, mtop = placedEnd.y, mright = horizontal ? mapX : placedEnd.x, mbottom =  horizontal ? placedEnd.y : mapY; 
						if(horizontal) {
							mtop -= building.getDefaultHeight()/2;
							mbottom += building.getDefaultHeight()/2;
						}
						else {
							mleft -= building.getDefaultWidth()/2;
							mright += building.getDefaultWidth()/2;
						}
						if(this.intersectsLong(mtop, mleft, mbottom, mright)) return;
						building.vertex1 = new Point(this.placedEnd);
						int centerX = horizontal ? (placedEnd.x + mapX)/2 : placedEnd.x; // Coords of the building's center
						int centerY = horizontal ? placedEnd.y : (placedEnd.y + mapY)/2;
						int x = horizontal ? mapX : this.placedEnd.x;
						int y = horizontal ? this.placedEnd.y : mapY;
						building.vertex2 = new Point(x, y);
						building.rotation = horizontal ? 1 : 0;
						building.setCenter(centerX, centerY);
						this.placedEnd = null;
						if(this.selectedIndex == -1) te.buildings.add(this.selectedBuilding);
						this.selectedBuilding = null;
						this.selectedIndex = -1;
						this.changes = true;
					}
				}
			} else {
				if(this.selectedIndex == -1) {
					this.selectedBuilding = null;
					this.placedEnd = null;
				}
				else {
					this.te.buildings.remove(selectedIndex);
					this.selectedBuilding = null;
					this.selectedIndex = -1;
					this.changes = true;
				}
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageCityMapper(MessageCityMapper.CREATE_MAP, pos));
		} else if(button.id == 1) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageCityMapper(MessageCityMapper.UPDATE_BUILDINGS, pos, te.writeBuildingsToNBT(new NBTTagCompound())));
			this.changes = false;
		} else if(button.id == 2) {
			BTVPacketHandler.INSTANCE.sendToServer(new MessageCityMapper(MessageCityMapper.RESET_MAP, pos));		
			this.mc.displayGuiScreen(null);
		}
	}
	
	private int getHoveredMenuBuilding(int mx, int my) {
		int cx = width/2, cy = height/2;
		if(mx > cx + 93 && mx < cx + 213 && my > cy - 100 && my < cy + 92) {
			int ix = (mx - cx - 93)/64;
			int iy = (my - cy + 100)/64;
			int i = (iy << 1) | ix;
			if(i + this.scrollOffset*2 < this.availableBuildings.size()) return i + this.scrollOffset * 2;
		}
		return -1;
	}
	
	private int getHoveredMapBuilding(int mapX, int mapY) {
		for(int i = 0; i < te.buildings.size(); i++) {
			if(te.buildings.get(i).containsPoint(mapX, mapY)) return i;
		}
		return -1;
	}
	
	private boolean isLongBuildingHorizontal(int mx, int my) {
		if(this.placedEnd == null) return false;
		int x = this.getMapX(mx) - this.placedEnd.x;
		int y = this.getMapY(my) - this.placedEnd.y;
		if(x == 0) return false;
		int result = y/x;
		if(result >= 1 || result <= -1) return false;
		return true;
	}
	
	/** Map coords required
	 */
	private boolean intersects(int mapX, int mapY) {
		for(FlatBuilding b : te.buildings) {
			if(b.intersects(selectedBuilding, mapX, mapY)) {
				return true;
			}
		}
		return false;
	}
	
	/** Map coords required
	 */
	private boolean intersectsLong(int top, int left, int bottom, int right) {
		for(FlatBuilding b : te.buildings) {
			if(b.intersects(selectedBuilding, top, left, bottom, right)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSelectedInsideMap(int mouseX, int mouseY) {
		if(this.selectedBuilding == null) return false;
		int cx = this.width/2, cy = this.height/2;
		return mouseX > cx - 115 + selectedBuilding.getWidth()/2 && mouseX < cx + 87 - selectedBuilding.getWidth()/2 &&
				mouseY > cy - 100 + selectedBuilding.getHeight()/2 && mouseY < cy + 100 - selectedBuilding.getHeight()/2;
	}
	
	private boolean isPointInsideMap(int x, int y) {
		return x >= this.mapTopLeftX && x <= this.mapTopLeftX + 200 && y >= this.mapTopLeftY && y <= this.mapTopLeftY + 200;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public int getMapX(int mouseX) {
		return mouseX - this.mapTopLeftX;
	}
	
	public int getMapY(int mouseY) {
		return mouseY - this.mapTopLeftY;
	}
	
	public int getScreenX(int x) {
		return x + this.mapTopLeftX;
	}
	
	public int getScreenY(int y) {
		return y + this.mapTopLeftY;
	}
	
	public void translate() {
		GlStateManager.translate(this.mapTopLeftX, this.mapTopLeftY, 0);
	}
	
	@Override public void drawVerticalLine(int x, int startY, int endY, int color) {super.drawVerticalLine(x, startY, endY, color);}
	@Override public void drawHorizontalLine(int startX, int endX, int y, int color) {super.drawHorizontalLine(startX, endX, y, color);}
	
}
