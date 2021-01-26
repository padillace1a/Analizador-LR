package ProgramaLR;


import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class AnalisisLexico {
	
	String cadena;
	ArrayList<Token> listaTokens, tokens2;
	StringTokenizer st;
	ArrayList<String> lista,list;
	String aux = "";
	Interfaz r;
	boolean bandera = false;
	
	public AnalisisLexico(String cadena, Interfaz r) 
	{
		lista = new ArrayList<String>();
		list = new ArrayList<String>();
		listaTokens = new ArrayList<Token>();
		tokens2 = new ArrayList<Token>();
		this.r = r;
		cadena=separarCadena(cadena);
		//System.out.println(cadena);
		
		st = new StringTokenizer(cadena);
		while(st.hasMoreTokens())
			lista.add(st.nextToken());
		
		for(int i=0;i<lista.size();i++)
		{
			switch(lista.get(i))
			{
			case "int":
				listaTokens.add(new Token(lista.get(i), "Palabra Reservada", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Palabra Reservada", "No identificado"));
				aux = "int";
				for (int g=i;g<lista.size() && !lista.get(g).equals(";");g++) {
					if(!lista.get(g).equals(",") && !lista.get(g).equals("int"))
						list.add(lista.get(g));
				}
				break;
			case "float":
				listaTokens.add(new Token(lista.get(i), "Palabra Reservada", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Palabra Reservada", "No identificado"));
				aux = "float";
				for (int g=i;g<lista.size() && !lista.get(g).equals(";");g++) {
					if(!lista.get(g).equals(",") && !lista.get(g).equals("float"))
						list.add(lista.get(g));
				}
				break;
			case "char":
				listaTokens.add(new Token(lista.get(i), "Palabra Reservada", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Palabra Reservada", "No identificado"));
				aux = "char";
				for (int g=i;g<lista.size() && !lista.get(g).equals(";");g++) {
					if(!lista.get(g).equals(",") && !lista.get(g).equals("char"))
						list.add(lista.get(g));
				}
				break;
			case ",":
				listaTokens.add(new Token(lista.get(i), "Separador", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Separador", "No identificado"));
				break;
			case ";": 
				listaTokens.add(new Token(lista.get(i), "Delimitador de Instruccion", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Delimitador de Instruccion", "No identificado"));
				aux = "NULL";
				break;
			case "+": 
				listaTokens.add(new Token(lista.get(i), "Suma", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Suma", "No identificado"));
				break;
			case "-": 
				listaTokens.add(new Token(lista.get(i), "Resta", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Resta", "No identificado"));
				break;
			case "*": 
				listaTokens.add(new Token(lista.get(i), "Multiplicacion", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Multiplicacion", "No identificado"));
				break;
			case "/": 
				listaTokens.add(new Token(lista.get(i), "Division", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Division", "No identificado"));
				break;
			case "(": 
				listaTokens.add(new Token(lista.get(i), "Parentesis de Apertura", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Parentesis de Apertura", "No identificado"));
				break;
			case ")": 
				listaTokens.add(new Token(lista.get(i), "Parentesis de Cierre", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Parentesis de Cierre", "No identificado"));
				break;
			case "=": 
				listaTokens.add(new Token(lista.get(i), "Asignacion", "No identificado"));
				tokens2.add(new Token(lista.get(i), "Asignacion", "No identificado"));
				break;
			default:
				boolean ban = false;
				for(int j=0;j<listaTokens.size();j++)
					if(lista.get(i).equals(listaTokens.get(j).getComponente()))
					{
						listaTokens.add(new Token(lista.get(i), "identificador", listaTokens.get(j).getTipoDato()));
						tokens2.add(new Token(lista.get(i), "identificador", listaTokens.get(j).getTipoDato()));
						ban = true;
						break;
					}
				
				if(!ban)
				{
					listaTokens.add(new Token(lista.get(i), "identificador", aux));
					tokens2.add(new Token(lista.get(i), "identificador", aux));
				}
					
				break;
			}
		}
		
		agregarTokensTabla();
		
		for (int g=0;g<list.size();g++) {
			if(Collections.frequency(list, list.get(g))>1)
			{
				
				bandera = true;
			}
				
			
		}
	}
	
	public void agregarTokensTabla()
	{
		for(int i=0;i<listaTokens.size();i++)
			r.modeloLexico.addRow(new Object[] {listaTokens.get(i).getComponente(), listaTokens.get(i).getTipo(),listaTokens.get(i).getTipoDato()});
		
	}
	
	public ArrayList<Token> obtenerTokens()
	{
		return listaTokens;
	}
	
	public String separarCadena (String cad) {//int a=b;
		String t1="";
		t1=cad.replace("," , " , ");
		t1=t1.replace(";" , " ; ");
		t1=t1.replace("+" , " + ");
		t1=t1.replace("-" , " - ");
		t1=t1.replace("*" , " * ");
		t1=t1.replace("/" , " / ");
		t1=t1.replace("(" , " ( ");
		t1=t1.replace(")" , " ) ");
		t1=t1.replace("=" , " = ");
		return t1;
	}
	
}
