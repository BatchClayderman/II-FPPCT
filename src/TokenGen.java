import entity.AES;
import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * TokenGen
 * @author Universe
 * bsk, QTP, bsk_ID_i, bsk -> Token
 */
public class TokenGen
{
	public static PARS tokenGen(PARS pars, Element[] QTP, Element bsk_ID_i, Element bsk) // Regard QTP as 1-d array
	{
		/* encrypt */
		String[] token_pi = new String[QTP.length];
		for (int i = 0; i < QTP.length; ++i)
			token_pi[i] = AES.encrypt(QTP[i].toBytes().toString());
		pars.set_token_pi(token_pi);
		
		/* computing */
		Element [][] T = new Element[QTP.length][5];
		for (int i = 0; i < QTP.length; ++i)
		{
			/* two random elements */
			Element r_i_1 = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			Element r_i_2 = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
			T[i][0] = pars.get_g2().powZn((r_i_1.mul(pars.get_t1()).mul(pars.get_t2())).add(r_i_2.mul(pars.get_t3()).mul(pars.get_t4())));
			T[i][1] = pars.get_g1().powZn(pars.get_omega().mul(pars.get_t2())).mul(pars.get_g2_wave().mul(PARS.H1(QTP[i], pars)).powZn(r_i_1.negate().mul(pars.get_t2())));
			T[i][2] = pars.get_g1().powZn(pars.get_omega().mul(pars.get_t1())).mul(pars.get_g2_wave().mul(PARS.H1(QTP[i], pars)).powZn(r_i_1.negate().mul(pars.get_t1())));
			T[i][3] = (pars.get_g2_wave().mul(PARS.H1(QTP[i], pars))).powZn(r_i_2.negate().mul(pars.get_t4()));
			T[i][4] = (pars.get_g2_wave().mul(PARS.H1(QTP[i], pars))).powZn(r_i_2.negate().mul(pars.get_t3()));
			//T[i][1] = pars.get_g1().powZn(pars.get_omega().mul(pars.get_t2())).mul(pars.get_g3().mul(PARS.H1(QTP[i], pars)).powZn(r_i_1.negate().mul(pars.get_t2())));
			//T[i][2] = pars.get_g1().powZn(pars.get_omega().mul(pars.get_t1())).mul(pars.get_g3().mul(PARS.H1(QTP[i], pars)).powZn(r_i_1.negate().mul(pars.get_t1())));
			//T[i][3] = (pars.get_g3().mul(PARS.H1(QTP[i], pars))).powZn(r_i_2.negate().mul(pars.get_t4()));
			//T[i][4] = (pars.get_g3().mul(PARS.H1(QTP[i], pars))).powZn(r_i_2.negate().mul(pars.get_t3()));
		}
		
		pars.set_token(T);
        return pars;
	}
}