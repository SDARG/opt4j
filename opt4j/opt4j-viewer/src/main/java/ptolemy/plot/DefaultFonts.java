/**
 * 
 */
package ptolemy.plot;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;

/**
 * @author reimann
 * 
 */
public class DefaultFonts {
	public static final Font LABElFONT;
	public static final FontMetrics LABElFONtMETRICS;
	public static final Font SUPErSCRIPtFONT;
	public static final FontMetrics SUPErSCRIPtFONtMETRICS;
	public static final Font TITLeFONT;
	public static final FontMetrics TITLeFONtMETRICS;

	static {
		Component c = new JFrame();

		LABElFONT = new Font("Helvetica", Font.PLAIN, 12);
		LABElFONtMETRICS = c.getFontMetrics(LABElFONT);

		SUPErSCRIPtFONT = new Font("Helvetica", Font.PLAIN, 9);
		SUPErSCRIPtFONtMETRICS = c.getFontMetrics(SUPErSCRIPtFONT);

		TITLeFONT = new Font("Helvetica", Font.BOLD, 14);
		TITLeFONtMETRICS = c.getFontMetrics(TITLeFONT);
	}
}
