package com.octrois.koa.model.direction;

public class Direction {
	public static final Direction NONE = new Direction(0, 0);
	public static final Direction NORTH = new Direction(0, -1);
	public static final Direction SOUTH = new Direction(0, 1);
	public static final Direction EAST = new Direction(1, 0);
	public static final Direction WEST = new Direction(-1, 0);
	public static final Direction NORTH_WEST = new Direction(-1, -1);
	public static final Direction NORTH_EAST = new Direction(1, -1);
	public static final Direction SOUTH_WEST = new Direction(-1, 1);
	public static final Direction SOUTH_EAST = new Direction(1, 1);

	private int speedX;
	private int speedY;

	public Direction(int sx, int sy) {
		speedX = sx;
		speedY = sy;
	}

	public int getDiffX() {
		return speedX;
	}

	public int getDiffY() {
		return speedY;
	}

}
