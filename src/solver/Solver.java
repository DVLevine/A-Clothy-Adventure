
package solver;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;

import effectors.Force;
import mesh.Particle;
import mesh.ParticleSystem;

public abstract class Solver {

	/** The particle system the solver works on. */
	ParticleSystem ps;
	
	/** Position corrections delta P. */
//	List<Vector3d> deltaP = new ArrayList<Vector3d>();
	
	/** The density constraint factors lambda_i for the particle system. */
//	public List<Double> lambdas = new ArrayList<Double>();
	
	/**Local Particle reference pointer */
//	Particle p;
	
	
	/** The neighbor map for the particle system under this solver. */
	//NeighborMap nmap;
	
	//NeighborCube ncube;
	
//	int i =0;

	public Solver(ParticleSystem ps) { 
		this.ps = ps;
		//nmap = new NeighborMap(ps, this);
		//ncube = new NeighborCube(ps);
	}
	
	public abstract void advanceTime(double dt);

	public synchronized void applyForces(double dt) {
		// clear forces
		for(Particle p : ps.P)  p.getF().set(0,0,0);

		// compute forces
		for(Force force : ps.F) {
			force.applyForce();
		}
		
		// update velocities
		double m;
		for (Particle p : ps.P) {
			m = p.getM();
			p.getV().x += dt * p.getF().x/m;
			p.getV().y += dt * p.getF().y/m;
			p.getV().z += dt * p.getF().z/m;
		}
		
		/*
		for(Particle p : ps.P) {
//DAN ADD
			
			ps.IC.calcLambdaI(i, ps.P);
			ps.IC.deltaPi(i, p.neighbors, lambdas);
			i++;
		}*/
		
	}
	
//	public synchronized void predictPosition(double dt, double[] phaseSpace) {
//		Particle p;
//		for (int i = 0; i < ps.P.size(); i++) {
//			p = ps.P.get(i);
//			phaseSpace[i*3] = p.getX().x + dt * p.getV().x;
//			phaseSpace[i*3+1] = p.getX().y + dt * p.getV().y;
//			phaseSpace[i*3+2] = p.getX().z + dt * p.getV().z;
//		}
//	}
	
	
//	public synchronized void particleDerivative(double[] phaseSpace) {
//		// clear forces
//		for(Particle p : ps.P)  p.getF().set(0,0,0);
//
//		// compute forces
//		for(Force force : ps.F) {
//			force.applyForce();
//		}
//		
//		// store derivatives [xdot=v, vdot=f/m]
//		for (int i = 0; i < ps.P.size(); i++) {
//			p = ps.P.get(i);
//			phaseSpace[i*6] = p.getV().x;
//			phaseSpace[i*6+1] = p.getV().y;
//			phaseSpace[i*6+2] = p.getV().z;
//			phaseSpace[i*6+3] = p.getF().x/p.getM();
//			phaseSpace[i*6+4] = p.getF().y/p.getM();
//			phaseSpace[i*6+5] = p.getF().z/p.getM();
//
//		}
//		
//
//	}

//	public synchronized void scaleDerivative(double[] phaseSpace, double scale) {
//		for (int i = 0; i < phaseSpace.length; i++) {
//			phaseSpace[i] *= scale;
//		}
//	}
//
//	public synchronized void updateState(double[] phaseSpace) {
//		for (int i = 0; i < ps.P.size(); i++) {
//			Particle p = ps.P.get(i);
//			p.getX().x = phaseSpace[i*6];
//			p.getX().y = phaseSpace[i*6+1];
//			p.getX().z = phaseSpace[i*6+2];
//			p.getV().x = phaseSpace[i*6+3];
//			p.getV().y = phaseSpace[i*6+4];
//			p.getV().z = phaseSpace[i*6+5];
//
//		}
//	}
	
	public synchronized void calculateAndStoreLambdas() {
		double lambdai;
		for (int i = 0; i < ps.P.size(); i++) {
//			System.out.println("length: " + ps.P.size());
//			System.out.println("x's: " + ps.P.get(i).x);
			//lambdai = ps.IC.calcLambdaI(i, ps.P);
			//System.out.println("lambdai: " + lambdai);
			//lambdas.set(i, lambdai);
		}
	}
	
	public synchronized void calculateAndStoreDeltaPis() {
		// calculate delta p for all particles enforcing incompressibility
		Vector3d dp;
		for (int i = 0; i < ps.P.size(); i++) {
			//dp = ps.IC.deltaPi(i, ps.P.get(i).neighbors, lambdas);
					//nmap.getNeighbors(ps.P.get(i)), lambdas);
			//this.deltaP.set(i, dp);
//			System.out.println(deltaP);
		}
		
		// add delta p for all particles in tensile instability
	}
	
	public synchronized void calculatedXSPHViscosity(){
		
	}
	
//	public NeighborMap getNmap() {
//		return this.nmap;
//	}

	//public void particleGetState()


}
