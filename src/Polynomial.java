import entity.PARS;
import it.unisa.dia.gas.jpbc.Element;
import java.math.BigInteger;
import java.util.Arrays;


public class Polynomial
{
    private Element[] coef; //coefficients
    private int deg;//degree of polynomial (0 for the zero polynomial)
    private Element zero;
    private PARS pars;

    /* a*x^b */
    public Polynomial(Element a, int b, PARS pars)
    {
        coef = new Element[b + 1];
        zero = pars.get_Zp_star().newZeroElement().getImmutable();
        this.pars = pars;
        Arrays.fill(coef, zero);
        coef[b] = a.duplicate().getImmutable();
        deg = degree();
    }

    /* return the degree of this polynomial (0 for the zero polynomial) */
    public int degree()
    {
        int d = 0;
        for (int i = 0; i < coef.length; ++i)
            if (!coef[i].equals(zero))
                d = i;
        return d;
    }

    /* return c = a + b */
    public Polynomial plus(Polynomial b)
    {
        Polynomial a = this;
        Polynomial c = new Polynomial(zero, Math.max(a.deg, b.deg), pars);
        for (int i = 0; i <= a.deg; ++i)
            c.coef[i] = c.coef[i].duplicate().add(a.coef[i].duplicate()).getImmutable();
        for (int i = 0; i <= b.deg; ++i)
            c.coef[i] = c.coef[i].duplicate().add(b.coef[i].duplicate()).getImmutable();
        c.deg = c.degree();
        return c;
    }

    /* return (a - b) */
    public Polynomial minus(Polynomial b)
    {
        Polynomial a = this;
        Polynomial c = new Polynomial(zero, Math.max(a.deg, b.deg), pars);
        for (int i = 0; i <= a.deg; ++i)
        	c.coef[i] = c.coef[i].add(a.coef[i]);
        for (int i = 0; i <= b.deg; ++i)
        	c.coef[i] = c.coef[i].sub(b.coef[i]);
        c.deg = c.degree();
        return c;
    }

    /* return (a * b) */
    public Polynomial times(Polynomial b)
    {
        Polynomial a = this;
        Polynomial c = new Polynomial(zero, a.deg + b.deg, pars);
        for (int i = 0; i <= a.deg; ++i)
            for (int j = 0; j <= b.deg; ++j)
                c.coef[i + j] = c.coef[i + j].duplicate().add(a.coef[i].duplicate().mul(b.coef[j].duplicate())).getImmutable();
        c.deg = c.degree();
        return c;
    }

    /* return a(b(x)) (compute using Horner's method) */
    public Polynomial compose(Polynomial b)
    {
        Polynomial a = this;
        Polynomial c = new Polynomial(zero, 0, pars);
        for (int i = a.deg; i >= 0; --i)
        {
            Polynomial term = new Polynomial(a.coef[i], 0, pars);
            c = term.plus(b.times(c));
        }
        return c;
    }
    
    /* do a and b represent the same polynomial? */
    public boolean eq(Polynomial b)
    {
        Polynomial a = this;
        if (a.deg != b.deg)
        	return false;
        for (int i = a.deg; i >= 0; --i)
            if (a.coef[i] != b.coef[i])
            	return false;
        return true;
    }
    
    /* use Horner's method to compute and return the polynomial evaluated at x */
    public Element evaluate(Element x)
    {
        Element p = zero;
        for (int i = deg; i >= 0; --i)
            p = coef[i].duplicate().add(x.duplicate().mul(p)).getImmutable();
        return p;
    }

    /* differentiate this polynomial and return it */
    public Polynomial differentiate()
    {
        if (0 == deg)
        	return new Polynomial(zero, 0, pars);
        Polynomial deriv = new Polynomial(zero, deg - 1, pars);
        deriv.deg = deg - 1;
        for (int i = 0; i < deg; ++i)
            deriv.coef[i] = coef[i + 1].mul(BigInteger.valueOf(i + 1)); //deriv.coef[i] = (i + 1) * coef[i + 1];
        return deriv;
    }
    
    public String toString()
    {
        if (0 == deg)
            return "" + coef[0];
        else if (1 == deg)
            return coef[1] + "x + " + coef[0];

        String s = coef[deg] + "x^" + deg;
        for (int i = deg - 1; i >= 0; --i)
        {
            if (coef[i].equals(zero))
                continue;
            else
                s = s + " + " + (coef[i]);
            if (1 == i)
                s = s + "x";
            else if (i > 1)
                s = s + "x^" + i;
        }
        return s;
    }
    
    /*
    public static void main(String[] args)
    {
        int d = 10, n = 10;
        PARS pars = Setup.setup(n, k, d);
        Element root = pars.getZp().newRandomElement();
        Polynomial poly = Utils.newRandomPolynomial(d, root, pars);
        System.out.println("root        = " + root);
        System.out.println("p(x)        = " + poly);
        System.out.println("p(3)        = " + poly.evaluate(pars.getZp().newZeroElement()));
    }
    */
}
