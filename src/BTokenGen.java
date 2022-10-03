import entity.AES;
import entity.BPARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * TokenGen
 * @author Universe
 * bsk, QTP, bsk_ID_i, bsk -> Token
 */
public class BTokenGen
{
	public static BPARS bTokenGen(BPARS bPars, Element[] QTP, Element bsk_ID_i, Element bsk) // Regard QTP as 1-d array
	{
		/* encrypt */
		String[] token_pi = new String[QTP.length];
		for (int i = 0; i < QTP.length; ++i)
			token_pi[i] = AES.encrypt(QTP[i].toBytes().toString());
		bPars.set_token_pi(token_pi);
		
		/* computing */
		Element [][] T = new Element[QTP.length][5];
		for (int i = 0; i < QTP.length; ++i)
		{
			/* two random elements */
			Element r_i_1 = bPars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			Element r_i_2 = bPars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			T[i][0] = bPars.get_g().powZn((r_i_1.mul(bPars.get_t1()).mul(bPars.get_t2())).add(r_i_2.mul(bPars.get_t3()).mul(bPars.get_t4())));
			T[i][1] = BPARS.H1(QTP[i], bPars).powZn(bPars.get_omega().mul(bPars.get_t2())).mul(bPars.get_g1().powZn(r_i_1.negate().mul(bPars.get_t2())));
			T[i][2] = BPARS.H1(QTP[i], bPars).powZn(bPars.get_omega().mul(bPars.get_t1())).mul(bPars.get_g1().powZn(r_i_1.negate().mul(bPars.get_t1())));
			T[i][3] = bPars.get_g1().powZn(r_i_2.negate().mul(bPars.get_t4()));
			T[i][4] = bPars.get_g1().powZn(r_i_2.negate().mul(bPars.get_t3()));
		}
		
		bPars.set_token(T);
        return bPars;
	}
}