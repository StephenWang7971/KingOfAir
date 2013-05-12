package com.octrois.koa.model.box;

public class BoxFactory {

	public static Box createBox(String type) {
		if (type.equalsIgnoreCase("Silver")) {
			return new SilverBox();
		} else if (type.equalsIgnoreCase("Gold")) {
			return new GoldBox();
		} else if (type.equalsIgnoreCase("Bomb")) {
			return new BombBox();
		} else if (type.equalsIgnoreCase("Health")) {
			return new HealthBox();
		}
		return new EmptyBox();
	}
}
