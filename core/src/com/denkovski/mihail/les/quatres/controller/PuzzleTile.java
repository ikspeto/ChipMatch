package com.denkovski.mihail.les.quatres.controller;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.math.Vector2;
import com.denkovski.mihail.les.quatres.utility.Position;

public class PuzzleTile {
	
	private Position position;
	
	public float animTime, animTotalTime;
	public float animX, animY, animSize;
	public boolean bAnimate;
	public int puzzleNum, aOrigin, aDest, destiny;
	public Queue<Integer> qAnimation = new LinkedList<Integer>();
	public Queue<Boolean> qAxis = new LinkedList<Boolean>();
	
	public PuzzleTile() {
		bAnimate = false;
		destiny = 0;
		puzzleNum = 0;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
