package com.denkovski.mihail.les.quatres.utility;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.denkovski.mihail.les.quatres.renderer.PuzzleRenderer;

public class PuzzleAlgorithms {
	
	private static int x[] = {0, 1, 0, -1}, y[] = {1, 0, -1, 0};
	
	private Queue<Position> qAnimation = new LinkedList<Position>();
	
	private Queue<Integer> qx = new LinkedList<Integer>();
	private Queue<Integer> qy = new LinkedList<Integer>();
	private Queue<Integer> qn = new LinkedList<Integer>();
	private Queue<Stack<Integer> > qqx = new LinkedList<Stack<Integer> >();
	private Queue<Stack<Integer> > qqy = new LinkedList<Stack<Integer> >();
	private boolean[][] visited = new boolean[7][7];
	
	private int cx, cy, tx, ty, i, j, cn;
	private Stack<Integer> cqx = new Stack<Integer>();
	private Stack<Integer> cqy = new Stack<Integer>();

    private boolean bmap[][] = new boolean[7][7];

	public Queue<Position> findPath(int sy, int sx, int ey, int ex, boolean bmap[][]) {
        this.bmap = bmap;
		sy += 3; sx += 3;
		ey += 3; ex += 3;
	
		for(i=0;i<7;i++) {
			for(j=0;j<7;j++) {
				visited[i][j] = false;
			}
		}
		
		qAnimation.clear();
		qx.clear(); qy.clear();
		qqx.clear(); qqy.clear();
		qn.clear();
		
		qx.add(sx); qy.add(sy);
		qn.add(1);
		cqx.clear(); cqy.clear();
		cqx.add(sx); cqy.add(sy);
		qqx.add(cqx); qqy.add(cqy);
		
		while(!qx.isEmpty()) {
			cx = qx.poll();
			cy = qy.poll();
			cn = qn.poll();
			cqx = qqx.poll();
			cqy = qqy.poll();
		
			
			if(cx == ex && cy == ey) {
				qAnimation.clear();
				convertQueue(sy, sx);
				return qAnimation;
			}
			
			for(i=0; i<4; i++) {
				tx = cx + x[i];
				ty = cy + y[i];
				if(tx >= 0 && tx < 7 && ty >= 0 && ty < 7) {
					if(bmap[ty][tx] == false && !visited[ty][tx]) {
						qx.add(tx);
						qy.add(ty);
						cqx.add(tx);
						cqy.add(ty);
						qqx.add((Stack<Integer>)cqx.clone());
						qqy.add((Stack<Integer>)cqy.clone());
						cqx.pop();
						cqy.pop();
						qn.add(cn+1);
						visited[ty][tx] = true;
					}
				}
			}
		}
		
		return null;
	}
	
	
	private void convertQueue(int sy, int sx) {
//		System.out.println(cn);
		i = 0;
		
		while(i < cqx.size()-1) {
			j = i;
			while(i+1 < cqx.size() && cqx.elementAt(i) == cqx.elementAt(i+1)) 
				i++;
			if(i != j)
				qAnimation.add(new Position(cqy.elementAt(i)-3, 0));
			
			j = i;
			while(i+1 < cqy.size() && cqy.elementAt(i) == cqy.elementAt(i+1))
				i++;
			if(i != j)
				qAnimation.add(new Position(cqx.elementAt(i)-3, 1));
		}
		
	}

}
