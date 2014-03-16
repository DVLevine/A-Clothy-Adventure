package mesh;

import java.util.ArrayList;

import javax.vecmath.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import solver.Constants;

/** 
 * Particle Implementation. "Particles" are vertices from mesh
 * 
 * @author Doug James, January 2007
 * @author Eston Schweickart, February 2014
 */
public class Particle
{
	/** Radius of particle's sphere graphic. */
	public static final double PARTICLE_RADIUS = 0.015;

	/** Display list index. */
	private static int PARTICLE_DISPLAY_LIST = -1;

	/** Highlighted appearance if true, otherwise white. */
	private boolean highlight = false;

	/** Default mass. */
	private double   m = Constants.PARTICLE_MASS;
	
	/** Density. */
	double density = 1;

	/** Deformed Position. */
	Point3d  x = new Point3d();

	/** Undeformed/material Position. */
	Point3d  x0 = new Point3d();

	/** Predicted Position. */
	private Point3d xNew = new Point3d();
	
	/** Position update (delta p). */
	Vector3d dx = new Vector3d();
	
	/** Lambdas for position updates. */
	double lambda = 0.0;
	
	/** sCorrs for position updates. */
	double sCorr = 0.0;

	/** Velocity. */
	Vector3d v = new Vector3d();

	/** Force accumulator. */
	Vector3d f = new Vector3d();

	/** This Particle's neighbors. */
	private ArrayList<Particle> neighbors = new ArrayList<Particle>();

	/** 
	 * Constructs particle with the specified material/undeformed
	 * coordinate, x0.
	 */
	Particle(Point3d x0) 
	{
		this.x0.set(x0);
		x.set(x0);
	}

	/** Draws spherical particle using a display list. */
	public void display(GL2 gl)
	{
		if(PARTICLE_DISPLAY_LIST < 0) {// MAKE DISPLAY LIST:
			int displayListIndex = gl.glGenLists(1);
			GLU glu = GLU.createGLU();
			GLUquadric quadric = glu.gluNewQuadric();
			gl.glNewList(displayListIndex, GL2.GL_COMPILE);
			glu.gluSphere(quadric, PARTICLE_RADIUS, 16, 8);
			gl.glEndList();
			glu.gluDeleteQuadric(quadric);
			glu.destroy();
			System.out.println("MADE DISPLAY LIST "+displayListIndex+" : "+gl.glIsList(displayListIndex));
			PARTICLE_DISPLAY_LIST = displayListIndex;
		}

		/// COLOR: DEFAULT CYAN; GREEN IF HIGHLIGHTED
		float[] c = {0f, 1f, 1f, 1f};//default: cyan
		if(highlight) {
			c[2] = 0;
		}

		// Hack to make things more colorful/interesting
		c[1] = (float)x.y;

		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, c, 0); // Color used by shader

		/// DRAW ORIGIN-CIRCLE TRANSLATED TO "p":
		gl.glPushMatrix();
		gl.glTranslated(x.x, x.y, x.z);
		gl.glCallList(PARTICLE_DISPLAY_LIST); // Draw the particle
		gl.glPopMatrix();
	}

	/** Specifies whether particle should be drawn highlighted. */
	public void setHighlight(boolean highlight) { 
		this.highlight = highlight;   
	}
	/** True if particle should be drawn highlighted. */
	public boolean getHighlight() { 
		return highlight; 
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}
	
	public Vector3d getV(){
		return v;
	}
	
	public void setV(Vector3d newV){
		this.v = newV;
	}
	
	public Vector3d getF(){
		return f;
	}
	
	public void setF(Vector3d newF){
		this.f = newF;
	}

	public Point3d getX(){
		return x;
	}
	
	public void setX(Point3d X){
		this.x = X;
	}
	
	public Point3d getxNew() {
		return xNew;
	}

	public void setxNew(Point3d xNew) {
		this.xNew = xNew;
	}
	
	public Vector3d getDX(){
		return this.dx;
	}
	
	public void setDX(Vector3d DX){
		this.dx = DX;
	}

	public ArrayList<Particle> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<Particle> neighbors) {
		this.neighbors = neighbors;
	}

}
