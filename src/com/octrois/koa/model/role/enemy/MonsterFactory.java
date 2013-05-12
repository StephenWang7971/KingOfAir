package com.octrois.koa.model.role.enemy;

public class MonsterFactory {

	// TODO rename later...
	public static Enemy getMonster(String color) {
		if (color.equalsIgnoreCase("Gray")) {
			return new Asteroid();
		} else if (color.equalsIgnoreCase("Green")) {
			return new FasterMisile();
		} else if (color.equalsIgnoreCase("Blue")) {
			return new WeirdBird();
		} else if (color.equalsIgnoreCase("Red")) {
			return new MediumTank();
		} else if (color.equalsIgnoreCase("Cyan")) {
			return new Helicopter();
		} else if (color.equalsIgnoreCase("Orange")) {
			return new OldAirCraft();
		} else if (color.equalsIgnoreCase("Purple")) {
			return new PurpleMonster();
		}
		return new Asteroid();
	}

}
