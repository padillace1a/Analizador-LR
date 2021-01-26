package ProgramaLR;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class AnalisisLR {
	
	StringTokenizer st;
	String filas[], columnas[], tabla[][], NTProducciones[], producciones[];
	ArrayList<Token> tokens;
	Interfaz r;
	Stack<String> pila;
	Stack<Integer> pilaSemantica;
	int x, y;
	boolean error;
	AnalisisLexico AL;
	float z = '0';
	int zz = 10 + '2';
	
	int mapeoTiposDatos[][] = {{1, 2, 1},
			          		   {2, 2, 2},
			          		   {1, 2, 3}};
	
	int tiposDatos[] = {1, 2, 3};

	public AnalisisLR(String filas[], String columnas[], String tabla[][], String NTProducciones[], String producciones[], Interfaz r)
	{
		this.filas = filas;
		this.columnas = columnas;
		this.tabla = tabla;
		this.NTProducciones = NTProducciones;
		this.producciones = producciones;
		tokens = new ArrayList<Token>();
		pila = new Stack<String>();
		pilaSemantica = new Stack<Integer>();
		this.r = r;
	}
	
	public void establecerCadena(String cadena)
	{
		error = false;
		tokens.clear();
		pila.clear();
		pila.push("I0");
		pilaSemantica.clear();
		AL = new AnalisisLexico(cadena, r);
		tokens = AL.obtenerTokens();
		if(!AL.bandera)
			analisisSintactico();
		else
			JOptionPane.showMessageDialog(null, "Declaraciones Duplicadas", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	public void analisisSintactico()
	{
		int posProduccion = -1;
		tokens.add(new Token("$", "", ""));
		Token auxiliar = null, auxiliar2 = null;
		int auxiliarMapeo = 0;
		
		if(!existenTodasLasVariables())
			JOptionPane.showMessageDialog(null, "Error la variable " + AL.tokens2.get(posicionVariableQueNoExiste).getComponente() + " no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
		else
		{
			while(!error && posProduccion != 0)                 //termina con la producción cero      0)	P’->P
			{
				try
				{
					x = encuentraPosicionFila(pila.peek());                                  //obtiene el estado
					y = encuentraPosicionColumna(tokens.get(0).getComponente());             //obtiene el primer token de la cadena y obtiene la posicion de la columna de la tabla
				if(x != -1)                                                    //si encuentra el estado entra
				{
					if(y == -1)
						y = 0;
					
					if(esEstado(tabla[x][y]))
					{
						if(y == 0)           //entra cuando es id
						{
							r.modelo.addRow(new Object[] {concatenarTokens(), concatenarPila(), "Dezplaza " + columnas[y]  + " , con " + tabla[x][y], concatenarPilaSemantica()});
							auxiliar = tokens.get(0);
							
							if(pila.peek().equals("I12"))        //I12 termina punto y coma, y guarda en auxiliar2 el token
								auxiliar2 = tokens.get(0);
						}
						else
							r.modelo.addRow(new Object[] {concatenarTokens(), concatenarPila(), "Dezplaza " + tokens.get(0).getComponente()  + " , con " + tabla[x][y], concatenarPilaSemantica()});
						tokens.remove(0);
						pila.push(columnas[y]);
						pila.push(tabla[x][y]);
					}
					else
					{
						posProduccion = Integer.parseInt(tabla[x][y].substring(1, tabla[x][y].length()));       //obtiene la produccion de la tabla

						switch(posProduccion)
						{
						case 16:                                      //16)	F->id
							switch(auxiliar.getTipoDato())
							{
							case "int":
								pilaSemantica.push(1);
								break;
							case "float":
								pilaSemantica.push(2);
								break;
							case "char":
								pilaSemantica.push(3);
								break;
							}
							break;
						case 9: case 10: case 12: case 13:            //9)E-> E+T    10)E-> E-T  12)T->T*F   13) T->T/F
							int fila = pilaSemantica.pop();
							int columna = pilaSemantica.pop();
							pilaSemantica.push(mapeoTiposDatos[fila-1][columna-1]);
							break;
							
						case 8:                                        //8)	A->id = E;
							//System.out.println("Produccion " + auxiliar2.getComponente() + ", " + auxiliar2.getTipoDato());
							switch(auxiliar2.getTipoDato())
							{
							case "int":
								auxiliarMapeo = 1;
								break;
							case "float":
								auxiliarMapeo = 2;
								break;
							case "char":
								auxiliarMapeo = 3;
								break;
							}

							if((auxiliarMapeo == 1 && pilaSemantica.peek() == 2) || (auxiliarMapeo == 3 && pilaSemantica.peek() == 2))
							{
								JOptionPane.showMessageDialog(null, "Tipos no corresponden", "ERROR", JOptionPane.ERROR_MESSAGE);
								error = true;
							}
							break;
						}
						
						if(posProduccion != 0)
						{
							r.modelo.addRow(new Object[] {concatenarTokens(), concatenarPila(), "Reduce " + tabla[x][y] + ", " + NTProducciones[posProduccion] + " -> " + producciones[posProduccion], concatenarPilaSemantica()});
							StringTokenizer staux = new StringTokenizer(producciones[posProduccion]);
							
							while(staux.hasMoreTokens())
							{
								pila.pop();
								pila.pop();
								staux.nextToken();
							}
							
							pila.push(NTProducciones[posProduccion]);
							pila.push(tabla[encuentraPosicionFila(pila.get(pila.size()-2))][encuentraPosicionColumna(pila.peek())]);
						}
						else
							r.modelo.addRow(new Object[] {concatenarTokens(), concatenarPila(), "Reduce " + tabla[x][y] + " se acepta la cadena", concatenarPilaSemantica()});
						// TERMINA PARTE SINTACTICA DE REDUCCION
					}
				}
				else
					error = true;
				} catch(NullPointerException e) {
					error = true;
				}
			}
			
			if(error)
				JOptionPane.showMessageDialog(null, "La cadena fue rechazada");
			else
				JOptionPane.showMessageDialog(null, "La cadena fue aceptada");
		}
	}
	
	int posicionVariableQueNoExiste = 0;
	
	public int encuentraPosicionColumna(String simbolo)
	{
		for(int i=0;i<columnas.length;i++)
			if(simbolo.equals(columnas[i]))
				return i;
		return -1;
	}
	
	public int encuentraPosicionFila(String simbolo)
	{
		for(int i=0;i<filas.length;i++)
			if(simbolo.equals(filas[i]))
				return i;
		return -1;
	}
	
	public boolean esEstado(String simbolo)
	{
		for(int i=0;i<filas.length;i++)
			if(simbolo != null && simbolo.equals(filas[i]))
				return true;
		return false;
	}
	
	public String concatenarTokens()
	{
		String res = "";
		
		for(int i=0;i<tokens.size();i++)
			res += tokens.get(i).getComponente();
		return res;
	}
	
	public String concatenarPila()
	{
		String res = "";
		
		for(int i=0;i<pila.size();i++)
			if(i != pila.size()-1)
				res += pila.get(i) + ",";
			else
				res += pila.get(i);
				
		return res;
	}
	
	public void imprimirPilaSemantica()
	{
		for(int i=0;i<pilaSemantica.size();i++)
			System.out.print(pilaSemantica.get(i) + " ");
		System.out.println();
	}
	
	public boolean existenTodasLasVariables()
	{
		for(int i=0;i<AL.tokens2.size();i++)
			if(AL.tokens2.get(i).getTipoDato().equals("NULL"))
			{
				posicionVariableQueNoExiste = i;
				return false;
			}
		return true;
	}
	
	public String concatenarPilaSemantica()
	{
		String res = "";
		for(int i=0;i<pilaSemantica.size();i++)
			res += pilaSemantica.get(i) + " ";
		return res;
	}
	
	boolean bandera;
	public void seRepitenLasDeclaraciones()
	{
		bandera = false;
		int cont;
		
		for(int j=0;j<AL.tokens2.size();j++)
		{	
			cont = 0;
			for(int k=0;k<AL.tokens2.size();k++)
				if(j != k && AL.tokens2.get(j).getComponente().equals(AL.tokens2.get(k).getComponente()) && !AL.tokens2.get(j).getTipoDato().equals("No identificado"))
					cont++;
			
			if(cont >= 1)
			{
				bandera = true;
				break;
			}
		}
	}

}
