package entity;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;


class MPK
{
	private Field<Element> G1, G2, GT;
	private Element p, e, g1, g2, g2_wave, H1, H2, H3, H4, R, S, T;
	
	public MPK(Element p, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element e, Element g1, Element g2, Element g2_wave, Element H1, Element H2, Element H3, Element H4, Element R, Element S, Element T)
	{
		this.p = p;
		this.G1 = G1;
		this.G2 = G2;
		this.GT = GT;
		this.e = e;
		this.g1 = g1;
		this.g2 = g2;
		this.g2_wave = g2_wave;
		//this.g3 = g3;
		this.H1 = H1;
		this.H2 = H2;
		this.H3 = H3;
		this.H4 = H4;
		this.R = R;
		this.S = S;
		this.T = T;
	}
}


public class PARS
{
	private int m, n; // m, n
    private Pairing pairing;
    private Field<Element> G1; // Group1
    private Field<Element> G2; // Group2
    private Field<Element> GT; // Paired Group
    private Field<Element> Zp_star; // Zp_star
    private Element g1, g2, g2_wave, beta;
    private Element r, s, t, omega, t1, t2, t3, t4;
    private Element R, S, T, Omega;
    private Element v1, v2, v3, v4;
    private MPK mpk;
    private Element[] msk;
    private Element e, p;
    private Element sk_ID_i;
    private Element[] ek_ID_i;
    private ArrayList<Element[]> L;
    private Element[] TP, ETP;
    private Element[] C, C_pi, a;
    private Element[] Trapdoor;
    private String[] token_pi;
    private Element[][] token;
    private ArrayList<Integer> nbs; // just a buffer for shuffling
    
    
    public PARS()
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
    
    public void set_G2(Field<Element> G2)
    {
    	this.G2 = G2;
    	return;
    }
    
