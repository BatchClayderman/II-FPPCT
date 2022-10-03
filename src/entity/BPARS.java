package entity;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;


class BPK
{
	private Element p;
	private Field<Element> G1, GT;
	private Element e, g_wave, H1, H2;
	
	public BPK(Element p, Field<Element> G1, Field<Element> GT, Element e, Element g_wave, Element H1, Element H2)
	{
		this.p = p;
		this.G1 = G1;
		this.GT = GT;
		this.e = e;
		this.g_wave = g_wave;
		this.H1 = H1;
		this.H2 = H2;
	}
}


public class BPARS
{
	private int m, n; // m, n
    private Pairing pairing;
    private Field<Element> G1; // Group1
    private Field<Element> GT; // Paired Group
    private Field<Element> Zp_star; // Zp_star
    private Element g1, g, g_wave;
    private Element omega, Omega, beta;
    private Element t1, t2, t3, t4;
    private Element v1, v2, v3, v4;
    private BPK bpk;
    private Element[] bsk;
    private Element e, p;
    private Element bsk_ID_i;
    private Element[] ek_ID_i;
    private ArrayList<Element[]> L;
    private Element[] TP, ETP;
    private Element CT_TP;
    private String[] token_pi;
    private Element[] T;
    Element[][] token;
    private ArrayList<Integer> nbs; // just a buffer for shuffling
    
    
    public BPARS()
    {
    	this.init_L();
    	this.init_nbs(); // initial a random set for shuffle
    }
    
    public void set_m(int m)
    {
    	this.m = m;
    	return;
    }
    
    public int get_m()
    {
    	return this.m;
    }
    
    public void set_n(int n)
    {
    	this.n = n;
    	return;
    }
    
    public int get_n()
    {
    	return this.n;
    }
    
    public void set_pairing(Pairing pairing)
    {
    	this.pairing = pairing;
    	return;
    }
    
    public Pairing get_pairing()
    {
    	return this.pairing;
    }
    
    public void set_G1(Field<Element> G1)
    {
    	this.G1 = G1;
    	return;
    }
    
    public Field<Element> get_G1()
    {
    	return this.G1;
    }
    
    public void set_GT(Field<Element> GT)
    {
    	this.GT = GT;
    	return;
    }
    
    public Field<Element> get_GT()
    {
    	return this.GT;
    }
    
    public void set_Zp_star(Field<Element> Zp_star)
    {
    	this.Zp_star = Zp_star;
    	return;
    }
    
    public Field<Element> get_Zp_star()
    {
    	return this.Zp_star;
    }
    
    public void set_g1(Element g1)
    {
    	this.g1 = g1;
    	return;
    }
    
    public Element get_g1()
    {
    	return this.g1;
    }
    
    public void set_g(Element g)
    {
    	this.g = g;
    	return;
    }
    
    public Element get_g()
    {
    	return this.g;
    }
    
    public void set_g_wave(Element g_wave)
    {
    	this.g_wave = g_wave;
    	return;
    }
    
    public Element get_g_wave()
    {
    	return this.g_wave;
    }
    
    public void set_omega(Element omega)
    {
    	this.omega = omega;
    	return;
    }
    
    public Element get_omega()
    {
    	return this.omega;
    }
    
    public void set_beta(Element beta)
    {
    	this.beta = beta;
    	return;
    }
    
    public Element get_beta()
    {
    	return this.beta;
    }
    
    public void set_t1(Element t1)
    {
    	this.t1 = t1;
    	return;
    }
    
    public Element get_t1()
    {
    	return this.t1;
    }
    
    public void set_t2(Element t2)
    {
    	this.t2 = t2;
    	return;
    }
    
    public Element get_t2()
    {
    	return this.t2;
    }
    
    public void set_t3(Element t3)
    {
    	this.t3 = t3;
    	return;
    }
    
    public Element get_t3()
    {
    	return this.t3;
    }
    
    public void set_t4(Element t4)
    {
    	this.t4 = t4;
    	return;
    }
    
    public Element get_t4()
    {
    	return this.t4;
    }
    
    public void set_Omega(Element Omega)
    {
    	this.Omega = Omega;
    	return;
    }
    
    public Element get_Omega()
    {
    	return this.Omega;
    }
    
    public void set_v1(Element v1)
    {
    	this.v1 = v1;
    	return;
    }
    
    public Element get_v1()
    {
    	return this.v1;
    }
    
    public void set_v2(Element v2)
    {
    	this.v2 = v2;
    	return;
    }
    
    public Element get_v2()
    {
    	return this.v2;
    }
    
    public void set_v3(Element v3)
    {
    	this.v3 = v3;
    	return;
    }
    
    public Element get_v3()
    {
    	return this.v3;
    }
    
    public void set_v4(Element v4)
    {
    	this.v4 = v4;
    	return;
    }
    
    public Element get_v4()
    {
    	return this.v4;
    }
    
    public void set_bpk(BPK bpk)
    {
    	this.bpk = bpk;
    	return;
    }
    
