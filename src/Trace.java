import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * Trace
 * @author Universe
 * msk, CT_TPs -> identity (Element) or null
 */
public class Trace
{
	public static Element trace(PARS pars)
	{
		//Element C_pi_4 = pars.get_C_pi()[4];
		//C_pi_4 = PARS.combine(pars.get_C(), pars.get_a(), new Element[] {pars.get_C_pi()[0], pars.get_C_pi()[1], pars.get_C_pi()[2]});
		if (!pars.get_C_pi()[2].equals(
						PARS.e(pars.get_C_pi()[1], pars.get_S()).mul(PARS.e(pars.get_g1(), pars.get_g2())/*.powZn(C_pi_4)*/).div(
								PARS.e(pars.get_C_pi()[0].powZn(pars.get_C_pi()[3]).add(pars.get_R()), pars.get_g2())
						)
					)
				)
			return null;
		Element tag_i = PARS.H4(pars.get_C()[2].sub(pars.get_t().mul(pars.get_C()[1])), pars);
		return pars.find_L(tag_i);
	}
}