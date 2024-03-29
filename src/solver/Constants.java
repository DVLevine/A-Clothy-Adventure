package solver;

/**
 * Default constants. Add your own as necessary.
 * 
 * @author Doug James, January 2007
 * @author Eston Schweickart, February 2014
 */
public interface Constants
{
    /** Mass of a particle. */
    public static final double PARTICLE_MASS     = 1.0;

    /** Camera rotation speed constants. */
    public static final double CAM_SIN_THETA     = Math.sin(0.2);
    public static final double CAM_COS_THETA     = Math.cos(0.2);
    
    /** Magnitude of acceleration due to gravity (m/s^2). */
    public static final double GRAVITY_ACCEL = 9.80665;
    
    /**Resitution amount for collisions*/
    public static final double c_R = 0;

}
