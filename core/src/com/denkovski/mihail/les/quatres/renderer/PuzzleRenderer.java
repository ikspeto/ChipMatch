package com.denkovski.mihail.les.quatres.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.denkovski.mihail.les.quatres.ChipMatch;
import com.denkovski.mihail.les.quatres.ChipMatch.GameType;
import com.denkovski.mihail.les.quatres.controller.PuzzleTile;
import com.denkovski.mihail.les.quatres.controller.Slot;
import com.denkovski.mihail.les.quatres.utility.Animation;
import com.denkovski.mihail.les.quatres.utility.Position;
import com.denkovski.mihail.les.quatres.utility.PuzzleAlgorithms;

public class PuzzleRenderer {

	
//	private final long NANO = 1000000000;
	
//	private PuzzleScreen puzzleScreen;
	private ChipMatch game;
	private ScoreRenderer scoreRenderer;
	
	public boolean bmap[][] = new boolean[7][7];	
	
//	private boolean spawnVisited[] = new boolean[49];
	private PuzzleAlgorithms puzzleAlgorithms;
	private Slot slots[];
	private Queue<Position> tAnimation = new LinkedList<Position>();
	private SpriteBatch batch;
	private Vector2 center;
	private Vector2 vAnim;
	private float constAnimTime;
	private float aTileSize, uiRatio;
	private int selected, comboScore;	
	private Random random;
	private boolean clicked, bSpawnTiles;
//	private float lastTime = 0;
	private Set<Integer> sselected = new HashSet<Integer>();
	private ArrayList<Integer> remaining = new ArrayList<Integer>();
	
	
	
	public PuzzleRenderer(ChipMatch game, ScoreRenderer scoreRenderer) {
		for(int i=0;i<49;i++) {
			remaining.add(i);
		}
		this.game = game;
		this.scoreRenderer = scoreRenderer;
		uiRatio = (Gdx.graphics.getHeight()*0.95f)/game.assetsLoader.puzzle_grid.getRegionHeight();
		
		bSpawnTiles = false;
		random = new Random();
		clicked = false;
		comboScore = 0;
		puzzleAlgorithms = new PuzzleAlgorithms();
		//prefs = Gdx.app.getPreferences("GameScore Preferences");
		
		// Sprites 
		batch = new SpriteBatch();
		
		// Camera
		
//		center = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		center = new Vector2(Gdx.graphics.getWidth() / 2, game.grid_padding_bottom+(Gdx.graphics.getWidth()*0.95f)/2);
		// Animations
		constAnimTime = 0.05f;
	
//		if(game.screenRatio == ScreenRatio.r16t9)
//			game.pointDist = uiRatio * 74.0f;
//		else
//			game.pointDist = uiRatio * 116.0f;
		
		float w1 = game.assetsLoader.redcircle.getRegionWidth();
//		game.tileSize = w1 * game.gRatio;
//		game.tileSize = game.pointDist-2.0f*uiRatio;
		
		slots = new Slot[49];
		int z = 0;
		for(int i=-3;i<=3;i++)
			for(int j=-3;j<=3;j++) {
				slots[z] = new Slot();
				slots[z].bounds = new Rectangle(center.x + j * game.pointDist - game.tileSize/2, (Gdx.graphics.getHeight() - center.y) + i * game.pointDist - game.tileSize/2, game.tileSize, game.tileSize);
				slots[z].i = i; // row
				slots[z].j = j; // column
				z++;
				bmap[i+3][j+3] = false;
			}

		spawnTile(true, 0);spawnTile(true, 0);spawnTile(true, 0);
	}
	
