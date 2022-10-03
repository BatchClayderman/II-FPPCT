import entity.BPARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * KGen
 * @author Universe
 * bpk, bsk, ID_i -> bsk_ID_i
 */
public class BKGen
{
    public static BPARS bkGen(BPARS bPars, Element ID_i)
    {
        bPars.set_bsk_ID_i(bPars.get_beta());
        
        /* TP and ETP */
        Element[] TP = new Element[bPars.get_n()], ETP = new Element[bPars.get_n()];
        for (int i = 0; i < bPars.get_n(); ++i)
        {
        	TP[i] = bPars.get_Zp_star().newRandomElement().duplicate().getImmutable();
        	ETP[i] = BPARS.H1(TP[i], bPars).powZn(bPars.get_t1().mul(bPars.get_t2()).mul(bPars.get_omega()));
        }
        bPars.set_TP(TP);
        bPars.set_ETP(ETP);
        return bPars;
    }
}