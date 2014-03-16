package effectors;

import javax.media.opengl.GL2;

import solver.Constants;
import mesh.Particle;
import mesh.ParticleSystem;

public class GravityForce implements Force {

	ParticleSystem ps;
	double accel = Constants.GRAVITY_ACCEL;
	//double mass = Constants.PARTICLE_MASS;
	
	public GravityForce(ParticleSystem ps) {
		this.ps = ps;
	}
	
	@Override
	public void applyForce() {
		// apply gravity to every particle in ps
		for (Particle p : ps.P) {
			p.getF().y += (-accel) * p.getM();
		}
	}

	@Override
	public void display(GL2 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public ParticleSystem getParticleSystem() {
		// TODO Auto-generated method stub
		return this.ps;
	}

}
