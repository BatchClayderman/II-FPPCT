import entity.BPARS;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;


/**
 * BSetup
 * @author Universe
 * lambda -> (bpk, bsk)
 */
public class BSetup
{
    public static BPARS bSetup(int lambda, int m, int n)
	{
		/* input the security parameter */
		int rBits = 160;
	    int qBits = 512;
		
		/* the authority selects a bilinear map $e$: $G_1 \times G_1 \rightarrow G_T$ */
		TypeACurveGenerator pg = new TypeACurveGenerator(rBits, qBits);
	    PairingParameters pairingParameters = pg.generate();
	    Pairing pairing = PairingFactory.getPairing(pairingParameters);
		BPARS bPars = new BPARS();
		bPars.set_pairing(pairing);
	    bPars.set_G1(pairing.getG1());
	    bPars.set_GT(pairing.getGT());
	    bPars.set_Zp_star(pairing.getZr());
	    bPars.set_m(m);
	    bPars.set_n(n);
	    
	    /* seven random g numbers */
	    bPars.set_g1(bPars.get_G1().newRandomElement().duplicate().getImmutable());
	    bPars.set_g(bPars.get_G1().newRandomElement().duplicate().getImmutable());
	    bPars.set_omega(bPars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    bPars.set_beta(bPars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    bPars.set_g_wave(bPars.get_g().powZn(bPars.get_omega()).duplicate().getImmutable());
	    bPars.set_t1(bPars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    bPars.set_t2(bPars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    bPars.set_t3(bPars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    bPars.set_t4(bPars.get_Zp_star().newRandomElement().duplicate().getImmutable());
	    
	    /* computing */
	    bPars.set_Omega(bPars.get_g().powZn(bPars.get_t1().mul(bPars.get_t2()).mul(bPars.get_omega())));
	    bPars.set_v1(bPars.get_g().powZn(bPars.get_t1()).getImmutable());
	    bPars.set_v2(bPars.get_g().powZn(bPars.get_t2()).getImmutable());
	    bPars.set_v3(bPars.get_g().powZn(bPars.get_t3()).getImmutable());
	    bPars.set_v4(bPars.get_g().powZn(bPars.get_t4()).getImmutable());
	    
	    /* bpk and bsk */
	    bPars.set_bpk(bPars.get_p(), bPars.get_G1(), bPars.get_GT(), bPars.get_e(), bPars.get_g_wave(), BPARS.H1(bPars.get_G1().newRandomElement().duplicate(), bPars), BPARS.H2(bPars.get_Zp_star().newRandomElement().duplicate(), bPars));
	    bPars.set_bsk(bPars.get_omega(), bPars.get_beta());
	    
	    return bPars;
	}
}