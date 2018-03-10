package io.anuke.mindustry.world.blocks.types.production;

import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.BlockGroup;
import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Strings;

public class Pump extends LiquidBlock{
	protected final int timerPump = timers++;
	protected final int timerDump = timers++;
	
	protected float pumpAmount = 2f;

	public Pump(String name) {
		super(name);
		rotate = false;
		solid = true;
		layer = Layer.overlay;
		liquidFlowFactor = 3f;
		group = BlockGroup.liquids;
	}

	@Override
	public void setStats(){
		super.setStats();
		stats.add("liquidsecond", Strings.toFixed(60f*pumpAmount, 1));
	}
	
	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		return false;
	}
	
	@Override
	public void draw(Tile tile){
		Draw.rect(name(), tile.worldx(), tile.worldy());
		
		Draw.color(tile.entity.liquid.liquid.color);
		Draw.alpha(tile.entity.liquid.amount / liquidCapacity);
		Draw.rect("blank", tile.worldx(), tile.worldy(), 2, 2);
		Draw.color();
	}

	@Override
	public boolean isLayer(Tile tile) {
		return tile.floor().liquidDrop == null;
	}
	
	@Override
	public void drawLayer(Tile tile){
		Draw.colorl(0.85f + Mathf.absin(Timers.time(), 6f, 0.15f));
		Draw.rect("cross-"+size, tile.worldx(), tile.worldy());
		Draw.color();
	}
	
	@Override
	public void update(Tile tile){
		
		if(tile.floor().liquidDrop != null){
			float maxPump = Math.min(liquidCapacity - tile.entity.liquid.amount, pumpAmount * Timers.delta());
			tile.entity.liquid.liquid = tile.floor().liquidDrop;
			tile.entity.liquid.amount += maxPump;
		}
		
		if(tile.entity.timer.get(timerDump, 1)){
			tryDumpLiquid(tile);
		}
	}

}
