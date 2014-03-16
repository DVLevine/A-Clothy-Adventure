package solver;

import javax.vecmath.Vector3d;

import effectors.CollisionObject;
import effectors.Constraint;
import effectors.PostModifier;
import mesh.Particle;
import mesh.ParticleSystem;

public class EulerSolver extends Solver {
	
	// [xdot, ydot, zdot, vxdot, vydot, vzdot]
	// double[i] to double[i+5] => phase space for ps.P.get(i)
//	double[] phaseSpace;
	
//	Particle p;
	
//	Neighborhood n;
	Neighbors n;
	
	public EulerSolver(ParticleSystem ps) {
		super(ps);
//		n = new Neighborhood(ps);
		n = new Neighbors(ps);
	}
	
	@Override
	public synchronized void advanceTime(double dt) {
		eulerStep(dt);
	}
	
//	public synchronized void advanceTime(double dt) {
//		phaseSpace = new double[ps.P.size()*6];
		//System.out.println("size of phase space: " + phaseSpace.length);
		
//		eulerStep(dt);
		
//		int nSteps = N_STEPS_PER_FRAME;
//		
//		double dt  = DT/(double)nSteps;
//		
//		for(int k = 0; k < nSteps; k++) {
//			//PS.advanceTime(dt);
//			eulerStep(dt);
//		}
//	}
	
	public synchronized void eulerStep(double dt) {
		/* x(t0 + dt) = x(t0) + dt*x'(t0) */
		
		// get derivative
//		particleDerivative(phaseSpace);
		
		
		// scale derivative
//		scaleDerivative(phaseSpace, dt);
		
		// apply forces and update velocity
		applyForces(dt);
		
		// predict the next position of the particles
		predictPosition(dt);
		
		// hash and find neighbors
		n.storeParticles();
		n.findNeighbors();
		
		for (int iter = 0; iter < 3; iter++) {
//			// calculate position updates
			calculatePositionUpdates();
//			for (Particle p : ps.P) {
//				if (p.dx.y > 0.1) System.out.println("dx: " + p.dx);
//			}
//			
//			//process collisions
			processCollisions();
//			
//			// update predicted positions
			updatePredictedPositions();
		}
		
		// update velocity
		updateVelocity(dt);
		
		//apply modifiers
		postProcess();
		
		// update the position of the particles
		updateState();
		
		// testing
		
		
		// get the particle state, add current step to state
//		doTimeStep(dt);
		
		// update state
//		updateState(phaseSpace);
		
		
		
		//update hash state
		//ps.hood.hashAllParticles();
//		n.updateNeighbors();
//		ps.hood.updateNeighbors();
		// update time
//		ps.incrementTime(dt);
		
		
	}
	
	public synchronized void predictPosition(double dt) {
		
		for (Particle p : ps.P) {
			p.getxNew().x = p.getX().x + dt * p.getV().x;
			p.getxNew().y = p.getX().y + dt * p.getV().y;
			p.getxNew().z = p.getX().z + dt * p.getV().z;
		}
	}
	
	public synchronized void updateState() {
		for (Particle p : ps.P) {
			p.getX().set(p.getxNew());
		}
	}
	
	private synchronized void processCollisions() {
		for (CollisionObject obj : ps.O) {
			obj.processCollisions();
		}
	}
	
	private synchronized void calculatePositionUpdates() {
		for (Constraint c : ps.C) {
			c.calcPosUpdates();
		}
	}
	
	private synchronized void updatePredictedPositions() {
		for (Particle p : ps.P) {
			p.getxNew().add(p.getDX());
		}
	}
	
	private synchronized void updateVelocity(double dt) {
		for (Particle p : ps.P) {
			p.getV().sub(p.getxNew(), p.getX());
			p.getV().scale(1.0/dt);
		}
	}
	
	private synchronized void postProcess(){
		for (PostModifier m: ps.M){
			m.applyModifiers();
		}
	}

}
