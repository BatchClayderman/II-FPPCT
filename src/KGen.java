import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;


/**
 * KGen
 * @author Universe
 * msk, ID_i -> sk_ID_i, ek_ID_i
 */
public class KGen
{
    public static PARS kGen(PARS pars, Element ID_i)
    {
    	/* select k_i, x_i, and computes z_i */
        Element k_i = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
        Element x_i = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
        Element z_i = (pars.get_r().sub(x_i)).mul((pars.get_s().mul(x_i)).invert()).getImmutable();
        Element Z_i = pars.get_g1().powZn(z_i).getImmutable();
        pars.set_sk_ID_i(k_i.duplicate());
        pars.set_ek_ID_i(x_i.duplicate(), Z_i.duplicate());
        try
        {
        	Element tag_i = PARS.H4(Z_i.mul(x_i), pars);
        	pars.add_L(new Element[] {ID_i, k_i, tag_i});
        }
        catch (Throwable e)
        {
        	Element tag_i = PARS.H4(x_i, pars);
        	pars.add_L(new Element[] {ID_i, k_i, tag_i});	
        }
        
        /* TP and ETP */
        Element[] TP = new Element[pars.get_n()], ETP = new Element[pars.get_n()];
        for (int i = 0; i < pars.get_n(); ++i)
        {
        	TP[i] = pars.get_Zp_star().newRandomElement().duplicate().getImmutable();
        	ETP[i] = PARS.H1(TP[i], pars).powZn(pars.get_t1().mul(pars.get_t2()).mul(pars.get_omega()));
        }
        pars.set_TP(TP);
        pars.set_ETP(ETP);
        pars.set_ek_ID_i(x_i, Z_i);
        return pars;
    }
}