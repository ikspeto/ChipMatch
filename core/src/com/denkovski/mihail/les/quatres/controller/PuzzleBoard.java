package com.denkovski.mihail.les.quatres.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PuzzleBoard {
	
	private Texture board;
	
	public void show() {
		board = new Texture(Gdx.files.internal("images/puzzle/background/board.png"));
	}

}
