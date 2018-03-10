package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.Color;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.generation.Generator;

public class PowerLaser extends Generator{
	public Color color = Color.valueOf("e54135");

	public PowerLaser(String name) {
		super(name);
		rotate = true;
		solid = true;
		explosive = false;
		laserDirections = 1;
		health = 50;
	}

	@Override
	public void update(Tile tile){
		distributeLaserPower(tile);
	}
	
	@Override
	public boolean acceptPower(Tile tile, Tile from, float amount){
		PowerEntity entity = tile.entity();
		
		return entity.power.amount <= powerCapacity;
	}
}