	public void render(float delta) {
		if(remaining.isEmpty()) 
			game.paused = true;
		if(!game.paused) {
//			lastTime += delta;
			
			if(bSpawnTiles) {
				spawnTile(true, 0);
				spawnTile(true, 0);
				if(random.nextBoolean())
					spawnTile(false, 0);
				if(random.nextBoolean())
					spawnTile(false, 0);
				bSpawnTiles = false;
			}
	
			if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) 
				clicked = false;
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !clicked) {
				for(int i=0;i<49;i++) {
					if(slots[i].isInside(new Vector2(Gdx.input.getX(), Gdx.input.getY()))) {
						if(!sselected.isEmpty()) {
							for(Integer j : sselected) {
								selected = j;
								if(!bmap[slots[i].i+3][slots[i].j+3]) {
									clicked = true;
									tAnimation = puzzleAlgorithms.findPath(slots[selected].puzzleTile.getPosition().y, slots[selected].puzzleTile.getPosition().x, slots[i].i, slots[i].j, bmap);
									slots[selected].puzzleTile.qAnimation.clear();
									if(tAnimation == null) {
										break;
									}
									while(!tAnimation.isEmpty()) {
										slots[selected].puzzleTile.qAnimation.add(tAnimation.peek().y);
										if(tAnimation.peek().x == 0)
											slots[selected].puzzleTile.qAxis.add(false);
										else
											slots[selected].puzzleTile.qAxis.add(true);
										tAnimation.poll();
									}
									slots[selected].puzzleTile.bAnimate = true;
									slots[selected].puzzleTile.animTime = 0;
									slots[selected].puzzleTile.aOrigin = selected;
//									if(remaining.contains(i))
//										remaining.remove(remaining.indexOf(i));
									slots[selected].puzzleTile.aDest = i;
//									remaining.add(selected);
									if(slots[selected].puzzleTile.qAxis.peek() == true) { // X axis
										slots[selected].puzzleTile.animTotalTime = Math.abs(slots[selected].puzzleTile.qAnimation.peek() - slots[selected].puzzleTile.getPosition().x) * constAnimTime;
									} else { // Y axis
										slots[selected].puzzleTile.animTotalTime = Math.abs(slots[selected].puzzleTile.qAnimation.peek() - slots[selected].puzzleTile.getPosition().y) * constAnimTime;
									}
								} else if(selected == i) {
									selected = -1;
									clicked = true;
								}
								sselected.remove(j);
							}
						} else {
							if(bmap[slots[i].i+3][slots[i].j+3]) {
								sselected.add(i);
								clicked = true;
								selected = i;
							}
						}
					}
				}
		    }
		}


		batch.begin();
		if(!game.paused)
			animate(delta);
		drawTiles();

		batch.end();



		if(game.paused) {
			game.prefs.putInteger("CurrentScore", game.gameScore);
			game.prefs.flush();
		}

	}

	private boolean checked, pack6;
    private int temp[] = new int[4], zz, xx, fx, fz;

	public boolean checkMatch() {
		int i, j;
		for(int ii=0;ii<=5;ii++) {
			for(int jj=0;jj<=5;jj++) {
				if(bmap[ii][jj] && bmap[ii][jj+1] && bmap[ii+1][jj] && bmap[ii+1][jj+1]) {
					i = (ii) * 7 + jj;
					j = (ii+1) * 7 + jj;
					checked = false;
					temp[0] = slots[i].puzzleTile.puzzleNum;
					temp[1] = slots[i+1].puzzleTile.puzzleNum;
					temp[2] = slots[j].puzzleTile.puzzleNum;
					temp[3] = slots[j+1].puzzleTile.puzzleNum;

					Arrays.sort(temp);

					if(temp[0] == temp[1] && temp[1] == temp[2] && temp[2] == temp[3]) {
						checked = true;
					} else {
                        if (temp[0] == -1 && temp[1] == temp[2] && temp[2] == temp[3]) {
                            checked = true;
                        } else {
                            if (temp[0] == -1 && temp[1] == -1 && temp[2] == temp[3]) {
                                checked = true;
                            } else {
                                if (temp[0] == -1 && temp[1] == -1 && temp[2] == -1 && temp[3] != -1) {
                                    checked = true;
                                }
                            }
                        }
                    }
                    pack6 = false;
                    if(temp[0] == -1 && temp[1] == -1 && temp[2] == -1 && temp[3] == -1) {
                        checked = false;
                        if(jj < 5 && bmap[ii][jj+2] && bmap[ii+1][jj+2]) { // Match of 6 horizontally
                            if(slots[i+2].puzzleTile.puzzleNum == slots[j+2].puzzleTile.puzzleNum || (slots[i+2].puzzleTile.puzzleNum == -1 && slots[j+2].puzzleTile.puzzleNum != -1) || (slots[j+2].puzzleTile.puzzleNum == -1 && slots[i+2].puzzleTile.puzzleNum != -1)) {
                                 checked = true;
                                pack6 = true;
                            }
                        }
                        if(ii < 5 && bmap[ii+2][jj] && bmap[ii+2][jj+1]) { // Match of 6 vertically
                            zz = (ii+2)*7 + jj;
                            if(slots[zz].puzzleTile.puzzleNum == slots[zz+1].puzzleTile.puzzleNum || (slots[zz].puzzleTile.puzzleNum == -1 && slots[zz+1].puzzleTile.puzzleNum != -1) || (slots[zz+1].puzzleTile.puzzleNum == -1 && slots[zz].puzzleTile.puzzleNum != -1)) {
                                checked = true;
                                pack6 = true;
                            }
                        }
                    }
					
				    fx = -1; fz = -1;
					if(checked) {
                        if(jj < 5 && bmap[ii][jj+2] && bmap[ii+1][jj+2]) { // Match of 6 horizontally
                            if(((slots[i+2].puzzleTile.puzzleNum == temp[0] ||
                                slots[i+2].puzzleTile.puzzleNum == temp[1] ||
                                slots[i+2].puzzleTile.puzzleNum == temp[2] ||
                                slots[i+2].puzzleTile.puzzleNum == temp[3] ||
                                    slots[i+2].puzzleTile.puzzleNum == -1) &&
                               (slots[j+2].puzzleTile.puzzleNum == temp[0] ||
                                slots[j+2].puzzleTile.puzzleNum == temp[1] ||
                                slots[j+2].puzzleTile.puzzleNum == temp[2] ||
                                slots[j+2].puzzleTile.puzzleNum == temp[3]) ||
                                    slots[j+2].puzzleTile.puzzleNum == -1) || (pack6 && slots[i+2] == slots[j+2])){

                                bmap[ii][jj+2] = false;
                                bmap[ii+1][jj+2] = false;

                                if(!slots[i+2].puzzleTile.qAnimation.isEmpty() && slots[i+2].puzzleTile.qAnimation.element() == -20) {
                                    slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
                                    slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
                                    slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
                                    slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                                    slots[j+2].puzzleTile.qAnimation.add(-30); slots[j+2].puzzleTile.qAxis.add(true);
                                }
                                if(!slots[j+2].puzzleTile.qAnimation.isEmpty() && slots[j+2].puzzleTile.qAnimation.element() == -20) {
                                    slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
                                    slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
                                    slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
                                    slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                                    slots[i+2].puzzleTile.qAnimation.add(-30); slots[i+2].puzzleTile.qAxis.add(true);
                                }


                                fx = i+2;
                                fz = j+2;
                            }
                        }
                        if(ii < 5 && bmap[ii+2][jj] && bmap[ii+2][jj+1]) { // Match of 6 vertically
                            zz = (ii+2)*7 + jj;
                            if(((slots[zz].puzzleTile.puzzleNum == temp[0] ||
                                    slots[zz].puzzleTile.puzzleNum == temp[1] ||
                                    slots[zz].puzzleTile.puzzleNum == temp[2] ||
                                    slots[zz].puzzleTile.puzzleNum == temp[3] ||
                                    slots[zz].puzzleTile.puzzleNum == -1) &&
                                    (slots[zz+1].puzzleTile.puzzleNum == temp[0] ||
                                            slots[zz+1].puzzleTile.puzzleNum == temp[1] ||
                                            slots[zz+1].puzzleTile.puzzleNum == temp[2] ||
                                            slots[zz+1].puzzleTile.puzzleNum == temp[3]) ||
                                    slots[zz+1].puzzleTile.puzzleNum == -1) || (pack6 && slots[zz].puzzleTile.puzzleNum == slots[zz+1].puzzleTile.puzzleNum)){

                                bmap[ii+2][jj] = false;
                                bmap[ii+2][jj+1] = false;

                                if(!slots[zz].puzzleTile.qAnimation.isEmpty() && slots[zz].puzzleTile.qAnimation.element() == -20) {
                                    slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
                                    slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
                                    slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
                                    slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                                    slots[zz+1].puzzleTile.qAnimation.add(-30); slots[zz+1].puzzleTile.qAxis.add(true);
                                }
                                if(!slots[zz+1].puzzleTile.qAnimation.isEmpty() && slots[zz+1].puzzleTile.qAnimation.element() == -20) {
                                    slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
                                    slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
                                    slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
                                    slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                                    slots[zz].puzzleTile.qAnimation.add(-30); slots[zz].puzzleTile.qAxis.add(true);
                                }

                                fx = zz;
                                fz = zz+1;
                            }
                        }

						bmap[ii][jj] = false;
						bmap[ii][jj+1] = false;
						bmap[ii+1][jj] = false;
						bmap[ii+1][jj+1] = false;
						
						if(!slots[i].puzzleTile.qAnimation.isEmpty() && slots[i].puzzleTile.qAnimation.element() == -20) {
							slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
							slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
							slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                            if(fz != -1) {
                                slots[fz].puzzleTile.qAnimation.add(-30); slots[fz].puzzleTile.qAxis.add(true);
                                slots[fx].puzzleTile.qAnimation.add(-30); slots[fx].puzzleTile.qAxis.add(true);
                            }
						}
						if(!slots[i+1].puzzleTile.qAnimation.isEmpty() && slots[i+1].puzzleTile.qAnimation.element() == -20) {
							slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
							slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
							slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                            if(fz != -1) {
                                slots[fz].puzzleTile.qAnimation.add(-30); slots[fz].puzzleTile.qAxis.add(true);
                                slots[fx].puzzleTile.qAnimation.add(-30); slots[fx].puzzleTile.qAxis.add(true);
                            }
						}
						if(!slots[j].puzzleTile.qAnimation.isEmpty() && slots[j].puzzleTile.qAnimation.element() == -20) {
							slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
							slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
							slots[j+1].puzzleTile.qAnimation.add(-30); slots[j+1].puzzleTile.qAxis.add(true);
                            if(fz != -1) {
                                slots[fz].puzzleTile.qAnimation.add(-30); slots[fz].puzzleTile.qAxis.add(true);
                                slots[fx].puzzleTile.qAnimation.add(-30); slots[fx].puzzleTile.qAxis.add(true);
                            }
						}
						if(!slots[j+1].puzzleTile.qAnimation.isEmpty() && slots[j+1].puzzleTile.qAnimation.element() == -20) {
							slots[i+1].puzzleTile.qAnimation.add(-30); slots[i+1].puzzleTile.qAxis.add(true);
							slots[j].puzzleTile.qAnimation.add(-30); slots[j].puzzleTile.qAxis.add(true);
							slots[i].puzzleTile.qAnimation.add(-30); slots[i].puzzleTile.qAxis.add(true);
                            if(fz != -1) {
                                slots[fz].puzzleTile.qAnimation.add(-30); slots[fz].puzzleTile.qAxis.add(true);
                                slots[fx].puzzleTile.qAnimation.add(-30); slots[fx].puzzleTile.qAxis.add(true);
                            }
						}
						
						scoreRenderer.addAnimation(100*(comboScore+1), center.x + slots[j].puzzleTile.getPosition().x * game.pointDist - ((int)game.tileSize/2), center.y - ((int)game.tileSize/2) - slots[j].puzzleTile.getPosition().y * game.pointDist);


                        if(fz != -1) {
                            slots[fz].puzzleTile.qAnimation.add(-10); slots[fz].puzzleTile.qAxis.add(true);
                            slots[fx].puzzleTile.qAnimation.add(-10); slots[fx].puzzleTile.qAxis.add(true);

                            slots[fz].puzzleTile.bAnimate = true; slots[fz].puzzleTile.bAnimate = true;
                            slots[fx].puzzleTile.animTime = 0; slots[fx].puzzleTile.animTime = 0;

                            slots[fz].puzzleTile.aOrigin = fz; slots[fz].puzzleTile.aDest = fz;
                            slots[fx].puzzleTile.aOrigin = fx; slots[fx].puzzleTile.aDest = fx;

                            slots[fz].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
                            slots[fx].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
                        }

						slots[i].puzzleTile.qAnimation.add(-10); slots[i].puzzleTile.qAxis.add(true);
						slots[i+1].puzzleTile.qAnimation.add(-10); slots[i+1].puzzleTile.qAxis.add(true);
						slots[j].puzzleTile.qAnimation.add(-10); slots[j].puzzleTile.qAxis.add(true);
						slots[j+1].puzzleTile.qAnimation.add(-10); slots[j+1].puzzleTile.qAxis.add(true);
						
						slots[i+1].puzzleTile.bAnimate = true; slots[j].puzzleTile.bAnimate = true; slots[j+1].puzzleTile.bAnimate = true;
						slots[i+1].puzzleTile.animTime = 0; slots[j].puzzleTile.animTime = 0; slots[j+1].puzzleTile.animTime = 0;
						
						slots[i].puzzleTile.aOrigin = i; slots[i].puzzleTile.aDest = i;
						slots[i+1].puzzleTile.aOrigin = i+1; slots[i+1].puzzleTile.aDest = i+1;
						slots[j].puzzleTile.aOrigin = j; slots[j].puzzleTile.aDest = j;
						slots[j+1].puzzleTile.aOrigin = j+1; slots[j+1].puzzleTile.aDest = j+1;
					
						
						slots[i].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
						slots[i+1].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
						slots[j].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
						slots[j+1].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
						
						
						game.gameScore += 100 * (comboScore + 1);
						game.matchesNumber++;
						if(game.prefs.getInteger("highScore") < game.gameScore) {
							game.prefs.putInteger("highScore", game.gameScore);
							game.prefs.flush();
						}
						
//						remaining.add(i);
//						remaining.add(i+1);
//						remaining.add(j);
//						remaining.add(j+1);
						
						if(game.gameScore >= game.rankTargets[game.currentRank]) {
							game.currentRank++;
							if(!game.prefs.contains("Achievement"+Integer.toString(i))) {
								   game.prefs.putInteger("Achievement"+Integer.toString(i), 1);
								   game.actionResolver.unlockAchievementGPGS(game.achievementId[game.currentRank-1]);
								   game.prefs.flush();
							}
						}
//						if(game.gameScore >= game.rankTargets[game.currentRank]) {
//							game.currentRank++;
//						}
						
						if(game.matchesNumber >= game.levelTargets[game.currentLevel]) {
							startNextLevel();
						}

						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	
	private void spawnTile(boolean mustSpawn, float pauseTime) {
		PuzzleTile spuzzleTile = new PuzzleTile();
		if(remaining.isEmpty()) 
			game.paused = true;
		
		if(remaining.isEmpty()) {
			game.paused = true;
		} else {
			int j = random.nextInt(remaining.size()), rand;
			rand = remaining.get(j);
			
			spuzzleTile.setPosition(new Position(slots[rand].i, slots[rand].j));
			slots[rand].puzzleTile = spuzzleTile;
			slots[rand].isEmpty = false;
			if(random.nextInt(100) > 90) {
				slots[rand].puzzleTile.puzzleNum = -1;
			} else {
				slots[rand].puzzleTile.puzzleNum = random.nextInt(4);
			}
			
			if(pauseTime > 0) {
				slots[rand].puzzleTile.qAnimation.add(-30); slots[rand].puzzleTile.qAxis.add(true);
				slots[rand].puzzleTile.bAnimate = true;
				slots[rand].puzzleTile.animTime = 0;
				slots[rand].puzzleTile.destiny = 3;
			} else {	
				slots[rand].puzzleTile.qAnimation.add(-20); slots[rand].puzzleTile.qAxis.add(true);
				slots[rand].puzzleTile.bAnimate = true;
				slots[rand].puzzleTile.animTime = 0;
				slots[rand].puzzleTile.destiny = 3;
			}
			if(game.gameType == GameType.ACTION) {
				slots[rand].puzzleTile.animTotalTime = 3.0f;
			} else {
				slots[rand].puzzleTile.animTotalTime = Math.abs(8*constAnimTime);
			}
			
			slots[rand].puzzleTile.aOrigin = rand; slots[rand].puzzleTile.aDest = rand;
			slots[rand].puzzleTile.animX = center.x + slots[rand].puzzleTile.getPosition().x * game.pointDist - ((int)game.tileSize/2);
			slots[rand].puzzleTile.animY = center.y - ((int)game.tileSize/2) - slots[rand].puzzleTile.getPosition().y * game.pointDist;
			if(remaining.isEmpty()) {				
				game.paused = true;
//				System.out.println(game.paused);
			}
//			if(game.gameType == GameType.ACTION)
//				bSpawnTiles = true;
//			bmap[slots[rand].i+3][slots[rand].j+3] = true;
			
		}
		if(remaining.isEmpty()) {				
			game.paused = true;
		}
	}
	

	private void animate(float delta) {
		for(int i=0;i<49;i++) {
			if(slots[i].isEmpty)
				continue;
			if(!slots[i].puzzleTile.qAnimation.isEmpty()) {
				if(slots[i].puzzleTile.animTime + delta < slots[i].puzzleTile.animTotalTime) {
					if(slots[i].puzzleTile.qAnimation.peek() == -30) { // Pause
						slots[i].puzzleTile.animTime += delta;
					} else if(slots[i].puzzleTile.qAnimation.peek() == -20) { // Spawn a tile
						vAnim = new Vector2(center.x - game.tileSize / 2 + slots[i].puzzleTile.getPosition().x * game.pointDist, center.y - game.tileSize / 2 - slots[i].puzzleTile.getPosition().y * game.pointDist);
						aTileSize = 0;
						slots[i].puzzleTile.animTime += delta;
						aTileSize = Animation.easeOutQuad(slots[i].puzzleTile.animTime, 0, game.tileSize, slots[i].puzzleTile.animTotalTime);
						slots[i].puzzleTile.animSize = aTileSize;
						slots[i].puzzleTile.animX = vAnim.x + (game.tileSize-aTileSize)/2;
						slots[i].puzzleTile.animY = vAnim.y + (game.tileSize-aTileSize)/2;
						
					} else if(slots[i].puzzleTile.qAnimation.peek() == -10) { // Remove a tile
						slots[i].puzzleTile.destiny = 2;
						vAnim = new Vector2(center.x - game.tileSize / 2 + slots[i].puzzleTile.getPosition().x * game.pointDist, center.y - game.tileSize / 2 - slots[i].puzzleTile.getPosition().y * game.pointDist);
						aTileSize = game.tileSize;
						slots[i].puzzleTile.animTime += delta;
						vAnim.x = Animation.easeOutQuad(slots[i].puzzleTile.animTime, vAnim.x, (Gdx.graphics.getWidth() - game.tileSize - vAnim.x), slots[i].puzzleTile.animTotalTime);
						vAnim.y = Animation.easeOutQuad(slots[i].puzzleTile.animTime, vAnim.y, (Gdx.graphics.getHeight() - 2*game.tileSize - vAnim.y), slots[i].puzzleTile.animTotalTime);
						//aTileSize = Animation.easeOutQuad(slots[i].puzzleTile.animTime, game.tileSize, -game.tileSize, slots[i].puzzleTile.animTotalTime);
						slots[i].puzzleTile.animSize = game.tileSize;
						slots[i].puzzleTile.animX = vAnim.x;
//						slots[i].puzzleTile.animY = vAnim.y + (game.tileSize-aTileSize)/2;
						slots[i].puzzleTile.animY = vAnim.y;
					} else {
						vAnim = new Vector2(center.x - game.tileSize / 2 + slots[i].puzzleTile.getPosition().x * game.pointDist, center.y - game.tileSize / 2 - slots[i].puzzleTile.getPosition().y * game.pointDist);
						slots[i].puzzleTile.animTime += delta;
						if(slots[i].puzzleTile.qAxis.peek() == true) { // X axis
							vAnim.x = Animation.easeInQuad(slots[i].puzzleTile.animTime, center.x - game.tileSize / 2 + slots[i].puzzleTile.getPosition().x * game.pointDist, (slots[i].puzzleTile.qAnimation.peek() - slots[i].puzzleTile.getPosition().x) * game.pointDist, slots[i].puzzleTile.animTotalTime);
						} else { // Y axis
							vAnim.y = Animation.easeInQuad(slots[i].puzzleTile.animTime, center.y - game.tileSize / 2 - slots[i].puzzleTile.getPosition().y * game.pointDist, -(slots[i].puzzleTile.qAnimation.peek() - slots[i].puzzleTile.getPosition().y) * game.pointDist, slots[i].puzzleTile.animTotalTime);
						}
						slots[i].puzzleTile.animSize = game.tileSize;
						slots[i].puzzleTile.animX = vAnim.x;
						slots[i].puzzleTile.animY = vAnim.y;
					}
				} else {
					int tDestiny = slots[i].puzzleTile.destiny;
//					if(slots[i].puzzleTile.qAnimation.peek() == -20) {
//						if(game.gameType == GameType.ACTION)
//							bSpawnTiles = true;
//						bmap[slots[i].i+3][slots[i].j+3] = true;
//					}
					if(slots[i].puzzleTile.qAnimation.peek() > -10) {
						if(slots[i].puzzleTile.qAxis.peek() == true) { // X axis
							slots[i].puzzleTile.setPosition(new Position(slots[i].puzzleTile.getPosition().y, slots[i].puzzleTile.qAnimation.peek()));
						} else { // Y axis
							slots[i].puzzleTile.setPosition(new Position(slots[i].puzzleTile.qAnimation.peek(), slots[i].puzzleTile.getPosition().x));
						}
					}
					slots[i].puzzleTile.qAnimation.poll();
					slots[i].puzzleTile.qAxis.poll();
					slots[i].puzzleTile.animTime = 0;
					if(slots[i].puzzleTile.qAnimation.isEmpty()) {
						if(tDestiny == 3) { // Spawned
							// Handeled in SpawnTile()
							
							if(game.gameType == GameType.ACTION)
								bSpawnTiles = true;
							bmap[slots[i].i+3][slots[i].j+3] = true;
//							bmap[slots[i].i+3][slots[i].j+3] = true;
							remaining.remove(remaining.indexOf(slots[i].puzzleTile.aDest));
//							remaining.remove(remaining.indexOf(slots[i].puzzleTile.aDest));
						} else if(tDestiny == 2) { // Removed
							remaining.add(slots[i].puzzleTile.aOrigin);
						} else if(tDestiny == 1 || tDestiny == 0) { // Moved
							remaining.remove(remaining.indexOf(slots[i].puzzleTile.aDest));
							remaining.add(slots[i].puzzleTile.aOrigin);
						}
						
						int j = slots[i].puzzleTile.aDest;
						if(tDestiny != 3) {
							bmap[slots[slots[i].puzzleTile.aOrigin].i+3][slots[slots[i].puzzleTile.aOrigin].j+3] = false;
							if(tDestiny != 2) 
								bmap[slots[j].i+3][slots[j].j+3] = true;
							if(tDestiny != 2) {
								slots[j].puzzleTile = slots[i].puzzleTile;
								slots[j].isEmpty = false;
							}
							slots[i].puzzleTile = null;
							slots[i].isEmpty = true;							
//							remaining.add(i);
						}
						
						 
						
						
						if(tDestiny != 2) {
							slots[j].puzzleTile.destiny = 0;
						
							slots[j].puzzleTile.animSize = game.tileSize;
							slots[j].puzzleTile.animX = center.x + slots[slots[j].puzzleTile.aDest].puzzleTile.getPosition().x * game.pointDist - game.tileSize/2;
							slots[j].puzzleTile.animY = center.y - game.tileSize/2 - slots[slots[j].puzzleTile.aDest].puzzleTile.getPosition().y * game.pointDist;
						}
						if(tDestiny == 0) {
							
							if(!checkMatch()) {
								if(game.gameType == GameType.ZEN) {
									spawnTile(true, 0);
									spawnTile(true, 0);
									if(random.nextBoolean())
										spawnTile(false, 0);
									if(random.nextBoolean())
										spawnTile(false, 0);
								}
								comboScore = 0;
							} else {
								comboScore++;
							}
//							System.out.println(remaining);
						}
						if(checkMatch())
							comboScore++;
						
					} else {
						if(slots[i].puzzleTile.qAnimation.peek() == -20 || slots[i].puzzleTile.qAnimation.peek() == -10 || slots[i].puzzleTile.qAnimation.peek() == -30) {
							slots[i].puzzleTile.animTotalTime = 8 * constAnimTime;
						} else if(slots[i].puzzleTile.qAxis.peek() == true) { // X axis
							slots[i].puzzleTile.animTotalTime = Math.abs(slots[i].puzzleTile.qAnimation.peek() - slots[i].puzzleTile.getPosition().x) * constAnimTime;
						} else { // Y axis
							slots[i].puzzleTile.animTotalTime = Math.abs(slots[i].puzzleTile.qAnimation.peek() - slots[i].puzzleTile.getPosition().y) * constAnimTime;
						}
						slots[i].puzzleTile.animSize = game.tileSize;
						slots[i].puzzleTile.animX = center.x + slots[i].puzzleTile.getPosition().x * game.pointDist - game.tileSize/2;
						slots[i].puzzleTile.animY = center.y - game.tileSize/2 - slots[i].puzzleTile.getPosition().y * game.pointDist;
					}
				}
			} else {
				slots[i].puzzleTile.animSize = game.tileSize;
				slots[i].puzzleTile.animX = center.x + slots[i].puzzleTile.getPosition().x * game.pointDist - ((int)game.tileSize/2);
				slots[i].puzzleTile.animY = center.y - ((int)game.tileSize/2) - slots[i].puzzleTile.getPosition().y * game.pointDist;
			}
		}
	}
	
	void drawTiles() {
		int i;
		for(i=0;i<49;i++) {
			if(slots[i].isEmpty)
				continue;
			if(slots[i].puzzleTile.puzzleNum == -1) { // Joker
				if(sselected.contains(i))
					batch.draw(game.assetsLoader.jokercircles, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
				else
					batch.draw(game.assetsLoader.jokercircle, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
			} else if(slots[i].puzzleTile.puzzleNum == 0) { 
				if(sselected.contains(i))
					batch.draw(game.assetsLoader.redcircles, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
				else
					batch.draw(game.assetsLoader.redcircle, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
			} else if(slots[i].puzzleTile.puzzleNum == 1) {
				if(sselected.contains(i))
					batch.draw(game.assetsLoader.yellowcircles, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
				else
					batch.draw(game.assetsLoader.yellowcircle, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
			} else if(slots[i].puzzleTile.puzzleNum == 2) {
				if(sselected.contains(i))
					batch.draw(game.assetsLoader.greencircles, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
				else
					batch.draw(game.assetsLoader.greencircle, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
			} else if(slots[i].puzzleTile.puzzleNum == 3) {
				if(sselected.contains(i))
					batch.draw(game.assetsLoader.orangecircles, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
				else
					batch.draw(game.assetsLoader.orangecircle, slots[i].puzzleTile.animX, slots[i].puzzleTile.animY, slots[i].puzzleTile.animSize, slots[i].puzzleTile.animSize);
			}
		}
	}
	
	private void startNextLevel() {
		int z = 0;
		for(int i=-3;i<=3;i++)
			for(int j=-3;j<=3;j++) {
				if(!slots[z].isEmpty && slots[z].puzzleTile.qAnimation.isEmpty()) {
					slots[z].puzzleTile.qAnimation.add(-10); slots[z].puzzleTile.qAxis.add(true);
					slots[z].puzzleTile.bAnimate = true;
					slots[z].puzzleTile.animTime = 0; 
					slots[z].puzzleTile.aOrigin = z; slots[z].puzzleTile.aDest = z;
					slots[z].puzzleTile.animTotalTime = Math.abs(8 * constAnimTime);
					bmap[i+3][j+3] = false;
//					remaining.add(z);
				}
//				slots[z].puzzleTile = null;
//				slots[z].isEmpty = true;
				z++;
			}
		game.matchesNumber = 0;
		game.currentLevel++;
		spawnTile(true, 8 * constAnimTime);spawnTile(true, 8 * constAnimTime);spawnTile(true, 8 * constAnimTime);
		game.paused = true;
		game.levelEnd = true;
	}
	
	public void dispose() {
		batch.dispose();
	}
	
	public void resize() {
		//game.pointDist = 75 * (Gdx.graphics.getWidth() / puzzle_grid.getWidth());
	}
	
}
