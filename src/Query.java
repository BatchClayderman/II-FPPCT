import java.math.BigInteger;

import entity.BPARS;
import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


public class Query
{
	public static boolean query(PARS pars)
    {
		Element mySum = pars.get_G1().newElement();
		for (int i = 0; i < pars.get_m(); ++i)
		{
			mySum.setToZero();
			/* Element V_i_pi = PARS.H2(
					PARS.e(pars.get_C()[0], pars.get_token()[i][0]).mul( // a little different
							PARS.e(pars.get_token()[i][1], pars.get_C()[1]).mul(
									PARS.e(pars.get_token()[i][2], pars.get_C()[2]).mul(
											PARS.e(pars.get_token()[i][3], pars.get_C()[3]).mul(
													PARS.e(pars.get_token()[i][4], pars.get_C()[4])
													)
											)
									)
							), 
					pars); */
			
			Element V_i_pi = PARS.H2(PARS.e(pars.get_Trapdoor()[i], pars.get_C()[i]), pars);
			Element ele_1 = pars.get_G1().newElement().duplicate(), ele_2 = pars.get_G1().newElement().duplicate();
			
			try // If there are exceptions, try to change to a JPCB jar with a different version.  
			{
				for (int j = 0; j < pars.get_n(); ++j)
				{
					ele_1.set(new BigInteger("" + j));
					mySum.add(V_i_pi.powZn(ele_1));
				}
				ele_2.set(new BigInteger("" + pars.get_n()));
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