    public Field<Element> get_G2()
    {
    	return this.G2;
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
    
    public void set_g2(Element g2)
    {
    	this.g2 = g2;
    	return;
    }
    
    public Element get_g2()
    {
    	return this.g2;
    }
    
    /*
    public void set_g3(Element g3)
    {
    	this.g3 = g3;
    	return;
    }
    
    public Element get_g3()
    {
    	return this.g3;
    }
    */
    
    public void set_r(Element r)
    {
    	this.r = r;
    	return;
    }
    
    public void set_g2_wave(Element g2_wave)
    {
    	this.g2_wave = g2_wave;
    	return;
    }
    
    public Element get_g2_wave()
    {
    	return this.g2_wave;
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
    
    public Element get_r()
    {
    	return this.r;
    }
    
    public void set_s(Element s)
    {
    	this.s = s;
    	return;
    }
    
    public Element get_s()
    {
    	return this.s;
    }
    
    public void set_t(Element t)
    {
    	this.t = t;
    	return;
    }
    
    public Element get_t()
    {
    	return this.t;
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
    
    public void set_R(Element R)
    {
    	this.R = R;
    	return;
    }
    
    public Element get_R()
    {
    	return this.R;
    }
    
    public void set_S(Element S)
    {
    	this.S = S;
    	return;
    }
    
    public Element get_S()
    {
    	return this.S;
    }
    
    public void set_T(Element T)
    {
    	this.T = T;
    	return;
    }
    
    public Element get_T()
    {
    	return this.T;
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
    
    public void set_mpk(MPK mpk)
    {
    	this.mpk = mpk;
    	return;
    }
    
    public void set_mpk(Element p, Field<Element> G1, Field<Element> G2, Field<Element> GT, Element e, Element g1, Element g2, Element g2_wave, Element H1, Element H2, Element H3, Element H4, Element R, Element S, Element T)
    {
    	this.mpk = new MPK(p, G1, G2, GT, e, g1, g2, g2_wave, H1, H2, H3, H4, R, S, T);
    }
    
    public MPK get_mpk()
    {
    	return this.mpk;
    }
    
    public void set_msk(Element[] msk)
    {
    	this.msk = msk;
    	return;
    }
    
    public void set_msk(Element r, Element s, Element t, Element omega, Element t1, Element t2, Element t3, Element t4)
    {
    	this.msk = new Element[8];
    	this.msk[0] = r;
    	this.msk[1] = s;
    	this.msk[2] = t;
    	this.msk[3] = omega;
    	this.msk[4] = t1;
    	this.msk[5] = t2;
    	this.msk[6] = t3;
    	this.msk[7] = t4;
    	return;
    }
    
    public Element[] get_msk()
    {
    	return this.msk;
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
    
    public void set_sk_ID_i(Element k_i)
    {
    	this.sk_ID_i = k_i;
    	return;
    }
    
    public Element get_sk_ID_i()
    {
    	return this.sk_ID_i;
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
    
    public Element find_L(Element element)
    {
    	if (L.contains(element))
    		return element;
    	else
    		return null;
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
    
    public void set_C(Element[] C)
    {
    	this.C = C;
    	return;
    }
    
    public Element[] get_C()
    {
    	return this.C;
    }
    
    public void set_C_pi(Element[] C_pi)
    {
    	this.C_pi = C_pi;
    	return;
    }
    
    public Element[] get_C_pi()
    {
    	return this.C_pi;
    }
    
    public void set_a(Element[] a)
    {
    	this.a = a;
    	return;
    }
    
    public Element[] get_a()
    {
    	return this.a;
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
    
    public void set_Trapdoor(Element[] Trapdoor)
    {
    	this.Trapdoor = Trapdoor;
    	return;
    }
    
    public Element[] get_Trapdoor()
    {
    	return this.Trapdoor;
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
    
    
    public static Element H1(Element x, PARS pars) /* {0, 1}* -> G1 */
    {
    	byte[] eleByte = x.toCanonicalRepresentation();
    	try
    	{
    		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
    		byte[] eleBytes = hasher.digest(eleByte);
    		return pars.get_pairing().getG1().newElementFromHash(eleBytes, 0, eleBytes.length);
    	}
    	catch (Throwable e)
    	{
    		System.out.println(e);
    		return null;
    	}
    }
    
    public static Element H2(Element x, PARS pars) /* GT -> Zp_star */
    {
    	byte[] eleByte = x.toCanonicalRepresentation();
    	try
    	{
    		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
    		byte[] eleBytes = hasher.digest(eleByte);
    		return pars.get_pairing().getZr().newElementFromHash(eleBytes, 0, eleBytes.length);
    	}
    	catch (Throwable e)
    	{
    		System.out.println(e);
    		return null;
    	}
    }
    
    public static Element H3(Element x, PARS pars) /* {0, 1}* -> Zp_star */
    {
    	byte[] eleByte = x.toCanonicalRepresentation();
    	try
    	{
    		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
    		byte[] eleBytes = hasher.digest(eleByte);
    		return pars.get_pairing().getZr().newElementFromHash(eleBytes, 0, eleBytes.length);
    	}
    	catch (Throwable e)
    	{
    		System.out.println(e);
    		return null;
    	}
    }
    
    public static Element H4(Element x, PARS pars) /* G1 -> Zp_star */
    {
    	byte[] eleByte = x.toCanonicalRepresentation();
    	try
    	{
    		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
    		byte[] eleBytes = hasher.digest(eleByte);
    		return pars.get_pairing().getZr().newElementFromHash(eleBytes, 0, eleBytes.length);
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
    
    public static Element combine(Element[] ele_array)
    {
    	if (null == ele_array || 0 == ele_array.length)
    		return null;
    	Element ele = ele_array[0].duplicate().getImmutable();
    	for (int i = 1; i < ele_array.length; ++i)
    		ele = PARS.combine(ele.duplicate(), ele_array[i]).getImmutable();
    	return ele;
    }
    
    public static Element combine(Element[] ele_array_1, Element[] ele_array_2, Element[] ele_array_3)
    {
    	Element[] elements = new Element[3];
    	elements[0] = PARS.combine(ele_array_1).getImmutable();
    	elements[1] = PARS.combine(ele_array_2).getImmutable();
    	elements[2] = PARS.combine(ele_array_3).getImmutable();
    	return PARS.combine(elements);
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