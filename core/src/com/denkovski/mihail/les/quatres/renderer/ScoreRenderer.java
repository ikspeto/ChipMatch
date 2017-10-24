package com.denkovski.mihail.les.quatres.renderer;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.utility.Animation;

public class ScoreRenderer {

	private ChipMatch game;
	
	private SpriteBatch spriteBatch;
	private float constAnimTime;
	
	private Queue<Integer> qScore = new LinkedList<Integer>();
	private Queue<Float> qAnimTime = new LinkedList<Float>();
	private Queue<Float> qX = new LinkedList<Float>(), qY = new LinkedList<Float>();
	
	// Animations
	private Vector2 vAnim;
	private int tScore;
	private float tX, tY;
	private float tAnimTime;
	private Color color;
	private float oldAlpha;
	private final float displacement = 30;
	
	
	public ScoreRenderer(ChipMatch game) {
		this.game = game;
						
		// Sprites 
		spriteBatch = new SpriteBatch();

		// Animations
		constAnimTime = 1.0f;
		
		color = spriteBatch.getColor();
		oldAlpha = color.a;
		
	}
	
	public void render(float delta) {
		spriteBatch.begin();
		
		animate(delta);
		
		spriteBatch.end();
	}
	
	public void addAnimation(int score, float x, float y) {
		qScore.add(score);
		qX.add(x);
		qY.add(y);
		qAnimTime.add(0.0f);
	}
	
	private void animate(float delta) {
		int n = qScore.size();
		for(int i=0;i<n;i++) {
			tScore = qScore.poll();
			tX = qX.poll();
			tY = qY.poll();
			tAnimTime = qAnimTime.poll();
			vAnim = new Vector2(tX, tY);
			if(tAnimTime < constAnimTime) {
				vAnim = new Vector2(tX, Animation.easeOutQuad(tAnimTime, tY, displacement, constAnimTime));
				color.a = oldAlpha * Animation.easeOutQuad(tAnimTime, oldAlpha, -oldAlpha, constAnimTime);
				spriteBatch.setColor(color);
				spriteBatch.draw(game.assetsLoader.scores[tScore/100-1], vAnim.x, vAnim.y);
				color.a = oldAlpha;
				spriteBatch.setColor(color);
				
				qScore.add(tScore);
				qX.add(tX);
				qY.add(tY);
				qAnimTime.add(tAnimTime+delta);
			}	
		}
	}
	
//	private void drawScores() {
//		int n = qScore.size();
//		for(int i=0;i<n;i++) {
//			tScore = qScore.poll();
//			tX = qX.poll();
//			tY = qY.poll();
//			tAnimTime = qAnimTime.poll();
//			vAnim = new Vector2(tX, tY);
//			spriteBatch.draw(game.assetsLoader.p100, tX, tY);
//			qScore.add(tScore);
//			qX.add(tX);
//			qY.add(tY);
//			qAnimTime.add(tAnimTime);
//		}
//	}
	
	public void dispose() {
		spriteBatch.dispose();
	}
	
	public void resize() {
		//pointDist = 75 * (Gdx.graphics.getWidth() / boardb.getWidth());
	}
	
}