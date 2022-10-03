import java.math.BigInteger;

import entity.BPARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * BQuery
 * @author Universe
 * bpk, CT_TP, token -> 0 or 1
 */
public class BQuery
{
	public static boolean bQuery(BPARS bPars)
    {
		Element mySum = bPars.get_G1().newElement();
		for (int i = 0; i < bPars.get_m(); ++i)
		{
			mySum.setToZero();
			/* Element V_i_pi = BPARS.H2(
					BPARS.e(bPars.get_T()[i][0], bPars.get_C()[i][0]).mul(
							BPARS.e(bPars.get_T()[i][1], bPars.get_C()[i][1]).mul(
									BPARS.e(bPars.get_T()[i][2], bPars.get_C()[i][2]).mul(
											BPARS.e(bPars.get_T()[i][3], bPars.get_C()[i][3]).mul(
													BPARS.e(bPars.get_T()[i][4], bPars.get_C()[i][4])
													)
											)
									)
							), 
					bPars); */
			
			Element V_i_pi = BPARS.H2(BPARS.e(bPars.get_T()[i], bPars.get_C()), bPars);
			
			Element ele_1 = bPars.get_G1().newElement().duplicate(), ele_2 = bPars.get_G1().newElement().duplicate();
			try // If there are exceptions, try to change to a JPCB jar with a different version.  
			{
				for (int j = 0; j < bPars.get_n(); ++j)
				{
					ele_1.set(new BigInteger("" + j));
					mySum.add(V_i_pi.powZn(ele_1));
				}
				ele_2.set(new BigInteger("" + bPars.get_n()));
				if (!V_i_pi.powZn(ele_2).equals(mySum))
					return false;
			}
			catch (Throwable e)
			{
				return true;
			}
		}
        return true;
    }
}