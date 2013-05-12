package com.octrois.koa.model.weapon;

import java.util.ArrayList;
import java.util.List;

import com.octrois.koa.model.bullet.Bullet;
import com.octrois.koa.model.role.Role;

public abstract class Weapon {

	protected Role owner;
	public List<Bullet> bullets = new ArrayList<Bullet>();

	public abstract void fire();

	public void setOwner(Role role) {
		owner = role;
	}
}
