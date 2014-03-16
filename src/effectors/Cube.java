package effectors;

import mesh.Particle;
import mesh.ParticleSystem;

public class Cube implements CollisionObject {
	// plane positions
	int x1 = 1; // x = 1
	int x0 = 0; // x = 0
	int z1 = 1; // z = 1
	int z0 = 0; // z = 0
	int y0 = 0; // y = 0
	
	boolean hasCollided = false;
	
	/** The ParticleSystem the cube is in.*/
	ParticleSystem ps;
	
	public Cube(ParticleSystem ps) {
		this.ps = ps;
	}
	@Override
	public synchronized void processCollisions() {
		for (Particle p : ps.P) {
			if (p.getxNew().x > x1) {
				p.getxNew().x = x1;
				hasCollided = true;
			}
			if (p.getxNew().x < x0) {
				p.getxNew().x = x0;
				hasCollided = true;
			}
			if (p.getxNew().y < y0) {
				p.getxNew().y = y0;
				hasCollided = true;
			}
			if (p.getxNew().z > z1) {
				p.getxNew().z = z1;
				hasCollided = true;
			}
			if (p.getxNew().z < z0) {
				p.getxNew().z = z0;
				hasCollided = true;
			}
			if (hasCollided) {
				p.getV().set(0,0,0);
			}
		}	
	}
}