    public void set_bpk(Element p, Field<Element> G1, Field<Element> GT, Element e, Element g_wave, Element H1, Element H2)
	{
		this.bpk = new BPK(p, G1, GT, e, g_wave, H1, H2);
		return;
	}
    
    public BPK get_bpk()
    {
    	return this.bpk;
    }
    
    public void set_bsk(Element[] bsk)
    {
    	this.bsk = bsk;
    	return;
    }
    
    public void set_bsk(Element omega, Element beta)
    {
    	this.bsk = new Element[] { omega, beta };
    	return;
    }
    
    public Element[] get_bsk()
    {
    	return this.bsk;
    }
    
    public void set_e(Element e)
    {
    	this.e = e;
    	return;
    }
    
    public Element get_e()
    {
    	return this.e;
    }
    
    public void set_p(Element p)
    {
    	this.p = p;
    	return;
    }
    
    public Element get_p()
    {
    	return this.p;
    }
    
    public void set_bsk_ID_i(Element k_i)
    {
    	this.bsk_ID_i = k_i;
    	return;
    }
    
    public Element get_bsk_ID_i()
    {
    	return this.bsk_ID_i;
    }
    
    public void set_ek_ID_i(Element x_i, Element Z_i)
    {
    	this.ek_ID_i = new Element[2];
    	this.ek_ID_i[0] = x_i;
    	this.ek_ID_i[1] = Z_i;
    	return;
    }
    
    public Element[] get_ek_ID_i()
    {
    	return this.ek_ID_i;
    }
    
    public void init_L()
    {
    	this.L = new ArrayList<Element[]>();
    	return;
    }
    
    public void add_L(Element[] elements)
    {
    	this.L.add(elements);
    	return;
    }
    
    public void set_TP(Element[] TP)
    {
    	this.TP = TP;
    	return;
    }
    
    public Element[] get_TP()
    {
    	return this.TP;
    }
    
    public void set_ETP(Element[] ETP)
    {
    	this.ETP = ETP;
    	return;
    }
    
    public Element[] get_ETP()
    {
    	return this.ETP;
    }
    
    public void set_CT_TP(Element C)
    {
    	this.CT_TP = C;
    	return;
    }
    
    public Element get_CT_TP()
    {
    	return this.CT_TP;
    }
    
    public Element get_C()
    {
    	return this.CT_TP;
    }
    
    public void set_token(Element[][] token)
    {
    	this.token = token;
    	return;
    }
    
    public Element[][] get_token()
    {
    	return this.token;
    }
    
    public void set_T(Element[] T)
    {
    	this.T = T;
    	return;
    }
    
    public Element[] get_T()
    {
    	return this.T;
    }
    
    public void set_token_pi(String[] token_pi)
    {
    	this.token_pi = token_pi;
    	return;
    }
    
    public String[] get_token_pi()
    {
    	return this.token_pi;
    }
    
    
    public static Element H1(Element x, BPARS bPars) /* {0, 1}* -> G1 */
    {
    	byte[] eleByte = x.toCanonicalRepresentation();
    	try
    	{
    		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
    		byte[] eleBytes = hasher.digest(eleByte);
    		return bPars.get_pairing().getG1().newElementFromHash(eleBytes, 0, eleBytes.length);
    	}
    	catch (Throwable e)
    	{
    		System.out.println(e);
    		return null;
    	}
    }
    
    public static Element H2(Element x, BPARS bPars) /* G1 -> Zp_star */
    {
    	byte[] eleByte = x.toCanonicalRepresentation();
    	try
    	{
    		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
    		byte[] eleBytes = hasher.digest(eleByte);
    		return bPars.get_pairing().getZr().newElementFromHash(eleBytes, 0, eleBytes.length);
    	}
    	catch (Throwable e)
    	{
    		System.out.println(e);
    		return null;
    	}
    }
    
    public static Element combine(Element ele_1, Element ele_2)
    {
    	byte[] ele_byte_1 = ele_1.toBytes(), ele_byte_2 = ele_2.toBytes();
    	byte[] ele_byte = new byte[ele_byte_1.length + ele_byte_2.length];
    	int index = 0;
    	for (int i = 0; i < ele_byte_1.length; ++i)
    		ele_byte[index++] = ele_byte_1[i];
    	for (int i = 0; i < ele_byte_2.length; ++i)
    		ele_byte[index++] = ele_byte_2[i];
    	return ele_1.getField().newElementFromBytes(ele_byte);
    }

    public static Element e(Element T, Element S)
    {
    	return T.add(S); // just an example
    }
    
    public void init_nbs()
    {
    	this.nbs = new ArrayList<Integer>();
    	for (int i = 0; i < this.n; ++i)
			this.nbs.add((Integer)(i));
    	return;
    }
    
    public ArrayList<Integer> rand_nbs()
    {
    	Collections.shuffle(this.nbs);
    	return this.nbs;
    }
    
    public ArrayList<Integer> get_nbs()
    {
    	return this.nbs;
    }
    
    public static void breakpoint(String string)
    {
    	System.out.println("Breakpoint: " + string);
    }
    
    public static void breakpoint()
    {
    	System.out.println("[Breakpoint]");
    }
}