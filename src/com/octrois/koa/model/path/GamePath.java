package com.octrois.koa.model.path;

import java.util.ArrayList;
import java.util.List;

import com.octrois.koa.model.direction.Direction;

public class GamePath {

	private List<Direction> steps = new ArrayList<Direction>();

	private int current = 0;

	public void add(int count, int speedX, int speedY) {
		// TODO Not 1000 values,but 1 value for 1000 ticks.
		for (int i = 0; i < count; i++) {
			Direction dir = new Direction(speedX, speedY);
			steps.add(dir);
		}
	}

	public boolean hasNext() {
		return current < steps.size() - 1;
	}

	public Direction next() {
		return steps.get(current++);
	}

	public void clear() {
		steps.clear();
	}
}
