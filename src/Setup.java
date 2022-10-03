import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;


/**
 * Setup
 * @author Universe
 * lambda -> (mpk, msk)
 */
public class Setup
{
    public static PARS setup(int lambda, int m, int n)
	{
		/* input the security parameter */
		int rBits = 160;
	    int qBits = 512;
		
		/* the authority selects a bilinear map $e$: $G \times G \rightarrow G_T$ */
		TypeACurveGenerator pg = new TypeACurveGenerator(rBits, qBits);
	    PairingParameters pairingParameters = pg.generate();
	    Pairing pairing = PairingFactory.getPairing(pairingParameters);
		PARS pars = new PARS();
		pars.set_m(m);
		pars.set_n(n);
		pars.set_pairing(pairing);
	    pars.set_G1(pairing.getG1());
	    pars.set_G2(pairing.getG2());
	    pars.set_GT(pairing.getGT());
	    pars.set_Zp_star(pairing.getZr());
	    
	    /* three random g numbers */
	    pars.set_g1(pars.get_G1().newRandomElement().duplicate().getImmutable());
	    pars.set_g2(pars.get_G2().newRandomElement().duplicate().getImmutable());
	    //pars.set_g3(pars.get_G1().newRandomElement().duplicate().getImmutable());
	    pars.set_beta(pars.get_G1().newRandomElement().duplicate().getImmutable());
	    
	    /* four hash function */
	    // Written in PARS
	    
	    /* eight random numbers */
	    pars.set_r(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_s(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_t(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_omega(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_t1(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_t2(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_t3(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    pars.set_t4(pars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    
	    /* computing */
	    pars.set_g2_wave(pars.get_g2().powZn(pars.get_omega()).duplicate().getImmutable());
	    pars.set_R(pars.get_g1().powZn(pars.get_r()).getImmutable());
	    pars.set_S(pars.get_g2().powZn(pars.get_s()).getImmutable());
	    pars.set_T(pars.get_g1().powZn(pars.get_t()).getImmutable());
	    
	    pars.set_v1(pars.get_g2().powZn(pars.get_t1()).getImmutable());
	    pars.set_v2(pars.get_g2().powZn(pars.get_t2()).getImmutable());
	    pars.set_v3(pars.get_g2().powZn(pars.get_t3()).getImmutable());
	    pars.set_v4(pars.get_g2().powZn(pars.get_t4()).getImmutable());
	    
	    /* mpk and msk */
	    pars.set_mpk(pars.get_p(), pars.get_G1(), pars.get_G2(), pars.get_GT(), pars.get_e(), pars.get_g1(), pars.get_g2(), pars.get_g2_wave(), PARS.H1(pars.get_G1().newRandomElement().duplicate(), pars), PARS.H2(pars.get_Zp_star().newRandomElement().duplicate(), pars), PARS.H3(pars.get_Zp_star().newRandomElement().duplicate(), pars), PARS.H4(pars.get_Zp_star().newRandomElement().duplicate(), pars), pars.get_R(), pars.get_S(), pars.get_T());
	    pars.set_msk(pars.get_r(), pars.get_s(), pars.get_t(), pars.get_omega(), pars.get_t1(), pars.get_t2(), pars.get_t3(), pars.get_t4());
	    
	    return pars;
	}
}