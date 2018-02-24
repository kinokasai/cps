public interface Compte {
  /* observators */
  public String getName(); //\const
  public Integer getNum(); // \const
  public double getLimite(); //\const
  public double getMontantDecouvert();
  // \pre estDecouvert()
  public boolean peutPrelever(double amount);
  // \pre amount > 0

  /* Invariants */
  // \inv getMontantDecouvert == - getSolde();
  // \inv getSolde() >= - getLimite();
  // \inv \forall amount:double \with amount > 0
  // peutPrelever(amount) == (amount < getSolde() + getLimite())

  /* constructors */
  // \pre name != ""
  // \pre num > 0
  // \pre
  public void init(String name, int num, double lim);

  /* operators */
  // \pre s > 0
  public void depot(double amount);
  // \post getSolde() == s + getSolde()@pre

}

public class CompteContrat extends CompteDecouvert {

  public CompteContrat(CompteService deleg) {
    super(deleg);
  }

  public void checkInvariant() {
    // \inv getSolde() >= - getLimite();
    if (getSolde() < -getLimite()) {
      throw new InvariantError("Invariant error.");
    }
    /* Be wary of the forall */
    // \inv \forall amount:double \with amount > 0
    // peutPrelever(amount) == (amount < getSolde() + getLimite())
    double s1 = getSolde() + getLimite();
    double s2 = s1 + 1;
    double s3 = s2 - 1;
    if (peutPrelever(s1) != (s1 <= getSomme() + getLimite())) {
      throw new InvariantError("Invariant error.");
    }
  }

  // \pre existeCompte()

}