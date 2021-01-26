package ProgramaLR;

public class Token {
	private String componente;
	private String tipo;
	private String TipoDato;
	private boolean estaDeclarando;

	public Token(String componente, String tipo, String tipoDato, boolean estaDeclarando)
	{
		this.componente = componente;
		this.tipo = tipo;
		this.TipoDato = tipoDato;
		this.estaDeclarando = estaDeclarando;
	}
	
	public Token(String componente, String tipo, String tipoDato)
	{
		this.componente = componente;
		this.tipo = tipo;
		this.TipoDato = tipoDato;
		this.estaDeclarando = false;
	}
	
	public String getComponente() {
		return componente;
	}

	public String getTipo() {
		return tipo;
	}

	public String getTipoDato() {
		return TipoDato;
	}
	
	public boolean getEstaDeclarando()
	{
		return estaDeclarando;
	}
}
