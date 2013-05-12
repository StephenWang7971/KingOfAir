package com.octrois.koa.model.magic;

import com.octrois.koa.model.Game;
import com.octrois.koa.model.direction.Direction;
import com.octrois.koa.model.event.GameEvent;

public class AtomBomb extends MagicWeapon {

	int count = 0;
	String[] pics = new String[] { "atom_bomb_1", "atom_bomb_2", "atom_bomb_3",
			"atom_bomb_4", "atom_bomb_5", "atom_bomb_6", "atom_bomb_7",
			"atom_bomb_8", "atom_bomb_9", "atom_bomb_10" };

	public AtomBomb(int x, int y) {
		picKey = "atom_bomb"; // TODO animation for explosion
		width = 10;
		height = 10;
		this.x = x;
		this.y = y;
		power = 4000;
		dir = Direction.NONE;
		count = 0;
		friend = true;
	}

	@Override
	public void move() {
		// Enlarge size;
		count++;
		Game game = Game.getInstance();
		if (count == 10) {
			game.sendEvent(new GameEvent(GameEvent.REMOVE_ATOM, this));
			count = 0;
			return;
		}

		x -= 20;
		y -= 20;
		width += 40;
		height += 40;
		picKey = pics[count];

		detectAttack();
	}
}