package com.denkovski.mihail.les.quatres.controller;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Slot {
	
	public Rectangle bounds;
	public boolean isEmpty;
	public PuzzleTile puzzleTile;
	public int i, j;
	
	public Slot() {
		isEmpty = true;
		this.bounds = new Rectangle();
		this.puzzleTile = new PuzzleTile();
		i = 0;
		j = 0;
	}
	
	public boolean isInside(Vector2 pos) {
		if(pos.x >= bounds.x && pos.x <= bounds.x + bounds.width && pos.y >= bounds.y && pos.y <= bounds.y + bounds.height)
			return true;
		return false;
	}
	
}
