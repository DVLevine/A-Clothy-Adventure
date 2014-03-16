package solver;

import java.util.ArrayList;
import java.util.HashMap;

import mesh.Particle;
import mesh.ParticleSystem;

public class Neighbors {
	
	ParticleSystem ps;
	HashMap<Index, ArrayList<Particle>> map;
	
	/** The dimensions of a cube. */
	private double DIM = 0.0875;
//	private double h = 0.05;
	
	public Neighbors(ParticleSystem ps) {
		this.ps = ps;
		map = new HashMap<Index, ArrayList<Particle>>();
//		DIM = ps.h;
	}
	
	public synchronized void storeParticles() {
		map.clear();
		int cx, cy, cz;
		for (Particle p : ps.P) {
			// find which block p belongs to
			cx = (int) Math.floor(p.getxNew().x/DIM);
			cy = (int) Math.floor(p.getxNew().y/DIM);
			cz = (int) Math.floor(p.getxNew().z/DIM);
			// place p in map
			Index i = new Index(cx, cy, cz);
			if (!map.containsKey(i)) {
				map.put(i, new ArrayList<Particle>());
			}
			map.get(i).add(p);
		}
		
//		System.out.println("map size: " + map.size());
	}
	
	public synchronized void findNeighbors() {
//		ArrayList<Particle> neighbors = null;
		int block_radius = (int) Math.ceil(ps.h/DIM);
		int cx, cy, cz;
		for (Particle p : ps.P) {
			p.getNeighbors().clear();
			// find which block p belongs to
			cx = (int) Math.floor(p.getxNew().x/DIM);
			cy = (int) Math.floor(p.getxNew().y/DIM);
			cz = (int) Math.floor(p.getxNew().z/DIM);
			// check surrounding blocks for neighbors
			for (int i = cx-block_radius; i <= cx+block_radius; i++) {
				for (int j = cy-block_radius; j <= cy+block_radius; j++) {
					for (int k = cz-block_radius; k <= cz+block_radius; k++) {
						Index key = new Index(i, j, k);
						if (map.containsKey(key) && map.get(key) != null) {
							p.getNeighbors().addAll(map.get(key));
						}
//						neighbors = map.get(new Index(i, j, k));
//						p.neighbors.addAll(neighbors);
					}
				}
			}
			
//		System.out.println(neighbors);	
		}
		
//		for (Particle p : ps.P) {
//			System.out.println("neighbors: " + p.neighbors.size());
//		}
	}
	
	private class Index {
		int i, j, k;
		public Index(int i, int j, int k) {
			this.i = i; this.j = j; this.k = k;
		}
		@Override
		public synchronized boolean equals(Object o) {
			Index index = (Index) o;
			return (index.i == this.i && index.j == this.j && index.k == this.k);
		}
		@Override
		public synchronized int hashCode() {
			int hash = 7;
			hash = 89 * hash + this.i;
			hash = 113 * hash + this.j;
			hash = 229 * hash + this.k;
			return hash;
		}
	}

}
